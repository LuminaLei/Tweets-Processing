package models;

import java.io.File;
import java.util.ArrayList;

public class Parameter {

    String version = "1.0";
    String versionMessage;
    int mode = 0; // 0 for online, 1 for offline
    int port = 12345;
    boolean isFile = false; // 0 for fileinput,1 for standard input
    String filePath = "";
    boolean isLib = false; // 0 for default path, 1 for customer lib
    ArrayList<String> libPath = new ArrayList<String>();
    ArrayList<Integer> libScore = new ArrayList<Integer>();

    File file;
    public static String offlinePara = "-offline";
    public static String onlinePara = "-online";
    public static String versionPara = "-version";
    public static String helpPara = "-help";
    public static String filePara = "-file";
    public static String PortPara = "-speedport";
    public static String tweetsportPara="-tweetsPort";
    public static String libPara = "-lib";

    public Parameter(String args[]) {
        processingPara(args);
    }

    public String getVersionMessage() {
        versionMessage = "\nThis is version " + version + "\n\n"
                + "This Program is used to help anaylze the current "
                + "growing tweets, get the new tweets online or offlne "
                + "and test the speed of each minute, hour, day and month " 
                + "\n\nFor more details, please use java Processing -help"
                + "\n\nProgramed by Danping Lei and Yingbo Wang"
                + "From University of Melbourne" 
                + "\n\nGuide by DR Aaron Harwood, Thanks you!" +
                		"\n\nif you find any problem or ideas, please contact us" +
                		"\n\nYingbo Wang \nEmail: flywyb@gmail.com" +
                		"\nDanping Lei \nEmail: dlei@student.unimelb.edu.au\n\n";
        return versionMessage;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPort(String port) {
        try {
            int port1 = Integer.valueOf(port);
            if (port1<1024 && port1>65535){
                System.err.println("port should between 1024 and 65535");
                return;
            }
            this.port=port1;
        } catch (Exception e) {
            System.err.println("speed port parameter error!");
        }
    }

    public boolean setFile(String path) {
        file = new File(path);
        if (!file.exists()) {
            System.out.println("file "+path+" not exist");
            return false;
        }
        this.filePath = path;
        return true;
    }

    public boolean addLib(String path, String score) {
        try {
            int score1=Integer.valueOf(score);
            file=new File(path);
            if(!file.exists()){
                System.err.println("lib file "+path+" not exist");
                return false;
            }
            libPath.add("path");
            libScore.add(score1);
            
        } catch (Exception e) {
            System.err.println(e
                    + "lib Parameter error! customer lib import fail");
            return false;
        }
        return true;
    }
    public int getPort(){
        return this.port;
    }
    public void processingPara(String args[]) {
        int length = args.length;
        for (int i = 0; i < length; i++) {

            if (args[i].equalsIgnoreCase(onlinePara)) {
                setMode(0);
            } else if (args[i].equalsIgnoreCase(offlinePara)) {
                setMode(1);
            } else if (args[i].equalsIgnoreCase(versionPara)) {
                System.out.println(getVersionMessage());
            } else if (args[i].equalsIgnoreCase(helpPara)) {

            } else if (args[i].equalsIgnoreCase(PortPara)) {
                if (i < length) {
                    setPort(args[i + 1]);
                }
            } else if (args[i].equalsIgnoreCase(filePara)) {
                if (i < length) {
                    if(setFile(args[i + 1]))
                        this.isFile = true;
                }
            } else if (args[i].equalsIgnoreCase(libPara)) {
                if (i < length - 1) {
                    if (addLib(args[i + 1], args[i + 2]))
                        this.isLib = true;
                }
            }
        }
    }
}
