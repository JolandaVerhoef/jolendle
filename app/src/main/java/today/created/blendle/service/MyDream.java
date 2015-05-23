package today.created.blendle.service;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.widget.ImageView;

import today.created.blendle.R;
import today.created.blendle.asynctask.LoadPopularItemTask;
import today.created.blendle.broadcastreceiver.DreamAlarmReceiver;
import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.view.customview.BodyPartTextView;

/**
 * Created by jolandaverhoef on 23/05/15.
 * Android Daydream that shows the most popular item while device is sleeping.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MyDream extends DreamService {
    private static final String TAG = "MyDream";
    private static MyDream instance;

    public static MyDream getInstance() {
        return instance;
    }

    private PendingIntent alarmIntent;
    public LoadPopularItemTask loadPopularItemTask;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        instance = this;
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
        long start = SystemClock.elapsedRealtime() + interval;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DreamAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Log.v(TAG, "planning alarm");
        alarmManager.setInexactRepeating(alarmType, start, interval, alarmIntent);
    }

    @Override
    public void onDreamingStopped() {
        Log.v(TAG, "stopped dreaming");
        alarmIntent.cancel();
        loadPopularItemTask.cancel(true);
    }

    @Override
    public void onDetachedFromWindow() {
        instance = null;
    }

    public void updateUI(HalItemPopular item, Bitmap mImage) {
        Log.v(TAG, "Update UI");
        BodyPartTextView textView = (BodyPartTextView) findViewById(R.id.title);
        textView.setBodyPart(item.getManifest().getTitle());
        ImageView imgView = (ImageView) findViewById(R.id.featured_image);
        imgView.setImageBitmap(mImage);
    }
}
