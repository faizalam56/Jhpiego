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

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.BackgroundMessage;
import zmq.com.jhpiego.messages.InnerThought;
import zmq.com.jhpiego.messages.Message;
import zmq.com.jhpiego.messages.PopUpInstruction;
import zmq.com.jhpiego.messages.PopUpMessage;
import zmq.com.jhpiego.messages.PopUpMessageBottom;
import zmq.com.jhpiego.other.DecisionPoint;
import zmq.com.jhpiego.other.GameChangerEffect;
import zmq.com.jhpiego.other.Helper;
import zmq.com.jhpiego.other.Timer;
import zmq.com.jhpiego.preferences.MyUtility;
import zmq.com.jhpiego.preferences.UpdateScore;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.Constant;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.MySound;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 20/4/2015.
 */

public class Scene4_5_Canvas extends BaseSurface implements Runnable, Recycler {


    private int sceneNumber=-1;
    private int subSceneNumber;
    ShahSprite bgSprite,kissing_char_bg,kissing_char;
    ShahSprite faded_bgSprite,joseph_thinking_expression;
    ShahSprite maleChar, femaleChar,dancingChar,condomChar,roomatetellevrybodyChar;


    DecisionPoint dpGuy_consume_drink_douche_clinic,dp_tellGF_trytoseeboth;
    Message message;
    PopUpMessage twoOptionMess;
    PopUpInstruction popUpInstruction;

    private BackgroundMessage backgroundMessage;
    //private GameChangerMessage gameChangerMessage;
    private PopUpMessage popUpMessage;
    private int dilogueDuration = 2;
    private boolean common_Scene=false;
    private InnerThought innerThought_top_left, innerThought_top_right, innerThought_bottom_left,innerThought_bottom_right;
    Helper helper;
    Intent intent;
    private Constant myConstant;
    private boolean onClickStatus=false;
    private Canvas myCanvas;

    private int countDown=GlobalVariables.timerValue;
    private boolean countDownStatus=false;
    private DecisionPoint whichDecisionPoint;
    private Timer timer;
    private GameChangerEffect gameChangerEffect;
    private Paint paint1;

    public Scene4_5_Canvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        paint.setColor(Color.WHITE);
        paint1=new Paint();
        myConstant=new Constant(context);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" Scene4_5_Canvas surfaceDestroyed Called...");
        }
    }
    @Override
    protected void drawSomething(Canvas g) {
       if(g!=null) g.drawColor(Color.TRANSPARENT);
        switch (sceneNumber) {
            case -1:
                myConstant.drawImageWithRotation(g);
                loadImages();
                break;

            case 0:
                scene0(g);
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber  +" Sub : "+subSceneNumber , 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 1:
                scene1(g);
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
           
            case 3:
                scene3(g);
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;

            default:
                break;
        }
        if(countDownStatus){
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

            countDownStatus=false;
            helper.setVisible(false);

            if(whichDecisionPoint!=null){
                onClickStatus=true;
                timer.setVisible(false);
                whichDecisionPoint.setActive();
            }
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

    public void scene0(Canvas g) {
        myCanvas=g;
        bgSprite.paint(g, null);
        switch (subSceneNumber) {

            case -2:
                 if(isDelay){
                     delayCount();
                 }
                if(delayCounter==5 ){
                    delayCounter=0;
                    subSceneNumber=0;
                }

                kissing_char_bg.paint(g,null);
                kissing_char.paint(g,null);
                break;

            case 0:

                 if(isDelay){
                     delayCount();
                 }
                if(delayCounter==4  && !dpGuy_consume_drink_douche_clinic.isVisible()){
                    maleChar.setPosition(-30,maleChar.getY());
                    bgSprite.setPosition(-(bgSprite.getWidth()-width),bgSprite.getY());
                    dpGuy_consume_drink_douche_clinic.setPosition(maleChar.getX()+maleChar.getWidth(),dpGuy_consume_drink_douche_clinic.getY());
                    dpGuy_consume_drink_douche_clinic.messPosX=dpGuy_consume_drink_douche_clinic.bgImage.getX()+dpGuy_consume_drink_douche_clinic.bgImage.getWidth()/2-(int)paint.measureText(dpGuy_consume_drink_douche_clinic.messString)/2;
                    dpGuy_consume_drink_douche_clinic.setVisible(true);
                    faded_bgSprite.setVisible(true);
                    popUpInstruction.setVisible(false);

                    delayCounter=0;

                    kissing_char_bg.recycle();
                    kissing_char.recycle();
                }
                if(delayCounter==2 && dpGuy_consume_drink_douche_clinic.isVisible()){
                    twoOptionMess.setVisible(true);
                    helper = new Helper(new String[]{
                            "I’m sure everything will be fine. Just try to forget about it.",
                            "You should really go to the clinic. They can give you advice and make sure everything else is ok.",
                            "You both need to come in to the clinic. I’ll be happy to set up an appointment for you guys either separately or together.",
                            "Drinks lots of hot tea."
                    });
                    helper.setVisible(false);
                    delayCounter=0;
                    isDelay=false;
                }
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                popUpInstruction.update(g,paint);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                twoOptionMess.update(g,paint);

                break;

            case 1:

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                //popUpInstruction.update(g,paint);
                helper.update(g,paint);
                break;

            case -1:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                break;

            case 2:
                   faded_bgSprite.paint(g,null);
                   backgroundMessage.update(g,paint);
                break;

            case 3:

                break;

            case 4: //Help yes button

                break;
            case 5:

                break;

            case 100:
                if(common_Scene) {
                    faded_bgSprite.setVisible(true);
                    sceneNumber = 3;
                    subSceneNumber = 0;

                    //drawSomething(g);
                }
                break;
        }
    }

    public void scene1(Canvas g) {
        myCanvas=g;
        bgSprite.paint(g,null);

        switch (subSceneNumber) {
            case 0:

                faded_bgSprite.setVisible(true);
                faded_bgSprite.paint(g, null);
                backgroundMessage.setMessString("You liked the kiss & thought that this could be your chance of taking revenge with your GF/BF but suddenly… ");
                backgroundMessage.update(g, paint);

                break;
            case 1:

                backgroundMessage.setVisible(false);
                femaleChar.paint(g, null);
                maleChar.paint(g, null);
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 ){
                    //faded_bgSprite.paint(g,null);
//                    gameChangerMessage.setVisible(true);
//                    gameChangerMessage.update(g,paint);
                    isDelay=false;

                }

                break;
            case 2:



                break;
            case 3:

                break;

            case 4:

                break;
            case 5:

                maleChar.paint(g,null);
                faded_bgSprite.paint(g,null);
                backgroundMessage.setVisible(true);
                backgroundMessage.setMessString("Well done. Intimate contact always needs to be consensual. This means that both people involved should be fully aware of what they are doing, and fully in agreement. By apologizing you are accepting responsibility and trying to preserve your friendship.");
                backgroundMessage.update(g, paint);
                break;
            case 6:

                break;
            case 7:


                break;

            case 100:
                if(common_Scene) {

                    //joseph_thinking_expression.setVisible(false);
                    sceneNumber = 3;
                    subSceneNumber = 0;
                    //drawSomething(g);
                }
                break;

            default:

                break;
        }


    }

    public void scene3(Canvas g){
        myCanvas=g;
        bgSprite.paint(g,null);

        switch (subSceneNumber) {
            case 0:
               if(isDelay){
                   delayCount();
               }
                if(delayCounter==4 ){
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                    innerThought_top_left.setMessString("Ooh s*** what did I do?");
                    innerThought_top_right.setMessString("How do I get out of this?");
                    innerThought_bottom_left.setMessString("Does anyone else know this happened?");

                    innerThought_bottom_right.setMessString("Is my BFF really the one for me?");

                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                    innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                    innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                            maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);

                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                    innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                    innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

                    isDelay=true;
                    delayCounter=0;
                    subSceneNumber=1;
                }
                faded_bgSprite.paint(g,null);
                backgroundMessage.update(g,paint);
                joseph_thinking_expression.paint(g,null);
                popUpInstruction.update(g,paint);
                break;

            case 1:

                paint.setColor(Color.BLACK);
                if(isDelay){
                    delayCount();
                }
                if ((delayCounter == 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;
                    joseph_thinking_expression.recycle();
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    innerThought_top_right.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    innerThought_bottom_left.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    innerThought_bottom_right.setVisible(true);
                    delayCounter = 0;
                }

                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    innerThought_bottom_right.setVisible(false);

                    maleChar.setPosition(-10,maleChar.getY());
                    dp_tellGF_trytoseeboth = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.tell_bf_gf, R.drawable.try_to_see_both});
                    dp_tellGF_trytoseeboth.setPosition(maleChar.getX()+maleChar.getWidth(),dp_tellGF_trytoseeboth.getY());

                    dp_tellGF_trytoseeboth.setPosition(maleChar.getX()+maleChar.getWidth(),dp_tellGF_trytoseeboth.getY());
                    dp_tellGF_trytoseeboth.messPosX=dp_tellGF_trytoseeboth.bgImage.getX()+dp_tellGF_trytoseeboth.bgImage.getWidth()/2-(int)paint.measureText(dp_tellGF_trytoseeboth.messString)/2;

                    helper = new Helper(new String[]{
                            "If your friend knows about your true love and doesn’t care that’s pretty much a dream come true... Friends with benefits!",
                            "Hey, you could risk losing them both!",
                            "Having multiple partners really increases your risk of getting an STI.",
                            "Are you sure you want to create a reputation for yourself as a two-timer? What happens when they find out you are juggling?"
                    });
                    helper.setVisible(false);
                    dp_tellGF_trytoseeboth.setVisible(true);
                    twoOptionMess.setVisible(false);
                    //popUpInstruction.setVisible(false);

                    delayCounter = 0;
                    faded_bgSprite.setVisible(true);
                    subSceneNumber=2;


                }
                maleChar.paint(g,null);
                innerThought_top_left.update(g, paint);
                innerThought_top_right.update(g, paint);
                innerThought_bottom_left.update(g, paint);
                innerThought_bottom_right.update(g, paint);

                break;

            case 2:
                paint.setColor(Color.WHITE);
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 && !twoOptionMess.isVisible()){
                    twoOptionMess.setVisible(true);
                    delayCounter=0;
                    isDelay=false;
                }

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_tellGF_trytoseeboth.update(g,paint);
                twoOptionMess.update(g,paint);
               // popUpInstruction.update(g,paint);
                helper.update(g,paint);
                break;

            case 3: //Tell GF/BF
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    backgroundMessage.update(g, paint);
                }

                break;

            case 4://Try to see both

                if(isDelay){
//                    paint.setColor(Color.WHITE);
                    delayCount();
                }
                if(delayCounter>=4 && roomatetellevrybodyChar.isVisible()){

                    paint.setColor(Color.WHITE);
                    roomatetellevrybodyChar.setVisible(false);
                    maleChar.setVisible(true);
                    popUpInstruction.setVisible(false);

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    delayCounter=0;
                    //isDelay=false;
                }

                if ((delayCounter == 1 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !roomatetellevrybodyChar.isVisible())) {
                    paint.setColor(Color.BLACK);
//                    maleChar.setVisible(true);
//                    popUpInstruction.setVisible(false);
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
                }

                //thinking bubble


                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() )) {
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    faded_bgSprite.setVisible(true);
                    backgroundMessage.setVisible(true);
                    paint.setColor(Color.WHITE);
                    isDelay=false;
                    delayCounter=0;
                }

                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g, null);
                    roomatetellevrybodyChar.paint(g,null);

                    popUpInstruction.update(g, paint);
                    innerThought_top_left.update(g, paint);
                    innerThought_top_right.update(g, paint);
                    innerThought_bottom_left.update(g, paint);
                   // faded_bgSprite.paint(g, null);

                    backgroundMessage.update(g, paint);
                }

                break;
//



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
//                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
//                            faded_bgSprite.setVisible(false);
//                            backgroundMessage.setVisible(false);
//                            maleChar.setVisible(true);
//
//                            helper = new Helper(new String[]{
//                                    "I’m sure everything will be fine. Just try to forget about it.",
//                                    "You should really go to the clinic. They can give you advice and make sure everything else is ok.",
//                                    "You both need to come in to the clinic. I’ll be happy to set up an appointment for you guys either separately or together.",
//                                    "Wash yourself with coca cola."
//                            });
//                            helper.setVisible(false);
//                            isDelay=true;
//                        }
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {

                            //*********Score Update............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7, 5, 3}));
                            // ***********************************

                            helper.setVisible(true);
                            twoOptionMess.setVisible(false);
                            //popUpInstruction.setVisible(true);

                            //***********Helper Count Down**********
                            countDownStatus=true;
                                    if(whichDecisionPoint!=null){
                                    whichDecisionPoint.recycle();
                                    }
                                    countDown=GlobalVariables.timerValue;
                                    whichDecisionPoint=dpGuy_consume_drink_douche_clinic;
                            //*************************************

                            subSceneNumber=1;
                        }
                        if (twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                            dpGuy_consume_drink_douche_clinic.setActive();
                            //popUpInstruction.setVisible(false);
                            subSceneNumber=-1;
                            onClickStatus=true;
                        }
                        break;
                    case 1:

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

                            dpGuy_consume_drink_douche_clinic.setActive();
                            helper.setVisible(false);
                            onClickStatus=true;
                            subSceneNumber=-1;

                        }

                        break;

                    case -1:

                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(0).getDstRect().intersect(touchrecF)  && onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Consume emergency contraceptive");
                            // ***********************************

                            backgroundMessage.setMessString("EC cannot be taken by men. However, it might be smart to check with Susan and see if she has taken EC.");
                            backgroundMessage.setVisible(true);
                            subSceneNumber=2;
//                            common_Scene=true;
//                            subSceneNumber=100;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(1).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Drink lots of hot tea.");
                            // ***********************************

                            backgroundMessage.setMessString("Drinking hot tea will not prevent pregnancy or an STI. Any time you have a sexual encounter that is unprotected with a condom or Future Protection method you are at risk. The best thing to do is go to the clinic.");
                            backgroundMessage.setVisible(true);
                            subSceneNumber=2;
//                            common_Scene=true;
//                            subSceneNumber=100;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(2).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Douche");
                            // ***********************************

                            backgroundMessage.setMessString("Traditional medicines will not prevent pregnancy or an STI. Any time you have a sexual encounter that is unprotected with a condom or Future Protection method you are at risk. The best thing to do is go to the clinic.");
                            backgroundMessage.setVisible(true);

                            subSceneNumber=2;
//                            common_Scene=true;
//                            subSceneNumber=100;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(3).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6", MyUtility.timeDifference(GlobalVariables.end_Time, GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{10,9,9}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Go to the clinic");
                            // ***********************************


                            backgroundMessage.setMessString("Excellent choice! At the clinic you can get emergency contraceptives and good advice about STI/HIV testing and Future Protection methods that are safe and reliable.");
                            backgroundMessage.setVisible(true);
                            popUpInstruction.setVisible(false);
                            subSceneNumber=2;
//                            common_Scene=true;
//                            subSceneNumber=100;

                        }
                        break;
                    case 2:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)) {
//                            backgroundMessage.setMessString("Now that you have taken the precautions, you can’t stop thinking about last night…");
                            backgroundMessage.setVisible(false);
                            popUpInstruction.setMessString("Now that you have taken the precautions, you can’t stop thinking about last night…");
                            popUpInstruction.setVisible(true);
                            common_Scene=true;
                            //subSceneNumber=100;

                            faded_bgSprite.setVisible(false);
                            helper.recycle();

                            joseph_thinking_expression = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.joseph_thinking_expression));
                            sceneNumber = 3;
                            subSceneNumber = 0;
                            isDelay=true;
                        }
                        break;
                    case 3:

                        break;

                    default:
                        break;
                }
                break;


            case 3:// For Commone scenes of dpGuy_consume_drink_douche_clinic
                switch (subSceneNumber) {
                    case 0:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            backgroundMessage.setVisible(false);

                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.boywrongmsg);
                            maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                            maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                            innerThought_top_left.setMessString("Ooh s*** what did I do?");
                            innerThought_top_right.setMessString("How do I get out of this?");
                            innerThought_bottom_left.setMessString("Does anyone else know this happened?");

                            innerThought_bottom_right.setMessString("Is my BFF really the one for me?");

                            innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                            innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                            innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                            innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                                    maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);

                            innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                            innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                            innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                            innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

                            isDelay=true;
                            delayCounter=0;
                            subSceneNumber=1;
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5,3}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                           // popUpInstruction.setVisible(true);
                            helper.setVisible(true);

                            //***********Helper Count Down**********
                                countDownStatus=true;
                                        if(whichDecisionPoint!=null){
                                        whichDecisionPoint.recycle();
                                        }
                                        countDown=GlobalVariables.timerValue;
                                        whichDecisionPoint=dp_tellGF_trytoseeboth;
                           //*************************************

                        }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************


                            twoOptionMess.setVisible(false);
                            dp_tellGF_trytoseeboth.setActive();
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

                            dp_tellGF_trytoseeboth.setActive();
                            helper.setVisible(false);

                        }

                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                                timer.setVisible(false);
//                                countDownStatus=false;
//                                countDown=GlobalVariables.timerValue;
//                                }
                        //***************************************

                        if(!helper.isVisible() && dp_tellGF_trytoseeboth.isVisible() && delayCounter==0 && dp_tellGF_trytoseeboth.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D8",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dp_tellGF_trytoseeboth.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{9,6, 5}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Tell GF");
                            // ***********************************

                            backgroundMessage.setMessString("Your Gf has broken up with you, but at least you were honest and you are still friends with Susan. Who knows… the best loves often start as good friends");
                            backgroundMessage.setVisible(true);
                            faded_bgSprite.setVisible(true);
                            subSceneNumber=3;
                        }
                        if(!helper.isVisible() && dp_tellGF_trytoseeboth.isVisible() && delayCounter==0 && dp_tellGF_trytoseeboth.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D8",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dp_tellGF_trytoseeboth.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{3,2,1}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Try to see both of them until you figure out what you really want.");
                            // ***********************************

//                            gameChangerMessage.setMessString("Roomie tells everybody about him juggling both his GF");
//                            gameChangerMessage.setVisible(false);

                            popUpInstruction.setMessString("Roomie tells everybody about him juggling both his GF");
                            popUpInstruction.setVisible(true);

                            gameChangerEffect.setVisible(true);//GameChanger

                            maleChar.setVisible(false);

                            roomatetellevrybodyChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roomie_tells_everyone));


                            backgroundMessage.setMessString("You lose both- your GF as well as Susan");
                            backgroundMessage.setVisible(false);

                            faded_bgSprite.setVisible(false);
                            maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                            innerThought_top_left.setMessString("What? I thought you were loyal");
                            innerThought_top_right.setMessString("I knew she was jealous");
                            innerThought_bottom_left.setMessString("You must be interested in my gf");

                            innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);
                            innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);
                            innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                            innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                            innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                            innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);

                            twoOptionMess.setVisible(false);

                            isDelay=true;
                            //delayCounter=0;
                            subSceneNumber=4;
                        }

                        break;
                    case 3:// After Math
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

//                            //*********Analytic Started.............
//                            UpdateScore.setModulePath(context,"After Math");
//                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.AFTER_MATH=500;
                            ((Activity) context).setResult(GlobalVariables.AFTER_MATH, temp);
                            ((Activity) context).finish();
                            this.recycle();

                        }

                        break;
                    case 4://After math
//                        if(gameChangerMessage.isVisible() && gameChangerMessage.actionButton.getDstRect().intersect(touchrecF)){
//                            gameChangerMessage.setVisible(false);
//                            faded_bgSprite.setVisible(false);
//                            roomatetellevrybodyChar.setVisible(false);
//                            maleChar.setVisible(true);
//                            isDelay=true;
//                            delayCounter=0;
//
//                        }
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

//                            //*********Analytic Started.............
//                            UpdateScore.setModulePath(context,"After Math");
//                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();
                            GlobalVariables.AFTER_MATH=500;
                            ((Activity) context).setResult(GlobalVariables.AFTER_MATH, temp);
                            ((Activity) context).finish();
                            this.recycle();
                        }

                        break;

                    case 5:
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

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.nxt_morning_bg));
        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        faded_bgSprite.setVisible(false);

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.mor_thinking_boy));
        int tempMaleX = intent.getIntExtra("maleX",width/2-maleChar.getWidth()/2);
        int tempMaleY = intent.getIntExtra("maleY", height - maleChar.getHeight());
        maleChar.setPosition(tempMaleX, tempMaleY);
        maleChar.setVisible(true);

        dpGuy_consume_drink_douche_clinic = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.consume, R.drawable.drink,R.drawable.douche, R.drawable.gotoclinic});
        dpGuy_consume_drink_douche_clinic.setVisible(false);

//        int tempFemaleX = intent.getIntExtra("femaleX", 0);
//        int tempFemaleY = intent.getIntExtra("femaleY", 0);
//        femaleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.female_char));
//        femaleChar.setPosition(tempFemaleX, tempFemaleY);

        message = new Message(R.drawable.background_mess, "What will you do now?");
        message.setMessPosY(message.messPosY + 50);
        message.setVisible(false);

//        gameChangerMessage = new GameChangerMessage(R.drawable.gamechanger_message_bg, "Your BFF is advancing on  you again");
//        gameChangerMessage.setVisible(false);

        twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
        twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
        twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
        twoOptionMess.setVisible(false);

        popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                "You feel pretty bad about last night’s sexual encounter with Susan & worried about getting catching an STI/Getting infected with HIV."
        /*GlobalVariables.getResource.getString(R.string.scene0_popup)*/);


//        gameChangerMessage = new GameChangerMessage(R.drawable.gamechanger_message_bg, "Your BFF is advancing on  you again");
//        gameChangerMessage.setVisible(false);

        innerThought_top_left = new InnerThought(R.drawable.inner_thought_topleft_new, "Do we really have to use a condom?");
        innerThought_top_left.setVisible(false);
        innerThought_top_right = new InnerThought(R.drawable.inner_thought_topright_new, "I like and trust her");
        innerThought_top_right.setVisible(false);
        innerThought_bottom_left = new InnerThought(R.drawable.inner_thought_bottomleft_new, "I know everything about her and I think she is safe");
        innerThought_bottom_left.setVisible(false);
        innerThought_bottom_right = new InnerThought(R.drawable.inner_thought_bottomright_new, "Doesn’t matter we can get emergency pills later");
        innerThought_bottom_right.setVisible(false);

        backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"You feel pretty bad about last night’s sexual encounter with Susan & worried about getting catching an STI/Getting infected with HIV.");

        kissing_char_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1bg));
        kissing_char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_kissing));
        kissing_char.setPosition(width / 2 - kissing_char.getWidth() / 2, height / 2 - kissing_char.getHeight() / 2);

        timer=new Timer();

        isDelay=true;

        System.out.println("End Joinig.......");
        Log.d("msg", "End Joinig.......");
        gameChangerEffect=new GameChangerEffect();
        sceneNumber=0;
        subSceneNumber=-2;
    }

    @Override
    public void recycle() {
        drawSomething(myCanvas);
        bgSprite.recycle();
        maleChar.recycle();

        if(dpGuy_consume_drink_douche_clinic!=null)dpGuy_consume_drink_douche_clinic.recycle();
        if(dp_tellGF_trytoseeboth!=null)dp_tellGF_trytoseeboth.recycle();

        message.recycle();
        twoOptionMess.recycle();
       // popUpInstruction.recycle();
        backgroundMessage.recycle();
        timer.recycle();

        innerThought_top_left.recycle();
        innerThought_top_right.recycle();
        innerThought_bottom_left.recycle();
        innerThought_bottom_right.recycle();

        if(helper!=null) helper.recycle();
        if(dancingChar!=null)dancingChar.recycle();
        if(condomChar!=null)condomChar.recycle();
        if(roomatetellevrybodyChar!=null)roomatetellevrybodyChar.recycle();
        gameChangerEffect.recycle();
    }


}
