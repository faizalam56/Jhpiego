package zmq.com.jhpiego.activity;

import android.os.Bundle;
import zmq.com.jhpiego.canvas.Story3Canvas;

public class Story3 extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Story3Canvas(Story3.this,getIntent()));
    }

}
