//description of a table in the Dining Philosophers problem
package diningphilosophers;

/**
 *
 * @author jof
 */
public class RLTable
{

    int dinner; // number of philosopher eating at the same time
    int timeOfPeak;

    int nbrOfChopsticks;
    private boolean chopstick[]; //true if chopstick[i] is available

    public RLTable(int nbrOfSticks)
    {
        nbrOfChopsticks = nbrOfSticks;
        chopstick = new boolean[nbrOfChopsticks];
        for (int i = 0; i < nbrOfChopsticks; i++) {
            chopstick[i] = true;
        }
    }

    public synchronized void getLeft(int n) throws InterruptedException
    {
        //philosopher n picks up its left chopstick
        int pos = n + 1;
        if (pos == nbrOfChopsticks) {
            pos = 0;
        }
        while (!chopstick[n] || !chopstick[pos]) {
            this.wait();
        }

        chopstick[n] = false;
    }

    public synchronized boolean getRight(int n) throws InterruptedException
    {
        //philosopher n picks up its right chopstick
        int pos = n + 1;
        if (pos == nbrOfChopsticks) {
            pos = 0;
        }

        if (!chopstick[pos]) { // if the right is busy then release the left asuming the as long as you start to pick up the right then the left already in hand
            releaseLeft(n);
            //Thread.sleep(10);
            return false;
        }
        chopstick[pos] = false;
        dinner++;
        if (dinner >= (nbrOfChopsticks / 2)) {
            //System.out.println("peak dinner");
            timeOfPeak++;
            //System.out.println("number of peak:---------------- " + timeOfPeak);
        }
        return true;
        //System.out.println("dinner: " + dinner);
    }

    public synchronized void releaseLeft(int n)
    {
        //philosopher n puts down its left chopstick
        chopstick[n] = true;
        dinner--;
        //System.out.println("dinner: " + dinner);
        this.notify();

    }

    public synchronized void releaseRight(int n)
    {
        //philosopher n puts down its right chopstick
        int pos = n + 1;
        if (pos == nbrOfChopsticks) {
            pos = 0;
        }
        chopstick[pos] = true;
        this.notify();
    }
}
