package me.stupideme.shucampus.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EventsFragment eventsFragment;
    private MarkFragment markFragment;
    private CoursesFragment coursesFragment;
    private RemindersFragment remindersFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View view = navigationView.getHeaderView(0);
        View head = view.findViewById(R.id.imageView);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NavProfileActivity.class));
            }
        });

        //add events fragment
        eventsFragment = EventsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, eventsFragment).commit();
        markFragment = MarkFragment.newInstance();
        coursesFragment = CoursesFragment.newInstance();
        remindersFragment = RemindersFragment.newInstance();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CampusApp.eventList.clear();
        CampusApp.commentList.clear();
        CampusApp.markedEventList.clear();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_notification) {
            startActivity(new Intent(MainActivity.this, NotifyActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_hot) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, eventsFragment).commit();

        } else if (id == R.id.nav_mark) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, markFragment).commit();

        } else if (id == R.id.nav_class) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CoursesFragment()).commit();

        } else if (id == R.id.nav_reminder) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, remindersFragment).commit();

        } else if (id == R.id.nav_settings) {

            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        } else if (id == R.id.nav_feedback) {

            startActivity(new Intent(MainActivity.this, NavFeedbackActivity.class));

        } else if (id == R.id.nav_info) {

            startActivity(new Intent(MainActivity.this, NavAboutActivity.class));

        } else if (id == R.id.nav_exit) {

            SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("hasExit", true);
            editor.apply();
            startActivity(new Intent(MainActivity.this, LoginActivityMaterial.class));
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
