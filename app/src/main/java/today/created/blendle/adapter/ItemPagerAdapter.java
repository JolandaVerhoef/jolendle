package today.created.blendle.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Locale;

import today.created.blendle.R;
import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.view.ItemFragment;

/**
 * Created by jolandaverhoef on 22/05/15.
 * An adapter that shows a Blendle item on each page.
 */
public class ItemPagerAdapter extends FragmentPagerAdapter {
    private Context appContext;
    private List<HalItemPopular> items;

    public ItemPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        appContext = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return appContext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return appContext.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return appContext.getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}
