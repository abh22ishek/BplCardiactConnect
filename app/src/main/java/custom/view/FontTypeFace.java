package custom.view;


import android.content.*;
import android.graphics.*;

import java.util.*;


class FontTypeFace {

   private static HashMap<String,Typeface> fontcache=new HashMap<>();



    static Typeface getTypeFace(String fontname,Context context)
   {
       // fontaname is path
       Typeface typeface=fontcache.get(fontname);

       if(typeface==null)
       {
           try {
               typeface = Typeface.createFromAsset(context.getAssets(), fontname);
           }
           catch (Exception e) {
               return null;
           }
           fontcache.put(fontname,typeface);
       }
       return typeface;
   }
}
