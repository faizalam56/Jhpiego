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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.messages.BackgroundMessage;
import zmq.com.jhpiego.messages.PopUpInstruction;
import zmq.com.jhpiego.preferences.UpdateScore;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.Constant;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.MySound;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by zmq162 on 24/6/15.
 */
public class GameBCanvas extends BaseSurface implements Runnable, Recycler {

    private PopUpInstruction popUpInstruction;
    private BackgroundMessage backgroundMessage;
    private ShahSprite bgSprite;
    private int sceneNumber = -2;
    private int subSceneNumber = 0;
    private int rand_key_object;
    private Canvas myCanvas;
    private Intent intent;
    private Constant myConstant;
    private ShahSprite first_box,second_box,third_box;
    private ShahSprite klub_sprite,machine_sprite,machine_hit_box;
    private  ShahSprite firstBoxSprite,secondBoxSprite,thirdBoxSprite;
    private  ShahSprite klubTicket,machineZoom;
    private boolean []boxTouchStatus = {false,false,false};
    private Integer []firstArray,secondArray,thirdArray;
    private int ticketSerchCounter = 1;

    int counterF,counterS=1,counterT=2;
    boolean boolF = true,boolS = true,boolT = true;
    int sleepCounter;
    int mSleepTime;
    int commonIndex=-1;
    int lives = 3;
    Paint mPaint;

    int setAlfa[] = {0,50,25,100,50,141};
    int setAlfaCounter;

    int boxCounter;

    public GameBCanvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        myConstant = new Constant(context);
        mPaint = new Paint();

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" GameBCanvas surfaceDestroyed Called...");
        }
    }
    @Override
    protected void drawSomething(Canvas g) {
        myCanvas = g;

        if(g!=null)g.drawColor(Color.TRANSPARENT);


        switch (sceneNumber) {

            case -2:

                myConstant.drawImageWithRotation(g);
                loadImages();
                break;

            case 1:
                scene1(g);
                break;
            case 2:

                break;
            default:
                break;
        }
    }

    private void drawText(Canvas canvas, String Text, int xMidPosition, int yMidPosition, int textSize) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Text, xMidPosition, yMidPosition, paint);
    }

    private void scene1(Canvas g) {

        myCanvas = g;
        klub_sprite.paint(g, null);

        switch (subSceneNumber) {

            case 0:

                if (isDelay) {
                    delayCount();
                }
                if (delayCounter == 8) {

                    delayCounter = 0;
                    isDelay = false;
                    popUpInstruction.setMessString("Match the pictures to get the tickets!");

                    machine_sprite.setVisible(true);
                    machine_hit_box.setVisible(true);
                }

                machine_sprite.paint(g, null);
                popUpInstruction.update(g,paint);
                //machine_hit_box.paint(g, null);

                break;

            case 1:

                if(isDelay){
                    delayCount();
                }
                if(delayCounter == 2 && first_box.isVisible()){
                    first_box.setVisible(false);
                    delayCounter = 0;
                    isDelay = false;
                    boolF = true;
                }
                if(delayCounter == 2 && second_box.isVisible()){
                    second_box.setVisible(false);
                    delayCounter = 0;
                    isDelay = false;
                    boolS = true;
                }
                if(delayCounter == 2 && third_box.isVisible()){
                    third_box.setVisible(false);
                    delayCounter = 0;
                    isDelay = false;
                    boolT = true;
                }

                manageSleepTime(GlobalVariables.sleepTime);
                bgSprite.paint(g, null);
                drawText(g, "Lives : "+lives, (int) (GlobalVariables.xScale_factor *400), (int) (GlobalVariables.yScale_factor *40), 30);


                if(counterF<5 && boolF && sleepCounter == 2){
                    counterF++;
                }else if(counterF==5 && boolF && sleepCounter == 2){
                    counterF = 0;
                }

                if(counterS<5 && boolS && sleepCounter == 2){
                    counterS++;
                }else if(counterS==5 && boolS && sleepCounter == 2){
                    counterS = 0;
                }
                if(counterT<5 && boolT && sleepCounter == 2){
                    counterT++;
                }else if(counterT==5 && boolT && sleepCounter == 2){
                    counterT = 0;
                }

                if(setAlfaCounter < 5){
                    setAlfaCounter++;
                }
                else{
                    setAlfaCounter = 0;
                }

                firstBoxSprite.setFrame(firstArray[counterF]);
                secondBoxSprite.setFrame(firstArray[counterS]);
                thirdBoxSprite.setFrame(firstArray[counterT]);

                firstBoxSprite.paint(g,null);
                secondBoxSprite.paint(g,null);
                thirdBoxSprite.paint(g, null);


                mPaint.setAlpha(setAlfa[setAlfaCounter]);


                first_box.paint(g,mPaint);
                second_box.paint(g,mPaint);
                third_box.paint(g,mPaint);


                break;

            case 2:

                if(!bgSprite.sourceImage.isRecycled()){
                    bgSprite.paint(g, null);
                    firstBoxSprite.paint(g,null);
                    secondBoxSprite.paint(g,null);
                    thirdBoxSprite.paint(g, null);

                    //    mPopUpMessage.update(g,paint);
                    backgroundMessage.update(g,paint);
                }

                break;

            case 3:

                if(!bgSprite.sourceImage.isRecycled()){
                    bgSprite.paint(g, null);
                    firstBoxSprite.paint(g,null);
                    secondBoxSprite.paint(g,null);
                    thirdBoxSprite.paint(g, null);

                    //    mPopUpMessage.update(g,paint);
                    backgroundMessage.actionButton.sourceImage = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.continue1);
                    backgroundMessage.setMessString("Congratulations");
                    backgroundMessage.update(g,paint);
                }

                break;
            case 4:
                machineZoom.paint(g, null);
                klubTicket.paint(g, null);
                //popUpInstruction.setMessString("Tap on the tickets to collect");
                popUpInstruction.update(g,paint);

                if(isDelay){
                    delayCount();
                }

                if(delayCounter == 2){

                    delayCounter = 0;
                    isDelay = false;
                    gameThread.running = false;
                    gameThread = null;
                    this.surfaceHolder.removeCallback(this);

                    MySound.stopSound(context,GlobalVariables.Audio_File_Name);
                    GlobalVariables.INVITE_IN=25;
                    ((Activity) context).setResult(GlobalVariables.INVITE_IN, new Intent());
                    ((Activity) context).finish();
                    this.recycle();
                }
                break;
        }

    }


    boolean initialiseThreadLoad = true;
    Thread localThread = null;

    public void loadImages() {
        if (initialiseThreadLoad) {
            localThread = new Thread(this);
            localThread.start();

            System.out.println("Start Joinig.......");
            initialiseThreadLoad = false;
        }
    }
    private HashMap findTicketPoints(int counter){
        HashMap<String,Integer> map=null;
        if(counter==1){
            map=new HashMap<String,Integer>();
            map.put("bonus",30);
        }
        else if(counter==2){
            map=new HashMap<String,Integer>();
            map.put("bonus",20);
        }
        else if(counter==3){//Note: should be change
            map=new HashMap<String,Integer>();
            map.put("bonus",10);
        }
        else{
            map=new HashMap<String,Integer>();
            map.put("bonus",0);
        }
        return  map;
    }

    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context,R.raw.tap_sound);

        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);

        switch (sceneNumber) {

            case 1:

                switch (subSceneNumber) {

                    case 0:

                        if (machine_hit_box.isVisible() && machine_hit_box.getDstRect().intersect(touchrecF)) {
                            popUpInstruction.setVisible(false);

                            Bitmap temp_firstBoxSprite=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.icons_sprite);
                            firstBoxSprite = new ShahSprite(temp_firstBoxSprite,temp_firstBoxSprite.getWidth()/6,temp_firstBoxSprite.getHeight());
                            firstBoxSprite.setPosition((int) (GlobalVariables.xScale_factor * 181), (int) (GlobalVariables.yScale_factor * 174));


                            Bitmap temp_secondBoxSprite=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.icons_sprite);
                            secondBoxSprite = new ShahSprite(temp_secondBoxSprite,temp_secondBoxSprite.getWidth()/6,temp_secondBoxSprite.getHeight());
                            secondBoxSprite.setPosition((int) (GlobalVariables.xScale_factor * 346), (int) (GlobalVariables.yScale_factor * 174));

                            Bitmap temp_thirdBoxSprite=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.icons_sprite);
                            thirdBoxSprite = new ShahSprite(temp_thirdBoxSprite,temp_thirdBoxSprite.getWidth()/6,temp_thirdBoxSprite.getHeight());
                            thirdBoxSprite.setPosition((int) (GlobalVariables.xScale_factor * 508), (int) (GlobalVariables.yScale_factor * 174));

                            subSceneNumber = 1;
                            firstArray = getUniqueRandom();
                            isDelay = false;

//                          secondArray = getUniqueRandom();
//                            thirdArray = getUniqueRandom();
                        }
                        break;

                    case 1:
                        if (!first_box.isVisible() && first_box.getDstRect().intersect(touchrecF) && !boxTouchStatus[0]) {
                            //   Toast.makeText(context,"Touch "+counterF,Toast.LENGTH_SHORT).show();
                            boolF=false;
                            if(commonIndex == -1){
                                gameLifeChecker();
                                boxCounter++;
                                boxTouchStatus[0] = true;
                            }
                            else{

                                if(commonIndex == firstBoxSprite.getFrame()){
                                    boxCounter++;
                                    if(boxCounter == 3){

                                        subSceneNumber = 3;
                                    }
                                    else{
                                        boxTouchStatus[0] = true;
                                    }
                                }
                                else{
                                    if(lives > 0){
                                        lives--;
                                        first_box.setVisible(true);
                                        second_box.setVisible(false);
                                        third_box.setVisible(false);
                                        isDelay = true;
                                    }
                                    else{
                                        subSceneNumber = 2;
                                    }

                                }
                            }
                        }
                        if (!second_box.isVisible() && second_box.getDstRect().intersect(touchrecF) && !boxTouchStatus[1]) {
                            boolS=false;
                            if(commonIndex == -1){
                                gameLifeChecker();
                                boxCounter++;
                                boxTouchStatus[1] = true;
                            }
                            else{
                                if(commonIndex == secondBoxSprite.getFrame()){
                                    boxCounter++;
                                    if(boxCounter == 3){

                                        subSceneNumber = 3;
                                    }
                                    else{
                                        boxTouchStatus[1] = true;
                                    }
                                }
                                else{
                                    if(lives > 0){

                                        lives--;
                                        first_box.setVisible(false);
                                        second_box.setVisible(true);
                                        third_box.setVisible(false);
                                        isDelay = true;

                                    }
                                    else{
                                        subSceneNumber = 2;
                                    }
                                }
                            }
                        }
                        if (!third_box.isVisible() && third_box.getDstRect().intersect(touchrecF) && !boxTouchStatus[2]) {
                            boolT=false;
                            if(commonIndex == -1){
                                gameLifeChecker();
                                boxCounter++;
                                boxTouchStatus[2] = true;
                            }
                            else{
                                if(commonIndex == thirdBoxSprite.getFrame()){
                                    boxCounter++;
                                    if(boxCounter == 3){

                                        subSceneNumber = 3;
                                    }
                                    else{
                                        boxTouchStatus[2] = true;
                                    }
                                }
                                else{
                                    if(lives > 0 ){
                                        lives--;
                                        first_box.setVisible(false);
                                        second_box.setVisible(false);
                                        third_box.setVisible(true);
                                        isDelay = true;
                                    }else {
                                        subSceneNumber = 2;
                                    }
                                }
                            }

                        }

                        break;

                    case 2:

                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            first_box.setVisible(false);
                            second_box.setVisible(false);
                            third_box.setVisible(false);

                            firstBoxSprite.setVisible(true);
                            secondBoxSprite.setVisible(true);
                            thirdBoxSprite.setVisible(true);

                            ticketSerchCounter++;

                            boolF=true;
                            boolS=true;
                            boolT=true;
                            lives=3;
                            subSceneNumber=1;
                            commonIndex = -1;
                            counterF=0;counterS=1;counterT=2;
                            setAlfaCounter= 0;
                            boxCounter=0;
                            boxTouchStatus[0] = false;
                            boxTouchStatus[1] = false;
                            boxTouchStatus[2] = false;

                        }


                        break;

                    case 3:



                        if(backgroundMessage.isVisible() && backgroundMessage.actionButton.getDstRect().intersect(touchrecF)){
                            popUpInstruction.setVisible(true);
                            popUpInstruction.setMessString("Tap on the tickets to collect");
                            machineZoom = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.ticket_vending_machine_zoom));
                            subSceneNumber = 4;
                        }

                        break;
                    case 4:

                        if(klubTicket.isVisible() && klubTicket.getDstRect().intersect(touchrecF)){

                            //***************Score Added****************
                            UpdateScore.score(context, findTicketPoints(ticketSerchCounter));
                            //**************************************

                            popUpInstruction.setVisible(false);
                            klubTicket.setVisible(false);
                            isDelay =true;
                        }

                        break;
                }
                break;

            case 2:



                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public  Integer[] getUniqueRandom()  {

        Integer[] arr = new Integer[6];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        System.out.println(Arrays.toString(arr));

        return  arr;
    }



    @Override

    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.ticket_vending_machine));

        klub_sprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_inside));
        machine_sprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_image_outside_machine));
        machine_sprite.setVisible(false);

        machine_hit_box = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.machine_hit));
        machine_hit_box.setPosition((int) (GlobalVariables.xScale_factor * 215), (int) (GlobalVariables.yScale_factor * 85));
        machine_hit_box.setVisible(false);


        backgroundMessage=new BackgroundMessage(R.drawable.background_mess,"Would you like to play again?");
        backgroundMessage.actionButton.sourceImage = Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.play_againbtn);

        klubTicket = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.klub_tickets));
        klubTicket.setPosition((int) (GlobalVariables.xScale_factor * 248), (int) (GlobalVariables.yScale_factor * 203));

        this.popUpInstruction = new PopUpInstruction(R.drawable.popupbg, "You decided to take it easy & go to Klub Image with your BFF but you don’t know what is waiting for you inside…");

        first_box = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.blink_box));
        first_box.setPosition((int) (GlobalVariables.xScale_factor * 181), (int) (GlobalVariables.yScale_factor * 174));
        first_box.setVisible(false);

        second_box = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.blink_box));
        second_box.setPosition((int) (GlobalVariables.xScale_factor * 346), (int) (GlobalVariables.yScale_factor * 174));
        second_box.setVisible(false);

        third_box = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.blink_box));
        third_box.setPosition((int) (GlobalVariables.xScale_factor * 508), (int) (GlobalVariables.yScale_factor * 174));
        third_box.setVisible(false);

        sceneNumber = 1;
    }

    @Override
    public void recycle() {

        drawSomething(myCanvas);
        bgSprite.recycle();
        first_box.recycle();
        second_box.recycle();
        third_box.recycle();
        firstBoxSprite.recycle();
        secondBoxSprite.recycle();
        thirdBoxSprite.recycle();

        klub_sprite.recycle();
        machine_sprite.recycle();
        machine_hit_box.recycle();
        popUpInstruction.recycle();
        // mPopUpMessage.recycle();
        backgroundMessage.recycle();
        machineZoom.recycle();
        klubTicket.recycle();

    }

    public void  manageSleepTime(int millSec){


        if(mSleepTime < 200){
            mSleepTime = mSleepTime+millSec;
            sleepCounter++;
            // System.out.println("Sleep Counter "+mSleepTime+"   "+sleepCounter);
        }else{
            sleepCounter = 0;
            mSleepTime = 0;
        }

    }

    public void gameLifeChecker(){

        if(!boolF & boolS & boolT){
            commonIndex = firstArray[counterF];

        }
        else if(!boolS & boolF & boolT){
            commonIndex = firstArray[counterS];

        }
        else if(!boolT & boolF & boolS){
            commonIndex = firstArray[counterT];

        }


    }

}