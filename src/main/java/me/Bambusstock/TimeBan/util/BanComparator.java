package me.Bambusstock.TimeBan.util;

import java.util.Comparator;

/**
 *
 * @author Bambusstock
 */
public class BanComparator implements Comparator<Ban> {
    
    private boolean reverse;
    
    public BanComparator() {
        reverse = false;
    }
    
    public BanComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(Ban o1, Ban o2) {
        int result = o1.getUntil().compareTo(o2.getUntil());
        if(!reverse) {
            return result;
        } else {
            return result * -1;
        }
    }
    
}
