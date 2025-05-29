package com.example.core.viewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.data.retrofit.RetrofitClient;
import com.example.data.retrofit.ShopApi;
import com.example.data.utils.Utils;
import com.example.domain.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SigNupViewModel extends ViewModel {
    private ShopApi shopApi;
    private FirebaseAuth firebaseAuth;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<RegistrationResult> _registrationResult = new MutableLiveData<>();
    public MutableLiveData<RegistrationResult> getRegistrationResult() {
        return _registrationResult;
    }

    private final MutableLiveData<String> _errorEvent = new MutableLiveData<>();

    public MutableLiveData<String> getErrorEvent() {
        return _errorEvent;
    }

    public SigNupViewModel() {
        shopApi = RetrofitClient.getInstance(Utils.server_url).create(ShopApi.class);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void sigNupAccount(String email, String pass, String username, String mobile) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            postUser(email, pass, username, mobile, user.getUid());
                        }else {
                            _errorEvent.setValue("Đăng ký thất bại");
                        }
                    } else {
                        if (task.getException() != null) {
                            _errorEvent.setValue(task.getException().getMessage());
                        } else {
                            _errorEvent.setValue("Email đã tồn tại hoặc có lỗi xảy ra.");
                        }
                    }
                });
    }

    private void postUser(String email, String pass, String username, String mobile, String uid) {
        compositeDisposable.add(shopApi.sigNup(email, pass, username, mobile, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        UserModel -> {
                            if (UserModel.isSuccess()) {
                                Utils.user_current.setEmail(email);
                                Utils.user_current.setPass(pass);
                                _registrationResult.setValue(RegistrationResult.SUCCESS);
                            } else {
                                _errorEvent.setValue(UserModel.getMessage());
                                _registrationResult.setValue(RegistrationResult.FAILURE);
                            }
                        },
                        throwable -> {
                            if (throwable.getMessage() != null) {
                                _errorEvent.setValue(throwable.getMessage());
                            } else {
                                _errorEvent.setValue("An unknown error occurred during post data.");
                            }
                        }
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
    public enum RegistrationResult {
        SUCCESS,
        FAILURE
    }
}
