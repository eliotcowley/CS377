package diningjavaphilosophers;

/**
 * This class implements a dining philosopher.
 *
 * @author Marc L. Smith
 * @version 1.0
 */
public class Philosopher extends Thread {
    /* instance variables */
    private int id;     // Philosopher's unique identifier
    private int left;   // philosopher's left chopstick
    private int right;  // philosopher's right chopstick

    /** Constructor for Philosopher object.
     * @param id Unique identifier for this Philosopher.
     */
    public Philosopher(int id) {
        // initialize instance variables
        this.id = id;
        this.left = id;
        this.right = (id + 1) % Chopsticks.numPhilosophers;
    }

    /** A dining philosopher's behavior is to eat and think -- forever!
     */
    @Override
    public void run() {
        // don't all start in order of creation!
        this.delay(this.randomInt());

        while (true) {

            // pick up forks
            this.getForks();      

            // eat
            System.out.println("Philosopher " + this.id + " eating...");
            this.delay(this.randomInt()); // chew your food!

            // finished eating, so put down forks
            this.putDownForks();
            System.out.println("BURP! (Philosopher " + this.id + ")");

            // think
            System.out.println("Philosopher " + id + " thinking...");
            this.delay(this.randomInt()); // can't rush genius!
        }
    }

    /**
     * Unsafe way to pick up both forks.
     */
    private void getForks() {
        /* to avoid deadlock, have one philosopher (in this case, philosopher 0)
         * pick up their chopsticks in a different order than the others */
        if (this.id == 0) {
            System.out.println("Philosopher " + this.id
                    + " waiting for left fork...");
            synchronized (Chopsticks.getLeft(this.id)) {
                // simulate time to pick up left fork
                this.delay(this.randomInt());
                System.out.println("Philosopher " + this.id
                        + " picked up left fork!");
                System.out.println("Philosopher " + this.id
                        + " waiting for right fork...");
                synchronized (Chopsticks.getRight(this.id)) {
                    // simulate time to pick up right fork
                    this.delay(this.randomInt());
                    System.out.println("Philosopher " + this.id
                            + " picked up right fork!");
                }
            }
        }
        /* other philosophers pick up right chopstick first, then left */
        else {
            System.out.println("Philosopher " + this.id
                    + " waiting for right fork...");
            synchronized (Chopsticks.getRight(this.id)) {
                // simulate time to pick up right fork
                this.delay(this.randomInt());
                System.out.println("Philosopher " + this.id
                        + " picked up right fork!");
                System.out.println("Philosopher " + this.id
                        + " waiting for left fork...");
                synchronized (Chopsticks.getLeft(this.id)) {
                    // simulate time to pick up left fork
                    this.delay(this.randomInt());
                    System.out.println("Philosopher " + this.id
                            + " picked up left fork!");
                }
            }
        }
    }

    /**
     * Philosopher puts down forks.
     */
    private void putDownForks() {
        /* philosopher 0 puts down chopsticks left first, then right */
        if (this.id == 0) {
            Chopsticks.putDownLeft(this.id);
            System.out.println("Philosopher " + this.id + " put down left fork.");
            this.delay(this.randomInt());   // simulate time to put down fork
            Chopsticks.putDownRight(this.id);
            System.out.println("Philosopher " + this.id + " put down right fork.");
            this.delay(this.randomInt());   // simulate time to put down fork
        }
        /* other philosophers put down chopsticks right first, then left */
        else {
            Chopsticks.putDownRight(this.id);
            System.out.println("Philosopher " + this.id + " put down right fork.");
            this.delay(this.randomInt());   // simulate time to put down fork
            Chopsticks.putDownLeft(this.id);
            System.out.println("Philosopher " + this.id + " put down left fork.");
            this.delay(this.randomInt());   // simulate time to put down fork
        }
    }

    /**
     * Returns a random integer.
     * @return Random integer between 1 and 100?
     */
    public int randomInt() {
        double r = Math.random();
        return (int) Math.floor(r * 100) + 1;
    }

    /**
     * Simulates a philosopher pausing for a given amount of time.
     * @param mSec Integer representing milliseconds that philosopher pauses for.
     */
    public void delay(int mSec) {
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException ex) {
        }
    }
}
