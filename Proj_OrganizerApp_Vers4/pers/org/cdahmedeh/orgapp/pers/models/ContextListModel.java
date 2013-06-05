package org.cdahmedeh.orgapp.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;

public class ContextListModel implements ModelInterface<Context, Object> {

	public String objectToSQL(Context context, Object object) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ContextListTable values('");
		sql.append(context.getName());
		sql.append("', '");
		sql.append(context.getColor());
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
	public ArrayList<Context> resultSetToObject(ResultSet rs) throws SQLException {
		ArrayList<Context> contextList = new ArrayList<>();
		while (rs.next()){
			Context context = new Context(rs.getString(1));
			context.setColor(rs.getInt(2));
			contextList.add(context);
		}
		return contextList;
	}

	@Override
	public String loadResultSetSQL() {
		return "select name, color from ContextListTable";
	}

	@Override
	public String dropTableSQL() {
		return "drop table if exists ContextListTable";
	}

	@Override
	public String createTableSQL() {
		return "create table ContextListTable (name string, color int)";
	}

}
