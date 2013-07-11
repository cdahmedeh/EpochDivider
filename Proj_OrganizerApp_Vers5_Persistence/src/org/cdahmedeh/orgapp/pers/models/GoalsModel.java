package org.cdahmedeh.orgapp.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

public class GoalsModel implements ModelInterface<Entry<View, Duration>, String, Object> {

	public String getTableName() {
		return "GoalsTable";
	}
	
	public String[] getFieldNames() {
		return new String[]{"name", "startDate", "endDate", "duration"};
	}
	
	public String objectToSQL(Entry<View, Duration> goal, String name) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into GoalsTable values('");
		sql.append(name);
		sql.append("', '");
		sql.append(goal.getKey().getStartDate().toString());
		sql.append("', '");
		sql.append(goal.getKey().getEndDate().toString());
		sql.append("', '");
		sql.append(goal.getValue().toString());
		sql.append("')");
		return sql.toString();
	}

	public HashMap<String, HashMap<View, Duration>> resultSetToObject(ResultSet rs, Object object) throws SQLException {
		HashMap<String, HashMap<View, Duration>> contextGoalHashmaps = new HashMap<>();
		
		while (rs.next()){
			String name = rs.getString(1);
			View view = new View ((new LocalDate(rs.getString(2))),(new LocalDate(rs.getString(3))));
			Duration duration = new Duration(rs.getString(4));
			if (contextGoalHashmaps.get(name) == (null)){
				HashMap<View,Duration> goal = new HashMap<>();
				goal.put(view , duration);
				contextGoalHashmaps.put(name, goal);
			} else {
				contextGoalHashmaps.get(name).put(view,duration);
			}
		}
		
		return contextGoalHashmaps;
	}

	@Override
	public String loadResultSetSQL() {
		return "select name, startDate, endDate, duration from GoalsTable";
	}

	@Override
	public String dropTableSQL() {
//		return "drop table if exists GoalsTable";
		return "drop table if exists " + getTableName(); 
	}

	@Override
	public String createTableSQL() {
		return "create table GoalsTable (name string, startDate string, endDate string, duration string)";
	}
}
