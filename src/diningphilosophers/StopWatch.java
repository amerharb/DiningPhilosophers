/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

/**
 *
 * @author Amer
 */
public class StopWatch
{

    private long period;
    private long startTime;
//    private long stopTime;
    private byte status; //0- stoped, 1-running

    public StopWatch()
    {
        period = 0;
    }

    public void stop()
    {
        //period += (System.currentTimeMillis() - startTime);
        period += (System.nanoTime() - startTime);
        status = 0;
    }

    public void start()
    {
        //startTime = System.currentTimeMillis();
        startTime = System.nanoTime();
        status = 1;
    }

    public void reset()
    {
        period = 0;
        //startTime = System.currentTimeMillis();
        startTime = System.nanoTime();
    }

    public long getPeriod()
    {
        switch (status) {
        case 0:
            return period;
        case 1:
            //return period + (System.currentTimeMillis() - startTime);
            return period + (System.nanoTime() - startTime);
        }

        return period;
    }

}
