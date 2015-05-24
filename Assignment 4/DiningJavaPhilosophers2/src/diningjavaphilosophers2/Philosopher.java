/* CS-377: Parallel Programming
 * Assignment 4: Dining Java Philosophers, Waiter Implementation with 
 * Synchronized Methods
 * 4/13/2015
 */

package diningjavaphilosophers2;

/**
 * This class implements a dining philosopher.
 * 
 * @author Eliot Cowley
 * @version 1.0
 */
public class Philosopher extends Thread
{
    // instance variables 
    private int id;             // Philosopher's unique identifier
    private TableMon table;     // Table where philosopher eats
    private WaiterMon waiter;   // Waiter that seats philosophers at table

    /**
     * Constructor for Philosopher objects.
     * 
     * @param id Integer representing number of philosopher.
     * @param table Table monitor that philosophers are seated at.
     * @param waiter Waiter monitor that seats philosophers.
     */
    public Philosopher(int id, TableMon table, WaiterMon waiter)
    {
      // initialize instance variables
      this.id = id;
      this.table = table;
      this.waiter = waiter;
    }


    /*
     * A dining philosopher's behavior 
     * is to eat and think -- forever!
     */ 
    @Override
    public void run()
    {
      // don't all start in order of creation!
      this.delay( this.randomInt() );
	  
      while (true) {
          
        // try to sit at table
        System.out.println("Philosopher " + this.id + 
                " is waiting for a seat...");
        this.waiter.sitDown(this.id);
        System.out.println("Philosopher " + this.id + " has been seated.");

        // pick up forks
        this.getForks();      // unsafe!

        // eat
        System.out.println("Philosopher " + this.id + " eating...");
        this.delay( this.randomInt() ); // chew your food!

        // finished eating, so put down forks
        this.putDownForks();
        System.out.println("BURP! (Philosopher " + this.id + ")");
        
        // leave table
        this.waiter.standUp(this.id);
        System.out.println("Philosopher " + this.id + " has left the table.");

        // think
        System.out.println("Philosopher " + id + " thinking...");
        this.delay( this.randomInt() ); // can't rush genius!
      }
    }

    /**
     * Pick up both forks.
     */
    private void getForks()
    {
      this.table.getLeftFork(this.id);
      this.delay( this.randomInt() );   // simulate time to pick up fork
      this.table.getRightFork(this.id);
      this.delay( this.randomInt() );   // simulate time to pick up fork
    }
	
    /**
     * Philosopher puts down forks.
     */
    private void putDownForks()
    {
      this.table.putDownLeftFork(this.id);
      this.delay( this.randomInt() );   // simulate time to put down fork
      this.table.putDownRightFork(this.id);
      this.delay( this.randomInt() );   // simulate time to put down fork
    }

    /**
     * Returns a random integer.
     */
    public int randomInt() {
      double r = Math.random();
      return (int) Math.floor( r * 100 ) + 1;
    }

    /**
     * Simulates a philosopher pausing for a given amount of time.
     */
    public void delay(int mSec) {
      try {
        Thread.sleep(mSec);
      } catch (InterruptedException ex) {}
    }
}
