package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

public class OrderListActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.order_list);

    }

    private RecyclerView mRecyclerView;
    private OrderListAdapter mOrderListAdapter;
    private ArrayList<OrderList> mOrderList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mRecyclerView = findViewById(R.id.rv_orderList);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrderList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        String role = prefs.getString("role", "-");

        String url = Setting.IP_SERVER + role +"/"+ id;

        final ProgressDialog pd = new ProgressDialog(OrderListActivity.this);
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");

                            if (status) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        JSONObject data = null;

                                        try {
                                            data = response.getJSONObject("data");
                                            JSONObject user = data.getJSONObject("user");
                                            JSONArray order = user.getJSONArray("order");

                                            for (int i=0; i < order.length(); i++){
                                                JSONObject jo = order.getJSONObject(i);

                                                String dateOrder = jo.getString("tanggal");
                                                String time = jo.getString("jam");
                                                String transaction = jo.getString("metode");
                                                String totalPrice = jo.getString("harga");
                                                Boolean penerimaan = jo.getBoolean("penerimaan");
                                               if (penerimaan.equals(false)){
                                                    mOrderList.add(new OrderList(dateOrder, time, transaction, totalPrice));
                                               }

                                            }
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mOrderListAdapter = new OrderListAdapter(OrderListActivity.this, mOrderList);
                                                    mRecyclerView.setAdapter(mOrderListAdapter);
                                                }
                                            });


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
                Toast.makeText(getApplicationContext(), "errorwoy", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
}
