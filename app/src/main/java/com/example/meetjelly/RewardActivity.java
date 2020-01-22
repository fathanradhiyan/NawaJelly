package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RewardActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.reward);
    }

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mResellerNumber = 0;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mLoadingText = (TextView) findViewById(R.id.loadingComplete);

        OkHttpClient postman = new OkHttpClient();

        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        final String role = prefs.getString("role", "-");

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP_SERVER + role + "/" + id + "")
                .build();

//        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
//        pd.setTitle("Processing...");
//        pd.setMessage("Please Wait...");
//        pd.setCancelable(false);
//        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Error catching data!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // pd.dismiss();
                        String hasil = null;
                        try{
                            hasil = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject j = new JSONObject(hasil);
                            Boolean r = j.getBoolean("status");
                            JSONObject data = j.getJSONObject("data");
                            JSONObject user = data.getJSONObject("user");
                            JSONArray reseller = data.getJSONArray("reseller");

                            Integer reseller_num = reseller.length();

                            mResellerNumber = reseller_num;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (mProgressStatus < mResellerNumber){
                                        mProgressStatus++;
                                        android.os.SystemClock.sleep(50);
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressBar.setProgress(mProgressStatus);
                                            }
                                        });
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mLoadingText.setVisibility(View.VISIBLE);
                                            mLoadingText.setText("Total Sales : " +mResellerNumber);
                                        }
                                    });
                                }
                            }).start();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }
}
