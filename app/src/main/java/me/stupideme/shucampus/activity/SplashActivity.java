package me.stupideme.shucampus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import me.stupideme.shucampus.R;

/**
 * Created by StupidL on 2016/8/1.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);



        ImageView imageView = (ImageView) findViewById(R.id.image);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                String name = preferences.getString("UserName", null);
                String pwd = preferences.getString("Password", null);
                boolean hasExit = preferences.getBoolean("hasExit", true);

                if (name == null || pwd == null || hasExit) {
                    startActivity(new Intent(SplashActivity.this, LoginActivityMaterial.class));

                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                SplashActivity.this.finish();
            }
        };

        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f);
        scaleAnimation1.setDuration(1000);
        imageView.setAnimation(scaleAnimation1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
