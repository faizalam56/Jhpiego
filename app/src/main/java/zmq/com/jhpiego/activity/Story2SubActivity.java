
//Story2SubActivity
package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.Story1Canvas;
import zmq.com.jhpiego.canvas.Story2SubActivityCanvas;

/**
 * Created by Faiyaz on 14/04/15.
 */
public class Story2SubActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Story2SubActivityCanvas(Story2SubActivity.this,getIntent()));
    }
}
