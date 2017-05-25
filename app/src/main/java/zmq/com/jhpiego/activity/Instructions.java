package zmq.com.jhpiego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.utility.MySound;


public class Instructions extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        ImageButton aboutImageButton= (ImageButton) findViewById(R.id.backbtn);
        aboutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySound.playSoundOnDemand(Instructions.this, R.raw.tap_sound);

                Intent intent =new Intent();
                intent.setClass(Instructions.this,MenuScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
