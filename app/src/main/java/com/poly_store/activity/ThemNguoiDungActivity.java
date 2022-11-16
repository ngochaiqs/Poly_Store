package com.poly_store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.poly_store.R;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemNguoiDungActivity extends AppCompatActivity {
    EditText emailND, matKhauND, reMatKhauND, sdtND, tenNDND;
    Toolbar toolbar;
    AppCompatButton buttonThemND;
    ApiBanHang apiBanHang;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        initView();
        initControll();
        ActionToolBar();
    }
    private void initControll(){
        buttonThemND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });
    }

    private void dangKy() {
        String str_tenND = tenNDND.getText().toString().trim();
        String str_email = emailND.getText().toString().trim();
        String str_matKhau = matKhauND.getText().toString().trim();
        String str_reMatKhau = reMatKhauND.getText().toString().trim();
        String str_sdt = sdtND.getText().toString().trim();

        if (TextUtils.isEmpty(str_tenND)) {
            Toast.makeText(getApplicationContext(), "Chưa nhập họ và tên!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "Chưa nhập Email!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_matKhau)) {
            Toast.makeText(getApplicationContext(), "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_reMatKhau)) {
            Toast.makeText(getApplicationContext(), "Chưa nhập xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_sdt)) {
            Toast.makeText(getApplicationContext(), "Chưa nhập số điện thoại!", Toast.LENGTH_SHORT).show();


        }else{
            if (str_matKhau.equals(str_reMatKhau)){
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(str_email,str_matKhau)
                        .addOnCompleteListener(ThemNguoiDungActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        postData(str_tenND,str_email, str_matKhau, str_sdt, user.getUid());

                                    }


                                }else {
                                    Toast.makeText(getApplicationContext(),"Email đã tồn tại!",Toast.LENGTH_SHORT).show();
                                }
                            }

                        });


            }else{
                Toast.makeText(getApplicationContext(), "Mật khẩu chưa khớp!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void  postData(String str_tenND, String str_email, String str_matKhau, String str_sdt, String uid){
        //post data
        compositeDisposable.add(apiBanHang.dangKy(str_tenND,str_email,str_matKhau,str_sdt, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()){
                                Utils.nguoidung_current.setEmail(str_email);
                                Utils.nguoidung_current.setMatKhau(str_matKhau);
                                Toast.makeText(getApplicationContext(),nguoiDungModel.getMessage(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Thêm người dùng thành công!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thất bại!", Toast.LENGTH_SHORT).show();
                        }
                ));

    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        emailND = findViewById(R.id.emailND);
        matKhauND = findViewById(R.id.matKhauND);
        reMatKhauND = findViewById(R.id.reMatKhauND);
        buttonThemND = findViewById(R.id.btnthemND);
        sdtND = findViewById(R.id.sdtND);
        tenNDND = findViewById(R.id.tenNDND);
        toolbar = findViewById(R.id.toolbarNguoiDung);
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();;
    }
}