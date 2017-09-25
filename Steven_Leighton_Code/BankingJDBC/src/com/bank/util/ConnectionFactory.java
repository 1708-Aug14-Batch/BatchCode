package com.bank.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//singleton design pattern
public class ConnectionFactory {

	private static ConnectionFactory cf = null;
	private static Boolean build = true;
	
	private ConnectionFactory(){
		build = false;
	}
	
	/*
	 * provides method callers with the cf object
	 * and synchronized to prevent 2 threads from create
	 * connection object simultaneously
	 */
	public static synchronized ConnectionFactory getInstance(){
		if(build = true){
			cf = new ConnectionFactory();
		}
		return cf;
	}
	
	public Connection getConnection(){
		Connection conn = null;
		try{
			Properties prop = new Properties();
			
			//get file path for database.properties -> right click (properties) -> location -> ctrl-F replace \ with /
			prop.load(new FileReader("C:/Users/steve/my_git_repos/1708Aug14Code/Steven_Leighton_Code/BankingJDBC/src/com/bank/util/database.properties"));
			//loads the class associated with driver property (oracledriver)
			Class.forName(prop.getProperty("driver"));
			//get the actually connection from drivermanager using properties in file
			conn = DriverManager.getConnection(prop.getProperty("url"),
												prop.getProperty("usr"),
												prop.getProperty("pwd"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
	}
}