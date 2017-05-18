package module;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a recipe with all information needed to view it.
 *
 * @author Oscar, Olof
 */
public class Recipe implements Serializable {

	private String title, country, author, upload, instruction, imgFileName;
	private int time, id;
	private ArrayList<String> ingredientList = new ArrayList<String>();
	private byte[] img;

	/**
	 * Set title for recipe.
	 * 
	 * @author Oscar
	 * @param name
	 */
	public void setTitle(String name) {
		this.title = name;
	}

	/**
	 * Get title for recipe.
	 * 
	 * @author Oscar
	 * @return title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Set cookingtime for recipe.
	 * 
	 * @author Oscar
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Get cookingtime for recipe.
	 * 
	 * @author Oscar
	 * @return
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * Get Country where the recipe is from.
	 * 
	 * @author Oscar, Olof
	 * @return country
	 */
	public String getCountry() {
		if (country != null) {
			return country;
		}
		else{
			return "No specified country";
		}
	}

	/**
	 * Set country to where the recipe is from.
	 * 
	 * @author Oscar
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Add ingredient to the recipe.
	 * 
	 * @param ingredient
	 * @author Olof
	 */
	public void addIngredient(String ingredient) {
		ingredientList.add(ingredient);
	}

	/**
	 * Get the ingredients for this recipe.
	 * 
	 * @author Olof
	 * @return String[] ingredients
	 */
	public String[] getIngredients() {
		String[] ingredients = new String[ingredientList.size()];
		ingredientList.toArray(ingredients);
		return ingredients;
	}

	/**
	 * Get ID for this recipe. Is uniqe.
	 * 
	 * @author Oscar
	 * @return id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set unique Id for this recipe.
	 * 
	 * @author Oscar
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get author of this recipe.
	 * 
	 * @author Oscar
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Set author for this recipe.
	 * 
	 * @author Oscar
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Set uploadtime of this recipe.
	 * 
	 * @author Oscar
	 * @return Upploadtime
	 */
	public String getUpload() {
		return upload;
	}

	/**
	 * Set uppload time for this recipe.
	 * 
	 * @author Oscar
	 * @param upploadtime
	 */
	public void setUpload(String uppload) {
		this.upload = uppload;
	}

	/**
	 * Set instruction for this recipe.
	 * 
	 * @param instruction
	 * @author Olof
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	/**
	 * Get instruction for this recipe.
	 * 
	 * @author Olof
	 * @return instruction
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * Set img filename
	 * 
	 * @param filename
	 * @author Olof
	 */
	public void setImgFileName(String filename) {
		this.imgFileName = filename;
	}

	/**
	 * Get image filename.
	 * 
	 * @return
	 * @author Olof
	 */
	public String getImgFileName() {
		return imgFileName;
	}

	/**
	 * Get image for this recipe.
	 * 
	 * @author Oscar
	 * @return recipeImage
	 */
	public byte[] getImg() {
		return img;
	}

	/**
	 * Set image for this recipe.
	 * 
	 * @author Oscar
	 * @param img
	 */
	public void setImg(byte[] img) {
		this.img = img;
	}
}
