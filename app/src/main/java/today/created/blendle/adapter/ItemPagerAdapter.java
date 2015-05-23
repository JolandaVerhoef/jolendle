package today.created.blendle.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.view.ItemFragment;
import today.created.blendle.view.ProgressFragment;

/**
 * Created by jolandaverhoef on 22/05/15.
 * An adapter that shows a Blendle item on each page.
 * It is a lazy loading list that automatically retrieves new items when the user is almost at the
 * end of the current list. When the user tries to view an item that has not yet loaded, the app
 * shows a progress fragment.
 *
 * To make sure the progress fragment is replaced on data load, we need to keep track of it.
 * Inspiration: http://stackoverflow.com/questions/15357581
 */
public class ItemPagerAdapter extends FragmentStatePagerAdapter {
    private final List<HalItemPopular> items;
    private final SparseArray<WeakReference<Fragment>> currentFragments = new SparseArray<>();
    private ProgressFragment progressFragment;
    private boolean isLoadingData;

    public ItemPagerAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object item = super.instantiateItem(container, position);
        currentFragments.append(position, new WeakReference<>((Fragment) item));
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        currentFragments.put(position, null);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        if (isPositionOfProgressFragment(position)) {
            return getProgressFragment();
        }

        WeakReference<Fragment> fragmentRef = currentFragments.get(position);
        if (fragmentRef == null) {
            return ItemFragment.newInstance(items.get(position));
        }

        return fragmentRef.get();
    }

    private boolean isPositionOfProgressFragment(int position) {
        return isLoadingData && position == items.size();
    }

    @Override
    public int getCount() {
        int size = items.size();
        return isLoadingData ? ++size : size;
    }

    @Override
    public int getItemPosition(Object item) {
        if (item.equals(progressFragment) && !isLoadingData) {
            return PagerAdapter.POSITION_NONE;
        }
        return PagerAdapter.POSITION_UNCHANGED;
    }

    public void addItems(List<HalItemPopular> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setLoadingData(boolean isLoadingData) {
        this.isLoadingData = isLoadingData;
        notifyDataSetChanged();
    }

    public boolean isNotLoadingData() {
        return !isLoadingData;
    }

    private Fragment getProgressFragment() {
        if (progressFragment == null) {
            progressFragment = new ProgressFragment();
        }
        return progressFragment;
    }
}
