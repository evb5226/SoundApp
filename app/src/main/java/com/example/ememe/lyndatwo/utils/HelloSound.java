package com.example.ememe.lyndatwo.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.ememe.lyndatwo.R;

/**
 * Created by ememe on 3/8/2017.
 */

//first insert .wav or .mp3 to the res directory

//backend functions for music

public class HelloSound {

    //create object
    private SoundPool mHello;
    private int mSoundIDtwo;
    private boolean mLoadedtwo;
    private float mVolumetwo;

    public HelloSound(Activity activity) {

        //create object
        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        //determine best volume
        //stream volume
        float actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //max volume
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //best volume
        mVolumetwo = actVolume / maxVolume;

        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //if more recent version of android (lollipop or later) do this method
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mHello = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(6).build();
        }
        //if older version of android do this
        else {
            //noinspection deprecation
            mHello = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        //load sound object
        mHello.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mLoadedtwo = true;
            }
        });


        //music file
        mSoundIDtwo = mHello.load(activity, R.raw.hello, 1);
    }

    //play the sound
    public void playSoundHello() {
        //make sure that there is sound in the object
        if (mLoadedtwo) {
            //if loaded play sound
            //play(soundID, left volume, right volume, priority, if loop or not, speed of sound 1=original)
            mHello.play(mSoundIDtwo, mVolumetwo, mVolumetwo, 1, 0, 1f);
        }
    }
}
