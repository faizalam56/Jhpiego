package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.Story2GetHotHeavyCanvas;
import zmq.com.jhpiego.canvas.Story2SubActivityCanvas;

/**
 * Created by zmq161 on 22/5/15.
 */
public class Story2GetHotHeavy extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Story2GetHotHeavyCanvas(Story2GetHotHeavy.this,getIntent()));
    }
}