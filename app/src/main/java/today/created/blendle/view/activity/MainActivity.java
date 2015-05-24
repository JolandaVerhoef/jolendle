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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import today.created.blendle.R;
import today.created.blendle.Webservice;
import today.created.blendle.adapter.ItemPagerAdapter;
import today.created.blendle.hal.HalItemsPopular;
import today.created.blendle.hal.Link;

/**
 * Central activity that shows the list of popular items.
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private Link nextPage;
    private ItemPagerAdapter mItemPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemPagerAdapter = new ItemPagerAdapter(getFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mItemPagerAdapter.getCount() - 1 && mItemPagerAdapter.isNotLoadingData()) {
                    new LoadPopularItemsTask().execute();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setAdapter(mItemPagerAdapter);
        mViewPager.setPageTransformer(true, new MyPageTransformer());

        if(daydreamDisabled()) showDaydreamDialog();
        new LoadPopularItemsTask().execute();
    }

    // Ask the user to enable daydreaming for this app
    private void showDaydreamDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new DaydreamDialogFragment().show(ft, "dialog");
    }

    // Did the user already enable daydreaming for this app?
    private boolean daydreamDisabled() {
        Boolean daydreamEnabled = Settings.Secure.getInt(getContentResolver(),
                "screensaver_enabled", 0) == 1;
        Log.d(TAG, "Daydream enabled: " + daydreamEnabled);
        if (!daydreamEnabled) return true;
        String componentString = Settings.Secure.getString(getContentResolver(),
                "screensaver_components");
        return componentString == null || !componentString.contains(getApplicationContext().getPackageName());
    }

    private class LoadPopularItemsTask extends AsyncTask<Void, Void, HalItemsPopular> {
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

    // Handles animation when scrolling between viewpager pages.
    private class MyPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position <= 1) { // [-1,1]
                View mTitle = view.findViewById(R.id.title);
                if(mTitle != null) {
                    float titleHeight = mTitle.getHeight();
                    mTitle.setTranslationY(Math.abs(position) * titleHeight);
                    mTitle.setAlpha(1-Math.abs(position));
                }
            }
        }

    }
}
