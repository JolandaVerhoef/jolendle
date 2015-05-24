package today.created.blendle.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jolandaverhoef on 24/05/15.
 */
public class FullscreenImageView extends ImageView {

    private int screenHeight;

    public FullscreenImageView(Context context) {
        super(context);
    }

    public FullscreenImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, screenHeight);
    }

    public void setScreenHeight(int screenheight) {
        this.screenHeight = screenheight;
    }
}
