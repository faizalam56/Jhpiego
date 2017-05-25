package zmq.com.jhpiego.canvas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.BackgroundMessage;
import zmq.com.jhpiego.messages.Message;
import zmq.com.jhpiego.messages.PopUpMessage;
import zmq.com.jhpiego.messages.PopUpMessageBottom;
import zmq.com.jhpiego.other.DecisionPoint;
import zmq.com.jhpiego.other.Helper;
import zmq.com.jhpiego.other.Timer;
import zmq.com.jhpiego.preferences.MyPreference;
import zmq.com.jhpiego.preferences.MyUtility;
import zmq.com.jhpiego.preferences.UpdateScore;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.Constant;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.MySound;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 6/5/2015.
 */
public class Story3Canvas extends BaseSurface implements Runnable, Recycler {

    private int sceneNumber=-1;
    private int subSceneNumber;
    ShahSprite bgSprite;
    ShahSprite faded_bgSprite;
    ShahSprite maleChar/*, femaleChar*/;
    Message message;
    DecisionPoint dp_kickOut_club_inviteIn;
    PopUpMessage twoOptionMess;
    Helper helper;
    //PopUpInstruction popUpInstruction;
    private BackgroundMessage backgroundMessage;
    private Constant myConstant;
    Intent intent;
    private Canvas myCanvas;

    private int countDown=GlobalVariables.timerValue;
    private boolean countDownStatus=false;
    private DecisionPoint whichDecisionPoint;
    private Timer timer;

    public Story3Canvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        paint.setColor(Color.WHITE);
        myConstant=new Constant(context);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" Story3Canvas surfaceDestroyed Called...");
        }
    }

    @Override
    protected void drawSomething(Canvas g) {
        switch (sceneNumber) {
            case -1:
                myConstant.drawImageWithRotation(g);
                loadImages();
                break;

            case 0:
                scene0(g);
                break;

            default:
                break;
        }

        if(countDownStatus){
            helperCountDown();
            timer.update(g,paint);
            drawText(myCanvas,""+(countDown),width/2,height/2+(int)(GlobalVariables.yScale_factor*20),(int)(GlobalVariables.xScale_factor*50));
        }
    }
    private void drawText(Canvas canvas, String Text,int xMidPosition,int yMidPosition, int textSize) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Text,xMidPosition,yMidPosition, paint);
    }

    private void helperCountDown(){

        if(countDown>0){
            countDown--;
            timer.setVisible(true);
        }
        if(countDown==0){
            //***************** Calculating Decision Time ******************
            GlobalVariables.start_Time=System.currentTimeMillis();
            // ***********************************

            countDownStatus=false;
            helper.setVisible(false);
            // popUpInstruction.setVisible(false);

            if(whichDecisionPoint!=null){
                if(whichDecisionPoint .equals(dp_kickOut_club_inviteIn)){
                    subSceneNumber=3;
                }

                timer.setVisible(false);
                whichDecisionPoint.setActive();
            }
        }

    }

    public void scene0(Canvas g) {
        myCanvas=g;
        if(!bgSprite.sourceImage.isRecycled())bgSprite.paint(g, null);
        switch (subSceneNumber) {
            case 0:
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==1 && !message.isVisible()){
                    message.setVisible(true);
//                    maleChar.setPosition(dp_kickOut_club_inviteIn.getX() - maleChar.getWidth(), maleChar.getY());
                    maleChar.setPosition(-10,maleChar.getY());
                    faded_bgSprite.setVisible(true);
                    dp_kickOut_club_inviteIn.setPosition(maleChar.getX()+maleChar.getWidth(),dp_kickOut_club_inviteIn.getY());
                    dp_kickOut_club_inviteIn.messPosX=dp_kickOut_club_inviteIn.bgImage.getX()+dp_kickOut_club_inviteIn.bgImage.getWidth()/2-(int)paint.measureText(dp_kickOut_club_inviteIn.messString)/2;
                    delayCounter=0;
                }
                if(delayCounter==1 && message.isVisible()){
                    message.setVisible(false);
                   delayCounter=0;
                   subSceneNumber=1;
                }
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_kickOut_club_inviteIn.update(g,paint);
                //message.update(g,paint);
                break;
            case 1:
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==0 && !twoOptionMess.isVisible()){
                    twoOptionMess.setVisible(true);
                    delayCounter=0;
                    isDelay=false;
                }
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_kickOut_club_inviteIn.update(g,paint);
                twoOptionMess.update(g,paint);
                break;

            case 2:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_kickOut_club_inviteIn.update(g,paint);
                if (helper != null) {
                   helper.update(g,paint);
                }
               // popUpInstruction.update(g,paint);
                //helper
                break;
            case 3:
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    dp_kickOut_club_inviteIn.update(g, paint);
                }
                break;
            case 4:
                if(!bgSprite.sourceImage.isRecycled()) {
                    Log.d("mytag","....= "+bgSprite.sourceImage.isRecycled());
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g, null);
                    twoOptionMess.update(g, paint);
                }
                break;

            case 5:
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.update(g, paint);
                }
                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context, R.raw.tap_sound);
        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);

        switch (sceneNumber) {
            case 0:
                switch (subSceneNumber) {
                    case 0:
                        break;
                    case 1:
                        if(twoOptionMess.isVisible()&&twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Score Update............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5,3}));
                            // ***********************************

                            //***********Helper Count Down**********
                                countDownStatus=true;
                                        if(whichDecisionPoint!=null){
                                        whichDecisionPoint.recycle();
                                        }
                                        countDown=GlobalVariables.timerValue;
                                        whichDecisionPoint=dp_kickOut_club_inviteIn;
                            //*************************************

                            twoOptionMess.setVisible(false);
                            subSceneNumber=2;
                        }
                        if(twoOptionMess.isVisible()&&twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                           //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************


                            dp_kickOut_club_inviteIn.setActive();
                            subSceneNumber=3;
                        }
                        break;
                    case 2:
                        if (helper.isVisible() && helper.isUsed() && helper.actionButton.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            dp_kickOut_club_inviteIn.setActive();
                            subSceneNumber = 3;

                        }
                        if (helper.isVisible() && !helper.isUsed() && countDownStatus) {
                            //check helper touch and set action
                            for (int i = 0; i < helper.helper_sprite_array.length; i++) {
                                ShahSprite shahSprite = helper.helper_sprite_array[i];
                                if (shahSprite.getDstRect().intersect(touchrecF)) {

                                    timer.setVisible(false);
                                    countDownStatus=false;
                                    countDown=GlobalVariables.timerValue;

                                    helper.setUsed(true);
                                    helper.setMessStringForIndex(i);
                                    break;
                                }
                            }
                        }
                        break;
                    case 3:
                        if (dp_kickOut_club_inviteIn.isVisible() && dp_kickOut_club_inviteIn.getBtnAtIndext(0).getDstRect().intersect(touchrecF)) {

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Kick out");
                            // ***********************************

                            //*********Score Update............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{8, 6, 4}));
                            // ***********************************

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                             //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D2",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dp_kickOut_club_inviteIn.setVisible(false);
                            //twoOptionMess.recycle();
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "", R.drawable.play_againbtn, R.drawable.exitbtn);
                            twoOptionMess.mess_bgImage.sourceImage = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.gamechanger_message_bg);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            twoOptionMess.setPositionOnDemand();
                            subSceneNumber = 4;
                        }

                        if (dp_kickOut_club_inviteIn.isVisible() && dp_kickOut_club_inviteIn.getBtnAtIndext(1).getDstRect().intersect(touchrecF)) {

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Go to Klub Image");
                            // ***********************************

                            //*********Score Update............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{8,7,7}));
                            // ***********************************

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context, "D2", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // jump to next story
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            temp.putExtra("maleX", maleChar.getX());
                            temp.putExtra("maleY", maleChar.getY());

                            ((Activity) context).setResult(555, temp);
                            ((Activity) context).finish();
                            this.recycle();
                        }
                        if (dp_kickOut_club_inviteIn.isVisible() && dp_kickOut_club_inviteIn.getBtnAtIndext(2).getDstRect().intersect(touchrecF)) {

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Invite in");
                            // ***********************************

                            //*********Score Update............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{4,5,4}));
                            // ***********************************

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context, "D2", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);

                            Intent temp = new Intent();
                            temp.putExtra("maleX", maleChar.getX());
                            temp.putExtra("maleY", maleChar.getY());


                            GlobalVariables.INVITE_IN=20;
                            ((Activity) context).setResult(GlobalVariables.INVITE_IN, temp);
                            ((Activity) context).finish();
                            this.recycle();
                        }
                        break;
                    case 4:
                        if(twoOptionMess.isVisible()&&twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){
                            //Play Again

                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context, "endTime", MyUtility.getTime());
                            //*************************************************

                            //********* Saving Scores in Database***************
                            Utility.saveDataInDB(context);
                            //**************************************************

                            //*************** cleared Prefrences *************
                            //new File("/data/data/zmq.com.jhpiego/shared_prefs/temp_data.xml").delete();
                            // ***************************************************************

                            MyPreference.removeKeys(context,1);

                            //*************** save preferences *************
                            MyPreference.saveStringKeyValue(context, "startTime", MyUtility.getTime());
                            MyPreference.saveStringKeyValue(context, "sessionsId", MyPreference.getStringValue(context,"sessionsId"));
                            // ***************************************************************

                            delayCounter = 0;
                            isDelay = false;
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();

                            GlobalVariables.initialize();

                            ((Activity) context).setResult(Activity.RESULT_OK, temp);
                            ((Activity) context).finish();
//                             this.recycleImages();
                            this.recycle();
                        }
                        if(twoOptionMess.isVisible()&&twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){

                            //*********Score Update............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"While it might be a good idea to give yourself time and space to think about the situation, kicking your best friend out after they have made themselves vulnerable to you is mean. It also indicates that you aren’t taking any responsibility for what has happened—you actually sent the text that got this situation started!");
                            backgroundMessage.setVisible(true);
                            subSceneNumber=5;

                        }
                        break;

                    case 5:
                        //recycler and call finish
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context,"endTime", MyUtility.getTime());
                            //*************************************************


                            //********* Saving Scores in Database***************
                            Utility.saveDataInDB(context);
                            //**************************************************

                            //*************** cleared Prefrences *************
                            //new File("/data/data/zmq.com.jhpiego/shared_prefs/temp_data.xml").delete();
                            // ***************************************************************
                            MyPreference.removeKeys(context,0);

                            delayCounter = 0;
                            isDelay = false;
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);

                            GlobalVariables.initialize();

                            ((Activity) context).setResult(123, new Intent());
                            ((Activity) context).finish();
//                            this.recycleImages();
                            this.recycle();
                        }

                        break;
                    default:
                        break;
                }
                break;

        }

        return super.onTouchEvent(event);
    }
    boolean initialiseThreadLoad=true;
    Thread localThread=null;
    public void loadImages(){
        if(initialiseThreadLoad){
            localThread=new Thread(this);
            localThread.start();

//            try {
//                localThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println("Start Joinig.......");
            initialiseThreadLoad=false;
        }
    }


//    boolean initialiseThreadRecycle=true;
//    Thread localThread1=null;
//    public void recycleImages(){
//        if(initialiseThreadRecycle){
//            localThread1=new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//            localThread1.start();
//            initialiseThreadRecycle=false;
//        }
//    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1bg02));
        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.boywrongmsg));
        int tempMaleX = intent.getIntExtra("maleX", width / 2 - maleChar.getWidth() / 2);
        int tempMaleY = intent.getIntExtra("maleY", height - maleChar.getHeight());

        maleChar.setPosition(tempMaleX, tempMaleY);

        message=new Message(R.drawable.background_mess, "If you are confused then try some other options");
        message.setMessPosY(message.messPosY + (int)(GlobalVariables.yScale_factor*50));
        message.setVisible(false);
        dp_kickOut_club_inviteIn=new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.kick_out_sprite, R.drawable.goto_club_btn_sprite, R.drawable.invite_in_btn_sprite});
//        dp_kickOut_club_inviteIn.setVisible(false);
        dp_kickOut_club_inviteIn.messString="If you are confused then try some other options";
        helper = new Helper(new String[]{
                "Why don’t you guys come to the Klub… everyone is here! ",
                "If you invite them in you can talk about what happened and explain your mistake!",
                "If you’re not sure what you want, being alone together might be risky.",
                "Sometimes the best loves begin with friendship!"
        });

        twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
        twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
        twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
        twoOptionMess.setVisible(false);

//        popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
//                "Click on any one of the helper to seek their advice"
//        /*GlobalVariables.getResource.getString(R.string.scene0_popup)*/);

        System.out.println("End Joinig.......");
        Log.d("msg", "End Joinig.......");
        timer=new Timer();

        sceneNumber=0;
        subSceneNumber=0;
    }
    @Override
       public void recycle() {

        drawSomething(myCanvas);

        bgSprite.recycle();
        maleChar.recycle();
        faded_bgSprite.recycle();

        message.recycle();
        twoOptionMess.recycle();
        timer.recycle();
        //popUpInstruction.recycle();
        if(backgroundMessage!=null)backgroundMessage.recycle();

        dp_kickOut_club_inviteIn.recycle();

        helper.recycle();

    }


}
