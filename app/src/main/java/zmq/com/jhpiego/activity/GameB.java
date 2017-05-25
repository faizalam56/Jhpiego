package zmq.com.jhpiego.activity;

import android.os.Bundle;

import zmq.com.jhpiego.canvas.GameBCanvas;

public class GameB extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameBCanvas(GameB.this,getIntent()));
    }

}
