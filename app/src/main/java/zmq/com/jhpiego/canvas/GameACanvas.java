package zmq.com.jhpiego.canvas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.HashMap;
import java.util.Random;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
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
public class GameACanvas extends BaseSurface implements Runnable, Recycler {

    private PopUpInstruction popUpInstruction;
    private ShahSprite bgSprite;
    private int key_sceneNumber =-2;
    private int key_subSceneNumber =0;
    private  int rand_key_object;
    private Canvas myCanvas;
    private Intent intent;
    private Constant myConstant;
    private ShahSprite letter_box,left_vase_box,right_vase_box,carpet_box;
    private ShahSprite letter_boxSprite,vase_boxSpriteLeft,vase_boxSpriteRight,carpet_boxSprite;
    private ShahSprite door_prite,key_hand,door_overlap;
    private ShahSprite zoomBg;
    private boolean key_serch_objectStatus[]={false,false,false,false};
    int x_decrement;
    private int keySerchCounter=1;


    public GameACanvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;
        myConstant=new Constant(context);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" GameACanvas surfaceDestroyed Called...");
        }
    }
    @Override
    protected void drawSomething(Canvas g) {

        if(g!=null)g.drawColor(Color.TRANSPARENT);

    switch (key_sceneNumber) {

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
    private HashMap findKeyPoints(int counter){
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

    private void scene1(Canvas g) {

        myCanvas=g;
        bgSprite.paint(g, null);

        switch (key_subSceneNumber) {


            case 0:

                letter_box.paint(g,null);
                left_vase_box.paint(g,null);
                right_vase_box.paint(g,null);
                carpet_box.paint(g,null);

                popUpInstruction.update(g, paint);
                isDelay=false;
                break;

            case 1:// letter box ///0
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    key_subSceneNumber =0;
                    delayCounter=0;
                    isDelay=false;
                }
                zoomBg.paint(g,null);
                letter_boxSprite.paint(g, null);
                popUpInstruction.update(g,paint);
                break;

            case 2://  left vase ///1
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    key_subSceneNumber =0;
                    delayCounter=0;
                    isDelay=false;
                }
                zoomBg.paint(g,null);
                vase_boxSpriteLeft.paint(g,null);
                popUpInstruction.update(g,paint);
                break;

            case 3://  Carpet ///2

                if (isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    isDelay=false;
                    delayCounter = 0;
                    popUpInstruction.setVisible(false);
                    key_subSceneNumber =0;
                }
                zoomBg.paint(g,null);
                carpet_boxSprite.paint(g, null);
                popUpInstruction.update(g,paint);
                break;

            case 4:// right vase

                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    key_subSceneNumber =0;
                    delayCounter=0;
                    isDelay=false;
                }
                zoomBg.paint(g,null);
                vase_boxSpriteRight.paint(g,null);
                popUpInstruction.update(g,paint);
                break;

            case 5:

                if(key_hand.getX() > 221){
                    x_decrement --;
                    key_hand.setPosition(key_hand.getX() + x_decrement,186);
                }
                else{
                    isDelay=true;
                }
                if(isDelay){
                    delayCount();
                }
                if(delayCounter==2){
                    delayCounter = 0;
                    isDelay = false;
                    // Replay
                    gameThread.running = false;
                    gameThread = null;
                    this.surfaceHolder.removeCallback(this);


                    GlobalVariables.INVITE_IN=20;

                    ((Activity) context).setResult(GlobalVariables.INVITE_IN, new Intent());
                    ((Activity) context).finish();
                    this.recycle();

                }
            if(!bgSprite.sourceImage.isRecycled()) {
                door_prite.paint(g, null);
                key_hand.paint(g, null);
                door_overlap.paint(g, null);
            }
                break;
            default:
                break;
        }
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

    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context,R.raw.tap_sound);

        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);

        switch (key_sceneNumber) {

            case 1:

                switch (key_subSceneNumber) {

                    case 0://letter box random_object=0


                        if (letter_box.getDstRect().intersect(touchrecF) && !key_serch_objectStatus[0]){
                            popUpInstruction.setMessString("Tap the image......");
                            zoomBg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.letterbox_bg_min));
                            key_subSceneNumber = 1;
                        }
                        if (left_vase_box.getDstRect().intersect(touchrecF) && !key_serch_objectStatus[1]){
                            popUpInstruction.setMessString("Tap the image......");
                            zoomBg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_bg_min));
                            key_subSceneNumber = 2;
                            vase_boxSpriteLeft.setFrame(0);
                        }
                        if (right_vase_box.getDstRect().intersect(touchrecF)&& !key_serch_objectStatus[2]) {
                            popUpInstruction.setMessString("Tap the image......");
                            zoomBg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_bg_min));
                            key_subSceneNumber = 4;
                            vase_boxSpriteRight.setFrame(0);
                        }
                        if (carpet_box.getDstRect().intersect(touchrecF)&& !key_serch_objectStatus[3]) {
                            popUpInstruction.setMessString("Tap the image......");
                            zoomBg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.under_mat_bg_min));
                            key_subSceneNumber = 3;
                        }

                        break;

                    case 1:

                        if(letter_boxSprite.isVisible()&&letter_boxSprite.getDstRect().intersect(touchrecF) && !key_serch_objectStatus[0]){

                           // if( isDelay){
                                if (letter_boxSprite.getFrame() == 0 && rand_key_object == 0) {
                                    letter_boxSprite.setFrame(1);
                                    //set instruction


                                    //***************Score Added****************
                                    UpdateScore.score(context, findKeyPoints(keySerchCounter));
                                    //**************************************

                                    popUpInstruction.setMessString("Pick the Key");
                                    popUpInstruction.setVisible(true);
                                } else if (letter_boxSprite.getFrame() == 1) {

                                    //pic up phone instruction and set big phone screen
                                    popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                    popUpInstruction.setVisible(true);

                                   key_subSceneNumber =5;
                                    isDelay=false;
                                } else {
                                    letter_boxSprite.setFrame(2);

                                    keySerchCounter++;
                                    popUpInstruction.setMessString("Try Other Place");
                                    popUpInstruction.setVisible(true);
                                    isDelay = true;
                                    delayCounter=0;
                                    key_serch_objectStatus[0]= true;

                                }
                          //  }

                        }
                        break;


                    case 2:

                        if(vase_boxSpriteLeft.isVisible()&&vase_boxSpriteLeft.getDstRect().intersect(touchrecF)&& !key_serch_objectStatus[1]){

                            //if( isDelay){
                            if (vase_boxSpriteLeft.getFrame() == 0 && rand_key_object == 1) {
                                vase_boxSpriteLeft.setFrame(1);

                                //***************Score Added****************
                                UpdateScore.score(context, findKeyPoints(keySerchCounter));
                                //**************************************

                                popUpInstruction.setMessString("Pick the Key");
                                popUpInstruction.setVisible(true);
                            } else if (vase_boxSpriteLeft.getFrame() == 1) {

                                //pic up phone instruction and set big phone screen
                                popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                popUpInstruction.setVisible(true);
                                key_subSceneNumber =5;
                                isDelay=false;
                            } else {
                                vase_boxSpriteLeft.setFrame(2);

                                keySerchCounter++;
                                popUpInstruction.setMessString("Try Other Place");
                                popUpInstruction.setVisible(true);
                                isDelay = true;
                                delayCounter=0;
                                key_serch_objectStatus[1]= true;
                            }
                            // }
                        }
                        break;
                    case 3:

                        if(carpet_boxSprite.isVisible()&&carpet_boxSprite.getDstRect().intersect(touchrecF)&& !key_serch_objectStatus[3]){

                        //    if( isDelay){
                                if (carpet_boxSprite.getFrame() == 0 && rand_key_object == 3) {
                                    carpet_boxSprite.setFrame(1);

                                    //***************Score Added****************
                                    UpdateScore.score(context, findKeyPoints(keySerchCounter));
                                    //**************************************

                                    popUpInstruction.setMessString("Pick the Key");
                                    popUpInstruction.setVisible(true);
                                } else if (carpet_boxSprite.getFrame() == 1) {

                                    //pic up phone instruction and set big phone screen
                                    popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                    popUpInstruction.setVisible(true);
                                    key_subSceneNumber =5;
                                    isDelay=false;
                                } else {
                                    carpet_boxSprite.setFrame(2);

                                    keySerchCounter++;
                                    popUpInstruction.setMessString("Try Other Place");
                                    popUpInstruction.setVisible(true);
                                    isDelay = true;
                                    delayCounter=0;
                                    key_serch_objectStatus[3] = true;
                                }
                        //    }
                        }
                        break;
                    case 4:

                        if(vase_boxSpriteRight.isVisible()&&vase_boxSpriteRight.getDstRect().intersect(touchrecF)&& !key_serch_objectStatus[2]){

                            //if( isDelay){
                            if (vase_boxSpriteRight.getFrame() == 0 && rand_key_object == 2) {
                                vase_boxSpriteRight.setFrame(1);

                                //***************Score Added****************
                                UpdateScore.score(context, findKeyPoints(keySerchCounter));
                                //**************************************

                                popUpInstruction.setMessString("Pick the Key");
                                popUpInstruction.setVisible(true);
                            } else if (vase_boxSpriteRight.getFrame() == 1) {

                                //pic up phone instruction and set big phone screen
                                popUpInstruction.setMessString(GlobalVariables.getResource.getString(R.string.scene0_popup1));
                                popUpInstruction.setVisible(true);
                                key_subSceneNumber =5;
                                isDelay=false;
                            } else {
                                vase_boxSpriteRight.setFrame(2);

                                keySerchCounter++;
                                popUpInstruction.setMessString("Try Other Place");
                                popUpInstruction.setVisible(true);
                                isDelay = true;
                                delayCounter=0;
                                key_serch_objectStatus[2]= true;
                            }
                            // }
                        }


                        break;

                    case 5:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      //common scenes for all objects

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

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        bgSprite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.main_bg_min));
        zoomBg =  new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.letterbox_bg_min));

        left_vase_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_hit));
        left_vase_box.setPosition((int)(GlobalVariables.xScale_factor*120),(int)(GlobalVariables.yScale_factor*215));
        left_vase_box.setVisible(false);

        right_vase_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_hit));
        right_vase_box.setPosition((int)(GlobalVariables.xScale_factor*555),(int)(GlobalVariables.yScale_factor*215));
        right_vase_box.setVisible(false);

        carpet_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.mat_hit));
        carpet_box.setPosition((int)(GlobalVariables.xScale_factor*280),(int)(GlobalVariables.yScale_factor*385));
        carpet_box.setVisible(false);

        letter_box=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.letterbox_hit));
        letter_box.setPosition((int)(GlobalVariables.xScale_factor*205),(int)(GlobalVariables.yScale_factor*40));
        letter_box.setVisible(false);

        rand_key_object = new Random().nextInt(4);

//        vase_boxSprite=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_sprite_min));
//        vase_boxSprite.setVisible(false);
//        carpet_boxSprite=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.under_mat_sprite_min));
//        carpet_boxSprite.setVisible(false);
//        letter_boxSprite=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.letterbox_sprite_min));
//        letter_boxSprite.setVisible(false);
        this.popUpInstruction = new PopUpInstruction(R.drawable.popupbg, "Click on various objects to search your key");

        Bitmap temp_vaseR=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_sprite_min);
        vase_boxSpriteRight = new ShahSprite(temp_vaseR,temp_vaseR.getWidth()/3,temp_vaseR.getHeight());
        vase_boxSpriteRight.setPosition(0, height - vase_boxSpriteRight.getHeight()-popUpInstruction.mess_bgImage.getHeight());

        Bitmap temp_vaseL=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.vase_sprite_min);
        vase_boxSpriteLeft = new ShahSprite(temp_vaseL,temp_vaseL.getWidth()/3,temp_vaseL.getHeight());
        vase_boxSpriteLeft.setPosition(0, height - vase_boxSpriteLeft.getHeight()-popUpInstruction.mess_bgImage.getHeight());


        Bitmap temp_carpet=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.under_mat_sprite_min);
        carpet_boxSprite = new ShahSprite(temp_carpet,temp_carpet.getWidth()/3,temp_carpet.getHeight());
        carpet_boxSprite.setPosition(0, height - carpet_boxSprite.getHeight()-popUpInstruction.mess_bgImage.getHeight());

        Bitmap temp_letter=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.letterbox_sprite_min);
        letter_boxSprite = new ShahSprite(temp_letter,temp_letter.getWidth()/3,temp_letter.getHeight());
        letter_boxSprite.setPosition(0, height - letter_boxSprite.getHeight()-popUpInstruction.mess_bgImage.getHeight());

        door_prite = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.door_opening_bg));
        key_hand = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.door_opening_hand));
        key_hand.setPosition((int)(GlobalVariables.xScale_factor*400),(int)(GlobalVariables.xScale_factor*186));
        door_overlap = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.door_opening_overlap));
        door_overlap.setPosition(0,height-door_overlap.getHeight());
//        door_overlap.setVisible(false);

        //***************** Sound************
        MySound.stopAndPlay(context, new int[]{R.raw.disco_outside, R.raw.a});
        //*****************************

        key_sceneNumber =1;
        myConstant.backGround.recycle();
        myConstant.waitingStar.recycle();
    }

    @Override
    public void recycle() {
        drawSomething(myCanvas);

        bgSprite.recycle();
        vase_boxSpriteRight.recycle();
        vase_boxSpriteLeft.recycle();
        carpet_boxSprite.recycle();
        letter_boxSprite.recycle();
        left_vase_box.recycle();
        right_vase_box.recycle();
        carpet_box.recycle();
        letter_box.recycle();
        popUpInstruction.recycle();
        zoomBg.recycle();

        door_prite.recycle();
        key_hand.recycle();
        door_overlap.recycle();
    }
}
