package me.Bambusstock.TimeBan.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bambusstock
 */
public class CommandLineParser {
    
    /**
     * Processes a array of string passed as args to a command.
     * The use of Strings like "Hello World!" often results in a
     * array like ['"Hello ", 'World!"']. This methods is going to
     * combine these Strings.
     * 
     * Strings like '"hello " world"' are considered illegal.
     * Nevertheless a string like '"hello' etc. is ok.
     * 
     * @param args to process
     * 
     * @return a normalized pack of args.
     */
    public static String[] normalizeArgs(String[] args) {
        if(args == null) {
            return null;
        } else if(args.length == 0) {
            return new String[0];
        } else if(args.length == 1) {
            return args;
        }
        
        List<String> stack = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        boolean shrinking = false;
        for(String s : args) {
            
            // if this is not a starting '"' and we're not already shrinking...
            if(!s.startsWith("\"") && !shrinking) {
                stack.add(s);
            } else {
                builder.append(s).append(" ");
                shrinking = true;
            }
            
            // all this should only happen if we already had a starting "
            if(s.endsWith("\"") && shrinking) {
                builder.deleteCharAt(builder.length()-1);
                stack.add(builder.toString());
                builder = new StringBuilder();
                shrinking = false;
            }
        }
        
        // a string like hello "world or hello world" was entered
        // "hello " world" may also be possible but is considered illegal...
        if(shrinking == true) {
            return args;
        }
        
        return stack.toArray(new String[] {});
    }

    /**
     * Gets all options like /cmd test -ra -> [r, a].
     *
     * @param cmdLineArg command line containing options
     *
     * @return a char array containing all options. empty if no options found.
     */
    public static char[] getOptions(String cmdLineArg) {
        char[] result = new char[0];

        if (cmdLineArg == null || cmdLineArg.isEmpty()) {
            return result;
        }

        // avoid "--abc"
        if (cmdLineArg.lastIndexOf('-') == 0) {
            result = cmdLineArg.replace("-", "").toCharArray();
        }

        return result;
    }

    /**
     * Checks if there is a option present.
     *
     * @param cmdLineArg command line containing options
     * @param option option to search for
     *
     * @return true if option available, false if not
     */
    public static boolean isOptionPresent(String cmdLineArg, char option) {
        boolean result = false;

        // avoid "--abc"
        if (cmdLineArg.lastIndexOf('-') == 0) {
            result = cmdLineArg.indexOf(option) >= 1;
        }

        return result;
    }

    /**
     * Extract a list of strings from a command line arg. E.g. - user,user2 ->
     * [user, user2] - user,,,user2 -> [user, user2] - user, ,user2 -> [user,
     * user2] - user -> [user]
     *
     * @param cmdLineArg command line arg to use
     *
     * @return list of strings (no "," and no trailing/heading whitespaces).
     * empty list if nothing found
     */
    public static List<String> getListOfString(String cmdLineArg) {
        if (cmdLineArg == null || cmdLineArg.isEmpty()) {
            return new ArrayList<String>(0);
        }

        String[] splitted = cmdLineArg.split(",");
        List<String> result = new ArrayList<String>(splitted.length);

        for (String s : splitted) {
            String str = s.trim();
            if (!str.isEmpty()) {
                result.add(str);
            }
        }

        return result;
    }

    /**
     * Returns a pretty string from a command line arg. E.g. "hello world!" ->
     * hello world! Trailing/Heading whitespaces will be kept.
     *
     * @param cmdLineArg command line arg to use
     *
     * @return formatted string or <b>null</b> if no " where found
     */
    public static String getPrettyString(String cmdLineArg) {
        String result = null;

        if (cmdLineArg == null || cmdLineArg.isEmpty()) {
            return result;
        }

        String rawCmdLine = cmdLineArg.trim();
        if (rawCmdLine.startsWith("\"") && rawCmdLine.endsWith("\"")) {
            result = rawCmdLine.replace("\"", "");
        }

        return result;
    }
}
