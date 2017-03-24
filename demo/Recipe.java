package demo;
import java.util.ArrayList;

import javax.swing.*;



public class Recipe {
	//Ingredienser, namn och tillagningstid
	ArrayList<String> ing = new ArrayList<String>();
	private String name;
	private String author;
	private int tillag;
	private String country;
	
	public Recipe(){
	name = null;
	tillag = 0;

		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setTillag(int tillag){
		this.tillag = tillag;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getTillag(){
		return this.tillag;
	}
	
	public void addIngredient(String ingredient){
		ing.add(ingredient);
	}
	
	public String ingredienser(){
		String ingredienser = null;
		for (int i = 0; i < ing.size(); i++){
			ingredienser += "\n";
			ingredienser += ing.get(i);
		}
		return ingredienser;
	}
	public String toString(){
		return "Namn: " + getName() + "\n\n" + "Tillagningstid: " + getTillag() + " minuter\nIngredienser:" 
				+ ingredienser()+"\nLand: " + getCountry() + "\n\n";
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

}
