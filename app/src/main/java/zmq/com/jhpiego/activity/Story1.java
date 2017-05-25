package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.Story1Canvas;


public class Story1 extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Story1Canvas(Story1.this,getIntent()));
    }

    @Override
    protected void onStop() {
        //finish();
        super.onStop();
    }
}
