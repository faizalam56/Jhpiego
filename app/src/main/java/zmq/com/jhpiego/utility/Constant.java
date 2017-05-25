package zmq.com.jhpiego.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.sprite.ShahSprite;

/**
 * Created by zmq161 on 28/5/15.
 */
public class Constant {

    public Bitmap backGround,waitingStar;
    Matrix matrix;
    int rotationAngle=0;
    int threadSpeedController=0;

    public Constant(Context context) {
        matrix=new Matrix();

        backGround=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.scene1bg);
        waitingStar=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.wait);

    }

    public void drawImageWithRotation(Canvas canvas){

        threadSpeedController++;
        if(threadSpeedController>999)threadSpeedController=0;

        if(threadSpeedController%2==0){         // controlling main thread speed
            if(rotationAngle>=360)rotationAngle=0;
            else rotationAngle+=22.5;
        }

        matrix.preTranslate(canvas.getWidth()/2-waitingStar.getWidth()/2,canvas.getHeight()/2-waitingStar.getHeight()/2);
        matrix.setRotate(rotationAngle,waitingStar.getWidth()/2,waitingStar.getHeight()/2);
        matrix.postTranslate(canvas.getWidth()/2-waitingStar.getWidth()/2,canvas.getHeight()/2-waitingStar.getHeight()/2);
        canvas.drawBitmap(waitingStar, matrix, null);

    }
}
