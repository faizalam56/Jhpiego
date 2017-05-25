package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.StringDraw;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 16/4/2015.
 */
public class GameChangerMessage extends Message{

    public ShahSprite fadedBg;
    public ShahSprite actionButton;
    public ShahSprite animChar;
    public GameChangerMessage(int resId, String messString) {
        super(resId, messString);
        mess_bgImage.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);

        actionButton = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.continue1));
        actionButton.setPosition(mess_bgImage.getX() + mess_bgImage.getWidth()/2-actionButton.getWidth()/2, mess_bgImage.getY()+mess_bgImage.getHeight()-actionButton.getHeight());

        fadedBg=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1_faded_bg));

        animChar=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.game_changer));
        animChar.setPosition(mess_bgImage.getX(),mess_bgImage.getY()+mess_bgImage.getHeight()/2-animChar.getHeight()/2);

        this.setMessLinePixelWidth(mess_bgImage.getWidth()-(animChar.getWidth()));
        this.setMessString(messString);
        this.setMessTextPosition(animChar.getX()+animChar.getWidth()+5,animChar.getY()+10);
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
