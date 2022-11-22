package com.poly_store.retrofit;

import com.poly_store.model.NotiResponse;
import com.poly_store.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    //Completable sendNofitication(NotiSendData notiSendData);
    @Headers(
            {
                    "content-type: application/json",
                    "authorization: key=AAAAFtY5slI:APA91bHsQzKtYlLn-B6NKnOB6_uFGFWGinZf7FtDvws3Tlcg_n1kkMTlMqWuLDY-yPxjzpjaiOGWyRiP59oauRmMMcpzIRG15Ugu12PfKYZTysyKvGz8MCdcf4NtwJ0xdFXus6C0CsOg"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
