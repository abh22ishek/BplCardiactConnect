package custom.ecg.lead.realdata;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.util.*;
import android.view.*;

import java.util.*;

import constants.*;


public class LeadV4GraphView extends View {

    Paint mPaint;
    int mPixelsPerCm = 0;



    float yValue=0;
    float xValue=0;

    float  newYValue;
    float newXvalue;


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


    int baseXpoint=0;

    List<Integer> floatListpoints;



    float mYplotpoints;

    float heightScale;
    float widthScale;



    Handler handler;

    int pxCount=0;

    float blackRect=widthScale;
    float topLeft=0;
    boolean startErasing;

    boolean mDrawPoints;
    boolean mEraser;



    int NoOfWidthCrossescount=0;

    int countVal=0;

    public LeadV4GraphView(Context context) {
        super(context);
        init();

    }

    public LeadV4GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();

        handler = new Handler();
        final float density = getResources().getDisplayMetrics().densityDpi;

        mPixelsPerCm = (int) (density / Constants.CMS_PER_INCH );

        heightScale = Constants.AMPLITUDE_PER_CM / mPixelsPerCm; //
        widthScale = mPixelsPerCm / Constants.SAMPLES_PER_CM;


        newXvalue = widthScale;

        if (floatListpoints == null) {
            floatListpoints = new ArrayList<>();

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(":)","Hello OnDraw()");

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
            canvas.drawBitmap(mGraphImg, r1, r2, mPaint);
            return;
        }


        //

        mPaint.setColor(0X0082CB00);

        mCanvas.drawRect(r3, mPaint);



        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);




        if(mDrawPoints){



            for(int i=0;i<floatListpoints.size();i++){

                newYValue=floatListpoints.get(i)/heightScale;
                Log.d("plotting V4 Y value=", ""+newYValue);

                mCanvas.drawLine(xValue, (-yValue + baseXpoint), newXvalue, (-newYValue + baseXpoint), mPaint);

                xValue=newXvalue;
                yValue=newYValue;
                newXvalue= newXvalue+widthScale;
            }


            floatListpoints.clear();

            canvas.drawBitmap(mGraphImg, r1, r2, mPaint);


            mDoPaint=true;
            mDrawPoints=false;
        }


    }



    public void drawpoints(int yValue)
    {
        mYplotpoints=yValue;
        mDrawPoints=true;
        countVal++;
        floatListpoints.add(yValue);
        //  Log.d("List to plot=", ""+floatListpoints);
        //   Log.d("newXvallue=", ""+newXvalue +" mWidth="+mWidth);
        mEraser=false;
        if(newXvalue>mWidth) {
            Log.d("((--CountVal--))=",""+countVal);
            floatListpoints.clear();
            newXvalue = 0;
            xValue=0;
            mEraser=true;

            NoOfWidthCrossescount++;
            topLeft=0;
            blackRect=100;
            Log.d("**********8","Graph reaches to the end ");

            countVal=0;
        }


        if(NoOfWidthCrossescount>=2){
            erasePartOfCanvas();
        }
        if(mEraser && !startErasing)
        {

            erasePartOfCanvas();
            startRepeatingTask();

        }
        invalidate();

    }



    public void erasePartOfCanvas(){
        // Set eraser paint properties
        Paint eraserPaint=new Paint();
        eraserPaint.setAlpha(0);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraserPaint.setAntiAlias(true);
        startErasing=true;
        mCanvas.drawRect(topLeft,0,blackRect,mHeight,eraserPaint);

        //  invalidate();

    }


    private void moverRecctangle()
    {
        topLeft=topLeft+(2*widthScale);
        blackRect=blackRect+(2*widthScale);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("--On size() changed--","width and Height  = "+w+" "+h);
        pxCount++;
        mWidth=w;
        mHeight=h;
        baseXpoint=mHeight/2;
    }





    // Work in progress



    // alternate the view's background color
    Runnable mRunnableCode = new Runnable() {
        @Override
        public void run() {
            if (startErasing) {

                moverRecctangle();
                erasePartOfCanvas();

            }

            // repost the code to run again after a delay
            handler.postDelayed(mRunnableCode, 10);
        }
    };


    void startRepeatingTask() {
        mRunnableCode.run();

    }


    public void stopRepeatingTask() {
        if(handler!=null)
        {
            handler.removeCallbacks(mRunnableCode);
        }
    }


}