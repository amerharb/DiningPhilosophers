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
        final int iteration = 100;
        final int thinkingTime = 100;
        final int afterPickingLeftTime = 10;
        final int eatingTime = 100;
        final int afterReleaseLeftTime = 10;

        //the following block will generate a random value of sleeping then inject them to the philisopher 
        //class that we are going to use in order to make it a fair comparing 
        long StartTime = System.currentTimeMillis();
        
        final int[][][] PhilosophersTimingArray = new int[size][4][iteration];
        
        for (int p = 0; p < size; p++){
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
            
            PhilosophersTimingArray[p][0] =  thinkingTimeArray;
            PhilosophersTimingArray[p][1] =  afterPickingLeftTimeArray;
            PhilosophersTimingArray[p][2] =  eatingTimeArray;
            PhilosophersTimingArray[p][3] =  afterReleaseLeftTimeArray;
            
        }
        
        System.out.println("How long it take to generate times (in milliseconds): " + (System.currentTimeMillis() - StartTime));

        Table tab;
        tab = new Table(size);

        Philosopher[] normalPh = new Philosopher[size];
        
        for (int p = 0; p < size; p++) {
            normalPh[p] = new Philosopher(p, tab, iteration, PhilosophersTimingArray[p][0], PhilosophersTimingArray[p][1], PhilosophersTimingArray[p][2], PhilosophersTimingArray[p][3]);
            Thread th = new Thread(normalPh[p]);
            th.start();
        }

//        while (Thread.activeCount() > 1) {
//            Thread.sleep(2500);
//        }

//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("the Result Above is for Normal Table Class for the folowing input");
//        System.out.println("size = " + size);
//        System.out.println("itreation = " + iteration);
//        System.out.println("thinkingTime = " + thinkingTime);
//        System.out.println("afterPickingLeftTime = " + afterPickingLeftTime);
//        System.out.println("eatingTime =  " + eatingTime);
//        System.out.println("afterReleaseLeftTime = " + afterReleaseLeftTime);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

//        Philosopher.resetAllPeriods();

        AdvTable advTab;
        advTab = new AdvTable(size);

        Philosopher[] advPh = new Philosopher[size];
        
        for (int p = 0; p < size; p++) {
            advPh[p] = new Philosopher(p, advTab, iteration, PhilosophersTimingArray[p][0], PhilosophersTimingArray[p][1], PhilosophersTimingArray[p][2], PhilosophersTimingArray[p][3]);
            Thread th = new Thread(advPh[p]);
            th.start();
        }
        
//        while (Thread.activeCount() > 1) {
//            Thread.sleep(2400);
//        }

//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("the Result Above is for ADV Table Class for these input");
//        System.out.println("size = " + size);
//        System.out.println("itreation = " + iteration);
//        System.out.println("thinkingTime = " + thinkingTime);
//        System.out.println("afterPickingLeftTime = " + afterPickingLeftTime);
//        System.out.println("eatingTime =  " + eatingTime);
//        System.out.println("afterReleaseLeftTime = " + afterReleaseLeftTime);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //Philosopher.resetAllPeriods();

        RLTable rlTab;
        rlTab = new RLTable(size);

        Philosopher[] rlPh = new Philosopher[size];

        for (int p = 0; p < size; p++) {
            rlPh[p] = new Philosopher(p, rlTab, iteration, PhilosophersTimingArray[p][0], PhilosophersTimingArray[p][1], PhilosophersTimingArray[p][2], PhilosophersTimingArray[p][3]);
            Thread th = new Thread(rlPh[p]);
            th.start();
        }
//        while (Thread.activeCount() > 1) {
//            Thread.sleep(2400);
//        }

//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("the Result Above is for RL Table Class for these input");
//        System.out.println("size = " + size);
//        System.out.println("itreation = " + iteration);
//        System.out.println("thinkingTime = " + thinkingTime);
//        System.out.println("afterPickingLeftTime = " + afterPickingLeftTime);
//        System.out.println("eatingTime =  " + eatingTime);
//        System.out.println("afterReleaseLeftTime = " + afterReleaseLeftTime);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        while (Thread.activeCount() > 1) {
            Thread.sleep(2500);
        }

        long advTableWaiting = 0;// = Philosopher.getAllPhilosophersWaitingPeriods();
        long advTableTotalTime = 0;// = Philosopher.getAllPhilosophersTotalPeriod();
        long rlTableWaiting = 0;//Philosopher.getAllPhilosophersWaitingPeriods();
        long rlTableTotalTime = 0;//Philosopher.getAllPhilosophersTotalPeriod();
        long normalTableWaiting = 0; // = Philosopher.getAllPhilosophersWaitingPeriods();
        long normalTableTotalTime = 0; // = Philosopher.getAllPhilosophersTotalPeriod();
        for (int p = 0; p < size; p++) {
            advTableWaiting += advPh[p].getWaitingPeriod();
            advTableTotalTime += advPh[p].getTotalPeriod();
            rlTableWaiting += rlPh[p].getWaitingPeriod();
            rlTableTotalTime += rlPh[p].getTotalPeriod();
            normalTableWaiting += normalPh[p].getWaitingPeriod();
            normalTableTotalTime += normalPh[p].getTotalPeriod();
        }
//        Philosopher.resetAllPeriods();

        System.out.println("normal waiting:" + normalTableWaiting);
        System.out.println("normal total time:" + normalTableTotalTime);
        System.out.println("RL waiting:" + rlTableWaiting);
        System.out.println("RL total time:" + rlTableTotalTime);
        System.out.println("ADV waiting:" + advTableWaiting);
        System.out.println("ADV total time:" + advTableTotalTime);
        
        System.out.println("ADV waiting is faster than normal by :" + ((double) (normalTableWaiting - advTableWaiting) / normalTableWaiting * 100) + "%");
        System.out.println("ADV total time is faster than normal by :" + ((double) (normalTableTotalTime - advTableTotalTime) / normalTableTotalTime * 100) + "%");
        System.out.println("RL waiting is faster than normal by :" + ((double) (normalTableWaiting - rlTableWaiting) / normalTableWaiting * 100) + "%");
        System.out.println("RL total time is faster than normal by :" + ((double) (normalTableTotalTime - rlTableTotalTime) / normalTableTotalTime * 100) + "%");
        System.out.println("ADV waiting is faster than RL by :" + ((double) (rlTableWaiting - advTableWaiting) / rlTableWaiting * 100) + "%");
        System.out.println("ADV total time is faster than RL by :" + ((double) (rlTableTotalTime - advTableTotalTime) / rlTableTotalTime * 100) + "%");

    }
}