package com.example.meetjelly;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.meetjelly.OrderActivity.EXTRA_IMAGE;
import static com.example.meetjelly.OrderActivity.EXTRA_PRICE;
import static com.example.meetjelly.OrderActivity.EXTRA_VARIANT;

public class DetailOrderActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle("Detail");

    }

    int priceVariant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        Intent intent = getIntent();
        String imageVariant = intent.getStringExtra(EXTRA_IMAGE);
        String variant = intent.getStringExtra(EXTRA_VARIANT);
        priceVariant = intent.getIntExtra(EXTRA_PRICE, 0);

        ImageView imageView = findViewById(R.id.imageVariant_detail);
        TextView textViewVariant = findViewById(R.id.variantName_detail);
        TextView textViewPrice = findViewById(R.id.variantPrice_detail);

        Picasso.get().load(imageVariant).fit().centerInside().into(imageView);
        textViewVariant.setText(variant);
        textViewPrice.setText("Harga : Rp " + priceVariant + ",-");
    }



    public void DoneOrder(View view) {
        runOnUiThread(new Runnable() {
            EditText orderSize = findViewById(R.id.orderSize);
            String orderSize2 = orderSize.getText().toString();

            @Override
            public void run() {
                int orderSize3 = Integer.parseInt(orderSize2);
                int result = priceVariant * orderSize3;


                Intent i = new Intent();
                i.putExtra("addPrice", result);
                setResult(RESULT_OK, i);
                finish();
            }
        });



    }


}
