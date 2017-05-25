package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 15/4/2015.
 */
public class PopUpInstruction extends Message{
    ShahSprite animChar;
    public PopUpInstruction(int resId, String messString) {
        super(resId, messString);
        super.mess_bgImage.setPosition(0, GlobalVariables.height - mess_bgImage.getHeight());
        animChar = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.messenger));
//        animChar.setPosition(10, (mess_bgImage.getY()+mess_bgImage.getHeight()/2)-animChar.getHeight());
        animChar.setPosition(10, (mess_bgImage.getY()+mess_bgImage.getHeight())-(animChar.getHeight()-(int)GlobalVariables.yScale_factor*10));
//        this.setMessLinePixelWidth(mess_bgImage.getWidth()-(animChar.getX()+animChar.getWidth()+messPadding));
        this.setMessLinePixelWidth(mess_bgImage.getWidth()-(2*messPadding));
        this.setMessString(messString);
//        this.setMessTextPosition(animChar.getX()+animChar.getWidth()+messPadding/2,mess_bgImage.getY()+mess_bgImage.getHeight()/4);
        this.setMessTextPosition(5,mess_bgImage.getY()+mess_bgImage.getHeight()/4);

        animChar.setVisible(false);
//        this.setMessPosX(200);
//        this.setMessPosY(mess_bgImage.getY()+mess_bgImage.getHeight()/2);
    }

    @Override
    public void recycle() {
        super.recycle();
        if (!animChar.sourceImage.isRecycled()) {
            animChar.sourceImage.recycle();
        }
    }

    @Override
    public void update(Canvas g, Paint paint) {
        super.update(g, paint);
        if(visible)
        animChar.paint(g,null);
    }

}
