package zmq.com.jhpiego.girlactivity;

import android.os.Bundle;

import zmq.com.jhpiego.activity.MyActivity;
import zmq.com.jhpiego.girlcanvas.G_Story2GetHotHeavyCanvas;

/**
 * Created by zmq161 on 24/8/15.
 */
public class G_Story2GetHotHeavy extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new G_Story2GetHotHeavyCanvas(G_Story2GetHotHeavy.this,getIntent()));
    }
}