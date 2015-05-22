package today.created.blendle.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import today.created.blendle.R;

/**
 * Created by jolandaverhoef on 22/05/15.
 * This fragment shows the content of a Blendle item.
 */
public class ItemFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ItemFragment newInstance(int sectionNumber) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View rootView,  Bundle savedInstanceState) {
        TextView sectionLabel = (TextView) rootView.findViewById(R.id.section_label);
        int sectionTextResource;
        switch((int)this.getArguments().get(ARG_SECTION_NUMBER)) {
            case 1: sectionTextResource = R.string.title_section1; break;
            case 2: sectionTextResource = R.string.title_section2; break;
            case 3: sectionTextResource = R.string.title_section3; break;
            default: sectionTextResource = R.string.hello_world;
        }
        sectionLabel.setText(sectionTextResource);
    }
}