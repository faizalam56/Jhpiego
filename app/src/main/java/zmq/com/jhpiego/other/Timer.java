package zmq.com.jhpiego.other;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.StoryAction;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

public class Timer implements StoryAction{
    public ShahSprite bgImage,timerImage;
    private boolean visible;

    public Timer(){
        bgImage=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        bgImage.setPosition(-(int)(GlobalVariables.xScale_factor*114),0);
        //bgImage.setVisible(false);

        timerImage=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.seek_help));
        timerImage.setPosition(GlobalVariables.width/2-timerImage.getWidth()/2,GlobalVariables.height/2-timerImage.getHeight()/2);
        //timerImage.setVisible(false);
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean isVisible() {
        return visible;
    }
    @Override
    public void recycle() {
        bgImage.recycle();
        timerImage.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void update(Canvas g, Paint paint) {
     if(visible){
         bgImage.paint(g,null);
         timerImage.paint(g,null);
     }
    }
}

////***********Helper Count Down**********
//countDownStatus=true;
//        if(whichDecisionPoint!=null){
//        whichDecisionPoint.recycle();
//        }
//        countDown=100;
//        whichDecisionPoint=dp_roomInviteIn_down;
////*************************************


////**********On Timer Click Option********
//if(countDownStatus && timer.isVisible()&& timer.timerImage.getDstRect().intersect(touchrecF)){
//        timer.setVisible(false);
//        //popUpInstruction.setVisible(false);
//        countDownStatus=false;
//        countDown=100;
//        }
////***************************************