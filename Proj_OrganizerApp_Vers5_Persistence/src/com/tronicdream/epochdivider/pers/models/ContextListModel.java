package com.tronicdream.epochdivider.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tronicdream.epochdivider.core.types.context.*;

public class ContextListModel implements
		ModelInterface<Context, Object, Object> {

	public String objectToSQL(Context context, Object object) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ContextListTable values('");
		sql.append(context.getName());
		sql.append("', '");
		sql.append(context.getColor());
		sql.append("', '");
		// sql.append(context.getClass().getName());
		if (context instanceof AllTasksContext) { // TODO: This is very bad practice. To be revised.
			sql.append("AllContextsContext");
		} else if (context instanceof DueThisViewContext) {
			sql.append("DueThisViewContext");
		} else if (context instanceof DueTodayContext) {
			sql.append("DueTodayContext");
		} else if (context instanceof DueTodayContext) {
			sql.append("DueTomorrowContext");
		} else if (context instanceof DueTomorrowContext) {
			sql.append("NoContextContext");
		} else if (context instanceof NoDueDateContext) {
			sql.append("NoDueDateContext");
		}else if (context instanceof Context) {
			sql.append("Context");
		}
		sql.append("')");
		return sql.toString();
	}

	/**
	 * Returns contextList with null values in "goals"
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Context> resultSetToObject(ResultSet rs, Object object)
			throws SQLException {
		ArrayList<Context> contextList = new ArrayList<>();
		while (rs.next()) {
			Context context = null;
			switch (rs.getString(3)) {
			case ("Context"):
				context = new Context(rs.getString(1));
				break;
			case ("DueThisViewContext"):
				context = new DueThisViewContext();
				break;
			case ("DueTodayContext"):
				context = new DueTodayContext();
				break;
			case ("DueTomorrowContext"):
				context = new DueTomorrowContext();
				break;
			case ("NoContextContext"):
				context = new NoContextContext();
				break;
			case ("NoDueDateContext"):
				context = new NoDueDateContext();
				break;
			case ("AllContextsContext"):
				context = new AllTasksContext();
				break;
			}
			context.setColor(rs.getInt(2));
			contextList.add(context);
		}
		return contextList;
	}

	@Override
	public String loadResultSetSQL() {
		return "select name, color, contexttype from ContextListTable";
	}

	@Override
	public String dropTableSQL() {
		return "drop table if exists ContextListTable";
	}

	@Override
	public String createTableSQL() {
		return "create table ContextListTable (name string, color int, contexttype string)";
	}

}
