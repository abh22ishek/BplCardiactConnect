package custom.view;

import android.content.*;
import android.graphics.*;
import android.support.annotation.*;
import android.util.*;
import android.view.*;

import java.util.*;

import constants.*;
import logger.*;

import static android.support.constraint.Constraints.TAG;


public class EcgGraphView extends View {


    Paint mPaint;
    int mPixelsPerCm = 0;
    public  String leadArr[]={"I","II","III","aVR","aVL","aVF","MCL1","MCL2","MCL3","MCL4","MCL5","MCL6"};


    List<String> EcgLead1;
    List<String> EcgLead2;

    List<String> EcgLeadV1;

    List<String> EcgLeadV2;

    List<String> EcgLeadV3;

    List<String> EcgLeadV4;
    List<String> EcgLeadV5;


    List<String> EcgLeadV6;


    public EcgGraphView(Context context) {
        super(context);
        init();
    }

    public EcgGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint();
        final float density = getResources().getDisplayMetrics().densityDpi;

        mPixelsPerCm = (int) (density / Constants.CMS_PER_INCH + 0.5f);



    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);


        // mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);// default value

        // Find out the pixel density

        Logger.log(Level.DEBUG,TAG,"--pixels per cm --"+mPixelsPerCm);

        final float widthScale = mPixelsPerCm / Constants.SAMPLES_PER_CM; // 100
        // samples per cm
        final float heightScale = Constants.AMPLITUDE_PER_CM / mPixelsPerCm; // pixels

        mPaint.setColor(Color.BLUE);
        //   mCanvas.drawRect(r3, mPaint);


        for (float i = mPixelsPerCm / (float) 10; i < getWidth(); i += mPixelsPerCm / (float) 10) {
            mCanvas.drawLine(i, 0, i, getHeight(), mPaint);
        }

        for (float i = mPixelsPerCm / (float) 10; i < getHeight(); i += mPixelsPerCm
                / (float) 10) {
            mCanvas.drawLine(0, i, getWidth(), i, mPaint);
        }

        mPaint.setColor(0x43AFEB00);// 4FBFFF00);
        mPaint.setTextSize(12.0f);

        int count = 1;
        int y ;




        for (float i = mPixelsPerCm; i < getWidth(); i += mPixelsPerCm) {
            mPaint.setColor(Color.GRAY);// 4FBFFF00);
            mPaint.setStrokeWidth(2.5f);
            mCanvas.drawLine(i, 0, i, getHeight(), mPaint);


            for (int k = 0; k < 12; k++) {
                y = ((k * 3) * mPixelsPerCm) + 15;
                mCanvas.drawText(count + "", i + 2, y, mPaint);
            }
            count++;
        }

        for (float i = mPixelsPerCm; i < getHeight(); i += mPixelsPerCm) {
            mCanvas.drawLine(0, i, getWidth(), i, mPaint);
        }

        mPaint.setColor(Color.BLACK);// Color.WHITE);
        // paint.setStyle(Paint.Style.FILL);
        // mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setTextSize(25f);


        if (null != leadArr) {
            int mul ;
            for (int i = 0; i < leadArr.length; i++) {
                mul = (3 * i) * mPixelsPerCm;
                mCanvas.drawText(leadArr[i], 2, 20 + mul, mPaint);
            }
        }

        int mP1 = 0, mPp1 = 0;

        int mP2 = 0, mPp2 = 0;

        int mP3 = 0, mPp3 = 0;

        int mP4 = 0, mPp4 = 0;

        int mP5 = 0, mPp5 = 0;

        int mP6 = 0, mPp6 = 0;

        int mP7 = 0, mPp7 = 0;

        int mP8 = 0, mPp8 = 0;

        int mP9 = 0, mPp9 = 0;

        int mP10 = 0, mPp10 = 0;

        int mP11 = 0, mPp11 = 0;

        int mP12 = 0, mPp12 = 0;

        float mX = mP1 = mP2 = mP3 = 0;
        mP4 = mP5 = mP6 = mP7 = mP8 = mP9 = mP10 = mP11 = mP12 = 0;

        final int graphHeight = mPixelsPerCm;
        Logger.log(Level.DEBUG,TAG,"--GEt Height --"+getHeight());
        Logger.log(Level.DEBUG,TAG,"--pixels per 36 cm --"+36 * mPixelsPerCm);
        Logger.log(Level.DEBUG,TAG,"--pixels per cm --"+40 * graphHeight);

        float mPx1 = 0;
        mPp1 = 7 * graphHeight;
        float mPx2 = 0;
        mPp2 = 10 * graphHeight; // graphHeight * 2;
        float mPx3 = 0;
        mPp3 = 13 * graphHeight; // graphHeight * 3;
        float mPx4 = 0;
        mPp4 = 16 * graphHeight; // graphHeight * 3;
        float mPx5 = 0;
        mPp5 = 19 * graphHeight; // graphHeight * 3;
        float mPx6 = 0;
        mPp6 = 22 * graphHeight; // graphHeight * 3;
        float mPx7 = 0;
        mPp7 = 25 * graphHeight; // graphHeight * 3;
        float mPx8 = 0;
        mPp8 = 28 * graphHeight; // graphHeight * 3;
        float mPx9 = 0;
        mPp9 = 31 * graphHeight; // graphHeight * 3;
        float mPx10 = 0;
        mPp10 = 34 * graphHeight; // graphHeight * 3;
        float mPx11 = 0;
        mPp11 = 37 * graphHeight; // graphHeight * 3;
        float mPx12 = 0;
        mPp12 = 40 * graphHeight; // graphHeight * 3;

        final int noOfLeads = leadArr.length;

        final int leadSampleCount = 10000;
        // traverse through the points one by one to get moving graphs
        for (int i = 0; i < leadSampleCount; i++) {
            if (mX > getWidth()) {
                break;
            }
            mX += widthScale;

            mPaint.setColor(Color.BLACK);


            mPaint.setStrokeWidth(2.5f );
            mPaint.setAntiAlias(true);
            // draw ECG


               mP1 = (7* graphHeight) - (int) (Integer.parseInt(EcgLead1.get(i)) / heightScale);
                mCanvas.drawLine(mPx1, mPp1, mX, mP1, mPaint);


            mP2 = (10 * graphHeight)
                    - (int) (Integer.parseInt(EcgLead2.get(i))/ heightScale);
            mCanvas.drawLine(mPx2, mPp2, mX, mP2, mPaint);



            mP3 = (13 * graphHeight) - (int)  (Integer.parseInt(EcgLeadV3.get(i)) / heightScale);
            mCanvas.drawLine(mPx3, mPp3, mX, mP3, mPaint);








                mP4 = (16 * graphHeight)
                        - (int) (Integer.parseInt(EcgLead1.get(i)) / heightScale);
                mCanvas.drawLine(mPx4, mPp4, mX, mP4, mPaint);



                mP5 = (19 * graphHeight)
                        - (int) (Integer.parseInt(EcgLead1.get(i))/ heightScale);
                mCanvas.drawLine(mPx5, mPp5, mX, mP5, mPaint);



                mP6 = (22 * graphHeight)
                        - (int) (Integer.parseInt(EcgLead1.get(i))/ heightScale);
                mCanvas.drawLine(mPx6, mPp6, mX, mP6, mPaint);



                mP7 = (25 * graphHeight)
                        - (int) (Integer.parseInt(EcgLeadV1.get(i))/ heightScale);
                mCanvas.drawLine(mPx7, mPp7, mX, mP7, mPaint);



                mP8 = (28 * graphHeight)
                        - (int) (Integer.parseInt(EcgLeadV2.get(i)) / heightScale);
                mCanvas.drawLine(mPx8, mPp8, mX, mP8, mPaint);



                mP9 = (31 * graphHeight)
                        - (int) (Integer.parseInt(EcgLeadV3.get(i)) / heightScale);
                mCanvas.drawLine(mPx9, mPp9, mX, mP9, mPaint);



                mP10 = (34 * graphHeight) - (int) (Integer.parseInt(EcgLeadV4.get(i))/ heightScale);
                mCanvas.drawLine(mPx10, mPp10, mX, mP10, mPaint);



                mP11 = (37 * graphHeight) -(int) (Integer.parseInt(EcgLeadV5.get(i)) / heightScale);
                mCanvas.drawLine(mPx11, mPp11, mX, mP11, mPaint);







            mP12 =  (40 * graphHeight) - (int)(Integer.parseInt(EcgLeadV6.get(i)) / heightScale);
            mCanvas.drawLine(mPx12, mPp12, mX, mP12, mPaint);

            // store current point as previous point
            mPp1 = mP1;
            mPp2 = mP2;
            mPp3 = mP3;
            mPp4 = mP4;
            mPp5 = mP5;
            mPp6 = mP6;
            mPp7 = mP7;
            mPp8 = mP8;
            mPp9 = mP9;
            mPp10 = mP10;
            mPp11 = mP11;
            mPp12 = mP12;
            mPx1 = mPx2 = mPx3 = mPx4 = mPx5 = mPx6 = mPx7 = mPx8 = mPx9 = mPx10 = mPx11 = mPx12 = mX;
        }

    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(25 *mPixelsPerCm,36* mPixelsPerCm);
    }



    public void setList(List<String> lead1, List<String> lead2 , List<String> leadV1, List<String> leadV2
    , List<String> leadV3, List<String> leadV4, List<String> leadV5, List<String> leadV6){

        EcgLead1=lead1;
        EcgLead2=lead2;
        EcgLeadV1=leadV1;

        EcgLeadV2=leadV2;

        EcgLeadV3=leadV3;
        EcgLeadV4=leadV4;

        EcgLeadV5=leadV5;
        EcgLeadV6=leadV6;

        }

}