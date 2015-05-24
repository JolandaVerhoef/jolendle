package today.created.blendle.view.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

import today.created.blendle.hal.HalContent;

/**
 * Created by jolandaverhoef on 23/05/15.
 * An extension for TextView that sets font size and style based on HalContent type.
 */
public class BodyPartTextView extends TextView {

    public BodyPartTextView(Context context) {
        super(context);
    }

    public BodyPartTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBodyPart(HalContent bodyPart) {
        setText(Html.fromHtml(bodyPart.content));
        int padding = Math.round(convertDpToPixel(10, getContext()));
        setPadding(0,padding,0,padding);
        styleText(bodyPart.type);
    }

    private void styleText(String type) {
        String font;
        int fontSize;
        if("hl1".equals(type)) {
            font = "Lato-Bold.ttf";
            fontSize = 36;
        } else if("intro".equals(type)) {
            font = "Lato-Italic.ttf";
            fontSize = 18;
        } else {
            font = "Lato-Regular.ttf";
            fontSize = 14;
        }
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), font));
        setTextSize(fontSize);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    private float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}
