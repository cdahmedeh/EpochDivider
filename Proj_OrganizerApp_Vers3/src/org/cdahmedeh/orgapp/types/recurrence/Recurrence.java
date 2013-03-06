package org.cdahmedeh.orgapp.types.recurrence;

import java.text.ParseException;

import org.joda.time.LocalDate;

import com.google.ical.compat.jodatime.LocalDateIterable;
import com.google.ical.compat.jodatime.LocalDateIteratorFactory;

public abstract class Recurrence {
	public Recurrence(LocalDate start){
		this.start = start;
	}
	
	private int multiplier = RecurrenceConstants.DEFAULT_MULTIPLIER;
	public int getMultiplier() {return multiplier;}
	public void setMultiplier(int multiplier) {this.multiplier = multiplier;}
	
	private int amount = RecurrenceConstants.DEFAULT_RECURRENCE_AMOUNT;
	public int getAmount() {return amount;}
	public void setAmount(int amount) {this.amount = amount;}
	
	private LocalDate start = null;
	public void setStart(LocalDate start) {this.start = start;}
	public LocalDate getStart() {return start;}
	
	private LocalDate end = null;
	public LocalDate getEnd() {return end;}
	public void setEnd(LocalDate end) {this.end = end;}
	
	public abstract StringBuilder buildSpecificRule();
	
	private StringBuilder buildGeneralRule(){
		StringBuilder sb = new StringBuilder();
		
		//TOOD: amount or end, but not both.
		
		if (amount != RecurrenceConstants.RECURRENCE_AMOUNT_NONE){
			sb.append("COUNT=");
			sb.append(amount);
			sb.append(";");
		}
		
		if (end != null){
			sb.append("UNTIL=");
			sb.append(end.toString(RecurrenceConstants.RFC2445_DATE_FORMAT));
			sb.append(";");
		}
		
		if (multiplier > 1){
			sb.append("INTERVAL=");
			sb.append(multiplier);
			sb.append(";");
		}
		
		return sb;
	}
	
	public LocalDateIterable generateRecurrenceDateIterable(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("RRULE:");
		sb.append(buildSpecificRule());
		sb.append(buildGeneralRule());
		
		try {
			return LocalDateIteratorFactory.createLocalDateIterable(sb.toString(), start, true);
		} catch (ParseException e) {
			return null;
		}
	}
}
