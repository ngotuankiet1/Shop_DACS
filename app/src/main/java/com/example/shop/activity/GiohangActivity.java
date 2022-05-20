package com.example.shop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.shop.R;
import com.example.shop.adapter.GioHangAdapter;
import com.example.shop.model.Giohang;
import com.example.shop.utils.Utils;

import java.util.List;

public class GiohangActivity extends AppCompatActivity {
    TextView giohangtrong,tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    List<Giohang> giohangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        initView();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else{
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang );
            recyclerView.setAdapter(adapter);
        }


    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recyclerViewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
    }
}