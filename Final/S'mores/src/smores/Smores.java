package smores;

/**
 * Name: Eliot Cowley
 * Class: CS-377 Parallel Programming
 * Professor Smith
 * Date: 5/14/2015
 * Final Assignment, Exercise 1
 * 
 * I have structured this program in the following way: There are 3 threads 
 * of the Child class, which represent the children who are holding the
 * ingredients and making s'mores. There is also 1 Chooser thread, which is
 * responsible for picking the ingredients that will be placed on the table.
 * Finally there is a Monitor class that synchronizes the 4 threads.
 * 
 * The Child threads wait for the Chooser thread to choose a Child who will 
 * eat a s'more. The Chooser puts down the ingredients besides the one that 
 * the Child he has chosen is holding. (I implemented it this way because I 
 * figured it would be easier and more efficient to choose one random number 
 * rather than two.) The Child whom the Chooser has chosen picks up the 
 * ingredients, makes his s'more, and eats it. The Chooser waits for the 
 * Child to finish eating, and then picks another random Child, repeating the 
 * cycle.
 * 
 * This solution avoids deadlock using a Monitor with synchronized methods. 
 * Any time a thread needs to access a shared variable, it goes through the 
 * Monitor. In this way, race conditions are avoided and no two threads will 
 * be stuck fighting for the same resource. I cannot say that this solution 
 * is completely fair, however after extensive testing I have found that the 
 * number of s'mores eaten by each child is generally similar and no one 
 * child is generally more greedy than any other.
 * 
 * @author Eliot
 */
public class Smores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // initialize monitor and child threads
        Monitor monitor = new Monitor();
        for (int i = 0; i < Monitor.NUM_EATERS; i++) {
            new Child(i, monitor).start();
        }
        new Chooser(monitor).start();
    }
}
