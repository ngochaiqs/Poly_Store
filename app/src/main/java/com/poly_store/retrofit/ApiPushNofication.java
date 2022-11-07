package com.poly_store.retrofit;

import com.poly_store.model.NotiResponse;
import com.poly_store.model.NotiSendData;

import io.reactivex.Completable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    //Completable sendNofitication(NotiSendData notiSendData);
    @Headers(
            {
                    "content-type: application/json",
                    "authorization: key=AAAAFtY5slI:APA91bFLYNncBWpmKEiX7StcriDzUTsns1EXCfpzgPD10i-2Yf_wMyVb12BspaOllqU2BAuMPqrTsCckt4kB3aqKuqV2Xn0YKJLzrZz8-A6AMQ1JBAd7rihWx8gYrsv2MYP1TGNyN0nF"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
