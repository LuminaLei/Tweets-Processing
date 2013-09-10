package utils;

import java.sql.Timestamp;

import models.Tweets;
import models.User;

public class Ref {

    public static int day = 30;
    public static int hour = 24;
    public static int minute = 60;
    public static int second = 60;
    public static int transMsToSeconds = 1000;
    public static int arrayLength = day * hour * minute * second;
    // public static array counterArray
    public static int[] counterArray = new int[arrayLength];
    public static int[] scoreArray = new int[arrayLength];
    public static int monthCounter = 0; // Defined the per-month counter
    public static int dayCounter = 0; // Defined the per-day counter
    public static int hourCounter = 0; // Defined the per-hour counter
    public static int minuteCounter = 0; // Defined the per-minute counter
    public static int secondCounter = 0; // Defined the per-second counter
    public static long startT = 0; // The start time
    public static long currentT = 0; // The current counter reference
    public static int lastSecond = 0; // array reference of last second
    public static int lastMin = 0; // array reference of last second in 1 minute
    public static int lastHour = 0; // array reference of last second in 1 hour
    public static int lastDay = 0; // array reference of last second in 1 day
    public static int lastMonth = 0; // array reference of last second in 1
                                     // month
    public static Timestamp startTime;
    public static int count = 0; // the total number of tweets, for testing
    public static int secondcount = 0;    
    public static int tweetsNumber=10;
    public static int userNumber=10;
    public static Tweets[] posTop10;
    public static Tweets[] negTop10;
    public static float mark = 0;
    
    public static float monthScore = 0; // Defined the per-month counter
    public static float dayScore = 0; // Defined the per-day counter
    public static float hourScore = 0; // Defined the per-hour counter
    public static float minuteScore = 0; // Defined the per-minute counter
    public static float secondScore = 0; // Defined the per-second counter
    
    public static User[] hotUser;
    public static long version=0l;
    public static String tweetsPath="tweets.txt";
    public static String userPath = "users.txt";
    public static String path;
    
    public static String posTweetsMessage="";
    public static String negTweetsMessage="";
    public static String userMessage="";
}
