package today.created.blendle.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import today.created.blendle.R;

/**
 * Created by jolandaverhoef on 18/04/15.
 * Page that shows the preferences for the Android Daydream screen.
 * Called from Settings app.
 */
public class MyDreamSettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
