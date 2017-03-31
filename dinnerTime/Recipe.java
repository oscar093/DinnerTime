package dinnerTime;

import javax.swing.*;

public class Recipe {
	private String title, author, country;
	private int time;

	public Recipe() {
		
	}

	public void setName(String name) {
		this.title = name;
	}
	
	public String getName() {
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
}
