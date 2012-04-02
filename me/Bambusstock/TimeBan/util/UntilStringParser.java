package me.Bambusstock.TimeBan.util;

import java.util.Calendar;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UntilStringParser
{
	Logger log = Logger.getLogger("Minecraft");
	public String text;
	public Calendar calendar;
	
	public UntilStringParser(String input) {
		this.parseUserInput(input);
		this.text = input;
	}
	
	public UntilStringParser(Calendar input) {
		if(input.after(Calendar.getInstance())) {
			this.parseCalenderInput(input);
		}
	}
	
	/**
	 * Get the difference of a field between to calendars
	 * @param field Field to research
	 * @param future Date in future
	 * @return Difference as int
	 */
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
			output = output.concat(diff + labels[i]);
			input.add(fields[i], diff);
		}
		this.text = output;
	}
	
	/**
	 * Parse user input into calendar object.
	 * @param input The user input
	 */
	protected void parseUserInput(String input) {
		Calendar output = Calendar.getInstance();
		int[] fields = {Calendar.YEAR, Calendar.MONTH, Calendar.WEEK_OF_YEAR, Calendar.DAY_OF_YEAR, Calendar.HOUR_OF_DAY, Calendar.SECOND};
		String[] labels = {"y", "m", "w", "d", "h", "s"};
		for(int i = 0; i < fields.length; i++) {
			Pattern p = Pattern.compile("(\\d{1,})" + labels[i] + ".*");
			Matcher m = p.matcher(input);
			if(m.matches()) {
				output.add(fields[i], Integer.parseInt(m.group(1)));
				input = input.replace(m.group(1) + labels[i], ""); // remove so that the regexpr works
				if(input.length() == 0) break;
			}
		}
		this.calendar = output;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public String getText() {	
		return this.text;
	}

}