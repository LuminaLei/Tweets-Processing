package models;

import java.sql.Timestamp;

import utils.Ref;

public class Tweets {
    public String ID;
    public Timestamp time;
    public String message;
    public int retweet_count = 0;
    public float score = 0.0f;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public void setTweets(Tweets tweets) {
        this.setMessage(tweets.getMessage());
        this.setID(tweets.getID());
        this.setRetweet_count(tweets.getRetweet_count());
        this.setTime(tweets.getTime());

    }

}
