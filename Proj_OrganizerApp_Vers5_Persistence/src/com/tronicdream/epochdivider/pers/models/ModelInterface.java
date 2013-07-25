package com.tronicdream.epochdivider.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelInterface<T,U,V> {

	public abstract String objectToSQL(T object, U i);

	public abstract Object resultSetToObject(ResultSet rs, V i) throws SQLException;

	public abstract String loadResultSetSQL();
	
	public abstract String dropTableSQL();
	
	public abstract String createTableSQL();
}