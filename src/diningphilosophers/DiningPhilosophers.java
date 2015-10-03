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
    public static void main(String[] args) throws InterruptedException
    {
        //CHANGE HERE
        final int size = 3; //number of philosophers and chopsticks
        final int iteration = 300;
        final int thinkingTime = 100;
        final int afterPickingLeftTime = 5;
        final int eatingTime = 100;
        final int afterReleaseLeftTime = 10;

        Table tab;
        tab = new Table(size);
        for (int i = 0; i < size; i++) {
            Thread th = new Thread(new Philosopher(i, tab, iteration,thinkingTime, afterPickingLeftTime, eatingTime, afterReleaseLeftTime));
            th.start();
        }
        while (Thread.activeCount() > 1){
            Thread.sleep(2000);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("the Result Above is for Normal Table Class");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        // do the same JOB Above but with ADV Class
        AdvTable advTab;
        advTab = new AdvTable(size);
        for (int i = 0; i < size; i++) {
            Thread th = new Thread(new Philosopher(i, advTab, iteration,thinkingTime, afterPickingLeftTime, eatingTime, afterReleaseLeftTime));
            th.start();
        }
        while (Thread.activeCount() > 1){
            Thread.sleep(2000);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("the Result Above is for ADV Table Class");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
