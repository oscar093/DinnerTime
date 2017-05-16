package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;
import module.Login;
import module.Logout;
import module.Recipe;
import module.Register;

import java.io.*;
import java.net.*;

/**
 * Serves clients with information.
 * 
 * @author Oscar, David 
 *
 */
public class Server implements Runnable {
	private int port;
	private Thread server = new Thread(this);
	private ArrayList<ClientHandler> threads = new ArrayList<ClientHandler>();
	private ArrayList<String> onlineUsers = new ArrayList<String>();
	private HashMap<String, ClientHandler> usersAndThreads = new HashMap<String, ClientHandler>();
	private Register reg;
	private DatabaseController dbc = new DatabaseController();

	/** 
	 * Contructor for server, takes port On which the server is to run.
	 * 
	 * @author David
	 * @param port
	 */
	public Server(int port) {
		this.port = port;
		server.start();
	}
	
	public void run() {
		Socket socket = null;
		try(ServerSocket ss = new ServerSocket(port)) {
			System.out.println("Server started.\nLocal IP: " 	+ InetAddress.getLocalHost().getHostAddress()
														+ "\nHost-name: " + InetAddress.getLocalHost().getHostName()
														+ "\nPort: "+  ss.getLocalPort() + "\n");
			while(true) {
				try {
					socket = ss.accept();
					ClientHandler ch = new ClientHandler(socket);
					threads.add(ch);
					System.out.println("Client connected.\nIP: " + ss.getInetAddress() + "\n");
				} catch(IOException e) {
					e.printStackTrace();
					if(socket != null) {
						socket.close();
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Handles every clients threads and communication between them.
	 * 
	 * @author Oscar
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		/** 
		 * Constructor for clienthandler, takes socket from connections.
		 * 
		 * @author Oscar, David
		 * @param socket
		 * @throws IOException
		 */
		public ClientHandler(Socket socket) throws IOException {
			this.socket = socket;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			start();
		}
		
		public void run() {
			try {
				while(true) {
					try {
						Object obj = ois.readObject();
						if(obj instanceof String){
							stringHandler((String) obj);
						}else if(obj instanceof Login) {
							String username = ((Login) obj).getUsername();
							String password = ((Login) obj).getPassword();
							login(username, password);
						}
						else if(obj instanceof Register) {
							reg = (Register)obj;
							register(reg.getUsername(), reg.getPassword(), reg.getFirstname(), reg.getSurname(),
									reg.getRegion(), reg.getCountry());
						}
						else if(obj instanceof Recipe){
							Recipe recipe = (Recipe)obj;
							newRecipe(recipe);
						}
						else if(obj instanceof Logout) {
							String username = ((Logout) obj).getUsername();
							logout(username);
						}
					} catch(ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			} catch(IOException e) {
				try {
					socket.close();
				} catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		/** 
		 * Controls if username and password is correct. 
		 * 
		 * @author Oscar, David
		 * @param username
		 * @param password
		 */
		public void login(String username, String password) {
			DatabaseController dbc = new DatabaseController();
			String loginStatus;
			loginStatus = dbc.login(username, password);
			onlineUsers.add(username);
			usersAndThreads.put(username, threads.get(threads.size() - 1));
			ClientHandler ch = usersAndThreads.get(username);
			try {
				ch.oos.writeObject(new Login(loginStatus));
				ch.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(loginStatus.equals("failed")) {
				usersAndThreads.remove(username);
				onlineUsers.remove(username);
				threads.remove(ch);
			}
		}
		
		/** 
		 * Registers a person as a member of DinnerTime.
		 * 
		 * @author David
		 * @param username
		 * @param password
		 * @param firstname
		 * @param surname
		 * @param region
		 * @param country
		 */
		public void register(String username, String password, String firstname, String surname, String region, String country) {
			DatabaseController dbc = new DatabaseController();
			String registerStatus;
			registerStatus = dbc.register(username, password, firstname, surname, region, country);
			onlineUsers.add(username);
			usersAndThreads.put(username, threads.get(threads.size() - 1));
			ClientHandler ch = usersAndThreads.get(username);
			try {
				ch.oos.writeObject(new Register(registerStatus));
				ch.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(registerStatus.equals("failed")) {
				usersAndThreads.remove(username);
				onlineUsers.remove(username);
				threads.remove(ch);
			}
		}
		
		/**
		 * Saves a new recipe in to database.
		 * 
		 * @param recipe
		 */
		public void newRecipe(Recipe recipe){
			DatabaseController dbc = new DatabaseController();
			dbc.newRecipe(recipe);
		}
		
		/**
		 * Log out a user.
		 * 
		 * @param username
		 */
		public void logout(String username) {
			ClientHandler ch = usersAndThreads.get(username);
			usersAndThreads.remove(username);
			onlineUsers.remove(username);
			threads.remove(ch);
			System.out.println(usersAndThreads.toString());
			System.out.println(onlineUsers.toString());
			System.out.println(threads.toString());
		}
		
		/** 
		 * Translates and delegates all incoming Strings from client.
		 * 
		 * @author Oscar
		 * @param command - What the client wants the server to do. 
		 */
		public void stringHandler(String command){
			if(command.contains("getrecipebycountry")){
				String[] strArray = command.split(" ");
				String country = strArray[1];
				recipeByCountryRequest(country.toLowerCase());
			}else if(command.contains("getRecipeImage")){
				String[] strArray = command.split(" ");
				String id = strArray[1];
			}
			if(command.startsWith("titleSearch")){
				String[] response = dbc.getTitleSearch(command.substring(11));
				try {
					oos.writeObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(command.startsWith("countrySearch")){
				String[] response = dbc.getCountrySearch(command.substring(13));
				try {
					oos.writeObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(command.startsWith("authorSearch")){
				String[] response = dbc.getAuthorSearch(command.substring(12));
				try {
					oos.writeObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(command.startsWith("getRecipeCount")){
				String[] s =command.split(" ");
				int response = dbc.getRecipeCount(s[1]);
				try {
					oos.writeObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Sends a array of recipe from specified country to client.
		 * 
		 * @author Oscar
		 * @param country - country of wish the recipes should be from.
		 */
		public void recipeByCountryRequest(String country){
			Recipe[] r = dbc.getRecipeByCountry(country);
			try {
				oos.writeObject(r);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Start method for server.
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server(3250);
		
	}
}