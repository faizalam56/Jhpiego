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

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.canvas.BaseSurface;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.AutoHelper;
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

public class G_Story2SubActivityCanvas extends BaseSurface implements Runnable, Recycler {


    private int sceneNumber=-1;
    private int subSceneNumber;
    private ShahSprite bgSprite;
    private ShahSprite faded_bgSprite;
    private ShahSprite maleChar, femaleChar,dancingChar,condomChar;


    private DecisionPoint dp_roomInviteIn_down,dp_acceptApologize_pressure,dpGuy_consume_drink_douche_clinic,dp_dontCondom_takeCondom,dp_tellGF_trytoseeboth;
    private Message message;
    private PopUpMessage twoOptionMess,twoOptionMessBottom;
    private PopUpInstruction popUpInstruction;

    private BackgroundMessage backgroundMessage;
    //private GameChangerMessage gameChangerMessage;
    private AutoHelper gameChangerMessage_For_ForcelyHelp;
    private PopUpMessage popUpMessage;
    private Message maleConversationMessage, femaleConversationMessage;
    private int dilogueDuration = 4;
    private boolean common_Scene=false;
    private InnerThought innerThought_top_left, innerThought_top_right, innerThought_bottom_left,innerThought_bottom_right;
    private Helper helper;

    //ShahSprite door1;
    Intent intent;
    private Constant myConstant;
    private Canvas myCanvas;
    private boolean onClickStatus=false;

    private int countDown=GlobalVariables.timerValue;
    private boolean countDownStatus=false;
    private DecisionPoint whichDecisionPoint;
    private Timer timer;
    private Paint paint1;

    private ShahSprite dancing_Couple01,dancing_Couple01_copy1;
    private ShahSprite dancing_Couple02,joseph_thinking_expression;
    private ShahSprite disco_light01,disco_light02,roomatetellevrybodyChar;
    private ShahSprite backingOff_male_Char,backingOff_female_Char,kissing_char,kissingChar_nxtmorning;
    private ShahSprite condom_couple,condom_couple_zoom,condom_char_moving,condom_broke_bg;
    private int backing_girl_xCord=(int)(GlobalVariables.xScale_factor*170);
    private GameChangerEffect gameChangerEffect;
    private boolean nxtSceneStatus=false,isLineBrk=false,isTxtBrk=false;

    public G_Story2SubActivityCanvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        paint.setColor(Color.WHITE);
        myConstant=new Constant(context);
        paint1=new Paint();

    }

    private void getSceneNumber(){

        if(GlobalVariables.SWITCH_SCENE==100){//Dance & Have fun
            femaleChar.recycle();
            Bitmap temp1 = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.conversation_female);
            femaleChar.setImage(temp1, temp1.getWidth(), temp1.getHeight());
            femaleChar.setPosition(width/2-femaleChar.getWidth(),height-femaleChar.getHeight());

            maleChar.recycle();
            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.conversation_male);
            maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
            maleChar.setPosition(width/2,height-maleChar.getHeight());

            popUpInstruction.setMessString("You decide to deal with your BF later & enjoy your night by dancing & having fun but then you realize…");
            popUpInstruction.setVisible(true);

            subSceneNumber=0;
            sceneNumber=0;
            isDelay=true;
        }
        else if(GlobalVariables.SWITCH_SCENE==200){//Jealous
            backingOff_male_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.backing_off_boy));
            backingOff_male_Char.setPosition(width-(int)(GlobalVariables.xScale_factor*382),height-backingOff_male_Char.getHeight());
            backingOff_male_Char.setVisible(false);

            backingOff_female_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.backing_off_girl));
            backingOff_female_Char.setPosition(backing_girl_xCord,height-backingOff_female_Char.getHeight());
            backingOff_female_Char.setVisible(false);

            kissing_char=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.advance_couple));
            kissing_char.setPosition(width/2-kissing_char.getWidth()/2,height-kissing_char.getHeight());
            kissing_char.setVisible(true);
            popUpInstruction.setVisible(true);

            isDelay=true;
            subSceneNumber=0;
            sceneNumber=1;
        }
        else if(GlobalVariables.SWITCH_SCENE==300){// Get Drunk

            condomChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_friend_flipped));
            condomChar.setPosition(width/2-condomChar.getWidth()-30,height-condomChar.getHeight());
            maleChar.setVisible(false);

            backingOff_female_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_male));
            backingOff_female_Char.setPosition(width/2-backingOff_female_Char.getWidth(),height-backingOff_female_Char.getHeight());
            backingOff_female_Char.setVisible(false);

            condom_couple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_couple));
            condom_couple.setPosition(width/2-condom_couple.getWidth()/2,height-condom_couple.getHeight());

            condom_char_moving = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_char_move));
            condom_char_moving.setPosition(-condom_char_moving.getWidth(),height-condom_char_moving.getHeight());
            condom_char_moving.setVisible(false);

            condom_couple_zoom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_couple_zooom));
            condom_couple_zoom.setPosition(width-condom_couple_zoom.getWidth(),height-condom_couple_zoom.getHeight());
            condom_couple_zoom.setVisible(false);

            popUpInstruction.setVisible(true);
            subSceneNumber=0;
            isDelay=true;
            sceneNumber=2;
        }



    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" Story2SubActivityCanvas surfaceDestroyed Called...");
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
    }
    private void setVisibility(boolean visibility,Canvas g){
        dancing_Couple01.setVisible(visibility);
        dancing_Couple01_copy1.setVisible(visibility);
        dancing_Couple02.setVisible(visibility);
        disco_light01.setVisible(visibility);
        disco_light02.setVisible(visibility);

        disco_light01.paint(g,null);
        disco_light02.paint(g,null);
        dancing_Couple01.paint(g,null);
        dancing_Couple01_copy1.paint(g,null);
        dancing_Couple02.paint(g,null);


        dancing_Couple01.nextFrame();
        dancing_Couple01_copy1.nextFrame();
        dancing_Couple02.nextFrame();
        disco_light01.nextFrame();
        disco_light02.nextFrame();
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
                if(whichDecisionPoint .equals(dp_roomInviteIn_down)){
                    subSceneNumber=6;
                }
                if(whichDecisionPoint .equals(dp_acceptApologize_pressure)){

                }
                if(whichDecisionPoint .equals(dp_tellGF_trytoseeboth)){

                }
                timer.setVisible(false);
                whichDecisionPoint.setActive();
            }
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
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 1:
                scene1(g);
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 2:
                scene2(g);
                // drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 3:
                scene3(g);
                // drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 4:
                scene4(g);
                // drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber +" Sub : "+subSceneNumber, 5, (int) (GlobalVariables.yScale_factor * 20), 20);
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

    public void scene0(Canvas g) {
        myCanvas=g;
        bgSprite.paint(g, null);
        switch (subSceneNumber) {
            case 0:

                if(isDelay){
                    delayCount();

                }
                if(delayCounter==6 && femaleChar.isVisible()){
                    femaleChar.setVisible(false);

                    maleChar.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.backing_off_kiss);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                    popUpInstruction.setMessString("Your BFF is advancing on  you again ");
                    delayCounter=0;
                    subSceneNumber=1;

                }

                setVisibility(true,g);
                femaleChar.paint(g,null);
                maleChar.paint(g,null);
                popUpInstruction.update(g,paint);
//                if(isDelay){
//                    delayCount();
//                }
//                if(delayCounter==2 ) {

//                    faded_bgSprite.setVisible(true);
//                    faded_bgSprite.paint(g, null);
//                    backgroundMessage.update(g, paint);
                //delayCounter=0;
                // isDelay=false;

                // }


                break;

            case 1:


                if(isDelay){
                    delayCount();
                }
                if(delayCounter>=2 && !gameChangerEffect.isVisible()) {

                    gameChangerEffect.setVisible(true);
//                    gameChangerMessage.setVisible(true);
//                    gameChangerMessage.update(g, paint);
                    delayCounter=0;
                    //isDelay=false;
                }
                if(delayCounter==2 && gameChangerEffect.isVisible()){
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

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

                    femaleChar.setVisible(false);
                    isDelay=true;
                    delayCounter=0;

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    subSceneNumber=2;
                }
                setVisibility(true,g);
                maleChar.paint(g,null);
                popUpInstruction.update(g,paint);
                //gameChangerEffect.update(g,paint1);
                break;

            case 2:
//                isDelay=true;
//                delayCounter=0;
                faded_bgSprite.setVisible(false);
                paint.setColor(Color.BLACK);
                if(isDelay){
                    delayCount();
                }
                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;
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
                //thinking bubble


                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    innerThought_bottom_right.setVisible(false);

                    maleChar.setPosition(-10,maleChar.getY());
                    dp_roomInviteIn_down.setPosition(maleChar.getX()+maleChar.getWidth(),dp_roomInviteIn_down.getY());
                    dp_roomInviteIn_down.messPosX=dp_roomInviteIn_down.bgImage.getX()+dp_roomInviteIn_down.bgImage.getWidth()/2-(int)paint.measureText(dp_roomInviteIn_down.messString)/2;
                    dp_roomInviteIn_down.setVisible(true);
                    dp_roomInviteIn_down.update(g, paint);
                    delayCounter = 0;
                    subSceneNumber=3;

                }
                setVisibility(true,g);
                maleChar.paint(g,null);
                innerThought_top_left.update(g, paint);
                innerThought_top_right.update(g, paint);
                innerThought_bottom_left.update(g, paint);
                innerThought_bottom_right.update(g, paint);


                break;

            case 3:
                if(isDelay){
                    delayCount();
                }
                paint.setColor(Color.WHITE);
                if(delayCounter==1 && dp_roomInviteIn_down.isVisible()){
                    twoOptionMessBottom.setVisible(true);
                    delayCounter=0;
                    isDelay=false;

                    helper = new Helper(new String[]{
                            "YOLO! Your true love has lied. This will make you feel better!",
                            "You don’t really know what’s going on with your True Love. If you do this, you’ll ruin everything! And everybody is watching!",
                            "Things are rarely as urgent as they seem. If your BFF really likes you, they will be willing to wait for you to sort things out.",
                            "Make sure that you aren’t doing it just for revenge."
                    });
                    helper.setVisible(false);
                }
                setVisibility(true,g);
                faded_bgSprite.setVisible(true);
                faded_bgSprite.paint(g,null);
                maleChar.paint(g, null);
                dp_roomInviteIn_down.update(g, paint);
                twoOptionMessBottom.update(g, paint);
                break;

            case 4: //Help yes button
                setVisibility(true,g);
                twoOptionMessBottom.setVisible(false);
//                helper.setVisible(true);
                faded_bgSprite.paint(g,null);
                maleChar.paint(g, null);
                dp_roomInviteIn_down.update(g, paint);
                helper.update(g,paint);

                //popUpInstruction.setVisible(true);
                //popUpInstruction.update(g,paint);
                break;
            case 5:
                setVisibility(true,g);
//                helper.setVisible(true);
                faded_bgSprite.paint(g,null);
                maleChar.paint(g, null);
                dp_roomInviteIn_down.update(g, paint);
                helper.update(g,paint);

                break;

            case 6:

                setVisibility(true,g);
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    dp_roomInviteIn_down.update(g, paint);
                }
                break;
            case 7:
                setVisibility(true,g);
                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.paint(g, null);
                    if (!isDelay && !backgroundMessage.isVisible()) {

                        twoOptionMess.setVisible(true);
                        twoOptionMess.update(g, paint);
                    }
                    if (isDelay) {
                        delayCount();
                    }
                    if (delayCounter == 1) {
                        twoOptionMess.setVisible(false);
                        //backgroundMessage.setVisible(true);
                        backgroundMessage.update(g, paint);
                        //delayCounter=0;
                        isDelay = false;
                    }
                }

                break;
        }
    }

    public void scene1(Canvas g) {
        myCanvas=g;
        bgSprite.paint(g,null);

        switch (subSceneNumber) {
            case 0:
                setVisibility(true,g);
//                    faded_bgSprite.setVisible(true);
//                    faded_bgSprite.paint(g, null);
//                    backgroundMessage.setMessString("You liked the kiss & thought that this could be your chance of taking revenge with your GF but suddenly… ");
//                    backgroundMessage.update(g, paint);

                popUpInstruction.setMessString("You liked the kiss & thought that this could be your chance of taking revenge with your BF but suddenly…");


                if(isDelay){
                    delayCount();
                }
                if(delayCounter==6 && kissing_char.isVisible()){
                    delayCounter=0;
                    kissing_char.setVisible(false);
                    backingOff_female_Char.setVisible(true);
                    backingOff_male_Char.setVisible(true);
                    popUpInstruction.setVisible(false);

                }

                if(delayCounter==2 && backingOff_female_Char.isVisible()){
                    delayCounter=0;
                    subSceneNumber=-1;
                    isDelay=false;
                }
                kissing_char.paint(g,null);
                backingOff_female_Char.paint(g,null);
                backingOff_male_Char.paint(g,null);
                popUpInstruction.update(g,paint);

                break;

            case -1:
                setVisibility(true,g);
                if(backingOff_male_Char.getX()<(width-backingOff_male_Char.getWidth()-10)){
                    backing_girl_xCord=backingOff_male_Char.getX();
                    backingOff_male_Char.setPosition(backing_girl_xCord+10,height-backingOff_male_Char.getHeight());
                    popUpInstruction.setVisible(true);
                    gameChangerEffect.setVisible(false);//GameChanger
                }
                else{
                    isDelay=true;
                }

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 && !gameChangerEffect.isVisible()){
                    gameChangerEffect.setVisible(true);
                    delayCounter=0;
                }
                if(delayCounter==2 && gameChangerEffect.isVisible()){
                    femaleChar.recycle();
                    Bitmap temp1 = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.conversation_female);
                    femaleChar.setImage(temp1, temp1.getWidth(), temp1.getHeight());
                    femaleChar.setPosition(30,height-femaleChar.getHeight());

                    maleChar.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.conversation_male);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width-maleChar.getWidth(),height-maleChar.getHeight());

                    delayCounter=0;

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    subSceneNumber=1;
                }

                backingOff_female_Char.paint(g,null);
                backingOff_male_Char.paint(g,null);
                popUpInstruction.setMessString("Your BFF started backing off ");
                popUpInstruction.update(g,paint);

                break;

            case 1:

                setVisibility(true,g);
                backgroundMessage.setVisible(false);
                femaleChar.paint(g, null);
                maleChar.paint(g, null);

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 && !popUpInstruction.isVisible()){
                    //faded_bgSprite.paint(g,null);
                    backingOff_female_Char.recycle();
                    backingOff_male_Char.recycle();
                    kissing_char.recycle();

                    //popUpInstruction.setVisible(true);
//                    gameChangerMessage.setVisible(true);
//                    gameChangerMessage.update(g,paint);

                    delayCounter=0;
                    //isDelay=false;

                }
                if(delayCounter==2 && popUpInstruction.isVisible()){
                    delayCounter=0;
                    popUpInstruction.setVisible(false);

                    femaleChar.setVisible(false);

                    //maleChar.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                    innerThought_top_left.setMessString("What’s going on? I thought you wanted this?");

                    innerThought_top_right.setMessString("I think he needs a little push—maybe they are just playing “hard to get”...");

                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);
                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);

                    subSceneNumber=2;
                }
                //popUpInstruction.update(g,paint);

                break;
            case 2:
                setVisibility(true,g);
                paint.setColor(Color.BLACK);
                if(isDelay){
                    delayCount();
                }
                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() )) {
                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;
                }
                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() )) {
                    innerThought_top_right.setVisible(true);
                    delayCounter = 0;

                }

                if ((delayCounter ==4 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() )) {
                    innerThought_top_right.setMessString("because everyone is watching. I want to continue");

                    //isDelay=false;
                }

                if(delayCounter==8 && innerThought_top_left.isVisible() && innerThought_top_right.isVisible()){
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    delayCounter=0;
                    //isDelay=false;
                    femaleChar.setVisible(true);

                    //maleChar.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.conversation_male);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width-maleChar.getWidth()-30,height-maleChar.getHeight());//maleChar.getWidth()/2+10

                    maleConversationMessage = new Message(R.drawable.talkbubble_female, "No! Idon’t like the way you are acting right now. We are in public, and you are just... ");
                    maleConversationMessage.setVisible(false);
                    maleConversationMessage.mess_bgImage.setPosition(maleChar.getX() -maleConversationMessage.mess_bgImage.getWidth()-15, maleChar.getY());
                    maleConversationMessage.setMessTextPosition(maleConversationMessage.mess_bgImage.getX() + 10, maleConversationMessage.mess_bgImage.getY() + 10);

                    femaleConversationMessage = new Message(R.drawable.talkbubble_male, "I thought you liked this…you kissed me earlier!");
                    femaleConversationMessage.setVisible(false);
                    femaleConversationMessage.mess_bgImage.setPosition(femaleChar.getX() + femaleChar.getWidth()-10, femaleChar.getY());
                    femaleConversationMessage.setMessTextPosition(femaleConversationMessage.mess_bgImage.getX() + 10, femaleConversationMessage.mess_bgImage.getY() + 10);
                    subSceneNumber=3;
                }
                femaleChar.paint(g,null);
                maleChar.paint(g, null);
                innerThought_top_left.update(g,paint);
                innerThought_top_right.update(g,paint);
                break;

            case 3:

                setVisibility(true,g);
                femaleChar.paint(g,null);
                maleChar.paint(g,null);
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2 && !maleConversationMessage.isVisible() && !femaleConversationMessage.isVisible()){
                    //maleConversationMessage.setVisible(true);
                    femaleConversationMessage.setVisible(true);
                    delayCounter=0;
                }

                if (delayCounter == dilogueDuration && femaleConversationMessage.isVisible() && !maleConversationMessage.isVisible()) {

                    femaleConversationMessage.setVisible(false);
//                    isDelay=false;
                    maleConversationMessage.setVisible(true);
                    delayCounter = 0;
                }
                if(delayCounter==4 && !femaleConversationMessage.isVisible() &&maleConversationMessage.isVisible() && !isLineBrk){
                    maleConversationMessage.setMessString("trying to make your boyfriend jealous! This isn’t what I want!");
                    isLineBrk=true;
                    delayCounter=0;
                }
                if(delayCounter==dilogueDuration && !femaleConversationMessage.isVisible() && maleConversationMessage.isVisible()){

                    maleConversationMessage.setVisible(false);
                    femaleConversationMessage.setVisible(false);

                    //maleChar.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(-10, maleChar.getY());

                    dp_acceptApologize_pressure = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.accept_aplogize_exit, R.drawable.putonpressure});

                    dp_acceptApologize_pressure.setPosition(maleChar.getX()+maleChar.getWidth(),dp_acceptApologize_pressure.getY());
                    dp_acceptApologize_pressure.messPosX=dp_acceptApologize_pressure.bgImage.getX()+dp_acceptApologize_pressure.bgImage.getWidth()/2-(int)paint.measureText(dp_acceptApologize_pressure.messString)/2;
                    dp_acceptApologize_pressure.setVisible(true);

                    helper = new Helper(new String[]{
                            "Maybe they are just playing hard to get because they don’t want your true love to get mad.",
                            "I really don’t know what to think. Mixed signals are so tricky and unfair!",
                            "Careful, here. Even if they started it, if they are saying no now, you need to respect that.",
                            "You are feeling very upset. This is not a great time to get physical with someone who really likes you. Someone could get really hurt."
                    });
                    gameChangerMessage_For_ForcelyHelp = new AutoHelper(R.drawable.faded_bg, "Stop this! You are getting out of control!");
                    helper.setVisible(false);

                    delayCounter=0;
                    subSceneNumber=4;
                }
                maleConversationMessage.update(g, paint);
                femaleConversationMessage.update(g, paint);
                break;

            case 4:
                setVisibility(true,g);
                maleConversationMessage.update(g, paint);

                paint.setColor(Color.WHITE);

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==1 && dp_acceptApologize_pressure.isVisible()){
                    twoOptionMessBottom.setVisible(true);
                    delayCounter=0;
                    isDelay=false;


                }


                faded_bgSprite.setVisible(true);
                faded_bgSprite.paint(g,null);

                dp_acceptApologize_pressure.update(g,paint);
                maleChar.paint(g,null);
                twoOptionMessBottom.update(g, paint);
                helper.update(g,paint);
                //popUpInstruction.update(g,paint);
                break;

            case 5:
                setVisibility(true,g);
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.setVisible(true);
                    backgroundMessage.setMessString("Well done. Intimate contact always needs to be consensual. This means that both people involved should be fully aware of what they are doing, and fully in agreement. By apologizing you are accepting responsibility and trying to preserve your friendship.");
                    backgroundMessage.update(g, paint);
                }
                break;

            case 6:
                setVisibility(true,g);
                maleChar.paint(g,null);
                faded_bgSprite.paint(g,null);
                gameChangerMessage_For_ForcelyHelp.update(g,paint);
                backgroundMessage.update(g,paint);
                break;

            case -7://New Scene added before going to next morning scenario
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==5){
                    delayCounter=0;
                    subSceneNumber=7;
                }
                condom_broke_bg.paint(g,null);
                kissingChar_nxtmorning.paint(g,null);
                break;

            case 7:

                if(isDelay){
                    delayCount();
                }

                if(delayCounter==4 && !twoOptionMessBottom.isVisible() ){
                    maleChar.setPosition(-30,maleChar.getY());

                    beforeNxtMorSceneRecycle();

                    //***************** Sound************
                    MySound.stopSound(context,GlobalVariables.Audio_File_Name);
                    MySound.playSound(context,R.raw.a);
                    GlobalVariables.Audio_File_Name=R.raw.a;
                    //*****************************

                    dpGuy_consume_drink_douche_clinic.setPosition(maleChar.getX()+maleChar.getWidth(),dpGuy_consume_drink_douche_clinic.getY());
                    dpGuy_consume_drink_douche_clinic.messPosX=dpGuy_consume_drink_douche_clinic.bgImage.getX()+dpGuy_consume_drink_douche_clinic.bgImage.getWidth()/2-(int)paint.measureText(dpGuy_consume_drink_douche_clinic.messString)/2;
                    dpGuy_consume_drink_douche_clinic.setVisible(true);
                    delayCounter=0;
                    twoOptionMessBottom.setVisible(true);
                    faded_bgSprite.setVisible(true);
                    popUpInstruction.setVisible(false);
                    isDelay=false;
                }

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);

                //backgroundMessage.update(g,paint);
                popUpInstruction.update(g,paint);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                helper.update(g,paint);
                twoOptionMessBottom.update(g,paint);
                //popUpInstruction.update(g,paint);
                break;

            case 8:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                helper.update(g,paint);
                break;

            case 100:
                if(isDelay){
                    delayCount();
                }

                if(delayCounter==10 && isTxtBrk){
                    backgroundMessage.setMessString("...more than two weeks. You need to consult with university clinic counselors and health providers so that you can make the best decisions. Your health depends upon it!!");
                    backgroundMessage.actionButton.setVisible(true);
                    isDelay=false;
                    delayCounter=0;
                    isTxtBrk=false;
                }

                if(!bgSprite.sourceImage.isRecycled()) {
                    faded_bgSprite.setVisible(true);
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.update(g, paint);
                }

                break;

            default:

                break;
        }


    }


    public void scene2(Canvas g) {//drunk
        myCanvas=g;
        bgSprite.paint(g,null);
        switch (subSceneNumber) {
            case 0:
                isDelay=true;
                if(isDelay){
                    delayCount();

                }
                if(delayCounter>=4 && condom_couple.isVisible()){
                    condom_couple.setVisible(false);
                    condom_char_moving.setVisible(true);
                    condom_couple_zoom.setVisible(true);
                    maleChar.setVisible(false);

                    backingOff_female_Char.setPosition(width-backingOff_female_Char.getWidth(),backingOff_female_Char.getY());
                    backingOff_female_Char.setVisible(false);
                    popUpInstruction.setVisible(false);

                    delayCounter=0;

                    //isDelay=false;
                }
                if(delayCounter>=4 && condom_couple_zoom.isVisible()){

                    delayCounter=0;
                    isDelay=true;

                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_get_drunk_flipped);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
                    maleChar.setVisible(true);

                    backingOff_female_Char.setVisible(true);
                    condom_couple_zoom.setVisible(false);
                    subSceneNumber=1;

                }
                setVisibility(true,g);

                if(condom_char_moving.isVisible() && condom_char_moving.getX()<width/2-3*condom_char_moving.getWidth()/2){//(int)(GlobalVariables.xScale_factor*565)
                    int x=condom_char_moving.getX();
                    x=x+10;
                    condom_char_moving.setPosition(x,condom_char_moving.getY());
                }
//                if(condom_char_moving.isVisible() && condom_char_moving.getX()==(int)(GlobalVariables.xScale_factor*565)){
//                    subSceneNumber=1;
//                }
                maleChar.paint(g,null);
                backingOff_female_Char.paint(g,null);

                condom_couple.paint(g,null);
                popUpInstruction.setMessString("Watching your true love,you decided to get drunk and dance with your BFF. Somehow you find yourself getting hot & heavy with your BFF…");
                popUpInstruction.update(g,paint);

                condom_couple_zoom.paint(g,null);
                condom_char_moving.paint(g,null);

//                System.out.println("DelayCounter Value = "+delayCounter +" isDelay= "+isDelay +" Subscene ="+subSceneNumber);
                break;

            case 1:
                setVisibility(true,g);
                popUpInstruction.setVisible(true);
                popUpInstruction.setMessString("A friend comes up to you & offers condom");
                //gameChangerEffect.setVisible(true);//GameChanger

                if(isDelay){
                    delayCount();

                }
                if(delayCounter==2 && !gameChangerEffect.isVisible()){
                    gameChangerEffect.setVisible(true);
                }
                if(delayCounter==4 ){
                    // gameChangerMessage.setMessString("A friend comes up to you & offers condom");
                    // gameChangerMessage.setVisible(true);

                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                    innerThought_top_left.setMessString("Why did he offer me a condom");
                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                    innerThought_top_right.setMessString("Does he know something I don’t");
                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                    innerThought_bottom_left.setMessString("Everyone else knows what is going on");
                    innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                    innerThought_bottom_right.setMessString("Does he think I’m too drunk?");
                    innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                            maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);

                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                    innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                    innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

                    isDelay=true;
                    delayCounter=0;

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    subSceneNumber=2;

                }
                //faded_bgSprite.paint(g,null);

                backingOff_female_Char.paint(g,null);
                maleChar.paint(g,null);
                condomChar.paint(g,null);
                popUpInstruction.update(g,paint);
                //gameChangerMessage.update(g,paint);

                break;

            case 2:
                setVisibility(true,g);
                paint.setColor(Color.BLACK);

                if(isDelay){
                    delayCount();
                }
                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;

                    condom_couple.recycle();
                    condom_char_moving.recycle();
                    backingOff_female_Char.recycle();
                    condom_couple_zoom.recycle();
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
                //thinking bubble


                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    innerThought_bottom_right.setVisible(false);

                    maleChar.setPosition(-10,maleChar.getY());
                    dp_dontCondom_takeCondom = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.dont_take_condom, R.drawable.take_condom});
                    dp_dontCondom_takeCondom.setPosition(maleChar.getX()+maleChar.getWidth(),dp_dontCondom_takeCondom.getY());

                    condom_broke_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_bedroom));
                    condom_broke_bg.setVisible(false);

                    twoOptionMessBottom.setVisible(false);
                    // gameChangerMessage.setVisible(false);

                    delayCounter = 0;
                    isDelay=false;
                    paint.setColor(Color.WHITE);
                    popUpInstruction.setVisible(false);

                    //maleChar.setVisible(false);

                    subSceneNumber=3;


                }
                maleChar.paint(g,null);
                innerThought_top_left.update(g,paint);
                innerThought_top_right.update(g,paint);
                innerThought_bottom_left.update(g,paint);
                innerThought_bottom_right.update(g,paint);

                break;

            case 3:// Take Condom & Have Sex
                setVisibility(true,g);
                if(isDelay && !nxtSceneStatus){

                    delayCount();
                }
                if(delayCounter==2 && !gameChangerEffect.isVisible() && popUpInstruction.isVisible()){
                    gameChangerEffect.setVisible(true);
                }
                if(delayCounter==4 && maleChar.isVisible() && popUpInstruction.isVisible()){
                    paint.setColor(Color.BLACK);
                    delayCounter=0;
                    maleChar.setVisible(true);
                    popUpInstruction.setVisible(false);

                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                    gameChangerEffect.setVisible(false);//GameChanger
                    gameChangerEffect.setCount(0);

                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
                    faded_bgSprite.setVisible(false);

                    innerThought_top_left.setMessString("Do I have another condom");
                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                    innerThought_top_right.setMessString("This is too good we can continue without one");
                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                    innerThought_bottom_left.setMessString("I have to get another one, will he be here when I come back");
                    innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                    innerThought_bottom_right.setMessString("Maybe this a sign that I should stop");
                    innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                            maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);

                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                    innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                    innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

                    dpGuy_consume_drink_douche_clinic = new DecisionPoint(R.drawable.g_decision_point_bg_smal, new int[]{R.drawable.consume, R.drawable.drink,R.drawable.take_pregnancy_test,R.drawable.douche, R.drawable.gotoclinic});
                    dpGuy_consume_drink_douche_clinic.setVisible(false);

                    helper = new Helper(new String[]{
                            "I’m sure everything will be fine. Just buy Emergency Contraceptives from that guy who sells them.",
                            "You should really go to the clinic. They can help you with Emergency contraceptives and make sure everything else is ok.",
                            "You both need to come in to the clinic. I’ll be happy to set up an appointment for you guys either separately or together.",
                            "First wash yourself with coca cola, and then get a pregnancy test."
                    });
                    helper.setVisible(false);

                }
                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible()) && maleChar.isVisible() && !popUpInstruction.isVisible()) {

                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;
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
                //thinking bubble


                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible())) {
                    innerThought_top_left.setVisible(false);
                    innerThought_top_right.setVisible(false);
                    innerThought_bottom_left.setVisible(false);
                    innerThought_bottom_right.setVisible(false);

                    //***************** Sound************
                    MySound.stopSound(context,GlobalVariables.Audio_File_Name);
                    MySound.playSound(context,R.raw.a);
                    GlobalVariables.Audio_File_Name=R.raw.a;
                    //*****************************

//                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.nxt_morning_bg);
//                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
//
//                    Bitmap tempMale = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.mor_thinking_boy);
//                    maleChar.setImage(tempMale, tempMale.getWidth(), tempMale.getHeight());
//                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
//                    maleChar.setVisible(true);

                    popUpInstruction.setMessString("You feel pretty bad about last night’s sexual encounter with Joseph & worried about getting pregnant/catching an STI/Getting infected with HIV.");
                    popUpInstruction.setVisible(false);

                    //popUpInstruction.setVisible(false);
                    paint.setColor(Color.WHITE);
                    faded_bgSprite.setVisible(false);

                    isDelay=true;
                    delayCounter=0;
                    beforeNxtMorScene();
                    subSceneNumber=-4;
                }

                if(condom_broke_bg!=null)condom_broke_bg.paint(g,null);
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_dontCondom_takeCondom.update(g,paint);

                popUpInstruction.update(g,paint);
                // gameChangerEffect.update(g,paint);

                innerThought_top_left.update(g,paint);
                innerThought_top_right.update(g,paint);
                innerThought_bottom_left.update(g,paint);
                innerThought_bottom_right.update(g,paint);
                break;

            case -4://New Scene added before going to next morning scenario
                if(isDelay){
                    delayCount();

                }
                if(delayCounter==5){
                    delayCounter=0;

                    if(nxtSceneStatus){
                        repeatCode();
                    }

                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_nxt_morning_bg);
                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());

                    Bitmap tempMale = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_mor_thinking_boy);
                    maleChar.setImage(tempMale, tempMale.getWidth(), tempMale.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
                    maleChar.setVisible(true);
                    popUpInstruction.setVisible(true);

                    subSceneNumber=4;
                }
                condom_broke_bg.paint(g,null);
                kissingChar_nxtmorning.paint(g,null);

                break;

            case 4://Next Morning Scenario

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==4 && !faded_bgSprite.isVisible()){
                    bgSprite.setPosition(-(bgSprite.getWidth()-width),bgSprite.getY());
                    maleChar.setPosition(-30,maleChar.getY());

                    beforeNxtMorSceneRecycle();

                    condom_broke_bg.recycle();
                    dpGuy_consume_drink_douche_clinic.setPosition(maleChar.getX()+maleChar.getWidth(),dpGuy_consume_drink_douche_clinic.getY());
                    dpGuy_consume_drink_douche_clinic.messPosX=dpGuy_consume_drink_douche_clinic.bgImage.getX()+dpGuy_consume_drink_douche_clinic.bgImage.getWidth()/2-(int)paint.measureText(dpGuy_consume_drink_douche_clinic.messString)/2;
                    dpGuy_consume_drink_douche_clinic.setVisible(true);
                    faded_bgSprite.setVisible(true);
                    popUpInstruction.setVisible(false);
                    //delayCounter=0;
                }
                if(delayCounter==6 && !twoOptionMessBottom.isVisible()){
                    twoOptionMessBottom.setVisible(true);

                    delayCounter=0;
                    isDelay=false;
                }


                faded_bgSprite.paint(g,null);
                //backgroundMessage.update(g,paint);

                maleChar.paint(g,null);
                popUpInstruction.update(g,paint);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                twoOptionMessBottom.update(g,paint);


                break;
            case 5: //helper yes button

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                //popUpInstruction.update(g,paint);
                helper.update(g,paint);
                break;

            case -5:
                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dpGuy_consume_drink_douche_clinic.update(g,paint);
                break;

            case 6://helper no button
                if(isDelay){
                    delayCount();
                }

                if(delayCounter==10 && isTxtBrk){
                    backgroundMessage.setMessString("...more than two weeks. You need to consult with university clinic counselors and health providers so that you can make the best decisions. Your health depends upon it!!");
                    backgroundMessage.actionButton.setVisible(true);
                    isDelay=false;
                    delayCounter=0;
                    isTxtBrk=false;
                }
                faded_bgSprite.paint(g,null);
                backgroundMessage.update(g,paint);
                break;


            case 100:
                //if(common_Scene) {
                sceneNumber = 4;
                subSceneNumber = 0;
//                    this.drawSomething(g);
                // }
                break;
        }
    }
    public void scene3(Canvas g){

    }
    public void scene4(Canvas g){
        myCanvas=g;
        bgSprite.paint(g,null);

        switch (subSceneNumber) {
            case 0:
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==4){
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boywrongmsg);
                    maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                    maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                    innerThought_top_left.setMessString("Ooh s*** what did I do?");
                    innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                    innerThought_top_right.setMessString("How do I get out of this?");
                    innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                            maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                    innerThought_bottom_left.setMessString("Does anyone else know this happened?");
                    innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                            maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                    innerThought_bottom_right.setMessString("Is my BFF really the one for me?");
                    innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                            maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);

                    innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                    innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                    innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
                    innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);
                    delayCounter=0;
                    subSceneNumber=1;
                }

//                faded_bgSprite.paint(g,null);
//                backgroundMessage.update(g,paint);
                joseph_thinking_expression.paint(g,null);
                popUpInstruction.update(g,paint);

                break;

            case 1:

                paint.setColor(Color.BLACK);
                if(isDelay){
                    delayCount();
                }
                if ((delayCounter == 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible())) {
                    joseph_thinking_expression.recycle();
                    innerThought_top_left.setVisible(true);
                    delayCounter = 0;
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
                    dp_tellGF_trytoseeboth = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.g_tell_bf_gf, R.drawable.g_try_to_see_both});
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
                    twoOptionMessBottom.setVisible(false);
                    //popUpInstruction.setVisible(false);

                    delayCounter = 0;
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
                if(delayCounter==2 && !twoOptionMessBottom.isVisible()){
                    twoOptionMessBottom.setVisible(true);
                    delayCounter=0;
                    isDelay=false;
                }

                faded_bgSprite.paint(g,null);
                maleChar.paint(g,null);
                dp_tellGF_trytoseeboth.update(g,paint);
                twoOptionMessBottom.update(g,paint);
                //popUpInstruction.update(g,paint);
                helper.update(g,paint);
                break;

            case 3://Tell GF/BF
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.update(g, paint);
                }

                break;

            case 4:// Try to see both

                if(isDelay){

                    delayCount();
                }
                if(delayCounter==4 && roomatetellevrybodyChar.isVisible()){
                    maleChar.setVisible(true);
                    roomatetellevrybodyChar.setVisible(false);
                    popUpInstruction.setVisible(false);

                    gameChangerEffect.setVisible(false);//Game Changer Effect
                    gameChangerEffect.setCount(0);

                    paint.setColor(Color.BLACK);
                    delayCounter=0;
                }
                if ((delayCounter == 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() )&& !roomatetellevrybodyChar.isVisible()) {

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
                }

                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    //gameChangerMessage.update(g, paint);
                    roomatetellevrybodyChar.paint(g,null);
                    popUpInstruction.update(g,paint);

                    innerThought_top_left.update(g, paint);
                    innerThought_top_right.update(g, paint);
                    innerThought_bottom_left.update(g, paint);
                    faded_bgSprite.paint(g, null);
                    backgroundMessage.update(g, paint);
                }

                break;
//



        }
    }
    private void beforeNxtMorScene(){
        condom_broke_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_bedroom));
        condom_broke_bg.setVisible(true);

        kissingChar_nxtmorning = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.lead_kissing));
        kissingChar_nxtmorning.setPosition(width / 2 - kissingChar_nxtmorning.getWidth() / 2, height / 2 - kissingChar_nxtmorning.getHeight() / 2);
        kissingChar_nxtmorning.setVisible(true);

        isDelay=true;
        delayCounter=0;
    }
    private void beforeNxtMorSceneRecycle(){
        condom_broke_bg.recycle();
        kissingChar_nxtmorning.recycle();
    }
    private void repeatCode(){

        Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_nxt_morning_bg);
        bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());


        Bitmap tempMale = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_mor_thinking_boy);
        maleChar.setImage(tempMale, tempMale.getWidth(), tempMale.getHeight());
        maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
        maleChar.setVisible(true);
        // maleChar.setVisible(false);

//        backgroundMessage.setMessString("You feel pretty bad about last night’s sexual encounter with Joseph/Susan & worried about getting pregnant/catching an STI/Getting infected with HIV.");
//        backgroundMessage.setVisible(true);

        popUpInstruction.setMessString("You feel pretty bad about last night’s sexual encounter with Joseph & worried about the consequences.");
        popUpInstruction.setVisible(true);

        paint.setColor(Color.WHITE);
        faded_bgSprite.setVisible(false);


        helper = new Helper(new String[]{
                "I’m sure everything will be fine. Just buy Emergency Contraceptives from that guy who sells them.",
                "You should really go to the clinic. They can help you with Emergency contraceptives and make sure everything else is ok.",
                "You both need to come in to the clinic. I’ll be happy to set up an appointment for you guys either separately or together.",
                "First wash yourself with coca cola, and then get a pregnancy test."
        });
        helper.setVisible(false);
        dpGuy_consume_drink_douche_clinic = new DecisionPoint(R.drawable.g_decision_point_bg_smal, new int[]{R.drawable.consume, R.drawable.drink,R.drawable.take_pregnancy_test,R.drawable.douche, R.drawable.gotoclinic});
        dpGuy_consume_drink_douche_clinic.setVisible(false);

        isDelay=true;
        delayCounter=0;

    }

    private void getDrunk(){
        condomChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_friend_flipped));
        condomChar.setPosition(width/2-condomChar.getWidth()-30,height-condomChar.getHeight());
        maleChar.setVisible(false);

        backingOff_female_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_male));
        backingOff_female_Char.setPosition(width/2-backingOff_female_Char.getWidth(),height-backingOff_female_Char.getHeight());
        backingOff_female_Char.setVisible(false);

        condom_couple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_couple));
        condom_couple.setPosition(width/2-condom_couple.getWidth()/2,height-condom_couple.getHeight());

        condom_char_moving = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_char_move));
        condom_char_moving.setPosition(-condom_char_moving.getWidth(),height-condom_char_moving.getHeight());
        condom_char_moving.setVisible(false);

        condom_couple_zoom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_couple_zooom));
        condom_couple_zoom.setPosition(width-condom_couple_zoom.getWidth(),height-condom_couple_zoom.getHeight());
        condom_couple_zoom.setVisible(false);


//        condomChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_friend_flipped));
//        condomChar.setPosition(0,height-condomChar.getHeight());
//
//        maleChar.setVisible(false);
//
//        backingOff_female_Char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.conversation_female));
//        backingOff_female_Char.setPosition(width/2-backingOff_female_Char.getWidth(),height-backingOff_female_Char.getHeight());
//        backingOff_female_Char.setVisible(false);
//
//        condom_couple = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_couple));
//        condom_couple.setPosition(width/2-condom_couple.getWidth()/2,height-condom_couple.getHeight());
//
//        condom_char_moving = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_char_move));
//        condom_char_moving.setPosition(-condom_char_moving.getWidth(),height-condom_char_moving.getHeight());
//        condom_char_moving.setVisible(false);
//
//        condom_couple_zoom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_condom_couple_zooom));
//        condom_couple_zoom.setPosition(0,height-condom_couple_zoom.getHeight());
//        condom_couple_zoom.setVisible(false);

        popUpInstruction.setVisible(true);
        subSceneNumber=0;
        delayCounter=0;
        isDelay=true;
        sceneNumber=2;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context,R.raw.tap_sound);
        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
        //getSceneNumber(touchrecF);

        switch (sceneNumber) {
            case 0:
                switch (subSceneNumber) {

                    case 3:
                        if (twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton1.getDstRect().intersect(touchrecF)) {
                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dp_roomInviteIn_down;
                            //timer.setVisible(true);
                            //*************************************

                            helper.setVisible(true);
                            subSceneNumber = 4;
                        }
                        if (twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton2.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            dp_roomInviteIn_down.setActive();
                            subSceneNumber =6;
                        }
                        break;
                    case 4:
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
                                    subSceneNumber=5;
                                    break;
                                }
                            }


                        }

                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

                        break;
                    case 5:
                        if (helper.isVisible() && helper.isUsed() && helper.actionButton.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            dp_roomInviteIn_down.setActive();
                            subSceneNumber = 6;
                            return true;
                        }
                        break;
                    case 6:

                        if(dp_roomInviteIn_down.isVisible() && dp_roomInviteIn_down.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D4",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Sound************
                            MySound.stopSound(context,GlobalVariables.Audio_File_Name);

                            //**************************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Return to room & invite in");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context, Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{4, 2, 1}));
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
                            temp.putExtra("femaleX", femaleChar.getX());
                            temp.putExtra("femaleY", femaleChar.getY());

                            GlobalVariables.GAME_A=1000;
                            ((Activity) context).setResult(GlobalVariables.GAME_A, temp);
                            ((Activity) context).finish();
                            this.recycle();

                        }

                        if(dp_roomInviteIn_down.isVisible() && dp_roomInviteIn_down.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D4",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Let them down easy");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{6, 5, 4}));
                            // ***********************************

                            dp_roomInviteIn_down.setVisible(false);
                            twoOptionMess = new PopUpMessage(R.drawable.background_mess, "Do you want to let them down easy?", R.drawable.yesbtn, R.drawable.nobtn);
                            twoOptionMess.setMessPosY(twoOptionMess.messPosY + 50);
                            isDelay=false;
                            backgroundMessage.setVisible(false);
                            subSceneNumber=7;
                        }
                        break;
                    case 7:
                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton1.getDstRect().intersect(touchrecF)) {

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7, 6, 5}));
                            // ***********************************

                            twoOptionMess.setVisible(false);
                            backgroundMessage.setMessString("Congratulations! \b\n" +
                                    "That was a well thought out decision. You did not succumb to the peer pressure &you were able to keep the relationship intact with your love as well as your BFF.");
                            backgroundMessage.setVisible(true);
                            isDelay=true;
                            //subSceneNumber=7;
                        }

                        if(twoOptionMess.isVisible() && twoOptionMess.actionButton2.getDstRect().intersect(touchrecF)) {//Go to getDrunk
                            backgroundMessage.setVisible(false);
                            isDelay=false;
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{4, 3, 2}));
                            // ***********************************
                            //System.out.println("DelayCounter Value = "+delayCounter +" isDelay= "+isDelay +" Subscene ="+subSceneNumber);
                            getDrunk();

                        }
                        if(backgroundMessage.isVisible()&& backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

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
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        if(dp_acceptApologize_pressure.isVisible()&& twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton1.getDstRect().intersect(touchrecF)){

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************

                            twoOptionMessBottom.setVisible(false);
                            helper.setVisible(true);
                            // popUpInstruction.setVisible(true);
                            isDelay=false;

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dp_acceptApologize_pressure;
                            //*************************************
                            return true;
                        }
                        if(dp_acceptApologize_pressure.isVisible()&& twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton2.getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            twoOptionMessBottom.setVisible(false);
                            dp_acceptApologize_pressure.setActive();
                            return  true;
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

                            dp_acceptApologize_pressure.setActive();
                            helper.setVisible(false);
                            // return true;
                        }

                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                            //popUpInstruction.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************



                        if(dp_acceptApologize_pressure.isVisible() && dp_acceptApologize_pressure.getBtnAtIndext(0).getDstRect().intersect(touchrecF) && !countDownStatus && !helper.isVisible()){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D5",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Accept, apologize & exit");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{8,7, 7}));
                            // ***********************************

                            subSceneNumber=5;
                        }
                        if(dp_acceptApologize_pressure.isVisible() && dp_acceptApologize_pressure.getBtnAtIndext(1).getDstRect().intersect(touchrecF) && !countDownStatus && !helper.isVisible()){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D5",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************


                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Put on pressure");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************


                            backgroundMessage.setVisible(false);
                            subSceneNumber=6;
                        }

                        break;
                    case 5:
                        if(backgroundMessage.isVisible()&& backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){

                            //************ Calculate Game EndTime**************
                            MyPreference.saveStringKeyValue(context,"endTime", MyUtility.getTime());
                            //*************************************************

                            //********* Saving Scores in Database***************
                            Utility.saveDataInDB(context);
                            //**************************************************

                            //*************** cleared Prefrences *************
                            // new File("/data/data/zmq.com.jhpiego/shared_prefs/temp_data.xml").delete();
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
                    case 6:
                        if(gameChangerMessage_For_ForcelyHelp.isVisible() && gameChangerMessage_For_ForcelyHelp.actionButton.getDstRect().intersect(touchrecF)){
                            gameChangerMessage_For_ForcelyHelp.setVisible(false);
                            backgroundMessage.setVisible(true);
                            backgroundMessage.setMessString("This is a bad choice. Intimate contact always needs to be consensual. This means that both people involved should be fully aware of what they are doing, and fully in agreement. By forcing someone who has clearly said no to have sex you are not just ruining a relationship—you are committing a crime.");

                        }
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            //backgroundMessage.setMessString("You feel pretty bad about last night’s sexual encounter with Susan & worried about the consequences.");

                            popUpInstruction.setMessString("You feel pretty bad about last night’s sexual encounter with Joseph & worried about the consequences.");
                            popUpInstruction.setVisible(true);

                            helper = new Helper(new String[]{
                                    "I’m sure everything will be fine. Just buy Emergency Contraceptives from that guy who sells them.",
                                    "You should really go to the clinic. They can help you with Emergency contraceptives and make sure everything else is ok.",
                                    "You both need to come in to the clinic. I’ll be happy to set up an appointment for you guys either separately or together.",
                                    "First wash yourself with coca cola, and then get a pregnancy test."
                            });
                            helper.setVisible(false);
                            dpGuy_consume_drink_douche_clinic = new DecisionPoint(R.drawable.g_decision_point_bg_smal, new int[]{R.drawable.consume, R.drawable.drink,R.drawable.take_pregnancy_test,R.drawable.douche, R.drawable.gotoclinic});
                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            twoOptionMessBottom.setVisible(false);

                            //bgSprite.recycle();
                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_nxt_morning_bg);
                            bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());

                            //maleChar.recycle();
                            Bitmap tempMale = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_mor_thinking_boy);
                            maleChar.setImage(tempMale, tempMale.getWidth(), tempMale.getHeight());
                            maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
                            bgSprite.setPosition(-(bgSprite.getWidth()-width),bgSprite.getY());

                            beforeNxtMorScene();

                            faded_bgSprite.setVisible(false);
                            isDelay=true;
                            delayCounter=0;
                            subSceneNumber=-7;
                        }
                        break;
                    case 7:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            backgroundMessage.setVisible(false);

                            isDelay=true;
                            delayCounter=0;
                        }
                        if(twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton1.getDstRect().intersect(touchrecF)) {
                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dpGuy_consume_drink_douche_clinic;
                            //*************************************

                            // popUpInstruction.setVisible(true);
                            twoOptionMessBottom.setVisible(false);
                            helper.setVisible(true);

                        }

                        if(twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton2.getDstRect().intersect(touchrecF)) {

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setActive();
                            twoOptionMessBottom.setVisible(false);
                            onClickStatus=true;
                            subSceneNumber=8;
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

                            dpGuy_consume_drink_douche_clinic.setActive();
                            helper.setVisible(false);
                            onClickStatus=true;
                            subSceneNumber=8;

                        }

                        break;

                    case 8:
                        // Decision Point Buttons Click Event
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(0).getDstRect().intersect(touchrecF)  && onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            onClickStatus=false;
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Consume emergency contraceptive");
                            // ***********************************

                            backgroundMessage.setMessString("Emergency Contraception is a good way to prevent an unintended pregnancy, but it is best taken as soon as possible (within 72 hours)after intercourse. It is also important to visit the university clinic for STI evaluation and pregnancy test. Also you should consider a long-acting contraceptive as future protection against a pregnancy until you are really ready to have a baby.");
                            backgroundMessage.setVisible(true);
                            common_Scene=true;
                            subSceneNumber=100;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(1).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            onClickStatus=false;
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Drink lots of hot tea.");
                            // ***********************************


                            backgroundMessage.setMessString("Drinking hot tea will not prevent pregnancy or an STI. Any time you have a sexual encounter that is unprotected with a condom or contraceptive method you are at risk of pregnancy and/or STI infection. The best thing to do is go to the clinic.");
                            backgroundMessage.setVisible(true);
                            common_Scene=true;
                            subSceneNumber=100;

                        }
                        // Take a pregnancy test click event

                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(2).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            onClickStatus=false;
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Take a pregnancy test");
                            // ***********************************

                            backgroundMessage.setMessString("A pregnancy test on the next day is a great decision. If the pregnancy test is positive, you need to go to the university clinic ASAP! If it is negative, you STILL need to go to the clinic and get Emergency Contraception, STI evaluation and please discuss options for long-acting contraceptives to protect you against unintended pregnancy. A positive pregnancy test on the next day means you were pregnant for..");
                            backgroundMessage.actionButton.setVisible(false);
                            backgroundMessage.setVisible(true);

                            common_Scene=true;
                            isTxtBrk=true;
                            isDelay=true;
                            delayCounter=0;
                            subSceneNumber=100;

                        }

                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(3).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            onClickStatus=false;
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Douche");
                            // ***********************************


                            backgroundMessage.setMessString("Washing out the penis or vagina with water, medicine, or any other substance will not prevent pregnancy or an STI. In fact, washing the vagina (douching) may actually increase your chances of an STI or pregnancy! Any time you have a sexual encounter that is unprotected with a condom or Future Protection method you are at risk. The best thing to do is go to the university clinic for STI and pregnancy evaluations.");
                            backgroundMessage.setVisible(true);
                            common_Scene=true;
                            subSceneNumber=100;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(4).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            onClickStatus=false;
                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{10,9,9}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Go to the clinic ");
                            // ***********************************


                            backgroundMessage.setMessString("Excellent choice! At the clinic you can get emergency contraceptives and good advice about STI/HIV testing and contraceptive methods that are safe and reliable.");
                            backgroundMessage.setVisible(true);
                            common_Scene=true;
                            subSceneNumber=100;

                        }
                        break;
                    case 100:// After Math
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

                    default:
                        break;
                }

                break;

            case 2://Drunk
                switch (subSceneNumber) {
                    case 0:
//                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
//                            faded_bgSprite.setVisible(false);
//                            backgroundMessage.setVisible(false);
//
//                            condomChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.condom_friend));
//                            condomChar.setPosition(width/2-condomChar.getWidth(),0);
//
//                           // maleChar.recycle();
//                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.get_drunk);
//                            maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
//                            maleChar.setPosition(width/2+maleChar.getWidth(),height-maleChar.getHeight());
//
//                            isDelay=true;
//                            subSceneNumber=1;
//                        }

                        break;
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        if(dp_dontCondom_takeCondom.isVisible() && dp_dontCondom_takeCondom.getBtnAtIndext(0).getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D7",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            beforeNxtMorScene();
                            subSceneNumber=-4;
//                            isDelay=true;
//                            delayCounter=0;
                            nxtSceneStatus=true;


                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{2,3,1}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Don’t take condom n have sex");
                            // ***********************************

//                            repeatCode();
//                            isDelay=true;
//                            delayCounter=0;
//
//                            beforeNxtMorScene();
//                            nxtSceneStatus=true;
//                            subSceneNumber=-4;
                        }
                        if(dp_dontCondom_takeCondom.isVisible() && dp_dontCondom_takeCondom.getBtnAtIndext(1).getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D7",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Take condom n have sex");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,6, 3}));
                            // ***********************************

                            dp_dontCondom_takeCondom.setVisible(false);
//                            gameChangerMessage.setMessString("Oops! Condom broke");
//                            gameChangerMessage.setVisible(true);
                            popUpInstruction.setMessString("Oops! Condom broke");
                            popUpInstruction.setVisible(true);
                            gameChangerEffect.setVisible(false);
                            maleChar.setVisible(false);
                            condom_broke_bg.setVisible(true);

                            Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_boy_looking_down);
                            maleChar.setImage(temp, temp.getWidth(), temp.getHeight());
                            maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());
                            maleChar.setVisible(true);

                            isDelay=true;

                        }

                        break;
                    case 4:
//                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
//                            faded_bgSprite.setVisible(false);
//                            maleChar.setVisible(true);
//                            backgroundMessage.setVisible(false);
//                            twoOptionMessBottom.setVisible(false);
//                            isDelay=true;
//                            delayCounter=0;
//
//                        }


                        if(twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton1.getDstRect().intersect(touchrecF)){
                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5, 3}));
                            // ***********************************


                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dpGuy_consume_drink_douche_clinic;
                            //*************************************

                            twoOptionMessBottom.setVisible(false);
                            helper.setVisible(true);
                            // popUpInstruction.setVisible(true);
                            subSceneNumber=5;
                        }
                        if(twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton2.getDstRect().intersect(touchrecF)){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            twoOptionMessBottom.setVisible(false);
                            dpGuy_consume_drink_douche_clinic.setActive();
                            //popUpInstruction.setVisible(false);
                            subSceneNumber=-5;
                            onClickStatus=true;

                        }
                        break;
                    case 5:
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
                            subSceneNumber=-5;

                        }

                        break;

                    case -5:
                        // Decision Point Buttons Click Event
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(0).getDstRect().intersect(touchrecF) && onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Consume emergency contraceptive");
                            // ***********************************


                            backgroundMessage.setMessString("Emergency Contraception is a good way to prevent an unintended pregnancy, but it is best taken as soon as possible (within 72 hours)after intercourse. It is also important to visit the university clinic for STI evaluation and pregnancy test. Also you should consider a long-acting contraceptive as future protection against a pregnancy until you are really ready to have a baby. ");
                            backgroundMessage.setVisible(true);

                            dpGuy_consume_drink_douche_clinic.setVisible(false);
                            subSceneNumber=6;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(1).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Drink lots of hot tea.");
                            // ***********************************


                            backgroundMessage.setMessString("Drinking hot tea will not prevent pregnancy or an STI. Any time you have a sexual encounter that is unprotected with a condom or contraceptive method you are at risk of pregnancy and/or STI infection. The best thing to do is go to the clinic.");
                            backgroundMessage.setVisible(true);

                            dpGuy_consume_drink_douche_clinic.setVisible(false);
                            subSceneNumber=6;

                        }
                        // Take a pregnancy test click event

                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(2).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            dpGuy_consume_drink_douche_clinic.setVisible(false);

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Take a pregnancy test");
                            // ***********************************

                            backgroundMessage.setMessString("A pregnancy test on the next day is a great decision. If the pregnancy test is positive, you need to go to the university clinic ASAP! If it is negative, you STILL need to go to the clinic and get Emergency Contraception, STI evaluation and please discuss options for long-acting contraceptives to protect you against unintended pregnancy. A positive pregnancy test on the next day means you were pregnant for...");
                            backgroundMessage.actionButton.setVisible(false);
                            backgroundMessage.setVisible(true);
                            isTxtBrk=true;
                            isDelay=true;
                            delayCounter=0;

                            subSceneNumber=6;

                        }

                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(3).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0,0}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Douche");
                            // ***********************************



                            backgroundMessage.setMessString("Washing out the penis or vagina with water, medicine, or any other substance will not prevent pregnancy or an STI. In fact, washing the vagina (douching) may actually increase your chances of an STI or pregnancy! Any time you have a sexual encounter that is unprotected with a condom or Future Protection method you are at risk. The best thing to do is go to the university clinic for STI and pregnancy evaluations.");
                            backgroundMessage.setVisible(true);

                            dpGuy_consume_drink_douche_clinic.setVisible(false);
                            subSceneNumber=6;

                        }
                        if(dpGuy_consume_drink_douche_clinic.isVisible() && dpGuy_consume_drink_douche_clinic.getBtnAtIndext(4).getDstRect().intersect(touchrecF)&& onClickStatus){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D6",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{10,9,9}));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Go to the clinic");
                            // ***********************************

                            backgroundMessage.setMessString("Excellent choice! At the clinic you can get emergency contraceptives and good advice about STI/HIV testing and contraceptive methods that are safe and reliable.");
                            backgroundMessage.setVisible(true);

                            dpGuy_consume_drink_douche_clinic.setVisible(false);
                            subSceneNumber=6;

                        }
                        break;

                    case 6:
                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            joseph_thinking_expression = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_joseph_thinking_expression));
                            popUpInstruction.setMessString("Now that you have taken the precautions, you can’t stop thinking about last night…");
                            popUpInstruction.setVisible(true);
                            backgroundMessage.setVisible(false);

                            common_Scene=true;
                            isDelay=true;
                            subSceneNumber=100;
                        }
                        break;


                    default:
                        break;
                }
                break;

            case 3://Invite In
                break;

            case 4:// For Commone scenes of dpGuy_consume_drink_douche_clinic
                switch (subSceneNumber) {
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        if(twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton1.getDstRect().intersect(touchrecF)){
                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{7,5,3}));
                            // ***********************************

                            //***********Helper Count Down**********
                            countDownStatus=true;
                            if(whichDecisionPoint!=null){
                                whichDecisionPoint.recycle();
                            }
                            countDown=GlobalVariables.timerValue;
                            whichDecisionPoint=dp_tellGF_trytoseeboth;
                            //*************************************


                            twoOptionMessBottom.setVisible(false);
                            //popUpInstruction.setVisible(true);
                            helper.setVisible(true);
                        }
                        if(twoOptionMessBottom.isVisible() && twoOptionMessBottom.actionButton2.getDstRect().intersect(touchrecF)){
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{0,0, 0}));
                            // ***********************************

                            twoOptionMessBottom.setVisible(false);
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

                        //**********On Timer Click Option********
//                        if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//                            timer.setVisible(false);
//                            countDownStatus=false;
//                            countDown=GlobalVariables.timerValue;
//                        }
                        //***************************************

                        if (helper.isVisible() && helper.isUsed() && helper.actionButton.getDstRect().intersect(touchrecF)) {
                            //***************** Calculating Decision Time ******************
                            GlobalVariables.start_Time=System.currentTimeMillis();
                            // ***********************************

                            dp_tellGF_trytoseeboth.setActive();
                            helper.setVisible(false);

                        }
                        if(!helper.isVisible() && dp_tellGF_trytoseeboth.isVisible() && delayCounter==0 && dp_tellGF_trytoseeboth.getBtnAtIndext(0).getDstRect().intersect(touchrecF)&& !countDownStatus){

                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D8",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Tell GF_BF");
                            // ***********************************

                            //*********Update Score.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{9,6, 5}));
                            // ***********************************


                            dp_tellGF_trytoseeboth.setVisible(false);
                            backgroundMessage.setMessString("Your BF has broken up with you, but at least you were honest and you are still friends with Joseph. Who knows… the best loves often start as good friends");
                            backgroundMessage.setVisible(true);
                            faded_bgSprite.setVisible(true);
                            subSceneNumber=3;
                        }
                        if(!helper.isVisible() && dp_tellGF_trytoseeboth.isVisible() && delayCounter==0 && dp_tellGF_trytoseeboth.getBtnAtIndext(1).getDstRect().intersect(touchrecF) && !countDownStatus){


                            //***************** Calculating Decision Time ******************
                            GlobalVariables.end_Time=System.currentTimeMillis();
                            // ***********************************

                            //***************** Send time taken on decision ******************
                            UpdateScore.setDecisionTime(context,"D8",MyUtility.timeDifference(GlobalVariables.end_Time,GlobalVariables.start_Time));
                            // ***********************************\

                            //*********Analytic Started.............
                            UpdateScore.setModulePath(context,"Try to see both of them until you figure out what you really want.");
                            // ***********************************

                            //*********Analytic started.............
                            UpdateScore.score(context,Utility.getMap(new String[]{"risk", "impulse", "relation"}, new int[]{3,2,1}));
                            // ***********************************


                            dp_tellGF_trytoseeboth.setVisible(false);
//                            gameChangerMessage.setMessString("Roomie tells everybody about him juggling both her GF & BFF");
//                            gameChangerMessage.setVisible(true);

                            popUpInstruction.setMessString("Roomie tells everybody about her juggling both her GF & BFF");
                            popUpInstruction.setVisible(true);

                            backgroundMessage.setMessString("You lose both- your BF as well as Joseph");
                            backgroundMessage.setVisible(false);

                            faded_bgSprite.setVisible(false);
                            maleChar.setPosition(width/2-maleChar.getWidth()/2,height-maleChar.getHeight());

                            innerThought_top_left.setMessString("What? I thought you were loyal");
                            innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);

                            innerThought_top_right.setMessString("I knew he was jealous");
                            innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                                    maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);

                            innerThought_bottom_left.setMessString("You must be interested in my gf");
                            innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                                    maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);

                            innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
                            innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
                            innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);

                            roomatetellevrybodyChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.roomie_tells_everyone));
                            maleChar.setVisible(false);
                            popUpInstruction.setMessString("Roomie tells everybody about her juggling both her BF");
                            popUpInstruction.setVisible(true);
                            twoOptionMessBottom.setVisible(false);
                            isDelay=true;
                            gameChangerEffect.setVisible(true);
                            paint.setColor(Color.WHITE);
                            subSceneNumber=4;
                        }

                        break;
                    case 3://After Math
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

                    case 4://After Math

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
        gameChangerEffect=new GameChangerEffect();
        gameChangerEffect.setVisible(false);

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_inside_bg));
        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        faded_bgSprite.setVisible(false);

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.advance_couple));
        int tempMaleX = intent.getIntExtra("maleX", width / 2 - maleChar.getWidth() / 2);
        int tempMaleY = intent.getIntExtra("maleY", height - maleChar.getHeight());

        maleChar.setPosition(tempMaleX, tempMaleY);

        dp_roomInviteIn_down = new DecisionPoint(R.drawable.decision_point_bg_smal, new int[]{R.drawable.returnroom_n_invitein, R.drawable.let_them_down});
        dp_roomInviteIn_down.setVisible(false);
//        toMoveX=dp_home_club_inviteIn.getX()-maleChar.getWidth();
//        isMoveMaleChar=true;

        int tempFemaleX = intent.getIntExtra("femaleX", 0);
        int tempFemaleY = intent.getIntExtra("femaleY", 0);
        femaleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.female_char));
//        femaleChar.setPosition(3*width/4,height/8);
        femaleChar.setPosition(tempFemaleX, tempFemaleY);
        dancingChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_dancing_couple));
        dancingChar.setPosition(width/2-dancingChar.getWidth()/2,0);
        dancingChar.setVisible(false);

        message = new Message(R.drawable.background_mess, "What will you do now?");
//        message.setMessPosX(message.mess_bgImage.getX()+message.mess_bgImage.getWidth()/2);
        message.setMessPosY(message.messPosY + 50);
        message.setVisible(false);

        twoOptionMessBottom = new PopUpMessageBottom(R.drawable.popupbg, "Would you like to take some help?", R.drawable.yesbtn, R.drawable.nobtn);
        twoOptionMessBottom.setPosition(0,height-twoOptionMessBottom.mess_bgImage.getHeight());
        twoOptionMessBottom.setMessPosY(twoOptionMessBottom.messPosY + twoOptionMessBottom.mess_bgImage.getHeight()/2);
        twoOptionMessBottom.setVisible(false);

        popUpInstruction = new PopUpInstruction(R.drawable.popupbg,
                "Click on any one of the helper to seek their advice"
        /*GlobalVariables.getResource.getString(R.string.scene0_popup)*/);
        popUpInstruction.setVisible(false);

        String temMyth[] = {"If you let your BFF stay in your room, it means you want to have sex.",
                "If your BFF stays in the room and you continue1 kissing, you will lose control of the situation, and you will have sex.",
                "When you kick your BFF out of the room, this means you are a virgin."};


//        gameChangerMessage = new GameChangerMessage(R.drawable.gamechanger_message_bg, "Your BFF is advancing on  you again");
//        gameChangerMessage.setVisible(false);

        innerThought_top_left = new InnerThought(R.drawable.inner_thought_topleft_new, "I liked your kissing maybe this was meant to be");
        innerThought_top_left.setVisible(false);
        innerThought_top_right = new InnerThought(R.drawable.inner_thought_topright_new, "It feels so good to be wanted");
        innerThought_top_right.setVisible(false);
        innerThought_bottom_left = new InnerThought(R.drawable.inner_thought_bottomleft_new, "Are you taking advantage of me because I’m upset?");
        innerThought_bottom_left.setVisible(false);
        innerThought_bottom_right = new InnerThought(R.drawable.inner_thought_bottomright_new, "I will show my true love who plays this game better!");
        innerThought_bottom_right.setVisible(false);

        backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"You decide to deal with your BF later & enjoy your night by dancing & having fun but then you realize…");

        showClubScene();
        timer=new Timer();
        isDelay=false;
        getSceneNumber();
        gameChangerEffect=new GameChangerEffect();

        myConstant.backGround.recycle();
        myConstant.waitingStar.recycle();
    }

    @Override
    public void recycle() {
        drawSomething(myCanvas);

        bgSprite.recycle();

        faded_bgSprite.recycle();
        maleChar.recycle();
        femaleChar.recycle();
        dancingChar.recycle();


        if(dp_roomInviteIn_down!=null)dp_roomInviteIn_down.recycle();
        if(dpGuy_consume_drink_douche_clinic!=null)dpGuy_consume_drink_douche_clinic.recycle();
        if(dp_acceptApologize_pressure!=null)dp_acceptApologize_pressure.recycle();
        if(dp_tellGF_trytoseeboth!=null)dp_tellGF_trytoseeboth.recycle();
        if(twoOptionMess!=null)twoOptionMess.recycle();
        if(roomatetellevrybodyChar!=null)roomatetellevrybodyChar.recycle();
        message.recycle();
        twoOptionMessBottom.recycle();
        //popUpInstruction.recycle();
        //gameChangerMessage.recycle();
        backgroundMessage.recycle();
        timer.recycle();

        innerThought_top_left.recycle();
        innerThought_top_right.recycle();
        innerThought_bottom_left.recycle();
        innerThought_bottom_right.recycle();

        if(helper!=null) helper.recycle();

        if(dancingChar!=null)dancingChar.recycle();
        if(condomChar!=null)condomChar.recycle();


        dancing_Couple01.recycle();
        dancing_Couple01_copy1.recycle();
        dancing_Couple02.recycle();
        disco_light01.recycle();
        disco_light02.recycle();
        gameChangerEffect.recycle();
    }

}
