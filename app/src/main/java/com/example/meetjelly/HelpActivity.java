package com.example.meetjelly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.help);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

}
