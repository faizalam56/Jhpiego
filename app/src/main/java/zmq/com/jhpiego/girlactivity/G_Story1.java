package zmq.com.jhpiego.girlactivity;

/**
 * Created by zmq161 on 5/6/15.
 */
import android.os.Bundle;

import zmq.com.jhpiego.activity.MyActivity;
import zmq.com.jhpiego.girlcanvas.G_Story1Canvas;


public class G_Story1 extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new G_Story1Canvas(G_Story1.this,getIntent()));
    }


}
