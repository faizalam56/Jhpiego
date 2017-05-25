package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by zmq161 on 13/5/15.
 */
public class PopUpMessageForMyth extends  Message {

    public ShahSprite actionButton1,actionButton2,actionButton3;

    public PopUpMessageForMyth(int resId, String messString,int btn1ResId,int btn2ResId,int btn3ResId) {
        super(resId, messString);
//        mess_bgImage.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);
        actionButton1=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, btn1ResId));
        actionButton2=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, btn2ResId));
        actionButton3=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, btn3ResId));
        this.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);

    }

    public void setPosition(int xPos, int yPos) {
//        mess_bgImage.setPosition(xPos,yPos);
        super.setPosition(xPos,yPos);
        actionButton1.setPosition(mess_bgImage.getX()+actionButton1.getWidth()/2-actionButton1.getWidth()/3,yPos+mess_bgImage.getHeight()-actionButton1.getHeight());
        actionButton2.setPosition(mess_bgImage.getX()+mess_bgImage.getWidth()/3+actionButton2.getWidth()/2-actionButton1.getWidth()/3,yPos+mess_bgImage.getHeight()-actionButton2.getHeight());
        actionButton3.setPosition(mess_bgImage.getX()+2*mess_bgImage.getWidth()/3+actionButton3.getWidth()/2-actionButton1.getWidth()/3,yPos+mess_bgImage.getHeight()-actionButton3.getHeight());
//        this.setMessTextPosition(xPos+this.messPadding/2,yPos+this.messPadding/2);
    }

    @Override
    public void update(Canvas g, Paint paint) {
        super.update(g,paint);
        if(visible){
            actionButton1.paint(g,null);
            actionButton2.paint(g,null);
            actionButton3.paint(g,null);
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
        if (!actionButton3.sourceImage.isRecycled()){
            actionButton3.sourceImage.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
