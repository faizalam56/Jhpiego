package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 16/4/2015.
 */
/* With two Option */

public class PopUpMessage extends  Message {

    public ShahSprite actionButton1,actionButton2;

    public PopUpMessage(int resId, String messString,int btn1ResId,int btn2ResId) {
        super(resId, messString);
//        mess_bgImage.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);
        actionButton1=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, btn1ResId));
        actionButton2=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, btn2ResId));
       this.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);

    }

    public void setPosition(int xPos, int yPos) {
        super.setPosition(xPos,yPos);
        actionButton1.setPosition(xPos+mess_bgImage.getWidth()/4-actionButton1.getWidth()/2,yPos+mess_bgImage.getHeight()-actionButton1.getHeight());
        actionButton2.setPosition(xPos+3*mess_bgImage.getWidth()/4-actionButton2.getWidth()/2,yPos+mess_bgImage.getHeight()-actionButton2.getHeight());
//        this.setMessTextPosition(xPos+this.messPadding/2,yPos+this.messPadding/2);
    }
    public void setPositionOnDemand(){
       actionButton1.setPosition(actionButton1.getX(),mess_bgImage.getY()+mess_bgImage.getHeight()/2-actionButton1.getHeight()/2);
       actionButton2.setPosition(actionButton2.getX(),mess_bgImage.getY()+mess_bgImage.getHeight()/2-actionButton1.getHeight()/2);
    }
    @Override
    public void update(Canvas g, Paint paint) {
        super.update(g,paint);
        if(visible){
            actionButton1.paint(g,null);
            actionButton2.paint(g,null);
        }
    }
    @Override
    public void recycle() {
        super.recycle();
        if (!actionButton1.sourceImage.isRecycled()){
            actionButton1.sourceImage.recycle();
        }
        if (!actionButton2.sourceImage.isRecycled()){
            actionButton2.sourceImage.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
