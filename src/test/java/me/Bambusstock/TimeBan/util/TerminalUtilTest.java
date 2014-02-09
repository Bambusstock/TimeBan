package me.Bambusstock.TimeBan.util;

import org.bukkit.util.ChatPaginator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ferenc
 */
public class TerminalUtilTest {
    
    private static final int LINE_WIDTH = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
    
    public TerminalUtilTest() {
    }
    
    @Test
    public void testHorizontalLine() {
        assertEquals(LINE_WIDTH, TerminalUtil.createHorizontalLine().length());
    }
    
    @Test
    public void testHeadline() {
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline("ABC").length());
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline("ABCD").length());
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline("").length());
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline(null).length());
        
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline("ABC", 1, 10).length());
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline("ABCD", 1, 10).length());
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline("", 1, 10).length());
        assertEquals(LINE_WIDTH, TerminalUtil.createHeadline(null, 1, 10).length());
    }
}
