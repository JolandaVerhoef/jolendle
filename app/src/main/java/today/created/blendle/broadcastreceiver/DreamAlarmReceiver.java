package today.created.blendle.broadcastreceiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import today.created.blendle.asynctask.LoadPopularItemTask;
import today.created.blendle.service.MyDream;

/**
 * Created by jolandaverhoef on 23/05/15.
 * Receives a broadcast whenever the Daydream alarm triggers
 * It will kick of a LoadPopularItemTask.
 */

public class DreamAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "DreamAlarmReceiver";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "Alarm received");
        MyDream myDream = MyDream.getInstance();
        if(myDream != null) {
            myDream.loadPopularItemTask = new LoadPopularItemTask(myDream);
            myDream.loadPopularItemTask.execute();
        }
    }
}