package dinnerTime;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
		private BufferedWriter bwRecipe = null;
		private BufferedWriter bwUser = null;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				bwRecipe = new BufferedWriter(new FileWriter("recipes/recipes.txt", true));
				bwUser = new BufferedWriter(new FileWriter("users/users.txt", true));
			} catch (IOException e) {
			}
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
						if (str.startsWith("login")) {	//när klienten loggar in skickas en sträng med username/password som alltid börjar på "login"
							str = str.substring(5);
							boolean OKToLogIn = checkLogin(str);
							System.out.println("Login: " + OKToLogIn);
							oos.writeObject(OKToLogIn);
							oos.flush();
						}
						if (str.startsWith("search")) {	//klientens sökningar börjar alltid på "search"
							str = str.substring(6);
							ArrayList<String> recipes = userAction(str);
							if (recipes.size() == 0) {
								oos.writeObject("recipe" + "Receptet " + str.substring(6) + " finns inte");
								oos.flush();
							} else {
								for (int i = 0; i < recipes.size(); i++) {
									oos.writeObject("recipe" + recipes.get(i));
									oos.flush();
								}
							}
						}
					}
				}
			} catch (IOException | ClassNotFoundException e) {
			}
		}

		public void newRecipe(Recipe recipe) {
			String theRecipe = "Titel: " + recipe.getTitle() + ", Skapare: " + user.getName() + ", Land: " + recipe.getCountry() + ", Tid: "
					+ recipe.getTime() + " minuter, Ingredienser: " + recipe.getIngredients();
			try {
				bwRecipe.write(theRecipe + "\n");
				bwRecipe.flush();
			} catch (IOException e) {
			}
		}

		public void newUser(User user) {
			boolean nameTaken = checkUsername(user.getName());

			try {
				if (nameTaken == false) {
					bwUser.write(user.getName() + "," + user.getPassword() + "\n");
					bwUser.flush();
				} else {
					JOptionPane.showMessageDialog(null, "Användarnamnet är upptaget!");
				}
			} catch (IOException e) {
			}
		}

		public boolean checkUsername(String username) {
			boolean nameTaken = false;
			try {
				BufferedReader br = new BufferedReader(new FileReader("users/users.txt"));
				String strLine = br.readLine();
				while (strLine != null) {
					if (strLine.startsWith(username)) {
						nameTaken = true;
						break;
					}
					strLine = br.readLine();
				}
			} catch (IOException e) {
			}

			return nameTaken;
		}

		public boolean checkLogin(String input) {
			boolean bool = false;
			try {
				BufferedReader br = new BufferedReader(new FileReader("users/users.txt"));
				String strLine = br.readLine();

				while (strLine != null) {
					if (strLine.startsWith(input)) {
						bool = true;
						String[] str = input.split(",");
						String username = str[0];
						String pw = str[1];
						user = new User(username, pw);
						break;
					}
					strLine = br.readLine();
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}

			return bool;
		}

		public ArrayList userAction(String str) {
			ArrayList<String> foundRecipes = new ArrayList<String>();
			try {
				BufferedReader br = new BufferedReader(new FileReader("recipes/recipes.txt"));
				String strLine = br.readLine();
				while (strLine != null) {
					if (strLine.startsWith("Titel: " + str)) {
						foundRecipes.add(strLine);
					}
					strLine = br.readLine();
				}
			} catch (IOException e) {
			}
			return foundRecipes;
		}
	}

	public static void main(String[] args) {
		Server server = new Server(3250, 5);
		server.start();
	}
}