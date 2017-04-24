package dinnerTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseController {
	
	private Connection c = null;
	
	public DatabaseController() {
		try {
			Class.forName("org.postgresql.Driver");
			
			//Om man inte kör servern remote.
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dinnertime", "postgres", "P@ssw0rd");
			
			//Om man kör servern lokalt.
//			c = DriverManager.getConnection("jdbc:postgresql://146.148.4.203:5432/dinnertime", "postgres", "P@ssw0rd");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String login(String username, String password) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM member;");
			while(rs.next()) {
				if((username.equals(rs.getString(1))) && (password.equals(rs.getString(2)))) {
					return "success";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "failed";
	}
	
	public String register(String username, String password, String firstname, String surname, String region, String country) {
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();

			String sql = "INSERT INTO MEMBER (username,password,firstname,surname,region,country) "
					+ "VALUES ('" + username + "','" + password + "','" + firstname + "','" + surname + "','" + region + "','" + country + "');";
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
	
	public String newRecipe(Recipe recipe){
		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
			Statement idStmt = c.createStatement();
			ResultSet rs = idStmt.executeQuery("SELECT COUNT(*) AS recipeCount FROM recipe;");
			rs.next();
			int id = rs.getInt("recipeCount");
			id++;
			rs.close();
			
			String sql = "INSERT INTO recipe (recipeid,title,author,time,upload,country) " +
					"VALUES ('" + id + "','" + recipe.getTitle() + "','" + recipe.getAuthor()+ "','" +
					recipe.getTime() + "','" + timeStamp + "','" + recipe.getCountry() +"');";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			return "success";
		} catch (SQLException e) {}
		
		return "failed";
	}
	
	public Recipe[] getRecipeByCountry(String country){
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		Statement stmt;
		try {
			stmt = c.createStatement();
		
		String sql = "select * from recipe where country='" + country +"'";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			Recipe recipe = new Recipe();
			recipe.setId(rs.getInt("recipeid"));
			recipe.setTitle(rs.getString("title"));
			recipe.setAuthor(rs.getString("author"));
			recipe.setTime(rs.getInt("time"));
			recipe.setUpload(rs.getString("upload"));
			recipe.setCountry(rs.getString("country"));
			result.add(recipe);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Recipe[] rArray = new Recipe[result.size()];
		result.toArray(rArray);
		return rArray;
	}
	
	/*
	 * Använd denna Main-metoden om du vill testa en metod direkt mot databasen. 
	 * Men ändra addressen till databasen först för att det ska fungera.
	 */
	public static void main(String[] args){
		DatabaseController d = new DatabaseController();
		
		//Test av getRecipeByCountry
		Recipe[] ra = d.getRecipeByCountry("kenya");
		
		for (Recipe r : ra) {
			System.out.println("ID: " + r.getId());
			System.out.println("Title: " + r.getTitle());
			System.out.println("Author: " + r.getAuthor());
			System.out.println("Time: " + r.getTime());
			System.out.println("Upload: " + r.getUpload());
			System.out.println("Country: " + r.getCountry());
			System.out.println("\n===========================\n");
		}
	}
}