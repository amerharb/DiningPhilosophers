package diningphilosophers;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jof
 */
public class Philosopher implements Runnable
{

    final int iteration;
    final int thinkingTime;
    final int afterPickingLeftTime;
    final int eatingTime;
    final int afterReleaseLeftTime;

    final byte runningMode; //0-random timming, 1-array of timing will be provided
    final int[] thinkingTimeArray;
    final int[] afterPickingLeftTimeArray;
    final int[] eatingTimeArray;
    final int[] afterReleaseLeftTimeArray;

    private int id;

    private long thinkingPeriod;
    private long eatingPeriod;
    private long waitingPeriod;

    private static long allPhilosopherThinkingPeriod;
    private static long allPhilosopherEatingPeriod;
    private static long allPhilosopherWaitingPeriod;

    private Table myTable;
    private AdvTable myAdvTable;
    private RLTable myRLTable;
    
    private byte whichTableToUse; // 0- Table class 1- Adv Table Class

    public Philosopher(int pid, Table tab, int iteration, int thinkingTime, int afterPickingLeftTime, int eatingTime, int afterReleaseLeftTime)
    {
        whichTableToUse = 0; //thats mean the class will use the normal Table class
        runningMode = 0; //the timing is random but the above value will have an affect of random range

        id = pid;
        myTable = tab;
        this.iteration = iteration;

        this.thinkingTime = thinkingTime;
        this.afterPickingLeftTime = afterPickingLeftTime;
        this.eatingTime = eatingTime;
        this.afterReleaseLeftTime = afterReleaseLeftTime;

        this.thinkingTimeArray = null;
        this.afterPickingLeftTimeArray = null;
        this.eatingTimeArray = null;
        this.afterReleaseLeftTimeArray = null;
    }

//    public Philosopher(int pid, Table tab)
//    {
//        this(pid, tab, 100, 100, 10, 100, 10);
//    }
    public Philosopher(int pid, Table tab, int iteration, int[] thinkingTime, int[] afterPickingLeftTime, int[] eatingTime, int[] afterReleaseLeftTime)
    {
        whichTableToUse = 0; //thats mean the class will use the normal Table class
        runningMode = 1; //there will be no random value instead the timming will come in arrays
        id = pid;
        myTable = tab;
        this.iteration = iteration;

        this.thinkingTimeArray = thinkingTime;
        this.afterPickingLeftTimeArray = afterPickingLeftTime;
        this.eatingTimeArray = eatingTime;
        this.afterReleaseLeftTimeArray = afterReleaseLeftTime;

        this.thinkingTime = 0;
        this.afterPickingLeftTime = 0;
        this.eatingTime = 0;
        this.afterReleaseLeftTime = 0;

    }

    public Philosopher(int pid, AdvTable advTab, int iteration, int thinkingTime, int afterPickingLeftTime, int eatingTime, int afterReleaseLeftTime)
    {
        whichTableToUse = 1; //that mean the class will use the ADV Table class
        runningMode = 0; //the timing is random but the above value will have an affect of random range
        id = pid;
        myAdvTable = advTab;
        this.iteration = iteration;

        this.thinkingTime = thinkingTime;
        this.afterPickingLeftTime = afterPickingLeftTime;
        this.eatingTime = eatingTime;
        this.afterReleaseLeftTime = afterReleaseLeftTime;

        this.thinkingTimeArray = null;
        this.afterPickingLeftTimeArray = null;
        this.eatingTimeArray = null;
        this.afterReleaseLeftTimeArray = null;
    }

    public Philosopher(int pid, AdvTable advTab, int iteration, int[] thinkingTime, int[] afterPickingLeftTime, int[] eatingTime, int[] afterReleaseLeftTime)
    {
        whichTableToUse = 1; //thats mean the class will use the normal Table class
        runningMode = 1; //there will be no random value instead the timming will come in arrays
        id = pid;
        myAdvTable = advTab;
        this.iteration = iteration;

        this.thinkingTimeArray = thinkingTime;
        this.afterPickingLeftTimeArray = afterPickingLeftTime;
        this.eatingTimeArray = eatingTime;
        this.afterReleaseLeftTimeArray = afterReleaseLeftTime;

        this.thinkingTime = 0;
        this.afterPickingLeftTime = 0;
        this.eatingTime = 0;
        this.afterReleaseLeftTime = 0;

    }

    public Philosopher(int pid, RLTable rlTab, int iteration, int[] thinkingTime, int[] afterPickingLeftTime, int[] eatingTime, int[] afterReleaseLeftTime)
    {
        whichTableToUse = 2; //thats mean the class will use the normal Table class
        runningMode = 1; //there will be no random value instead the timming will come in arrays
        id = pid;
        myRLTable = rlTab;
        this.iteration = iteration;

        this.thinkingTimeArray = thinkingTime;
        this.afterPickingLeftTimeArray = afterPickingLeftTime;
        this.eatingTimeArray = eatingTime;
        this.afterReleaseLeftTimeArray = afterReleaseLeftTime;

        this.thinkingTime = 0;
        this.afterPickingLeftTime = 0;
        this.eatingTime = 0;
        this.afterReleaseLeftTime = 0;

    }

//    public Philosopher(int pid, AdvTable advTab)
//    {
//        this(pid, advTab, 100, 100, 10, 100, 10);
//    }
//    
    @Override
    public void run()
    {
        StopWatch thinkingSW = new StopWatch();//count the time between release right (or from the begning as the initioal status of philosopher is thinking) chopstick until attend to pick the left one 
        StopWatch eatingSW = new StopWatch(); // count the time between actually pick up the right chopstick untill attend to release the left one
        StopWatch waitingSW = new StopWatch(); // it count the time between attend to pick up the left chopstick (not actualy picking it) until pick up the right one actually and the time from releasing the left until releasing the right
        switch (runningMode) {
        case 0:
            switch (whichTableToUse) {
            case 0:
                for (int i = 0; i < iteration; i++) {
                    try {
                        //think
                        System.out.println("Philosopher " + id + " thinks. Iteration " + i);
                        thinkingSW.start();
                        Thread.sleep((int) (Math.random() * thinkingTime));
                        thinkingSW.stop();

                        //pick up chopsticks
                        System.out.println("Philosopher " + id + " pick up left");
                        waitingSW.start();
                        myTable.getLeft(id);
                        Thread.sleep((int) (Math.random() * afterPickingLeftTime));
                        System.out.println("Philosopher " + id + " pick up right");
                        myTable.getRight(id);
                        waitingSW.stop();
                        //eat
                        System.out.println("Philosopher " + id + " eats. Iteration " + i);
                        eatingSW.start();
                        Thread.sleep((int) (Math.random() * eatingTime));
                        eatingSW.stop();
                        //release chopsticks
                        System.out.println("Philosopher " + id + " drop left");
                        waitingSW.start();
                        myTable.releaseLeft(id);
                        Thread.sleep((int) (Math.random() * afterReleaseLeftTime));
                        System.out.println("Philosopher " + id + " drop right");
                        myTable.releaseRight(id);
                        waitingSW.stop();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < iteration; i++) {
                    try {
                        //think
                        System.out.println("Philosopher " + id + " thinks. Iteration " + i);
                        thinkingSW.start();
                        Thread.sleep((int) (Math.random() * thinkingTime));
                        thinkingSW.stop();

                        //pick up chopsticks
                        System.out.println("Philosopher " + id + " pick up left");
                        waitingSW.start();
                        myAdvTable.getLeft(id);
                        Thread.sleep((int) (Math.random() * afterPickingLeftTime));
                        System.out.println("Philosopher " + id + " pick up right");
                        myAdvTable.getRight(id);
                        waitingSW.stop();
                        //eat
                        System.out.println("Philosopher " + id + " eats. Iteration " + i);
                        eatingSW.start();
                        Thread.sleep((int) (Math.random() * eatingTime));
                        eatingSW.stop();
                        //release chopsticks
                        System.out.println("Philosopher " + id + " drop left");
                        waitingSW.start();
                        myAdvTable.releaseLeft(id);
                        Thread.sleep((int) (Math.random() * afterReleaseLeftTime));
                        System.out.println("Philosopher " + id + " drop right");
                        myAdvTable.releaseRight(id);
                        waitingSW.stop();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            break;
        case 1: //there will be no random in this case
            switch (whichTableToUse) {
            case 0:
                for (int i = 0; i < iteration; i++) {
                    try {
                        //think
                        System.out.println("Philosopher " + id + " thinks. Iteration " + i);
                        thinkingSW.start();
                        Thread.sleep(thinkingTimeArray[i]);
                        thinkingSW.stop();

                        //pick up chopsticks
                        System.out.println("Philosopher " + id + " pick up left");
                        waitingSW.start();
                        myTable.getLeft(id);
                        Thread.sleep((int) (afterPickingLeftTimeArray[i]));
                        System.out.println("Philosopher " + id + " pick up right");
                        myTable.getRight(id);
                        waitingSW.stop();
                        //eat
                        System.out.println("Philosopher " + id + " eats. Iteration " + i);
                        eatingSW.start();
                        Thread.sleep((int) (eatingTimeArray[i]));
                        eatingSW.stop();
                        //release chopsticks
                        System.out.println("Philosopher " + id + " drop left");
                        waitingSW.start();
                        myTable.releaseLeft(id);
                        Thread.sleep((int) (afterReleaseLeftTimeArray[i]));
                        System.out.println("Philosopher " + id + " drop right");
                        myTable.releaseRight(id);
                        waitingSW.stop();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < iteration; i++) {
                    try {
                        //think
                        System.out.println("Philosopher " + id + " thinks. Iteration " + i);
                        thinkingSW.start();
                        Thread.sleep((int) (thinkingTimeArray[i]));
                        thinkingSW.stop();

                        //pick up chopsticks
                        System.out.println("Philosopher " + id + " pick up left");
                        waitingSW.start();
                        myAdvTable.getLeft(id);
                        Thread.sleep((int) (afterPickingLeftTimeArray[i]));
                        System.out.println("Philosopher " + id + " pick up right");
                        myAdvTable.getRight(id);
                        waitingSW.stop();
                        //eat
                        System.out.println("Philosopher " + id + " eats. Iteration " + i);
                        eatingSW.start();
                        Thread.sleep((int) (eatingTimeArray[i]));
                        eatingSW.stop();
                        //release chopsticks
                        System.out.println("Philosopher " + id + " drop left");
                        waitingSW.start();
                        myAdvTable.releaseLeft(id);
                        Thread.sleep((int) (afterReleaseLeftTimeArray[i]));
                        System.out.println("Philosopher " + id + " drop right");
                        myAdvTable.releaseRight(id);
                        waitingSW.stop();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < iteration; i++) {
                    try {
                        //think
                        System.out.println("Philosopher " + id + " thinks. Iteration " + i);
                        thinkingSW.start();
                        Thread.sleep((int) (thinkingTimeArray[i]));
                        thinkingSW.stop();

                        //pick up chopsticks
                        System.out.println("Philosopher " + id + " pick up left");
                        waitingSW.start();
                        do {
                            myRLTable.getLeft(id);
                            Thread.sleep((int) (afterPickingLeftTimeArray[i]));
                            System.out.println("Philosopher " + id + " pick up right");
                            
                        } while(!myRLTable.getRight(id)); 
                        waitingSW.stop();
                        //eat
                        System.out.println("Philosopher " + id + " eats. Iteration " + i);
                        eatingSW.start();
                        Thread.sleep((int) (eatingTimeArray[i]));
                        eatingSW.stop();
                        //release chopsticks
                        System.out.println("Philosopher " + id + " drop left");
                        waitingSW.start();
                        myRLTable.releaseLeft(id);
                        Thread.sleep((int) (afterReleaseLeftTimeArray[i]));
                        System.out.println("Philosopher " + id + " drop right");
                        myRLTable.releaseRight(id);
                        waitingSW.stop();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            break;

        }

        thinkingPeriod = thinkingSW.getPeriod();
        eatingPeriod = eatingSW.getPeriod();
        waitingPeriod = waitingSW.getPeriod();

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("thinking Time of Philosopher " + id + ": " + thinkingPeriod);
        System.out.println("eating Time of Philosopher " + id + ": " + eatingPeriod);
        System.out.println("waiting Time of Philosopher " + id + ": " + waitingPeriod);
        System.out.println("-------------------------------------------------------------------------");

        allPhilosopherThinkingPeriod += thinkingPeriod;
        allPhilosopherEatingPeriod += eatingPeriod;
        allPhilosopherWaitingPeriod += waitingPeriod;

        System.out.println("=========================================================================");
        System.out.println("Thinking Time of All Philosophers: " + allPhilosopherThinkingPeriod);
        System.out.println("Eating Time of All Philosophers: " + allPhilosopherEatingPeriod);
        System.out.println("Waiting Time of All Philosophers: " + allPhilosopherWaitingPeriod);
        System.out.println("Total Time of All Philosophers: " + getAllPhilosophersTotalPeriod());
        System.out.println("=========================================================================");

    }

    public static void resetAllPeriods()
    {
        allPhilosopherThinkingPeriod = 0;
        allPhilosopherEatingPeriod = 0;
        allPhilosopherWaitingPeriod = 0;
    }

    public static long getAllPhilosophersWaitingPeriods()
    {
        return (allPhilosopherWaitingPeriod);
    }

    public static long getAllPhilosophersTotalPeriod()
    {
        return (allPhilosopherThinkingPeriod + allPhilosopherEatingPeriod + allPhilosopherWaitingPeriod);
    }

    public long getThinkingPeriod()
    {
        return thinkingPeriod;
    }

    public long getEatingPeriod()
    {
        return eatingPeriod;
    }

    public long getWaitingPeriod()
    {
        return waitingPeriod;
    }

}
