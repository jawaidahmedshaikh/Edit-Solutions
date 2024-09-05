package com.selman.calcfocus.correspondence.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcConnection {

	public static Connection getConnection(String dbURL, String user, String pass, String db) throws SQLException {

		Connection conn = null;
		
		conn = DriverManager.getConnection(dbURL + db, user, pass);
		if (conn != null) {
			DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
			System.out.println("Driver name: " + dm.getDriverName());
			System.out.println("Driver version: " + dm.getDriverVersion());
			System.out.println("Product name: " + dm.getDatabaseProductName());
			System.out.println("Product version: " + dm.getDatabaseProductVersion());
		}

		return conn;
	}

}
