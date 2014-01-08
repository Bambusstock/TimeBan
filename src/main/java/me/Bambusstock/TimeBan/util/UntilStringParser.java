package me.Bambusstock.TimeBan.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UntilStringParser {

    private static final Logger log = Logger.getLogger("Minecraft");

    private static final String[] labels = {"y", "m", "w", "d", "h", "i", "s"};

    private static final Map<String, Pattern> patterns = new LinkedHashMap<String, Pattern>(7);

    private static final Map<String, Integer> fields = new LinkedHashMap<String, Integer>(7);

    static {
        for (String label : labels) {
            Pattern p = Pattern.compile("(\\d{1,})" + label + ".*");
            patterns.put(label, p);
        }

        fields.put("y", Calendar.YEAR);
        fields.put("m", Calendar.MONTH);
        fields.put("w", Calendar.WEEK_OF_YEAR);
        fields.put("d", Calendar.DAY_OF_YEAR);
        fields.put("h", Calendar.HOUR_OF_DAY);
        fields.put("i", Calendar.MINUTE);
        fields.put("s", Calendar.SECOND);
    }

    /**
     * Get the difference of a field between to calendars
     *
     * @param field Field to research
     * @param future Date in future
     * @return Difference as int
     */
    protected static int getFieldDifference(int field, Calendar future) {
        Calendar actual = Calendar.getInstance();
        return future.get(field) - actual.get(field);
    }

    /**
     * Parse a string into a calendar object.
     *
     * @param input Input string like 4d
     *
     * @return calendar object or null if input was null/empty.
     */
    public static Calendar parse(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        
        Calendar result = GregorianCalendar.getInstance();
        for (Entry<String, Pattern> entry : patterns.entrySet()) {
            Matcher m = entry.getValue().matcher(input);
            if (m.matches()) {
                int addTo = Integer.parseInt(m.group(1));
                int field = fields.get(entry.getKey());
                result.add(field, addTo);
                
                // remove found portion otherwise regex is not working... :/
                input = input.replace(m.group(1)+entry.getKey(), "");
            }
        }
        return result;
    }
}
