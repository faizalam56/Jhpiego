package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.Recycler;
import zmq.com.jhpiego.interfaces.StoryAction;
import zmq.com.jhpiego.interfaces.TouchListner;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 10/4/2015.
 */
public class BackgroundMessage extends Message implements StoryAction {
   public ShahSprite actionButton;

    static final String logTag = "ActivitySwipeDetector";
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    public BackgroundMessage(int resId, String messString) {
        super(resId, messString);
        mess_bgImage.setPosition(GlobalVariables.width/2- mess_bgImage.getWidth()/2,GlobalVariables.height/2- mess_bgImage.getHeight()/2);
        actionButton = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.continue1));
        actionButton.setPosition(mess_bgImage.getX() + mess_bgImage.getWidth() / 2 - actionButton.getWidth() / 2,
                mess_bgImage.getY() + mess_bgImage.getHeight() - actionButton.getHeight());
        messPosX=mess_bgImage.getX()+10;
        messPosY=mess_bgImage.getY()+20;
    }

    @Override
    public void update(Canvas g, Paint paint) {
        super.update(g, paint);
        if(visible)
        actionButton.paint(g, null);
    }

    @Override
    public void recycle() {
        super.recycle();
        if (!actionButton.sourceImage.isRecycled()) {
            actionButton.sourceImage.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(GlobalVariables.context, event.getAction(), Toast.LENGTH_SHORT).show();

        switch (MotionEvent.ACTION_MASK & event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();
//                Log.i(logTag, "Vertical Swipe was only " +  " long, need at least " + MIN_DISTANCE);
                Log.i("TOUCH", "ACTION_MOVE" + 100);

            }
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
//                Toast.makeText(GlobalVariables.context, "In Down", Toast.LENGTH_SHORT).show();
//                Log.i(logTag, "Vertical Swipe was only " +  " long, need at least ACTION_DOWN" + MIN_DISTANCE);
                Log.i("TOUCH", "ACTION_DOWN" + 100);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();
                Log.i("TOUCH", "ACTION_UP" + 100);
//                Toast.makeText(GlobalVariables.context, "In Up", Toast.LENGTH_SHORT).show();
            }
            case  MotionEvent.ACTION_POINTER_UP:{
                Toast.makeText(GlobalVariables.context, "In ACTION_POINTER_UP", Toast.LENGTH_SHORT).show();
            }
            case  MotionEvent.ACTION_POINTER_DOWN:{
                Toast.makeText(GlobalVariables.context, "In ACTION_POINTER_DOWN", Toast.LENGTH_SHORT).show();
            }
//                float deltaX = downX - upX;
//                float deltaY = downY - upY;
//
//                // swipe horizontal?
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    if (Math.abs(deltaX) > MIN_DISTANCE) {
//                        // left or right
//                        if (deltaX < 0) {
//                            Toast.makeText(GlobalVariables.context, "wow", Toast.LENGTH_SHORT).show();;
//                            return true;
//                        }
//                        if (deltaX > 0) {
//                            Toast.makeText(GlobalVariables.context, "wow", Toast.LENGTH_SHORT).show();;
//                            return true;
//                        }
//                    } else {
//                        Log.i(logTag, "Horizontal Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
//                        Toast.makeText(GlobalVariables.context, "Horizontal Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE, Toast.LENGTH_SHORT).show();;
//                        return false; // We don't consume the event
//                    }
//                }
//                // swipe vertical?
//                else {
//                    if (Math.abs(deltaY) > MIN_DISTANCE) {
//                        // top or down
//                        if (deltaY < 0) {
////                            this.onDownSwipe();
//                            Toast.makeText(GlobalVariables.context, "onDownSwipe", Toast.LENGTH_SHORT).show();;
//                            return true;
//                        }
//                        if (deltaY > 0) {
////                            this.onUpSwipe();
//                            Toast.makeText(GlobalVariables.context, "onUpSwipe", Toast.LENGTH_SHORT).show();;
//                            return true;
//                        }
//                    } else {
//                        Log.i(logTag, "Vertical Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
//                        Toast.makeText(GlobalVariables.context, "Vertical Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE, Toast.LENGTH_SHORT).show();;
//                        return false; // We don't consume the event
//                    }
//                }
//
//                return true;
//            }
        }
        return true;
    }
}
