package dinnerTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseController {
	
	private Connection c = null;
	
	public DatabaseController() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dinnertime", "postgres", "P@ssw0rd");
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
}