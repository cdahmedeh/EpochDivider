package org.cdahmedeh.orgapp.pers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.cdahmedeh.orgapp.pers.models.ContextListModel;
import org.cdahmedeh.orgapp.pers.models.DataContainerModel;
import org.cdahmedeh.orgapp.pers.models.GoalsModel;
import org.cdahmedeh.orgapp.pers.models.TaskListModel;
import org.cdahmedeh.orgapp.pers.models.TimeBlocksModel;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

public class SQLitePersistenceManager implements PersistanceManagerInterface {
	public static void main(String[] args) {
//		DataContainer dataContainer = TestDataGenerator.generateDataContainer();
		PersistanceManagerInterface pm = new SQLitePersistenceManager();
//		pm.saveDataContainer("database.db", dataContainer);
//		pm.loadDataContainer("database.db");
		System.out.println("Done");
	}

	public DataContainer loadDataContainer(File file) {
		//Loading Models
		DataContainerModel dataContainerModel = new DataContainerModel();
		ContextListModel contextListModel = new ContextListModel();
		GoalsModel goalsModel = new GoalsModel();
		TaskListModel taskListModel = new TaskListModel();
		TimeBlocksModel timeBlocksModel = new TimeBlocksModel();
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection connection = null;
		
		try {
			String location = file.getPath();
			connection = DriverManager.getConnection("jdbc:sqlite:" + location);

            Statement statementDataContainer = connection.createStatement();
            Statement statementContextList = connection.createStatement();
            Statement statementGoals = connection.createStatement();
            Statement statementTaskList = connection.createStatement();
            Statement statementTimeBlocks = connection.createStatement();
           
            statementDataContainer.setQueryTimeout(20);
            statementContextList.setQueryTimeout(20);
            statementGoals.setQueryTimeout(20);
            statementTaskList.setQueryTimeout(20);
            statementTimeBlocks.setQueryTimeout(20);
            
            //Populate result sets
            ResultSet rsDataContainer = statementDataContainer.executeQuery(dataContainerModel.loadResultSetSQL());
            ResultSet rsContextList = statementContextList.executeQuery(contextListModel.loadResultSetSQL());
            ResultSet rsGoals = statementGoals.executeQuery(goalsModel.loadResultSetSQL());
            ResultSet rsTaskList = statementTaskList.executeQuery(taskListModel.loadResultSetSQL());
            ResultSet rsTimeBlocks = statementTimeBlocks.executeQuery(timeBlocksModel.loadResultSetSQL());
          
            //Load data
            DataContainer dataContainer = dataContainerModel.resultSetToObject(rsDataContainer);
			ArrayList<Context> contextList = contextListModel.resultSetToObject(rsContextList);
			HashMap<String, HashMap<View,Duration>> contextGoalHashmaps = goalsModel.resultSetToObject(rsGoals);
			for (Context context: contextList){
				context.setGoals(contextGoalHashmaps.get(context.getName()));
			}
			ArrayList<Task> tasks = taskListModel.resultSetToObject(rsTaskList);
			HashMap<Integer,ArrayList<TimeBlock>> taskTimeblocks = timeBlocksModel.resultSetToObject(rsTimeBlocks);
			for (Task task: tasks){
				task.setTimeBlocks(taskTimeblocks.get(task.getId()));
			}
			dataContainer.setContexts(contextList);
			dataContainer.setTasks(tasks);
            return dataContainer;
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return new DataContainer();
	}
	
	@Override
	public void saveDataContainer(File file, DataContainer dataContainer){
		//Loading Models
		DataContainerModel dataContainerModel = new DataContainerModel();
		ContextListModel contextListModel = new ContextListModel();
		GoalsModel goalsModel = new GoalsModel();
		TaskListModel taskListModel = new TaskListModel();
		TimeBlocksModel timeBlocksModel = new TimeBlocksModel();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        Connection connection = null;
        try {        
        	String location = file.getPath();
			connection = DriverManager.getConnection("jdbc:sqlite:" + location);
            
            Statement statementDataContainer = connection.createStatement();
            Statement statementContextList = connection.createStatement();
            Statement statementGoals = connection.createStatement();
            Statement statementTaskList = connection.createStatement();
            Statement statementTimeBlocks = connection.createStatement();
           
            statementDataContainer.setQueryTimeout(20);
            statementContextList.setQueryTimeout(20);
            statementGoals.setQueryTimeout(20);
            statementTaskList.setQueryTimeout(20);
            statementTimeBlocks.setQueryTimeout(20);
            
            //Drop tables
            statementDataContainer.executeUpdate(dataContainerModel.dropTableSQL());
            statementContextList.executeUpdate(contextListModel.dropTableSQL());
            statementGoals.executeUpdate(goalsModel.dropTableSQL());
            statementTaskList.executeUpdate(taskListModel.dropTableSQL());
            statementTimeBlocks.executeUpdate(timeBlocksModel.dropTableSQL());
            
            //Create tables
            statementDataContainer.executeUpdate(dataContainerModel.createTableSQL());
            statementContextList.executeUpdate(contextListModel.createTableSQL());
            statementGoals.executeUpdate(goalsModel.createTableSQL());
            statementTaskList.executeUpdate(taskListModel.createTableSQL());
            statementTimeBlocks.executeUpdate(timeBlocksModel.createTableSQL());
            
            //Saving tables
            statementDataContainer.executeUpdate(dataContainerModel.objectToSQL(dataContainer,null));
            for (Context context: dataContainer.getContexts()){
            	statementContextList.executeUpdate(contextListModel.objectToSQL(context,null));
            	context.getGoals();
            	for (Entry<View,Duration> set: context.getGoals().entrySet())
            		statementGoals.executeUpdate(goalsModel.objectToSQL(set, context.getName()));
            }
            for (Task task: dataContainer.getTasks()){
            	statementTaskList.executeUpdate(taskListModel.objectToSQL(task,null));
            	for (TimeBlock timeblock: task.getAllTimeBlocks())
            		statementTimeBlocks.executeUpdate(timeBlocksModel.objectToSQL(timeblock, task.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
        		System.out.println("close connection");
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
