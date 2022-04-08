package com.mrhi.class_six.person_identity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class DBConnector {
	
	public DBConnector() {}	
	
	////		__init__ DB			////
	public Connection initConnection(){
		Connection connection = null;
	
		try {
			final String LOCATION = 
				"src\\com\\mrhi\\class_six\\person_identity\\person_identity.properties";
				
			final Properties inPrivate = new Properties();
			final FileReader inStream = new FileReader( LOCATION);
			inPrivate.load( inStream);
		
		
			final String DRIVER = inPrivate.getProperty("Driver");
			final String URL	= inPrivate.getProperty("URL");
			final String UID	= inPrivate.getProperty("UID");
			final String PASSWD	= inPrivate.getProperty("PASSWD");
	
	
			Class.forName( DRIVER);
			connection = (Connection ) DriverManager.getConnection( URL, UID, PASSWD);

				
			////	 __END__	////
		} catch ( ClassNotFoundException e) {
			System.err.println("cant __INIT__ DB");
			return null;
			
		} catch ( SQLException e) {
			System.err.println("cant __INIT__ DB");
			return null;

		} catch ( FileNotFoundException e) {
			System.err.println("cant load properties");
			return null;
		
		} catch ( Exception e) {
			System.err.println("? try again??");
			return null;
		}

		return connection;
	}
}
