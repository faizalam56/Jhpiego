package zmq.com.jhpiego.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zmq.com.jhpiego.utility.GlobalVariables;

/**
 * Created by shahnawazali on 14/04/15.
 */
public abstract class BaseSurface extends SurfaceView implements SurfaceHolder.Callback{

    public GameThread gameThread;
    public SurfaceHolder surfaceHolder;
    public int sleepTime= GlobalVariables.sleepTime;
    public Context context;
    public int delayCounter;
    public boolean isDelay=true;
    private Long lastMiliSeconds=System.currentTimeMillis();
    public boolean messDisplay=true;
    public boolean popMessDisplay;
    public Paint paint;
    public int width=GlobalVariables.width;
    public int height=GlobalVariables.height;
    public boolean destroyed=false;

    public BaseSurface(Context context) {
        super(context);
        this.context=context;
        paint=GlobalVariables.paint;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread(surfaceHolder,this);
    }

    protected void delayCount(){
        long currentMilisecond=System.currentTimeMillis();

        long temp=(currentMilisecond-lastMiliSeconds)/1000;
        if(temp>=1){
            lastMiliSeconds=currentMilisecond;
            delayCounter=delayCounter+1;
        }

//     delayCounter=delayCounter+((currentMilisecond-lastMiliSeconds)/1000>0?1:0);
    }

   abstract protected void drawSomething(Canvas g) ;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(gameThread==null){
            gameThread = new GameThread(surfaceHolder,this);
        }
        gameThread .start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        {
            gameThread.running=false;
            destroyed=true;
            System.out.println(" BaseSurface surfaceDestroyed Called...");
        }
    }

    public class GameThread extends Thread{
        SurfaceHolder _suHolder;
        BaseSurface _myMycanvas;
        public boolean running=true;
        public GameThread(SurfaceHolder surfaceHolder,BaseSurface mycanvas) {
            // TODO Auto-generated constructor stub
            _suHolder = surfaceHolder;
            _myMycanvas = mycanvas;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
//			super.run();
            Canvas canvas;
            while (running) {
                canvas =null;
                try {
                    canvas =_suHolder.lockCanvas(null);
                    synchronized (_suHolder) {
                        if(running){
                            _myMycanvas.drawSomething(canvas);
                        }
                    }
                    try {
                        Thread.sleep(GlobalVariables.sleepTime);
//                        threadTimeController++;
//                        if(threadTimeController>999)threadTimeController=1;
                    } catch (InterruptedException e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } finally{
                    //TODO: handle exception
                    if(canvas!=null)
                        _suHolder.unlockCanvasAndPost(canvas);
                }

            }
        }

    }
}
