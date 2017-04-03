/**
 * "Logger" class: used to interact with the User Data Repository.
 * Work in progress. Could also use some cleaning/simplifying.
 * 
 * CHANGELOG:
 * 	- 2017/03/26 -Changed getUserData() to return a string list containing extracted table info.
 * 				  Also can extract data regardless of number of rows.
 * 				 -Added getReport(). Given a date range, will return total entries and
 *  			  stocks, ma ranges and hd ranges in order of most frequent occurence with %.
 *  
 *  - 2017/04/02 -Changed the name of the table referred (usagedatatest -> appusagedata).
 *  			 -Added methods to assist in account management: Add a username/password
 *  			  to a SQL table (also contains user privileges (Admin or User)),
 *   			  check whether a name already exists within table, and check if a given
 *                username has Admin priviledges.
 *  			 
 *   
 * IMPORTANT: Need to download a JDBC Driver (here: https://dev.mysql.com/downloads/connector/j/5.1.html)
 *            and add it to PATH.
 *            Also, change column/table names in SQL queries to whatever's appropriate.
 */

import java.sql.*;
import java.util.ArrayList;


public class Logger {
	
	//inner class, used to interact with database
	public class UserDataConnection
	{

		
		// database details: url, username and password
		private String urlString;
		private String userName;
		private String password;
		
		// for interaction with db
		private Connection conn;
		private Statement stmt;
		
		private UserDataConnection(String url, String name, String pw)
		{
			urlString = url;
			userName = name;
			password = pw;
			
			conn = null;
			stmt = null;
			
		}
		
		/**
		 * Used to interact with database. (For INSERT, UPDATE, etc.)
		 * @param sqlQuery SQL Query to pass.
		 */
		public void logUserData(String sqlQuery)
		{
			try
			{
				// connect to database
				conn = DriverManager.getConnection(urlString, userName, password);
				
				// create statement object, used to interact with db
				stmt = conn.createStatement();
				
				//System.out.println("Query> " + sql); ignore, for testing
				
				//execute sql query, does not return a result
				stmt.executeUpdate(sqlQuery);
				
				//close connection and statement object
				stmt.close();
				conn.close();
				
			}
			catch (SQLException ex) //exception handling
			{
				ex.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally //close connection if exception thrown
			{
				try
				{
					if (stmt!=null)
						stmt.close();
				}
				catch (SQLException ex2)
				{
					
				}
				
				try
				{
					if (conn != null)
						conn.close();
				}
				catch (SQLException ex3)
				{
					ex3.printStackTrace();
				}
			}
			
		}
		
		/**
		 * Used to read from the user database.
		 * Reads 2 column contents
		 * 
		 * @param sqlQuery SQL query to be passed
		 * @return ResultSet object containing query results.
		 */
		public ArrayList<String> getUserData(String sqlQuery)
		{
			
			ArrayList<String> res1 = new ArrayList<String>();
			
			ResultSet rs = null;
			
			try
			{
				// connecting to db
				conn = DriverManager.getConnection(urlString, userName, password);
				
				stmt = conn.createStatement();
				
				//execute query, returns a ResultSet object
				rs = stmt.executeQuery(sqlQuery);
				
				// get meta data, to get column number
				ResultSetMetaData rsmd = rs.getMetaData();
				
				int noCols = rsmd.getColumnCount();
				
				while (rs.next())
				{
					//add values from each column, while there are rows
					//column indexes start from 1
					for (int i = 1; i < noCols + 1; i++)
					{
						res1.add(rs.getString(i));
					}
				}
				
				stmt.close();
				conn.close();
				
			}
			catch (SQLException se)
			{
				se.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (stmt!=null)
						stmt.close();
				}
				catch (SQLException ex2)
				{
					
				}
				
				try
				{
					if (conn != null)
						conn.close();
				}
				catch (SQLException ex3)
				{
					ex3.printStackTrace();
				}
				
				
			}
			
			return res1;
		}

	} // end of inner class

	
	/**
	 * Log user choices into the database
	 * 
	 * @param user_id user ID no.
	 * @param date entry date
	 * @param stock chosen stock
	 * @param ma_range chosen moving average range
	 * @param hd_range chosen historical data range
	 */
	public void logEntry(String userName, String date, String stock,
			String ma_range, String hd_range)
	{
		// database details, change to whatever will be used
		String url = "jdbc:mysql://localhost:3306/userdatarepos";
		String user = "root";
		String pass = "RelatedT0Database";
		
		//
		UserDataConnection newConn = new UserDataConnection(url, user, pass);
		
		//SQL string. Change table name and column names to whatever
		String sql = "INSERT INTO appusagedata(username, entry_date, stock, "
				+ "ma_range, hd_range) VALUES ( '" + userName + "', '" + 
				date + "', '" + stock + "', '" + ma_range + "', '"
				+ hd_range + "')";
		
		
		newConn.logUserData(sql);
		
	}
	
	/**
	 * Checks to see if a username is already in use
	 * 
	 * @param userName user name to check
	 * @return true if already used, false if available
	 */
	public boolean userExists(String userName)
	{
		// database details, change to whatever will be used
		String url = "jdbc:mysql://localhost:3306/userdatarepos";
		String user = "root";
		String pass = "RelatedT0Database";
				
		//
		UserDataConnection newConn = new UserDataConnection(url, user, pass);
				
		
		String sql = "SELECT username FROM appusers WHERE "
				+ "username = '" + userName + "'";
				
		// get an arraylist of results
		ArrayList<String> results = newConn.getUserData(sql);
				
		// if list is not empty, user already exists.
		if (results.size() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Add a new user to users table
	 * 
	 * @param userName name of new user
	 * @param passw new user's password
	 * @param priviledge regular User or Admin?
	 */
	public void registerUser(String userName, String passw, String priviledge)
	{
		// database details, change to whatever will be used
		String url = "jdbc:mysql://localhost:3306/userdatarepos";
		String user = "root";
		String pass = "RelatedT0Database";
		
		//
		UserDataConnection newConn = new UserDataConnection(url, user, pass);
		
		// add user to table
		String sql = "INSERT INTO appusers(username, pass_word, priviledge) " 
				+ "VALUES ( '" + userName + "', '" + passw + "', '" 
				+ priviledge + "')";
		
		newConn.logUserData(sql);
		
	}
	
	/**
	 * checks to see if a given user and password exist in the table
	 * 
	 * @param userName user name to verify
	 * @param passw user's password
	 * @return true if valid, false otherwise
	 */
	public boolean validateUser(String userName, String passw)
	{
		// database details, change to whatever will be used
		String url = "jdbc:mysql://localhost:3306/userdatarepos";
		String user = "root";
		String pass = "RelatedT0Database";
		
		//
		UserDataConnection newConn = new UserDataConnection(url, user, pass);
		
		String sql = "SELECT username, pass_word FROM appusers WHERE "
				+ "username = '" + userName + "' AND pass_word = '" 
				+ passw + "'";
		
		// get an arraylist of results
		ArrayList<String> results = newConn.getUserData(sql);
		
		// if list is not empty, password is valid.
		if (results.size() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks whether given username belongs to an Admin
	 * 
	 * @param userName name of user account to check
	 * @return true if admin, false otherwise.
	 */
	public boolean isAdmin(String userName)
	{
		// database details, change to whatever will be used
		String url = "jdbc:mysql://localhost:3306/userdatarepos";
		String user = "root";
		String pass = "RelatedT0Database";
		
		//
		UserDataConnection newConn = new UserDataConnection(url, user, pass);
		
		String sql = "SELECT username, priviledge FROM appusers WHERE "
				+ "username = '" + userName + "' AND priviledge = 'Admin'";
		
		// get an arraylist of results
		ArrayList<String> results = newConn.getUserData(sql);
		
		// if list is not empty, user has Admin priviledges.
		if (results.size() > 0)
			return true;
		else
			return false;
	}
	
	public ArrayList<String> getReport(String startDate, String endDate)
	{
		// database details, change to whatever will be used
		String url = "jdbc:mysql://localhost:3306/userdatarepos";
		String user = "root";
		String pass = "RelatedT0Database";
		
		//
		UserDataConnection newConn = new UserDataConnection(url, user, pass);
		
		// getting count twice because method is built to handle returning 2 columns for now.
		String sqlQ = "SELECT COUNT(*) FROM appusagedata WHERE entry_date "
				+ "BETWEEN '" + startDate + "' AND '" + endDate + "'";
		
		// results go in here
		ArrayList<String> resultList = new ArrayList<String>();
		
		// first set of results: number of entries
		ArrayList<String> res1;
		
		res1 = newConn.getUserData(sqlQ);
		
		// save total entries as int for later
		int totalEnt = Integer.parseInt(res1.get(res1.size() - 1));
		
		resultList.add("Total Entries: ");
		resultList.add(res1.get(res1.size() - 1));
		
		// retrieving most popular stocks, currently works for my own setup
		sqlQ = "SELECT stock, COUNT(stock) AS total_stock FROM appusagedata" 
				+ " WHERE entry_date BETWEEN '" + startDate + "' AND '" + 
				endDate + "' GROUP BY stock ORDER BY total_stock DESC";
		
		res1 = newConn.getUserData(sqlQ);
		
		// change count to percentage
		for (int i = 1; i < res1.size(); i = i + 2)
		{
			double temp1 = Double.parseDouble(res1.get(i));
			
			temp1 = temp1 / totalEnt;
			
			res1.set(i, Double.toString(temp1));
		}
		
		// add to results list
		resultList.add("Stocks: ");
		resultList.addAll(res1);
		
		// retrieving most popular moving averages
		sqlQ = "SELECT ma_range, COUNT(ma_range) AS total_ma FROM appusagedata" 
				+ " WHERE entry_date BETWEEN '" + startDate + "' AND '" + 
				endDate + "' GROUP BY ma_range ORDER BY total_ma DESC";
		
		res1 = newConn.getUserData(sqlQ);
		
		// change count to percentage
		for (int i = 1; i < res1.size(); i = i + 2)
		{
			double temp1 = Double.parseDouble(res1.get(i));
			
			temp1 = temp1 / totalEnt;
			
			res1.set(i, Double.toString(temp1));
		}
		
		// add to result list
		resultList.add("Moving Average ranges:");
		resultList.addAll(res1);
		
		// retrieving most popular historical data ranges
		sqlQ = "SELECT hd_range, COUNT(hd_range) AS total_hd FROM appusagedata" 
				+ " WHERE entry_date BETWEEN '" + startDate + "' AND '" + 
				endDate + "' GROUP BY hd_range ORDER BY total_hd DESC";
		
		res1 = newConn.getUserData(sqlQ);
		
		// change count to percentage
		for (int i = 1; i < res1.size(); i = i + 2)
		{
			double temp1 = Double.parseDouble(res1.get(i));
			
			temp1 = temp1 / totalEnt;
			
			res1.set(i, Double.toString(temp1));
		}
		
		// add to result list
		resultList.add("Historical Data ranges:");
		resultList.addAll(res1);
		
		// return list of results
		return resultList;
	}
	
	
}
