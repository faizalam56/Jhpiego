package zmq.com.jhpiego.girlactivity;

import android.os.Bundle;

import zmq.com.jhpiego.activity.MyActivity;
import zmq.com.jhpiego.girlcanvas.G_Scene4_5_Canvas;

/**
 * Created by zmq161 on 24/8/15.
 */
public class G_Scene4_5_Activity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new G_Scene4_5_Canvas(G_Scene4_5_Activity.this,getIntent()));
    }
}
