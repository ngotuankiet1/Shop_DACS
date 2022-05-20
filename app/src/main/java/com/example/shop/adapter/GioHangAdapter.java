package com.example.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.model.Giohang;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<Giohang> giohangList;


    public GioHangAdapter(Context context, List<Giohang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Giohang giohang = giohangList.get(position);
        holder.item_giohang_tensp.setText(giohang.getTensp());
        holder.item_giohang_soluong.setText(giohang.getSoluong() +"");
        Glide.with(context).load(giohang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText("Giá: "+decimalFormat.format((giohang.getGiasp()))+ "Đ" );
        long gia = giohang.getSoluong()*giohang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia)+ "Đ");
    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView item_giohang_image;
        TextView item_giohang_tensp,item_giohang_gia,item_giohang_soluong,item_giohang_giasp2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
        }
    }
}
