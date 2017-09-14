package com.rev.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	private static ConnectionFactory instance;
	
	private ConnectionFactory() {}
	
	public synchronized static ConnectionFactory getInstance() {
		if (instance == null)
			instance = new ConnectionFactory();
		return instance;
	}
	
	public Connection getConnection() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("src/com/rev/utils/database.properties"));
			Class.forName(prop.getProperty("driver"));
			return DriverManager.getConnection(prop.getProperty("url"),
					prop.getProperty("usr"), prop.getProperty("pw"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}