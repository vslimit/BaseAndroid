package com.vslimit.baseandroid.app.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.vslimit.baseandroid.app.R;
import com.vslimit.baseandroid.app.utils.PreferenceUtils;

public class SplashScreenActivity extends BaseActivity {

    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Splash screen view
        setContentView(R.layout.splash);

        // Start animating the image
        getActionBar().hide();
        final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
        splashImageView.setBackgroundResource(R.drawable.flag);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();
        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });


        final SplashScreenActivity sPlashScreen = this;

        // The thread to wait for splash screen events
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(5000);
                    }
                } catch (InterruptedException ex) {
                    Log.d("ERROR:", ex.toString());
                }

                finish();

                // Run next activity
                Intent intent = new Intent();
                boolean isPlayed = PreferenceUtils.get(SplashScreenActivity.this,
                        PreferenceUtils.FUNCTION_SCROLLER_PLAYED, false);
                Class clz = !isPlayed ? SplashScrollerActivity.class : BaseTabActivity.class;
                intent.setClass(sPlashScreen, clz);
                startActivity(intent);
//    			stop();
            }
        };

        mSplashThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return false;
    }

    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }


}
