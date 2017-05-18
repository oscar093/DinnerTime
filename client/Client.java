package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import module.Login;
import module.Recipe;
import module.Register;
import viewControllers.ClientViewController;
import viewControllers.LoginViewController;
import viewControllers.MyKitchenController;
import viewControllers.RegisterViewController;
import viewControllers.SearchViewController;

/** 
 * Client handles all communication between gui and the server. 
 *
 * @author Oscar, David, Jonathan, Olof
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
	private MyKitchenController mkc;
	private String firstName;
	private String surName;
	private String region;
	private String country;
	private String[] recipes;
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
	
	/**
	 * Set MyKitchenController
	 * 
	 * @param mkc
	 */
	public void setMyKitchenController(MyKitchenController mkc){
		this.mkc = mkc;
	}

	/**
	 * Starts the client's thread
	 */
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
						String[] response = (String[]) obj;
						if(response[response.length -1].equals("searchview")){
							svc.showResponse(response);
						}
						if(response[response.length - 1].equals("mykitchen")){
							mkc.showRecipe(response);
						}
						
					}
					else if (obj instanceof ArrayList<?>){
						String[] recipes = new String[0]; 
						recipes = (String[]) ((ArrayList<String>) obj).toArray(recipes);
						setRecipes(recipes);
					}
					else if (obj instanceof String && ((String) obj).startsWith("firstName")){
						String firstName = (String) obj;
						setFirstName(firstName.substring(9));
					}
					else if (obj instanceof String && ((String) obj).startsWith("surname")){
						String surName = (String) obj;
						setSurName(surName.substring(7));
					}
					else if(obj instanceof String && ((String) obj).startsWith("region")){
						String region = (String) obj;
						setRegion(region.substring(6));
					}
					else if(obj instanceof String && ((String) obj).startsWith("country")){
						String country = (String) obj;
						setCountry(country.substring(7));
					}
					else if(obj instanceof Integer){
						int count = (int)obj;
						cvm.setRecipeCount(count);
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
	 * Sets the username for this client.
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
	 * Sets the first name for this client
	 * @author Jonathan
	 * @param firstName
	 */
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	
	/**
	 * Gets the first name of this client
	 * @author Jonathan
	 * @return first name
	 */
	public String getFirstName(){
		return this.firstName;
	}
	
	/**
	 * Sets the surname for this client
	 * @author Jonathan
	 * @param surName
	 */
	public void setSurName(String surName){
		this.surName = surName;
	}
	
	/**
	 * Gets the sur name from this client
	 * @author Jonathan
	 * @return the sur name
	 */
	public String getSurName(){
		return this.surName;
	}
	
	/**
	 * Sets the region for this client
	 * @author Jonathan
	 * @param region
	 */
	public void setRegion(String region){
		this.region = region;
	}
	
	/**
	 * Gets the region for this client
	 * @author Jonathan
	 * @return the region
	 */
	public String getRegion(){
		return this.region;
	}
	
	/**
	 * Sets which country the client is from
	 * @author Jonathan
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets which country the client is from
	 * @author Jonathan
	 * @return the country
	 */
	public String getCountry(){
		return this.country;
	}
	
	/**
	 * Sets all recipes created by this client
	 * @author Jonathan
	 * @param recipes
	 */
	public void setRecipes(String[] recipes){
		this.recipes = recipes;
	}
	
	/**
	 * Gets all recipes this client has created
	 * @author Jonathan
	 * @return the recipes
	 */
	public String[] getRecipes(){
		return this.recipes;
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