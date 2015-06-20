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

	public static boolean GenerateTables(NGramDatabase db, int nGramCount)
	{
		for (int i = 1; i <= nGramCount; i++)
		{
            String columns = "word0 varchar(50)";
            for (int j = 1; j <= i; j++)
            {
                columns += ", word" + j + " varchar(50)";
            }

            String sql = "create table ngram" + i + "(" + columns + ")";
			boolean success = db.execute(sql);
			if (!success)
				return false;
		}
		return true;
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


	/**
	 * Function for executing queries to the database. For testing purposes.
	 * TODO: Remove this (security reasons)
	 * @return A string with the query response within
	 */
	public boolean execute(String query)
	{
		try {
			Statement stmt = connection.createStatement();
			boolean isResultsSet = stmt.execute(query);
			connection.commit();

			return true;

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return false;
	}
}

