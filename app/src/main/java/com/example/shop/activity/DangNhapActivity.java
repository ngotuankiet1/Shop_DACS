package com.example.shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shop.R;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }

    private void initControl() {
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangKiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        txtdangki = findViewById(R.id.txtdangki);
    }
}