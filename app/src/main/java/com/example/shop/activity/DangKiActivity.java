package com.example.shop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop.R;
import com.example.shop.retrofit.ApiBanHang;
import com.example.shop.retrofit.RetrofitClient;
import com.example.shop.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKiActivity extends AppCompatActivity {
    EditText email,pass,repass,mobile,username;
    AppCompatButton btndangki;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        initView();
        initControll();
    }

    private void initControll() {
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangki();
            }
        });
    }

    private void dangki() {
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_reapss = repass.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        if(TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập email",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập pass",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_reapss)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập repass",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_mobile)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập mobile",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_username)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập username",Toast.LENGTH_SHORT).show();
        }else{
            if(str_pass.equals(str_reapss)){
                compositeDisposable.add(apiBanHang.dangki(str_email,str_pass,str_username,str_mobile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                           userModel -> {
                               if(userModel.isSuccess()){
                                   Toast.makeText(getApplicationContext(),"thành công",Toast.LENGTH_SHORT).show();
                               }else{
                                   Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
                               }
                           },
                           throwable -> {
                               Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                           }
                        ));
            }else{
                Toast.makeText(getApplicationContext(),"Email hoặc mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        mobile = findViewById(R.id.mobile);
        username = findViewById(R.id.username);
        btndangki = findViewById(R.id.btndangki);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}