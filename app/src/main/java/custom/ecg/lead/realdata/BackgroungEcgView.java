package custom.ecg.lead.realdata;

import android.content.*;
import android.graphics.*;
import android.support.annotation.*;
import android.util.*;
import android.view.*;

import constants.*;
import logger.*;

public class BackgroungEcgView extends View {
    Paint mPaint;


    int mPixelsPerCm=0;
    float heightScale;
    float widthScale;

    float totalWidth,totalHeight;

    boolean mDopaints;

    DashPathEffect effects;

    Path path;

    public BackgroungEcgView(Context context) {
        super(context);
        init();
    }

    public BackgroungEcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint();

        final float density = getResources().getDisplayMetrics().densityDpi;

        mPixelsPerCm = (int) (density / Constants.CMS_PER_INCH + 0.5f);
        heightScale = Constants.AMPLITUDE_PER_CM / mPixelsPerCm; //
        widthScale = mPixelsPerCm / Constants.SAMPLES_PER_CM;

        path = new Path();

        effects = new DashPathEffect(new float[] { 2, 1, 2, 1 }, 0);



    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        totalHeight=getHeight();
        totalWidth=getWidth();
        Logger.log(Level.DEBUG,"BackgroundEcgView --On size() changed--",
                "width and Height  = "+totalWidth+" "+totalHeight);



        float startX1=0;
        float startX2=0;
        float startY1=0;
        float startY2=0;


        float mstartX1=0;
        float mstartX2=0;
        float mstartY1=0;
        float mstartY2=0;

        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(0.9f);


        for(int i=0;i<totalWidth;i+=mPixelsPerCm/2)
        {
            canvas.drawLine(startX1,0,startX2,totalHeight,mPaint);
            startX2=startX2+(mPixelsPerCm/2);
            startX1=startX2;


        }

        for(int i=0;i<totalHeight;i+=mPixelsPerCm/2)
        {
            canvas.drawLine(0,startY1,totalWidth,startY2,mPaint);

            startY2=startY2+(mPixelsPerCm/2);
            startY1=startY2;

        }




        //array is ON and OFF distances in px (4px line then 2px space)
        mPaint.setStrokeWidth(0.4f);
        for(int i=0;i<totalWidth;i+=mPixelsPerCm/10)
        {
            canvas.drawLine(mstartX1,0,mstartX2,totalHeight,mPaint);
            mstartX2=mstartX2+(mPixelsPerCm/10);
            mstartX1=mstartX2;

        }


        for(int i=0;i<totalHeight;i+=mPixelsPerCm/10)
        {
            canvas.drawLine(0,mstartY1,totalWidth,mstartY2,mPaint);
            mstartY2=mstartY2+(mPixelsPerCm/10);
            mstartY1=mstartY2;

        }




    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.log(Level.DEBUG,"BackgroundEcgView --On size() changed--","width and Height  = "+w+" "+h);
       totalHeight=h;
       totalWidth=w;

    }


}
