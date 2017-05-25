package zmq.com.jhpiego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.preferences.MyPreference;
import zmq.com.jhpiego.preferences.MyUtility;
import zmq.com.jhpiego.utility.MySound;


public class MenuScreen extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        ImageButton aboutImageButton= (ImageButton) findViewById(R.id.aboutImageButton);
        aboutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySound.playSoundOnDemand(MenuScreen.this, R.raw.tap_sound);

                Intent intent =new Intent();
                intent.setClass(MenuScreen.this,About.class);
                startActivity(intent);

//                Intent intent =new Intent();
//                intent.setClass(MenuScreen.this,ViewReport.class);
//                startActivity(intent);

            }
        });
        ImageButton instructionImageButton= (ImageButton) findViewById(R.id.instructionsImageButton);
        instructionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySound.playSoundOnDemand(MenuScreen.this, R.raw.tap_sound);

                Intent intent =new Intent();
                intent.setClass(MenuScreen.this,Instructions.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });

        ImageButton introductionImageButton= (ImageButton) findViewById(R.id.introductionImageButton);
        introductionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySound.playSoundOnDemand(MenuScreen.this, R.raw.tap_sound);

                Intent intent =new Intent();
                //intent.putExtra("switch","0");
                intent.setClass(MenuScreen.this,Introduction.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });

        ImageButton playImageButton= (ImageButton) findViewById(R.id.playImageButton);
        playImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySound.playSoundOnDemand(MenuScreen.this, R.raw.tap_sound);

                //*************** cleared Prefrences *************
              //  MyPreference.removeKeys(MenuScreen.this);
                // ***************************************************************

                MyPreference.saveStringKeyValue(MenuScreen.this, "startTime", MyUtility.getTime());
                MyPreference.saveStringKeyValue(MenuScreen.this, "sessionsId", "0");

                Intent intent =new Intent();
                intent.setClass(MenuScreen.this,Play.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public void onBackPressed() {
            //finish();
//        Intent intent = new Intent(MenuScreen.this, MenuScreen.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("EXIT", true);
//        startActivity(intent);
        //System.exit(1);
//    }
}
