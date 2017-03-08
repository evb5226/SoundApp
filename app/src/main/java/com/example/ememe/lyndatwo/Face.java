package com.example.ememe.lyndatwo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.ememe.lyndatwo.utils.PixelHelper;

/**
 * Created by ememe on 3/5/2017.
 * https://www.lynda.com/Android-tutorials/Implement-visual-objects-Java-classes/383247/533001-4.html?autoplay=true
 */

//creates function for Face image
    // face image is just a square b/c bad formatting
public class Face extends ImageView implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    //declare ValueAnimator as an animator
    private ValueAnimator mAnimator;

    public Face(Context context) {
        super(context);
    }

    //input context reference (where it goes), color of image as hexadecimal, number of pixels)
    public Face(Context context, int color, int rawHeight) {
        super(context);

        //defines image of face
        this.setImageResource(R.drawable.blankgreyface);

        //tints image
        this.setColorFilter(color);

        //function gives height in pixels, but not the width
        //this gets the width
        int rawWidth = rawHeight / 2;
        int dpHeight = PixelHelper.pixelsToDp(rawHeight, context);
        int dpWidth = PixelHelper.pixelsToDp(rawWidth, context);
        //create param group (height and width)
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpWidth, dpHeight);
        //set the param to the object
        setLayoutParams(params);

    }

    //launch balloon
    //two arguments screen height and duration
    public void releaseBalloon(int screenHeight, int duration) {
        //can animate any value
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(duration);
        //set start value of animation (bottom of screen)
        //Of means top of screen
        //screenHeight means the top of the screen
        mAnimator.setFloatValues(screenHeight, 0f);
        //determines linear, accel, decell or bouncing animation
        mAnimator.setInterpolator(new LinearInterpolator());
        //where to send the animation and listener
        //right click add to create 5 overrides
        mAnimator.setTarget(this);
        mAnimator.addListener(this);
        mAnimator.addUpdateListener(this);
        //launch on start
        mAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        //gives the current value of the location of the object
        //gives value as object, so write float instead
        setY((float) valueAnimator.getAnimatedValue());
    }
}
