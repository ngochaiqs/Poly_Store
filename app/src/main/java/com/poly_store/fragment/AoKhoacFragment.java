package com.poly_store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store.R;
import com.poly_store.adapter.SanPhamAdapter;
import com.poly_store.model.SanPham;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AoKhoacFragment extends Fragment {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recyclerView;
    List<SanPham> sanPhamList;
    SanPhamAdapter sanPhamAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ao_khoac, container, false);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        recyclerView = view.findViewById(R.id.recyclerviewAoKhoac);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getSanPham();

        return view;
    }

    private void getSanPham() {
        compositeDisposable.add(apiBanHang.getAoKhoac()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sanPhamModel -> {
                    if (sanPhamModel.isSuccess()) {
                        sanPhamList = sanPhamModel.getResult();
                        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList);
                        recyclerView.setAdapter(sanPhamAdapter);
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }));
    }
}