package models;

import java.sql.Timestamp;

import utils.Lock;
import utils.Ref;

public class TweetTimeCounter extends Thread {

    private long tweetTime;
    private int tweetReference;
    private long presentRef;
    private long secondRef;
    private long minRef;
    private long hourRef;
    private long dayRef;
    Ref reference;
    Lock lock;
    public TweetTimeCounter(Ref reference, Lock lock) {
        this.reference = reference;
        this.lock=lock;
    }

    public void setTweetTime() {
        while (Ref.startT == 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public synchronized void setPosition(long timestamp) {
//        while(!lock.getLock()){}
        synchronized (reference) {
            tweetTime = (((timestamp - Ref.startTime.getTime()) / Ref.transMsToSeconds) + Ref.arrayLength)
                    % (Ref.arrayLength);
            tweetReference = (int) tweetTime;
            presentRef = (Ref.currentT - tweetReference + Ref.arrayLength)
                    % Ref.arrayLength;
            Timestamp currentTimestamp = new Timestamp(
                    System.currentTimeMillis());

            // System.out.println((currentTimestamp.getTime() - timestamp) <
            // (Ref.arrayLength * (long)Ref.transMsToSeconds));
            // System.out.println(currentTimestamp.getTime() - timestamp);
            // System.out.println(Ref.arrayLength * (long)Ref.transMsToSeconds);
            // System.out.println(currentTimestamp.getTime() >= timestamp);

            if (((currentTimestamp.getTime() - timestamp) < (Ref.arrayLength * (long) Ref.transMsToSeconds))
                    && (currentTimestamp.getTime() >= timestamp)) {
                secondRef = (Ref.currentT - Ref.lastSecond + Ref.arrayLength)
                        % Ref.arrayLength;
                minRef = (Ref.currentT - Ref.lastMin + Ref.arrayLength)
                        % Ref.arrayLength;
                hourRef = (Ref.currentT - Ref.lastHour + Ref.arrayLength)
                        % Ref.arrayLength;
                dayRef = (Ref.currentT - Ref.lastDay + Ref.arrayLength)
                        % Ref.arrayLength;
                
                if (secondRef == presentRef) {
                    Ref.monthCounter++; // Add this tweet to the per-month counter
                    Ref.dayCounter++; // Add this tweet to the per-day counter
                    Ref.hourCounter++; // Add this tweet to the per-hour counter
                    Ref.minuteCounter++; // Add this tweet to the per-minute counter
                    Ref.secondCounter++; // Add this tweet to the per-second counter
                    Ref.monthScore += Ref.mark; // Add the score to the per-month score
                    Ref.dayScore += Ref.mark; // Add the score to the per-day score
                    Ref.hourScore += Ref.mark; // Add the score to the per-hour score
                    Ref.minuteScore += Ref.mark; // Add the score to the per-minute score
                    Ref.secondScore += Ref.mark; // Add the score to the per-second score
                } else if ((secondRef < presentRef) && (minRef >= presentRef)) {
                    Ref.monthCounter++;
                    Ref.dayCounter++;
                    Ref.hourCounter++;
                    Ref.minuteCounter++;
                    Ref.monthScore += Ref.mark; // Add the score to the per-month score
                    Ref.dayScore += Ref.mark; // Add the score to the per-day score
                    Ref.hourScore += Ref.mark; // Add the score to the per-hour score
                    Ref.minuteScore += Ref.mark; // Add the score to the per-minute score
                } else if ((minRef < presentRef) && (hourRef >= presentRef)) {
                    Ref.monthCounter++;
                    Ref.dayCounter++;
                    Ref.hourCounter++;
                    Ref.monthScore += Ref.mark; // Add the score to the per-month score
                    Ref.dayScore += Ref.mark; // Add the score to the per-day score
                    Ref.hourScore += Ref.mark; // Add the score to the per-hour score
                } else if ((hourRef < presentRef) && (dayRef >= presentRef)) {
                    Ref.monthCounter++;
                    Ref.dayCounter++;
                    Ref.monthScore += Ref.mark; // Add the score to the per-month score
                    Ref.dayScore += Ref.mark; // Add the score to the per-day score
                } else if ((dayRef) <= presentRef
                        && (Ref.arrayLength - 1) > presentRef) {
//                    System.out.println("True");
                    Ref.monthCounter++;
                    Ref.monthScore += Ref.mark; // Add the score to the per-month score
                }
                // System.out.println("###############");
                Ref.counterArray[tweetReference]++;
                Ref.scoreArray[tweetReference] += Ref.mark; // Add the score to the array
                
            }
//            lock.releaseLock();
        }
        
    }

}
