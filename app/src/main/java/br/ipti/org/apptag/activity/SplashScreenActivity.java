package br.ipti.org.apptag.activity;

/**
 * Created by adrianodiasx93 on 3/13/16.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ipti.apptag.R;

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashScreenActivity.this, WebViewActivity.class));
//                SplashScreenActivity.this.overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}