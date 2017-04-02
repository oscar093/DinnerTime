package dinnerTime;

import java.net.ServerSocket;
import java.net.Socket;
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
				pool.addRunnable(new ClientHandler(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ClientHandler implements Runnable {
		private Socket socket;
		private Recipe recipe;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			while (true) {
				try {
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					Object obj = ois.readObject();
					
					if(obj instanceof Recipe){
						recipe = (Recipe)obj;
						newRecipe(recipe);
					}

				} catch (IOException | ClassNotFoundException e) {
				}
			}
		}
		
		public void newRecipe(Recipe recipe){
			//vad som h�nder n�r ett recept skickas fr�n klienten
			String title = recipe.getTitle();
			String author = recipe.getAuthor();
			String country = recipe.getCountry();
			int time = recipe.getTime();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(3250, 5);
		server.start();
	}
}