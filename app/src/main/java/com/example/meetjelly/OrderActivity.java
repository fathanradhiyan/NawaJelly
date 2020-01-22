package com.example.meetjelly;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class OrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, VariantAdapter.OnItemClickListener {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.order);

    }

    int request_Code = 1;

    public static final String EXTRA_IMAGE = "gambar";
    public static final String EXTRA_VARIANT = "variant";
    public static final String EXTRA_PRICE = "harga";

    private RecyclerView mRecyclerView;
    private VariantAdapter mVariantAdapter;
    private ArrayList<Variant> mVariantList;
    private RequestQueue mRequestQueue;
    private ArrayList<Integer> mCartList;

    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        totalPrice = findViewById(R.id.totalPrice);

        SharedPreferences prefs = getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);
        String role = prefs.getString("role", "-");

        mRecyclerView = findViewById(R.id.rv1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mVariantList = new ArrayList<>();

        mCartList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        //untuk pengambilan tanggal
        Button button = findViewById(R.id.pickDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.currentDate);
        textViewDate.setText(currentDate);
    }

    private void parseJSON(){
        String url = Setting.IP_SERVER + "product";

        final ProgressDialog pd = new ProgressDialog(OrderActivity.this);
        pd.setTitle("Processing...");
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        try {
                            JSONObject data_product = response.getJSONObject("data");
                            JSONArray products = data_product.getJSONArray("product");

                            for (int i = 0; i < products.length(); i++){
                                JSONObject product = products.getJSONObject(i);

                                String imageVariant = product.getString("gambar");
                                String variantName = product.getString("variant");
                                int variantPrice = product.getInt("harga");

                                mVariantList.add(new Variant(imageVariant, variantName, variantPrice));
                            }

                            mVariantAdapter = new VariantAdapter(OrderActivity.this, mVariantList);
                            mRecyclerView.setAdapter(mVariantAdapter);
                            mVariantAdapter.setOnItemClickListener(OrderActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView dateTaken = findViewById(R.id.dateTaken);
        dateTaken.setText(currentDateString);
    }

    public void order(View view) {
        TextView currentDate = findViewById(R.id.currentDate);
        TextView dateTaken = findViewById(R.id.dateTaken);

        String currentDate2 = currentDate.getText().toString();
        String dateTaken2 = dateTaken.getText().toString();
        String totalPrice2 = totalPrice.getText().toString();

//        OkHttpClient postman = new OkHttpClient();
//
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart()

        Intent i = new Intent(getApplicationContext(),PaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("currentDate", currentDate2);
        bundle.putString("dateTaken", dateTaken2);
        bundle.putString("totalPrice", totalPrice2);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailOrderActivity.class);
        Variant clickedItem = mVariantList.get(position);

        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getmImageVariant());
        detailIntent.putExtra(EXTRA_VARIANT, clickedItem.getmVariant());
        detailIntent.putExtra(EXTRA_PRICE, clickedItem.getmVariantPrice());

        startActivityForResult(detailIntent, request_Code);
    }

    int cart = 0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_Code) {
            if (resultCode == RESULT_OK) {
                mCartList.add(data.getIntExtra("addPrice", 0));

                for (int i=0; i<mCartList.size(); i++){
                    cart =+ mCartList.get(i);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        totalPrice.setText(String.valueOf(cart));
                    }
                });

            }
        }
    }
}
