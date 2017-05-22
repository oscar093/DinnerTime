package server;

import module.Recipe;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Handles communication between server and database.
 * 
 * @author Olof, Oscar, David, Jonathan
 */

public class DatabaseController {

	private Connection c = null;
	
	

	/**
	 * Contructor for databasecontroller. Gets driver for Postgres and connects
	 * to database.
	 * 
	 * @author Oscar, David
	 */
	public DatabaseController() {
		try {
			Class.forName("org.postgresql.Driver");

			// Om man kör servern remote.
			// c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dinnertime","postgres", "P@ssw0rd");

			// Om man kör servern lokalt.
			c = DriverManager.getConnection("jdbc:postgresql://146.148.4.203:5432/dinnertime", "postgres", "P@ssw0rd");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Controlles if combination of password and username exists in database.
	 * 
	 * @author David
	 * @param username
	 * @param password
	 * @return 'success' if succesfull 'failed' if not.
	 */
	public String login(String username, String password) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM member;");
			while (rs.next()) {
				 if ((username.equals(rs.getString(1))) && (password.equals(rs.getString(2)))) {
					return "success";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "failed";
	}

	/**
	 * Saves a new user to the database.
	 * 
	 * @author David
	 * @param username
	 * @param password
	 * @param firstname
	 * @param surname
	 * @param region
	 * @param country
	 * @return 'success' if succesfull 'failed' if not
	 */
	public String register(String username, String password, String firstname, String surname, String region,
			String country) {

		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();

			String sql = "INSERT INTO MEMBER (username,password,firstname,surname,region,country) " + "VALUES ('"
					+ username + "','" + password + "','" + firstname + "','" + surname + "','" + region + "','"
					+ country + "');";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "failed";
	}

	/**
	 * Method for getting the first name of a specific user
	 * 
	 * @author Jonathan
	 * @param username
	 *            name of the user
	 * @return the first name
	 */
	public String firstName(String username) {
		String firstName = "";
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String sql = "SELECT firstname FROM member WHERE username = '" + username + "' ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				firstName = rs.getString("firstname");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "firstName" + firstName;

	}

	/**
	 * Method for getting the surname of a specific user
	 * 
	 * @author Jonathan
	 * @param username
	 *            name of the user
	 * @return the surname
	 */
	public String surName(String username) {
		String surName = "";
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String sql = "SELECT surname FROM member where username = '" + username + "' ;";
			ResultSet rs = stmt.executeQuery(sql);
			// rs.next();
			while (rs.next()) {
				surName = rs.getString("surname");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "surname" + surName;
	}

	/**
	 * Method for getting which region a specific user is from
	 * 
	 * @author Jonathan
	 * @param username
	 *            name of the user
	 * @return the region
	 */
	public String region(String username) {
		String region = "";
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String sql = "SELECT region FROM member where username = '" + username + "' ;";
			ResultSet rs = stmt.executeQuery(sql);
			// rs.next();
			while (rs.next()) {
				region = rs.getString("region");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "region" + region;
	}

	/**
	 * Method for getting which country a specific user is from
	 * 
	 * @author Jonathan
	 * @param username
	 *            name of the user
	 * @return the country
	 */
	public String country(String username) {
		String country = "";
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String sql = "SELECT country FROM member WHERE username = '" + username + "' ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				country = rs.getString("country");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "country" + country;
	}

	/**
	 * The new recipe is added to the database.
	 * 
	 * @author Olof
	 * @param recipe
	 */
	public void newRecipe(Recipe recipe) {
		try {
			int ingredientID = getNewIngredientID();
			int recipeID = getNewRecipeID();
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
			String sql = "INSERT INTO recipe (recipeid,title,author,time,upload,country,instruction) " + "VALUES ('"
					+ recipeID + "','" + recipe.getTitle() + "','" + recipe.getAuthor() + "','" + recipe.getTime()
					+ "','" + timeStamp + "','" + recipe.getCountry().toLowerCase() + "','" + recipe.getInstruction()
					+ "');";

			String[] ingredientArray = recipe.getIngredients();			
			/**
			 * goes through every ingredient and stores them in another table
			 * every ingredient gets its own ID
			 */
			for (int i = 0; i < ingredientArray.length; i++) {
				
				sql += "\nINSERT INTO ingredient(ingredientid,recipeid,name) VALUES (" + ingredientID + "," + recipeID
						+ ",'" + ingredientArray[i] + "');";
				ingredientID++;
			}

			String recipeImg = recipe.getImgFileName();
			if (recipeImg != null) {
				addImage(recipeID, recipeImg);
			}

			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Gives a unique id for a recipe.
	 * @author Oscar
	 * @return unique recipe id
	 */
	public int getNewRecipeID() {
		int recipeID = -1;
		ResultSet rs;
		try {
			Statement idStmt = c.createStatement();

			rs = idStmt.executeQuery("SELECT recipeid from recipe;");
			int tmpRecipeID = -1;
			while (rs.next()) {
				
				tmpRecipeID = rs.getInt("recipeid");
				if(tmpRecipeID > recipeID){
					recipeID = tmpRecipeID;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipeID + 1;
	}
	
	/**
	 * Gives a unique id for a ingredient.
	 * @author Oscar
	 * @return unique ingredient id
	 */
	public int getNewIngredientID() {
		int ingredientID = -1;
		ResultSet rs;
		try {
			Statement idStmt = c.createStatement();

			rs = idStmt.executeQuery("SELECT ingredientid from ingredient;");
			int tmpIngredientID = -1;
			while (rs.next()) {
				
				tmpIngredientID = rs.getInt("ingredientid");
				if(tmpIngredientID > ingredientID){
					ingredientID = tmpIngredientID;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredientID + 1;
	}

	/**
	 * Adds the image to the database
	 * 
	 * @author Olof
	 * @param recipeId:
	 *            the ID of the recipe the image belongs to.
	 * @param filename:
	 *            file path to the image
	 */
	public void addImage(int recipeId, String filename) {
		try {
			File file = new File(filename);

			FileInputStream fis = new FileInputStream(file);
			PreparedStatement ps = c.prepareStatement("INSERT INTO image VALUES (?, ?, ?)");
			ps.setInt(1, recipeId);
			ps.setString(2, file.getName());
			ps.setBinaryStream(3, fis, (int) file.length());
			ps.executeUpdate();
			ps.close();
			fis.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the image the image is stored as byte.
	 * 
	 * @author Oscar, Olof
	 * @param recipeId
	 *            : the ID of the recipe the image belongs to.
	 * @return byte[]
	 */
	public byte[] getImage(int recipeId) {
		byte[] img = null;
		try {
			PreparedStatement ps = c.prepareStatement("SELECT img FROM image WHERE recipeid = ?");
			ps.setInt(1, recipeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				img = rs.getBytes(1);
			}
			rs.close();
			ps.close();
			return img;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * Return all recipes from a specific country. This method includes pictures
	 * with the recipes.
	 * 
	 * @author Oscar
	 * @param country
	 * @return Recipe[]
	 */
	public Recipe[] getRecipeByCountry(String country) {
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql = "select * from recipe where country='" + country + "';";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Recipe recipe = new Recipe();
				recipe.setId(rs.getInt("recipeid"));
				recipe.setTitle(rs.getString("title"));
				recipe.setAuthor(rs.getString("author"));
				recipe.setTime(rs.getInt("time"));
				recipe.setUpload(rs.getString("upload"));
				recipe.setCountry(rs.getString("country"));
				recipe.setInstruction(rs.getString("instruction"));
				result.add(recipe);

				Statement stmtIngr = c.createStatement();
				String sqlIngr = "select name from ingredient where recipeID='" + recipe.getId() + "';";
				ResultSet rsIngr = stmtIngr.executeQuery(sqlIngr);
				while (rsIngr.next()) {
					recipe.addIngredient(rsIngr.getString("name"));
				}
				if (getImage(recipe.getId()) != null) {
					recipe.setImg(getImage(recipe.getId()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Recipe[] rArray = new Recipe[result.size()];
		result.toArray(rArray);
		return rArray;
	}

	/**
	 * Gets all the recipes that matches the title-search.
	 * 
	 * @author Olof
	 * @param title
	 *            : the users search
	 * @return returns an array with all the recipes
	 */
	public String[] getTitleSearch(String title) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.title like '%"
					+ title + "%';");
			ResultSet rs = stmt1.executeQuery(sqlRecipe);

			while (rs.next()) {
				response.add("title_" + rs.getString("title")); // the string
																// and values
																// are split
																// with "_"
				response.add("country_" + rs.getString("country"));
				response.add("time_" + rs.getString("time"));
				response.add("author_" + rs.getString("author"));
				response.add("instruction_" + rs.getString("instruction"));

				String sqlIngredient = "select ingredient.name from ingredient join recipe on ingredient.recipeid = recipe.recipeid and recipe.upload = '"
						+ rs.getString("upload") + "';";
				ResultSet rsIngredient = stmt2.executeQuery(sqlIngredient);
				ArrayList<String> ingredients = new ArrayList<String>();
				while (rsIngredient.next()) {
					ingredients.add(rsIngredient.getString("name"));
				}
				String ingredientList = "";
				for (int i = 0; i < ingredients.size(); i++) {
					ingredientList += ingredients.get(i) + "\n";
				}
				response.add("ingredient_" + ingredientList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		responseArray = new String[response.size() + 1];
		response.toArray(responseArray);
		responseArray[responseArray.length - 1] = "searchview";
		return responseArray;
	}

	/**
	 * Gets all the recipes that matches the country-search.
	 * 
	 * @author Olof
	 * @param title
	 *            : the users search
	 * @return returns an array with all the recipes
	 */
	public String[] getCountrySearch(String country) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.country = '"
					+ country + "';");
			ResultSet rs = stmt1.executeQuery(sqlRecipe);

			while (rs.next()) {
				response.add("title_" + rs.getString("title")); // the string
																// and values
																// are split
																// with "_"
				response.add("country_" + rs.getString("country"));
				response.add("time_" + rs.getString("time"));
				response.add("author_" + rs.getString("author"));
				response.add("instruction_" + rs.getString("instruction"));

				String sqlIngredient = "select ingredient.name from ingredient join recipe on ingredient.recipeid = recipe.recipeid and recipe.upload = '"
						+ rs.getString("upload") + "';";
				ResultSet rsIngredient = stmt2.executeQuery(sqlIngredient);
				ArrayList<String> ingredients = new ArrayList<String>();
				while (rsIngredient.next()) {
					ingredients.add(rsIngredient.getString("name"));
				}
				String ingredientList = "";
				for (int i = 0; i < ingredients.size(); i++) {
					ingredientList += ingredients.get(i) + "\n";
				}
				response.add("ingredient_" + ingredientList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		responseArray = new String[response.size() + 1];
		response.toArray(responseArray);
		responseArray[responseArray.length - 1] = "searchview";
		return responseArray;
	}

	/**
	 * Gets all the recipes that matches the author-search.
	 * 
	 * @author Olof
	 * @param title
	 *            : the users search
	 * @return returns an array with all the recipes
	 */
	public String[] getAuthorSearch(String author) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.author = '"
					+ author + "';");
			ResultSet rs = stmt1.executeQuery(sqlRecipe);

			while (rs.next()) {
				response.add("title_" + rs.getString("title")); // the string
																// and value are
																// split with
																// "_"
				response.add("country_" + rs.getString("country"));
				response.add("time_" + rs.getString("time"));
				response.add("author_" + rs.getString("author"));
				response.add("instruction_" + rs.getString("instruction"));

				String sqlIngredient = "select ingredient.name from ingredient join recipe on ingredient.recipeid = recipe.recipeid and recipe.upload = '"
						+ rs.getString("upload") + "';";
				ResultSet rsIngredient = stmt2.executeQuery(sqlIngredient);
				ArrayList<String> ingredients = new ArrayList<String>();
				while (rsIngredient.next()) {
					ingredients.add(rsIngredient.getString("name"));
				}
				String ingredientList = "";
				for (int i = 0; i < ingredients.size(); i++) {
					ingredientList += ingredients.get(i) + "\n";
				}
				response.add("ingredient_" + ingredientList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		responseArray = new String[response.size() + 1];
		response.toArray(responseArray);
		responseArray[responseArray.length - 1] = "searchview";
		return responseArray;	
	}

	/**
	 * Method for getting information about a specific recipe written by an
	 * specific author
	 * 
	 * @author Jonathan
	 * @param author
	 *            the author's name
	 * @param title
	 *            the title of the recipe
	 * @return an array containing the recipe's information
	 */
	public String[] getRecipeByAuthor(String author, String title) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.author = '"
					+ author + "' and recipe.title = '" + title + "' ;");
			ResultSet rs = stmt1.executeQuery(sqlRecipe);

			while (rs.next()) {
				response.add("title_" + rs.getString("title"));
				response.add("country_" + rs.getString("country"));
				response.add("time_" + rs.getString("time"));
				response.add("author_" + rs.getString("author"));
				response.add("instruction_" + rs.getString("instruction"));

				String sqlIngredient = "select ingredient.name from ingredient join recipe on ingredient.recipeid = recipe.recipeid and recipe.upload = '"
						+ rs.getString("upload") + "';";
				ResultSet rsIngredient = stmt2.executeQuery(sqlIngredient);
				ArrayList<String> ingredients = new ArrayList<String>();
				while (rsIngredient.next()) {
					ingredients.add(rsIngredient.getString("name"));
				}
				String ingredientList = "";
				for (int i = 0; i < ingredients.size(); i++) {
					ingredientList += ingredients.get(i) + "\n";
				}
				response.add("ingredient_" + ingredientList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		responseArray = new String[response.size() + 1];
		response.toArray(responseArray);
		responseArray[responseArray.length - 1] = "mykitchen";
		return responseArray;
	}

	/**
	 * Method for getting the names of all recipes created by a specific author
	 * 
	 * @author Jonathan
	 * @param author
	 *            the author's name
	 * @return an ArrayList containing all the recipes' names
	 */
	public ArrayList<String> getAuthorRecipes(String author) {
		ArrayList<String> recipes = new ArrayList<String>();
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT title FROM recipe where author = '" + author + "' ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				recipes.add(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recipes;
	}

	/**
	 * Count the recipes made for each country
	 * 
	 * @author Olof
	 */
	public int getRecipeCount(String country) {
		int response = 0;
		try {
			PreparedStatement ps = c.prepareStatement("select count(*) from recipe where country = ?;");
			ps.setString(1, "" + country.toLowerCase() + "");
			ResultSet rs = ps.executeQuery();
			rs.next();
			response = rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static void main(String[] args){
		DatabaseController db = new DatabaseController();
		System.out.println("Ingredient: " + db.getNewIngredientID());
		System.out.println("Recipeid: " + db.getNewRecipeID());
	}
}