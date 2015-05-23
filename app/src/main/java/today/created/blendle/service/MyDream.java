package today.created.blendle.service;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;

import today.created.blendle.R;

/**
 * Created by jolandaverhoef on 23/05/15.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MyDream extends DreamService {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean night_mode = sp.getBoolean("night_mode", false);
        setScreenBright(night_mode);
        // Exit dream upon user touch
        setInteractive(false);
        // Hide system UI
        setFullscreen(true);
        // Set the dream layout
        setContentView(R.layout.dream);
    }

}
