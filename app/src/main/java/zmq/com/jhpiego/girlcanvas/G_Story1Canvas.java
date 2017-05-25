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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.HashMap;
import java.util.Random;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.canvas.BaseSurface;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.BackgroundMessage;
import zmq.com.jhpiego.messages.InnerThought;
import zmq.com.jhpiego.messages.Message;
import zmq.com.jhpiego.messages.PopUpInstruction;
import zmq.com.jhpiego.other.GameChangerEffect;
import zmq.com.jhpiego.preferences.UpdateScore;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.Constant;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.MySound;
import zmq.com.jhpiego.utility.Utility;


public class G_Story1Canvas extends BaseSurface implements Runnable, Recycler {



    private BackgroundMessage backGroundMess;
    private PopUpInstruction popUpInstruction;
    private int sceneNumber=-2;
    private int subSceneNumber=0;
    private ShahSprite bgSprite;
    private ShahSprite faded_bgSprite;
    private ShahSprite zoomed_bg;
    private ShahSprite bag, mattress, cusion /*book*/;// scene0 objects with sequence 0,1,2,3

    private ShahSprite bag_box,cusion_box,matress_box;
    private  int rand_phone_object;
    private ShahSprite /*phone*/ bigPhone;
    //private ShahSprite gameStart,player_Selection_Box_male,player_Selection_Box_female,play;
    private ShahSprite maleChar, femaleChar,girl_conversation_bg,wrong_msg_char;
    private ShahSprite scene1_boy;
    private ShahSprite door, door1,doorKissingChar;
    private ShahSprite knocksign01,knocksign02,beep01,beep02;
    private Message maleConversationMessage, femaleConversationMessage, message;
    private int maleMessCounter;
    private int dilogueDuration = 5;

    private InnerThought innerThought;
    private InnerThought innerThought_top_left, innerThought_top_right, innerThought_bottom_left, innerThought_bottom_right;
    private Intent intent;
    private String messageString="";
    private Constant myConstant;
    private Canvas myCanvas;
    private int fonSerchCounter=1;
    private boolean fon_serch_objectStatus[]={false,false,false};
    private Paint paint1;

    private GameChangerEffect gameChangerEffect;
//    private boolean isGameChanger=false;

    public G_Story1Canvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        paint1=new Paint();
        myConstant=new Constant(context);

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

            delayCounter = 0;
            isDelay = false;
            gameThread.running = false;
            gameThread = null;
            this.surfaceHolder.removeCallback(this);
            Intent temp = new Intent();

            GlobalVariables.initialize();
            ((Activity) context).setResult(666, temp);
            ((Activity) context).finish();
            this.recycle();
            System.out.println(" Story1Canvas surfaceDestroyed Called...");

    }

    private void drawText(Canvas canvas, String Text,int xMidPosition,int yMidPosition, int textSize) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(Text,xMidPosition,yMidPosition, paint);
    }

    protected void drawSomething(Canvas g) {

        if(g!=null)g.drawColor(Color.TRANSPARENT);
        switch (sceneNumber) {
            case -2:

                myConstant.drawImageWithRotation(g);

                loadImages();
                break;

            case 0:
                scene0(g);
                //drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber , 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 1:
                scene1(g);
                // drawText(g, getClass().getSimpleName() + " SCENE NO : " + sceneNumber , 5, (int) (GlobalVariables.yScale_factor * 20), 20);
                break;
            case 2:

                break;
            default:
                break;
        }
        if(gameChangerEffect!=null){
            if(gameChangerEffect.isVisible()){
                gameChangerEffect.update(g,paint1);
            }
        }



    }

//    public void scene4(Canvas g){
//        MyPreference.saveStringKeyValue(context,"gender","boy");
//        gameStart.paint(g,null);
//        player_Selection_Box_male.paint(g,null);
//        player_Selection_Box_female.paint(g,null);
//        play.paint(g,null);
//    }
    public void scene0(Canvas g) {
//        g.drawColor(Color.TRANSPARENT);
        myCanvas=g;
        bgSprite.paint(g, null);

//        bag.paint(g, null);
//        mattress.paint(g, null);
//        cusion.paint(g, null);
//        book.paint(g, null);
        switch (subSceneNumber) {
            case 0:

//                if (isDelay) {
//                    delayCount();
//                }
//                if (delayCounter == 3 && isDelay) {
//                    messDisplay = true;
//                    isDelay = false;
//                    delayCounter = 0;
//                }
                scene1_boy.paint(g,null);
                if (messDisplay) {
                    //display message
                    faded_bgSprite.paint(g, null);
                    backGroundMess.update(g, paint);
                }
                break;
            case 1:
                if(isDelay){
                    delayCount();
                }

                if(delayCounter==3 && !popMessDisplay){

                    bgSprite.recycle();
                    ShahSprite tempBG= new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_scene1bg02));
                    bgSprite=tempBG;
                    popMessDisplay=true;

                    isDelay=false;
                }
                if(!popMessDisplay){
                    scene1_boy.paint(g,null);
                }
                if (popMessDisplay) {
                    popUpInstruction.update(g, paint);
                }
//                cusion_box.paint(g,null);
//                matress_box.paint(g,null);
//                bag_box.paint(g,null);
                break;
            case 2:

                break;
            case 3://cusion
                if (isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    isDelay=false;
                    delayCounter = 0;

                    popUpInstruction.setVisible(false);

                    subSceneNumber=2;
                }
                zoomed_bg.paint(g,null);
                cusion.paint(g, null);
                popUpInstruction.update(g,paint);

                break;
            case 4://matress
                if (isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    isDelay=false;
                    delayCounter = 0;
                    popUpInstruction.setVisible(false);
//                    popUpInstruction.setVisible(true);
//                    popUpInstruction.setMessString("Tab the image to search phone2");
                    subSceneNumber=2;
                }
                zoomed_bg.paint(g,null);
                mattress.paint(g,null);

                popUpInstruction.update(g,paint);
                break;
            case 5://bag
                if (isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    isDelay=false;
                    delayCounter = 0;
                    popUpInstruction.setVisible(false);
//                    popUpInstruction.setVisible(true);
//                    popUpInstruction.setMessString("Tab the image to search phone3");
                    subSceneNumber=2;
                }
                zoomed_bg.paint(g,null);
                bag.paint(g,null);

                popUpInstruction.update(g,paint);
//                phone.paint(g, null);
                break;
            case 6:
                faded_bgSprite.paint(g,null);

                popUpInstruction.update(g, paint);
                bigPhone.paint(g, null);
                break;
            case 7:
                maleChar.paint(g, null);
                girl_conversation_bg.paint(g,null);
                femaleChar.paint(g, null);

                if (isDelay) {
                    delayCount();
                }
                if (delayCounter == 2 && !maleConversationMessage.isVisible() && !femaleConversationMessage.isVisible() && !femaleChar.isVisible()) {
                    girl_conversation_bg.setVisible(true);
                    femaleChar.setVisible(true);
                    delayCounter = 0;

                }

                if (delayCounter == 1 && !maleConversationMessage.isVisible() && !femaleConversationMessage.isVisible()&& femaleChar.isVisible()) {
                    maleConversationMessage.setVisible(true);
                    maleMessCounter = 1;
                    delayCounter = 0;

                    //isDelay=false;
                }

                if (delayCounter == dilogueDuration && maleConversationMessage.isVisible() && !femaleConversationMessage.isVisible() && maleMessCounter!=5) {

                    switch (maleMessCounter) {
                        case 1:
                            maleConversationMessage.setVisible(false);
                            femaleConversationMessage.setVisible(true);
                            maleMessCounter = 3;
                            break;
                        case 2:
//                            maleMessCounter = 0;
//                            //set inner thought position
//                            innerThought.setPosition(maleChar.getX() + 3*maleChar.getWidth()/4, maleChar.getY() +innerThought.mess_bgImage.getHeight() / 4);
//                            innerThought.setMessPosY(innerThought.messPosY+innerThought.mess_bgImage.getHeight()/4);
//                            subSceneNumber = 8;
//                            isDelay = true;
                            break;
                        case 3:

                            maleConversationMessage.setMessString("all day and all of the things I’d like to do to make you feel great.");
                            maleConversationMessage.setVisible(true);
                            maleMessCounter=5;
                            break;
                    }


                    delayCounter = 0;
                }

                if(delayCounter==dilogueDuration && maleMessCounter==5){
                    maleMessCounter = 0;
                    //set inner thought position
                    innerThought.setPosition(maleChar.getX() + 3*maleChar.getWidth()/4, maleChar.getY() +innerThought.mess_bgImage.getHeight() / 4);
                    innerThought.setMessPosY(innerThought.messPosY+innerThought.mess_bgImage.getHeight()/4);

                    delayCounter=0;
                    isDelay = true;
                    subSceneNumber = 8;
                }

                if (delayCounter == dilogueDuration && !maleConversationMessage.isVisible() && femaleConversationMessage.isVisible()) {
                    paint.setColor(Color.BLACK);
                    maleConversationMessage.setMessString("Hey sweetie. I sure wish I could see you tonight. I’ve been thinking about you...");
                    //day and all of the things I’d like to do to make you feel great.
                    maleConversationMessage.setVisible(true);
                    femaleConversationMessage.setVisible(false);
                    delayCounter = 0;
                }

                maleConversationMessage.update(g, paint);
                femaleConversationMessage.update(g, paint);
                break;
            case 8:


//                maleChar.recycle();
//                Bitmap temp1=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.boywrongmsg);
//                maleChar.setImage(temp1,temp1.getWidth(),temp1.getHeight());
//                maleChar.paint(g, null);

                if (isDelay) {
                    delayCount();
                }

                if(delayCounter==2  &&!innerThought.isVisible() && !wrong_msg_char.isVisible()){
                    wrong_msg_char.setVisible(true);
                    delayCounter=0;
                }
                if(delayCounter==2 && !innerThought.isVisible() && wrong_msg_char.isVisible() && !beep01.isVisible() && beep02.isVisible()){
                    delayCounter=0;
                    beep02.setVisible(false);
                    wrong_msg_char.setVisible(false);


                    //***************** Sound************

                    MySound.stopSound(context,R.raw.message_sound);
                    MySound.playSound(context,R.raw.a);
                    GlobalVariables.Audio_File_Name=R.raw.a;
                    //*****************************

                    maleChar.recycle();
                    Bitmap temp1=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.girl_standing_surprised);
                    maleChar.setImage(temp1,temp1.getWidth(),temp1.getHeight());
                    maleChar.paint(g, null);
                    innerThought.setVisible(true);

//                    gameChangerEffect.setVisible(true);
                    paint.setColor(Color.BLACK);

                }
                if(delayCounter==1 && !beep01.isVisible() && !beep02.isVisible() && !innerThought.isVisible() && wrong_msg_char.isVisible()){
                    beep01.setVisible(true);

                    //***************** Sound************
                    MySound.isLooping=false;

                    MySound.playSound(context,R.raw.message_sound);
                    GlobalVariables.Audio_File_Name=R.raw.a;

                    //*****************************

                    delayCounter=0;
                }
                if(delayCounter==1 && beep01.isVisible() && !beep02.isVisible() && !innerThought.isVisible() && wrong_msg_char.isVisible()){
                    beep01.setVisible(false);
                    beep02.setVisible(true);
                    delayCounter=0;
                    //isDelay=false;
                }

                if (delayCounter == 2 && innerThought.isVisible() && !gameChangerEffect.isVisible()) {
                    gameChangerEffect.setVisible(true);
                    delayCounter = 0;
//                    innerThought.setVisible(false);
//                    gameChangerEffect.setVisible(false);
//                    gameChangerEffect.setCount(0);
//
//                    isDelay = true;
//                    //paint.setColor(Color.WHITE);
//                    delayCounter = 0;
//                    subSceneNumber = 9;
                }

                if(delayCounter==2 && innerThought.isVisible() && gameChangerEffect.isVisible()){
                    innerThought.setVisible(false);
                    gameChangerEffect.setVisible(false);
                    gameChangerEffect.setCount(0);

                    isDelay = true;
                    //paint.setColor(Color.WHITE);
                    delayCounter = 0;
                    subSceneNumber = 9;
                }
                //gameChangerMessage.update(g, paint);
                maleChar.paint(g, null);
                wrong_msg_char.paint(g,null);
                beep01.paint(g,null);
                beep02.paint(g,null);
                innerThought.update(g, paint);

                break;
            case 9:
                paint.setColor(Color.WHITE);
                maleChar.paint(g, null);
                if (isDelay) {
                    delayCount();
                }
                //                if(delayCounter==1 && !faded_bgSprite.isVisible()){
//                    faded_bgSprite.setVisible(true);
//                }
//                if(delayCounter==1 && !message.isVisible()){
//                    message.setVisible(true);
//                }
                if (delayCounter == 1 && message.isVisible()) {
                    isDelay = true;
                    delayCounter = 0;

                    bgSprite.recycle();
                    Bitmap temp = Utility.decodeSampledBitmapFromResource(getResources(), R.drawable.g_knock_bg);
                    bgSprite.setImage(temp, temp.getWidth(), temp.getHeight());
                    popUpInstruction.setMessString("Suddenly you hear a knock at the door");
                    popUpInstruction.setVisible(false);
                    subSceneNumber = 0;
                    sceneNumber = 1;
                }

//                faded_bgSprite.paint(g, null);
//                message.update(g, paint);

                break;
        }

    }

    public void scene1(Canvas g) {
        myCanvas=g;
        bgSprite.paint(g, null);
        switch (subSceneNumber) {
            case 0:

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==1 && !knocksign01.isVisible() && !knocksign02.isVisible()){
                    scene_0_recyle();

                    //**********Sound**********************

                    MySound.playSound(context,R.raw.door_knock03);
                    //*****************************************

                    knocksign01.setVisible(true);
                    popUpInstruction.setVisible(true);
                    delayCounter=0;
                }
                if(delayCounter==1 && knocksign01.isVisible() && !knocksign02.isVisible()){
                    knocksign01.setVisible(false);
                    knocksign02.setVisible(true);
                    delayCounter=0;
                }
                if(delayCounter==1 && !knocksign01.isVisible() && knocksign02.isVisible()){
                    knocksign01.setVisible(false);
                    knocksign02.setVisible(false);

                    //**********Sound**********************
                    MySound.isLooping=true;
                    MySound.stopSound(context,R.raw.door_knock03);
                    //MySound.stopAndPlay(context,new int[]{R.raw.door_knock03,R.raw.a});
                    //*****************************************

                    isDelay=false;
                    delayCounter=0;
                    popUpInstruction.setMessString("Click on the door to open it.");
                }

                door.paint(g, null);
                door1.paint(g, null);
                knocksign01.paint(g,null);
                knocksign02.paint(g,null);
                popUpInstruction.update(g, paint);//click on the door to open
                break;
            case 1:
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==5 && !faded_bgSprite.isVisible()&& !popUpInstruction.isVisible()){
                    faded_bgSprite.setVisible(true);
                    // backGroundMess.setVisible(true);
                    //popUpInstruction.setVisible(true);
                    isDelay=true;
                    delayCounter=0;

                    maleChar.setVisible(false);
                    door1.setVisible(false);
                    femaleChar.setVisible(false);
                    innerThought.setVisible(false);

                    subSceneNumber=2;
                }
                door1.paint(g, null);

                femaleChar.paint(g, null);
                maleChar.paint(g, null);
                //faded_bgSprite.paint(g, null);
                //backGroundMess.update(g, paint);
                popUpInstruction.update(g,paint);
                break;
            case 2:
//                door1.paint(g, null);
                maleChar.paint(g, null);
                if (isDelay) {
                    delayCount();
                }
                if(delayCounter==2 && !popUpInstruction.isVisible() && !maleChar.isVisible()){
                    popUpInstruction.setVisible(true);

                    delayCounter=0;
                    //isDelay=false;
                }

                if(delayCounter==5 && isDelay && popUpInstruction.isVisible()){
                    doorKissingChar.setVisible(false);
                    popUpInstruction.setVisible(false);

                    //repeatedScene();
                    //isDelay=false;

                    maleChar.setVisible(true);
                    door1.setVisible(true);
                    femaleChar.setVisible(true);
                    delayCounter=0;

                    paint.setColor(Color.BLACK);
                    innerThought.setMessString("I have been waiting so long….");
                    innerThought.setPosition(width-innerThought.mess_bgImage.getWidth(), femaleChar.getY() + innerThought.mess_bgImage.getHeight()/2);
                    innerThought.setThoughtPositions(innerThought,2);

//                    innerThought.setMessTextPosition(innerThought.mess_bgImage.getX()+47,innerThought.mess_bgImage.getY()+10);

                    //isDelay=false;
                }
                if(delayCounter==1 && !innerThought.isVisible() && femaleChar.isVisible()){
                    innerThought.setVisible(true);

                    delayCounter=0;
                    //isDelay=false;
                }

                if(delayCounter==3 && innerThought.isVisible()){
                    innerThought.setVisible(false);
                    repeatedScene();
                    delayCounter=0;
                    subSceneNumber=3;
                }

                doorKissingChar.paint(g,null);
                popUpInstruction.update(g,paint);

                door1.paint(g, null);
                femaleChar.paint(g, null);
                maleChar.paint(g, null);
                innerThought.update(g,paint);

                break;
            case 3:
                if (isDelay) {
                    delayCount();
                }

                if ((delayCounter >= 2 && isDelay) && (!innerThought_top_left.isVisible() && !innerThought_top_right.isVisible() && !innerThought_bottom_left.isVisible() && !innerThought_bottom_right.isVisible() )) {
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
                    //isDelay=false;

                }

                if ((delayCounter == 2 && isDelay) && (innerThought_top_left.isVisible() && innerThought_top_right.isVisible() && innerThought_bottom_left.isVisible() && innerThought_bottom_right.isVisible())) {
                    delayCounter = 0;
                    isDelay = false;
                    // jump to next story
                    gameThread.running = false;
                    gameThread = null;
                    this.surfaceHolder.removeCallback(this);
                    Intent temp = new Intent();
                    temp.putExtra("maleX", maleChar.getX());
                    temp.putExtra("maleY", maleChar.getY());
                    temp.putExtra("femaleX", femaleChar.getX());
                    temp.putExtra("femaleY", femaleChar.getY());

                    GlobalVariables.initialize();
                    ((Activity) context).setResult(Activity.RESULT_OK, temp);
//                    ((Activity)context).setIntent(temp);
                    ((Activity) context).finish();
                    // this.recycleImages();
                    this.recycle();
                }
                //thinking bubble
                if(!bgSprite.sourceImage.isRecycled()) {
                    maleChar.paint(g, null);
                    innerThought_top_left.update(g, paint);
                    innerThought_top_right.update(g, paint);
                    innerThought_bottom_left.update(g, paint);
                    innerThought_bottom_right.update(g, paint);
                }
                break;
        }
    }

    private HashMap findPhonePoints(int counter){
        HashMap<String,Integer> map=null;
        if(counter==1){
            map=new HashMap<String,Integer>();
            map.put("bonus",30);
        }
        else if(counter==2){
            map=new HashMap<String,Integer>();
            map.put("bonus",20);
        }
        else if(counter>=3){//Note: should be change
            map=new HashMap<String,Integer>();
            map.put("bonus",10);
        }
        return  map;
    }

    private void repeatedScene(){

        femaleChar.setVisible(false);

        ShahSprite tempBG= new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_scene1bg));
        bgSprite.recycle();
        bgSprite=tempBG;

        ShahSprite tempMale=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_boywrongmsg));
        maleChar.recycle();
        maleChar=tempMale;
        maleChar.setPosition(width / 2 - maleChar.getWidth() / 2, 0);
        maleChar.setVisible(true);


        paint.setColor(Color.BLACK);
        //inner Thought for next Screen

        innerThought_top_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_left.mess_bgImage.getHeight(), InnerThought.TOP_LEFT);
        innerThought_top_left.setThoughtPositions(innerThought_top_left,1);

        innerThought_top_right.setPosition(maleChar.getX() +3* maleChar.getWidth()/4,
                maleChar.getY()+maleChar.getHeight()/2 - innerThought_top_right.mess_bgImage.getHeight(), InnerThought.TOP_RIGHT);
        innerThought_top_right.setThoughtPositions(innerThought_top_right,2);

        innerThought_bottom_left.setPosition(maleChar.getX()+maleChar.getWidth()/4 - innerThought_top_left.mess_bgImage.getWidth(),
                maleChar.getY() +3* maleChar.getWidth() / 4, InnerThought.BOTTOM_LEFT);
        innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);

        innerThought_bottom_right.setPosition(maleChar.getX()  +3* maleChar.getWidth()/4,
                maleChar.getY() + 3*maleChar.getWidth() / 4, InnerThought.BOTTOM_RIGHT);
        innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);

        isDelay = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context,R.raw.tap_sound);

        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
        switch (sceneNumber) {

            case 0:
                switch (subSceneNumber) {
                    case 0:
                        if (messDisplay) {
                            if (backGroundMess.actionButton.getDstRect() != null && backGroundMess.actionButton.getDstRect().intersect(touchrecF)) {
                                if (messDisplay && !popMessDisplay) {
                                    messDisplay = false;
                                    isDelay=true;
//                                    popMessDisplay = true;
//                                    backGroundMess.recycle();
//                                    faded_bgSprite.recycle();
                                    subSceneNumber = 1;
                                }
                            }
                        }
                        break;
                    case 1:
                        if (popMessDisplay &&
                                ((cusion_box.getDstRect().intersect(touchrecF)) ||
                                        (matress_box.getDstRect().intersect(touchrecF)) ||
                                        (bag_box.getDstRect().intersect(touchrecF)))) {
                            popUpInstruction.setVisible(false);
                            subSceneNumber = 2;
                        }
                        //  break;//noted

                    case 2:
                        if (cusion_box.getDstRect().intersect(touchrecF) && !fon_serch_objectStatus[0]) {
                            if (zoomed_bg != null) {
                                zoomed_bg.recycle();
                            }
                            popUpInstruction.setVisible(true);
                            popUpInstruction.setMessString("Tap the image to search phone");

                            cusion.setFrame(0);
                            zoomed_bg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_cusion_zoomed_bg));
                            subSceneNumber = 3;
                        }
                        if (matress_box.getDstRect().intersect(touchrecF) && !fon_serch_objectStatus[1]) {
                            if (zoomed_bg != null) {
                                zoomed_bg.recycle();
                            }
                            popUpInstruction.setVisible(true);
                            popUpInstruction.setMessString("Tap the image to search phone");

                            mattress.setFrame(0);
                            zoomed_bg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_bed_zoomed_bg));
                            subSceneNumber = 4;
                        }
                        if (bag_box.getDstRect().intersect(touchrecF) && !fon_serch_objectStatus[2]) {
                            if (zoomed_bg != null) {
                                zoomed_bg.recycle();
                            }
                            popUpInstruction.setVisible(true);
                            popUpInstruction.setMessString("Tap the image to search phone");

                            bag.setFrame(0);
                            zoomed_bg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_bag_zoomed_bg));
                            subSceneNumber = 5;
                        }

                        break;
                    case 3://cusion random_object=0
                        if(cusion.isVisible()&&cusion.getDstRect().intersect(touchrecF) && !fon_serch_objectStatus[0]){
                            if(!isDelay){
                                if (cusion.getFrame() == 0 && rand_phone_object == 0) {
                                    cusion.setFrame(1);
                                    //set instruction

                                    popUpInstruction.setMessString("Pick the Phone");
                                    popUpInstruction.setVisible(true);
                                } else if (cusion.getFrame() == 1) {

                                    //pic up phone instruction and set big phone screen
                                    popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                    popUpInstruction.setVisible(true);
                                    subSceneNumber=6;
                                } else {
                                    cusion.setFrame(2);
                                    fonSerchCounter++;
                                    //set instruction try other places
                                    popUpInstruction.setMessString("Try Other Place");
                                    popUpInstruction.setVisible(true);
                                    isDelay = true;
                                    delayCounter=0;
                                    fon_serch_objectStatus[0]=true;
                                }
                            }

                        }

                        break;
                    case 4://matress
                        if(mattress.isVisible()&&mattress.getDstRect().intersect(touchrecF) && !fon_serch_objectStatus[1]){
                            if(!isDelay){
                                if (mattress.getFrame() == 0 && rand_phone_object == 1) {
                                    mattress.setFrame(1);
                                    //set instruction
                                    popUpInstruction.setMessString("Pick the Phone");
                                    popUpInstruction.setVisible(true);
                                } else if (mattress.getFrame() == 1) {

                                    //pic up phone instruction and set big phone screen
                                    popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                    popUpInstruction.setVisible(true);
                                    subSceneNumber=6;

                                } else {
                                    mattress.setFrame(2);
                                    fonSerchCounter++;
                                    //set instruction try other places
                                    popUpInstruction.setMessString("Try Other Place");
                                    popUpInstruction.setVisible(true);
                                    isDelay = true;
                                    delayCounter=0;
                                    fon_serch_objectStatus[1]=true;
                                }

                            }

                        }
                        break;

                    case 5://bag
                        if(bag.isVisible()&&bag.getDstRect().intersect(touchrecF) && !fon_serch_objectStatus[2]){
                            if(!isDelay){
                                if (bag.getFrame() == 0 && rand_phone_object == 2) {
                                    bag.setFrame(1);
                                    //set instruction
                                    popUpInstruction.setMessString("Pick the Phone");
                                    popUpInstruction.setVisible(true);
                                } else if (bag.getFrame() == 1) {

                                    //pic up phone instruction and set big phone screen
                                    popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                    popUpInstruction.setVisible(true);
                                    subSceneNumber=6;
                                } else {
                                    bag.setFrame(2);
                                    fonSerchCounter++;
                                    //set instruction try other places
                                    popUpInstruction.setMessString("Try Other Place");
                                    popUpInstruction.setVisible(true);
                                    isDelay = true;
                                    delayCounter=0;
                                    fon_serch_objectStatus[2]=true;
                                }
                            }

                        }
                        break;
                    case 6:
                        //when  phone visible
                        if (bigPhone.isVisible() && bigPhone.getDstRect().intersect(touchrecF)) {
                            bigPhone.setVisible(false); //"Now, click on the mobile phone to start the conversation."

                            // bgSprite.recycle();
                            ShahSprite tempBG= new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_scene1bg));
                            bgSprite=tempBG;

                            wrong_msg_char = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_wrong_msg));
                            wrong_msg_char.setPosition(width-wrong_msg_char.getWidth(),0);
                            wrong_msg_char.setVisible(false);

                            beep01 = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.beep01));
                            beep01.setPosition((int)(GlobalVariables.xScale_factor*510),(int)(GlobalVariables.yScale_factor*372));
                            beep01.setVisible(false);

                            beep02 = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.beep02));
                            beep02.setPosition((int)(GlobalVariables.xScale_factor*610),height-beep02.getHeight());
                            beep02.setVisible(false);

                            //***************Score Added****************
                            UpdateScore.score(context,findPhonePoints(fonSerchCounter));
                            //**************************************

//                            ShahSprite tempMale=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.thinking_bubble_character));
//                            maleChar.recycle();
//                            maleChar=tempMale;
//
//                            ShahSprite tempFemale=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.thinking_bubble_character));
//                            femaleChar.recycle();
//                            femaleChar=tempFemale;


                            isDelay = true;
                            delayCounter=0;
                            paint.setColor(Color.BLACK);
                            subSceneNumber = 7;
                        }
                        break;

                }
                break;
            case 1:
                switch (subSceneNumber) {
                    case 0:
                        if ((door.isVisible() && door.getDstRect().intersect(touchrecF))&&
                             popUpInstruction.isVisible()&&(!knocksign01.isVisible()&&!knocksign02.isVisible())) {

                            //**********Sound**********************
                            MySound.stopSound(context,R.raw.a);
                            MySound.playSound(context,R.raw.a);
                            GlobalVariables.Audio_File_Name=R.raw.a;
                            //*****************************************

                            door.setVisible(false);
                            door1.setVisible(true);
                            popUpInstruction.setVisible(false);

                            maleChar.recycle();
                            Bitmap tempMale=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_male_knock_char);
                            maleChar.setImage(tempMale,tempMale.getWidth(),tempMale.getHeight());
                            maleChar.setPosition(door1.getX() - maleChar.getWidth()/2, height- maleChar.getHeight());

                            femaleChar.recycle();
                            Bitmap tempFemale=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.joseph_normal);
                            femaleChar.setImage(tempFemale,tempFemale.getWidth(),tempFemale.getHeight());
                            femaleChar.setPosition(door1.getX()+door1.getWidth()-femaleChar.getWidth()-(int)(GlobalVariables.xScale_factor*30), door1.getY() + door1.getHeight() - femaleChar.getHeight()+(int)(GlobalVariables.yScale_factor*30));

                            popUpInstruction.setMessString("You open the door & see Joseph standing. He grabs you and gives you a deep kiss");
                            popUpInstruction.setVisible(false);

                            faded_bgSprite.setVisible(false);
                            isDelay=true;
                            delayCounter=0;
                            subSceneNumber = 1;
                        }
                        break;
                    case 1:

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

            System.out.println("Start Joining.......");
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

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_scene1bg));
                //gameStart = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.player_selection));
        scene1_boy= new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_scene1_boy));
        scene1_boy.setPosition((int)(110*GlobalVariables.xScale_factor),(int)(150*GlobalVariables.yScale_factor));

        Bitmap temp_bag=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_mobileinbagsprite);
        bag = new ShahSprite(temp_bag,temp_bag.getWidth()/3,temp_bag.getHeight());
        bag.setPosition(width / 2-bag.getWidth()/2, 0);

        Bitmap temp_mattress=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_mobileundermatress);
        mattress = new ShahSprite(temp_mattress,temp_mattress.getWidth()/3,temp_mattress.getHeight());
        mattress.setPosition(width-mattress.getWidth(),height-mattress.getHeight());

        Bitmap temp_pillow=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_mobileundercusion);
        cusion = new ShahSprite(temp_pillow,temp_pillow.getWidth()/3,temp_pillow.getHeight());
        cusion.setPosition(width / 2-cusion.getWidth()/2, (int)(100*GlobalVariables.yScale_factor));

        cusion_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_cusion_box));
        cusion_box.setPosition((int)(GlobalVariables.xScale_factor*434),(int)(GlobalVariables.yScale_factor*94));

        matress_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_matress_box));
        matress_box.setPosition((int)(GlobalVariables.xScale_factor*246),(int)(GlobalVariables.yScale_factor*173));

        bag_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_bag_box));
        bag_box.setPosition((int)(GlobalVariables.xScale_factor*71),(int)(GlobalVariables.yScale_factor*173));

        doorKissingChar=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_opndoorkissingscene));
        doorKissingChar.setPosition(width/2-doorKissingChar.getWidth()/2,height-doorKissingChar.getHeight());
        doorKissingChar.setVisible(true);

        rand_phone_object = new Random().nextInt(3);

        bigPhone = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.mobiletocall));
        bigPhone.setPosition(width / 2 - bigPhone.getWidth() / 2, 0);

        maleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_female_char));
        femaleChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_male_char));
        maleChar.setPosition(0, 0);
        femaleChar.setPosition(width-femaleChar.getWidth(),0);
        femaleChar.setVisible(false);

        girl_conversation_bg = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.g_girl_conversation_bg));
        girl_conversation_bg.setPosition(width-girl_conversation_bg.getWidth(),0);
        girl_conversation_bg.setVisible(false);

        maleConversationMessage = new Message(R.drawable.boy_conversation_box, "Hey! By what time should I come to pick you up?");
        maleConversationMessage.setVisible(false);
//        maleConversationMessage.mess_bgImage.setPosition(maleChar.getX() + maleChar.getWidth()/2+10,maleChar.getY()+maleChar.getHeight()/2);
        maleConversationMessage.mess_bgImage.setPosition(maleChar.getX() + maleChar.getWidth()/2+20,(int)(GlobalVariables.yScale_factor*200));
        maleConversationMessage.setMessTextPosition(maleConversationMessage.mess_bgImage.getX()+(int)(GlobalVariables.xScale_factor*10) , maleConversationMessage.mess_bgImage.getY() + (int)(GlobalVariables.yScale_factor*10));

        femaleConversationMessage = new Message(R.drawable.girl_msg_txt_box, " I Can’t go out as I have to work on a project tonight. Sorry ");
        femaleConversationMessage.setVisible(false);
//        femaleConversationMessage.mess_bgImage.setPosition(femaleChar.getX() - femaleConversationMessage.mess_bgImage.getWidth()/2, femaleChar.getY()+femaleChar.getHeight()/2);
        femaleConversationMessage.mess_bgImage.setPosition((int)(GlobalVariables.xScale_factor*315), (int)(GlobalVariables.yScale_factor*160));
//        femaleConversationMessage.setMessTextPosition(femaleConversationMessage.mess_bgImage.getX() + 20, femaleConversationMessage.mess_bgImage.getY() + 10);
        femaleConversationMessage.setMessTextPosition(femaleConversationMessage.mess_bgImage.getX() +(int)(GlobalVariables.xScale_factor*10) , femaleConversationMessage.mess_bgImage.getY() +(int)(GlobalVariables.yScale_factor*10) );

        //gameChangerMessage = new GameChangerMessage(R.drawable.gamechanger_message_bg, "Oops! You sent the text to Susan");
        innerThought = new InnerThought(R.drawable.inner_thought_topright_new, "OMG I sent the text to Joseph");
        innerThought.setPosition(maleChar.getX() + maleChar.getWidth(), maleChar.getY() + innerThought.mess_bgImage.getHeight());
//        innerThought.setMessTextPosition(innerThought.mess_bgImage.getX()+47,innerThought.mess_bgImage.getY()+10);
        innerThought.setThoughtPositions(innerThought,2);
        innerThought.setVisible(false);

        message = new Message(R.drawable.background_mess, "Suddenly you hear a knock at the door");
//        message.setVisible(false);
        door = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.door));
        door.setPosition(width / 2 - door.getWidth() / 2, height / 2 - door.getHeight() / 2);
        door1 = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.door1));
        door1.setPosition(width / 2 - door.getWidth() / 2, height / 2 - door.getHeight() / 2);
        door1.setVisible(false);

        knocksign01=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.knocksign01));
        knocksign01.setVisible(false);
        knocksign01.setPosition(door.getX()+knocksign01.getWidth()/4,door.getY()+door.getHeight()/8);

        knocksign02=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.knocksign02));
        knocksign02.setPosition(door.getX()+knocksign01.getWidth()/2,door.getY()+door.getHeight()/2);
        knocksign02.setVisible(false);

        innerThought_top_left = new InnerThought(R.drawable.inner_thought_topleft_new, "Wait what is he talking about?");
        innerThought_top_left.setVisible(false);
        innerThought_top_right = new InnerThought(R.drawable.inner_thought_topright_new, "OMG has anyone seen us?");
        innerThought_top_right.setVisible(false);
        innerThought_bottom_left = new InnerThought(R.drawable.inner_thought_bottomleft_new, "Is this a test?");
        innerThought_bottom_left.setVisible(false);
        innerThought_bottom_right = new InnerThought(R.drawable.inner_thought_bottomright_new, "WOW you are a great kisser! I always thought you were cute.");
        innerThought_bottom_right.setVisible(false);

        faded_bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        backGroundMess = new BackgroundMessage(R.drawable.background_mess, "It’s Friday night and you have plans of going out with your BF, so you think of texting him asking the time by which you should pick him up. But then you just can’t find your phone.");
        this.popUpInstruction = new PopUpInstruction(R.drawable.popupbg, GlobalVariables.getResource.getString(R.string.scene0_popup));

        gameChangerEffect=new GameChangerEffect();

        System.out.println("End Joinig.......");
        Log.d("msg","End Joinig.......");
        sceneNumber=0;
    }
    @Override
    public void recycle() {
        drawSomething(myCanvas);
        bgSprite.recycle();
        faded_bgSprite.recycle();
        backGroundMess.recycle();
        popUpInstruction.recycle();

        maleChar.recycle();
        femaleChar.recycle();
        door.recycle();
        door1.recycle();
        knocksign01.recycle();
        knocksign02.recycle();
        doorKissingChar.recycle();
        message.recycle();

        innerThought.recycle();
        innerThought_top_left.recycle();
        innerThought_top_right.recycle();
        innerThought_bottom_left.recycle();
        innerThought_bottom_right.recycle();
        gameChangerEffect.recycle();




    }
    private void scene_0_recyle(){

        bag.recycle();
        mattress.recycle();
        cusion.recycle();
        bigPhone.recycle();
        maleConversationMessage.recycle();
        femaleConversationMessage.recycle();
        girl_conversation_bg.recycle();
        wrong_msg_char.recycle();
        beep01.recycle();
        beep02.recycle();

        scene1_boy.recycle();
        zoomed_bg.recycle();
        cusion.recycle();
        mattress.recycle();
    }


}
