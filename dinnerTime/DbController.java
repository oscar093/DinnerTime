package dinnerTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbController {

	public void testDatabase() {

		System.out.println("——– PostgreSQL ” + “JDBC Connection Testing ————");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? ” + “Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			// Denn servers skall vi använda.
			String user = "sqmkxuar";
			String url = "jdbc:postgresql://104.46.40.113:5432/sqmkxuar";
			String pw = "_40zWEK1pSh16XuWAurF4wVB_VU63Ebx";

			// Till Jespers server
			// String user = "";
			// String url = "jdbc:postgresql://195.178.224.72:5432/ag7036";
			// String pw = "";

			connection = DriverManager.getConnection(url, user, pw);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		// Statement sql = connection.createStatement();
		//
		// if (connection != null) {
		//
		// sqlStatement("INSERT INTO TESTTABLE (product_id,product_name,)
		// VALUES(1, ‘product1’)");
		// System.out.println("Successfully added");
		//
		// } else {
		//
		// System.out.println("Failed to make connection!");
		//
		// }
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DbController d = new DbController();
		d.testDatabase();
	}
}
