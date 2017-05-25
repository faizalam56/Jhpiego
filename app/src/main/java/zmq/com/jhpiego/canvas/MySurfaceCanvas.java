package zmq.com.jhpiego.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ZMQ154 on 14/4/2015.
 */
public abstract class MySurfaceCanvas extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    protected int sleepTime=10;
    public MySurfaceCanvas(Context context) {
        super(context);
    }
 @Override
    abstract protected void onDraw(Canvas g);
}
