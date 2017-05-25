package zmq.com.jhpiego.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ZMQ154 on 7/4/2015.
 */
public class MyActivity extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //System.out.println(getClass().getSimpleName() +" onResume.........");
        Log.d("MyTag",""+getClass().getSimpleName() +" onResume.........");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //System.out.println(getClass().getSimpleName() +" onStart.........");
        Log.d("MyTag",""+getClass().getSimpleName() +" onStart.........");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MyTag",""+getClass().getSimpleName() +" onRestart.........");
    }

    @Override
    protected void onStop() {
        //finish();
        super.onStop();
        //System.out.println(getClass().getSimpleName() +" Stop Activity.........");
        Log.d("MyTag",""+getClass().getSimpleName() +" Stop Activity.........");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.out.println(getClass().getSimpleName() +" Destroy Activity.........");
        Log.d("MyTag",""+getClass().getSimpleName() +" Destroy Activity.........");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //System.out.println(getClass().getSimpleName() +" Pause Activity.........");
        Log.d("MyTag",""+getClass().getSimpleName() +" Pause Activity.........");
    }

    @Override
    protected void onUserLeaveHint() {
        //System.out.println(getClass().getSimpleName() +" onUserLeaveHint .........");
        super.onUserLeaveHint();
    }

    @Override
    public void onBackPressed() {
        //GlobalVariables.isHomePressed=true;
        //System.out.println(getClass().getSimpleName() +" onBackPressed .........");
        Log.d("MyTag",""+getClass().getSimpleName() +" onBackPressed.........");
        //GlobalVariables.initialize();
        this.setResult(007, new Intent());
        super.onBackPressed();
    }


}
