//Simulation of the dining philosopher problem
package diningphilosophers;

/**
 *
 * @author jof
 */
public class DiningPhilosophers
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //CHANGE HERE
        int size = 11; //number of philosophers and chopsticks
        //AdvTable tab = new AdvTable(size);
        Table tab = new Table(size);
        for (int i = 0; i < size; i++) {
            Thread th = new Thread(new Philosopher(i, tab));
            th.start();
        }
    }
}
