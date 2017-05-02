package dinnerTime;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;

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
//	private HashMap<Image,Integer> ImageMap = new HashMap<Image,Integer>();
	private String username;

	
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
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}

	/**
	 * Sends object to server. Make Sure Object is instance of known type.
	 * @param obj
	 */
	public void sendToServer(Object obj) {
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Find recipe by title.
	 * @param title
	 * @return recipe
	 */
	public Recipe getRecipe(String title){
		
		for(Recipe r : downloadedRecipes){
			if(r.getTitle().toLowerCase().contentEquals(title.toLowerCase())){
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Find recipe by id.
	 * @param title
	 * @return recipe
	 */
	public Recipe getRecipe(int id){
		
		for(Recipe r : downloadedRecipes){
			if(r.getId() == id){
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Stores all downloaded recipes.
	 * @param array of recipes
	 */
	public void storeRecipes(Recipe[] recipes){
		cvm.updateCountryNode(recipes);
		for(Recipe r : recipes){
			downloadedRecipes.add(r);
		}
	}
	
	/**
	 * Gets Image for recipe.
	 * @param id
	 * @return Image
	 */
	public Image getRecipeImage(int id){
		sendToServer("getRecipeImage " +  16);
		try {
			Image image = (Image)ois.readObject();
			return image;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}