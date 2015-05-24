/* CS-377: Parallel Programming
 * Assignment 4: Dining Java Philosophers with Waiter Monitor and Synchronized 
 * Methods
 * 4/13/2015
 */

package diningjavaphilosophers2;

/**
 * This is the main program to demonstrate 
 * a monitor solution to the Dining Philosophers 
 * problem in Java.  It uses threads and synchronized methods, and a waiter who
 * seats at most 4 philosophers at the table at a time to avoid deadlock.
 * 
 * @author Eliot Cowley
 * @version 1.0
 */
public class Main
{
    
    // Constant representing the number of philosophers in this simulation
    public static final int NUM_PHILOSOPHERS = 5;
    
    /**
     * The main method for the Main class.
     */
    public static void main(String[] arg)
    {
      // Create the philosophers' table (the monitor) and the waiter
      TableMon table = new TableMon();
      WaiterMon waiter = new WaiterMon();

      // Create and start the philosophers (threads)
      for (int i=1; i <= NUM_PHILOSOPHERS; i++) {
        new Philosopher(i, table, waiter).start();
      }
    }
}
