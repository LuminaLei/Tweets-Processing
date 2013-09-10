package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sentiment {
    String sentence = "hello! Wonderful World,  zestful devastatingly beautiful of unrivalled beauty ";
    ArrayList pos, pos1, neg, neg1,level;
    Scanner inputStream = null;
    String line = null;
    float score=0.0f;
    static String path1= "pos.txt";
    static String path2= "pos1.txt";
    static String path3= "neg.txt";
    static String path4= "neg1.txt";
    static String path5= "level.txt";
    /**
     * @param args
     */
    public static void main(String[] args) {
        Sentiment a = new Sentiment();
        System.out.println(a.sentence + " : " + a.analysis(a.sentence));

    }

    public float analysis(String line) {
        score=0.0f;
        count(line,pos,1);
        count(line,pos1,2);
        count(line,neg,-1);
        count(line,neg1,-2);
        return score;
    }
    public void count(String line, ArrayList array, float smallScore){
        for(int i=0;i<array.size();i++){
            if(line.contains(array.get(i).toString().trim())) score+=smallScore;
        }
    }
    
    public Sentiment() {
        pos = new ArrayList();
        pos1 = new ArrayList();
        neg = new ArrayList();
        neg1 = new ArrayList();
        level=new ArrayList();
        loadLib(pos,path1);
        loadLib(pos1,path2);
        loadLib(neg,path3);
        loadLib(neg1,path4);
        loadLib(level,path5);
        //display();
    }

    private void display() {
       for(int i=0;i<pos.size();i++){
           System.out.println(pos.get(i));
       }
        
    }

    public void loadLib(ArrayList array,String path){
        try {
            inputStream = new Scanner(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
        while (inputStream.hasNextLine()) {
            array.add(inputStream.nextLine());
        }
        inputStream.close();
    }

}
