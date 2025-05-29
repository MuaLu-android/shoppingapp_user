package com.example.data.retrofit;
import com.example.domain.model.UserModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface ShopApi {
    @POST("dangki1.php")
    @FormUrlEncoded
    Observable<UserModel> sigNup(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("uid") String uid
    );
    @POST("dangnhap1.php")
    @FormUrlEncoded
    Observable<UserModel> logIn(
            @Field("email") String email,
            @Field("pass") String pass
    );
}
