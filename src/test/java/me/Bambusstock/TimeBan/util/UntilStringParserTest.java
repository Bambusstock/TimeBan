/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.Bambusstock.TimeBan.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bambusstock
 */
public class UntilStringParserTest {
    
    @Test
    public void testStringParse() {
        Calendar result;
        Calendar supposedResult;
        
        // second
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.SECOND, 10);
        result = UntilStringParser.parse("10s");
        assertEquals(supposedResult.get(Calendar.SECOND), result.get(Calendar.SECOND));
        
        // minute
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.MINUTE, 10);
        result = UntilStringParser.parse("10i");
        assertEquals(supposedResult.get(Calendar.MINUTE), result.get(Calendar.MINUTE));
        
        // minute
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.HOUR, 10);
        result = UntilStringParser.parse("10h");
        assertEquals(supposedResult.get(Calendar.HOUR), result.get(Calendar.HOUR));
        
        // day
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.DAY_OF_YEAR, 1);
        result = UntilStringParser.parse("1d");
        assertEquals(supposedResult.get(Calendar.DAY_OF_YEAR), result.get(Calendar.DAY_OF_YEAR));
        
        // week
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.WEEK_OF_YEAR, 10);
        result = UntilStringParser.parse("10w");
        assertEquals(supposedResult.get(Calendar.WEEK_OF_YEAR), result.get(Calendar.WEEK_OF_YEAR));
        
        // month
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.MONTH, 10);
        result = UntilStringParser.parse("10m");
        assertEquals(supposedResult.get(Calendar.MONTH), result.get(Calendar.MONTH));
        
        // year
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.YEAR, 10);
        result = UntilStringParser.parse("10y");
        assertEquals(supposedResult.get(Calendar.YEAR), result.get(Calendar.YEAR));
        
        // minute hour second
        supposedResult = GregorianCalendar.getInstance();
        supposedResult.add(Calendar.SECOND, 10);
        supposedResult.add(Calendar.MINUTE, 10);
        supposedResult.add(Calendar.HOUR, 10);
        result = UntilStringParser.parse("10h10i10s");
        assertEquals(supposedResult.get(Calendar.SECOND), result.get(Calendar.SECOND));
        assertEquals(supposedResult.get(Calendar.MINUTE), result.get(Calendar.MINUTE));
        assertEquals(supposedResult.get(Calendar.HOUR), result.get(Calendar.HOUR));

        System.out.println("Here we go...");
        
        // day week month year
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.DAY_OF_YEAR, 1);
        supposedResult.add(Calendar.WEEK_OF_YEAR, 1);
        supposedResult.add(Calendar.MONTH, 1);
        supposedResult.add(Calendar.YEAR, 1);
        result = UntilStringParser.parse("1y1m1w1d");
        assertEquals(supposedResult.get(Calendar.DAY_OF_YEAR), result.get(Calendar.DAY_OF_YEAR));
        assertEquals(supposedResult.get(Calendar.WEEK_OF_YEAR), result.get(Calendar.WEEK_OF_YEAR));
        assertEquals(supposedResult.get(Calendar.MONTH), result.get(Calendar.MONTH));
        assertEquals(supposedResult.get(Calendar.YEAR), result.get(Calendar.YEAR));
        
        // altogehter now
        supposedResult = Calendar.getInstance();
        supposedResult.add(Calendar.SECOND, 1);
        supposedResult.add(Calendar.MINUTE, 2);
        supposedResult.add(Calendar.HOUR, 3);
        supposedResult.add(Calendar.DAY_OF_YEAR, 4);
        supposedResult.add(Calendar.WEEK_OF_YEAR, 5);
        supposedResult.add(Calendar.MONTH, 6);
        supposedResult.add(Calendar.YEAR, 7);
        result = UntilStringParser.parse("7y6m5w4d3h2i1s");
        assertEquals(supposedResult.get(Calendar.SECOND), result.get(Calendar.SECOND));
        assertEquals(supposedResult.get(Calendar.MINUTE), result.get(Calendar.MINUTE));
        assertEquals(supposedResult.get(Calendar.HOUR), result.get(Calendar.HOUR));
        assertEquals(supposedResult.get(Calendar.DAY_OF_YEAR), result.get(Calendar.DAY_OF_YEAR));
        assertEquals(supposedResult.get(Calendar.WEEK_OF_YEAR), result.get(Calendar.WEEK_OF_YEAR));
        assertEquals(supposedResult.get(Calendar.MONTH), result.get(Calendar.MONTH));
        assertEquals(supposedResult.get(Calendar.YEAR), result.get(Calendar.YEAR));

        // null
        result = UntilStringParser.parse(new String());
        assertNull(result);
        
        result = UntilStringParser.parse("");
        assertNull(result);
    }
    
}
