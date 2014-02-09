package me.Bambusstock.TimeBan.util;

import org.bukkit.util.ChatPaginator;

/**
 *
 * @author Bambusstock
 */
public class TerminalUtil {
    
    private TerminalUtil() {
    }
    
    
    private static final int LINE_WIDTH = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
    
    private static final String horizontalLine = getLine(LINE_WIDTH, '-');
    
    /**
     * Create a line consisting of a char.
     * @param length line length
     * @param c character so build line
     * @return
     */
    protected static String getLine(int length, char c) {
        StringBuilder b = new StringBuilder();
        for(;length > 0; length--) {
            b.append(c);
        }
        
        return b.toString();
    }
    
    /**
     * Create a new headline.
     * @param text
     * @return 
     */
    public static String createHeadline(String text) {
        if(text == null || text.isEmpty()) {
            return createHorizontalLine();
        }
        
        int missingChars = LINE_WIDTH - (text.length() + 2); // reserve 2 whitespaces
        if(missingChars % 2 == 0) {
            String start = getLine(missingChars / 2, '-');
            String end = getLine(missingChars / 2, '-');
            return start + " " + text + " " + end;
        } else {
            String start = getLine((missingChars / 2) - 1, '-');
            String end = getLine((missingChars / 2) + 2, '-');
            return start + " " + text + " " + end;
        }
    }
    
    public static String createHeadline(String text, int page, int maxPage) {
        String headline = text + " - (" + page + " / " + maxPage + ")";
        return createHeadline(headline);
    }
    
    public static String createHorizontalLine() {
        return horizontalLine;
    }
}
