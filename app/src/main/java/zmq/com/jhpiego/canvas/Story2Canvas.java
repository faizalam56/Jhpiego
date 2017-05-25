package zmq.com.jhpiego.canvas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.Random;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.BackgroundMessage;
import zmq.com.jhpiego.messages.InnerThought;
import zmq.com.jhpiego.messages.Message;
import zmq.com.jhpiego.messages.MythMessage;
import zmq.com.jhpiego.messages.PopUpInstruction;
import zmq.com.jhpiego.messages.PopUpMessage;
import zmq.com.jhpiego.messages.PopUpMessageBottom;
import zmq.com.jhpiego.other.DecisionPoint;
import zmq.com.jhpiego.other.GameChangerEffect;
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


public class Story2Canvas extends BaseSurface implements Runnable, Recycler {

    private int xINCREMENT = 5;
    private int sceneNumber=-1,touchScene;
    private int subSceneNumber;
    private ShahSprite bgSprite,door;
    private ShahSprite faded_bgSprite;
    private ShahSprite maleChar,femaleChar,dancingChar,watchingCouple,leadCouple,kissingChar,roomateChar;
    private ShahSprite invite_in_female_char,invite_in_male_char;

    DecisionPoint dp_home_club_inviteIn,dp_ignore_jelous_drunk,dp_downeasy_kissagain,dp_klub_hotheavy;
    Message message;
    PopUpMessage twoOptionMess;
    PopUpInstruction popUpInstruction;
    MythMessage mythMessage;
    private BackgroundMessage backgroundMessage;
    //private GameChangerMessage gameChangerMessage;

    //private InnerThought innerThought;
    private InnerThought innerThought_top_left, innerThought_top_right, innerThought_bottom_left;
    private Helper helper,helper1;

    //ShahSprite door1;
    Intent intent;
    private String randomMythMesg="";
    private Constant myConstant;
    private Canvas myCanvas;
    private boolean onTouchStatus=false;
    private int[]dance_frame_sequence={0,1,2,1};

    private int countDown=GlobalVariables.timerValue;
    private boolean countDownStatus=false;
    private DecisionPoint whichDecisionPoint;
    private Timer timer;
    private GameChangerEffect gameChangerEffect;

    private Paint paint1;
    private ShahSprite klub_Image_Outside_machine;
    private ShahSprite dancing_Couple01,dancing_Couple01_copy1;
    private ShahSprite dancing_Couple02;
    private ShahSprite disco_light01,disco_light02;
    private ShahSprite roomate_surprised_sprite,roomate_surprised_bg;

//    int []opacity={0,50,25,100,50,141};;//{0,30,15,60,30,85};
//    int count=0;
//    boolean incremnt_decremnt=true;


    public Story2Canvas(Context context, Intent intent) {
        super(context);

        this.intent = intent;
        paint.setColor(Color.WHITE);
        myConstant=new Constant(context);
        paint1=new Paint();

        //***************** Sound************
        MySound.stopSound(context,R.raw.a);
        MySound.playSound(context, R.raw.a);
        GlobalVariables.Audio_File_Name=R.raw.a;
        //***************** Sound************

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" Story2Canvas surfaceDestroyed Called...");
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

    @Override
    protected void drawSomething(Canvas g) {
        if(g!=null)g.drawColor(Color.TRANSPARENT);
        switch (sceneNumber) {
            case -1:
                myConstant.drawImageWithRotation(g);

                loadImages();
                break;
            case 0:

                scene0(g);
               // drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber , 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 1:
                scene1(g);
              // drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber , 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 2:
                scene2(g);
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber , 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            default:
                break;



        }
        if(countDownStatus){
            GlobalVariables.sleepTime=100;
            helperCountDown();
            timer.update(g,paint);
            drawText(myCanvas,""+(countDown),width/2,height/2+(int)(GlobalVariables.yScale_factor*20),(int)(GlobalVariables.xScale_factor*50));
        }

        if(gameChangerEffect!=null){
            if(gameChangerEffect.isVisible()){
                gameChangerEffect.update(g,paint1);
            }
        }

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

            GlobalVariables.sleepTime=100;
            countDownStatus=false;
            if(helper!=null)helper.setVisible(false);
           // popUpInstruction.setVisible(false);

            if(whichDecisionPoint!=null){
                if(whichDecisionPoint .equals(dp_home_club_inviteIn)){
                    subSceneNumber=2;
                    touchScene=2;
                }
                if(whichDecisionPoint .equals(dp_ignore_jelous_drunk)){
                    subSceneNumber=6;
                    helper1.setVisible(false);
                }
                if(whichDecisionPoint .equals(dp_klub_hotheavy)){
                    subSceneNumber=6;
                    onTouchStatus=true;
                    helper.setVisible(false);
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
//                if(isMoveMaleChar){
//                    if (maleChar.getX() < toMoveX) {
//                        maleChar.setPosition(maleChar.getX() + xINCREMENT, maleChar.getY());
//                    }else{
//                        isMoveMaleChar=false;
//                    }
//
//                }
                if (isDelay) {
                    delayCount();
                }
                if (delayCounter >=0 && !dp_home_club_inviteIn.isVisible() && !message.isVisible()) {
                    maleChar.setPosition(-10,maleChar.getY());
//                    maleChar.setPosition(dp_home_club_inviteIn.getX() - maleChar.getWidth(), maleChar.getY());
                    faded_bgSprite.setVisible(true);
                    dp_home_club_inviteIn.setPosition(maleChar.getX()+maleChar.getWidth(),dp_home_club_inviteIn.getY());
                    dp_home_club_inviteIn.messPosX=dp_home_club_inviteIn.bgImage.getX()+dp_home_club_inviteIn.bgImage.getWidth()/2-(int)paint.measureText(dp_home_club_inviteIn.messString)/2;
                    dp_home_club_inviteIn.setVisible(true);

                    delayCounter = 0;
                }
                if ((delayCounter == 1) && dp_home_club_inviteIn.isVisible() && !message.isVisible() && !twoOptionMess.isVisible()) {
                    message.setVisible(true);
                    delayCounter = 0;
                }
                if (delayCounter == 1 && !twoOptionMess.isVisible() && dp_home_club_inviteIn.isVisible() && message.isVisible()) {
                    message.setVisible(false);
                    twoOptionMess.setVisible(true);
                    delayCounter = 0;
                    helper = new Helper(new String[]{
                            "Why don’t you guys come to the Klub… everyone is here!",
                            "If you invite them in you can talk about what happened and explain your mistake!",
                            "If you’re not sure what you want, being alone together might be risky.",
                            "Sometimes the best loves begin with friendship!"
                    });
                    helper.setVisible(false);

                    helper1 = new Helper(new String[]{
                            "The only way to deal with this is to show your love what they’re missing—make them jealous.",
                            "You’re really upset…maybe a drink will calm you down.",
                            "It’s easy for things to get out of control when you are drinking. You can easily end up doing something you’ll regret.",
                            "Your true love lied to you, and that is uncool. But you don’t have to do anything tonight… just try to have fun and talk to them tomorrow."
                    });
                    helper1.setVisible(false);
                    //helper1.setUsed(false);
                    isDelay = false;
                }
//                helper.setMessPosY(helper.messPosY+10);
                faded_bgSprite.paint(g,null);
                maleChar.paint(g, null);

                dp_home_club_inviteIn.update(g, paint);
//                if(twoOptionMess.isVisible()){
//                    faded_bgSprite.paint(g,null);
//                }
//                message.update(g, paint);
                twoOptionMess.update(g, paint);

                break;
            case 1://helper(with yesbtn option)
                faded_bgSprite.paint(g,null);
                maleChar.paint(g, null);
                dp_home_club_inviteIn.update(g, paint);
                helper.update(g, paint);
                //popUpInstruction.update(g, paint);
                break;
            case 2://nobtn helper

                faded_bgSprite.paint(g,null);
                maleChar.paint(g, null);
                dp_home_club_inviteIn.update(g, paint);
                break;

            case 3:
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g, null);
                    dp_home_club_inviteIn.update(g, paint);
                    twoOptionMess.update(g, paint);
                }
                break;
            case 4:
                maleChar.paint(g, null);
                faded_bgSprite.paint(g,null);
                mythMessage.update(g, paint);
                break;
            case 5:
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g,null);
                    twoOptionMess.update(g, paint);
                }
                break;
            case 6:
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.update(g, paint);
                }
                break;
            case 7:
                break;
        }
    }
    private void showClubScene(){

        Bitmap temp_dancing_Couple01=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_couple01);
        dancing_Couple01 = new ShahSprite(temp_dancing_Couple01,temp_dancing_Couple01.getWidth()/8,temp_dancing_Couple01.getHeight());
        dancing_Couple01.setPosition(0, 20);
        dancing_Couple01.setVisible(false);

        Bitmap temp_dancing_Couple01_copy=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_couple01);
        dancing_Couple01_copy1 = new ShahSprite(temp_dancing_Couple01_copy,temp_dancing_Couple01_copy.getWidth()/8,temp_dancing_Couple01_copy.getHeight());
        dancing_Couple01_copy1.setPosition(dancing_Couple01.getX()+dancing_Couple01.getWidth(),50);
        dancing_Couple01_copy1.setVisible(false);

//////////////////////////////////////////////////////////////////////////////////////////////////
        Bitmap temp_dancing_Couple02=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_couple02);
        dancing_Couple02 = new ShahSprite(temp_dancing_Couple02,temp_dancing_Couple02.getWidth()/8,temp_dancing_Couple02.getHeight());
        dancing_Couple02.setPosition(width-dancing_Couple02.getWidth(),30);
        dancing_Couple02.setVisible(false);

        /////////////////////////////////////////////////////////////////////////////////////
        Bitmap temp_discolight01=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.disco_light01);
        disco_light01 = new ShahSprite(temp_discolight01,temp_discolight01.getWidth()/6,temp_discolight01.getHeight());
        disco_light01.setPosition(disco_light01.getWidth()/2,0);
        disco_light01.setVisible(false);

        Bitmap temp_discolight02=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.disco_light02);
        disco_light02 = new ShahSprite(temp_discolight02,temp_discolight02.getWidth()/6,temp_discolight02.getHeight());
        disco_light02.setPosition(width-disco_light02.getWidth()-disco_light02.getWidth()/3,0);
        disco_light02.setVisible(false);

        bgSprite.recycle();
        Bitmap temp1 = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.klub_inside_bg);
        bgSprite.setImage(temp1, temp1.getWidth(), temp1.getHeight());
        bgSprite.setVisible(true);

        popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                        "You see your true love dancing with her “Ex”!");
                popUpInstruction.setVisible(false);
        isDelay=true;
        subSceneNumber=2;
    }
    public void scene1(Canvas g) {
       //bgSprite.paint(g,null);
        myCanvas=g;
        switch (subSceneNumber) {
            case 0:
                //***************** Sound************
                MySound.isLooping=true;
                MySound.stopAndPlay(context,new int[]{R.raw.a,R.raw.disco_outside});
                GlobalVariables.Audio_File_Name=R.raw.disco_outside;
                //*****************************

                   showClubScene();

                break;

            case 1:
                  if(isDelay){
                      delayCount();
                  }
                  if(delayCounter==2 && !popUpInstruction.isVisible()){
                    popUpInstruction.setVisible(true);
                      delayCounter=0;
                  }
                  if(delayCounter==2 && popUpInstruction.isVisible()){
                      popUpInstruction.setVisible(false);


                      Bitmap temp_dancing_Couple01=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_couple01);
                      dancing_Couple01 = new ShahSprite(temp_dancing_Couple01,temp_dancing_Couple01.getWidth()/8,temp_dancing_Couple01.getHeight());
                      dancing_Couple01.setPosition(0, 20);
                      dancing_Couple01.setVisible(false);

                      Bitmap temp_dancing_Couple01_copy=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_couple01);
                      dancing_Couple01_copy1 = new ShahSprite(temp_dancing_Couple01_copy,temp_dancing_Couple01_copy.getWidth()/8,temp_dancing_Couple01_copy.getHeight());
                      dancing_Couple01_copy1.setPosition(dancing_Couple01.getX()+dancing_Couple01.getWidth(),50);
                      dancing_Couple01_copy1.setVisible(false);

//////////////////////////////////////////////////////////////////////////////////////////////////
                      Bitmap temp_dancing_Couple02=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_couple02);
                      dancing_Couple02 = new ShahSprite(temp_dancing_Couple02,temp_dancing_Couple02.getWidth()/8,temp_dancing_Couple02.getHeight());
                      dancing_Couple02.setPosition(width-dancing_Couple02.getWidth(),30);
                      dancing_Couple02.setVisible(false);

  /////////////////////////////////////////////////////////////////////////////////////
                      Bitmap temp_discolight01=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.disco_light01);
                      disco_light01 = new ShahSprite(temp_discolight01,temp_discolight01.getWidth()/6,temp_discolight01.getHeight());
                      disco_light01.setPosition(disco_light01.getWidth()/2,0);
                      disco_light01.setVisible(false);

                      Bitmap temp_discolight02=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.disco_light02);
                      disco_light02 = new ShahSprite(temp_discolight02,temp_discolight02.getWidth()/6,temp_discolight02.getHeight());
                      disco_light02.setPosition(width-disco_light02.getWidth()-disco_light02.getWidth()/3,0);
                      disco_light02.setVisible(false);

                      klub_Image_Outside_machine = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_image_outside_machine));
                      klub_Image_Outside_machine.setVisible(false);
                      delayCounter=0;
                      subSceneNumber=2;
                  }

                  // backgroundMessage.setVisible(true);
                  //faded_bgSprite.setVisible(true);
                   sceneNumber=1;
                   isDelay=true;

                break;
            case 2:
                GlobalVariables.sleepTime=200;
                if(isDelay){
                    delayCount();
                }

                if(delayCounter>=0 && xINCREMENT==5  && !leadCouple.isVisible() && !dancingChar.isVisible()){

                    //***************** Sound************
                    MySound.stopAndPlay(context,new int[]{R.raw.disco_outside,R.raw.disco_inside});
                    GlobalVariables.Audio_File_Name=R.raw.disco_inside;
                    //*****************************

                    bgSprite.setVisible(true);
//                    Bitmap temp1 = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.klub_inside_bg);
//                    bgSprite.setImage(temp1, temp1.getWidth(), temp1.getHeight());
                    xINCREMENT=3;
                    leadCouple.setVisible(true);
                    dancing_Couple01.setVisible(true);
                    dancing_Couple01_copy1.setVisible(true);
                    dancing_Couple02.setVisible(true);
                    disco_light01.setVisible(true);
                    disco_light02.setVisible(true);

                    bgSprite.setPosition(-(bgSprite.getWidth()-width),bgSprite.getY());

                    delayCounter=0;

                }

                if(delayCounter==2 && leadCouple.isVisible() ){
                    bgSprite.setPosition(0,bgSprite.getY());
                    leadCouple.setVisible(false);
                    dancingChar.setVisible(true);

                    disco_light01.recycle();
                    Bitmap temp1 = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.disco_light03);
                    int w=temp1.getWidth();
                    int h=temp1.getHeight();
                    disco_light01.setImage(temp1, temp1.getWidth()/6, temp1.getHeight());

                    disco_light02.recycle();
                    Bitmap temp2 = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.disco_light04);
                    disco_light02.setImage(temp2, temp2.getWidth()/6, temp2.getHeight());

                    delayCounter=0;
                }

                if(delayCounter==2 && !leadCouple.isVisible() &&  xINCREMENT==3) {

                   subSceneNumber=3;
                   delayCounter=0;
                    //delayCounter=0;
                }

                dancingChar.nextFrame();
                break;
            case 3:

                GlobalVariables.sleepTime=200;
                dancingChar.setVisible(true);
                watchingCouple.setVisible(false);
                leadCouple.setVisible(false);

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==3 && !popUpInstruction.isVisible()) {
                    subSceneNumber=3;
                    delayCounter=0;

                    popUpInstruction.setMessString("You see your true love dancing with her “Ex”!");
                    popUpInstruction.setVisible(true);
                    gameChangerEffect.setVisible(true);

                   //isDelay=false;
                }
                if(delayCounter==4 && popUpInstruction.isVisible()){
                    popUpInstruction.setVisible(false);

                    dancingChar.setVisible(false);

                    gameChangerEffect.setVisible(false);
                    gameChangerEffect.setCount(0);

                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());


                    int gap=(int)(GlobalVariables.xScale_factor*60);
                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);
//                    innerThought_top_left.setMessLinePixelWidth(innerThought_top_left.mess_bgImage.getWidth()-gap);
//                    innerThought_top_left.setMessTextPosition(innerThought_top_left.mess_bgImage.getX()+5,innerThought_top_left.mess_bgImage.getY()+10);

                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);
//                    innerThought_top_right.setMessLinePixelWidth(innerThought_top_right.mess_bgImage.getWidth()-gap);
//                    innerThought_top_right.setMessTextPosition(innerThought_top_right.mess_bgImage.getX()+45,innerThought_top_right.mess_bgImage.getY()+10);

                    innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);
//                    innerThought_bottom_left.setMessLinePixelWidth(innerThought_bottom_left.mess_bgImage.getWidth()-gap);
//                    innerThought_bottom_left.setMessTextPosition(innerThought_bottom_left.mess_bgImage.getX()+5,innerThought_bottom_left.mess_bgImage.getY()+50);

                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                    innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);

                    isDelay=true;
                    delayCounter=0;
                    subSceneNumber=4;


                }

                dancingChar.nextFrame();

                break;

            case 4:
                GlobalVariables.sleepTime=100;
                if(isDelay){
                    delayCount();
                }

                maleChar.setVisible(true);

                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && dp_ignore_jelous_drunk==null)) {
                    paint.setColor(Color.BLACK);
                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() )) {
                    innerThought_top_right.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() )) {
                    innerThought_bottom_left.setVisible(true);
                    delayCounter = 0;

                    //isDelay=false;
                }
                if ((delayCounter == 4 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() )) {
                    delayCounter = 0;
                     //isDelay=false;

                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    maleChar.setPosition(-10,maleChar.getY());

                    dp_ignore_jelous_drunk = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.ignore_butt_sprite, R.drawable.jealous_butt_sprite, R.drawable.forget_butt_sprite});
                    dp_ignore_jelous_drunk.setPosition(maleChar.getX()+maleChar.getWidth(),dp_home_club_inviteIn.getY());
                    dp_ignore_jelous_drunk.messPosX=dp_ignore_jelous_drunk.bgImage.getX()+dp_home_club_inviteIn.bgImage.getWidth()/2-(int)paint.measureText(dp_home_club_inviteIn.messString)/2;
                    dp_ignore_jelous_drunk.setVisible(true);

                    //twoOptionMess.recycle();
                    twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
                    twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
                    twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
                    twoOptionMess.setVisible(false);
                    paint.setColor(Color.WHITE);
                }
                if(delayCounter==2 && dp_ignore_jelous_drunk!=null){
                    twoOptionMess.setVisible(true);
                    delayCounter = 0;
                    isDelay=false;

                }



                break;


            case 5://helper yes btn
                twoOptionMess.setVisible(false);
                //helper.recycle();



                break;
            case 6://helper no btn
                if(!bgSprite.sourceImage.isRecycled()) {
                    helper1.setVisible(false);
                    twoOptionMess.setVisible(false);
                   // popUpInstruction.setVisible(false);
                }
                break;
        }


        bgSprite.paint(g,null);

        //faded_bgSprite.paint(g,null);
        if(disco_light01!=null){
            disco_light01.paint(g,null);
            disco_light01.nextFrame();
            disco_light02.paint(g,null);
            disco_light02.nextFrame();

           // klub_Image_Outside_machine.paint(g,null);
        }

        if(dancing_Couple01!=null){

            dancing_Couple01.paint(g,null);
            dancing_Couple01.nextFrame();

            dancing_Couple01_copy1.paint(g,null);
            dancing_Couple01_copy1.nextFrame();

            dancing_Couple02.paint(g,null);
            dancing_Couple02.nextFrame();
        }

        dancingChar.paint(g,null);

        popUpInstruction.update(g,paint);
       // gameChangerMessage.update(g,paint);
        maleChar.paint(g,null);
        innerThought_top_left.update(g, paint);
        innerThought_top_right.update(g, paint);
        innerThought_bottom_left.update(g, paint);

        if(dp_ignore_jelous_drunk!=null){
            dp_ignore_jelous_drunk.update(g,paint);
            twoOptionMess.update(g,paint);
            if(helper1!=null)helper1.update(g,paint);
        }

        leadCouple.paint(g,null);
        watchingCouple.paint(g,null);
    }


  private void repeatedCode(){
      helper = new Helper(new String[]{
              "Well, all I know is that your true love is here at Klub Image with us—you should get here.",
              "I can’t believe this is happening! You have condoms right?",
              "Whatever you do, make sure you are being safe about it.",
              "Sometimes friends end up being the best partners. But what is the rush?"

      });
      helper.setVisible(false);

      dp_klub_hotheavy = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.goto_club_btn_sprite, R.drawable.get_hot});
      dp_klub_hotheavy.setPosition(maleChar.getX()+maleChar.getWidth(),dp_klub_hotheavy.getY());
      dp_klub_hotheavy.messPosX=dp_klub_hotheavy.bgImage.getX()+dp_klub_hotheavy.bgImage.getWidth()/2-(int)paint.measureText(dp_klub_hotheavy.messString)/2;
      dp_klub_hotheavy.setVisible(false);

      kissingChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_kissing));
      kissingChar.setPosition(width / 2 - kissingChar.getWidth() / 2, height / 2 - kissingChar.getHeight() / 2);
      kissingChar.setVisible(false);

      roomateChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roommate));
      roomateChar.setPosition(width / 2 - roomateChar.getWidth() / 2, height  - roomateChar.getHeight() );
      roomateChar.setVisible(false);

      door = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.door1));
      door.setPosition(width / 2 - door.getWidth() / 2, height / 2 - door.getHeight() / 2);
      door.setVisible(false);

      Bitmap temp_char=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roommate_surprised_sprite);
      roomate_surprised_sprite = new ShahSprite(temp_char,temp_char.getWidth()/2,temp_char.getHeight());
      roomate_surprised_sprite.setPosition(width / 2-roomate_surprised_sprite.getWidth()/2, 0);
      roomate_surprised_sprite.setVisible(false);

      roomate_surprised_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roommate_surprised_bg));
      roomate_surprised_bg.setVisible(false);

//      gameChangerMessage.setMessString("Roommate walks into the room, sees you kissing,  then quickly gets out");
//      gameChangerMessage.setVisible(false);

      faded_bgSprite.setVisible(false);
      twoOptionMess.setVisible(false);
     // popUpInstruction.setVisible(false);
      maleChar.setVisible(false);

      isDelay=true;
      delayCounter=0;
      subSceneNumber=6;
  }

    public void scene2(Canvas g) {
        myCanvas=g;
        bgSprite.paint(g, null);
        switch (subSceneNumber) {
            case 0:

                if(isDelay){
                     delayCount();
                 }
                if(delayCounter==6 && !maleChar.isVisible()){
                    faded_bgSprite.setVisible(true);
                    dp_downeasy_kissagain.setVisible(true);

                    maleChar.setVisible(true);
                    invite_in_female_char.setVisible(false);
                    invite_in_male_char.setVisible(false);
                    popUpInstruction.setVisible(false);

                    //***************** Calculating Decision Time ******************
                    GlobalVariables.end_Time=System.currentTimeMillis();
                    // ***********************************

                    //***************** Send time taken on decision ******************
                    UpdateScore.setDecisionTime(context,"D1",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                    // ***********************************

                    delayCounter=0;
                }
                if(delayCounter==1 && !backgroundMessage.isVisible() && maleChar.isVisible()){
                    twoOptionMess.setVisible(true);
                    delayCounter=0;
                    isDelay=false;

                    helper = new Helper(new String[]{
                            "You should kiss again, just to see how you really feel about it.",
                            "Wow, this is so confusing! Maybe you should try to get out of there!",
                            "You owe them an apology… and you have a lot of thinking to do about what you really want.",
                            "Are you sure you want to create a reputation for yourself as a two-timer?"
                    });
                    helper.setVisible(false);

                    String temMyth[] = {"It’s impossible for girls and guys to be “Just friends.” Somebody always has romantic feelings and wants more.",
                                        "Anytime you invite someone into your room, you’re sending the message: “I want sex”"};

                    int rand=new Random().nextInt(2);
                    String temMyth_DownEasy[] = {"It is very possible for girls and guys to be “just friends” without anyone having romantic feelings. But if one person has feelings and the other doesn’t, that can be awkward and painful for everybody.",
                            "Never assume anyone wants to have sex unless they say it outright."};
                    randomMythMesg=temMyth_DownEasy[rand];

                    mythMessage.recycle();
                    mythMessage = new MythMessage(R.drawable.background_mess, temMyth[rand], R.drawable.agree, R.drawable.disagree,R.drawable.dont_know);
                    mythMessage.setMessPosY(mythMessage.messPosY + 50);
                    mythMessage.setVisible(false);
                }
                invite_in_female_char.paint(g,null);
                invite_in_male_char.paint(g,null);
                popUpInstruction.update(g,paint);

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
               // backgroundMessage.update(g,paint);
                dp_downeasy_kissagain.update(g,paint);
                twoOptionMess.update(g,paint);

                break;
            case 1:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_downeasy_kissagain.update(g,paint);
                helper.update(g,paint);
               // popUpInstruction.update(g,paint);
                twoOptionMess.setVisible(false);
                break;
            case 2:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                mythMessage.update(g,paint);
                backgroundMessage.update(g,paint);
                twoOptionMess.update(g,paint);
                break;
            case 3:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                backgroundMessage.update(g,paint);
                break;

            case 4:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                twoOptionMess.update(g,paint);
                break;
            case 5:
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    backgroundMessage.update(g, paint);
                }
                break;

            case 6://kiss again
                 if(isDelay){
                     delayCount();
                 }
                if(delayCounter>=0 && !kissingChar.isVisible() && !roomateChar.isVisible() && !roomate_surprised_sprite.isVisible() && !maleChar.isVisible()){
                    kissingChar.setVisible(true);

                    delayCounter=0;

                }
                if(delayCounter==2 && kissingChar.isVisible() && !roomateChar.isVisible() && !roomate_surprised_sprite.isVisible()&& !maleChar.isVisible()){
                    kissingChar.setVisible(false);
                    roomateChar.setVisible(true);

                   // bgSprite.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.knock_bg);
                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
                    bgSprite.paint(g,null);
                    door.setVisible(true);
                    delayCounter=0;

                }
                if(delayCounter==2 && roomateChar.isVisible()){
                    faded_bgSprite.setVisible(true);
                    roomateChar.setVisible(false);
                    roomate_surprised_sprite.setVisible(true);
                    roomate_surprised_bg.setVisible(true);

                   // gameChangerMessage.setVisible(true);
                    //isDelay=false;
                    delayCounter=0;

                }

                if(delayCounter==1 && roomate_surprised_bg.isVisible() && roomate_surprised_sprite.getFrame()==0){
                    innerThought_top_right.setPosition(width-innerThought_top_right.mess_bgImage.getWidth(),
                            roomate_surprised_sprite.getY()+roomate_surprised_sprite.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                    innerThought_top_right.setMessString("Hey Woah!");
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
//                    innerThought_top_right.setMessLinePixelWidth(innerThought_top_right.mess_bgImage.getWidth()-55);
//                    innerThought_top_right.setMessTextPosition(innerThought_top_right.mess_bgImage.getX()+45,innerThought_top_right.mess_bgImage.getY()+10);
                    innerThought_top_right.setVisible(true);

                    roomate_surprised_sprite.setFrame(1);
                    popUpInstruction.setVisible(true);
                    gameChangerEffect.setVisible(true);//Game Changer
                    delayCounter=0;
                    //isDelay=false;

                }
                if(delayCounter==2 && innerThought_top_right.isVisible()){
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.scene1bg);
                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
//

                    twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
                    twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
                    twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
                    twoOptionMess.setVisible(false);


                    faded_bgSprite.setVisible(true);
                    dp_klub_hotheavy.setVisible(true);
                    maleChar.setVisible(true);

                    innerThought_top_right.setVisible(false);
                    roomate_surprised_bg.setVisible(false);
                    roomate_surprised_sprite.setVisible(false);
                    popUpInstruction.setVisible(false);

                    gameChangerEffect.setVisible(false);//Game Changer
                    gameChangerEffect.setCount(0);

                    door.setVisible(false);
                    delayCounter=0;
                }
                if(delayCounter==1 && dp_klub_hotheavy.isVisible()){
                    twoOptionMess.setVisible(true);
                    delayCounter=0;
                    isDelay=false;
                }
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    kissingChar.paint(g, null);

                    dp_klub_hotheavy.update(g, paint);
                    twoOptionMess.update(g, paint);
                    helper.update(g, paint);
                    door.paint(g, null);
                    roomateChar.paint(g, null);

                    roomate_surprised_bg.paint(g,null);
                    roomate_surprised_sprite.paint(g,null);
                    paint.setColor(Color.BLACK);
                    innerThought_top_right.update(g,paint);
                    paint.setColor(Color.WHITE);
                    popUpInstruction.update(g,paint);
                    //gameChangerMessage.update(g, paint);
                }

                break;

        }
    }

    private void getSceneNumber(RectF touchrecF ){
        if (dp_home_club_inviteIn.isVisible() && dp_home_club_inviteIn.getBtnAtIndext(0).getDstRect().intersect(touchrecF)) {
            sceneNumber=0;
            subSceneNumber=2;
            touchScene=1;
        }
        else if (dp_home_club_inviteIn.isVisible() && dp_home_club_inviteIn.getBtnAtIndext(1).getDstRect().intersect(touchrecF)) {

//            Bitmap temp_bag=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_sprite);
//            dancingChar = new ShahSprite(temp_bag,temp_bag.getWidth()/4,temp_bag.getHeight());
//            dancingChar.setPosition(width / 2-dancingChar.getWidth()/2, 0);
//            dancingChar.setVisible(false);
//
//            leadCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_couple));
//            leadCouple.setPosition(width-leadCouple.getWidth()-leadCouple.getWidth()/2,height-leadCouple.getHeight());
//            leadCouple.setVisible(false);
//
//            watchingCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_dancing_couple));
//            watchingCouple.setPosition(0,0);
//            watchingCouple.setVisible(false);
//
//            sceneNumber=1;
//            subSceneNumber=0;
//            maleChar.setVisible(false);
//            helper.setVisible(false);
//            touchScene=1;


            //***************** Calculating Decision Time ******************
            GlobalVariables.end_Time=System.currentTimeMillis();
            // ***********************************

            //*********Update Score.............
            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6, 8, 6}));
            // ***********************************

            //*********Analytic Started.............
            UpdateScore.setModulePath(context,"Go to Klub Image");
            // ***********************************


            //***************** Send time taken on decision ******************
             UpdateScore.setDecisionTime(context,"D1",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
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
        else if (dp_home_club_inviteIn.isVisible() && dp_home_club_inviteIn.getBtnAtIndext(2).getDstRect().intersect(touchrecF)) {

            backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"You decided to invite them in and figure out what’s going on. Now what next??");
            backgroundMessage.setVisible(false);

            popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                    "You decided to invite them in and figure out what’s going on. Now what next??");
            popUpInstruction.setVisible(true);
            twoOptionMess.setVisible(false);

            dp_downeasy_kissagain = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.let_them_down, R.drawable.kiss_again});
            dp_downeasy_kissagain.setPosition(maleChar.getX()+maleChar.getWidth(),dp_downeasy_kissagain.getY());
            dp_downeasy_kissagain.messPosX=dp_downeasy_kissagain.bgImage.getX()+dp_downeasy_kissagain.bgImage.getWidth()/2-(int)paint.measureText(dp_downeasy_kissagain.messString)/2;
            dp_downeasy_kissagain.setVisible(false);

            invite_in_female_char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_female_flip));
            invite_in_female_char.setPosition(width-3*invite_in_female_char.getWidth()/2,height-invite_in_female_char.getHeight());

            invite_in_male_char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_male_flip));
            invite_in_male_char.setPosition(invite_in_male_char.getWidth()/2,height-invite_in_male_char.getHeight());

            maleChar.setVisible(false);

            faded_bgSprite.setVisible(false);
            isDelay=true;
            sceneNumber=2;
            subSceneNumber=0;
            touchScene=1;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        MySound.playSoundOnDemand(context,R.raw.tap_sound);

        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
        if(touchScene==2){
            getSceneNumber(touchrecF);

        }

        switch (sceneNumber) {
            case 0:
                switch (subSceneNumber) {
                    case 0:
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************
                            helper.setVisible(true);
                            subSceneNumber = 1;

                           //***********Helper Count Down**********
                            countDownStatus=true;
                            whichDecisionPoint=dp_home_club_inviteIn;
                           //*************************************

                        }
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {
                          //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                           // ***********************************

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));

                            // ***********************************


                            dp_home_club_inviteIn.setActive();
                            touchScene=2;
                            subSceneNumber = 2;
                        }
                        break;

                    case 1:
                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                              timer.setVisible(false);
//                              //popUpInstruction.setVisible(false);
//                              countDownStatus=false;
//                              countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

                        if (helper.isVisible() && helper.isUsed() && helper.actionButton.getDstRect().intersect(touchrecF) && !countDownStatus) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            dp_home_club_inviteIn.setActive();
                            helper.setVisible(false);
                            subSceneNumber = 2;
                            touchScene=2;

                           // return true;
                        }
                        if (helper.isVisible() && !helper.isUsed() && countDownStatus) {
                            //check helper touch and set action
                            for (int i = 0; i < helper.helper_sprite_array.length; i++) {
                                ShahSprite shahSprite = helper.helper_sprite_array[i];
                                if (shahSprite.getDstRect().intersect(touchrecF)) {
                                    //set helper text and
                                  //  popUpInstruction.setVisible(false);
                                    helper.setUsed(true);
                                    helper.setMessStringForIndex(i);

                                    timer.setVisible(false);
//                              //popUpInstruction.setVisible(false);
                                    countDownStatus=false;
                                    countDown=GlobalVariables.timerValue;
                                    break;
                                }
                            }
                        }
                        break;

                    case 2://Send Home
                        if (dp_home_club_inviteIn.isVisible() && dp_home_club_inviteIn.getBtnAtIndext(0).getDstRect().intersect(touchrecF)) {

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D1",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                           UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6, 3, 0}));
                            // ***********************************


                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Send Home");
                            // ***********************************

                            dp_home_club_inviteIn.setVisible(false);
                            twoOptionMess.recycle();
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "Are you sure you want to send her back home?", R.drawable.yes_sure, R.drawable.no_notsure);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            subSceneNumber = 3;
                       }
                        break;
                    case 3:
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{10, 5, 0}));
                            // ***********************************
                            faded_bgSprite.setVisible(true);
                            subSceneNumber = 4;
                        }
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{4, 4, 1}));
                            // ***********************************

                            //subSceneNumber=-100;

                            delayCounter = 0;
                            isDelay = false;
                            // jump to next story
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            temp.putExtra("maleX", maleChar.getX());
                            temp.putExtra("maleY", maleChar.getY());

                            GlobalVariables.initialize();

                            ((Activity) context).setResult(Activity.RESULT_OK, temp);
                            ((Activity) context).finish();
                            this.recycle();

                        }
                        break;
                    case 4:
                        if (mythMessage.isVisible() && mythMessage.actionButton1.getDstRect().intersect(touchrecF)) {//Agree

                           //*********Update Score.............
                                UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{0}));
                           // ***********************************


                            //twoOptionMess.recycle();
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "Hmm…not quite true! "+randomMythMesg, R.drawable.play_againbtn, R.drawable.exitbtn);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            subSceneNumber = 5;
                        }
                        if (mythMessage.isVisible() && mythMessage.actionButton2.getDstRect().intersect(touchrecF)) {//disagree

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{10}));
                            // ***********************************

                            //twoOptionMess.recycle();
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "Congratulations! Correct! "+randomMythMesg, R.drawable.play_againbtn, R.drawable.exitbtn);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            subSceneNumber = 5;
                        }
                        if (mythMessage.isVisible() && mythMessage.actionButton3.getDstRect().intersect(touchrecF)) {//don't know

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{4}));
                            // ***********************************

                            //twoOptionMess.recycle();
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, randomMythMesg, R.drawable.play_againbtn, R.drawable.exitbtn);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            subSceneNumber = 5;
                        }
                        break;
                    case 5:
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {//replay

                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context,"endTime", MyUtility.getTime());
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
                            MyPreference.saveStringKeyValue(context, "sessionsId", MyPreference.getStringValue(context, "sessionsId"));
                            // ***************************************************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.initialize();

                            ((Activity) context).setResult(GlobalVariables.REPLAY, temp);
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {//exitbtn
                            backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"While it might be a good idea to give yourself time and space to think about the situation, kicking your best friend out after they have made themselves vulnerable to you is mean. It also indicates that you aren’t taking any responsibility for what has happened—you actually sent the text that got this situation started!");
                            backgroundMessage.setVisible(true);

                            subSceneNumber=6;

                        }
                        break;
                    case 6:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context,"endTime", MyUtility.getTime());
                            //*************************************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0, 0, 0}));
                            // ***********************************

                            //********* Saving Scores in Database***************
                             Utility.saveDataInDB(context);
                             //**************************************************

                            //*************** cleared Prefrences *************
                            //new File("/data/data/zmq.com.jhpiego/shared_prefs/temp_data.xml").delete();
                            // ***************************************************************

                            MyPreference.removeKeys(context,0);

                            delayCounter = 0;
                            isDelay = false;
                            // jump to next story
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);

                            GlobalVariables.initialize();

                            ((Activity) context).setResult(123, new Intent());
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }
                        break;
                    case 7:
                        break;
                    default:
                        break;
                }
                break;

            case 1://Go to Klub Image
                switch (subSceneNumber) {
                    case 0:
                        dp_home_club_inviteIn.setVisible(false);

                        break;
                    case 1:


                        //if (backgroundMessage.actionButton.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)) {
//                            //*********Update Score.............
//                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6, 8, 6}));
//                            // ***********************************
//
//                            //*********Analytic Started.............
//                            UpdateScore.setModulePath(context,"Go to Klub Image");
//                            // ***********************************


                            backgroundMessage.setVisible(false);
                            faded_bgSprite.setVisible(false);
                            subSceneNumber=2;
                        //}
                        break;
                    case 2:
//                        if(klub_Image_Outside_machine.isVisible() && klub_Image_Outside_machine.getDstRect().intersect(touchrecF)){
//                            klub_Image_Outside_machine.setVisible(false);
//                            isDelay=true;
//                            delayCounter=0;
//                            xINCREMENT=2;
//                        }
                        break;
                    case 3:

//                        if (gameChangerMessage.actionButton.isVisible() && gameChangerMessage.actionButton.getDstRect().intersect(touchrecF)) {
//                            gameChangerMessage.setVisible(false);
//                            faded_bgSprite.setVisible(false);
//                            dancingChar.setVisible(false);
//                            maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
//
//
//                            int gap=(int)(GlobalVariables.xScale_factor*60);
//                            innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
//                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);
//                            innerThought_top_left.setMessLinePixelWidth(innerThought_top_left.mess_bgImage.getWidth()-gap);
//                            innerThought_top_left.setMessTextPosition(innerThought_top_left.mess_bgImage.getX()+5,innerThought_top_left.mess_bgImage.getY()+10);
//
//                            innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
//                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);
//                            innerThought_top_right.setMessLinePixelWidth(innerThought_top_right.mess_bgImage.getWidth()-gap);
//                            innerThought_top_right.setMessTextPosition(innerThought_top_right.mess_bgImage.getX()+45,innerThought_top_right.mess_bgImage.getY()+10);
//
//                            innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
//                                    maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);
//                            innerThought_bottom_left.setMessLinePixelWidth(innerThought_bottom_left.mess_bgImage.getWidth()-gap);
//                            innerThought_bottom_left.setMessTextPosition(innerThought_bottom_left.mess_bgImage.getX()+5,innerThought_bottom_left.mess_bgImage.getY()+50);
//
//                            isDelay=true;
//                            delayCounter=0;
//                            subSceneNumber=4;
                      //  }
                        break;

                    case 4:
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************

                            helper1.setVisible(true);
                           // popUpInstruction.setVisible(true);
                            subSceneNumber = 5;

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            whichDecisionPoint=dp_ignore_jelous_drunk;
                            countDown=GlobalVariables.timerValue;
                            //*************************************

                            return true;
                        }

                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {
                            //***************** Calc//***************** Calculating Decision Time ******************

                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            dp_ignore_jelous_drunk.setActive();
                            twoOptionMess.setVisible(false);
                            subSceneNumber = 6;
                        }

                        break;
                    case 5:
                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                            //popUpInstruction.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

                        if (helper1.isVisible() && !helper1.isUsed() && countDownStatus) {
                            //check helper touch and set action
                            for (int i = 0; i < helper1.helper_sprite_array.length; i++) {
                                ShahSprite shahSprite = helper1.helper_sprite_array[i];
                                if (shahSprite.getDstRect().intersect(touchrecF)) {
                                    //set helper text and
                                   // popUpInstruction.setVisible(false);

                                    timer.setVisible(false);
                                    //popUpInstruction.setVisible(false);
                                    countDownStatus=false;
                                    countDown=GlobalVariables.timerValue;

                                    helper1.setUsed(true);
                                    helper1.setMessStringForIndex(i);
                                    break;
                                }
                            }
                        }

                        if (helper1.isVisible() && helper1.isUsed() && helper1.actionButton.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************


                            dp_ignore_jelous_drunk.setActive();
                            subSceneNumber = 6;

                        }


                        break;
                    case 6:// Ignore_Jealous_Drunk Decision Point Click Event

                        if (dp_ignore_jelous_drunk.isVisible() && dp_ignore_jelous_drunk.getBtnAtIndext(0).getDstRect().intersect(touchrecF)) {
                            dp_ignore_jelous_drunk.setVisible(false);

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context, "D3", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{8, 8, 8}));
                            // ***********************************


                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Ignore it, Just Dance & Have fun");
                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.SWITCH_SCENE=100;
                            ((Activity) context).setResult(GlobalVariables.SWITCH_SCENE, temp);
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }
                        if (dp_ignore_jelous_drunk.isVisible() && dp_ignore_jelous_drunk.getBtnAtIndext(1).getDstRect().intersect(touchrecF)) {
                            dp_ignore_jelous_drunk.setVisible(false);

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context, "D3", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{3, 1, 2}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Make true love jealous by cuddling up to your BFF");
                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.SWITCH_SCENE=200;
                            ((Activity) context).setResult(GlobalVariables.SWITCH_SCENE, temp);
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }
                        if (dp_ignore_jelous_drunk.isVisible() && dp_ignore_jelous_drunk.getBtnAtIndext(2).getDstRect().intersect(touchrecF)) {
                            dp_ignore_jelous_drunk.setVisible(false);

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context, "D3", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6, 4, 4}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"This is too awful--Get drunk to forget about it");
                            // ***********************************


                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.SWITCH_SCENE=300;
                            ((Activity) context).setResult(GlobalVariables.SWITCH_SCENE, temp);
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }

                        break;

                    default:
                        break;
                }
                break;

            case 2://Invite In
                switch (subSceneNumber) {
                    case 0:


                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Invite In");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{5, 7, 4}));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            whichDecisionPoint=dp_downeasy_kissagain;
                            countDown=GlobalVariables.timerValue;
                            //*************************************

                           twoOptionMess.setVisible(false);
                           helper.setVisible(true);
                           //popUpInstruction.setVisible(true);
                           invite_in_male_char.recycle();
                           invite_in_female_char.recycle();
                           subSceneNumber=1;
                        }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Invite In");
                            // ***********************************

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{5, 7, 4}));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                           dp_downeasy_kissagain.setActive();
                           //popUpInstruction.setVisible(false);
                            invite_in_male_char.recycle();
                            invite_in_female_char.recycle();
                           subSceneNumber=1;
                        }



                        break;

                    case 1:
                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                            //popUpInstruction.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

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

                            dp_downeasy_kissagain.setActive();
                            helper.setVisible(false);
                            return true;
                        }

                        if(dp_downeasy_kissagain.isVisible() && dp_downeasy_kissagain.getBtnAtIndext(0).getDstRect().intersect(touchrecF) && !countDownStatus && !helper.isVisible()){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D9",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Let them down easy");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,6, 5}));
                            // ***********************************

                           mythMessage.setVisible(true);
                           maleChar.setVisible(false);
                           backgroundMessage.setVisible(false);
                           subSceneNumber=2;
                        }
                        if(dp_downeasy_kissagain.isVisible() && dp_downeasy_kissagain.getBtnAtIndext(1).getDstRect().intersect(touchrecF)&& !countDownStatus && !helper.isVisible()){
                            //same code repeated

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D9",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Kiss again");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{5,5, 4}));
                            // ***********************************

                            Bitmap temp_char=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roommate_surprised_sprite);
//                            roomate_surprised_sprite = new ShahSprite(temp_char,temp_char.getWidth()/2,temp_char.getHeight());
//                            roomate_surprised_sprite.setPosition(width / 2-roomate_surprised_sprite.getWidth()/2, 0);
//                            roomate_surprised_sprite.setVisible(false);
//
//                            roomate_surprised_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roommate_surprised_bg));
//                            roomate_surprised_bg.setVisible(false);

                            popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                                    "Roommate walks in, sees you kissing !");
                            popUpInstruction.setVisible(false);

                            repeatedCode();
                            //subSceneNumber=6;
                        }


                        break;

                    case 2:


                        if(mythMessage.isVisible() && mythMessage.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{0}));
                            // ***********************************

                           backgroundMessage.setMessString("Hmm…not quite true! "+randomMythMesg);

                           backgroundMessage.setVisible(true);
                           mythMessage.setVisible(false);
                           subSceneNumber=3;
                        }
                        if(mythMessage.isVisible() && mythMessage.actionButton2.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{10}));
                            // ***********************************

                            backgroundMessage.setMessString("Congratulations! Correct!" +randomMythMesg);

                            backgroundMessage.setVisible(true);
                            mythMessage.setVisible(false);
                            subSceneNumber=3;
                        }
                        if(mythMessage.isVisible() && mythMessage.actionButton3.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"knowledge"}, new int[]{4}));
                            // ***********************************

                          backgroundMessage.setMessString(randomMythMesg);
                          backgroundMessage.setVisible(true);
                          mythMessage.setVisible(false);
                          subSceneNumber=3;

                        }

                        break;

                    case 3:

                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF) && !mythMessage.isVisible()){
//                            twoOptionMess.recycle();
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "Are you sure you want to let them down easy?", R.drawable.yes_stay_friends, R.drawable.no_kiss_again);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            twoOptionMess.setVisible(true);
                            backgroundMessage.setVisible(false);
                            subSceneNumber=4;
                        }

                        break;
                    case 4:

                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{9,9, 10}));
                            // ***********************************

                            backgroundMessage.actionButton.sourceImage = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.exitbtn);
                            backgroundMessage.setMessString("You took a right step & preserved your friendship as well as the relationship with your true love.");
                            backgroundMessage.setVisible(true);
                            twoOptionMess.setVisible(false);
                            subSceneNumber=5;


                        }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6,5, 4}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                            repeatedCode();
                            //subSceneNumber=6;
                        }
                        break;

                    case 5:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF) && !mythMessage.isVisible()){

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
                            // jump to next story
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);

                            GlobalVariables.initialize();
                            ((Activity) context).setResult(123, new Intent());
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }
                        break;

                    case 6://kiss again
//                          if(gameChangerMessage.isVisible() && gameChangerMessage.actionButton.getDstRect().intersect(touchrecF)){
//                              gameChangerMessage.setVisible(false);
//                              //dp_klub_hotheavy.setVisible(false);
//                              kissingChar.setVisible(false);
//                              roomateChar.setVisible(false);
//                              door.setVisible(false);
//
//                              Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.scene1bg);
//                              bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
////
//
//                              twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
//                              twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
//                              twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
//                              twoOptionMess.setVisible(false);
//
//
//                              faded_bgSprite.setVisible(true);
//                              dp_klub_hotheavy.setVisible(true);
//                              maleChar.setVisible(true);
//                              isDelay=true;
//                              delayCounter = 0;
//
//                          }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            whichDecisionPoint=dp_klub_hotheavy;
                            countDown=GlobalVariables.timerValue;
                            //*************************************

                            helper.setVisible(true);
                            twoOptionMess.setVisible(false);
                           // popUpInstruction.setVisible(true);
                        }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************


                            twoOptionMess.setVisible(false);
                            dp_klub_hotheavy.setActive();
                            onTouchStatus=true;
                        }

                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                           // popUpInstruction.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

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

                            dp_klub_hotheavy.setActive();
                            helper.setVisible(false);
                            onTouchStatus=true;

                        }
                        if(dp_klub_hotheavy.isVisible() && dp_klub_hotheavy.getBtnAtIndext(0).getDstRect().intersect(touchrecF) && onTouchStatus){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D10",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,6, 6}));
                            // ***********************************

                            dp_downeasy_kissagain.recycle();
                            mythMessage.recycle();
                            kissingChar.recycle();
                            roomateChar.recycle();
                            door.recycle();

                            roomate_surprised_sprite.recycle();
                            roomate_surprised_bg.recycle();

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Go to Klub Image");
                            // ***********************************

//                            Bitmap temp_bag=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_sprite);
//                            dancingChar = new ShahSprite(temp_bag,temp_bag.getWidth()/4,temp_bag.getHeight());
//                            dancingChar.setPosition(width / 2-dancingChar.getWidth()/2, 0);
//                            dancingChar.setVisible(false);
//
//                            leadCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_couple));
//                            leadCouple.setPosition(width-leadCouple.getWidth()-leadCouple.getWidth()/2,height-leadCouple.getHeight());
//                            leadCouple.setVisible(false);
//
//                            watchingCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_dancing_couple));
//                            watchingCouple.setPosition(0,0);
//                            watchingCouple.setVisible(false);
//
//                            helper1 = new Helper(new String[]{
//                                    "The only way to deal with this is to show your love what they’re missing—make them jealous.",
//                                    "You’re really upset…maybe a drink will calm you down.",
//                                    "It’s easy for things to get out of control when you are drinking. You can easily end up doing something you’ll regret.",
//                                    "Your true love lied to you, and that is uncool. But you don’t have to do anything tonight… just try to have fun and talk to them tomorrow."
//                            });
//                            helper1.setVisible(false);
//
//                            sceneNumber=1;
//                            subSceneNumber=0;
//                            maleChar.setVisible(false);
//                            helper.setVisible(false);
//                            touchScene=1;

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

                        if(dp_klub_hotheavy.isVisible() && dp_klub_hotheavy.getBtnAtIndext(1).getDstRect().intersect(touchrecF) && onTouchStatus){


                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D10",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            // *********Analytic Started.............
                             UpdateScore.setModulePath(context,"Get Hot & heavy");
                             // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{2,3, 1}));
                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.GET_HOT_HEAVY=5;
                            ((Activity) context).setResult(GlobalVariables.GET_HOT_HEAVY, temp);
                            ((Activity) context).finish();
                            this.recycle();
//                            this.recycleImages();
                        }
                        break;
                    case 7:


                        break;
                    case 8:
                        break;

                    default:
                        break;
                }
                break;

            default:
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
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    recycle();
//                }
//            });
//            localThread1.start();
//
//            try {
//                localThread1.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("Start Joinig.......");
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

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1bg));
        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        faded_bgSprite.setVisible(false);

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.boywrongmsg));
        int tempMaleX = intent.getIntExtra("maleX", width / 2 - maleChar.getWidth() / 2);
        int tempMaleY = intent.getIntExtra("maleY", height - maleChar.getHeight());

        maleChar.setPosition(tempMaleX, tempMaleY);

        dp_home_club_inviteIn = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.send_home_btn_sprite, R.drawable.goto_club_btn_sprite, R.drawable.invite_in_btn_sprite});
        dp_home_club_inviteIn.setVisible(false);
//        toMoveX=dp_home_club_inviteIn.getX()-maleChar.getWidth();
//        isMoveMaleChar=true;

        int tempFemaleX = intent.getIntExtra("femaleX", 0);
        int tempFemaleY = intent.getIntExtra("femaleY", 0);
        femaleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.female_char));
//        femaleChar.setPosition(3*width/4,height/8);
        femaleChar.setPosition(tempFemaleX, tempFemaleY);

//        Bitmap temp_bag=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_sprite);
//        dancingChar = new ShahSprite(temp_bag,temp_bag.getWidth()/4,temp_bag.getHeight());
//        dancingChar.setPosition(width / 2-dancingChar.getWidth()/2, 0);
//        dancingChar.setVisible(false);
//
//        leadCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_couple));
//        leadCouple.setPosition(width-leadCouple.getWidth()-leadCouple.getWidth()/2,height-leadCouple.getHeight());
//        leadCouple.setVisible(false);
//
//        watchingCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_dancing_couple));
//        watchingCouple.setPosition(0,0);
//        watchingCouple.setVisible(false);

        timer = new Timer();
        message = new Message(R.drawable.background_mess, "What will you do now?");
//        message.setMessPosX(message.mess_bgImage.getX()+message.mess_bgImage.getWidth()/2);
        message.setMessPosY(message.messPosY + 50);
        message.setVisible(false);

        twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
        twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
        twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
        twoOptionMess.setVisible(false);


        String temMyth[] = {"If you let your BFF stay in your room, it means you want to have sex.",
                "If your BFF stays in the room and you continue kissing, you will lose control of the situation, and you will probably end up having sex.",
                "If you kick your BFF out of the room, it means you are a prude."};

        String randWlcomMesg[]={"Inviting someone into your room is not the same as giving consent for sexual contact.",
                "Just because you kiss doesn’t mean you have to do anything else. You can stop intimate activity at any time for any reason.",
                "Kicking your BFF out doesn’t mean anything about what you are ready for sexually. However, it’s not a nice way to treat a friend who has made themselves vulnerable to you"};

        int rand=new Random().nextInt(3);
        randomMythMesg=randWlcomMesg[rand];

        mythMessage = new MythMessage(R.drawable.background_mess, temMyth[rand], R.drawable.agree, R.drawable.disagree,R.drawable.dont_know);
        mythMessage.setMessPosY(mythMessage.messPosY + 50);

//        gameChangerMessage = new GameChangerMessage(R.drawable.gamechanger_message_bg, "You see your true love dancing with her “Ex”!");
//        gameChangerMessage.setVisible(false);

        innerThought_top_left = new InnerThought(R.drawable.inner_thought_topleft_new, "What!? You were supposed to be studying.");
        innerThought_top_left.setVisible(false);
        innerThought_top_right = new InnerThought(R.drawable.inner_thought_topright_new, "I knew you were not over your ex.");
        innerThought_top_right.setVisible(false);
        innerThought_bottom_left = new InnerThought(R.drawable.inner_thought_bottomleft_new, "Gosh I feel so betrayed and embarrassed.");
        innerThought_bottom_left.setVisible(false);
// door1=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource,R.drawable.door1));
//        door1.setPosition(width/2-door.getWidth()/2,height/2-door.getHeight()/2);

        if(GlobalVariables.INVITE_IN==20){ //Invite In
            dp_home_club_inviteIn.recycle();

            backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"You decided to invite them in and figure out what’s going on. Now what next??");
            backgroundMessage.setVisible(false);

            popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                    "You decided to invite them in and figure out what’s going on. Now what next??");
            popUpInstruction.setVisible(true);

            invite_in_female_char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_female_flip));
            invite_in_female_char.setPosition(width-3*invite_in_female_char.getWidth()/2,height-invite_in_female_char.getHeight());

            invite_in_male_char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_male_flip));
            invite_in_male_char.setPosition(invite_in_male_char.getWidth()/2,height-invite_in_male_char.getHeight());

            maleChar.setVisible(false);

            twoOptionMess.setVisible(false);

            maleChar.setPosition(-10,maleChar.getY());
            dp_downeasy_kissagain = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.let_them_down, R.drawable.kiss_again});
            dp_downeasy_kissagain.setPosition(maleChar.getX()+maleChar.getWidth(),dp_downeasy_kissagain.getY());
            dp_downeasy_kissagain.messPosX=dp_downeasy_kissagain.bgImage.getX()+dp_downeasy_kissagain.bgImage.getWidth()/2-(int)paint.measureText(dp_downeasy_kissagain.messString)/2;
            dp_downeasy_kissagain.setVisible(false);

            faded_bgSprite.setVisible(false);
            sceneNumber=2;
            subSceneNumber=0;
            touchScene=1;
            isDelay=true;
            delayCounter=0;

        }
        else if(GlobalVariables.INVITE_IN==25){//Klub Image
            Bitmap temp_bag=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.dancing_sprite);
            dancingChar = new ShahSprite(temp_bag,temp_bag.getWidth()/4,temp_bag.getHeight());
            dancingChar.setPosition(width / 2-dancingChar.getWidth()/2, 0);
            dancingChar.setVisible(false);

            leadCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_couple));
            leadCouple.setPosition(width-leadCouple.getWidth()-leadCouple.getWidth()/2,height-leadCouple.getHeight());
            leadCouple.setVisible(false);

            watchingCouple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_dancing_couple));
            watchingCouple.setPosition(0,0);
            watchingCouple.setVisible(false);

            helper1 = new Helper(new String[]{
                    "The only way to deal with this is to show your love what they’re missing—make them jealous.",
                    "You’re really upset…maybe a drink will calm you down.",
                    "It’s easy for things to get out of control when you are drinking. You can easily end up doing something you’ll regret.",
                    "Your true love lied to you, and that is uncool. But you don’t have to do anything tonight… just try to have fun and talk to them tomorrow."
            });
            helper1.setVisible(false);
            maleChar.setVisible(false);
           // popUpInstruction.setVisible(false);
            sceneNumber=1;
            subSceneNumber=0;
            isDelay=true;
            delayCounter=0;

        }
       else if(GlobalVariables.INVITE_IN==12){

            sceneNumber=0;
            subSceneNumber=0;
            isDelay=true;
            delayCounter=0;

        }
        System.out.println("End Joinig.......");
        Log.d("msg", "End Joinig.......");
        gameChangerEffect=new GameChangerEffect();
        myConstant.backGround.recycle();
        myConstant.waitingStar.recycle();
    }
    @Override
    public void recycle() {
        drawSomething(myCanvas);
        //scene_2_recycle();

        bgSprite.recycle();
        faded_bgSprite.recycle();
        femaleChar.recycle();

        if(backgroundMessage!=null)  backgroundMessage.recycle();
        message.recycle();

       // popUpInstruction.recycle();
        dp_home_club_inviteIn.recycle();
        twoOptionMess.recycle();
       // gameChangerMessage.recycle();

        if(dancingChar!=null) {
            dancingChar.recycle();
            watchingCouple.recycle();
            leadCouple.recycle();
        }


        maleChar.recycle();

        innerThought_top_left.recycle();
        innerThought_top_right.recycle();
        innerThought_bottom_left.recycle();

        if(dp_downeasy_kissagain!=null){
            dp_downeasy_kissagain.recycle();
            if(roomate_surprised_bg!=null)roomate_surprised_bg.recycle();
            if(roomate_surprised_sprite!=null)roomate_surprised_sprite.recycle();

            if(kissingChar!=null)kissingChar.recycle();
            if(roomateChar!=null)roomateChar.recycle();
            if(door!=null)door.recycle();

        }
        if(whichDecisionPoint!=null)whichDecisionPoint.recycle();
        if(mythMessage!=null)mythMessage.recycle();
        if(helper!=null)helper.recycle();
        if(helper1!=null)helper1.recycle();
        if(dancingChar!=null) dancingChar.recycle();
        timer.recycle();
        gameChangerEffect.recycle();

      if(dancing_Couple01!=null) {
          dancing_Couple01.recycle();
          dancing_Couple01_copy1.recycle();
          dancing_Couple02.recycle();
          disco_light01.recycle();
          disco_light02.recycle();
      }
    }
    private void scene_0_recycle(){
        if(dp_home_club_inviteIn!=null){
            dp_home_club_inviteIn.recycle();
            helper.recycle();
            mythMessage.recycle();

            innerThought_top_left.recycle();
            innerThought_top_right.recycle();
            innerThought_bottom_left.recycle();
        }

    }
    private void scene_1_recycle(){
        if(dp_ignore_jelous_drunk!=null){
            dp_ignore_jelous_drunk.recycle();
            //gameChangerMessage.recycle();
            leadCouple.recycle();
            dancingChar.recycle();
            watchingCouple.recycle();
            helper1.recycle();

            dancing_Couple01.recycle();
            dancing_Couple01_copy1.recycle();
            dancing_Couple02.recycle();
            disco_light01.recycle();
            disco_light02.recycle();

            innerThought_top_left.recycle();
            innerThought_top_right.recycle();
            innerThought_bottom_left.recycle();
        }
    }
    private void scene_2_recycle(){
        bgSprite.recycle();
        faded_bgSprite.recycle();
        femaleChar.recycle();

        if(dp_downeasy_kissagain!=null){
            dp_downeasy_kissagain.recycle();
            helper.recycle();
            mythMessage.recycle();
            kissingChar.recycle();
            roomateChar.recycle();
            door.recycle();
            dp_klub_hotheavy.recycle();

            maleChar.recycle();

            innerThought_top_left.recycle();
            innerThought_top_right.recycle();
            innerThought_bottom_left.recycle();
        }

        if(backgroundMessage!=null)backgroundMessage.recycle();
        message.recycle();
       // popUpInstruction.recycle();
        twoOptionMess.recycle();
    }



}
