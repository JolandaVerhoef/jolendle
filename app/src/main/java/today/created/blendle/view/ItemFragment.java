package today.created.blendle.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import today.created.blendle.R;
import today.created.blendle.hal.HalItemPopular;

/**
 * Created by jolandaverhoef on 22/05/15.
 * This fragment shows the content of a Blendle item.
 */
public class ItemFragment extends Fragment {
    private HalItemPopular item;
    private TextView sectionLabel;

    public static ItemFragment newInstance(HalItemPopular item) {
        ItemFragment fragment = new ItemFragment();
        fragment.setItem(item);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View rootView,  Bundle savedInstanceState) {
        sectionLabel = (TextView) rootView.findViewById(R.id.section_label);
        updateUI();
    }

    private void setItem(HalItemPopular item) {
        this.item = item;
    }

    private void updateUI() {
        if(item != null) {
            sectionLabel.setText(item.getManifest().body().get(0).content);
        } else {
            sectionLabel.setText(R.string.hello_world);
        }
    }
}