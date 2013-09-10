package utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeTransform {
    Timestamp time = null;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzzzz yyyy",
            Locale.ENGLISH);
    Date date;

    public Timestamp transfrom(String s) {
        //System.out.println(s);
        
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        
        return (new Timestamp(date.getTime()));
    }
}
