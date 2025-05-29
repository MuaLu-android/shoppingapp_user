package com.user.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.user.appbanhang.R;
import com.user.appbanhang.model.CreateOrder;
import com.user.appbanhang.model.Message;
import com.user.appbanhang.model.MesageData;
import com.user.appbanhang.model.Notification;
import com.user.appbanhang.model.UserModel;
import com.user.appbanhang.retrofit.ApiBanHang;
import com.user.appbanhang.retrofit.ApiPusNotification;
import com.user.appbanhang.retrofit.Authorization;
import com.user.appbanhang.retrofit.RetrofitClient;
import com.user.appbanhang.retrofit.RetrofitClientNoti;
import com.user.appbanhang.utils.Utils;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsdt, txtemail;
    EditText diachi;
    AppCompatButton  btndathang, btnzalo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int  totalItem, iddonhang;
    Notification notification;
    OkHttpClient client;
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Demo SDK";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ ABC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        //momo
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION

        //zalo pay
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);
        initView();
        coutItem();
        initControl();
    }

    //Get token through MoMo app
    private void requestPayment(String iddonhang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", iddonhang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", iddonhang); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("thanhcong", data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    compositeDisposable.add(apiBanHang.updateTokenzalo(iddonhang, token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(messageModel -> {
                                if (messageModel.isSuccess()){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                    },
                                    throwable -> {
                                Log.d("erro", throwable.getMessage());
                                    }


                            ));
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("thanhcong", "khong thanh cong");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("thanhcong", "that bai");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("thanhcong", "that bai");
                } else {
                    //TOKEN FAIL
                    Log.d("thanhcong", "that bai");
                }
            } else {
                Log.d("thanhcong", "that bai");
            }
        } else {
            Log.d("thanhcong", "that bai");
        }
    }

    private void PushNotiToUser() {
        if (Utils.tokenSend != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new Authorization(Utils.tokenSend))
                    .build();
        }
        //getToken
        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                            if (userModel.isSuccess()) {
                                for(int i =0; i<userModel.getResult().size(); i++){
                                    notification = new Notification("Thong bao","Ban co don hang moi");
                                    Message mesasge = new Message(userModel.getResult().get(i).getToken(), notification);
                                    MesageData mesageData = new MesageData(mesasge);
                                    ApiPusNotification apiPusNotification = RetrofitClientNoti.getInstance(client).create(ApiPusNotification.class);
                                    compositeDisposable.add(apiPusNotification.sendNofitication(mesageData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(notiResponse -> {
                                            },throwable -> {
                                               Log.d("logg",throwable.getMessage());
                                            }
                                            ));
                                }

                            }
                }, throwable -> {
                    Log.d("loog", throwable.getMessage());
                }
                ));

    }

    private void coutItem() {
        totalItem = 0;
        for(int i=0; i<Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar1);
        txttongtien = findViewById(R.id.txttong);
        txtsdt = findViewById(R.id.txtsodienthoai);
        txtemail = findViewById(R.id.txtemail);
        diachi = findViewById(R.id.editdiachi);
        btndathang = findViewById(R.id.btndathang);
        btnzalo = findViewById(R.id.btndathangzalopay);
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        txtsdt.setText(Utils.user_current.getMobile());
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Str_diachi = diachi.getText().toString().trim();
                if (TextUtils.isEmpty(Str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    String tr_email  = Utils.user_current.getEmail();
                    String tr_sdt  = Utils.user_current.getMobile();
                    int  iduser = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.creatOder(tr_sdt, tr_email, String.valueOf(tongtien),iduser,Str_diachi, totalItem,new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        PushNotiToUser();
                                        Toast.makeText(getApplicationContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(),   Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
        btnzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Str_diachi = diachi.getText().toString().trim();
                Log.d("CHECK_LOG", "Địa chỉ nhập vào: " + Str_diachi);
                if (TextUtils.isEmpty(Str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    //post data
                    String tr_email  = Utils.user_current.getEmail();
                    String tr_sdt  = Utils.user_current.getMobile();
                    int  iduser = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    // Kiểm tra dữ liệu trước khi gửi
                    Log.d("CHECK_LOG", "Email: " + tr_email);
                    Log.d("CHECK_LOG", "Số điện thoại: " + tr_sdt);
                    Log.d("CHECK_LOG", "ID User: " + iduser);
                    Log.d("CHECK_LOG", "Tổng tiền: " + tongtien);
                    Log.d("CHECK_LOG", "Số lượng sản phẩm: " + totalItem);
                    Log.d("CHECK_LOG", "Danh sách mua hàng JSON: " + new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.creatOder(tr_sdt, tr_email, String.valueOf(tongtien),iduser,Str_diachi, totalItem,new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        Log.d("CHECK_LOG", "Response từ API: " + new Gson().toJson(messageModel));

                                        Log.d("CHECK_LOG", "Tạo đơn hàng thành công, ID: " + messageModel.getIddonhang());
                                        PushNotiToUser();
                                        Toast.makeText(getApplicationContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();
                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
                                        Log.d("CHECK_LOG", "ID Đơn hàng sau khi parse: " + iddonhang);
                                        requeszalo();
                                    }, throwable -> {
                                        Log.e("CHECK_LOG", "Lỗi khi tạo đơn hàng: " + throwable.getMessage());
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(),   Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void requeszalo() {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder("100");
            String code = data.getString("return_code");
            Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();
            Log.d("test", code);
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                Log.d("test", token);

                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener(){

                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        compositeDisposable.add(apiBanHang.updateTokenzalo(iddonhang, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(messageModel -> {
                                            if (messageModel.isSuccess()){
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        },
                                        throwable -> {
                                            Log.d("erro", throwable.getMessage());
                                        }
                                ));
                        Log.d("ZaloPay", "Thanh toán thành công! ID giao dịch: " + s);
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Log.d("ZaloPay", "Giao dịch bị hủy: " + s1);
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Log.d("ZaloPay", "Lỗi thanh toán: " + zaloPayError.toString());
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}