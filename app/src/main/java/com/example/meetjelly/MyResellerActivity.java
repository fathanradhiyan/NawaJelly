package com.example.meetjelly;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyResellerActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.my_reseller);

    }

    private RecyclerView mRecyclerView;
    private MyResellerAdapter mMyResellerAdapter;
    private ArrayList<CardMyReseller> mMyResellerNameList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reseller);

        mRecyclerView = findViewById(R.id.rv2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMyResellerNameList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    public void parseJSON(){
        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        String role = prefs.getString("role", "-");

        String url = Setting.IP_SERVER + "agent/" + id ;

        final ProgressDialog pd = new ProgressDialog(MyResellerActivity.this);
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            final Boolean status = response.getBoolean("status");

                            if (status){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        JSONObject data = null;
                                        try {
                                            data = response.getJSONObject("data");
                                            JSONObject user = data.getJSONObject("user");
                                            JSONArray reseller = data.getJSONArray("reseller");

                                            for (int i=0; i < reseller.length(); i++){
                                                JSONObject jo = reseller.getJSONObject(i);

                                                String nama_depan = jo.getString("nama_depan");
                                                String nama_belakang = jo.getString("nama_belakang");

                                                mMyResellerNameList.add(new CardMyReseller(nama_depan + " " + nama_belakang));
                                            }

                                            mMyResellerAdapter = new MyResellerAdapter(MyResellerActivity.this, mMyResellerNameList);
                                            mRecyclerView.setAdapter(mMyResellerAdapter);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
}
