package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Scanner;
import models.CounterArray;
import models.Parameter;
import models.SecondTimer;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Lock;
import utils.Ref;

public class FileProcessor {
    FileInputProcessor in;
    OutputServer out;
    Ref ref;
    public FileProcessor(Parameter para) {
        Lock lock=new Lock();
        ref = new Ref();
        CounterArray countArray = new CounterArray(ref,lock);
        countArray.start();
        SecondTimer timer = new SecondTimer(countArray);
        timer.start();
        in = new FileInputProcessor(ref, para,lock);
        in.start();
        out = new OutputServer(ref,para.getPort());
        out.start();
    }


}
