package data;

import android.annotation.*;

import java.text.*;
import java.util.*;

import logger.*;

public class DateTime {


    // convert time from milli sec to hh mm ss
    public static String secondsTominutes(long timeInMillis){

        int hours = (int) ((timeInMillis / (1000 * 60 * 60)));
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int  seconds = (int) ((timeInMillis / 1000) % 60);

        if(hours==0)
        {
            return minutes+"m"+seconds+"s";
        }
        return hours+"h"+minutes+"m"+seconds+"s";
    }

    // convert time from milli sec to hh:mm:ss


    public static String secondsTominutesHours(long timeInMillis){

        int hours = (int) ((timeInMillis / (1000 * 60 * 60)));
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int  seconds = (int) ((timeInMillis / 1000) % 60);

        if(hours==0)
        {
            return minutes+":"+seconds;
        }
        return hours+":"+minutes+":"+seconds;
    }

    // get current date and time

    public  static String getDateTime()
    {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return  df.format(date);
    }


    // convert sec to hhmmss

    public  static double seconds_to_minutes_hours(double total_secs)
    {
        double time_str;
        int totalSecs= (int) (total_secs);
        int  hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int  seconds = totalSecs % 60;

        @SuppressLint("DefaultLocale") String temp = String.format("%02d%02d%02d",hours, minutes, seconds);

        time_str=Double.parseDouble(temp);
        Logger.log(Level.INFO,"DATETIME","get double time str="+time_str);
        return  time_str;
    }


    public static double time_to_seconds(String time)
    {
        String[] units = time.split(":"); //will break the string up into an array
        int minutes = Integer.parseInt(units[0]); //first element
        int seconds = Integer.parseInt(units[1]); //second element
        int hours=Integer.parseInt(units[2]);
        int duration =60*60*hours +60*minutes + seconds; //add up our values

        return Double.parseDouble(String.valueOf(duration));

    }


    public static String digit_to_time(String output_string)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatFrom = new SimpleDateFormat("HHmmss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTo = new SimpleDateFormat("HH:mm:ss");
        // SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        String newDateString = null;
        Date d=null;
        try {
            @SuppressLint("DefaultLocale") String formattedString = String.format("%06d", Integer.parseInt(output_string));
            Date date = formatFrom.parse(formattedString);
            newDateString = formatTo.format(date);
            // d=formatter.parse(newDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Logger.log(Level.DEBUG,"DATE TIME","date"+newDateString);
        return newDateString;
    }





}
