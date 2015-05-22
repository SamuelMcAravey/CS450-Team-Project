/** Contains the SQLite database
 * 
 */
package com.studlymen;

import java.sql.*;

/**
 * @author 1StudlyMan
 *
 */
public class NGramDatabase 
{
	Connection connection;
	boolean isAutoCommit = false; //We will need to manually commit changes down the road to the database if this is false.
	
	public NGramDatabase() 
	{
		System.out.println("Opening database.");
		try {
			Class.forName("org.sqlite.JDBC");
			//TODO Figure out where on earth this database is... why is it successfully connecting but we cannot query it? 
			connection = DriverManager.getConnection("jdbc:sqlite:ngram.db", "SomeStudlyMen", "OneStudlyPassword");
			connection.setAutoCommit(isAutoCommit);  
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Database opened successfully.");
	}
	
	/**
	 * Function for executing queries to the database. For testing purposes. 
	 * TODO: Remove this (security reasons)
	 * @return A string with the query response within
	 */
	public String executeQuery(String query)
	{
		try {
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			connection.commit();
			
			return resultSet.toString();
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "Error in executeQuery()";
	}
}

