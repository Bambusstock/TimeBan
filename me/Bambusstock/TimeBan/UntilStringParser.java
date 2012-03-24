package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.logging.Logger;


public class UntilStringParser
{
	Logger log = Logger.getLogger("Minecraft");
	public String text;
	public Calendar calendar;
	
	public UntilStringParser(String input) {
	}
	
	public UntilStringParser(Calendar input) {
		if(input.after(Calendar.getInstance())) {
			this.parseCalenderInput(input);
		}
		else {
			//System.("Date in future.");
		}
	}
	
	protected int getFieldDifference(int field, Calendar future) {
		Calendar actual = Calendar.getInstance();
		return future.get(field) - actual.get(field);
	}
	
	/**
	 * Parse a calender object into a string like ´1y2m3w4d5h6s´
	 * @param input A calendar in future
	 */
	protected void parseCalenderInput(Calendar input) {
		String output = "";
		int[] fields = {Calendar.YEAR, Calendar.MONTH, Calendar.WEEK_OF_YEAR, Calendar.DAY_OF_YEAR, Calendar.HOUR_OF_DAY, Calendar.SECOND};
		String[] labels = {"y", "m", "w", "d", "h", "s"};
		for(int i = 0; i < fields.length; i++) {
			int diff = this.getFieldDifference(fields[i], input);
			log.info("Differz von " + labels[i] + ": " + diff);
			//if(diff > 0) {
				output = output.concat(diff + labels[i]);
				input.set(fields[i], input.get(fields[i]) - diff);
			//}
		}
		this.text = output;
	}
	
	protected void parseUserInput() {
		
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public String getText() {	
		return this.text;
	}

}