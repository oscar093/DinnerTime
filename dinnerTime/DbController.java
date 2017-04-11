package dinnerTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * DbController controlles all ineraktion with the database for DinnerTime.
 * 
 * @author osc
 *
 */

/*
 * Denna används just nu för att visa hur man interagerar med databasen.
 */
public class DbController {
	private Connection connection = null;

	/**
	 * Connects to database.
	 */
	public void connectToDb() {

		System.out.println("——– PostgreSQL ” + “JDBC Connection Testing ————");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? ” + “Include in your library path!");
			e.printStackTrace();
			return;
		}

		System.out.println("PostgreSQL JDBC Driver Registered!\n");
		try {
			// Uppgifter till databasen
			String user = "sqmkxuar";
			String url = "jdbc:postgresql://104.46.40.113:5432/sqmkxuar";
			String pw = "_40zWEK1pSh16XuWAurF4wVB_VU63Ebx";

			connection = DriverManager.getConnection(url, user, pw);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console\n");
			e.printStackTrace();
			return;

		}

	}

	/**
	 * Prints all info in testtable.
	 */
	public void addToTesttable() {
		int keepGoing = JOptionPane.showConfirmDialog(null, "Vill du prova att lägga till en person i databasen?");
		if (keepGoing == 0) {
			String name = JOptionPane.showInputDialog("Skriv in namn");
			String strAge = JOptionPane.showInputDialog("Skriv in ålder");
			int age = Integer.parseInt(strAge);
			String address = JOptionPane.showInputDialog("Skriv in address");

			if (connection != null && name != null && name.length() > 1 && age > 0 && address != null
					&& address.length() > 1) {
				Statement sql;
				try {
					sql = connection.createStatement();
					sql.execute("insert into testtable (name,age,address) values('" + name + "','" + age + "','"
							+ address + "')");
					System.out.println("Successfully added");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Något gick fel, se till att skriva i alla fält.");
			}
		}
	}

	/**
	 * Returns specified table
	 * 
	 */
	public void getWholeTesttable() {

		try {
			Statement sql;
			sql = connection.createStatement();
			ResultSet rs = sql.executeQuery("select * from testtable");
			System.out.println("========================================\n");
			while (rs.next()) {
				System.out.println("   Namn: " + rs.getString("name"));
				System.out.println("  Ålder: " + rs.getString("age"));
				System.out.println("Address: " + rs.getString("address"));
				System.out.println("");
			}
			System.out.println("=========================================");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Används endast för testning.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DbController d = new DbController();
		d.connectToDb();
		d.addToTesttable();
		d.getWholeTesttable();
	}
}
