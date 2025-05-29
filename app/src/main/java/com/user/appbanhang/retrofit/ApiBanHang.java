package com.user.appbanhang.retrofit;


import com.user.appbanhang.model.DonhangModels;
import com.user.appbanhang.model.LoaiSpModel;
import com.user.appbanhang.model.MessageModel;
import com.user.appbanhang.model.SanPhamMoiModel;
import com.user.appbanhang.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiBanHang {
    @GET("getloaisp1.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi1.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("chitiet1.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );
//    @POST("dangki1.php")
//    @FormUrlEncoded
//    Observable<UserModel> dangki(
//            @Field("email") String email,
//            @Field("pass") String pass,
//            @Field("username") String username,
//            @Field("mobile") String mobile,
//            @Field("uid") String uid
//    );
//    @POST("dangnhap1.php")
//    @FormUrlEncoded
//    Observable<UserModel> dangnhap(
//            @Field("email") String email,
//            @Field("pass") String pass
//    );
    @POST("donhang1.php")
    @FormUrlEncoded
    Observable<MessageModel> creatOder(
            @Field("sdt") String sdt,
            @Field("email") String eamil,
            @Field("tongtien") String tongtien,
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );
    @POST("xemdonhang1.php")
    @FormUrlEncoded
    Observable<DonhangModels> xemDonhang(
            @Field("iduser") int id
    );
    @POST("timkiem1.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );
    @POST("gettoken1.php")
    @FormUrlEncoded
    Observable<UserModel> gettoken(
            @Field("status") int status
    );
//    @POST("xoa.php")
//    @FormUrlEncoded
//    Observable<MessageModel> xoaSanPham(
//            @Field("id") int id
//    );
//    @POST("insertsp.php")
//    @FormUrlEncoded
//    Observable<MessageModel> insertSp(
//            @Field("tensp") String tensp,
//            @Field("giasp") String giasp,
//            @Field("hinhanh") String hinhanh,
//            @Field("mota") String mota,
//            @Field("loai") int loai
//    );
//    @POST("updatesp.php")
//    @FormUrlEncoded
//    Observable<MessageModel> updatesp(
//            @Field("tensp") String tensp,
//            @Field("giasp") String giasp,
//            @Field("hinhanh") String hinhanh,
//            @Field("mota") String mota,
//            @Field("loai") int idloai,
//            @Field("id") int id
//    );
    @POST("updatetoken1.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );
    @POST("updatetokenzalo.php")
    @FormUrlEncoded
    Observable<MessageModel> updateTokenzalo(
            @Field("id") int id,
            @Field("token") String token
    );
//    @POST("upload.php")
//    Call<MessageModel> uploadFile(
//            @Part MultipartBody.Part file);

}
