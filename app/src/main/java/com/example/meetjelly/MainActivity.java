package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    boolean clickback = false;

    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            getSupportFragmentManager().popBackStackImmediate();
            return;
        }

        if (clickback == true){
            super.onBackPressed();
            return;
        }

        clickback = true;
        Toast.makeText(getApplicationContext(), getString(R.string.pressTwice),
                Toast.LENGTH_SHORT).show();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickback = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String role = getIntent().getStringExtra("role");
//        int id = getIntent().getIntExtra("id", 0);

        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);

        if (id > 0){
            //Toast.makeText(getApplicationContext(), "Welcome " + role + "" + id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "" + id, Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment selectedFragment = null;
        Bundle data = new Bundle();
        data.putString("role", role);
        data.putInt("id", id);

        selectedFragment = new HomeFragment();
        selectedFragment.setArguments(data);
        getSupportFragmentManager()
                .beginTransaction()
                .replace ( R.id.frame, selectedFragment)
                .commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                Bundle data = new Bundle();
                data.putString("role", role);

                switch (menuItem.getItemId()){
                     case R.id.home_nav:
                         selectedFragment = new HomeFragment();
                         selectedFragment.setArguments(data);
                         break;
                     case R.id.notifications_nav:
                         selectedFragment = new NotificationsFragment();
                         selectedFragment.setArguments(data);
                         break;
                     case R.id.profile_nav:
                         selectedFragment = new ProfileFragment();
                         selectedFragment.setArguments(data);
                         break;
                     default:
                         selectedFragment = new HomeFragment();
                         selectedFragment.setArguments(data);
                 }

                selectedFragment.setArguments(data);
                getSupportFragmentManager()
                         .beginTransaction()
                         .replace ( R.id.frame, selectedFragment)
                         .commit();

                 return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                getSharedPreferences("DATA_LOGIN", 0)
                        .edit().clear().apply();

                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;

            case R.id.menuExit:
                AlertDialog.Builder ask = new AlertDialog.Builder(MainActivity.this);
                ask.setTitle(R.string.confirm_exit);
                ask.setMessage(R.string.ays);
                ask.setIcon(R.drawable.ic_mood_bad_black_24dp);
                ask.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                });

                ask.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                ask.create().show();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}













/*
        //untuk fab speed dial
        FabSpeedDial fab = findViewById(R.id.fab_speed_dial);

        fab.setMenuListener(new SimpleMenuListenerAdapter(){
            @Override
            public boolean onMenuItemSelected (MenuItem menuItem){
                if (menuItem.getItemId() == R.id.signup){
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(i);
                }else if (menuItem.getItemId() == R.id.profile){
                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(i);
                }else if (menuItem.getItemId() == R.id.myReseller){
                    Intent i = new Intent(getApplicationContext(), MyResellerActivity.class);
                    startActivity(i);
                }else if (menuItem.getItemId() == R.id.order){
                    Intent i = new Intent(getApplicationContext(), OrderActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
*/