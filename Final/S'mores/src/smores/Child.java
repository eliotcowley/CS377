/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smores;

/**
 * A child holding an infinite amount of an ingredient and waiting for the 
 * other ingredients to be on the table so he can make a s'more and eat it.
 * @author Eliot Cowley
 */
public class Child extends Thread {
    
    /* index of this child */
    private int index;
    
    /* the monitor synchronizing the threads */ 
    private Monitor monitor;
    
    /* number of s'mores this child has eaten */
    public int numEaten;
    
    /**
     * Constructor for Child class.
     * @param index Index of this child.
     * @param monitor Monitor synchronizing this child.
     */
    public Child(int index, Monitor monitor) {
        this.index = index;
        this.monitor = monitor;
        this.numEaten = 0;
    }
    
    /**
     * Lifecycle of this child. Tries to pick up ingredients, makes his 
     * s'more, eats it, and repeats.
     */
    @Override
    public void run() {
        /* don't all start in order of creation */
        Child.delay(Child.randomInt());
        while (true) {
            System.out.println("Child " + this.index + " is waiting for the "
                    + "other ingredients...");
            this.pickUpIngredients();
            /* deal with plurality */
            String addS;
            if (this.numEaten > 1) {
                addS = "s";
            }
            else {
                addS = "";
            }
            System.out.println("Child " + this.index + " has eaten " + 
                    this.numEaten + " s'more" + addS);
            Child.delay(Child.randomInt());
        }
    }
    
    /**
     * Have this child try to pick up ingredients on table.
     */
    public void pickUpIngredients() {
        this.monitor.pickUpIngredients(this.index);
        this.numEaten++;
    }
    
    /**
     * Delay the thread for a certain amount of milliseconds.
     * @param mSec Number of milliseconds to delay thread for.
     */
    public static void delay(int mSec) {
      try {
        Thread.sleep(mSec);
      } catch (InterruptedException ex) {}
    }
    
    /**
     * Returns a random integer.
     */
    public static int randomInt() {
      double r = Math.random();
      return (int) Math.floor( r * 100 ) + 1;
    }
    
}
