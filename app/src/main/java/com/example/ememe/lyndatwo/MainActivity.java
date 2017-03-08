package com.example.ememe.lyndatwo;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.hardware.SensorEvent;

//import java files for sound
import com.example.ememe.lyndatwo.utils.ShakeDetector;
import com.example.ememe.lyndatwo.utils.SoundHelper;
import com.example.ememe.lyndatwo.utils.HelloSound;

public class MainActivity extends AppCompatActivity {

    //created for dynamic levels
        //delay=time between balloon releases
        //duration = time for balloon to reach top
    private static final int MIN_ANIMATION_DELAY = 500;
    private static final int MAX_ANIMATION_DELAY = 1500;
    private static final int MIN_ANIMATION_DURATION = 1000;
    private static final int MAX_ANIMATION_DURATION = 8000;

    private ViewGroup mContentView;
    //declare array of colors
    private int[] mBalloonColors = new int[3];
    //declare current color
        //defaults to 0
    //declare integers
    private int mNextColor, mScreenWidth, mScreenHeight;
    private int mLevel, mScore, mPinsUsed;

    //declare sound object
    private SoundHelper mSoundHelper;
    private HelloSound mSoundHelperTwo;

    //for shake
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    //to detect rotation
    private int[] gravity=new int[3];
    private int[] linear_acceleration=new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                mSoundHelper.playMusic();
            }
        });

        //define array of colors so face changes colors
        //.arbg(transparency, red, green, blue)
        mBalloonColors[0] = Color.argb(255, 255, 0, 0);
        mBalloonColors[1] = Color.argb(255, 0, 255, 0);
        mBalloonColors[2] = Color.argb(255, 0, 0, 255);


        /**
         * OnCreate do stuff here
         */

        /* Set background resource*/
        getWindow().setBackgroundDrawableResource(R.drawable.yellowtrianglebackround);
        mContentView = (ViewGroup) findViewById(R.id.activity_main);

        setToFullScreen();

        //get size of screen to make to scale
        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //remove so it only happens once
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    //get and store screen width
                    mScreenWidth = mContentView.getWidth();
                    //get and store screen height
                    mScreenHeight = mContentView.getHeight();
                }
            });
        }

        //creates sound pop object
        mSoundHelper = new SoundHelper(this);
        //prepare music player object
        mSoundHelper.prepareMusicPlayer(this);
        //mSoundHelperTwo=new HelloSound(this);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToFullScreen();
            }
        });

        //when class is touched...
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //on the up of the touch (instead of down)
                //for making it say Hello on different motion - doesn't work yet
                /**
                if (motionEvent.getAction()==motionEvent.EDGE_BOTTOM){
                    Face b = new Face(MainActivity.this, 0xFFFF0000, 100);
                    //place image at the place of touch
                    b.setX(motionEvent.getX());
                    b.setY(motionEvent.getY());
                    //add image to the screen
                    mContentView.addView(b);

                    //change speed of object based on level
                    int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (mLevel * 1000));
                    //release object
                    b.releaseBalloon(mScreenHeight, duration);

                    //play short beep
                    mSoundHelper.playMusic();
                }
                 */
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Face b = new Face(MainActivity.this, mBalloonColors[mNextColor], 100);
                    //place image at the place of touch
                    b.setX(motionEvent.getX());
                    b.setY(motionEvent.getY());
                    //add image to the screen
                    mContentView.addView(b);

                    //Let 'er fly
                    //change speed of object based on level
                    int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (mLevel * 1000));
                    //release object
                    b.releaseBalloon((int) motionEvent.getY(), duration);

                    //play short beep
                    mSoundHelper.playSound();

                    //logic to change color of face
                    //if number == length of color array (last one)
                    if (mNextColor + 1 == mBalloonColors.length) {
                        mNextColor = 0;
                    } else {
                        //change color
                        mNextColor++;
                    }

                }

                return false;
            }
        });



    }

    private void setToFullScreen(){
        /**
         * set an empty activity to full screen
         * w/o all the extra stuff in the full screen activity
         * that over complicates it
         */
        ViewGroup rootLayout=(ViewGroup) findViewById(R.id.activity_main);
        rootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume(){
        /**
         *makes less glitch-y when going to full screen
         */
        super.onResume();
        setToFullScreen();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    public void StartOver(){
        mContentView = (ViewGroup) findViewById(R.id.activity_main);
        mContentView.removeAllViews();
    }

/**
    public void onSensorChanged(SensorEvent event)
    {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = (float) 0.8;

        gravity[0] = (int) (alpha * gravity[0] + (1 - alpha) * event.values[0]);
        gravity[1] = (int) (alpha * gravity[1] + (1 - alpha) * event.values[1]);
        gravity[2] = (int) (alpha * gravity[2] + (1 - alpha) * event.values[2]);

        linear_acceleration[0] = (int) (event.values[0] - gravity[0]);
        linear_acceleration[1] = (int) (event.values[1] - gravity[1]);
        linear_acceleration[2] = (int) (event.values[2] - gravity[2]);

        if (linear_acceleration[0]>0){
            mSoundHelper.playMusic();
            //mSoundHelperTwo.playSoundHello();
        }
    }
*/
 }
