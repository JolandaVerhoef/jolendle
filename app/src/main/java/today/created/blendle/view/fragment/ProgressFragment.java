package today.created.blendle.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import today.created.blendle.R;

/**
 * Created by jolandaverhoef on 22/05/15.
 * This fragment shows a loading page while downloading new Blendle items.
 */
public class ProgressFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }
}