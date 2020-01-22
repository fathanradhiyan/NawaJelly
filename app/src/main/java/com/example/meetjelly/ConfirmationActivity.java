package com.example.meetjelly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.confirmation);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        TextView confirm_currentDate = findViewById(R.id.currentDate);
        TextView confirm_dateTaken = findViewById(R.id.dateTaken);
        TextView confirm_variant = findViewById(R.id.variantName);

        Bundle bundle = getIntent().getExtras();
        String variantList = bundle.getString("variant");
        String currentDate = bundle.getString("currentDate");
        String dateTaken = bundle.getString("dateTaken");

         confirm_currentDate.setText(currentDate);
         confirm_dateTaken.setText(dateTaken);
         confirm_variant.setText(variantList);

    }

    public void toPayment(View view) {
        Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
        startActivity(i);
    }
}
