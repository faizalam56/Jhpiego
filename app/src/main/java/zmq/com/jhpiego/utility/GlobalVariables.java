package zmq.com.jhpiego.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;

import zmq.com.jhpiego.R;

public class GlobalVariables {
	
public static Resources getResource;
    public static Context context;
    //public static int image_scale_factor=1;
    public static final int REPLAY=10;
    public static  int SWITCH_SCENE=2;
    public static  int GET_HOT_HEAVY=3;
    public static  int SCENE_4_5=15;
    public static  int INVITE_IN=12;
    public static  int AFTER_MATH=21;
    public static  int GAME_A=1000;

    public static float xScale_factor;
    public static float yScale_factor;
    public static  Paint paint;
    public static int width;
    public static int height;
    public static int sleepTime=100;
    public static int timerValue=100;

    public static int Audio_File_Name= R.raw.a;
    public static int selectedPlayer=999;
    public static Intent myIntent;
    public static long start_Time=0;
    public static long end_Time=0;

    public static void initialize(){

        SWITCH_SCENE=2;
        GET_HOT_HEAVY=3;
         SCENE_4_5=15;
        INVITE_IN=12;
        AFTER_MATH=21;
        GAME_A=1000;

    }
}
