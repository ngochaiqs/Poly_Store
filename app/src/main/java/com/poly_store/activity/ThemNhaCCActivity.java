package com.poly_store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poly_store.databinding.ActivityThemNhaCcactivityBinding;
import com.poly_store.model.NhaCungCap;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemNhaCCActivity extends AppCompatActivity {
    ActivityThemNhaCcactivityBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    NhaCungCap nhaCungCapSuaXoa;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemNhaCcactivityBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        setContentView(binding.getRoot());
        initData();
        ActionToolBar();

        Intent intent = getIntent();
        nhaCungCapSuaXoa = (NhaCungCap) intent.getSerializableExtra("suaNCC");

        if(nhaCungCapSuaXoa == null ){
            // them moi
            flag = false;
        }else {
            // sua
            flag = true;
            binding.btnThemNCC.setText("Sửa nhà cung cấp");
            binding.toolbarThemNCC.setTitle("Sửa Nhà Cung Cấp");
//             show data
            binding.edtDiaChiNCC.setText(nhaCungCapSuaXoa.getDiaChiNCC());
            binding.edtSDTNCC.setText(nhaCungCapSuaXoa.getSDTNCC());
            binding.edtTenNCC.setText(nhaCungCapSuaXoa.getTenNCC());
        }


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void ActionToolBar() {
        setSupportActionBar(binding.toolbarThemNCC);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarThemNCC.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData(){
        binding.btnThemNCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == false){
                    themNCC();
                }else {
                    suaNCC();
                }
            }
        });
        binding.edtTenNCC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        binding.edtSDTNCC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        binding.edtDiaChiNCC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        binding.edtTenNCC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilTenNCC.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.edtSDTNCC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilSDTNCC.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.edtDiaChiNCC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilDiaChiNCC.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void suaNCC() {
        String str_ten = binding.edtTenNCC.getText().toString().trim();
        String str_sdt = binding.edtSDTNCC.getText().toString().trim();
        String str_diachi = binding.edtDiaChiNCC.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten)) {
            binding.tilTenNCC.setError("Vui lòng nhập tên nhà cung cấp!");
        }else if(TextUtils.isEmpty(str_sdt)) {
            binding.tilSDTNCC.setError("Vui lòng nhập số điện thoại!");
        }else if(TextUtils.isEmpty(str_diachi)) {
            binding.tilDiaChiNCC.setError("Vui lòng nhập địa chỉ!");
        } else {
            final LoadingDialog loadingDialog = new LoadingDialog(ThemNhaCCActivity.this);
            loadingDialog.startLoadingDialog();
            compositeDisposable.add(apiBanHang.suaNCC(str_ten,str_sdt,str_diachi,nhaCungCapSuaXoa.getMaNCC())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            nhaCungCapModel -> {
                                if (nhaCungCapModel.isSuccess()){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), nhaCungCapModel.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ThemNhaCCActivity.this, NhaCungCapActivity.class);
                                    startActivity(intent);
                                }else {
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), nhaCungCapModel.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void themNCC() {
        String str_ten = binding.edtTenNCC.getText().toString().trim();
        String str_sdt = binding.edtSDTNCC.getText().toString().trim();
        String str_diachi = binding.edtDiaChiNCC.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten)) {
            binding.tilTenNCC.setError("Vui lòng nhập tên nhà cung cấp!");
        }else if(TextUtils.isEmpty(str_sdt)) {
            binding.tilSDTNCC.setError("Vui lòng nhập số điện thoại!");
        }else if(TextUtils.isEmpty(str_diachi)) {
            binding.tilDiaChiNCC.setError("Vui lòng nhập địa chỉ!");
        } else {
            final LoadingDialog loadingDialog = new LoadingDialog(ThemNhaCCActivity.this);
            loadingDialog.startLoadingDialog();
            compositeDisposable.add(apiBanHang.themNCC(str_ten,str_sdt,str_diachi)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ThemNhaCCActivity.this, NhaCungCapActivity.class);
                                    startActivity(intent);
                                }else {
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}