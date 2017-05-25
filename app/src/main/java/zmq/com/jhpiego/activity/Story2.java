package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.Story2Canvas;

public class Story2 extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Story2Canvas(Story2.this,getIntent()));
    }
    @Override
    protected void onStop() {
       // finish();
        super.onStop();
    }

}
