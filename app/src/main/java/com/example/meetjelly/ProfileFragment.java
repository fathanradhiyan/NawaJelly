package com.example.meetjelly;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView mProfileName;
    private TextView mProfileEmail;
    private TextView mProfilePhone;
    private TextView mProfileAddress;
    private TextView mResellerNumber;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

//        Integer id = getArguments().getInt("id");
//        final String[] role = {getArguments().getString("role")};

        mProfileName = v.findViewById(R.id.profileName);
        mProfileEmail = v.findViewById(R.id.profileEmail);
        mProfilePhone = v.findViewById(R.id.profilePhone);
        mProfileAddress = v.findViewById(R.id.profileAddress);
        mResellerNumber = v.findViewById(R.id.resellerNumber);

        OkHttpClient postman = new OkHttpClient();

        SharedPreferences prefs = getActivity().getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        final String role = prefs.getString("role", "-");

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP_SERVER + role + "/" + id + "")
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Error catching data!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        assert response.body() != null;
                        String hasil = null;
                        try {
                            hasil = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject j = new JSONObject(hasil);
                            Boolean r = j.getBoolean("status");
                            JSONObject data = j.getJSONObject("data");
                            JSONObject user = data.getJSONObject("user");


                            String nama_depan = user.getString("nama_depan");
                            String nama_belakang = user.getString("nama_belakang");
                            String email = user.getString("email");
                            String no_telepon = user.getString("no_telepon");
                            String address = user.getString("alamat");
                            //

                            Toast.makeText(getActivity(), "" + role, Toast.LENGTH_SHORT).show();
//
//                            Log.d("Test", nama_depan);

                            mProfileName.setText(nama_depan + " " + nama_belakang);
                            mProfileEmail.setText(email);
                            mProfilePhone.setText(no_telepon);
                            mProfileAddress.setText(address);
                            //

                            if (role.equals("agent")) {
                                JSONArray reseller = data.getJSONArray("reseller");

                                Integer reseller_num = reseller.length();

                                mResellerNumber.setText(reseller_num + " Reseller");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


        Button editProfile = v.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), EditActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

}
