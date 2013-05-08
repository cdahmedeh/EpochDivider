package org.cdahmedeh.orgapp.run;

import java.sql.*;

import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.pers.PersistanceManagerInterface;
import org.cdahmedeh.orgapp.pers.SQLitePersistenceManager;
import org.cdahmedeh.orgapp.types.container.DataContainer;

public class Run001PersistenceManager {
	public static void main(String[] args) {
		// load the sqlite-JDBC driver using the current class loader
	    try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


    	//Generate some test data.
    	DataContainer generatedDataContainer = TestDataGenerator.generateDataContainer();
	    
	    Connection connection = null;
	    
	    try
	    {
	    	// create a database connection
	    	connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	    	Statement statement = connection.createStatement();
	    	statement.setQueryTimeout(30);  // set timeout to 30 sec.

	    	statement.executeUpdate("drop table if exists testdata");

	    	statement.executeUpdate("create table testdata ()");

	    }catch(SQLException e)
	    {
	    	// connection close failed.
	    	System.err.println(e);
	    }
	    
	    
		
		
		
		//Save it
		PersistanceManagerInterface pm = new SQLitePersistenceManager();
		pm.saveDataContainer(generatedDataContainer);
		
		//Load it again
		DataContainer loadedDataContainer = pm.loadDataContainer();
		System.out.println(loadedDataContainer); //This is just to remove a compiler warning.
	}
}
