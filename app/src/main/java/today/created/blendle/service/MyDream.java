package today.created.blendle.service;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.widget.ImageView;
import android.widget.TextView;

import today.created.blendle.R;
import today.created.blendle.asynctask.LoadPopularItemTask;
import today.created.blendle.hal.HalItemPopular;

/**
 * Created by jolandaverhoef on 23/05/15.
 * Android Daydream that shows the most popular item while device is sleeping.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MyDream extends DreamService {

    private PendingIntent alarmIntent;
    private LoadPopularItemTask loadPopularItemTask;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean night_mode = sp.getBoolean("night_mode", false);
        setScreenBright(night_mode);
        // Exit dream upon user touch
        setInteractive(false);
        // Set the dream layout
        setContentView(R.layout.dream);
    }

    @Override
    public void onDreamingStarted() {
        loadPopularItemTask = new LoadPopularItemTask(this);
        loadPopularItemTask.execute();
        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long start = System.currentTimeMillis() + interval;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(alarmType, start, interval, alarmIntent);
    }

    @Override
    public void onDreamingStopped() {
        alarmIntent.cancel();
        loadPopularItemTask.cancel(true);
    }

    public void updateUI(HalItemPopular item, Bitmap mImage) {
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(item.getManifest().body().get(0).content);
        ImageView imgView = (ImageView) findViewById(R.id.featured_image);
        imgView.setImageBitmap(mImage);
    }

    private class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadPopularItemTask = new LoadPopularItemTask(MyDream.this);
            loadPopularItemTask.execute();
        }
    }
}
