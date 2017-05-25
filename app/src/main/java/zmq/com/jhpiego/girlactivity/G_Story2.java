package zmq.com.jhpiego.girlactivity;

import android.os.Bundle;

import zmq.com.jhpiego.activity.MyActivity;
import zmq.com.jhpiego.girlcanvas.G_Story2Canvas;

/**
 * Created by zmq161 on 22/8/15.
 */
public class G_Story2 extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new G_Story2Canvas(G_Story2.this,getIntent()));
    }
    @Override
    protected void onStop() {
        // finish();
        super.onStop();
    }

}