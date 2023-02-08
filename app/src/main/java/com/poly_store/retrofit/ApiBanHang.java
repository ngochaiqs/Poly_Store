package com.poly_store.retrofit;

import com.poly_store.model.DoanhThuModel;
import com.poly_store.model.DonHangModel;
import com.poly_store.model.LoaiSPModel;
import com.poly_store.model.MessageModel;
import com.poly_store.model.NguoiDungModel;
import com.poly_store.model.NhaCungCapModel;
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

    @GET("chitietsanpham.php")
    Observable<SanPhamModel> getSanPham();

    @GET("hienthiaokhoac.php")
    Observable<SanPhamModel> getAoKhoac();

    @GET("hienthiaothun.php")
    Observable<SanPhamModel> getAoThun();

    @GET("hienthiaosomi.php")
    Observable<SanPhamModel> getAoSoMi();

    @GET("hienthiquanjeans.php")
    Observable<SanPhamModel> getQuanJeans();

    @GET("getnhacungcap.php")
    Observable<NhaCungCapModel> getNhaCungCap();
    @GET("getspnncc.php")
    Observable<NhaCungCapModel> getSpinnerNCC();

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @POST("chitietdonhang.php")
    @FormUrlEncoded
    Observable<SanPhamModel> getSanPham(
            @Field("page") int page,
            @Field("maLoai") int maLoai
    );

    @POST("doanhThu.php")
    @FormUrlEncoded
    Observable<DoanhThuModel> getDoanhThu(
            @Field("tuNgay") String tuNgay,
            @Field("denNgay") String denNgay
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> dangKy(
            @Field("tenND") String tenND,
            @Field("email") String email,
            @Field("matKhau") String matKhau,
            @Field("SDT") String SDT,
            @Field("diaChi") String diaChi,
            @Field("uid") String uid
    );
    @POST("themnguoidung.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> themNguoiDung(
            @Field("tenND") String tenND,
            @Field("email") String email,
            @Field("matKhau") String matKhau,
            @Field("SDT") String SDT,
            @Field("diaChi") String diaChi,
            @Field("status") int status,
            @Field("uid") String uid
    );
    @POST("dangnhapadmin.php")
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
            @Field("tenND") String tenND,
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
    @POST("xemdon.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("maND") int maND

    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamModel> timKiem(
            @Field("timKiem") String timKiem

    );
    @POST("themsp.php")
    @FormUrlEncoded
    Observable<MessageModel> themSP(
            @Field("tenSP") String tenSP,
            @Field("giaSP") String giaSP,
            @Field("hinhAnhSP") String hinhAnhSP,
            @Field("moTa") String moTa,
            @Field("maLoai") int maLoai,
            @Field("maNCC") int maNCC
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesp(
            @Field("tenSP") String tenSP,
            @Field("giaSP") String giaSP,
            @Field("hinhAnhSP") String hinhAnhSP,
            @Field("moTa") String moTa,
            @Field("maLoai") int maLoai,
            @Field("maNCC") int maNCC,
            @Field("maSP") int maSP

    );

    @POST("xoa.php")
    @FormUrlEncoded
    Observable<SanPhamModel> xoaSanPham(
            @Field("maSP") int maSP

    );



    @POST("doimatkhau.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> doiMatKhau(
            @Field("email") String email,
            @Field("matKhau") String matKhau,
            @Field("matKhauMoi") String matKhauMoi

    );

    @POST("themncc.php")
    @FormUrlEncoded
    Observable<NhaCungCapModel> themNCC(
            @Field("tenNCC") String tenNCC,
            @Field("SDTNCC") String SDTNCC,
            @Field("diaChiNCC") String diaChiNCC

    );

    @POST("suancc.php")
    @FormUrlEncoded
    Observable<NhaCungCapModel> suaNCC(
            @Field("tenNCC") String tenNCC,
            @Field("SDTNCC") String SDTNCC,
            @Field("diaChiNCC") String diaChiNCC,
            @Field("maNCC") int maNCC

    );

    @POST("xoancc.php")
    @FormUrlEncoded
    Observable<NhaCungCapModel> xoaNhaCungCap(
            @Field("maNCC") int maNCC

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
}
