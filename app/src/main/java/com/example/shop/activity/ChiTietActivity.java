package com.example.shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.model.Giohang;
import com.example.shop.model.SanPhamMoi;
import com.example.shop.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp,giasp,mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionTooBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if(Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i = 0;i < Utils.manggiohang.size();i++){
                if(Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getGiasp()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if(flag == false){
                long gia = Long.parseLong(sanPhamMoi.getGiasp()) * soluong;
                Giohang giohang = new Giohang();
                giohang.setGiasp(gia);
                giohang.setSoluong(soluong);
                giohang.setIdsp(sanPhamMoi.getId());
                giohang.setTensp(sanPhamMoi.getTensp());
                giohang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(giohang);
            }

        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGiasp()) * soluong;
            Giohang giohang = new Giohang();
            giohang.setGiasp(gia);
            giohang.setSoluong(soluong);
            giohang.setIdsp(sanPhamMoi.getId());
            giohang.setTensp(sanPhamMoi.getTensp());
            giohang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(giohang);
        }
        int totalItem = 0;
        for(int i =0;i<Utils.manggiohang.size();i++){
            totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+ "Đ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(arrayAdapter);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        imghinhanh = findViewById(R.id.imgchitiet);
        spinner = findViewById(R.id.spinner);
        btnthem = findViewById(R.id.btnthemgiohang);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(giohang);
            }
        });


        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i =0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionTooBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i =0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        super.onResume();
    }
}