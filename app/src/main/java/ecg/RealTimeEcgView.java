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

    Canvas mCanvas;

    Bitmap mGraphImg;
    /** The r1. */
    private Rect r1;

    /** The r2. */
    private Rect r2;

    /** The r3. */
    private Rect r3;

    boolean mDoPaint;
    int count=0;

    List<Float> floatListpoints;
    private float[] points;

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
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if (null == mGraphImg) {
            /*
             * Used Bitmap.Config.ALPHA_8 instead Bitmap.Config.ARGB_8888 to
             * avoid OutOfMemory exception in the application on various phones
             * for example (Samsung Note 2,Micromax Bolt, LG Optimal)
             */
            mGraphImg = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
                    Bitmap.Config.ALPHA_8);
        }
        if (null == mCanvas) {
            mCanvas = new Canvas(mGraphImg);
        }

        if (null == r1) {
            r1 = new Rect(0, 0, getWidth(), getHeight());
        }

        if (null == r2) {
            r2 = new Rect(0, 0, getWidth(), getHeight());
        }

        if (null == r3) {
            r3 = new Rect(0, 0, getWidth(), getHeight());
        }


        if (null != mGraphImg && mDoPaint) {
            c.drawBitmap(mGraphImg, r1, r2, mPaint);
            return;
        }

         width=getWidth();
         height=getHeight();



        //

        mPaint.setStrokeWidth(10f);
        mPaint.setColor(0X0082CB00);



        mCanvas.drawRect(r3, mPaint);
        int baseXpoint=mPixelsPerCm;
        int fixedSamplesofXaxis=400;

         xMargin=width/fixedSamplesofXaxis;



         baseXpoint=height/2;





         mPaint.setColor(Color.CYAN);
         mCanvas.drawPoint(xValue,yValue+baseXpoint,mPaint);




         Logger.log(Level.DEBUG,":)","Hello OnDraw()");

        c.drawBitmap(mGraphImg, r1, r2, mPaint);
        mDoPaint=true;
    }




    public void drawpoints(int y)
    {

        count++;
        yValue=y;
        floatListpoints.add((float) y);
        if(xValue>width) {

            floatListpoints.clear();
            xValue = 0;
            clearCanvas();
        }

        xValue++;
      //  Logger.log(Level.DEBUG,"--","floatListpoints list size="+floatListpoints.size());
        mDoPaint=false;
        invalidate();

        //newFloatArray(floatListpoints.size());

    }


    public float[] newFloatArray(int size) {
     //    points = new float[size];

       //  points=floatListpoints.toArray(new Float[size]);
        Logger.log(Level.DEBUG,"--","points array size="+points.length);
        invalidate();
        return points;
    }


    public void clearCanvas()
    {
        try {

            mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
            mDoPaint=false;

            }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
