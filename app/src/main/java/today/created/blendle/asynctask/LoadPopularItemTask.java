package today.created.blendle.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import today.created.blendle.Webservice;
import today.created.blendle.hal.HalItemPopular;
import today.created.blendle.hal.Link;
import today.created.blendle.service.MyDream;

/**
 * Created by jolandaverhoef on 23/05/15.
 * Load the most popular item and show on screen.
 */
public class LoadPopularItemTask extends AsyncTask<Void, Void, HalItemPopular> {
    private static final String TAG = "LoadPopularItemTask";
    private final MyDream myDream;
    private Bitmap mImage;

    public LoadPopularItemTask(MyDream myDream) {
        this.myDream = myDream;
    }
    protected HalItemPopular doInBackground(Void... voids) {
        Log.v(TAG, "doInBackground");
        try {
            // retrieve the item
            final Webservice webservice = new Webservice();
            HalItemPopular popularItem = webservice.getMostPopularItem();
            // retrieve the featured image belonging to this item
            // Get largest image size for brevity's sake. We would ideally get the one that would
            // fit on the screen most easily.
            Link imageLink = popularItem.getManifest().getFeaturedImage().getLargestImageLink();
            try {
                InputStream in = new java.net.URL(imageLink.href).openStream();
                mImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return popularItem;
        } catch (IOException e) {
            Log.e("MyDream", e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(HalItemPopular popularItem) {
        Log.v(TAG, "onPostExecute: " + popularItem);
        if (popularItem != null) {
            myDream.updateUI(popularItem, mImage);
        }
    }
}
