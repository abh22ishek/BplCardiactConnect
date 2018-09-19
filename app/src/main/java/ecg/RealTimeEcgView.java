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
    float yValue=0;
    float xValue=0;

    float newYValue=0;
    float newXvalue=0;


    int mWidth;
    int mHeight;

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

    int baseXpoint=0;
    List<Float> floatListpoints;
    private float[] points;
    boolean mSizechanged;

    Path mPath;
     float heightScale;
     float widthScale;
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

        final float density = getResources().getDisplayMetrics().densityDpi;

        mPixelsPerCm = (int) (density / Constants.CMS_PER_INCH + 0.5f);

      heightScale = Constants.AMPLITUDE_PER_CM / mPixelsPerCm; //
        widthScale = mPixelsPerCm / Constants.SAMPLES_PER_CM;
        if(floatListpoints==null){
            floatListpoints=new ArrayList<>();

        }
        }


    @Override
    protected void onDraw(Canvas c) {


        if(pxCount<=1)
        {
            return;
        }




      if (null == mGraphImg) {

             /** Used Bitmap.Config.ALPHA_8 instead Bitmap.Config.ARGB_8888 to
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


        if (null != mGraphImg && mDoPaint && !mDrawPoints) {
            c.drawBitmap(mGraphImg, r1, r2, mPaint);
            return;
        }


        //

        mPaint.setColor(0X0082CB00);

        mCanvas.drawRect(r3, mPaint);

        int fixedSamplesofXaxis=400;


        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2f);

        Logger.log(Level.DEBUG,":)","Hello OnDraw()");


        if(mDrawPoints){





           // newXvalue++;


            int mCount=0;
            for(int i=0;i<floatListpoints.size();i++){

                newYValue=floatListpoints.get(i)/heightScale;

                mCanvas.drawLine(xValue,yValue+baseXpoint,newXvalue,newYValue+baseXpoint,mPaint);
                //mCanvas.drawPoint(newXvalue,newxy,mPaint);

                xValue=newXvalue;
                yValue=newYValue;
                newXvalue+=widthScale;

              mCount++;

            }

            Logger.log(Level.DEBUG,"--","mCount size="+mCount+"x val="+xValue+"-new Xval="+newXvalue);

            Logger.log(Level.DEBUG,"--","floatListpoints list size="+floatListpoints.size());
            c.drawBitmap(mGraphImg, r1, r2, mPaint);


            mDoPaint=true;
            mDrawPoints=false;
        }

    }



boolean mDrawPoints;
    boolean mEraser;
    public void drawpoints(List<Float> buffer)
    {

        count++;

        floatListpoints.clear();
      //  yValue=y;
        //floatListpoints.add((float) y);
        if(newXvalue>mWidth) {

            floatListpoints.clear();
            newXvalue = 0;
            xValue=0;
            mEraser=true;

        }

        if(mEraser)
        {
            erasePartOfCanvas();
        }


        mDrawPoints=true;
        mDoPaint=false;
        floatListpoints.addAll(buffer);
        invalidate();

       // newFloatArray(floatListpoints.size());

    }


    public float[] newFloatArray(int size) {
     //    points = new float[size];

       //  points=floatListpoints.toArray(new Float[size]);
        Logger.log(Level.DEBUG,"--","points array size="+points.length);
        invalidate();
        return points;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        }

int blackRect=100;
    int topLeft=0;

        public void erasePartOfCanvas(){
            // Set eraser paint properties
            Paint eraserPaint=new Paint();
            eraserPaint.setAlpha(0);
            eraserPaint.setStrokeJoin(Paint.Join.ROUND);
            eraserPaint.setStrokeCap(Paint.Cap.ROUND);
            eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            eraserPaint.setAntiAlias(true);



            mCanvas.drawRect(topLeft,0,blackRect,getHeight(),eraserPaint);
            topLeft=blackRect;
            blackRect+=100;
            invalidate();
        }


    public void clearCanvas()
    {
        try {

            mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
            mDoPaint=false;

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.log(Level.DEBUG,"---","--On size() changed--");
        pxCount++;
        mWidth=w;
        mHeight=h;
        baseXpoint=mHeight/2;
    }


    int pxCount=0;
}
