package org.cdahmedeh.orgapp.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.TaskType;
import org.joda.time.LocalDate;

public class DataContainerModel implements ModelInterface<DataContainer, Object, Object> {
	
	public String objectToSQL(DataContainer dataContainer, Object object) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into DataContainerTable values('");
		sql.append(dataContainer.getShowType());
		sql.append("', '");
		sql.append(dataContainer.getShowCompleted());
		sql.append("', '");
		sql.append(dataContainer.getDimPast());
		sql.append("', '");
		if (!(dataContainer.getContexts().isEmpty())){
			sql.append("1");//TODO: Remove this line once it's been decided how this data would be saved
//			sql.append(dataContainer.getContexts().get(0).getColorCounter());
		}
		sql.append("', '");
		if (!(dataContainer.getTasks().isEmpty())){
			sql.append("1");//TODO: Remove this line once it's been decided how this data would be saved
//			sql.append(dataContainer.getTasks().get(0).getIdCounter());
		}
		sql.append("', '");
		sql.append(dataContainer.getView().getStartDate());
		sql.append("', '");
		sql.append(dataContainer.getView().getEndDate());
		sql.append("')");
		return sql.toString();
	}

	/**
	 * Returns DataContainer with null values in "context", "tasks" and "selectedContext"
	 */
	public DataContainer resultSetToObject(ResultSet rs, Object object) throws SQLException {
		DataContainer dataContainer = new DataContainer();
		dataContainer.setShowType(TaskType.valueOf(rs.getString(1)));
		dataContainer.setShowCompleted(rs.getString(2).equals("true")? true: false);
		dataContainer.setDimPast(rs.getString(3).equals("true")? true: false);
		dataContainer.setView(new View((new LocalDate(rs.getString(6))),(new LocalDate (rs.getString(7)))));		
		return dataContainer;
	}

	@Override
	public String loadResultSetSQL() {
		return "select showType, showCompleted, dimPast, colorCounter, idCounter, startDate, endDate from DataContainerTable";
	}

	@Override
	public String dropTableSQL() {
		return "drop table if exists DataContainerTable";
	}

	@Override
	public String createTableSQL() {
		return "create table DataContainerTable (showType string, showCompleted string, dimPast string, colorCounter int, idCounter int, startDate string, endDate string)";
	}
}
