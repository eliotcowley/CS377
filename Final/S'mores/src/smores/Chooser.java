/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smores;

/**
 * Child who is choosing which ingredients to put on table.
 * @author Eliot
 */
public class Chooser extends Thread {
    
    /* synchronizing monitor */
    private Monitor monitor;
    
    /* index of child who will eat */
    private int choice;
    
    /**
     * Constructor for Chooser class.
     * @param monitor Synchronizing monitor.
     */
    public Chooser(Monitor monitor) {
        this.monitor = monitor;
    }
    
    /**
     * Lifecycle of Chooser.
     */
    @Override
    public void run() {
        Child.delay(Child.randomInt());
        while (true) {
            // choose ingredients to put down
            System.out.println("Chooser is choosing ingredients...");
            this.choice = this.choose();
            Child.delay(Child.randomInt());
            // try to put down ingredients
            System.out.println("Chooser is waiting to put down "
                    + "ingredients...");
            this.putDownIngredients();
            Child.delay(Child.randomInt());
        }
    }
    
    /**
     * Try to put down chosen ingredients.
     */
    private void putDownIngredients() {
        this.monitor.putDownIngredients(choice);
    }
    
    /**
     * Choose ingredients to put down.
     * @return int representing index of child who will make s'more.
     */
    private int choose() {
        return this.monitor.choose();
    }
    
}
