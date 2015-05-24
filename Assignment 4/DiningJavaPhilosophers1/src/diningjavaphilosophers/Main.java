package diningjavaphilosophers;

/**
 * This is the main program to demonstrate a solution to the Dining Philosophers
 * problem in Java using threads and synchronized code blocks. This solution has
 * one philosopher pick up their chopsticks in a different order than the others
 * to prevent deadlock.
 * 
 * @author Marc L. Smith/Eliot Cowley
 * @version 1.0
 * @source elcowley/CS377
 */
public class Main
{
    /**
     * The main method for the Main class.
     * @param arg 
     */
    public static void main(String[] arg)
    {
      // Create the philosophers' chopsticks
      Chopsticks chopsticks = new Chopsticks();

      // Create and start the philosophers (threads)
      for (int i = 0; i < 5; i++) {
        new Philosopher(i).start();
      }
    }
}
