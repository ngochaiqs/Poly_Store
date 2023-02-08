package com.poly_store.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store.Interface.ItemClickListener;
import com.poly_store.R;
import com.poly_store.model.EventBus.SuaXoaEventNCC;
import com.poly_store.model.NhaCungCap;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NhaCungCapAdapter extends RecyclerView.Adapter<NhaCungCapAdapter.MyViewHolder> {
    Context context;
    List<NhaCungCap> nhaCungCapList;

    public NhaCungCapAdapter(Context context, List<NhaCungCap> nhaCungCapList) {
        this.context = context;
        this.nhaCungCapList = nhaCungCapList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhacungcap, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NhaCungCap nhaCungCap = nhaCungCapList.get(position);
        holder.tvMaNCC.setText(Integer.toString(nhaCungCap.getMaNCC()));
        holder.tvTenNCC.setText(nhaCungCap.getTenNCC());
        holder.tvDiaChiNCC.setText(nhaCungCap.getDiaChiNCC());
        holder.tvSDTNCC.setText(nhaCungCap.getSDTNCC());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void conClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick) {
                    //click
                } else {
                    EventBus.getDefault().postSticky(new SuaXoaEventNCC(nhaCungCap));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nhaCungCapList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        TextView tvMaNCC, tvTenNCC, tvDiaChiNCC, tvSDTNCC;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaNCC = itemView.findViewById(R.id.tvMaNCC);
            tvTenNCC = itemView.findViewById(R.id.tvTenNCC);
            tvDiaChiNCC = itemView.findViewById(R.id.tvDiaChiNCC);
            tvSDTNCC = itemView.findViewById(R.id.tvSDTNCC);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickListener.conClick(v, getAdapterPosition(), true);
                    return false;
                }
            });
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.conClick(view, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Chọn thao tác");
            contextMenu.add(0, 0, getAdapterPosition(), "Xóa");
            contextMenu.add(0, 1, getAdapterPosition(), "Sửa");
        }
    }
}
