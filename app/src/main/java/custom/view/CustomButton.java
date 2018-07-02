package custom.view;

import android.annotation.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import logger.*;

@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    private final static int CENTRALESANS_XBOLD = 1;

    private final static int CENTRALSANS_LIGHT = 2;
    private final static int CENTRALSANS_BOOK = 3;

    private final static int AVANT_GARDE=4;
    private final static int AVANT_GAMI=5;
    private final static int ARIAL=6;



    String TYPEFACE_CENTRALSANS_XBOLD = "fonts/CentraleSans-XBold.otf";
    String TYPEFACE_CENTRALSANS_Light = "fonts/CentraleSans-Light.otf";
    String TYPEFACE_CENTRALSANS_BOOK = "fonts/CentraleSans-Book.otf";
    String TYPEFACE_AVANT_GARDE="fonts/ufonts.com_avantgarde.ttf";
    String TYPEFACE_AVANT_GAMI= "fonts/avangami.ttf";
    String TYPEFACE_ARIAL="fonts/arial.ttf";


    String TAG= CustomButton.class.getSimpleName();

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }


    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);

        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.CustomButton_fontname,1);
        Logger.log(Level.INFO,TAG,"styleable typeface value="+typeface);
        switch (typeface) {

            case CENTRALESANS_XBOLD:
                //Typeface  centralSansXBold = Typeface.createFromAsset(context.getAssets(), "fonts/CentraleSans-XBold.otf");
                Typeface centralSansXBold= custom.view.FontTypeFace.getTypeFace(TYPEFACE_CENTRALSANS_XBOLD, context);
                setTypeface(centralSansXBold);
                break;



            case CENTRALSANS_LIGHT:
                Typeface centralSansLight= custom.view.FontTypeFace.getTypeFace(TYPEFACE_CENTRALSANS_Light, context);
                setTypeface(centralSansLight);
                break;

            case CENTRALSANS_BOOK:
                Typeface centralSansBook = custom.view.FontTypeFace.getTypeFace(TYPEFACE_CENTRALSANS_BOOK, context);
                setTypeface(centralSansBook);
                break;

            case AVANT_GARDE:
                Typeface avantgarde=custom.view.FontTypeFace.getTypeFace(TYPEFACE_AVANT_GARDE, context);
                setTypeface(avantgarde);
                break;


            case AVANT_GAMI:
                Typeface avangami= custom.view.FontTypeFace.getTypeFace(TYPEFACE_AVANT_GAMI, context);
                setTypeface(avangami);
                break;

            case ARIAL:
                Typeface arial=FontTypeFace.getTypeFace(TYPEFACE_ARIAL,context);
                setTypeface(arial);
        }

        values.recycle();
    }
}
