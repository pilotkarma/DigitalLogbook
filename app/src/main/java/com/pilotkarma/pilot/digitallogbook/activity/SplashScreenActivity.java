package com.pilotkarma.pilot.digitallogbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pilotkarma.pilot.digitallogbook.R;

/**
 * Created by vsivajothy on 9/12/15.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final ImageView propplerIV = (ImageView)findViewById(R.id.propplerImageview);
        final Animation propplerAnimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.proppler_animation);

        propplerIV.startAnimation(propplerAnimation);
        propplerAnimation.setRepeatCount(10);

        propplerAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(),CameraActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }
}
