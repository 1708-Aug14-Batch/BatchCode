package com.revature.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;

public class ConnectionFactory {
	private static Logger log = Logging.getLogger();
	private static ConnectionFactory instance;
	
	private static final String DB_PROP_PATH =
			"C:/Users/vlad/my_git_repos/1708Aug14Code/Vladimir_Yevseenko_Code/CoreJava/ReimbursementSystem/src/main/resources/database.properties";
	
	private ConnectionFactory() {
		log.debug("ConnectionFactory() instance created");
	}
	
	public static ConnectionFactory getInstance() {
		log.debug("ConnectionFactory getInstance()");
		if (instance == null)
			instance = new ConnectionFactory();
		return instance;
	}
	
	
	public Connection getConnection() {
		log.debug("ConnectionFactory getConnection()");
		try {
			Properties prop = new Properties();
			prop.load(new FileReader(DB_PROP_PATH));
			Class.forName(prop.getProperty("driver"));
			return DriverManager.getConnection(prop.getProperty("url"),
					prop.getProperty("usr"), prop.getProperty("pw"));
		} catch (Exception ex) {
			log.fatal("ConnectionFactory getConnection() FAILURE");
			log.fatal(ex.getMessage());
			return null;
		}
	}
}
