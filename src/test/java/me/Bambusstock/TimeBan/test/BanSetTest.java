/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.Bambusstock.TimeBan.test;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import me.Bambusstock.TimeBan.util.Ban;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ferenc
 */
public class BanSetTest {
    
    Map<String, Ban> set = new TreeMap<String, Ban>();
    
    public BanSetTest() {
    }
    
    @Before
    public void setUp() {
        Random r = new Random();
        String[] names = {"abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx", "yz"};
        String[] addTo = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        
        int namesCnt = 0;
        int addToCnt = 0;
        
        //TimeBan tb = new TimeBan();
        for(int i = 0; i <= 1000; i++) {
            String name = names[namesCnt] + addTo[addToCnt];
            Ban b = new Ban(null, name, Calendar.getInstance(), "test");
            set.put(name, b);
            
            namesCnt++;
            if(namesCnt == names.length-1) {
                namesCnt = 0;
                addToCnt++;
                if(addToCnt == addTo.length) {
                    addToCnt = 0;
                }
            }
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPerformance() {
        set.get("abcd");
        assertTrue(true);
    }
}
