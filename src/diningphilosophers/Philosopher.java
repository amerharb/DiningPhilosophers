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

    private int id;

    private long thinkingPeriod;
    private long eatingPeriod;
    private long waitingPeriod;

    private static long allPhilosopherThinkingPeriod;
    private static long allPhilosopherEatingPeriod;
    private static long allPhilosopherWaitingPeriod;

    private Table myTable;
    private AdvTable myAdvTable;
    private byte whichTableToUse; // 0- Table class 1- Adv Table Class

    public Philosopher(int pid, Table tab, int iteration, int thinkingTime, int afterPickingLeftTime, int eatingTime, int afterReleaseLeftTime)
    {
        id = pid;
        myTable = tab;
        this.iteration = iteration;
        this.thinkingTime = thinkingTime;
        this.afterPickingLeftTime = afterPickingLeftTime;
        this.eatingTime = eatingTime;
        this.afterReleaseLeftTime = afterReleaseLeftTime;
        this.whichTableToUse = 0;
    }

    public Philosopher(int pid, Table tab)
    {
        this(pid, tab, 100, 100, 10, 100, 10);
    }

    public Philosopher(int pid, AdvTable advTab, int iteration, int thinkingTime, int afterPickingLeftTime, int eatingTime, int afterReleaseLeftTime)
    {
        id = pid;
        myAdvTable = advTab;
        this.iteration = iteration;
        this.thinkingTime = thinkingTime;
        this.afterPickingLeftTime = afterPickingLeftTime;
        this.eatingTime = eatingTime;
        this.afterReleaseLeftTime = afterReleaseLeftTime;
        this.whichTableToUse = 1;
    }

    public Philosopher(int pid, AdvTable advTab)
    {
        this(pid, advTab, 100, 100, 10, 100, 10);
    }

    @Override
    public void run()
    {
        StopWatch thinkingSW = new StopWatch();//count the time between release right (or from the begning as the initioal status of philosopher is thinking) chopstick until attend to pick the left one 
        StopWatch eatingSW = new StopWatch(); // count the time between actually pick up the right chopstick untill attend to release the left one
        StopWatch waitingSW = new StopWatch(); // it count the time between attend to pick up the left chopstick (not actualy picking it) until pick up the right one actually and the time from releasing the left until releasing the right

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
        System.out.println("Total Time of All Philosophers: " + getAllPhilosophersWaitingPeriods());
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
