package org.cdahmedeh.orgapp.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class TaskListModel implements ModelInterface<Task, Object,HashMap<String,Context>> {

	public String objectToSQL(Task task, Object object) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into TaskListTable values('");
		sql.append(task.getId());
		sql.append("', '");
		sql.append(task.getTitle());
		sql.append("', '");
		sql.append(task.getContext().getName());
		sql.append("', '");
		if (task.isDue()){
			sql.append(task.getDue().toString());
		}else{
			sql.append("null");
		}
		sql.append("', '");
		sql.append(task.getEstimate().toString());
		sql.append("', '");
		sql.append(task.isCompleted());
		sql.append("', '");
		sql.append(task.isEvent());
		sql.append("')");
		return sql.toString();
	}

	public ArrayList<Task> resultSetToObject(ResultSet rs, HashMap<String,Context> contexts) throws SQLException {
		ArrayList<Task> tasks = new ArrayList<>();
		while (rs.next()){
			Task task = new Task(rs.getString(2));
			task.setId(rs.getInt(1));
			String context = rs.getString(3);
			if (!context.equals("No Context")){
				task.setContext(contexts.get(context));
			}
			String dueDate = rs.getString(4);
			if (!dueDate.equals("null")){
				task.setDue(new DateTime(dueDate));
			}
			task.setEstimate(new Duration(rs.getString(5)));
			task.setCompleted(rs.getString(6).equals("true")? true: false);
			task.setEvent(rs.getString(7).equals("true")? true: false);
			tasks.add(task);
		}
		return tasks;
	}

	@Override
	public String loadResultSetSQL() {
		return "select id, title, context, due, estimate, completed, event from TaskListTable";
	}

	@Override
	public String dropTableSQL() {
		return "drop table if exists TaskListTable";
	}

	@Override
	public String createTableSQL() {
		return "create table TaskListTable (id int, title string, context string, due string, estimate string, completed string, event string)";
	}
}
