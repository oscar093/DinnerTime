package dinnerTime;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;

public class Recipe implements Serializable{

	private String title, country, ingredient, author, upload, instruction;
	private int time, id;

	private ArrayList<String> ingredientList = new ArrayList<String>();

	public Recipe() {
		
	}

	public void setTitle(String name) {
		this.title = name;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return this.time;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setIngredient(String ingredient){
		ingredientList.add(ingredient);
	}
	
	public String getIngredients(){
		String ingredients = "";
		for(int i = 0; i < ingredientList.size(); i++){
			ingredients += ingredientList.get(i);
			if(i < ingredientList.size() - 1){
				ingredients += ", ";
			}
		}
		return ingredients;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String uppload) {
		this.upload = uppload;
	}
	
	public void setInstruction(String instruction){
		this.instruction = instruction;
	}
	
	public String getInstruction(){
		return instruction;
	}
}