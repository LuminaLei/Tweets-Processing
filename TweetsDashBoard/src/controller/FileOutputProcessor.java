package controller;

import java.io.*;

import utils.ObjToJson;
import utils.Ref;

public class FileOutputProcessor {

    File file;
    BufferedWriter output;
    String jsonMessage;
    
    public FileOutputProcessor() {
    }

    public boolean updateTweets() {
        jsonMessage = "";
        jsonMessage += "{\"postweets\":[";
        for (int i = 0; i < Ref.tweetsNumber; i++) {
            jsonMessage += ObjToJson.objToJsonString(Ref.posTop10[i]);
            jsonMessage += (i == Ref.tweetsNumber - 1) ? "" : ",";
        }
        jsonMessage += "]}";
        Ref.posTweetsMessage=jsonMessage;
        return true;
    }
    public boolean updateNegTweets() {
        jsonMessage = "";
        jsonMessage += "{\"negtweets\":[";
        for (int i = 0; i < Ref.tweetsNumber; i++) {
            jsonMessage += ObjToJson.objToJsonString(Ref.negTop10[i]);
            jsonMessage += (i == Ref.tweetsNumber - 1) ? "" : ",";
        }
        jsonMessage += "]}";
        Ref.negTweetsMessage=jsonMessage;
        
        return true;
    }

    public boolean updateUser() {
        jsonMessage = "";
        jsonMessage += "{\"user\":[";
        for (int i = 0; i < Ref.userNumber; i++) {
            jsonMessage += ObjToJson.objToJsonString(Ref.hotUser[i]);
            jsonMessage += (i == Ref.userNumber - 1) ? "" : ",";
        }
        jsonMessage += "]}";
        Ref.userMessage=jsonMessage;
        return true;
    }

}
