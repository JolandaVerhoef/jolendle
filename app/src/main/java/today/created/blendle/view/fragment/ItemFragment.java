package today.created.blendle.view.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import java.io.InputStream;

import today.created.blendle.R;
import today.created.blendle.hal.HalContent;
import today.created.blendle.hal.HalImage;
import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.hal.Link;
import today.created.blendle.view.customview.BodyPartTextView;

/**
 * Created by jolandaverhoef on 22/05/15.
 * This fragment shows the content of a Blendle item.
 */
public class ItemFragment extends Fragment {
    private HalItemPopular item;

    private ImageView mImageView;
    private int screenHeight;
    private ViewGroup mItemHeader;

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
        mItemHeader = (ViewGroup) v.findViewById(R.id.item_header);
        mImageView = (ImageView) v.findViewById(R.id.image);
        BodyPartTextView mTitleView = (BodyPartTextView) v.findViewById(R.id.title);
        if(item != null) {
            boolean hasFeaturedImage = item.getManifest().getFeaturedImage() != null;
            if(hasFeaturedImage) {
                new loadImageTask().execute();
            } else {
                mItemHeader.setVisibility(View.GONE);
            }
            for (HalContent bodyPart : item.getManifest().body()) {
                if(bodyPart.isTitle() && hasFeaturedImage) {
                    mTitleView.setBodyPart(bodyPart);
                } else {
                    BodyPartTextView textView = new BodyPartTextView(container.getContext());
                    textView.setBodyPart(bodyPart);
                    tvContainer.addView(textView);
                }
            }
        }
        return v;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //do something like measure a view etc
                View content = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                Log.d("DISPLAY", content.getWidth() + " x " + content.getHeight());
                screenHeight = content.getHeight();
                ViewGroup.LayoutParams layoutParams = mItemHeader.getLayoutParams();
                layoutParams.height = screenHeight;
                mItemHeader.setLayoutParams(layoutParams);
                //we only wanted the first call back so now remove
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    //noinspection deprecation
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void setItem(HalItemPopular item) {
        this.item = item;
    }

    private class loadImageTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            final HalImage featuredImage = item.getManifest().getFeaturedImage();
            if(featuredImage == null) return null;

            Link imageLink = featuredImage.getLargestImageLink();
            try {
                InputStream in = new java.net.URL(imageLink.href).openStream();
                return BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        public void onPostExecute(Bitmap image) {
            mImageView.setImageBitmap(image);
        }
    }
}