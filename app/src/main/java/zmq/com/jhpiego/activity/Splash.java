package zmq.com.jhpiego.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.app.GoogleAnalyticApp;
import zmq.com.jhpiego.database.MyDatabaseAdapter;
import zmq.com.jhpiego.network.VolleyRequest;
import zmq.com.jhpiego.preferences.MyPreference;
import zmq.com.jhpiego.preferences.MyUtility;


public class Splash extends MyActivity {
    PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tracker t = ((GoogleAnalyticApp) getApplication()).getTracker(GoogleAnalyticApp.TrackerName.APP_TRACKER);
        t.setScreenName("Game Home");
        t.send(new HitBuilders.AppViewBuilder().build());

        setContentView(R.layout.activity_splash);

//        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Play");
//        this.mWakeLock.acquire();

        // Analytics Start.....

        MyDatabaseAdapter adapter  = new MyDatabaseAdapter(this);
        adapter.openToRead();
        Cursor cursor = adapter.selectData(MyDatabaseAdapter.DATABASE_TABLE_1, null, null, null, null, null);
        int totalRecords = cursor.getCount();

       if(totalRecords > 0 && isConnectingToInternet()){
           new VolleyRequest(this);
       }
        adapter.close();


        MyPreference.saveStringKeyValue(Splash.this,"date", MyUtility.getDate());
        MyPreference.saveStringKeyValue(Splash.this,"launchTime", MyUtility.getTime());

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.layoutsplash);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Splash.this,MenuScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

}
