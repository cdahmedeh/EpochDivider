package org.cdahmedeh.orgapp.types.recurrence;

import java.util.ArrayList;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class DailyRecurrence extends Recurrence {
	public DailyRecurrence(LocalDate start) {
		super(start);
	}

	@Override
	public StringBuilder buildSpecificRule() {
		return new StringBuilder().append("FREQ=DAILY;");
	}
}
