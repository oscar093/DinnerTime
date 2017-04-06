package dinnerTime;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class Server extends Thread {
	private int port;
	private Pool pool;

	public Server(int port, int nbrOfThreads) {
		this.port = port;
		pool = new Pool(nbrOfThreads);
		pool.start();
	}

	public void run() {
		while (true) {
			try (ServerSocket ss = new ServerSocket(port)) {
				Socket socket = ss.accept();
				System.out.println("SERVER PÅ");
				pool.addRunnable(new ClientHandler(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ClientHandler implements Runnable {
		private Socket socket;
		private Recipe recipe;
		private User user;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				while (true) {
					Object obj = ois.readObject();

					if (obj instanceof Recipe) {
						recipe = (Recipe) obj;
						newRecipe(recipe);
					} else if (obj instanceof User) {
						user = (User) obj;
						newUser(user);
					} else if (obj instanceof String) {
						String str = obj.toString();
						if (str.startsWith("search")) {
							System.out.println("Sökning: " + str.substring(7));	//sökningen av klienten böjar alltid på "search", substringen börjar därefter
						}
					}
				}
			} catch (IOException | ClassNotFoundException e) {
			}
		}

		public void newRecipe(Recipe recipe) {
			// vad som händer när ett recept skickas från klienten
			String title = recipe.getTitle();
			String country = recipe.getCountry();
			int time = recipe.getTime();
			String ingredients = recipe.getIngredients();

			System.out.println("Title: " + title + "\nAuthor: " + user.getName() + "\nCountry: " + country + "\nTime: "
					+ time + " minuter \nIngredients: " + ingredients);
		}

		public void newUser(User user) {
			System.out.println("Username: " + user.getName());
			System.out.println("Password: " + user.getPassword());
		}
	}

	public static void main(String[] args) {
		Server server = new Server(3250, 5);
		server.start();
	}
}