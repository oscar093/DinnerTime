package dinnerTime;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;

/**
 * A class that contains all information for the recipes
 * @author Group 26
 *
 */
public class Recipe implements Serializable {

	private String title, country, author, upload, instruction, imgFileName;
	private int time, id;
	private ArrayList<String> ingredientList = new ArrayList<String>();

	public Recipe() {

	}
	
	/**
	 * Method for setting the title of a recipe
	 * @param name the name to be set as title
	 */
	public void setTitle(String name) {
		this.title = name;
	}
	
	/**
	 * Method for getting the title of a recipe
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Method for setting the time it takes to make the dish of the recipe
	 * @param time how long it takes to make the dish
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Method for getting the time of a recipe
	 * @return time, how long it takes to make the dish
	 */
	public int getTime() {
		return this.time;
	}
	
	/**
	 * Method for getting which country the recipe comes from
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Method for setting which country the recipe comes from
	 * @param country the country to be set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Method for adding ingredients to a recipe
	 * @param ingredient the ingredient to be added
	 */
	public void addIngredient(String ingredient) {
		ingredientList.add(ingredient);
	}
	
	/**
	 * Method for getting all ingredients of a recipe
	 * @return an array containing all the ingredients
	 */
	public String[] getIngredients() {
		String[] ingredients = new String[ingredientList.size()];
		ingredientList.toArray(ingredients);
		return ingredients;
	}

	/**
	 * Method for getting the ID of a recipe
	 * @return the ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Method for setting the ID of a recipe
	 * @param id the ID to be set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Method for getting which author wrote the recipe
	 * @return the author of the recipe
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Method for setting a author to a recipe
	 * @param author name of the author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUpload() {
		return upload;
	}

	public void setUpload(String uppload) {
		this.upload = uppload;
	}
	
	/**
	 * Method for setting the instruction for a recipe
	 * @param instruction the instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	/**
	 * Method for getting the instruction of a recipe
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}
	
	/**
	 * Method for setting the filename of a image for a recipe
	 * @param filename the filename of the image
	 */
	public void setImgFileName(String filename){
		this.imgFileName = filename;
	}
	
	/**
	 * Method for getting the filename for the image of a recipe
	 * @return the filename of the image
	 */
	public String getImgFileName(){
		return imgFileName;
	}
}