package com.example.meetjelly;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class CashierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameCashier, new TabFragment())
                .commit();
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
                        .edit().clear().commit();

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.menuExit:
                AlertDialog.Builder ask = new AlertDialog.Builder(CashierActivity.this);
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
/*
    @Override
    public void onItemClick(int position) {
        Intent i = new Intent (this, CashierDetailActivity.class);
        CardCashier clickedItem = mCashierList.get(position);

        i.putExtra(EXTRA_EMAIL, clickedItem.getmEmail());
        i.putExtra(EXTRA_CODE, clickedItem.getmCode());

        startActivity(i);
    }*/
}
