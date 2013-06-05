package org.cdahmedeh.orgapp.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class TaskListModel implements ModelInterface<Task, Object> {

	public String objectToSQL(Task task, Object object) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into TaskListTable values('");
		sql.append(task.getId());
		sql.append("', '");
		sql.append(task.getTitle());
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

	public ArrayList<Task> resultSetToObject(ResultSet rs) throws SQLException {
		ArrayList<Task> tasks = new ArrayList<>();
		while (rs.next()){
			Task task = new Task(rs.getString(2));
			task.setId(rs.getInt(1));
			String dueDate = rs.getString(3);
			if (!dueDate.equals("null")){
				task.setDue(new DateTime(dueDate));
			}
			task.setEstimate(new Duration(rs.getString(4)));
			task.setCompleted(rs.getBoolean(5));
			task.setEvent(rs.getBoolean(6));
			tasks.add(task);
		}
		return tasks;
	}

	@Override
	public String loadResultSetSQL() {
		return "select id, title, due, estimate, completed, event from TaskListTable";
	}

	@Override
	public String dropTableSQL() {
		return "drop table if exists TaskListTable";
	}

	@Override
	public String createTableSQL() {
		return "create table TaskListTable (id int, title string, due string, estimate string, completed boolean, event boolean)";
	}
}
