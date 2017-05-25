package zmq.com.jhpiego.preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zmq162 on 3/6/15.
 */
public class MyUtility {

    public static String getDate() {

        Calendar cal = Calendar.getInstance();//88
        SimpleDateFormat forDate = new SimpleDateFormat("dd-MM-yyyy");//88
        Date date = cal.getTime();//88
        String formatedDate = forDate.format(date);	//88

        return formatedDate;
    }

    public static String getTime() {

        Calendar cal = Calendar.getInstance();//88
        SimpleDateFormat forTime = new SimpleDateFormat("HH:mm:ss");//88
        Date date = cal.getTime();//88
        String formatedTime = forTime.format(date);	//88

        return formatedTime;
    }


    public static String timeDifference(long t1, long t2) {

        int sec = (int)(t1-t2) / 1000;
        System.out.println("T1 "+t1 +" T2"+t2 +" SEC "+sec);
        if(sec == 0){
           return ""+1;
        }
        return ""+sec;
    }

    /*
                          //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                           //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D1",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

     */
}
