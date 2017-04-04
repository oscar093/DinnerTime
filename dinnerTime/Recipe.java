package dinnerTime;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;

public class Recipe implements Serializable{
	private String title, author, country, ingredient;
	private int time;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
			if(i < ingredientList.size() - 2){
				ingredients += ", ";
			}
		}
		return ingredients;
	}
}
