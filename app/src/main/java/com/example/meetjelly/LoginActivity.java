package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        TextInputEditText email = (TextInputEditText) findViewById(R.id.email);
        TextInputEditText pwd = (TextInputEditText) findViewById(R.id.pwd);

        final String email2 = email.getText().toString();
        final String pwd2 = pwd.getText().toString();

        if (email2.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.email_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd2.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.pwd_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Log.d("email", email2);
        //Log.d("pwd", pwd2);
        final OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email2)
                .addFormDataPart("password", pwd2)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(Setting.IP_SERVER + "auth")
                .build();

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        //kalau tidak ada koneksi
        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
//                        Toast.makeText(getApplicationContext(), "please Check Your Connection",
//                                Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this,e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();
                try {
                    final JSONObject j = new JSONObject(hasil);
                    boolean r = j.getBoolean("status");
                    if (r) {
                        final JSONObject data = j.getJSONObject("data");
                        final JSONObject user = data.getJSONObject("user");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                String role = null;
                                int id = 0;

                                try {
                                    role = user.getString("role");
                                    id = user.getInt("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                assert role != null;
                                switch (role) {
                                    case "agent":
                                    case "reseller": {
                                        SharedPreferences.Editor sp = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE).edit();
                                        sp.putString("dataemail", email2);
                                        sp.putString("role", role);
                                        sp.putInt("id", id);
                                        sp.putString("password", pwd2);
                                        sp.commit();
                                        finish();

                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        i.putExtra("role", role);
                                        i.putExtra("id", id + "");
                                        // i.putExtra("data", dataUser);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "" + role + "" + id, Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case "cashier":
                                    case "courier": {
                                        SharedPreferences.Editor sp = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE).edit();
                                        sp.putString("dataemail", email2);
                                        sp.putString("role", role);
                                        sp.putInt("id", id);
                                        sp.putString("password", pwd2);
                                        sp.commit();
                                        finish();

                                        Intent i = new Intent(getApplicationContext(), CashierActivity.class);
                                        i.putExtra("role", role);
                                        i.putExtra("id", id + "");
                                        // i.putExtra("data", dataUser);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "" + role + "" + id, Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    default:
                                        Toast.makeText(LoginActivity.this, "role belum terbaca"
                                                , Toast.LENGTH_SHORT).show();
                                        return;
                                }

                                SharedPreferences.Editor sp = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE).edit();
                                sp.putString("dataemail", email2);
                                sp.putString("role", role);
                                sp.putInt("id", id);
                                sp.putString("password", pwd2);
                                sp.commit();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                int code = 0;
                                try {
                                    code = j.getInt("code");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(), "" + code, Toast.LENGTH_SHORT).show();

                                if (code == 1) {
                                    Toast.makeText(getApplicationContext(), R.string.invalid_username_email, Toast.LENGTH_LONG).show();
                                } else if (code == 2) {
                                    Toast.makeText(getApplicationContext(), R.string.not_activated_email, Toast.LENGTH_LONG).show();
                                } else if (code == 3) {
                                    Toast.makeText(getApplicationContext(), R.string.incorrect_pwd, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void forgot(View view) {
        Intent i = new Intent(getApplicationContext(), ForgotActivity.class);
        startActivity(i);
    }
}
