package zmq.com.jhpiego.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by zmq161 on 31/8/15.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
             MySound.stopSound(context,GlobalVariables.Audio_File_Name);
             Toast.makeText(context,"Incoming Call...",Toast.LENGTH_LONG).show();
        }
    }
}
