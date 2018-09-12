package ecg;

import android.content.*;
import android.graphics.*;
import android.support.annotation.*;
import android.util.*;
import android.view.*;

import java.util.*;

import constants.*;
import logger.*;

public class RealTimeEcgView extends View {

    Paint mPaint;
    int mPixelsPerCm = 0;
    public  String leadArr[]={"I","II","III","aVR","aVL","aVF","V1","V2","V3","V4","V5","V6"};

    float xMargin;
    float yValue;
    float xValue;
    int width;
    int height;


    int count=0;

    List<Float> floatListpoints;
    private Float[] points;

    public RealTimeEcgView(Context context) {
        super(context);
        init();

    }

    public RealTimeEcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public RealTimeEcgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    private void init() {
        mPaint = new Paint();
        floatListpoints=new ArrayList<>();
        final float density = getResources().getDisplayMetrics().densityDpi;

        mPixelsPerCm = (int) (density / Constants.CMS_PER_INCH + 0.5f);

        }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         width=getWidth();
         height=getHeight();



        //

        mPaint.setStrokeWidth(10f);
        mPaint.setColor(Color.WHITE);

        int baseXpoint=mPixelsPerCm;
        int fixedSamplesofXaxis=400;

         xMargin=width/fixedSamplesofXaxis;



         baseXpoint=height/2;






            canvas.drawPoint(xValue,yValue+baseXpoint,mPaint);





    }




    public void drawpoints(int y)
    {

        count++;
        yValue=y;
        floatListpoints.add((float) y);
        if(xValue<width)
        {
            xValue++;

        }else{
            floatListpoints.clear();
            xValue=0;
        }

        Logger.log(Level.DEBUG,"--","floatListpoints list size="+floatListpoints.size());
        newFloatArray(floatListpoints.size());

    }


    public Float[] newFloatArray(int size) {
     //    points = new float[size];

         points=floatListpoints.toArray(new Float[size]);
        Logger.log(Level.DEBUG,"--","points array size="+points.length);
        invalidate();
        return points;
    }
}
