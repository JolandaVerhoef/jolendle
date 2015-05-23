package today.created.blendle.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import today.created.blendle.R;
import today.created.blendle.Webservice;
import today.created.blendle.adapter.ItemPagerAdapter;
import today.created.blendle.hal.HalItemsPopular;
import today.created.blendle.hal.Link;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private Link nextPage;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private ItemPagerAdapter mItemPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mItemPagerAdapter = new ItemPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mItemPagerAdapter.getCount() - 1 && mItemPagerAdapter.isNotLoadingData()) {
                    mItemPagerAdapter.setLoadingData(true);
                    new TestTask().execute();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setAdapter(mItemPagerAdapter);

        if(daydreamDisabled()) showDaydreamDialog();
        new TestTask().execute();
    }

    private void showDaydreamDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        new DaydreamDialogFragment().show(ft, "dialog");
    }

    private boolean daydreamDisabled() {
        Boolean daydreamEnabled = Settings.Secure.getInt(getContentResolver(),
                "screensaver_enabled", 0) == 1;
        Log.d(TAG, "Daydream enabled: " + daydreamEnabled);
        if (!daydreamEnabled) return true;
        String componentString = Settings.Secure.getString(getContentResolver(),
                "screensaver_components");
        return componentString == null || !componentString.contains(getApplicationContext().getPackageName());
    }

    private class TestTask extends AsyncTask<Void, Void, HalItemsPopular> {
        protected void onPreExecute() {
            mItemPagerAdapter.setLoadingData(true);
        }
        protected HalItemsPopular doInBackground(Void... voids) {
            try {
                final Webservice webservice = new Webservice();
                if(nextPage == null) {
                    return webservice.getPopularItems();
                } else {
                    return webservice.getNextPopularItems(nextPage);
                }
            } catch (IOException e) {
                Log.e("MainActivity", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(HalItemsPopular popularItems) {
            if(popularItems != null) {
                mItemPagerAdapter.addItems(popularItems.items());
                nextPage = popularItems.links.next;
            }
            mItemPagerAdapter.setLoadingData(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static class DaydreamDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_daydream_title)
                    .setMessage(R.string.dialog_daydream_message)
                    .setPositiveButton(R.string.dialog_daydream_enable, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                intent = new Intent(Settings.ACTION_DREAM_SETTINGS);
                            } else {
                                intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                            }
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.cancel();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
