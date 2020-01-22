package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.add_reseller);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        TextInputEditText firstname = (TextInputEditText) findViewById(R.id.firstname);
        TextInputEditText lastname = (TextInputEditText) findViewById(R.id.lastname);
        TextInputEditText username = (TextInputEditText) findViewById(R.id.username);
        TextInputEditText email = (TextInputEditText) findViewById(R.id.email);
        TextInputEditText pwd = (TextInputEditText) findViewById(R.id.pwd);
        TextInputEditText confirmpwd = (TextInputEditText) findViewById(R.id.pwd3);
        TextInputEditText phone = (TextInputEditText) findViewById(R.id.phone);


        String firstname2 = firstname.getText().toString();
        String lastname2 = lastname.getText().toString();
        String username2 = username.getText().toString();
        String email2 = email.getText().toString();
        String pwd2 = pwd.getText().toString();
        String phone2 = phone.getText().toString();
        String pwd3 = confirmpwd.getText().toString();

        if (firstname2.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.firstname_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastname2.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.lastname_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (username2.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.username_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (email2.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.email_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd2.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.pwd_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd3.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.confirm_pwd_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd2.equals(pwd3) == false){
            Toast.makeText(getApplicationContext(), R.string.pwd_not_match, Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone2.length() == 0){
            Toast.makeText(getApplicationContext(), R.string.phone_required,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("nama_depan", firstname2)
                .addFormDataPart("nama_belakang", lastname2)
                .addFormDataPart("email", email2)
                .addFormDataPart("username", username2)
                .addFormDataPart("password", pwd2)
                .addFormDataPart("no_telepon", phone2)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(Setting.IP_SERVER + "auth/register")
                .build();

        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
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
                        Toast.makeText(getApplicationContext(), "Please Check Your Connection",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();
                try{
                    final JSONObject j = new JSONObject(hasil);
                    boolean r = j.getBoolean("status");
                    if (r == true){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                AlertDialog.Builder ask = new AlertDialog.Builder(RegisterActivity.this);
                                ask.setIcon(R.drawable.ic_insert_emoticon_black_24dp);
                                ask.setTitle(R.string.account_created);
                                ask.setMessage(R.string.thanks);
                                ask.setPositiveButton(R.string.done, new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                                ask.create().show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                String m = null;
                                try{
                                    m = j.getString("message");
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "error cuk", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 1000);
    }
}
