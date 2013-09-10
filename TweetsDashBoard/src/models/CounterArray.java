package models;

import java.sql.Timestamp;

import utils.Lock;
import utils.Ref;

public class CounterArray extends Thread {
    private Timestamp currentTime;
    private int currentReference = 0;
    private int currentEmpty = 0;
    private int startReference = 0;
    private int currentMonthElement = 0;
    private int lastMonthElement = 0;
    private int emptyElement = 0;
    private int currentMonthScore = 0;
    private int lastMonthScore = 0;
    private int emptyScore = 0;
    Ref reference;
    Lock lock;
    public CounterArray(Ref reference, Lock lock) {
        this.reference = reference;
        this.lock=lock;
    }

    public void run() {
        this.setStartTime();
        while (true) {
            try {
//                while(!lock.getLock()){}
                this.calcuCounter();
//                lock.releaseLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized void setStartTime() {
        Ref.startTime = new Timestamp(System.currentTimeMillis());
        Ref.startT = Ref.startTime.getTime();
        startReference = (int) Ref.startT;
    }

    public synchronized void calcuCounter() throws InterruptedException {
        synchronized (reference) {
            currentTime = new Timestamp(System.currentTimeMillis());
            Ref.currentT = ((currentTime.getTime() - Ref.startTime
                    .getTime()) / Ref.transMsToSeconds)
                    % Ref.arrayLength;
            currentReference = (int) Ref.currentT;
            currentEmpty = currentReference + 1;
            lastMonthElement = currentMonthElement;
            currentMonthElement = emptyElement;
            emptyElement = Ref.counterArray[currentEmpty];
            
//            System.out.println("lastMonthElement = " + lastMonthElement);
//            System.out.println("currentMonthElement = " + currentMonthElement);
//            System.out.println("emptyElement = " + emptyElement);
            
            Ref.counterArray[currentEmpty] = 0;
            // Calculate the tweet traffic per second
            Ref.lastSecond = (currentReference + Ref.arrayLength - 1)
                    % Ref.arrayLength;
            Ref.secondCounter = Ref.counterArray[Ref.lastSecond];
            // Calculate the tweet traffic per minute
            Ref.lastMin = (currentReference + Ref.arrayLength
                    - Ref.second - 1)
                    % Ref.arrayLength;
            Ref.minuteCounter = Ref.minuteCounter
                    + Ref.secondCounter
                    - Ref.counterArray[Ref.lastMin];
            // Calculate the tweet traffic per hour
            Ref.lastHour = (currentReference + Ref.arrayLength
                    - Ref.second * Ref.minute - 1)
                    % Ref.arrayLength;
            Ref.hourCounter = Ref.hourCounter
                    + Ref.secondCounter
                    - Ref.counterArray[Ref.lastHour];
            // Calculate the tweet traffic per day
            Ref.lastDay = (currentReference + Ref.arrayLength
                    - Ref.hour * Ref.second * Ref.minute - 1)
                    % Ref.arrayLength;
            Ref.dayCounter = Ref.dayCounter
                    + Ref.secondCounter
                    - Ref.counterArray[Ref.lastDay];
            // Calculate the tweet traffic per month
            // Reference.currentMonth = (currentReference +
            // Reference.arrayLength - 1)
            // % Reference.arrayLength;
            // Reference.monthCounter = Reference.monthCounter
            // + Reference.secondCounter
            // - Reference.counterArray[Reference.currentMonth];
            
//            System.out.println("Reference.monthCounter = " + Ref.monthCounter);
//            System.out.println("Reference.secondCounter = " + Ref.secondCounter);
//            System.out.println("lastMonthElement = " + lastMonthElement);          
//            
            Ref.monthCounter = Ref.monthCounter
                    + Ref.secondCounter - lastMonthElement;
            
            
            
            
            lastMonthScore = currentMonthScore;
            currentMonthScore = emptyScore;
            emptyScore = Ref.scoreArray[currentEmpty];          
            Ref.scoreArray[currentEmpty] = 0;
            // Calculate the sentiment per second
            Ref.lastSecond = (currentReference + Ref.arrayLength - 1)
                    % Ref.arrayLength;
            Ref.secondScore = Ref.scoreArray[Ref.lastSecond];
            // Calculate the tweet traffic per minute
            Ref.lastMin = (currentReference + Ref.arrayLength
                    - Ref.second - 1)
                    % Ref.arrayLength;
            Ref.minuteScore = Ref.minuteScore
                    + Ref.secondScore
                    - Ref.scoreArray[Ref.lastMin];
            // Calculate the tweet traffic per hour
            Ref.lastHour = (currentReference + Ref.arrayLength
                    - Ref.second * Ref.minute - 1)
                    % Ref.arrayLength;
            Ref.hourScore = Ref.hourScore
                    + Ref.secondScore
                    - Ref.scoreArray[Ref.lastHour];
            // Calculate the tweet traffic per day
            Ref.lastDay = (currentReference + Ref.arrayLength
                    - Ref.hour * Ref.second * Ref.minute - 1)
                    % Ref.arrayLength;
            Ref.dayScore = Ref.dayScore
                    + Ref.secondScore
                    - Ref.scoreArray[Ref.lastDay];           
            Ref.monthScore = Ref.monthScore
                    + Ref.secondScore - lastMonthScore;
        }
    }
}
