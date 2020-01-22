package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.edit_profile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final TextInputEditText mEditFirstName = findViewById(R.id.firstname);
        final TextInputEditText mEditLastName = findViewById(R.id.lastname);
        final TextInputEditText mEditEmail = findViewById(R.id.email);
        final TextInputEditText mEditPhone = findViewById(R.id.phone);
        final TextInputEditText mEditAddress = findViewById(R.id.address);

        String mEditFirstName2 = mEditFirstName.getText().toString();
        String mEditLastName2 = mEditLastName.getText().toString();
        String mEditEmail2 = mEditEmail.getText().toString();
        String mEditPhone2 = mEditPhone.getText().toString();
        String mEditAddress2 = mEditAddress.getText().toString();

        OkHttpClient postman = new OkHttpClient();

        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        final String role = prefs.getString("role", "-");

        Request request = new Request.Builder()
                .get()
                .url(Setting.IP_SERVER + role + "/" + id + "")
                .build();

        final ProgressDialog pd = new ProgressDialog(EditActivity.this);
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Error catching data!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
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

                            mEditFirstName.setText(nama_depan);
                            mEditLastName.setText(nama_belakang);
                            mEditEmail.setText(email);
                            mEditPhone.setText(no_telepon);
                            mEditAddress.setText(address);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void updateProfile(View view) {
        TextInputEditText mEditFirstName = findViewById(R.id.firstname);
        TextInputEditText mEditLastName = findViewById(R.id.lastname);
        TextInputEditText mEditEmail = findViewById(R.id.email);
        TextInputEditText mEditPhone = findViewById(R.id.phone);
        TextInputEditText mEditAddress = findViewById(R.id.address);

        String mEditFirstName2 = mEditFirstName.getText().toString();
        String mEditLastName2 = mEditLastName.getText().toString();
        String mEditEmail2 = mEditEmail.getText().toString();
        String mEditPhone2 = mEditPhone.getText().toString();
        String mEditAddress2 = mEditAddress.getText().toString();

        OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("nama_depan", mEditFirstName2)
                .addFormDataPart("nama_belakang", mEditLastName2)
                .addFormDataPart("email", mEditEmail2)
                .addFormDataPart("username", mEditEmail2)
                .addFormDataPart("no_telepon", mEditPhone2)
                .addFormDataPart("alamat", mEditAddress2)
                .build();

        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        final String role = prefs.getString("role", "-");

        Request request = new Request.Builder()
                .put(body)
                .url(Setting.IP_SERVER + role + "/" + id + "")
                .build();

        final ProgressDialog pd = new ProgressDialog(EditActivity.this);
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "please Check Your Connection",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Data is successfully updated", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                });
            }
        });

        Intent i = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
