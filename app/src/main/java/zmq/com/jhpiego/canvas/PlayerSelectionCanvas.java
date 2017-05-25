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

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.preferences.MyPreference;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.MySound;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by zmq161 on 21/8/15.
 */
public class PlayerSelectionCanvas extends BaseSurface implements Runnable, Recycler {

    private ShahSprite gameStart,player_Selection_Box_male,player_Selection_Box_female,play;
    private Intent intent;
    private int sceneNumber=-1;
    private int resultCode=999;

    public PlayerSelectionCanvas(Context context, Intent intent) {
        super(context);
        this.intent = intent;

        gameStart = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.player_selection));
        Bitmap temp_box=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.player_selection_checkbox);
        player_Selection_Box_male = new ShahSprite(temp_box,temp_box.getWidth()/2,temp_box.getHeight());
        player_Selection_Box_male.setPosition((int)(185*GlobalVariables.xScale_factor),(int)(300*GlobalVariables.yScale_factor));

        player_Selection_Box_female=new ShahSprite(temp_box,temp_box.getWidth()/2,temp_box.getHeight());
        player_Selection_Box_female.setPosition((int)(530*GlobalVariables.xScale_factor),(int)(300*GlobalVariables.yScale_factor));

        play=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.play_button));
        play.setPosition(width / 2 - play.getWidth() / 2, height - play.getHeight() - 20);
        play.setVisible(false);

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            this.recycle();
            System.out.println(" Story1Canvas surfaceDestroyed Called...");
        }
    }



    protected void drawSomething(Canvas g) {

        g.drawColor(Color.TRANSPARENT);
        switch (sceneNumber) {

            case -1:
                scene4(g);
                break;

            default:
                break;
        }

    }

    public void scene4(Canvas g){
//        MyPreference.saveStringKeyValue(context, "gender", "boy");
        gameStart.paint(g,null);
        player_Selection_Box_male.paint(g,null);
        player_Selection_Box_female.paint(g,null);
        play.paint(g,null);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MySound.playSoundOnDemand(context,R.raw.tap_sound);

        RectF touchrecF = new RectF(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
        switch (sceneNumber) {
            case -1:

                if(player_Selection_Box_male.isVisible() && player_Selection_Box_male.getDstRect().intersect(touchrecF)){
                    resultCode=999;
                    player_Selection_Box_male.setFrame(1);
                    player_Selection_Box_female.setFrame(0);
                    play.setVisible(true);
                }
                if(player_Selection_Box_female.isVisible() && player_Selection_Box_female.getDstRect().intersect(touchrecF)){
                    resultCode=888;
                    player_Selection_Box_female.setFrame(1);
                    player_Selection_Box_male.setFrame(0);
                    play.setVisible(true);
                }

                if(play.isVisible() && play.getDstRect().intersect(touchrecF)){
                    gameStart.setVisible(false);

                    String gender="M";
                    if(resultCode==888){
                        gender="F";
                    }
                    MyPreference.saveStringKeyValue(context, "gender", gender);
                    //***************** Sound************

                    MySound.playSound(context,R.raw.a);
                    GlobalVariables.Audio_File_Name=R.raw.a;
                    //*****************************
                    isDelay = false;
                    // jump to next story
                    gameThread.running = false;
                    gameThread = null;
                    this.surfaceHolder.removeCallback(this);
                    Intent temp = new Intent();

                    GlobalVariables.initialize();
                    GlobalVariables.selectedPlayer=resultCode;

                    ((Activity) context).setResult(Activity.RESULT_OK, temp);
//                    ((Activity)context).setIntent(temp);
                    ((Activity) context).finish();
                    // this.recycleImages();
                    this.recycle();


                }
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }


    @Override
    public void run() {


    }
    @Override
    public void recycle() {
        gameStart.recycle();
        player_Selection_Box_male.recycle();
        player_Selection_Box_female.recycle();
        play.recycle();

    }

}
