package com.poly_store.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.poly_store.R;
import com.poly_store.adapter.ChatAdapter;
import com.poly_store.model.ChatMessage;
import com.poly_store.model.NotiSendData;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.ApiPushNofication;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.retrofit.RetrofitClientNoti;
import com.poly_store.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imgSend;
    EditText edtMess;
    FirebaseFirestore db;
    ChatAdapter adapter;
    List<ChatMessage> list;
    int iduser;
    String iduser_str;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        iduser = getIntent().getIntExtra("id",0); // ma nguoi nhan
        iduser_str = String.valueOf(iduser);

        initView();
        initControl();
        insertUser();
        listenMess();
        ActionToolbar();
    }

    private void ActionToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


        private void insertUser() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", Utils.nguoidung_current.getMaND());
        user.put("username", Utils.nguoidung_current.getTenND());
        db.collection("users").document(String.valueOf(Utils.nguoidung_current.getMaND())).set(user);
    }
    public void hideKeyboard(View view) {
    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
}
    private void initControl(){
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessToFire();
            }
        });
        edtMess.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void sendMessToFire() {
        String str_mess = edtMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)){

        }else {
            guiThongBaoND();
            HashMap<String, Object> message = new HashMap<>();
            message.put(Utils.SENDID, String.valueOf(Utils.nguoidung_current.getMaND()));
            message.put(Utils.RECEIVEDID, iduser_str);
            message.put(Utils.MESS, str_mess);
            message.put(Utils.DATETIME, new Date());
            db.collection(Utils.PATH_CHAT).add(message);
            edtMess.setText("");

        }
    }
    private void listenMess(){
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID, String.valueOf(Utils.nguoidung_current.getMaND()))
                .whereEqualTo(Utils.RECEIVEDID, iduser_str)
                .addSnapshotListener(eventListener);

        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID, iduser_str)
                .whereEqualTo(Utils.RECEIVEDID, String.valueOf(Utils.nguoidung_current.getMaND()))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error)->{
        if (error !=null){
            return ;
        }
        if (value !=null){
            int count = list.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendid = documentChange.getDocument().getString(Utils.SENDID);
                    chatMessage.received = documentChange.getDocument().getString(Utils.RECEIVEDID);
                    chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME));
                    list.add(chatMessage);
                }
            }
            Collections.sort(list, (obj1, obj2)-> obj1.dateObj.compareTo(obj2.dateObj));
            if (count == 0){
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyItemRangeChanged(list.size(), list.size());
                recyclerView.smoothScrollToPosition(list.size()-1);
            }
        }
    };

    private String format_date(Date date){
        return new SimpleDateFormat("hh:mm a- dd MMM yyyy", Locale.getDefault()).format(date);
    }
    private void guiThongBaoND() {
        //get token
        compositeDisposable.add(apiBanHang.getToken(0, Integer.parseInt(iduser_str))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel ->{
                            if (nguoiDungModel.isSuccess()){
                                for (int i =0; i<nguoiDungModel.getResult().size(); i++){
                                    Log.d("===//", "Token User nhận thông báo: " + nguoiDungModel.getResult().get(i).getToken());
                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", "Thông báo từ Poly Store!");
                                    data.put("body", "Bạn nhận được tin nhắn mới từ Poly Store. Vui lòng kiểm tra tin nhắn!");
                                    NotiSendData notiSendData = new NotiSendData(nguoiDungModel.getResult().get(i).getToken(), data);
                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
                                    compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {

                                                    },
                                                    throwable -> {
                                                        Log.d("logg", throwable.getMessage());
                                                    }
                                            ));
                                }
                            }
                        },
                        throwable -> {
                            Log.d("loggg", throwable.getMessage());
                        }
                ));
    }
    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleview_chat);
        imgSend = findViewById(R.id.imagechat);
        edtMess = findViewById(R.id.edtinputtex);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ChatAdapter(getApplicationContext(), list, String.valueOf(Utils.nguoidung_current.getMaND()));
        recyclerView.setAdapter(adapter);
    }
}