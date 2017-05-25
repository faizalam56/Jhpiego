package zmq.com.jhpiego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.utility.MySound;


public class About extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageButton aboutImageButton= (ImageButton) findViewById(R.id.backbtn);
        aboutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySound.playSoundOnDemand(About.this, R.raw.tap_sound);

                Intent intent =new Intent();
                intent.setClass(About.this,MenuScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
