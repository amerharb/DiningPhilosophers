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
        final int size = 30; //number of philosophers and chopsticks
        final int iteration = 10;
        final int thinkingTime = 100;
        final int afterPickingLeftTime = 100;
        final int eatingTime = 100;
        final int afterReleaseLeftTime = 10;
        
        Table tab;
        tab = new Table(size);
        for (int i = 0; i < size; i++) {
            Thread th = new Thread(new Philosopher(i, tab, iteration, thinkingTime, afterPickingLeftTime, eatingTime, afterReleaseLeftTime));
            th.start();
        }
        
        while (Thread.activeCount() > 1) {
            Thread.sleep(4000);
        }
        
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("the Result Above is for Normal Table Class for the folowing input");
        System.out.println("size = " + size);
        System.out.println("itreation = " + iteration);
        System.out.println("thinkingTime = " + thinkingTime);
        System.out.println("afterPickingLeftTime = " + afterPickingLeftTime);
        System.out.println("eatingTime =  " + eatingTime);
        System.out.println("afterReleaseLeftTime = " + afterReleaseLeftTime);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        Philosopher.resetAllPeriods();
        AdvTable advTab;
        advTab = new AdvTable(size);
        for (int i = 0; i < size; i++) {
            Thread th = new Thread(new Philosopher(i, advTab, iteration, thinkingTime, afterPickingLeftTime, eatingTime, afterReleaseLeftTime));
            th.start();
        }
        
        while (Thread.activeCount() > 1) {
            Thread.sleep(4000);
        }
        
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("the Result Above is for ADV Table Class for these input");
        System.out.println("size = " + size);
        System.out.println("itreation = " + iteration);
        System.out.println("thinkingTime = " + thinkingTime);
        System.out.println("afterPickingLeftTime = " + afterPickingLeftTime);
        System.out.println("eatingTime =  " + eatingTime);
        System.out.println("afterReleaseLeftTime = " + afterReleaseLeftTime);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
}
