/* CS-377: Parallel Programming
 * Assignment 4: Dining Java Philosophers, Waiter Implementation with 
 * Synchronized Methods
 * 4/13/15
 */

package diningjavaphilosophers2;

/**
 * This class is an implementation of the table from the Dining 
 * Philosophers problem.  The activities of obtaining and releasing 
 * forks are implemented using synchronized methods -- Java's 
 * version of approximating a monitor.  Philosophers wait() for forks
 * on the table object's implicit wait queue; and upon releasing 
 * their forks, signal any waiting philosophers via notifyAll().
 * 
 * @author Marc L. Smith
 * @version 1.0
 */
public class TableMon
{
    // an array of "forks"
    private int[] fork;

   /**
    * Table Constructor.
    */
    public TableMon()
    {
      /*
       * Allocate fork array (need 5 forks)
       */
      fork = new int[6];  // fork "0" not used...
      for (int i = 1; i <= 5; i++) {
        fork[i] = 1;      // initially each fork is available
      }
    }

   /*
    * Synchronized method to pick up left fork of given philosopher.
    * Notice the use of wait() to avoid busy waiting. This queues
    * the philosopher on the table's implicit condition variable.
    */
    public synchronized void getLeftFork(int id) {
      // wait for left fork, if necessary, then pick up
      while ( fork[id] == 0 ) {
        System.out.println("Philosopher " + id + " waiting for left fork...");
        try { wait(); }
            catch (InterruptedException ex) {}
      }
      fork[id] = 0;
      System.out.println("Philosopher " + id + " picked up left fork!");
    }

   /*
    * Synchronized method to pick up right fork of given philosopher.
    * Notice the use of wait() to avoid busy waiting. This queues
    * the philosopher on the table's implicit condition variable.
    */
    public synchronized void getRightFork(int id) {
      // wait for right fork, if necessary, then pick up
      while ( fork[id%5 + 1] == 0 ) {
        System.out.println("Philosopher " + id + " waiting for right fork...");
        try { wait(); }
            catch (InterruptedException ex) {}
      }
      fork[id%5 + 1] = 0;
      System.out.println("Philosopher " + id + " picked up right fork!");
    }

   /* 
    * Effect of this method: philosopher puts down left fork.
    * Since method is synchronized, the body treats access to
    * fork as critical section.
    */
    public synchronized void putDownLeftFork(int id) 
    {
        // put down left fork
        fork[id] = 1;
        System.out.println("Philosopher " + id + " put down left fork.");

        // give the other philosophers a chance to eat
        notifyAll();
    }
	
   /* 
    * Effect of this method: philosopher puts down left fork.
    * Since method is synchronized, the body treats access to
    * fork as critical section.
    */
    public synchronized void putDownRightFork(int id) 
    {
        // put down right fork
        fork[id%5 + 1] = 1;
        System.out.println("Philosopher " + id + " put down right fork.");

        // give the other philosophers a chance to eat
        notifyAll();
    }
}
