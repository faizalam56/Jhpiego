package zmq.com.jhpiego.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zmq161 on 1/9/15.
 */
public class InnerRecevier extends BroadcastReceiver {
    final String SYSTEM_DIALOG_REASON_KEY = "reason";
    final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                Log.e("cast", "action:" + action + ",reason:" + reason);
                Toast.makeText(context, "Home Button...", Toast.LENGTH_LONG).show();
//                if (mListener != null) {
//                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                        mListener.onHomePressed();
//                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//                        mListener.onHomeLongPressed();
//                    }
//                }
            }
        }
    }
}