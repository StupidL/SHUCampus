package me.stupideme.shucampus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;
import me.stupideme.shucampus.API.APIs;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.MainRecyclerViewAdapter;
import me.stupideme.shucampus.model.Event;
import me.stupideme.shucampus.model.MyUser;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(getApplicationContext(), APIs.APPLICATION_ID);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View view = navigationView.getHeaderView(0);
        View view1 = view.findViewById(R.id.imageView);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        List<Event> list = new ArrayList<>(20);

        Event event = new Event();
        MyUser user = new MyUser();
        user.setUsername("StupidL");
        event.setAuthor(user);
//
//        event.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(null==e){
//                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fab.attachToRecyclerView(recyclerView);
        // recyclerView.setAdapter(new Arra);

//        ListView listView = (ListView) findViewById(R.id.list_view);
//        fab.attachToListView(listView);
//        listView.setAdapter(new ArrayAdapter<CardView>(this,android.R.layout.simple_list_item_1,list));

        RecyclerAdapter<Event> adapter = new RecyclerAdapter<Event>(this, list, R.layout.item_recycler_view) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, Event item) {
                final int position = helper.getOldPosition();
                helper.setImageResource(R.id.head_image, R.drawable.head_male)
                        .setText(R.id.head_name, "StupidL")
                        .setText(R.id.head_time, "04:02")
                        .setText(R.id.content, "Hello World")
                        .setImageResource(R.id.event_image, R.drawable.nav_header)
                        .getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Clicked Item" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };
       // recyclerView.setAdapter(adapter);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hot) {
            // Handle the camera action
        } else if (id == R.id.nav_mark) {

        } else if (id == R.id.nav_class) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_settings) {

            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        } else if (id == R.id.nav_feedback) {

            startActivity(new Intent(MainActivity.this,FeedbackActivity.class));

        } else if (id == R.id.nav_info) {

            startActivity(new Intent(MainActivity.this, AboutActivity.class));

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
