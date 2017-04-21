package dinnerTime;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client extends Thread {
	private String ip;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Runnable onConnected;
	private LoginViewController lvc;
	private RegisterViewController rvc;
	private ClientViewController cvm;
	
	private ArrayList<Recipe> downloadedRecipes = new ArrayList<Recipe>();
	
	public void setOnConnected(Runnable onConnected) {
		this.onConnected = onConnected;
	}

	public Client(String ip, int port, LoginViewController lvc) {
		this.ip = ip;
		this.port = port;
		this.lvc = lvc;
	}
	
	public Client(String ip, int port, RegisterViewController rvc) {
		this.ip = ip;
		this.port = port;
		this.rvc = rvc;
	}
	
	public void setClientViewController(ClientViewController cvm){
		this.cvm= cvm;
	}

	public void run() {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			if(onConnected != null) {
				onConnected.run();
			}
			while (true) {
				try {
					Object obj = ois.readObject();
					if (obj instanceof Login) {
						String loginStatus = ((Login) obj).getLoginStatus();
						lvc.setLoginStatus(loginStatus);
					}
					else if(obj instanceof Register) {
						String registerStatus = ((Register) obj).getRegisterStatus();
						rvc.setRegisterStatus(registerStatus);
					}
					else if(obj instanceof Recipe){
						
					}else if(obj instanceof Recipe[]){
						storeRecipes((Recipe[]) obj);
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToServer(Object obj) {
		try {
			if(obj instanceof Recipe){
				Recipe recipe = (Recipe)obj;
				System.out.println(recipe.getTitle());

			}
			
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Recipe getRecipe(String title){
		for(Recipe r : downloadedRecipes){
			if(r.getTitle().contentEquals(title)){
				return r;
			}
		}
		return null;
	}
	
	public void storeRecipes(Recipe[] recipes){
		cvm.updateNode(recipes);
		for(Recipe r : recipes){
			downloadedRecipes.add(r);
		}
	}
}