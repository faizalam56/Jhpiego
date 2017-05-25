package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.Scene4_5_Canvas;

/**
 * Created by zmq161 on 23/5/15.
 */
public class Scene4_5_Activity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Scene4_5_Canvas(Scene4_5_Activity.this,getIntent()));
    }
}
