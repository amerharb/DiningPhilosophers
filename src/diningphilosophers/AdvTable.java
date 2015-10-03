//description of a table in the Dining Philosophers problem
package diningphilosophers;

public class AdvTable
{

    int nbrOfChopsticks;
//    private boolean chopstick[]; //true if chopstick[i] is available

    private class ChopstickClass
    {

        byte status; //0-avalible, 1-requested, 2-busy
        int philosopher; // -1 no one

        public ChopstickClass()
        {
            status = 0;
            philosopher = -1;
        }

    };
    private ChopstickClass[] advChopstick; //0-avalible, 1-requested, 2-busy

    //these variable to check the prefomance of the code by check how many dinner at the same time 
    int dinner; // number of philosopher eating at the same time
    int timeOfPeak; //how many time the peak number reached 

    public AdvTable(int nbrOfSticks)
    {
        nbrOfChopsticks = nbrOfSticks;
        advChopstick = new ChopstickClass[nbrOfSticks];
        for (int i = 0; i < nbrOfSticks; i++) {
            advChopstick[i] = new ChopstickClass();
        }
    }

    public synchronized void getLeft(int n) throws InterruptedException
    {
        //philosopher n picks up its left chopstick
        int rightChopstick = (n + 1) % nbrOfChopsticks;
//        while (!chopstick[n] | !chopstick[rightChopstick]) {
        while (advChopstick[n].status != 0 | advChopstick[rightChopstick].status != 0) {
            this.wait();
        }
//        chopstick[n] = false;
        advChopstick[n].status = 2;  //use Left Chopstick
        advChopstick[n].philosopher = n;
        advChopstick[rightChopstick].status = 1; //request Right Chopstick
        advChopstick[rightChopstick].philosopher = n;
        //System.out.println(n + ": get left");
    }

    public synchronized void getRight(int n) throws InterruptedException
    {
        //philosopher n picks up its right chopstick
        int rightChopstick = (n + 1) % nbrOfChopsticks;

        while (advChopstick[rightChopstick].status == 2
                | (advChopstick[rightChopstick].status == 1 & advChopstick[rightChopstick].philosopher != n)) {
            System.out.println("---------------STOP: right one requested when its busy");
            this.wait();
        }
        advChopstick[rightChopstick].status = 2;  //use Right Chopstick
        advChopstick[rightChopstick].philosopher = n;
        
        //this code to check the prefomance of the code by check how many dinner at the same time 
        dinner++;
        //System.out.println("number of dinner :" + dinner);
        if (dinner >= (nbrOfChopsticks / 2)) {
            //System.out.println("peak dinner");
            timeOfPeak++;
            //System.out.println("number of peak: --------------------------- " + timeOfPeak);
        }
        //System.out.println(n + ": get right");
    }

    public synchronized void releaseLeft(int n)
    {
        //philosopher n puts down its left chopstick
        //chopstick[n] = true;
        advChopstick[n].status = 0;
        advChopstick[n].philosopher = -1;
//        System.out.println(n + ": release left");
        dinner--;
//        System.out.println("number of dinner :" + dinner);
        this.notify();
    }

    public synchronized void releaseRight(int n)
    {
        //philosopher n puts down its right chopstick
        int rightChopstick = (n + 1) % nbrOfChopsticks;
        //chopstick[pos] = true;
        advChopstick[rightChopstick].status = 0;
        advChopstick[rightChopstick].philosopher = -1;
//        System.out.println(n + ": release right");
        this.notify();
    }
}
