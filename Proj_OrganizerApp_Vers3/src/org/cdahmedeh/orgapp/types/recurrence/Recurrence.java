package org.cdahmedeh.orgapp.types.recurrence;

public abstract class Recurrence {
	private int multiplier = RecurrenceConstants.DEFAULT_MULTIPLIER;
	public int getMultiplier() {return multiplier;}
	public void setMultiplier(int multiplier) {this.multiplier = multiplier;}
	
	private int amount = RecurrenceConstants.DEFAULT_RECURRENCE_AMOUNT;
	public int getAmount() {return amount;}
	public void setAmount(int amount) {this.amount = amount;}
}
