package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import models.Parameter;
import models.TweetTimeCounter;
import models.Tweets;
import models.User;
import utils.Lock;
import utils.Ref;
import utils.Sentiment;
import utils.TimeTransform;

public class FileInputProcessor extends Thread {
    TimeTransform timeTrans = new TimeTransform();
    private TweetTimeCounter TweetTimestamp;
    private long interval = 0;
    private long tweetsTime1 = 0;
    private long tweetsTime2 = 0;
    Scanner inputStream = null;
    String line = null; // currentline
    JSONObject obj = null;
    JSONObject obj1 = null;
    Tweets tempTweets = null;
    User tempUser = null;
    int insertPos;
    Ref ref = null;
    FileOutputProcessor file = null;
    long preRef = 0;
    Timestamp tweetsTime;
    Timestamp tweetsTime3;
    Timestamp currentTimestamp;
    Sentiment sentiment;
    float score = 0;
    Parameter para;
    Lock lock;
    public FileInputProcessor(Ref ref, Parameter para, Lock lock) {
        TweetTimestamp = new TweetTimeCounter(ref,lock);
        this.ref = ref;
        this.para=para;
        this.lock=lock;
        init();
    }

    public void run() {
        if (para.getMode() == 0) {// online
            inputStream = new Scanner(System.in);
            while (inputStream.hasNextLine()) {
                synchronized (ref) {
                    inputStream = new Scanner(System.in);
                    line = inputStream.nextLine();
                    setOnlineTime(line);
                    setData(line);
                    Ref.count++;
                    Ref.secondcount++;
                }
            }
        } else {
            try {
                inputStream = new Scanner(new FileInputStream("f:\\00_00.txt"));
                // outputStream = new PrintWriter(new
                // FileoutputStream("numbered.txt"));
            } catch (FileNotFoundException e) {

                System.out.println("File test1.txt was not found!");
                System.exit(0);
            }
            while (inputStream.hasNextLine()) {
                synchronized (ref) {

                    line = inputStream.nextLine();
                    while(!lock.getLock()){};
                    intervalTime(line);
                    setOfflineTime();

                    // count the total number of tweet input, for testing
                    Ref.count++;
                    Ref.secondcount++;
                    
                    
                    setData(line);
                    lock.releaseLock();
                    try {
                        // System.out.println(interval);
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // PrintWriter outputStream = null;
        // while (true) {
        // inputStream.close();
    }

    public void init() {
        synchronized (ref) {
            Ref.posTop10 = new Tweets[Ref.tweetsNumber];
            for (int i = 0; i < Ref.tweetsNumber; i++) {
                Ref.posTop10[i] = new Tweets();
            }
            Ref.negTop10 = new Tweets[Ref.tweetsNumber];
            for (int i = 0; i < Ref.tweetsNumber; i++) {
                Ref.negTop10[i] = new Tweets();
            }
            Ref.hotUser = new User[Ref.userNumber];
            for (int i = 0; i < Ref.userNumber; i++) {
                Ref.hotUser[i] = new User();
            }
            Ref.version = System.currentTimeMillis();
            this.preRef = Ref.version;
            file = new FileOutputProcessor();
            System.out.println(System.getProperty("user.dir"));
            sentiment = new Sentiment();
        }
    }

    public void setOnlineTime(String line) {
        try {
            obj = new JSONObject(line);
            this.tweetsTime = this.timeTrans.transfrom(obj
                    .getString("created_at"));
            this.TweetTimestamp.setPosition(tweetsTime.getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setOfflineTime() {
        this.tweetsTime = new Timestamp(System.currentTimeMillis());
        this.TweetTimestamp.setPosition(tweetsTime.getTime());
    }

    public void intervalTime(String line) {
        try {
            obj = new JSONObject(line);
            tweetsTime2 = tweetsTime1;
            this.tweetsTime3 = this.timeTrans.transfrom(obj
                    .getString("created_at"));
            tweetsTime1 = tweetsTime3.getTime();
            if (((tweetsTime1 - tweetsTime2) >= 0) && (tweetsTime2 != 0)) {
                interval = tweetsTime1 - tweetsTime2;
            } else
                interval = 0;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setData(String line) {
        try {
            // System.out.println(tweetsTime.toString());
            // System.out.println(tweetsTime.getTime());

            // if (true) {
            // this.TweetTimestamp.setPosition(tweetsTime.getTime());
            // this.TweetTimestamp.setPosition(System.currentTimeMillis());
            // if (this.TweetTimestamp.setPosition(tweetsTime.getTime())) {

            // this.insertPos = Ref.tweetsNumber;
            // for (int i = Ref.tweetsNumber - 1; i >= 0; i--) {
            // if (Ref.top10[i].getRetweet_count() < obj
            // .getInt("retweet_count")) {
            // this.insertPos = i;
            // } else
            // break;
            // }
            // if (this.insertPos < Ref.tweetsNumber) {
            // for (int i = Ref.tweetsNumber - 2; i >= this.insertPos; i--)
            // {
            // Ref.top10[i + 1].setTweets(Ref.top10[i]);
            // }
            // Ref.top10[insertPos].setRetweet_count(obj
            // .getInt("retweet_count"));
            // Ref.top10[insertPos].setID(obj.getString("id_str"));
            // Ref.top10[insertPos].setMessage(obj.getString("text"));
            // Ref.top10[insertPos].setTime(tweetsTime);
            // Ref.version = System.currentTimeMillis();
            // file.updateTweets();
            // }
            score = sentiment.analysis(line);
            Ref.mark = score;
            this.insertPos = Ref.tweetsNumber;
            for (int i = Ref.tweetsNumber - 1; i >= 0; i--) {
                if (Ref.posTop10[i].getScore() < score
                        || Ref.posTop10[i].getID() == null) {
                    this.insertPos = i;
                } else
                    break;
            }
            if (this.insertPos < Ref.tweetsNumber) {
                for (int i = Ref.tweetsNumber - 2; i >= this.insertPos; i--) {
                    Ref.posTop10[i + 1].setTweets(Ref.posTop10[i]);
                }
                Ref.posTop10[insertPos].setRetweet_count(obj
                        .getInt("retweet_count"));
                Ref.posTop10[insertPos].setID(obj.getString("id_str"));
                Ref.posTop10[insertPos].setMessage(obj.getString("text"));
                Ref.posTop10[insertPos].setTime(tweetsTime);
                Ref.posTop10[insertPos].setScore(score);
                Ref.version = System.currentTimeMillis();
//                System.out.println("insert a positive message");
                file.updateTweets();
            }
            this.insertPos = Ref.tweetsNumber;
            for (int i = Ref.tweetsNumber - 1; i >= 0; i--) {
                if (Ref.negTop10[i].getScore() > score
                        || Ref.negTop10[i].getID() == null) {
                    this.insertPos = i;
                } else
                    break;
            }
            if (this.insertPos < Ref.tweetsNumber) {
                for (int i = Ref.tweetsNumber - 2; i >= this.insertPos; i--) {
                    Ref.negTop10[i + 1].setTweets(Ref.negTop10[i]);
                }
                Ref.negTop10[insertPos].setRetweet_count(obj
                        .getInt("retweet_count"));
                Ref.negTop10[insertPos].setID(obj.getString("id_str"));
                Ref.negTop10[insertPos].setMessage(obj.getString("text"));
                Ref.negTop10[insertPos].setTime(tweetsTime);
                Ref.negTop10[insertPos].setScore(score);
                Ref.version = System.currentTimeMillis();
//                System.out.println("insert a negative message");
                file.updateNegTweets();
            }
            this.insertPos = Ref.userNumber;
            obj1 = obj.getJSONObject("user");
            for (int i = Ref.userNumber - 1; i >= 0; i--) {
                if (Ref.hotUser[i].getFollowers_count() < obj1
                        .getInt("followers_count")) {
                    this.insertPos = i;
                } else
                    break;
            }
            if (this.insertPos < Ref.userNumber) {
                for (int i = Ref.userNumber - 2; i >= this.insertPos; i--) {
                    Ref.hotUser[i + 1].setUser(Ref.hotUser[i]);
                }
                Ref.hotUser[insertPos].setFollowers_count(obj1
                        .getInt("followers_count"));
                Ref.hotUser[insertPos].setId_str(obj1.getString("id_str"));
                Ref.hotUser[insertPos].setName(obj1.getString("screen_name"));
                Ref.version = System.currentTimeMillis();
                file.updateUser();
            }
            // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // for (int i = 0; i < 10; i++) {
        // // System.out.println(i + " = " + Ref.top10[i].getTime());
        // // System.out.println(i + " = " +
        // // Ref.hotUser[i].getFollowers_count());
        // }
    }
}
