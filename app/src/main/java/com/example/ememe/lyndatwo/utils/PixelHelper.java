package com.example.ememe.lyndatwo.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by ememe on 3/5/2017.
 * https://www.lynda.com/Android-tutorials/Implement-visual-objects-Java-classes/383247/533001-4.html?autoplay=true
 * single static method
 * receives integer, number of absolute pixels and context reference
 * returns same integer, as number of pixels for image
 */

public class PixelHelper {
    public static int pixelsToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }
}
