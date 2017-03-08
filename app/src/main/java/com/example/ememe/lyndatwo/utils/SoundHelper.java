package com.example.ememe.lyndatwo.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.ememe.lyndatwo.R;

/**
 * Created by ememe on 3/7/2017.
 */

//first insert .wav or .mp3 to the res directory

//backend functions for music

public class SoundHelper {

    //create object
    private MediaPlayer mMusicPlayer;
    private SoundPool mSoundPool;
    private int mSoundID;
    private boolean mLoaded;
    private float mVolume;

    public SoundHelper(Activity activity) {

        //create object
        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        //determine best volume
        //stream volume
        float actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //max volume
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //best volume
        mVolume = actVolume / maxVolume;

        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //if more recent version of android (lollipop or later) do this method
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mSoundPool = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(6).build();
        }
        //if older version of android do this
        else {
            //noinspection deprecation
            mSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        //load sound object
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mLoaded = true;
            }
        });


        //music file
        mSoundID = mSoundPool.load(activity, R.raw.shorterbeep, 1);
    }

    //play the sound
    public void playSound() {
        //make sure that there is sound in the object
        if (mLoaded) {
            //if loaded play sound
            //play(soundID, left volume, right volume, priority, if loop or not, speed of sound 1=original)
            mSoundPool.play(mSoundID, mVolume, mVolume, 1, 0, 1f);
        }
    }

    //function in class to play background music
    //creates the object
    public void prepareMusicPlayer(Context context) {
        //place context.getapp etc so its in the entire application not just the xlm page
        //less glitchy
        //create media player
        mMusicPlayer = MediaPlayer.create(context.getApplicationContext(),
                R.raw.hello);
        //set left and right volume of the background music
        mMusicPlayer.setVolume(1f, 1f);
    }

    //method to play the music in the music player
    public void playMusic() {
        //make sure there is a song in the musicPlayer object
        if (mMusicPlayer != null) {
            //start the music
            mMusicPlayer.start();
        }
    }
}
