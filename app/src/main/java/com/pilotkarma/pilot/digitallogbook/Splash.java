package com.pilotkarma.pilot.digitallogbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by vsivajothy on 9/12/15.
 */
public class Splash extends Activity {

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
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }
}
