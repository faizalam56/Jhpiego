package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.AfterMathCanvas;
import zmq.com.jhpiego.canvas.Story1Canvas;


public class AfterMath extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new AfterMathCanvas(AfterMath.this,getIntent()));
    }
}
