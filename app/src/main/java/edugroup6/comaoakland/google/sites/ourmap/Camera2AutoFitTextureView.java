package edugroup6.comaoakland.google.sites.ourmap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * Created by Archie on 10/8/2015.
 */

public class Camera2AutoFitTextureView extends TextureView{
    private int mRatioWidth = 0;
    private int mRationHeight = 0;

    public Camera2AutoFitTextureView(Context context){
        this (context, null);
    }

    public Camera2AutoFitTextureView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public Camera2AutoFitTextureView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height){
        if(width<0 || height<0)
            throw new IllegalArgumentException("Size cannot be negative.");

        mRationHeight = height;
        mRatioWidth = width;
        requestLayout();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (0==mRatioWidth || 0==mRationHeight)
            setMeasuredDimension(width, height);
        else{
            if(width < (height * mRationHeight/mRatioWidth))
                setMeasuredDimension(width, (width * mRationHeight/mRatioWidth));
            else
                setMeasuredDimension((height * mRatioWidth/mRationHeight), height);
        }
    }
}
