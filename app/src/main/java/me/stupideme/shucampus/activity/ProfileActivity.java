package me.stupideme.shucampus.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.stupideme.shucampus.R;

/**
 * Created by 56211 on 2016/8/2.
 */
public class ProfileActivity extends AppCompatActivity{

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.profile_activity_toolbar);
        toolbar.setTitle("个人资料");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
}
