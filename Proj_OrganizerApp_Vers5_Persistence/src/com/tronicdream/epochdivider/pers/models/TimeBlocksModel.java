package com.tronicdream.epochdivider.pers.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.tronicdream.epochdivider.types.time.TimeBlock;
import org.joda.time.DateTime;


public class TimeBlocksModel implements ModelInterface<TimeBlock, Integer, Object>{

	public String objectToSQL(TimeBlock timeblock, Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into TimeBlockTable values('");
		sql.append(id);
		sql.append("', '");
		sql.append(timeblock.getStart());
		sql.append("', '");
		sql.append(timeblock.getEnd());
		sql.append("')");
		return sql.toString();
	}

	public HashMap<Integer,ArrayList<TimeBlock>> resultSetToObject(ResultSet rs, Object object) throws SQLException {
		HashMap<Integer,ArrayList<TimeBlock>> timeblocks = new HashMap<>();
		
		while (rs.next()){
			int id = rs.getInt(1);
			TimeBlock timeblock = new TimeBlock(new DateTime(rs.getString(2)),new DateTime(rs.getString(3)));
			if (timeblocks.get(id) == null){
				ArrayList<TimeBlock> block = new ArrayList<>();
				block.add(timeblock);
				timeblocks.put(id, block);
			} else {
				timeblocks.get(id).add(timeblock);
			}
		}
		return timeblocks;
	}

	@Override
	public String loadResultSetSQL() {
		return "select id, start, end from TimeBlockTable";
	}

	@Override
	public String dropTableSQL() {
		return "drop table if exists TimeBlockTable";
	}

	@Override
	public String createTableSQL() {
		return "create table TimeBlockTable (id int, start string, end string)";
	}
}
