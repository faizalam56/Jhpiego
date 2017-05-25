package zmq.com.jhpiego.girlactivity;

import android.os.Bundle;

import zmq.com.jhpiego.activity.MyActivity;
import zmq.com.jhpiego.girlcanvas.G_Story2SubActivityCanvas;

/**
 * Created by zmq161 on 22/8/15.
 */
public class G_Story2SubActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new G_Story2SubActivityCanvas(G_Story2SubActivity.this,getIntent()));
    }
}