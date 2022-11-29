package com.poly_store.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store.R;
import com.poly_store.adapter.NhaCungCapAdapter;
import com.poly_store.model.EventBus.SuaXoaEventNCC;
import com.poly_store.model.NhaCungCap;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NhaCungCapActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_themNCC;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<NhaCungCap> nhaCungCapList;
    NhaCungCapAdapter nhaCungCapAdapter;
    NhaCungCap nhaCungCapSuaXoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_cung_cap);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        AnhXa();
        initToolbar();
        getNhaCungCap();
    }

    private void getNhaCungCap() {
            compositeDisposable.add(apiBanHang.getNhaCungCap()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nhaCungCapModel -> {
                        if (nhaCungCapModel.isSuccess()) {
                            nhaCungCapList = nhaCungCapModel.getResult();
                            nhaCungCapAdapter = new NhaCungCapAdapter(getApplicationContext(), nhaCungCapList);
                            recyclerView.setAdapter(nhaCungCapAdapter);
                        }
                    }, throwable -> {
                        Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    }));
        }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            suaNhaCungCap();
        }else if(item.getTitle().equals("Xóa")){
            xoaNhaCungCap();
        }
        return super.onContextItemSelected(item);
    }
    private void xoaNhaCungCap() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa sản phẩm này không?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final LoadingDialog loadingDialog = new LoadingDialog(NhaCungCapActivity.this);
                loadingDialog.startLoadingDialog();
                compositeDisposable.add(apiBanHang.xoaNhaCungCap(nhaCungCapSuaXoa.getMaNCC())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                nhaCungCapModel -> {
                                    if(nhaCungCapModel.isSuccess()){
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(getApplicationContext(),nhaCungCapModel.getMessage(),Toast.LENGTH_LONG).show();
                                        getNhaCungCap();
                                    }else {
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(getApplicationContext(),nhaCungCapModel.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                },throwable -> {
                                    loadingDialog.dismissDialog();
                                    Log.d("log",throwable.getMessage());
                                }
                        ));
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    private void suaNhaCungCap() {
        Intent intent = new Intent(getApplicationContext(),ThemNhaCCActivity.class);
        intent.putExtra("suaNCC", nhaCungCapSuaXoa);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEventNCC event){
        if(event != null ){
            nhaCungCapSuaXoa = event.getNhaCungCap();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toobarNhaCC);
        recyclerView = findViewById(R.id.recycleview_nhaCC);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        img_themNCC = findViewById(R.id.img_themNCC);
        img_themNCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemNhaCCActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}