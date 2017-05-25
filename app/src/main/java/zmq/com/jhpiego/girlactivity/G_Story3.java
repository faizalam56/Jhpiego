package zmq.com.jhpiego.girlactivity;

import android.os.Bundle;

import zmq.com.jhpiego.activity.MyActivity;
import zmq.com.jhpiego.girlcanvas.G_Story3Canvas;

/**
 * Created by zmq161 on 24/8/15.
 */
public class G_Story3 extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new G_Story3Canvas(G_Story3.this,getIntent()));
    }
}

