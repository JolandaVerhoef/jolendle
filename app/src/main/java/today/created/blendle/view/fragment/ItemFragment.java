package today.created.blendle.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import today.created.blendle.R;
import today.created.blendle.hal.HalContent;
import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.view.customview.BodyPartTextView;

/**
 * Created by jolandaverhoef on 22/05/15.
 * This fragment shows the content of a Blendle item.
 */
public class ItemFragment extends Fragment {
    private HalItemPopular item;

    public static ItemFragment newInstance(HalItemPopular item) {
        ItemFragment fragment = new ItemFragment();
        fragment.setItem(item);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ViewGroup tvContainer = (ViewGroup) v.findViewById(R.id.container);
        if(item != null) {
            for (HalContent bodyPart : item.getManifest().body()) {
                BodyPartTextView textView = new BodyPartTextView(container.getContext());
                textView.setBodyPart(bodyPart);
                tvContainer.addView(textView);
            }
        }
        return v;
    }

    private void setItem(HalItemPopular item) {
        this.item = item;
    }
}