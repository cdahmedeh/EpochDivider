package org.cdahmedeh.orgapp.types.recurrence;

import java.text.ParseException;
import java.util.ArrayList;

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
	
	private ArrayList<LocalDate> exceptions = new ArrayList<>();
	public void addExceptions(LocalDate date) {exceptions.add(date);}
	
	public abstract StringBuilder buildSpecificRule();
	
	private StringBuilder buildExceptionRule() {
		StringBuilder sb = new StringBuilder();
		if (!exceptions.isEmpty()){
			sb.append("EXDATE:");
			for (LocalDate ex: exceptions) {sb.append(ex.toString(RecurrenceConstants.RFC2445_DATE_FORMAT)); sb.append(",");} 
		}
		return sb;
	}
	
	private StringBuilder buildGeneralRule(int pAmount, LocalDate pEnd, int pMultiplier){
		StringBuilder sb = new StringBuilder();
		if (pAmount != RecurrenceConstants.RECURRENCE_AMOUNT_NONE){
			sb.append("COUNT="); sb.append(amount);	sb.append(";");
		}
		if (pEnd != null){
			sb.append("UNTIL="); sb.append(end.toString(RecurrenceConstants.RFC2445_DATE_FORMAT)); sb.append(";");
		}
		if (pMultiplier > 1){
			sb.append("INTERVAL="); sb.append(multiplier); sb.append(";");
		}
		return sb;
	}
	
	public LocalDateIterable generateRecurrenceDateIterable(){
		StringBuilder sb = new StringBuilder();

		sb.append(buildExceptionRule());
		
		sb.append("\n");
		
		sb.append("RRULE:");
		sb.append(buildSpecificRule());
		sb.append(buildGeneralRule(amount, end, multiplier));
		
		try {
			return LocalDateIteratorFactory.createLocalDateIterable(sb.toString(), start, true);
		} catch (ParseException e) {
			//TODO: Shouldn't catch exception
			return null;
		}
	}
}
