package dinnerTime;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.net.*;

public class Server implements Runnable {
	private int port;
	private Thread server = new Thread(this);
	private ArrayList<ClientHandler> threads = new ArrayList<ClientHandler>();
	private ArrayList<String> onlineUsers = new ArrayList<String>();
	private HashMap<String, ClientHandler> usersAndThreads = new HashMap<String, ClientHandler>();
	private Register reg;

	public Server(int port) {
		this.port = port;
		server.start();
	}
	
	public void run() {
		Socket socket = null;
		try(ServerSocket ss = new ServerSocket(port)) {
			while(true) {
				try {
					socket = ss.accept();
					ClientHandler ch = new ClientHandler(socket);
					threads.add(ch);
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
	
	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		public ClientHandler(Socket socket) throws IOException {
			this.socket = socket;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			start();
		}
		
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				while(true) {
					try {
						Object obj = ois.readObject();
						if(obj instanceof Login) {
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
		public void newRecipe(Recipe recipe){
			DatabaseController dbc = new DatabaseController();
			dbc.newRecipe(recipe, reg);
		}
		
		public void logout(String username) {
			ClientHandler ch = usersAndThreads.get(username);
			usersAndThreads.remove(username);
			onlineUsers.remove(username);
			threads.remove(ch);
			System.out.println(usersAndThreads.toString());
			System.out.println(onlineUsers.toString());
			System.out.println(threads.toString());
		}
	}

	public static void main(String[] args) {
		Server server = new Server(3250);
	}
}