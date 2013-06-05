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
	}
	
	/* (non-Javadoc)
	 * @see org.cdahmedeh.orgapp.pers.PersistanceManagerInterface#loadDataContainer()
	 */
	@Override
	public DataContainer loadDataContainer(File file){
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

            Statement statementContext = connection.createStatement();
            Statement statementGoal = connection.createStatement();
            Statement statementTask = connection.createStatement();
            Statement statementTime = connection.createStatement();

            statementContext.setQueryTimeout(20);
            statementGoal.setQueryTimeout(20);
            statementTask.setQueryTimeout(20);
            statementTime.setQueryTimeout(20);

            ResultSet rs0 = statementContext.executeQuery("select name, color from contextlist");
            ResultSet rs1 = statementGoal.executeQuery("select contextname, startdate, enddate, duration from goalstable");
            ResultSet rs2 = statementTask.executeQuery("select id, title, context, due, duration, completed, event from tasklist");
            ResultSet rs3 = statementTime.executeQuery("select taskid, start, end from timeblocks");
			
            DataContainer dataContainer = new DataContainer();
        	ArrayList<Context> contexts = new ArrayList<>();
        	HashMap<String,Context> contextList = new HashMap<>();
        	ArrayList<Task> tasks = new ArrayList<>();
        	
        	//Add default contexts
        	contexts.addAll(dataContainer.generateDefaultContexts());
        	
            while(rs0.next()){
            	String name = rs0.getString(1);
            	Context context = new Context(name);
            	context.setColor(rs0.getInt(2));
            	rs1 = statementGoal.executeQuery("select contextname, startdate, enddate, duration from goalstable");
            	while(rs1.next()){
            		if (rs1.getString(1).equals(name)){
            			context.setGoal(new View(new LocalDate(rs1.getString(2)), new LocalDate(rs1.getString(3))), new Duration(rs1.getString(4)));
            		}
            	}
            	contextList.put(name, context);
            	contexts.add(context);
            }
            dataContainer.setContexts(contexts);
            
            while(rs2.next()){
            	int id = rs2.getInt(1);
            	String name = rs2.getString(2);
            	Task task = new Task(name);
            	task.setContext(contextList.get(rs2.getString(3)));//TODO: approve shortcut. Less space in database, more space while loading data
            	if (rs2.getString(4).equals("null"))
            		task.setDue(null);
            	else
            		task.setDue(new DateTime(rs2.getString(4)));
            	task.setEstimate(new Duration(rs2.getString(5)));
            	task.setCompleted(rs2.getBoolean(6));
            	task.setEvent(rs2.getBoolean(7));
            	rs3 = statementTime.executeQuery("select taskid, start, end from timeblocks");
            	while(rs3.next()){
            		if (rs3.getInt(1) == (id)){
            			task.assignToTimeBlock(new TimeBlock((new DateTime(rs3.getString(2))),(new DateTime(rs3.getString(3)))));
            		}
            	}
//            	rs3.first();
            	tasks.add(task);
            }
            dataContainer.setTasks(tasks);
			
            return dataContainer;
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
		return new DataContainer();
	}
	
	/* (non-Javadoc)
	 * @see org.cdahmedeh.orgapp.pers.PersistanceManagerInterface#saveDataContainer(org.cdahmedeh.orgapp.types.container.DataContainer)
	 */
	@Override
	public void saveDataContainer(File file, DataContainer dataContainer){
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
            
            Statement statementContext = connection.createStatement();
            Statement statementGoal = connection.createStatement();
            Statement statementTask = connection.createStatement();
            Statement statementTime = connection.createStatement();

            statementContext.setQueryTimeout(20);
            statementGoal.setQueryTimeout(20);
            statementTask.setQueryTimeout(20);
            statementTime.setQueryTimeout(20);
            
            //Tables that deal with the contexts arraylist
            statementContext.executeUpdate("drop table if exists contextlist");
            statementGoal.executeUpdate("drop table if exists goalstable");
            statementContext.executeUpdate("create table contextlist (name string, color int)");
            statementGoal.executeUpdate("create table goalstable (contextname string, startdate string, enddate string, duration string)");

            //Tables that deal with the tasks arraylist
            statementTask.executeUpdate("drop table if exists tasklist");
            statementTime.executeUpdate("drop table if exists timeblocks");
            statementTask.executeUpdate("create table tasklist (id int, title string, context string, due string, duration string, completed boolean, event boolean)");
            statementTime.executeUpdate("create table timeblocks (taskid int, start string, end string)");
            
            //Saving the tables for the contexts
            for (Context context: dataContainer.getContexts()){
            	if (context.isSelectable() && !(context instanceof NoContextContext)){
            		statementContext.executeUpdate("insert into contextlist values('"+ context.getName() +"', '"+ context.getColor() +"')");
            		for (Entry<View, Duration> set: context.getGoals().entrySet()){
            			statementGoal.executeUpdate("insert into goalstable values('"+ context.getName() +"', '"+ set.getKey().getStartDate().toString() +"', '"+ set.getKey().getEndDate().toString() +"', '"+ set.getValue().toString() +"')");	
            		}
            	}
            }
            
            //Saving the tables for the tasks
            for (Task task: dataContainer.getTasks()){
            	if (task.isDue()){
            		statementTask.executeUpdate("insert into tasklist values('"+ task.getId() +
                			"', '"+ task.getTitle() +
                			"', '"+ task.getContext().getName() +
                			"', '"+ task.getDue().toString() +
                			"', '"+ task.getEstimate().toString() +
                			"', '"+ task.isCompleted() +
                			"', '"+ task.isEvent() +"')");
            	} else {
            		statementTask.executeUpdate("insert into tasklist values('"+ task.getId() +
                			"', '"+ task.getTitle() +
                			"', '"+ task.getContext().getName() +
                			"', '"+ null +
                			"', '"+ task.getEstimate().toString() +
                			"', '"+ task.isCompleted() +
                			"', '"+ task.isEvent() +"')");
            	}
            	for(TimeBlock timeblock: task.getAllTimeBlocks()){
            		statementTime.executeUpdate("insert into timeblocks values('"+ task.getId() +"', '"+ timeblock.getStart().toString() +"', '"+ timeblock.getEnd().toString()+"')");
            	}
            }
            
            //Print for debugging
            ResultSet rs0 = statementContext.executeQuery("select * from contextlist");
            ResultSet rs1 = statementGoal.executeQuery("select * from goalstable");
            ResultSet rs2 = statementTask.executeQuery("select * from tasklist");
            ResultSet rs3 = statementTime.executeQuery("select * from timeblocks");
            
            while (rs0.next()){
                System.out.println(rs0.getString(1));
            }
            while (rs1.next()){
                System.out.println(rs1.getString(1));
            }
            while (rs2.next()){
                System.out.println(rs2.getString(1));
            }
            while (rs3.next()){
                System.out.println(rs3.getString(1));
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
