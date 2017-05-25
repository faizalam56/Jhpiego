package zmq.com.jhpiego.other;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.StoryAction;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by zmq161 on 26/6/15.
 */
public class GameChangerEffect implements StoryAction  {

    public ShahSprite gameChangerImageOpacity,fadeIn_Out_Image ;
    public boolean visible=false;
    private int []opacity={0,75,50,150,125,215};//{0,50,25,100,50,141};//{0,30,15,60,30,85};
    private int count=0;
    private boolean incremnt_decremnt=true;
    private boolean isFadeIn=false,isFadeOut=false;

    public void setFadeIn(boolean isFadeIn) {
        this.isFadeIn = isFadeIn;
    }

    public void setFadeOut(boolean isFadeOut) {
        this.isFadeOut = isFadeOut;
    }



    public GameChangerEffect(){
        gameChangerImageOpacity = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.white_box));
        gameChangerImageOpacity.setVisible(true);

        fadeIn_Out_Image = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.black_box));
        fadeIn_Out_Image.setVisible(true);

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public void recycle() {
        gameChangerImageOpacity.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void update(Canvas g, Paint paint) {
          if(visible) {
              paint.setAlpha(opacity[count]);
              gameChangerImageOpacity.paint(g, paint);

              if (count < opacity.length - 1 && incremnt_decremnt) {
                  count++;
              } else {

                  if (count != 0) {
                      count--;
                      incremnt_decremnt = false;
                  } else {
                      incremnt_decremnt = true;
                  }
              }
          }


    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void fadeInOut(Canvas g,Paint paint){
       if(isFadeIn){
           //paint.setAlpha(opacity[count]);
           fadeIn_Out_Image.paint(g,paint);
//           if (count < opacity.length - 1 ) {
//               count++;
//           }
      }
        if(isFadeOut){
            paint.setAlpha(opacity[count]);
            fadeIn_Out_Image.paint(g,paint);
            if (count >0 ) {
                count--;
            }
        }
    }

}
