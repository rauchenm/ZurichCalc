package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

	Connection con = null;

	// Open The Connection
	public void openConn() throws Exception {

		String url = "";
		String username = "";
		String password = "";

		url = "jdbc:mysql://10.0.0.2:3306/VersKV?useSSL=false";
		username = "root";
		password = "Ericpema_1";

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, username, password);

	}

	// Close The Connection
	public void closeCon() throws Exception {

		try {
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public double getValue(String stufe, int year) throws SQLException {
		
		Statement stmt = con.createStatement();
		Double jahresbrutto = 0.0;
		String table = "KV_" + year ;
		String data = "Select Wert from " + table + " where Stufe = '" + stufe + "';";
	
		
		ResultSet rs = stmt.executeQuery(data);
		while (rs.next()) {
			jahresbrutto = rs.getDouble("Wert");
		}
		return  jahresbrutto;
		
	}

}
