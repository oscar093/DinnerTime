﻿package dinnerTime;

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
						ArrayList<String> recipes = userAction(str);
						if (recipes.size() == 0) {
							oos.writeObject("recipe" + "Receptet " + str.substring(6) + " finns inte");
						} else {
							for (int i = 0; i < recipes.size(); i++) {
								oos.writeObject("recipe" + recipes.get(i));
							}
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

			String theRecipe = "Titel: " + title + ", Skapare: " + user.getName() + ", Land: " + country + ", Tid: "
					+ time + " minuter, Ingredienser: " + ingredients;

			try {
				bwRecipe.write(theRecipe + "\n");
				bwRecipe.flush();
			} catch (IOException e) {
			}
		}

		public void newUser(User user) {
			boolean nameTaken = checkUsername(user.getName());
			
			try {
				if(nameTaken == false){
					bwUser.write(user.getName() + ", " + user.getPassword() + "\n");
					bwUser.flush();	
				}
				else{
					JOptionPane.showMessageDialog(null, "Användarnamnet är upptaget!");
				}
			} catch (IOException e) {
			}
		}
		
		public boolean checkUsername(String username){
			boolean nameTaken = false;
			try{
				BufferedReader br = new BufferedReader(new FileReader("users/users.txt"));
				String strLine = br.readLine();
				while(strLine != null){
					if(strLine.startsWith(username)){
						nameTaken = true;
						break;
					}
					strLine = br.readLine();
				}
			}catch(IOException e){}
			
			return nameTaken;
		}

		public ArrayList userAction(String str) {
			ArrayList<String> foundRecipes = new ArrayList<String>();

			if (str.startsWith("search")) { // Stringen för klientens sökningen
											// böjar alltid på "search "
				str = str.substring(6); // substringen börjar efter "search "
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
			}
			return foundRecipes;
		}
	}

	public static void main(String[] args) {
		Server server = new Server(3250, 5);
		server.start();
	}
}