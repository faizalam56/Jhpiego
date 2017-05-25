package zmq.com.jhpiego.girlcanvas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.File;
import java.util.Random;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.canvas.BaseSurface;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.BackgroundMessage;
import zmq.com.jhpiego.messages.Message;
import zmq.com.jhpiego.messages.MythMessage;
import zmq.com.jhpiego.messages.PopUpInstruction;
import zmq.com.jhpiego.messages.PopUpMessage;
import zmq.com.jhpiego.messages.PopUpMessageBottom;
import zmq.com.jhpiego.other.DecisionPoint;
import zmq.com.jhpiego.other.Helper;
import zmq.com.jhpiego.other.Timer;
import zmq.com.jhpiego.preferences.MyPreference;
import zmq.com.jhpiego.preferences.MyUtility;
import zmq.com.jhpiego.preferences.UpdateScore;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by zmq161 on 24/8/15.
 */
public class G_AfterMathCanvas extends BaseSurface implements Runnable, Recycler {

    private int sceneNumber;
    private int subSceneNumber;
    ShahSprite bgSprite;
    ShahSprite faded_bgSprite;
    ShahSprite maleChar/*, femaleChar*/;
    Message message;
    DecisionPoint dp_ignoreandPray_gotoclinic;
    PopUpMessage twoOptionMess;
    Helper helper;
    PopUpInstruction popUpInstruction;
    private BackgroundMessage backgroundMessage;
    private MythMessage mythMessage;

    private Canvas myCanvas;
    // private int controlSwitching=0;

    private int countDown= GlobalVariables.timerValue;
    private boolean countDownStatus=false;
    private DecisionPoint whichDecisionPoint;
    private Timer timer;
    private String randomMythMesg="",dialogueMsg="",gameEndMsg="";
    private String sub_dialogueMsg="",sub_gameEndMsg="";
    private boolean isEndMsg=false;
    private Paint myPaint=null;
    private int rand = 0;

    public G_AfterMathCanvas(Context context, Intent intent) {
        super(context);
        paint.setColor(Color.WHITE);

//        paint.setTextAlign(Paint.Align.CENTER);

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.bathroom_bg));
        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_boy_looking_down));
        int tempMaleX = intent.getIntExtra("maleX", width / 2 - maleChar.getWidth() / 2);
        int tempMaleY = intent.getIntExtra("maleY", height - maleChar.getHeight());

        maleChar.setPosition(tempMaleX, tempMaleY);
        maleChar.setVisible(true);

        message = new Message(R.drawable.inside_clinic_txt_area, "What will you do now?");
        message.mess_bgImage.setPosition(13,15);
       // message.setMessPosY(message.messPosY + 50);


        dp_ignoreandPray_gotoclinic=new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.ignore_n_pray, R.drawable.gotoclinic});
        dp_ignoreandPray_gotoclinic.setVisible(false);

        helper = new Helper(new String[]{
                "This is serious. You need to get a pregnancy test from the chemist right away. ",
                "You need to get to the clinic right away",
                "Come into the clinic ASAP. I can see you today.",
                "Oh no. Wait for a few more days… maybe it’s just stress."
        });
        helper.setVisible(false);

        twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
        twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
        twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight() / 2);
        twoOptionMess.setVisible(false);

//        innerThought_top_right = new InnerThought(R.drawable.inner_thought_topright, "It feels so good to be wanted");
//        innerThought_top_right.setVisible(false);

        popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                "2 weeks later your menses is 5 days late. Could it be that night with Joseph?…What will you do next?"
        /*GlobalVariables.getResource.getString(R.string.scene0_popup)*/);
        popUpInstruction.setVisible(true);

        myPaint=new Paint();
        myPaint.setColor(Color.BLACK);
        int scaledSize = GlobalVariables.getResource.getDimensionPixelSize(R.dimen.text_size);
        myPaint.setTextSize(scaledSize);

        timer=new Timer();
        isDelay=true;
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" AfterMathCanvas surfaceDestroyed Called...");
        }
    }
    @Override
    protected void drawSomething(Canvas g) {
        switch (sceneNumber) {
            case 0:
                scene0(g);
                break;
            case 1:
                scene1(g);
                break;
            case 2:
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

            if(whichDecisionPoint!=null){
                if(whichDecisionPoint .equals(dp_ignoreandPray_gotoclinic)){
                    subSceneNumber=2;

                }

                timer.setVisible(false);
                whichDecisionPoint.setActive();
            }
        }

    }

    public void scene0(Canvas g) {
        myCanvas=g;

        bgSprite.paint(g, null);
        switch (subSceneNumber) {
            case 0:
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==4) {
                    maleChar.setPosition(-10, maleChar.getY());
                    dp_ignoreandPray_gotoclinic.setPosition(maleChar.getX()+maleChar.getWidth(),dp_ignoreandPray_gotoclinic.getY());
                    dp_ignoreandPray_gotoclinic.messPosX=dp_ignoreandPray_gotoclinic.bgImage.getX()+dp_ignoreandPray_gotoclinic.bgImage.getWidth()/2-(int)paint.measureText(dp_ignoreandPray_gotoclinic.messString)/2;
                    dp_ignoreandPray_gotoclinic.setVisible(true);

                    faded_bgSprite.setVisible(true);
                    dp_ignoreandPray_gotoclinic.setVisible(true);
                    popUpInstruction.setVisible(false);
                    delayCounter = 0;
                    subSceneNumber=1;
                }
                maleChar.paint(g,null);
                //faded_bgSprite.paint(g,null);
                popUpInstruction.update(g,paint);
                //backgroundMessage.update(g,paint);
                // maleChar.paint(g,null);

                break;
            case 1:
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 ){
                    twoOptionMess.setVisible(true);
                    delayCounter=0;
                    backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"You should consider the use of long-acting contraceptives. These contraceptives are safe for most university students to use, and they will provide continuous protection against pregnancy. Condoms should also be used consistently to prevent STIs/HIV. FOR MORE INFORMATION, consult the university health system. The doctor can offer some information about contraceptive options, with the advice that the student should return next week for follow-up to choose the best contraceptive method for her.");
                    backgroundMessage.setVisible(false);
                    isDelay=false;
                }
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_ignoreandPray_gotoclinic.update(g,paint);
                twoOptionMess.update(g,paint);
                //popUpInstruction.update(g,paint);
                helper.update(g,paint);
                break;

            case 2:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_ignoreandPray_gotoclinic.update(g,paint);
                break;

            case 3:
                maleChar.paint(g,null);
                faded_bgSprite.paint(g,null);
                mythMessage.update(g,paint);
                backgroundMessage.update(g,paint);
                break;

            case 4:
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g, null);
                    twoOptionMess.update(g, paint);

                    backgroundMessage.update(g, paint);
                }
                break;

            case 5:
                maleChar.paint(g, null);
                faded_bgSprite.paint(g,null);
                backgroundMessage.update(g,paint);
                break;

            case 6://Go to clinic
                //isDelay=false;


                if(isDelay){
                    delayCount();
                }

                if(delayCounter==8 && !isEndMsg){
                    message.setMessString(sub_dialogueMsg);

                   if(rand!=0){
                       isEndMsg=true;
                       delayCounter=0;
                   }

                }
                if(rand==0 && delayCounter==18 && !isEndMsg){
                    sub_dialogueMsg="...If not, please let him know that he also needs STI check up, just to be sure hie is okay.";
                    message.setMessString(sub_dialogueMsg);
                    isEndMsg=true;
                    delayCounter=0;
                }
                if(delayCounter==10 && !backgroundMessage.isVisible() && isEndMsg){
                    paint.setColor(Color.WHITE);

                    backgroundMessage.setMessString(gameEndMsg);
                    backgroundMessage.actionButton.setVisible(false);
                    backgroundMessage.setVisible(true);
                    faded_bgSprite.setVisible(true);
                    delayCounter=0;

                }

                if(delayCounter==10 && backgroundMessage.isVisible() && isEndMsg){
                    backgroundMessage.actionButton.setVisible(true);
                    backgroundMessage.setMessString(sub_gameEndMsg);
                    isDelay=false;
                }
                message.update(g,myPaint);
                faded_bgSprite.paint(g,null);
                backgroundMessage.update(g,paint);

                break;

        }
    }
    public void scene1(Canvas g) {
        switch (subSceneNumber) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }
  private void setRandomMythMesg() {

      rand = new Random().nextInt(3);

      String temMyth_DownEasy[] = {
              "You can’t get pregnant if you only had sex once, so there is no need to run to the clinic",
              "If you were forced into sex, you can’t get pregnant. So what if your period is late",
              "You won’t get an STI since your BFF is clean."
      };

      String doctor_dialogue[] = {
              "Oh dear! Listening to your situation it seems you could end up getting pregnant, so here take these Emergency conceptive pills. This should work and come back again after a week for...",
              "Oh dear! It looks like a symptom of STI. You have done the right thing to come. First we will examine you thoroughly  & then will put you on an appropriate treatment...",
              "We have conducted all the tests & they have come out to be negative, but I would suggest that you should always take precaution by using a condom. I think you should come for..."
      };

      String game_end_message[] = {
              "In addition to the risk of pregnancy, you will also need to consider the risk of STIs and HIV. We will take some tests for these infections, and you will need to come back next week for your results. In the meantime, please use condoms when having sex. I am also giving you some information about contraceptives...",
              "You should consider the use of long-acting contraceptives. These contraceptives are safe for most university students to use, and they will provide continuous protection against pregnancy. Condoms should also be used consistently to prevent STIs/HIV. FOR MORE INFORMATION,...",
              "You should consider the use of long-acting contraceptives. These contraceptives are safe for most university students to use, and they will provide continuous protection against pregnancy. Condoms should also be used consistently to prevent STIs/HIV. FOR MORE INFORMATION,..."
      };
      randomMythMesg = temMyth_DownEasy[rand];
      dialogueMsg = doctor_dialogue[rand];
      gameEndMsg = game_end_message[rand];

      if (rand == 0) {

          sub_dialogueMsg = "...another round of tests. Just to make sure. We will also evaluate you for STIs and HIV, just to be sure that we address these health issues as well. Is your partner with you today?...";
          sub_gameEndMsg = "...There is a range of contraceptives that are safe for your use. Many of them are long-acting and they give you the convenience of being in place, providing protection against pregnancy, so you will be protected against unintended pregnancy for an extended period of time. Read this information, and we can discuss the options that are best for you when you return next week.";
      }
      if (rand == 1) {
          sub_dialogueMsg="...along with some counseling";
          sub_gameEndMsg = "...consult the university health system. The doctor can offer some information about contraceptive options, with the advice that the student should return next week for follow-up to choose the best contraceptive method for her.";
      }
      if (rand == 2) {
          sub_dialogueMsg ="...another counseling session.";
          sub_gameEndMsg = "...consult the university health system. The doctor can offer some information about contraceptive options, with the advice that the student should return next week for follow-up to choose the best contraceptive method for her.";
      }

      message.setMessString(dialogueMsg);
      message.setMessTextPosition(18,20);
      paint.setColor(Color.BLACK);
  }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);

        switch (sceneNumber) {
            case 0:
                switch (subSceneNumber) {
                    case 0:

                        break;
                    case 1:
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {
                            //faded_bgSprite.setVisible(false);
                            //*********Update Score.............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7, 5, 3}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                            //popUpInstruction.setVisible(true);
                            helper.setVisible(true);

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dp_ignoreandPray_gotoclinic;
                            //*************************************


                        }

                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            dp_ignoreandPray_gotoclinic.setActive();
                            subSceneNumber=2;
                            twoOptionMess.setVisible(false);
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
                        if (helper.isVisible() && helper.isUsed() && helper.actionButton.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            dp_ignoreandPray_gotoclinic.setActive();
                            helper.setVisible(false);
                            subSceneNumber=2;
                        }
                        break;
                    case 2:
                        if(dp_ignoreandPray_gotoclinic.isVisible() && dp_ignoreandPray_gotoclinic.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D15",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{3,4,3}));
                            // ***********************************

                            setRandomMythMesg();
                            paint.setColor(Color.WHITE);

                            mythMessage = new MythMessage(R.drawable.background_mess,randomMythMesg, R.drawable.agree, R.drawable.disagree,R.drawable.dont_know);
                            mythMessage.setMessPosY(mythMessage.messPosY + 50);
                            backgroundMessage.setVisible(false);
                            faded_bgSprite.setVisible(true);
                            subSceneNumber=3;
                        }
                        if(dp_ignoreandPray_gotoclinic.isVisible() && dp_ignoreandPray_gotoclinic.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D15",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{9,8,7}));
                            // ***********************************
                            setRandomMythMesg();

                            isDelay=true;
                            delayCounter=0;
                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_clinic_inside);
                            bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
                            faded_bgSprite.setVisible(false);
                            subSceneNumber=6;
                        }
                        break;

                    case 3:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF) ){
                            backgroundMessage.setVisible(false);
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "Are you sure?", R.drawable.yesbtn, R.drawable.nobtn);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            faded_bgSprite.setVisible(true);
                            subSceneNumber=4;
                        }



                        if(mythMessage.isVisible() && mythMessage.actionButton1.getDstRect().intersect(touchrecF)){

                            ///*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{0}));
                            // ***********************************

                            backgroundMessage.setMessString("Hmm…not quite true!\n" +
                                    "STIs can also occur anytime you have sex without using a condom.You cannot assume that your partner is “clean” just because she is your BFF.");
                            backgroundMessage.setVisible(true);
                            faded_bgSprite.setVisible(true);
                            mythMessage.setVisible(false);
                            //controlSwitching=1;
                        }
                        if(mythMessage.isVisible() && mythMessage.actionButton2.getDstRect().intersect(touchrecF)){

                            ///*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{10}));
                            // ***********************************

                            backgroundMessage.setMessString("Congratulations! Correct!\n" +
                                    "STIs can also occur anytime you have sex without using a condom.You cannot assume that your partner is “clean” just because she is your BFF.");
                            backgroundMessage.setVisible(true);
                            faded_bgSprite.setVisible(true);
                            mythMessage.setVisible(false);
                            //controlSwitching=1;
                        }
                        if(mythMessage.isVisible() && mythMessage.actionButton3.getDstRect().intersect(touchrecF)){

                            ///*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{4}));
                            // ***********************************

                            backgroundMessage.setMessString("STIs can also occur anytime you have sex without using a condom.You cannot assume that your partner is “clean” just because she is your BFF.");
                            backgroundMessage.setVisible(true);
                            faded_bgSprite.setVisible(true);
                            mythMessage.setVisible(false);
                            //controlSwitching=1;
                        }

                        break;
                    case 4:
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){
                            backgroundMessage.setMessString("Ignoring a potential pregnancy or STI is a very dangerous move. Although it can be scary to go to the clinic or reach out for help, you really need it. An untreated STI could lead to infertility and other serious complications later. And the earlier you find out if you are pregnant or not, the more time you will have to figure out how you will handle the situation.");
                            backgroundMessage.setVisible(true);
                            twoOptionMess.setVisible(false);
                            faded_bgSprite.setVisible(true);
                            maleChar.setVisible(false);

                        }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){
//
                            backgroundMessage.setMessString("You decided to visit the doctor & get yourself checked");
                            backgroundMessage.setVisible(true);
                            faded_bgSprite.setVisible(true);
                            subSceneNumber=5;
                        }
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context, "endTime", MyUtility.getTime());
                            //*************************************************

                            //********* Saving Scores in Database***************
                            Utility.saveDataInDB(context);
                            //**************************************************


                            //********* Saving Scores in Database***************
                            MyPreference.removeKeys(context, 0);
                            //**************************************************

                            delayCounter = 0;
                            isDelay = false;
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);

                            GlobalVariables.initialize();

                            ((Activity) context).setResult(123, new Intent());
                            ((Activity) context).finish();
                            this.recycle();

                        }

                        break;

                    case 5://You decided to visit the doctor & get yourself checked
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

                            isDelay=true;
                            delayCounter=0;
                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_clinic_inside);
                            bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
                            faded_bgSprite.setVisible(false);
                            backgroundMessage.setVisible(false);
                            subSceneNumber=6;

                        }
                    case 6:

                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF) && faded_bgSprite.isVisible()){

                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context,"endTime", MyUtility.getTime());
                            //*************************************************

                            //********* Saving Scores in Database***************
                            Utility.saveDataInDB(context);
                            //**************************************************

                            //*************** cleared Prefrences *************
                            MyPreference.removeKeys(context,0);
                            // ***************************************************************

                            delayCounter = 0;
                            isDelay = false;
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);

                            GlobalVariables.initialize();
                            ((Activity) context).setResult(123, new Intent());
                            ((Activity) context).finish();
                            this.recycle();

                        }

                        break;
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void recycle() {
        drawSomething(myCanvas);

        bgSprite.recycle();
        faded_bgSprite.recycle();
        maleChar.recycle();
        timer.recycle();

        message.recycle();
        if(backgroundMessage!=null) backgroundMessage.recycle();
        twoOptionMess.recycle();
        if(mythMessage!=null)mythMessage.recycle();
        popUpInstruction.recycle();
        dp_ignoreandPray_gotoclinic.recycle();
        helper.recycle();

    }

    @Override
    public void run() {

    }
}
