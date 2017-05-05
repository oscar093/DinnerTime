package dinnerTime;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class DatabaseController {

	private Connection c = null;

	public DatabaseController() {
		try {
			Class.forName("org.postgresql.Driver");

			// Om man kör servern remote.
			// c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dinnertime", "postgres", "P@ssw0rd");

			// Om man kör servern lokalt.
			c = DriverManager.getConnection("jdbc:postgresql://146.148.4.203:5432/dinnertime", "postgres", "P@ssw0rd");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public void newRecipe(Recipe recipe) {
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

			Statement idStmt = c.createStatement();
			ResultSet rs = idStmt.executeQuery(
					"SELECT (SELECT COUNT(*) FROM recipe) AS recipeCount,(SELECT COUNT(*) FROM ingredient) AS ingredientCount;");
			rs.next();
			int recipeId = rs.getInt("recipeCount");
			int ingredientId = rs.getInt("ingredientCount");
			recipeId++;
			ingredientId++;
			rs.close();

			String sql = "INSERT INTO recipe (recipeid,title,author,time,upload,country,instruction) " + "VALUES ('"
					+ recipeId + "','" + recipe.getTitle() + "','" + recipe.getAuthor() + "','" + recipe.getTime()
					+ "','" + timeStamp + "','" + recipe.getCountry().toLowerCase() + "','" + recipe.getInstruction()
					+ "');";

			String[] ingredientArray = recipe.getIngredients();
			String ingredientList = "";

			for (int i = 0; i < ingredientArray.length; i++) {
				sql += "\nINSERT INTO ingredient(ingredientid,recipeid,name) VALUES (" + ingredientId + "," + recipeId
						+ ",'" + ingredientArray[i] + "');";
				ingredientId++;
			}

			String recipeImg = recipe.getImgFileName();
			if (recipeImg != null) {
				addImage(recipeId, recipeImg);
			}

			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (SQLException e) {
		}
	}

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
	 * Denna kommer förmodligen att tas bort.
	 * @param recipeId
	 * @return
	 */
	public ImageIcon getImageIcon(int recipeId) {
		ImageIcon img = null;
		try {
			PreparedStatement ps = c.prepareStatement("SELECT img FROM image WHERE recipeid = ?");
			ps.setInt(1, recipeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				byte[] imgBytes = rs.getBytes(1);
				img = new ImageIcon(imgBytes);
			}
			rs.close();
			ps.close();
			return img;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return img;
	}
	
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
				if(getImage(recipe.getId()) != null ){
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

	public String[] getTitleSearch(String search) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.title like '"
					+ search + "%';");
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

		responseArray = new String[response.size()];
		response.toArray(responseArray);

		return responseArray;
	}

	public String[] getCountrySearch(String search) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.country = '"
					+ search + "';");
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

		responseArray = new String[response.size()];
		response.toArray(responseArray);
		return responseArray;
	}

	public String[] getAuthorSearch(String search) {
		ArrayList<String> response = new ArrayList<String>();
		String[] responseArray;
		try {
			Statement stmt1 = c.createStatement();
			Statement stmt2 = c.createStatement();
			String sqlRecipe = ("select distinct recipe.title, recipe.country, recipe.time, recipe.author, recipe.instruction, recipe.upload "
					+ "from recipe join ingredient on recipe.recipeid = ingredient.recipeid and recipe.author = '"
					+ search + "';");
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

		responseArray = new String[response.size()];
		response.toArray(responseArray);
		return responseArray;
	}

	/*
	 * Använd denna Main-metoden om du vill testa en metod direkt mot databasen.
	 * Men ändra addressen till databasen först för att det ska fungera.
	 */
	public static void main(String[] args) {
		DatabaseController d = new DatabaseController();	
		if(d.getImage(12) == null){
			System.out.println("Its null");
		}

		ByteArrayInputStream in = new ByteArrayInputStream(d.getImage(12));
		Image img = new Image(in);
		System.out.println(img.getHeight());
	}	
}

