package today.created.blendle.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

        new TestTask().execute();
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
}
