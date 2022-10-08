package com.poly_store.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly_store.Interface.IImageClickListenner;
import com.poly_store.R;
import com.poly_store.model.EventBus.TinhTongEvent;
import com.poly_store.model.GioHang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTenspGH());
        holder.item_giohang_soluong.setText((gioHang.getSoluongGH() + ""));
        Glide.with(context).load(gioHang.getHinhspGH()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiaspGH())) );
        long gia = gioHang.getSoluongGH() * gioHang.getGiaspGH();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG","onImageClick:"+pos+"..."+giatri);
                if (giatri == 1 ) {
                    if (gioHangList.get(pos).getSoluongGH() > 1){
                    int soluongmoi = gioHangList.get(pos).getSoluongGH()-1;
                    gioHangList.get(pos).setSoluongGH(soluongmoi);
                }
                }else if(giatri == 2){
                if (gioHangList.get(pos).getSoluongGH() < 11){
                int soluongmoi = gioHangList.get(pos).getSoluongGH()+1;
                gioHangList.get(pos).setSoluongGH(soluongmoi);
                }

              }
                holder.item_giohang_soluong.setText((gioHangList.get(pos).getSoluongGH() + ""));
                long gia = gioHangList.get(pos).getSoluongGH() * gioHangList.get(pos).getGiaspGH();
                holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                EventBus.getDefault().postSticky(new TinhTongEvent());

            }


         });



    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        IImageClickListenner listenner;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById((R.id.item_giohang_gia));
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById((R.id.item_giohang_giasp2));
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);

            //event click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);


        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view ==imgtru){
            listenner.onImageClick(view,getAdapterPosition(),1);
            //tru1
        }else if (view ==imgcong){
                // 2 cong
            listenner.onImageClick(view, getAdapterPosition(),2);

        }
        }
    }
}
