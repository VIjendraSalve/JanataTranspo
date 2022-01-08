package com.wht.janatatraspo.my_library;/*
package com.wht.community.my_library;

import android.content.Context;

import com.wht.seaamigo.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class FormatedDate
{

    private Context context;
    private static FormatedDate instance;

    public static FormatedDate getInstance(Context context) {
        if (instance == null) {
            instance = new FormatedDate(context);
        }
        return instance;
    }

    public FormatedDate(Context context) {
        this.context=context;

    }
    public String formatToYesterdayOrToday(String date) {

        //System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
        //System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
        //System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
        //System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

        //Log.d("my tag", "formatToYesterdayOrToday: "+date);

        DateTime dateTime = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTimeFormatter timeFormatter = org.joda.time.format.DateTimeFormat.forPattern("hh:mm a");
        DateTimeFormatter timedateFormatter = org.joda.time.format.DateTimeFormat.forPattern("d-MMM-yy hh:mm a");

        //for only calculate day ago
        Date past=null;
        Date now=null;
        try {
            past = format.parse(date);
            now = new Date();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return context.getResources().getString(R.string.today)+" " + timeFormatter.print(dateTime);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate()))
        {
            return context.getResources().getString(R.string.yesterday)+" " + timeFormatter.print(dateTime);
        }else if (day==2 || day==3)
        {
            return day+" "+context.getResources().getString(R.string.day_ago)+" "+ timeFormatter.print(dateTime);
        }else {
            return timedateFormatter.print(dateTime);
        }
    }

    public boolean checkInBetween(String startdate, String endDate, String realDate){

        DateTime StartDate = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(startdate);
        DateTime EndDate = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(endDate);
        DateTime RealDate = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(realDate);

        boolean is2Between1And3 = ( ( RealDate.isAfter( StartDate ) ) && ( RealDate.isBefore( EndDate ) ) );

        return is2Between1And3;
    }
}
*/
