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
}
