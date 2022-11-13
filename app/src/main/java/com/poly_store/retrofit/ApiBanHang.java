package com.poly_store.retrofit;

import com.poly_store.model.DonHangModel;
import com.poly_store.model.LoaiSPModel;
import com.poly_store.model.MessageModel;
import com.poly_store.model.NguoiDung;
import com.poly_store.model.NguoiDungModel;
import com.poly_store.model.SanPhamModel;
import com.poly_store.model.ThongKeModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSPModel> getLoaiSp();

    @GET("getsanpham.php")
    Observable<SanPhamModel> getSanPham();

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @POST("chitietdonhang.php")
    @FormUrlEncoded
    Observable<SanPhamModel> getSanPham(
        @Field("page") int page,
        @Field("maLoai") int maLoai
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> dangKy(
            @Field("tenND") String tenND,
            @Field("email") String email,
            @Field("matKhau") String matKhau,
            @Field("SDT") String SDT,
            @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> dangNhap(
            @Field("email") String email,
            @Field("matKhau") String matKhau
    );

    @POST("quenmatkhau.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> quenMK(
            @Field("email") String email

    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> datHang(
            @Field("email") String email,
            @Field("SDT") String SDT,
            @Field("tongTien") String tongTien,
            @Field("maND") int maND,
            @Field("diaChi") String diaChi,
            @Field("soLuong") int soLuong,
            @Field("chiTiet") String chiTiet
    );

    @POST("updateorder.php")
    @FormUrlEncoded
    Observable<MessageModel> updateOrder(
            @Field("maDH") int maDH,
            @Field("trangThai") int trangThai
    );
    @POST("xemdonn.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("maND") int maND

    );

    @POST("timkiemsp.php")
    @FormUrlEncoded
    Observable<SanPhamModel> timKiem(
            @Field("timKiem") String timKiem

    );
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> gettoken(
            @Field("status") int status

    );


    @POST("xoa.php")
    @FormUrlEncoded
    Observable<SanPhamModel> xoaSanPham(
            @Field("maSP") int maSP

    );

    @POST("themsp.php")
    @FormUrlEncoded
    Observable<MessageModel> themSP(
            @Field("tenSP") String tenSP,
            @Field("giaSP") String giaSP,
            @Field("hinhAnhSP") String hinhAnhSP,
            @Field("moTa") String moTa,
            @Field("maLoai") int maLoai
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesp(
            @Field("tenSP") String tenSP,
            @Field("giaSP") String giaSP,
            @Field("hinhAnhSP") String hinhAnhSP,
            @Field("moTa") String moTa,
            @Field("maLoai") int maLoai,
            @Field("maSP") int maSP

    );

    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> getToken(
            @Field("status") int status,
            @Field("maND") int maND
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("maND") int maND,
            @Field("token") String token
    );
    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(
            @Part MultipartBody.Part file
    );

//    @POST("gettoken.php")
//    @FormUrlEncoded
//    Observable<NguoiDungModel> gettoken(
//            @Field("status") int status
//    );
}
