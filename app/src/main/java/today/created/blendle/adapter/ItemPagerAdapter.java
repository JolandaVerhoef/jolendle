package today.created.blendle.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.view.ItemFragment;

/**
 * Created by jolandaverhoef on 22/05/15.
 * An adapter that shows a Blendle item on each page.
 */
public class ItemPagerAdapter extends FragmentStatePagerAdapter {
    private List<HalItemPopular> items;

    public ItemPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void setItems(List<HalItemPopular> items) {
        this.items = items;
    }
}
