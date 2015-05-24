/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diningjavaphilosophers;

/**
 * Individual chopstick in array of Chopsticks.
 * @author elcowley
 */
public class Chopstick {
    
    private boolean available;  // Whether the chopstick is available
    
    /** Constructor for Chopstick class.
     */
    public Chopstick() {
        this.available = true;
    }
    
    /** Check whether the Chopstick is available or not.
     * @return Boolean--true if available, false if not.
     */
    public boolean isAvailable() {
        return this.available;
    }
    
    /** Set the Chopstick to be available or not.
     * @param available True if setting the Chopstick to be available, false if 
     *                  setting it to be unavailable.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
}
