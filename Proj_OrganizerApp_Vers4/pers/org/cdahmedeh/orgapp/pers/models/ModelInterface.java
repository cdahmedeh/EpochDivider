package org.cdahmedeh.orgapp.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelInterface<T,L> {

	public abstract String objectToSQL(T object, L i);

	public abstract Object resultSetToObject(ResultSet rs) throws SQLException;

	public abstract String loadResultSetSQL();
	
	public abstract String dropTableSQL();
	
	public abstract String createTableSQL();
}