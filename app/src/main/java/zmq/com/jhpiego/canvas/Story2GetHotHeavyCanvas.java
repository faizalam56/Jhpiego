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
import zmq.com.jhpiego.preferences.MyPreference;
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

public class Story2GetHotHeavyCanvas extends BaseSurface implements Runnable, Recycler {


    private int sceneNumber=-1;
    private int subSceneNumber;
    private ShahSprite bgSprite;
    private ShahSprite faded_bgSprite;
    private ShahSprite maleChar,condom_expired_Char,expiredCondom;
    private ShahSprite condoms_drawer_bg,drawer_with_condom,drawer_condom_overlap;

    DecisionPoint dp_fullSpeed_useCondom,dp_buy_borrow_cool,dp_fullSpeed_borrow,dp_useExpired_cool;
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
    private Canvas myCanvas;

    private int countDown=GlobalVariables.timerValue;
    private boolean countDownStatus=false;
    private DecisionPoint whichDecisionPoint;
    private Timer timer;
    private GameChangerEffect gameChangerEffect;
    private Paint paint1;

    public Story2GetHotHeavyCanvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        paint.setColor(Color.WHITE);
        paint1=new Paint();
        myConstant=new Constant(context);

//        paint.setTextAlign(Paint.Align.CENTER);

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" Story2GetHotHeavyCanvas surfaceDestroyed Called...");
        }
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
                if ((delayCounter == 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible() && dilogueDuration==2)) {
                    paint.setColor(Color.BLACK);
                    dilogueDuration=3;
                    faded_bgSprite.setVisible(false);
                    dp_fullSpeed_useCondom.setVisible(false);

                    maleChar.setPosition(width/2-maleChar.getWidth()/2,maleChar.getY());
                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                    innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                    innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                            maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);

                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                    innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                    innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

                    innerThought_top_left.setVisible(true);

                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    innerThought_top_right.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    innerThought_bottom_left.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    innerThought_bottom_right.setVisible(true);
                    delayCounter = 0;
                   //isDelay=false;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    paint.setColor(Color.WHITE);
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    innerThought_bottom_right.setVisible(false);

                    maleChar.setPosition(-10, maleChar.getY());
                    faded_bgSprite.setVisible(true);
                    dp_fullSpeed_useCondom.setVisible(true);

                    //***************** Calculating Decision Time ******************
                    GlobalVariables.start_Time=System.currentTimeMillis();
                    // ***********************************

                    //twoOptionMess.setVisible(true);

                    delayCounter=0;


                }
                if(delayCounter==2 && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible() && !twoOptionMess.isVisible()) && dp_fullSpeed_useCondom.isVisible()){
                    twoOptionMess.setVisible(true);
                    delayCounter=0;
                    isDelay=false;
                }

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_fullSpeed_useCondom.update(g,paint);

                innerThought_top_left.update(g, paint);
                innerThought_top_right.update(g, paint);
                innerThought_bottom_left.update(g, paint);
                innerThought_bottom_right.update(g,paint);
                twoOptionMess.update(g,paint);
                break;

            case 1:
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    dp_fullSpeed_useCondom.update(g, paint);
                    helper.update(g, paint);
                    backgroundMessage.update(g,paint);

                }
                break;

            case -1:
                if(drawer_with_condom.getY()<(int)(GlobalVariables.yScale_factor*73)){
                    int y=drawer_with_condom.getY();
                    y=y+10;
                    drawer_with_condom.setPosition(drawer_with_condom.getX(),y);
                }
                else{
                    if(drawer_with_condom.isVisible()){
                        popUpInstruction.setVisible(true);

//                        gameChangerEffect.setVisible(false);//GameChanger
                        isDelay=true;
                        subSceneNumber=2;
                    }

                }

                condoms_drawer_bg.paint(g,null);
                drawer_with_condom.paint(g,null);
                drawer_condom_overlap.paint(g,null);
                break;

            case 2://Use Condom
//                if(drawer_with_condom.getY()<73){
//                    int y=drawer_with_condom.getY();
//                    y=y+10;
//                    drawer_with_condom.setPosition(drawer_with_condom.getX(),y);
//                }
//                else{
//                    if(drawer_with_condom.isVisible()){
//                        popUpInstruction.setVisible(true);
//
////                        gameChangerEffect.setVisible(false);//GameChanger
//                        isDelay=true;
//                    }
//
//                }
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 && condoms_drawer_bg.isVisible()){
                    gameChangerEffect.setVisible(true);
                }

                if(delayCounter==4 && condoms_drawer_bg.isVisible()){
                    condoms_drawer_bg.setVisible(false);
                    drawer_with_condom.setVisible(false);
                    drawer_condom_overlap.setVisible(false);
                    popUpInstruction.setVisible(false);

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    maleChar.setVisible(true);
                    delayCounter=0;

                }
                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !dp_buy_borrow_cool.isVisible() && maleChar.isVisible())) {
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

                }
                if(delayCounter==4 && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() )){

                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);

                    paint.setColor(Color.WHITE);
                    maleChar.setPosition(-10, maleChar.getY());
                    dp_buy_borrow_cool.setPosition(maleChar.getX()+maleChar.getWidth(),dp_buy_borrow_cool.getY());
                    dp_buy_borrow_cool.messPosX=dp_buy_borrow_cool.bgImage.getX()+dp_buy_borrow_cool.bgImage.getWidth()/2-(int)paint.measureText(dp_buy_borrow_cool.messString)/2;

                    faded_bgSprite.setVisible(true);
                    dp_buy_borrow_cool.setVisible(true);

                    //***************** Calculating Decision Time ******************
                    GlobalVariables.start_Time=System.currentTimeMillis();
                    // ***********************************

                    delayCounter = 0;
                    isDelay=false;
                }

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                //gameChangerMessage.update(g,paint);
                innerThought_top_left.update(g, paint);
                innerThought_top_right.update(g, paint);
                innerThought_bottom_left.update(g, paint);

                condoms_drawer_bg.paint(g,null);
                drawer_with_condom.paint(g,null);
                drawer_condom_overlap.paint(g,null);
                popUpInstruction.update(g,paint);

                dp_buy_borrow_cool.update(g,paint);

                break;

            case 3://Buy From Kiosk


                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 && !gameChangerEffect.isVisible() && !maleChar.isVisible()){
                    gameChangerEffect.setVisible(true);
                }
                if(delayCounter==4 && !maleChar.isVisible()){
                    paint.setColor(Color.BLACK);
                    maleChar.setVisible(true);
                    popUpInstruction.setVisible(false);

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    delayCounter=0;

                    condoms_drawer_bg.recycle();
                    drawer_with_condom.recycle();
                    drawer_condom_overlap.recycle();
                }

                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !dp_fullSpeed_borrow.isVisible() && maleChar.isVisible())) {
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

                }
                if(delayCounter==2 && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() )){
                    paint.setColor(Color.WHITE);

                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);

                    maleChar.setPosition(-10, maleChar.getY());
                    dp_fullSpeed_borrow.setPosition(maleChar.getX()+maleChar.getWidth(),dp_fullSpeed_borrow.getY());
                    dp_fullSpeed_borrow.messPosX=dp_fullSpeed_borrow.bgImage.getX()+dp_fullSpeed_borrow.bgImage.getWidth()/2-(int)paint.measureText(dp_fullSpeed_borrow.messString)/2;

                    faded_bgSprite.setVisible(true);
                    dp_fullSpeed_borrow.setVisible(true);

                    //***************** Calculating Decision Time ******************
                    GlobalVariables.start_Time=System.currentTimeMillis();
                    // ***********************************


                    delayCounter = 0;
                    isDelay=false;
                }
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    popUpInstruction.update(g, paint);
                    innerThought_top_left.update(g, paint);
                    innerThought_top_right.update(g, paint);
                    innerThought_bottom_left.update(g, paint);
                    dp_fullSpeed_borrow.update(g, paint);
                }
                break;

            case 4://Borrow from friend
                if(isDelay){
                    delayCount();

                }
                if(delayCounter==2 && condom_expired_Char.isVisible()){
                    condom_expired_Char.setVisible(false);

                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.expired_condom_bg);
                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());

                    expiredCondom.setVisible(true);
                    popUpInstruction.setVisible(true);
                    delayCounter=0;
                    //isDelay=false;
                }
                if(delayCounter==2 && !maleChar.isVisible()){
                    gameChangerEffect.setVisible(true);
                    //delayCounter=0;
                }
                if(delayCounter==4 && expiredCondom.isVisible()){

                    //faded_bgSprite.setVisible(true);
                    maleChar.setVisible(true);
                    expiredCondom.setVisible(false);
                    popUpInstruction.setVisible(false);

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.borrow_from_friend_bg);
                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());

                    delayCounter=0;
                }

                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible() &&!dp_useExpired_cool.isVisible() && maleChar.isVisible())) {
                    paint.setColor(Color.BLACK);
                    innerThought_top_left.setVisible(true);
                    faded_bgSprite.setVisible(false);

                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    innerThought_top_right.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    innerThought_bottom_left.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    innerThought_bottom_right.setVisible(true);
                    delayCounter = 0;
//                    isDelay=false;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible()&& !twoOptionMess.isVisible())) {
                    paint.setColor(Color.WHITE);
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    innerThought_bottom_right.setVisible(false);

                    maleChar.setPosition(-10, maleChar.getY());
                    dp_useExpired_cool.setPosition(maleChar.getX()+maleChar.getWidth(),dp_useExpired_cool.getY());
                    dp_useExpired_cool.messPosX=dp_useExpired_cool.bgImage.getX()+dp_useExpired_cool.bgImage.getWidth()/2-(int)paint.measureText(dp_useExpired_cool.messString)/2;

                    faded_bgSprite.setVisible(true);
                    dp_useExpired_cool.setVisible(true);

                    //***************** Calculating Decision Time ******************
                    GlobalVariables.start_Time=System.currentTimeMillis();
                    // ***********************************

                    //twoOptionMess.setVisible(true);

                    delayCounter=0;
                    isDelay=false;

                }
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    maleChar.paint(g, null);
                    expiredCondom.paint(g, null);
                    condom_expired_Char.paint(g,null);
                    //gameChangerMessage.update(g, paint);
                    popUpInstruction.update(g,paint);
                    innerThought_top_left.update(g, paint);
                    innerThought_top_right.update(g, paint);
                    innerThought_bottom_left.update(g, paint);
                    innerThought_bottom_right.update(g, paint);

                    dp_useExpired_cool.update(g, paint);
                }
                break;

            case 5://cool things down
                if(!bgSprite.sourceImage.isRecycled()) {
                    paint.setColor(Color.WHITE);
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.update(g, paint);
                }
                break;
        }
    }


      private void repeatedCode(){

          paint.setColor(Color.WHITE);
          popUpInstruction.setMessString("Oh no! The condom has expired ");
          popUpInstruction.setVisible(false);

          Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.borrow_from_friend_bg);
          bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());

          maleChar.setPosition(width/2-maleChar.getWidth()/2,maleChar.getY());

          innerThought_top_left.setMessString("Is she going to be here when I come back");
          innerThought_top_right.setMessString("Go back and convince to use without ");
          innerThought_bottom_left.setMessString("Maybe I should buy one");
          innerThought_bottom_right.setMessString("This is a definite sign that I should stop");

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

          dp_useExpired_cool = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.use_expired, R.drawable.cool_things_dwn});

          dp_useExpired_cool.setVisible(false);
          faded_bgSprite.setVisible(false);
          expiredCondom.setVisible(false);
          isDelay=true;
          delayCounter=0;
          subSceneNumber=4;
      }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context, R.raw.tap_sound);
        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
        //getSceneNumber(touchrecF);

        switch (sceneNumber) {
            case 0:
                switch (subSceneNumber) {
                    case 0:

                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)){
                            //*********Update Score.............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7, 5,3}));
                            // ***********************************

                            helper = new Helper(new String[]{
                                    "At this time of night, by the time you find a condom, the mood will be DEAD.",
                                    "Doesn’t reply to your text.",
                                    "Condoms are always available for free at the campus clinic. You should ALWAYS have one on hand just in case",
                                    "If you go looking for a condom, someone will see you and your true love will find out."
                            });

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dp_fullSpeed_useCondom;
                            //*************************************

                            backgroundMessage.setVisible(false);
                            twoOptionMess.setVisible(false);
                            //popUpInstruction.setVisible(true);
                            subSceneNumber=1;
                        }
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0, 0,0}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                            dp_fullSpeed_useCondom.setActive();

                            helper = new Helper(new String[]{
                                    "At this time of night, by the time you find a condom, the mood will be DEAD.",
                                    "Doesn’t reply to your text.",
                                    "Condoms are always available for free at the campus clinic. You should ALWAYS have one on hand just in case",
                                    "If you go looking for a condom, someone will see you and your true love will find out."
                            });
                            backgroundMessage.setVisible(false);
                            helper.setVisible(false);
                            //popUpInstruction.setVisible(false);
                            subSceneNumber=1;
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
                            dp_fullSpeed_useCondom.setActive();
                            helper.setVisible(false);

                        }

                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

                       if(dp_fullSpeed_useCondom.isVisible() && dp_fullSpeed_useCondom.getBtnAtIndext(0).getDstRect().intersect(touchrecF) && !helper.isVisible()) {

                           //***************** Calculating Decision Time ******************
                           GlobalVariables.end_Time=System.currentTimeMillis();
                           // ***********************************

                           //***************** Send time taken on decision ******************
                           UpdateScore.setDecisionTime(context,"D11",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                           // ***********************************

                           //*********Analytic Started.............
                           UpdateScore.setModulePath(context,"Full speed ahead ");
                           // ***********************************

                           //*********Update Score.............
                           UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{1,0, 1}));
                           // ***********************************
                           maleChar.setVisible(false);
                           dp_fullSpeed_useCondom.setVisible(false);
                           backgroundMessage.setMessString("You had a great time with your BFF last night but there might be some repercussions…");
                           backgroundMessage.setVisible(true);
                       }
                        if(dp_fullSpeed_useCondom.isVisible() && dp_fullSpeed_useCondom.getBtnAtIndext(1).getDstRect().intersect(touchrecF) && !helper.isVisible()) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D11",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            condoms_drawer_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.condoms_for_drawer_bg));
                            drawer_condom_overlap = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.drawer_with_condoms_overlap));
                            drawer_with_condom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.drawer_with_condoms));
                            drawer_with_condom.setPosition((int)(GlobalVariables.xScale_factor*107),-drawer_with_condom.getHeight()/2);

                            subSceneNumber=-1;//room is blinking for a second before drawer animation

                            gameChangerEffect.setVisible(false);
                            faded_bgSprite.setVisible(false);
                            dp_fullSpeed_useCondom.setVisible(false);
                            maleChar.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{5,5, 4}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Use Condom");
                            // ***********************************

                            maleChar.setPosition(width/2-maleChar.getWidth()/2,maleChar.getY());

                            innerThought_top_left.setMessString("Omg my roommate must have used the condoms we got from the clinic");
                            innerThought_top_right.setMessString("I can continue without it—she said she is clean");
                            innerThought_bottom_left.setMessString("Is this a sign that I should stop?");


                            innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                            innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                            innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                            innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                            innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                            innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                            innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

                            dp_buy_borrow_cool = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.buy_frm_kiosk, R.drawable.borrow_frm_friend,R.drawable.cool_things_dwn});
                            dp_buy_borrow_cool.setVisible(false);

//                            condoms_drawer_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.condoms_for_drawer_bg));
//                            drawer_condom_overlap = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.drawer_with_condoms_overlap));
//                            drawer_with_condom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.drawer_with_condoms));
//                            drawer_with_condom.setPosition((int)(GlobalVariables.xScale_factor*107),-drawer_with_condom.getHeight()/2);

                           // subSceneNumber=2;
                        }
                       if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

                           delayCounter = 0;
                           isDelay = false;
                           // Replay
                           gameThread.running = false;
                           gameThread = null;
                           this.surfaceHolder.removeCallback(this);
                           Intent temp = new Intent();

                           GlobalVariables.SCENE_4_5=30;
                           ((Activity) context).setResult(GlobalVariables.SCENE_4_5, temp);
                           ((Activity) context).finish();
                           this.recycle();
                       }
                        break;

                    case 2://Use Condom

//                        if(gameChangerMessage.isVisible() && gameChangerMessage.actionButton.getDstRect().intersect(touchrecF)){
//                            gameChangerMessage.setVisible(false);
//
//                            isDelay=true;
//                            delayCounter=0;
//                        }
                        if(dp_buy_borrow_cool.isVisible() && dp_buy_borrow_cool.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D12",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dp_buy_borrow_cool.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6,7, 5}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Buy from the Kiosk");
                            // ***********************************


//                              gameChangerMessage.setMessString("Oops! The Shop  is  closed ");
//                              gameChangerMessage.setVisible(true);
                              popUpInstruction.setMessString("Oops! The Shop  is  closed");
                              popUpInstruction.setVisible(true);
                              gameChangerEffect.setVisible(false);//GameChanger
                              dp_buy_borrow_cool.setVisible(false);

                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.chemist_shop);
                            bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());

                            maleChar.setPosition(width/2-maleChar.getWidth()/2,maleChar.getY());

                            innerThought_top_left.setMessString("Is she going to be here when I come back");
                            innerThought_top_right.setMessString("Go back and convince to use without ");
                            innerThought_bottom_left.setMessString("This is a definite sign that I should stop");


                            int gap=(int)(GlobalVariables.xScale_factor*55);
                            innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);
//                            innerThought_top_left.setMessLinePixelWidth(innerThought_top_left.mess_bgImage.getWidth()-gap);
//                            innerThought_top_left.setMessTextPosition(innerThought_top_left.mess_bgImage.getX()+5,innerThought_top_left.mess_bgImage.getY()+10);

                            innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);
//                            innerThought_top_right.setMessLinePixelWidth(innerThought_top_right.mess_bgImage.getWidth()-gap);
//                            innerThought_top_right.setMessTextPosition(innerThought_top_right.mess_bgImage.getX()+45,innerThought_top_right.mess_bgImage.getY()+10);

                            innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);
//                            innerThought_bottom_left.setMessLinePixelWidth(innerThought_bottom_left.mess_bgImage.getWidth()-gap);
//                            innerThought_bottom_left.setMessTextPosition(innerThought_bottom_left.mess_bgImage.getX()+5,innerThought_bottom_left.mess_bgImage.getY()+50);
                            innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                            innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                            innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);


                              paint.setColor(Color.WHITE);
                              dp_fullSpeed_borrow = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.full_speed02, R.drawable.borrow_frm_friend});
                              dp_fullSpeed_borrow.setVisible(false);
                              faded_bgSprite.setVisible(false);

                              maleChar.setVisible(false);
                              isDelay=true;
                              subSceneNumber=3;
                        }
                        if(dp_buy_borrow_cool.isVisible() && dp_buy_borrow_cool.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D12",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dp_buy_borrow_cool.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6,7, 5}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Borrow from your friend");
                            // ***********************************
                            condom_expired_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.boy_looking_down_condom));
                            condom_expired_Char.setPosition(width/2-condom_expired_Char.getWidth()/2,height-condom_expired_Char.getHeight());
                            condom_expired_Char.setVisible(true);

                            expiredCondom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.expired_condom_in_hand));
                            expiredCondom.setVisible(false);
                            expiredCondom.setPosition(0,height/2-expiredCondom.getHeight()/2);
                            maleChar.setVisible(false);
                            repeatedCode();

                        }
                        if(dp_buy_borrow_cool.isVisible() && dp_buy_borrow_cool.getBtnAtIndext(2).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D12",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{9,10, 8}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Cool things down");
                            // ***********************************


                            dp_buy_borrow_cool.setVisible(false);

                            backgroundMessage.setMessString("Awesome! Even though you were tempted, when a condom wasn’t available you made a great decision to not take the risk of an unwanted pregnancy, STI or HIV infection. Just because you start making out doesn’t mean you have to have sex.");
                            backgroundMessage.setVisible(true);
                            subSceneNumber=5;

                        }
                        break;

                    case 3://Buy From Kiosk
//                            if(gameChangerMessage.isVisible() && gameChangerMessage.actionButton.getDstRect().intersect(touchrecF)){
//                                gameChangerMessage.setVisible(false);
//                                paint.setColor(Color.BLACK);
//                                isDelay=true;
//                                delayCounter=0;
//
//                            }

                        if(dp_fullSpeed_borrow.isVisible() && dp_fullSpeed_borrow.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D13",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dp_fullSpeed_borrow.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{1,0, 1}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Full speed ahead");
                            // ***********************************


                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();

                            GlobalVariables.SCENE_4_5=30;
                            ((Activity) context).setResult(GlobalVariables.SCENE_4_5, temp);
                            ((Activity) context).finish();
                            this.recycle();
                        }
                        if(dp_fullSpeed_borrow.isVisible() && dp_fullSpeed_borrow.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D13",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************


                            dp_fullSpeed_borrow.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6,7, 5}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Borrow");
                            // ***********************************

                            condom_expired_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.boy_looking_down_condom));
                            condom_expired_Char.setPosition(width/2-condom_expired_Char.getWidth()/2,height-condom_expired_Char.getHeight());
                            condom_expired_Char.setVisible(true);

                            expiredCondom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.expired_condom_in_hand));
                            expiredCondom.setVisible(true);
                            expiredCondom.setPosition(0,height/2-expiredCondom.getHeight()/2);
                            maleChar.setVisible(false);
                            isDelay=false;
                            repeatedCode();
                        }
                        break;

                    case 4://Borrow From Friend

//                        if(gameChangerMessage.isVisible() && gameChangerMessage.actionButton.getDstRect().intersect(touchrecF)){
//
//                            gameChangerMessage.setVisible(false);
//                            expiredCondom.setVisible(false);
//                            faded_bgSprite.setVisible(false);
//                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.borrow_from_friend_bg);
//                            bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
//                            maleChar.setVisible(true);
//                            isDelay=true;
//                            delayCounter=0;
//                        }

                        if(dp_useExpired_cool.isVisible() && dp_useExpired_cool.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D14",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************


                            dp_useExpired_cool.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{2,3, 2}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Use Expired");
                            // ***********************************

                            delayCounter = 0;
                            isDelay = false;
                            // Replay
                            gameThread.running = false;
                            gameThread = null;
                            this.surfaceHolder.removeCallback(this);
                            Intent temp = new Intent();

                            GlobalVariables.SCENE_4_5=30;
                            ((Activity) context).setResult(GlobalVariables.SCENE_4_5, temp);
                            ((Activity) context).finish();
                            this.recycle();
                        }
                        if(dp_useExpired_cool.isVisible() && dp_useExpired_cool.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D14",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************


                            dp_useExpired_cool.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{9,10, 8}));
                            // ***********************************

                            //*********Analytic Started.............
                             UpdateScore.setModulePath(context,"Cool things down");
                             // ***********************************


                            faded_bgSprite.setVisible(true);
                            backgroundMessage.setMessString("Awesome! Even though you were tempted, when a condom wasn’t available you made a great decision to not take the risk of an unwanted pregnancy, STI or HIV infection. Just because you start making out doesn’t mean you have to have sex.");
                            backgroundMessage.setVisible(true);
                            subSceneNumber=5;
                        }
                        break;

                    case 5://Cool Things Down
                           if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

                               //************ Calculate Game EndTime**************
                               MyPreference.saveStringKeyValue(context, "endTime", MyUtility.getTime());
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
                               this.recycle();
                           }
                        break;

                    default:
                        break;
                }
                break;

            case 1://Jealous
                switch (subSceneNumber) {

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

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1bg));
        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        faded_bgSprite.setVisible(true);

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.boywrongmsg));
        int tempMaleX = intent.getIntExtra("maleX",-10);
        int tempMaleY = intent.getIntExtra("maleY", height - maleChar.getHeight());
        maleChar.setPosition(tempMaleX, tempMaleY);

        dp_fullSpeed_useCondom = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.full_speed, R.drawable.use_condom});
        dp_fullSpeed_useCondom.setPosition(maleChar.getX() + maleChar.getWidth(), dp_fullSpeed_useCondom.getY());
        dp_fullSpeed_useCondom.messPosX=dp_fullSpeed_useCondom.bgImage.getX()+dp_fullSpeed_useCondom.bgImage.getWidth()/2-(int)paint.measureText(dp_fullSpeed_useCondom.messString)/2;
        dp_fullSpeed_useCondom.setVisible(true);

//        int tempFemaleX = intent.getIntExtra("femaleX", 0);
//        int tempFemaleY = intent.getIntExtra("femaleY", 0);
//        femaleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.female_char));
//        femaleChar.setPosition(tempFemaleX, tempFemaleY);

        message = new Message(R.drawable.background_mess, "What will you do now?");
        message.setMessPosY(message.messPosY + 50);
        message.setVisible(false);

        twoOptionMess = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
        twoOptionMess.setPosition(0,height-twoOptionMess.mess_bgImage.getHeight());
        twoOptionMess.setMessPosY(twoOptionMess.messPosY + twoOptionMess.mess_bgImage.getHeight()/2);
        twoOptionMess.setVisible(false);

        popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                "Condom not available"
        /*GlobalVariables.getResource.getString(R.string.scene0_popup)*/);
        popUpInstruction.setVisible(false);


//        gameChangerMessage = new GameChangerMessage(R.drawable.gamechanger_message_bg, "Your BFF is advancing on  you again");
//        gameChangerMessage.setVisible(false);

        innerThought_top_left = new InnerThought(R.drawable.inner_thought_topleft_new, "Do we really have to use a condom?");
        innerThought_top_left.setVisible(false);
        innerThought_top_right = new InnerThought(R.drawable.inner_thought_topright_new, "I like and trust her");
        innerThought_top_right.setVisible(false);
        innerThought_bottom_left = new InnerThought(R.drawable.inner_thought_bottomleft_new, "I know everything about her and I think she is safe");
        innerThought_bottom_left.setVisible(false);
        innerThought_bottom_right = new InnerThought(R.drawable.inner_thought_bottomright_new, "We can always get emergency pills tomorrow.");
        innerThought_bottom_right.setVisible(false);

        backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"You decide to deal with your GF later & enjoy your night by dancing & having fun but then you realize…");
        timer=new Timer();
        gameChangerEffect=new GameChangerEffect();

        isDelay=true;
        subSceneNumber=0;
        sceneNumber=0;
    }


    @Override
    public void recycle() {
        drawSomething(myCanvas);

        bgSprite.recycle();
        maleChar.recycle();
        faded_bgSprite.recycle();
        //femaleChar.recycle();

        if(dp_fullSpeed_useCondom!=null)dp_fullSpeed_useCondom.recycle();
        if(dp_useExpired_cool!=null)dp_useExpired_cool.recycle();
        if(dp_fullSpeed_borrow!=null)dp_fullSpeed_borrow.recycle();
        if(dp_buy_borrow_cool!=null)dp_buy_borrow_cool.recycle();
        timer.recycle();

        message.recycle();
        twoOptionMess.recycle();
        popUpInstruction.recycle();
        backgroundMessage.recycle();
        //if(gameChangerMessage!=null)gameChangerMessage.recycle();

        innerThought_top_left.recycle();
        innerThought_top_right.recycle();
        innerThought_bottom_left.recycle();
        innerThought_bottom_right.recycle();

        if(helper!=null) helper.recycle();

        if(condom_expired_Char!=null)condom_expired_Char.recycle();
        if(expiredCondom!=null)expiredCondom.recycle();
        if(condoms_drawer_bg!=null){
            condoms_drawer_bg.recycle();
            drawer_with_condom.recycle();
            drawer_condom_overlap.recycle();
        }
        gameChangerEffect.recycle();
    }

}
