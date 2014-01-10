/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.Bambusstock.TimeBan.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bambusstock
 */
public class CommandLineParserTest {

    @Test
    public void testOptionMethods() {
        String correctCmdLine = "-ratest";
        String wrongCmdLine = "ratest";

        // Check if works as we want it to work...
        char[] supposedResult = {'r', 'a', 't', 'e', 's', 't'};
        char[] result = CommandLineParser.getOptions(correctCmdLine);
        assertArrayEquals(supposedResult, result);

        assertTrue(CommandLineParser.isOptionPresent(correctCmdLine, 'r'));
        assertTrue(CommandLineParser.isOptionPresent(correctCmdLine, 'a'));
        assertTrue(CommandLineParser.isOptionPresent(correctCmdLine, 't'));
        assertTrue(CommandLineParser.isOptionPresent(correctCmdLine, 'e'));
        assertTrue(CommandLineParser.isOptionPresent(correctCmdLine, 's'));
        assertTrue(CommandLineParser.isOptionPresent(correctCmdLine, 't'));
        assertFalse(CommandLineParser.isOptionPresent(correctCmdLine, '-'));

        // Check if not work(and should not...)
        result = CommandLineParser.getOptions(wrongCmdLine);
        assertTrue(result.length == 0);

        assertFalse(CommandLineParser.isOptionPresent(wrongCmdLine, 'r'));
        assertFalse(CommandLineParser.isOptionPresent(wrongCmdLine, 'a'));
        assertFalse(CommandLineParser.isOptionPresent(wrongCmdLine, 't'));
        assertFalse(CommandLineParser.isOptionPresent(wrongCmdLine, 'e'));
        assertFalse(CommandLineParser.isOptionPresent(wrongCmdLine, 's'));
        assertFalse(CommandLineParser.isOptionPresent(wrongCmdLine, 't'));
        
        // Others
        result = CommandLineParser.getOptions("");
        assertTrue(result.length == 0);
        
        result = CommandLineParser.getOptions(null);
        assertTrue(result.length == 0);
    }

    @Test
    public void testStringFromList() {
        List<String> supposedResultA = new ArrayList<String>(3);
        supposedResultA.add("userA");
        supposedResultA.add("userB");
        supposedResultA.add("userC");

        List<String> supposedResultB = new ArrayList<String>(1);
        supposedResultB.add("userA");

        List<String> supposedResultC = new ArrayList<String>(2);
        supposedResultC.add("userA");
        supposedResultC.add("userB");

        // Test result a
        List<String> result = CommandLineParser.getListOfString("userA, userB, userC");
        assertEquals(supposedResultA, result);

        result = CommandLineParser.getListOfString("userA,userB,userC");
        assertEquals(supposedResultA, result);
        
        result = CommandLineParser.getListOfString("userA,userB, , userC");
        assertEquals(supposedResultA, result);
        
        // Test result b
        result = CommandLineParser.getListOfString("userA");
        assertEquals(supposedResultB, result);
        
        result = CommandLineParser.getListOfString(" userA ");
        assertEquals(supposedResultB, result);
        
        result = CommandLineParser.getListOfString(", userA ");
        assertEquals(supposedResultB, result);
        
        // Test result c
        result = CommandLineParser.getListOfString("userA, ,userB");
        assertEquals(supposedResultC, result);
        
        result = CommandLineParser.getListOfString("userA, userB, ,");
        assertEquals(supposedResultC, result);
        
        result = CommandLineParser.getListOfString("userA,,,userB");
        assertEquals(supposedResultC, result);
        
        // Others
        result = CommandLineParser.getListOfString("");
        assertEquals(Collections.emptyList(), result);
        
        result = CommandLineParser.getListOfString(null);
        assertEquals(Collections.emptyList(), result);
    }
    
    @Test
    public void testPrettyString() {
        String supposedResultA = "Hello World!";
        String supposedResultB = " Hello World! ";
        
        String result = CommandLineParser.getPrettyString("\"Hello World!\"");
        assertEquals(supposedResultA, result);
        
        result = CommandLineParser.getPrettyString("Hello World!");
        assertNull(result);
        
        result = CommandLineParser.getPrettyString("\" Hello World! \" ");
        assertEquals(supposedResultB, result);
        
        result = CommandLineParser.getPrettyString("\"Hello World!\",");
        assertNull(result);
        
        result = CommandLineParser.getPrettyString("");
        assertNull(result);
        
        result = CommandLineParser.getPrettyString(null);
        assertNull(result);
    }
    
    @Test
    public void testNormalizeArgs() {
        String[] argsA = {"ban", "someone", "2d", "\"This", "is", "a", "reason!\""};
        String[] supposedResultA = {"ban", "someone", "2d", "\"This is a reason!\""};
        
        String[] argsB = {"ban", "someone", "2d", "\"bla\""};
        String[] supposedResultB = {"ban", "someone", "2d", "\"bla\""};
        
        String[] argsC = {"ban"};
        
        String[] argsD = {"\"start", "message\""};
        String[] supposedResultD = {"\"start message\""};
        
        String[] argsE = {"ban", "incomplete", "\"ok", "that"};
        
        String[] argsF = {"ban", "something", "finished\""};
        
        String[] argsG = {"ban", "someo\"ne"};
        
        
        // Test predefined results...
        String[] result = CommandLineParser.normalizeArgs(argsA);
        assertArrayEquals(supposedResultA, result);
        
        result = CommandLineParser.normalizeArgs(argsB);
        assertArrayEquals(supposedResultB, result);
        
        result = CommandLineParser.normalizeArgs(argsC);
        assertArrayEquals(argsC, result);
        
        result = CommandLineParser.normalizeArgs(argsD);
        assertArrayEquals(supposedResultD, result);
        
        result = CommandLineParser.normalizeArgs(argsE);
        assertArrayEquals(argsE, result);
        
        result = CommandLineParser.normalizeArgs(argsF);
        assertArrayEquals(argsF, result);
        
        result = CommandLineParser.normalizeArgs(argsG);
        assertArrayEquals(argsG, result);
        
        // Test null and empty arrays        
        result = CommandLineParser.normalizeArgs(new String[0]);
        assertArrayEquals(new String[0], result);
        
        result = CommandLineParser.normalizeArgs(null);
        assertNull(result);
    }
}
