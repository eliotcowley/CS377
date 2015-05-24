/*
 * CS-377: Parallel Programming
 * Assignment 4: Dining Java Philosophers--Using synchronized methods and waiter
 * monitor
 * 4/13/2015
 */
package diningjavaphilosophers2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Waiter monitor class. Seats philosophers at table.
 * @author elcowley
 */
public class WaiterMon {
    
    // number of philosophers currently seated
    private int numSeated; 
    // number of seats at table (number of philosophers - 1)
    private static final int NUM_SEATS = Main.NUM_PHILOSOPHERS - 1;
    
    /**
     * Constructor for WaiterMon class.
     */
    public WaiterMon() {
        
    }
    
    /**
     * Philosopher sits down at table. Synchronized so that only one philosopher
     * can sit down at a time.
     * 
     * @param id Number of philosopher to sit down.
     */
    public synchronized void sitDown(int id) {
        if (this.numSeated >= NUM_SEATS) { // if all seats are taken
            try {
                wait(); // wait for a seat to be empty
            } catch (InterruptedException ex) {
                Logger.getLogger(WaiterMon.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        }
        this.numSeated++;
    }
    
    /**
     * Philosopher stands up and leaves table. Synchronized so that only one 
     * philosopher can stand up at a time.
     * 
     * @param id Number of philosopher to stand up.
     */
    public synchronized void standUp(int id) {
        this.numSeated--;
        notifyAll(); // notify other philosophers that a seat is available
    }
    
}
