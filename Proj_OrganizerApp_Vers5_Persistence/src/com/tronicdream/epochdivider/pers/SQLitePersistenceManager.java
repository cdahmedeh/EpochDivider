package com.tronicdream.epochdivider.pers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.tronicdream.epochdivider.types.container.DataContainer;
import com.tronicdream.epochdivider.types.context.Context;
import com.tronicdream.epochdivider.types.task.Task;
import com.tronicdream.epochdivider.types.time.TimeBlock;

import com.tronicdream.epochdivider.pers.models.ContextListModel;
import com.tronicdream.epochdivider.pers.models.DataContainerModel;
import com.tronicdream.epochdivider.pers.models.TaskListModel;
import com.tronicdream.epochdivider.pers.models.TimeBlocksModel;

public class SQLitePersistenceManager implements PersistanceManagerInterface {

	public DataContainer loadDataContainer(File file) {
		//Loading Models
		DataContainerModel dataContainerModel = new DataContainerModel();
		ContextListModel contextListModel = new ContextListModel();
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
            ResultSet rsTaskList = statementTaskList.executeQuery(taskListModel.loadResultSetSQL());
            ResultSet rsTimeBlocks = statementTimeBlocks.executeQuery(timeBlocksModel.loadResultSetSQL());
          
            //Load data
            DataContainer dataContainer = dataContainerModel.resultSetToObject(rsDataContainer, null);
			ArrayList<Context> contextList = contextListModel.resultSetToObject(rsContextList, null);
			HashMap<String, Context> contextByName = new HashMap<>();
			for (Context context: contextList){
				contextByName.put(context.getName(), context);
			}
			ArrayList<Task> tasks = taskListModel.resultSetToObject(rsTaskList, contextByName);
			HashMap<Integer,ArrayList<TimeBlock>> taskTimeblocks = timeBlocksModel.resultSetToObject(rsTimeBlocks, null);
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
            statementTaskList.executeUpdate(taskListModel.dropTableSQL());
            statementTimeBlocks.executeUpdate(timeBlocksModel.dropTableSQL());
            
            //Create tables
            statementDataContainer.executeUpdate(dataContainerModel.createTableSQL());
            statementContextList.executeUpdate(contextListModel.createTableSQL());
            statementTaskList.executeUpdate(taskListModel.createTableSQL());
            statementTimeBlocks.executeUpdate(timeBlocksModel.createTableSQL());
            
            //Saving tables
            statementDataContainer.executeUpdate(dataContainerModel.objectToSQL(dataContainer,null));
            for (Context context: dataContainer.getContexts()){
            	statementContextList.executeUpdate(contextListModel.objectToSQL(context,null));
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
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
