package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;
import module.Login;
import module.Recipe;
import module.Register;
import viewControllers.ClientViewController;
import viewControllers.LoginViewController;
import viewControllers.RegisterViewController;
import viewControllers.SearchViewController;

/** 
 * Client handles all communication between gui and the server. 
 *
 * @author Oscar, David
 */
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
	private SearchViewController svc;
	
	private ArrayList<Recipe> downloadedRecipes = new ArrayList<Recipe>();
	private String username;

	
	public void setOnConnected(Runnable onConnected) {
		this.onConnected = onConnected;
	}

	/**
	 * Constructor for client. Adds LoginViewController.
	 * 
	 * @author David
	 * @param ip
	 * @param port
	 * @param LoginViewController
	 */
	public Client(String ip, int port, LoginViewController lvc) {
		this.ip = ip;
		this.port = port;
		this.lvc = lvc;
	}
	
	/**
	 * Constructor for client. Adds RegisterViewController.
	 * 
	 * @author David
	 * @param ip
	 * @param port
	 * @param rvc
	 */
	public Client(String ip, int port, RegisterViewController rvc) {
		this.ip = ip;
		this.port = port;
		this.rvc = rvc;
	}
	
	/**
	 * Set ClientViewController.
	 * 
	 * @author David
	 * @param ClientViewController
	 */
	public void setClientViewController(ClientViewController cvm){
		this.cvm= cvm;
	}
	
	/**
	 * Set SearchViewController.
	 * 
	 * @author David
	 * @param svc
	 */
	public void setSearchViewController(SearchViewController svc){
		this.svc = svc;
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
						
					}
					else if(obj instanceof Recipe[]){
						storeRecipes((Recipe[]) obj);
					}
					else if(obj instanceof String[]){
						String[] response = (String[])obj;
						svc.showResponse(response);
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set username for this client.
	 * 
	 * @author David
	 * @param username
	 */
	public void setUsername(String username){
		this.username = username;
	}
	
	/**
	 * Get username for this client.
	 * 
	 * @author David
	 * @return username
	 */
	public String getUsername(){
		return username;
	}

	/**
	 *
	 * 
	 * Sends object to server. Make Sure Object is instance of known type.
	 * @author Oscar
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
	 * 
	 * 
	 * Find recipe by id.
	 * 
	 * @author Oscar
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
	 * 
	 * 
	 * Stores all downloaded recipes in client.
	 * 
	 * @author Oscar
	 * @param array of recipes
	 */
	public void storeRecipes(Recipe[] recipes){
		cvm.updateCountryNode(recipes);
		for(Recipe r : recipes){
			downloadedRecipes.add(r);
		}
	}
}