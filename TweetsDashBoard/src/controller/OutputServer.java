package controller;

import models.CounterArray;
import utils.Ref;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;

public class OutputServer extends Thread {
    JSONObject obj;
    String jsonString;
    ServerSocket s = null;
    int socket_no;
    Socket socket = null;
    OutputStream sout = null;
    DataOutputStream dos = null;
    InputStream s1In = null;
    DataInputStream dis = null;
    float monthSentiment = Ref.monthScore/Ref.monthCounter;
    float daySentiment = Ref.dayScore/Ref.dayCounter;
    float hourSentiment = Ref.hourScore/Ref.hourCounter;
    float minuteSentiment = Ref.minuteScore/Ref.minuteCounter;
    float secondSentiment = Ref.secondScore/Ref.secondCounter;
    int request = 0;
    Ref ref;
    public OutputServer(Ref ref,int port) {
        this.ref=ref;
        this.socket_no=port;
        obj = null;
        jsonString = "";
        this.setServer();

    }

    public void setServer() {
        try {
            s = new ServerSocket(socket_no);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        
//        do {
//            display();
//            System.out.println(toJson());
//            try {
//                Thread.sleep(1000);       
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } while (obj==null);
//        
        
        do {
            try {
                System.out.println("waiting at port "+socket_no );
                socket = s.accept();
                sout = socket.getOutputStream();
                dos = new DataOutputStream(sout);
                s1In = socket.getInputStream();
                dis = new DataInputStream(s1In);
                try {
                    do {
//                        System.out.println("requesting.." );
                        synchronized (ref) {
                            request = dis.readInt();
//                            System.out.println("request"+request);
                            if (request == 0) {
                                dos.writeLong(ref.version);
                            } else if (request == 1) {
                                dos.writeUTF(ref.posTweetsMessage);
                            } else if (request == 2) {
                                dos.writeUTF(ref.negTweetsMessage);
                            } else if(request==3){
                                dos.writeUTF(ref.userMessage);
                            }else if(request==4){
//                                System.out.println(toJson());
                                dos.writeUTF(toJson());
                                }
                            }
                            Thread.sleep(100);
                    } while (true);
                } catch (SocketException e) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        } while (true);

    }

    private String toJson() {
        monthSentiment = (Ref.monthCounter==0)?0:Ref.monthScore/Ref.monthCounter;
        daySentiment = (Ref.dayCounter==0)?0:Ref.dayScore/Ref.dayCounter;
        hourSentiment = (Ref.hourCounter==0)?0:Ref.hourScore/Ref.hourCounter;
        minuteSentiment = (Ref.minuteCounter==0)?0:Ref.minuteScore/Ref.minuteCounter;
        
        jsonString = "{" + "\"month\":" + Ref.monthCounter + "," + "\"day\":"
                + Ref.dayCounter + "," + "\"hour\":" + Ref.hourCounter
                + "," + "\"minute\":" + Ref.minuteCounter 
                + "," + "\"monthSentiment\":" +"\""
                + monthSentiment +"\""
                + "," + "\"daySentiment\":" +"\""
                + daySentiment+"\""
                + "," + "\"hourSentiment\":" +"\""
                +hourSentiment+"\""
                + "," + "\"minuteSentiment\":" +"\""
                + minuteSentiment +"\""
                + "}";
        return jsonString;
    }

    private void display() {
        System.out.println("month traffic = " + Ref.monthCounter);
        System.out.println("day traffic = " + Ref.dayCounter);
        System.out.println("hour traffic = " + Ref.hourCounter);
        System.out.println("minute traffic = " + Ref.minuteCounter);
        System.out.println("second traffic = " + Ref.secondCounter);
        System.out.println("month sentiment = " + monthSentiment);
        System.out.println("day sentiment = " + daySentiment);
        System.out.println("hour sentiment = " + hourSentiment);
        System.out.println("minute sentiment = " + minuteSentiment);
        System.out.println("second sentiment = " + secondSentiment);
        System.out.println("count = " + Ref.count);
        // System.out.println("secondcount = "+
        // FileInputProcessor.secondcount);

    }
}
