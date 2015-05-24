package smores;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Monitor to synchronize the placing down and picking up of ingredients.
 * @author Eliot
 */
public class Monitor {
    
    /* index of child who will eat */
    public int eater;
    
    /* whether ingredients are on table */
    private boolean ingredientsOnTable;
    
    /* array of ingredients on table */
    private int[] ingredients;
    
    /* number of children who want to eat */
    public static final int NUM_EATERS = 3;
    
    /**
     * Constructor for Monitor class.
     */
    public Monitor() {
        this.ingredientsOnTable = false;
        this.ingredients = new int[NUM_EATERS-1];
    }
    
    /**
     * Put down the ingredients of the two other children besides the eater.
     * @param eater The child who will not put down their ingredient.
     */
    public synchronized void putDownIngredients(int eater) {
        /* wait for table to be clear */
        while (this.ingredientsOnTable) { 
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        }
        /* assign ingredients to array */
        int index = 0;
        for (int i = 0; i < Monitor.NUM_EATERS; i++) {
            if (i != eater) {
                this.ingredients[index] = i;
                String name = getName(this.ingredients[index]);
                System.out.println("Chooser put down the " + name);
                index++;
            }
        }
        this.ingredientsOnTable = true;
        notifyAll();
    }
    
    /**
     * The child whose ingredient is not on the table picks up the 
     * ingredients, makes a s'more, and eats it.
     * @param eater Index of child who will make s'more.
     */
    public synchronized void pickUpIngredients(int eater) {
        // wait for it to be eater's turn and ingredients to be on table
        while ((eater != this.eater) || (!this.ingredientsOnTable)) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        }
        // pick up ingredients
        for (int i = 0; i < this.ingredients.length; i++) {
            int ingredient = this.ingredients[i];
            System.out.println("Child " + eater + " picked up the " + 
                    getName(ingredient));
        }
        this.ingredientsOnTable = false;
        System.out.println("Child " + eater + " is making his s'more");
        notifyAll();
    }
    
    /**
     * Choose a child who will eat his s'more this turn.
     * @return int representing index of child who will eat this turn.
     */
    public synchronized int choose() {
        // wait for table to be clear
        if (this.ingredientsOnTable) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        }
        Random random = new Random();
        /* random int between 0 and 2 */
        int choice = random.nextInt(Monitor.NUM_EATERS); 
        this.eater = choice;
        notifyAll();
        return choice;
    }
    
    /**
     * Returns the name of the ingredient represented by i.
     * @param i The index of the ingredient.
     * @return The name of the ingredient.
     */
    public String getName(int i) {
        switch (i) {
            case 0: {
                return "graham crackers";
            }
            case 1: {
                return "marshmallows";
            }
            case 2: {
                return "chocolate";
            }
            default: {
                return "null";
            }
        }
    }
    
}
