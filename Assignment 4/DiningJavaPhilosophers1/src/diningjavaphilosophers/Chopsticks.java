/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diningjavaphilosophers;

/**
 * Object containing all the individual Chopstick objects.
 * @author elcowley
 */
public class Chopsticks {
    
    public static int numPhilosophers; // number of Philosopher objects
    
    public static Chopstick[] chopsticks; // array of Chopstick objects
    
    /**
     * Constructor for Chopsticks object.
     */
    public Chopsticks() {
        numPhilosophers = 5; // 5 philosophers in this simulation
        // instantiate Chopsticks array
        chopsticks = new Chopstick[numPhilosophers];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick();
        }
    }
    
    /**
     * Get the left Chopstick of Philosopher id.
     * @param id The number of Philosopher that we are checking.
     * @return The Chopstick object to the left of Philosopher id.
     */
    public static Chopstick getLeft(int id) {
        chopsticks[id].setAvailable(false);
        return chopsticks[id];
    }
    
    /**
     * Get the right Chopstick of Philosopher id.
     * @param id The number of Philosopher that we are checking.
     * @return The Chopstick object to the right of Philosopher id.
     */
    public static Chopstick getRight(int id) {
        chopsticks[(id + 1) % numPhilosophers].setAvailable(false);
        return chopsticks[(id + 1) % numPhilosophers];
    }
    
    /**
     * Put down the left Chopstick of Philosopher id.
     * @param id The number of Philosopher that we are referencing.
     * @return The Chopstick to the left of Philosopher id.
     */
    public static Chopstick putDownLeft(int id) {
        chopsticks[id].setAvailable(true);
        return chopsticks[id];
    }
    
    /**
     * Put down the right Chopstick of Philosopher id.
     * @param id The number of Philosopher that we are referencing.
     * @return The Chopstick to the right of Philosopher id.
     */
    public static Chopstick putDownRight(int id) {
        chopsticks[(id + 1) % numPhilosophers].setAvailable(true);
        return chopsticks[(id + 1) % numPhilosophers];
    }
    
}
