package com.poly_store.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.poly_store.R;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {

    TextView txtdangki, txtresetMK;
    TextInputLayout line_email, line_matkhau;
    EditText email, matKhau;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    FirebaseAuth firebaseAuth;
    FirebaseUser tenND;
    ProgressBar progressBar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initView();
        initControll();
    }
    private void initControll(){
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });

        txtresetMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuenMatKhauActivity.class);
                startActivity(intent);
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line_email.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        matKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line_matkhau.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().trim();
                String str_matKhau = matKhau.getText().toString().trim();

                if (TextUtils.isEmpty(str_email)){
                    line_email.setError("Vui lòng nhập Email!");
                }else if (TextUtils.isEmpty(str_matKhau)){
                    line_matkhau.setError("Vui lòng nhập mật khẩu!");
                }else {
//                    save

//                    progressBar.setVisibility(View.VISIBLE);
                    Paper.book().write("email",str_email);
                    Paper.book().write("matKhau",str_matKhau);
                    dangNhap(str_email, str_matKhau);
                    if (tenND != null){
                        //user da co dang nhap firebase
                        dangNhap(str_email,str_matKhau);
                    }else {
                        firebaseAuth.signInWithEmailAndPassword(str_email,str_matKhau)
                                .addOnCompleteListener(DangNhapActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            dangNhap(str_email, str_matKhau);
                                        }
                                    }
                                });
                    }
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        matKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint("WrongViewCast")
    private void initView(){
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        txtdangki = findViewById(R.id.txtdangky);
        txtresetMK = findViewById(R.id.txtresetMK);

        email = findViewById(R.id.email);
        matKhau = findViewById(R.id.matKhau);
        btndangnhap = findViewById(R.id.btndangnhap);
        firebaseAuth = FirebaseAuth.getInstance();
        tenND = firebaseAuth.getCurrentUser();
        line_email = findViewById(R.id.line_email);
        line_matkhau = findViewById(R.id.line_matkhau);


        //read data
        if (Paper.book().read("email") != null && Paper.book().read("matKhau") != null){
            email.setText(Paper.book().read("email"));
            matKhau.setText(Paper.book().read("matKhau"));
            if (Paper.book().read("islogin")!= null){
                boolean flag = Paper.book().read("islogin");
                if (flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                                 dangNhap(Paper.book().read("email"), Paper.book().read("matkhau"));
                        }
                    },100);
                }
            }
        }
    }
    private void dangNhap( String email, String matkhau) {
        final LoadingDialog loadingDialog = new LoadingDialog(DangNhapActivity.this);
        loadingDialog.startLoadingDialog();
        compositeDisposable.add(apiBanHang.dangNhap(email,matkhau)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()){
                                isLogin  = true;
                                Paper.book().write("islogin",isLogin);
                                Utils.nguoidung_current = nguoiDungModel.getResult().get(0);
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();
                                //luu lai thong tin
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            loadingDialog.dismissDialog();
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối Internet!", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.nguoidung_current.getEmail() != null && Utils.nguoidung_current.getMatKhau() != null){
            email.setText(Utils.nguoidung_current.getEmail());
            matKhau.setText(Utils.nguoidung_current.getMatKhau());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}