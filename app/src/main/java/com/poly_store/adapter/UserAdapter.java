package com.poly_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store.R;
import com.poly_store.model.NguoiDung;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    Context context;
    List<NguoiDung> nguoiDungList;

    public UserAdapter(Context context, List<NguoiDung> nguoiDungList) {
        this.context = context;
        this.nguoiDungList = nguoiDungList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtid.setText(nguoiDungList.get(position).getMaND() + " ");
        holder.txtuser.setText(nguoiDungList.get(position).getTenND());

    }

    @Override
    public int getItemCount() {
        return nguoiDungList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtid, txtuser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.iduser);
            txtuser = itemView.findViewById(R.id.tenND);
        }
    }
}
