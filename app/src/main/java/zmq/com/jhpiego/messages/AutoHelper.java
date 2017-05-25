package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 16/4/2015.
 */
public class AutoHelper extends Message{

    public ShahSprite fadedBg;
    public ShahSprite actionButton;
    public ShahSprite animChar;
    private int rand1;

    public AutoHelper(int resId, String messString) {
        super(resId, messString);
        mess_bgImage.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);

        actionButton = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.continue1));

        fadedBg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1_faded_bg));
        rand1=new Random().nextInt(2);

        int  helper_image_array[]={(rand1==0?R.drawable.patrick_stand:R.drawable.cathy_stand)};

        animChar=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, helper_image_array[0]));
        animChar.setPosition(mess_bgImage.getX(),mess_bgImage.getY());
        actionButton.setPosition(mess_bgImage.getX() + animChar.getWidth(), mess_bgImage.getY()+mess_bgImage.getHeight()-actionButton.getHeight());

        this.setMessLinePixelWidth(mess_bgImage.getWidth()-(animChar.getWidth()));
        this.setMessString(messString);
        this.setMessTextPosition((int)(GlobalVariables.xScale_factor*315),(int)(GlobalVariables.yScale_factor*155));
//        messPosX=mess_bgImage.getX()+10;
//        messPosY=mess_bgImage.getY()+20;

    }

    @Override
    public void recycle() {
        super.recycle();
        if (!fadedBg.sourceImage.isRecycled()){
            fadedBg.sourceImage.recycle();
        }
        if (!actionButton.sourceImage.isRecycled()){
            actionButton.sourceImage.recycle();
        }
        if (!animChar.sourceImage.isRecycled()){
            animChar.sourceImage.recycle();
        }
    }

    @Override
    public void update(Canvas g, Paint paint) {
        if(visible){
            fadedBg.paint(g,null);
        }
        super.update(g,paint);
        if(visible){
            animChar.paint(g,null);
            actionButton.paint(g,null);
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}
