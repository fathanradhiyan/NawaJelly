package com.example.meetjelly;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
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

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
//mport okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private NotificationAdapter mNotificationAdapter;
    private ArrayList<Notification> mNotificationList;
    private RequestQueue mRequestQueue;


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_notifications, container, false);
        mRecyclerView = x.findViewById(R.id.rv_notification_frag);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(lm);

        mNotificationList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        parseJSON();
        return x;
    }

    public void parseJSON(){
        SharedPreferences prefs = getActivity().getSharedPreferences("DATA_lOGIN", Context.MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        String role = prefs.getString("role", "-");

        String url = Setting.IP_SERVER + "notif";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            final Boolean status = response.getBoolean("status");

                            if (status) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject data = null;
                                        try {
                                            data = response.getJSONObject("data");

                                            String subject = data.getString("subject");
                                            String message = data.getString("pesan");

                                            mNotificationList.add(new Notification(subject, message));
                                            mNotificationAdapter = new NotificationAdapter(getActivity().getApplicationContext(), mNotificationList);
                                            mRecyclerView.setAdapter(mNotificationAdapter);
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
                Toast.makeText(getActivity().getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

}


//        OkHttpClient postman = new OkHttpClient();
//
//        final Request request = new Request.Builder()
//                .get()
//                .url(Setting.IP_SERVER + "notif")
//                .build();
//
//        final ProgressDialog pd = new ProgressDialog(getActivity());
//        pd.setTitle("Processing...");
//        pd.setMessage("Please Wait...");
//        pd.setCancelable(false);
//        pd.show();
//
//        postman.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd.dismiss();
//                        Toast.makeText(getActivity(), "Please Check your Connection", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd.dismiss();
//                        JSONObject data = null;
//
//                        try{
//                            JSONObject jo = data.getJSONObject("data");
//
//                            String subject = jo.getString("subject");
//                            String message = jo.getString("pesan");
//
//                            mNotificationList.add(new Notification(subject, message));
//                            mNotificationAdapter = new NotificationAdapter(getActivity().getApplicationContext(), mNotificationList);
//                            mRecyclerView.setAdapter(mNotificationAdapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
