package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Scanner;

import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.Tweets;
import models.User;
import utils.Ref;

public class DownloadFileServer extends Thread {
    Ref ref;
    Tweets tweets[];
    User users[];
    Tweets negTweets[];
    long currentVersion = 0;
    long preVersion = 0;
    InetAddress aHost = null;
    Integer socket_no = 12345;
    Socket socket = null;
    InputStream s1In;
    DataInputStream dis;
    OutputStream sout = null;
    DataOutputStream dos = null;
    String st;
    Scanner inputStream = null;
    FileInputStream fileStream = null;
    String line;
    JSONObject obj = null;
    JSONObject speed=null;
    JSONArray objArray = null;
    String path = "";

    public DownloadFileServer() {
        ref = new Ref();
        tweets = new Tweets[ref.tweetsNumber];
        users = new User[ref.userNumber];
        negTweets = new Tweets[ref.tweetsNumber];
        for (int i = 0; i < ref.tweetsNumber; i++) {
            tweets[i] = new Tweets();
        }
        for (int i = 0; i < ref.tweetsNumber; i++) {
            negTweets[i] = new Tweets();
        }
        for (int i = 0; i < ref.userNumber; i++) {
            users[i] = new User();
        }

    }
    public void updateSpeed(){
        try {
            dos.writeInt(4);
//            System.out.println("go");
            st = new String(dis.readUTF());            
            speed = new JSONObject(st);
        } catch (JSONException e) {
            e.printStackTrace();
            speed = null;
        } catch (IOException e) {
            speed=null;
            e.printStackTrace();
        }
    }
    public void run() {

        try {
            connect();
            do {               
                updateSpeed();                
                updateVersion();                
            } while (true);
        } catch (IOException e) {
            System.out.println("warning! Connect issues" + e);
        }
        try {
            closeConnect();
        } catch (IOException e) {
            System.out.println("close the Connect error");
        }
    }

    public void connect() throws IOException {
        aHost = InetAddress.getByName("localhost");
        socket = new Socket(aHost, socket_no);
        s1In = socket.getInputStream();
        dis = new DataInputStream(s1In);
        sout = socket.getOutputStream();
        dos = new DataOutputStream(sout);
    }

    public void closeConnect() throws IOException {
        dis.close();
        s1In.close();
        socket.close();
        dos.close();
        sout.close();
    }

    private void updateVersion() {
        try {
            dos.writeInt(0);
            currentVersion = dis.readLong();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("********read version failed**********");
            return;
        }
        if (currentVersion != this.preVersion) {
            // if (!update(path+ref.userPath) || !update(path+ref.tweetsPath)) {
            // return;
            // }
            if (!update(1) || !update(2) || !update(3)) {
                return;
            }
            this.preVersion = currentVersion;
        }

    }

    private boolean update(int index) {
        // System.out.println(s);
        try {
            dos.writeInt(index);
            line = dis.readUTF();
//            System.out.println(line);
            if (line.equals("")) {
                System.out.println("empty line");
                return true;  
            }
//            
            obj = new JSONObject(line);
            if (line.contains("{\"postweets\":")) {
                objArray = obj.getJSONArray("postweets");
                for (int i = 0; i < objArray.length(); i++) {

                    // System.out.println(objArray.getJSONObject(i).getString("ID"));
                    tweets[i].setID(objArray.getJSONObject(i).getString("ID"));
                    tweets[i].setMessage(objArray.getJSONObject(i).getString(
                            "message"));
                    tweets[i].setRetweet_count(objArray.getJSONObject(i)
                            .getInt("retweet_count"));
                    tweets[i].setTime(Timestamp.valueOf(objArray.getJSONObject(
                            i).getString("time")));
                    tweets[i].setScore(Float.valueOf(objArray.getJSONObject(i)
                            .getString("score")));
                    // System.out.println(i+tweets[i].time.toString());
                }
            } else if (line.contains("{\"negtweets\":")) {
                objArray = obj.getJSONArray("negtweets");
                for (int i = 0; i < objArray.length(); i++) {

                    // System.out.println(objArray.getJSONObject(i).getString("ID"));
                    negTweets[i].setID(objArray.getJSONObject(i)
                            .getString("ID"));
                    negTweets[i].setMessage(objArray.getJSONObject(i)
                            .getString("message"));
                    negTweets[i].setRetweet_count(objArray.getJSONObject(i)
                            .getInt("retweet_count"));
                    negTweets[i].setTime(Timestamp.valueOf(objArray
                            .getJSONObject(i).getString("time")));
                    negTweets[i].setScore(Float.valueOf(objArray.getJSONObject(i)
                            .getString("score")));
                    // System.out.println(i+tweets[i].time.toString());
                }
            } else {
                objArray = obj.getJSONArray("user");
                for (int i = 0; i < objArray.length(); i++) {

                    users[i].setName(objArray.getJSONObject(i)
                            .getString("name"));
                    users[i].setId_str(objArray.getJSONObject(i).getString(
                            "id_str"));
                    users[i].setFollowers_count(objArray.getJSONObject(i)
                            .getInt("followers_count"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("input Stream error");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(line);
            System.out.println("input Stream error");
            return false;
        }
        return true;
    }

    private boolean update(String s) {
        // System.out.println(s);
        try {
            fileStream = new FileInputStream(s);
            inputStream = new Scanner(fileStream);
            if (!inputStream.hasNextLine()) {
                inputStream.close();
                fileStream.close();
                System.out.println("input Stream error");
                return false;
            }
            line = inputStream.nextLine();
            System.out.println(line);
            obj = new JSONObject(line);
            if (s.contains("tweets")) {
                objArray = obj.getJSONArray("tweets");
                for (int i = 0; i < objArray.length(); i++) {

                    // System.out.println(objArray.getJSONObject(i).getString("ID"));
                    tweets[i].setID(objArray.getJSONObject(i).getString("ID"));
                    tweets[i].setMessage(objArray.getJSONObject(i).getString(
                            "message"));
                    tweets[i].setRetweet_count(objArray.getJSONObject(i)
                            .getInt("retweet_count"));
                    tweets[i].setTime(Timestamp.valueOf(objArray.getJSONObject(
                            i).getString("time")));
                    // System.out.println(i+tweets[i].time.toString());
                }
            } else {
                objArray = obj.getJSONArray("user");
                for (int i = 0; i < objArray.length(); i++) {

                    users[i].setName(objArray.getJSONObject(i)
                            .getString("name"));
                    users[i].setId_str(objArray.getJSONObject(i).getString(
                            "id_str"));
                    users[i].setFollowers_count(objArray.getJSONObject(i)
                            .getInt("followers_count"));
                }
            }
            inputStream.close();
            fileStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("input Stream error");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("input Stream error");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("input Stream error");
            return false;
        }
        return true;
    }

    public String getVersion() {
        return "{\"version\":\"" + currentVersion + "\"}";
    }
    
    public JSONObject getSpeed() {
        return speed;
    }

}
