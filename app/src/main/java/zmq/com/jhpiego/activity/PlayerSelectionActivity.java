package zmq.com.jhpiego.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import zmq.com.jhpiego.canvas.PlayerSelectionCanvas;

/**
 * Created by zmq161 on 21/8/15.
 */
public class PlayerSelectionActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new PlayerSelectionCanvas(PlayerSelectionActivity.this, getIntent()));

    }
}
