package models;

import utils.Ref;

public class SecondTimer extends Thread {
    public CounterArray counterarray;

    public SecondTimer(CounterArray counterarray) {
        this.counterarray = counterarray;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (counterarray) {
                counterarray.notifyAll();
            }
        }
    }
}
