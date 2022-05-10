package com.example.shop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.adapter.LoaiSpAdapter;
import com.example.shop.model.LoaiSp;
import com.example.shop.retrofit.ApiBanHang;
import com.example.shop.retrofit.RetrofitClient;
import com.example.shop.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        ActionBar();
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getLoaiSanPham();
        }else{
            Toast.makeText(getApplicationContext(),"không co internet",Toast.LENGTH_LONG).show();
        }
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                loaiSpModel -> {
                    if(loaiSpModel.isSuccess()){
                        mangloaisp = loaiSpModel.getResult();
                        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
                        listViewManHinhChinh.setAdapter(loaiSpAdapter);
                    }
                }
        ));
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/Files/2020/04/28/1252401/maxresdefault_800x450.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2017/03/09/958798/son-tung-oppo-f3_800x450.jpg");
        mangquangcao.add("https://cdn.sforum.vn/sforum/wp-content/uploads/2020/08/OPPO-F17-1.jpg");
        for(int i = 0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toobarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerView);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        mangloaisp = new ArrayList<>();

        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
        listViewManHinhChinh.setAdapter(loaiSpAdapter);
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobie = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || (mobie != null && mobie.isConnected())){
            return true;
        }else{
            return false;
        }
    }

}