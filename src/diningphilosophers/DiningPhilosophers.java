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
        final int size = 5; //number of philosophers and chopsticks
        final int iteration = 1000;
        final int thinkingTime = 100;
        final int afterPickingLeftTime = 10;
        final int eatingTime = 100;
        final int afterReleaseLeftTime = 10;

        final int[] thinkingTimeArray = new int[iteration];
        final int[] afterPickingLeftTimeArray = new int[iteration];
        final int[] eatingTimeArray = new int[iteration];
        final int[] afterReleaseLeftTimeArray = new int[iteration];

        for (int i = 0; i < iteration; i++) {
            thinkingTimeArray[i] = (int) (Math.random() * thinkingTime);
            afterPickingLeftTimeArray[i] = (int) (Math.random() * afterPickingLeftTime);
            eatingTimeArray[i] = (int) (Math.random() * eatingTime);
            afterReleaseLeftTimeArray[i] = (int) (Math.random() * afterReleaseLeftTime);
        }

        Table tab;
        tab = new Table(size);
        for (int i = 0; i < size; i++) {
            //Thread th = new Thread(new Philosopher(i, tab, iteration, thinkingTime, afterPickingLeftTime, eatingTime, afterReleaseLeftTime));
            Thread th = new Thread(new Philosopher(i, tab, iteration, thinkingTimeArray, afterPickingLeftTimeArray, eatingTimeArray, afterReleaseLeftTimeArray));
            th.start();
        }

        while (Thread.activeCount() > 1) {
            Thread.sleep(2500);
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

        long normalTableWaiting = Philosopher.getAllPhilosophersWaitingPeriods();
        long normalTableTotalTime = Philosopher.getAllPhilosophersTotalPeriod();

        Philosopher.resetAllPeriods();

        AdvTable advTab;
        advTab = new AdvTable(size);
        for (int i = 0; i < size; i++) {
            //Thread th = new Thread(new Philosopher(i, advTab, iteration, thinkingTime, afterPickingLeftTime, eatingTime, afterReleaseLeftTime));
            Thread th = new Thread(new Philosopher(i, advTab, iteration, thinkingTimeArray, afterPickingLeftTimeArray, eatingTimeArray, afterReleaseLeftTimeArray));
            th.start();
        }

        while (Thread.activeCount() > 1) {
            Thread.sleep(2500);
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

        long advTableWaiting = Philosopher.getAllPhilosophersWaitingPeriods();
        long advTableTotalTime = Philosopher.getAllPhilosophersTotalPeriod();
        Philosopher.resetAllPeriods();

        System.out.println("ADV waiting is faster by (minus means slower) :" + ((double) (normalTableWaiting - advTableWaiting) / normalTableWaiting * 100) + "%");
        System.out.println("ADV total time is faster by (minus means slower) :" + ((double) (normalTableTotalTime - advTableTotalTime) / normalTableTotalTime * 100) + "%");

    }
}
