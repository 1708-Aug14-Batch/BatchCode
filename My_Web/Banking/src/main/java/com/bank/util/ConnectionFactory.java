package com.bank.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	
	private static ConnectionFactory instance = null;
	
	private ConnectionFactory() {
		instance = this;
	}
	
	public static synchronized ConnectionFactory getInstance() {
		return (instance == null) ? new ConnectionFactory() : instance;
	}
	
	public Connection getConnection() {
		Connection connect = null;		
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("C:\\Users\\Nathan\\my_dir_repos\\1708Aug14Code\\My_Web\\Banking\\src\\main\\java\\com\\bank\\util\\db.properties"));
			Class.forName(prop.getProperty("driver"));
			connect = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("usr"),
					prop.getProperty("pwd"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return connect;
	}

}