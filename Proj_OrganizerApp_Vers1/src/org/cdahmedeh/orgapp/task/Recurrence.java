package org.cdahmedeh.orgapp.task;

import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.LocalDate;

public class Recurrence implements Serializable {
	private static final long serialVersionUID = 4078593070078285527L;
	
	private RecurrenceFrequency freq = RecurrenceFrequency.NONE;
	public RecurrenceFrequency getFreq() {return freq;}
	public void setFreq(RecurrenceFrequency freq) {this.freq = freq;}
	
	public int mult = 1;
	public int getMult() {return mult;}
	public void setMult(int mult) {this.mult = mult;}
	
	public int amount = 0;
	public void setAmount(int amount) {this.amount = amount;}
	public int getAmount() {return amount;}

	private LocalDate until = null;
	public boolean endsUntil(){return until != null;}
	public LocalDate getUntil() {return until;}
	public void setUntil(LocalDate until) {this.until = until;}
	
	private ArrayList<LocalDate> exceptions = new ArrayList<>();
	public ArrayList<LocalDate> getExceptions() {return exceptions;}
	public void addException(LocalDate exception) {this.exceptions.add(exception);}
	public boolean isException(LocalDate exception){return this.exceptions.contains(exception);}
	
	public Recurrence copy(){
		Recurrence rec = new Recurrence();
		
		rec.setFreq(this.getFreq());
		rec.setMult(this.getMult());
		rec.setAmount(this.getAmount());
		rec.setUntil(this.getUntil());
		
		for (LocalDate ex: this.getExceptions()){
			rec.addException(ex);
		}
		
		return rec;
	}
}
