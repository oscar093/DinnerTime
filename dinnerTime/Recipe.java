package dinnerTime;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;

public class Recipe implements Serializable {

	private String title, country, author, upload, instruction, imgFileName;
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

	public void addIngredient(String ingredient) {
		ingredientList.add(ingredient);
	}

	public String[] getIngredients() {
		String[] ingredients = new String[ingredientList.size()];
		ingredientList.toArray(ingredients);
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

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getInstruction() {
		return instruction;
	}
	
	public void setImgFileName(String filename){
		this.imgFileName = filename;
	}
	
	public String getImgFileName(){
		return imgFileName;
	}
}
