package com.user.appbanhang.framents;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.core.viewModel.SigNupViewModel;
import com.user.appbanhang.R;
import com.user.appbanhang.activity.DangNhapActivity;
public class SingNupFragment extends Fragment {
    EditText email, pass, repass, username, mobile;
    Button btnSingNup;
    private SigNupViewModel sigNupViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_nup, container, false);
        sigNupViewModel = new ViewModelProvider(this).get(SigNupViewModel.class);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        repass = view.findViewById(R.id.repass);
        mobile = view.findViewById(R.id.mobile);
        username = view.findViewById(R.id.username);
        btnSingNup = view.findViewById(R.id.btnNup);
        btnSingNup.setOnClickListener(v -> performRegistration());
        observeViewModel();
        return view;
    }
    private void observeViewModel() {
        sigNupViewModel.getRegistrationResult().observe(getViewLifecycleOwner(), registrationResult -> {
            if (registrationResult == SigNupViewModel.RegistrationResult.SUCCESS) {
                Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), DangNhapActivity.class));
                getActivity().finish();
            }
        });
        sigNupViewModel.getErrorEvent().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void performRegistration() {
        String tr_email = email.getText().toString().trim();
        String tr_pass = pass.getText().toString().trim();
        String tr_repass = repass.getText().toString().trim();
        String tr_username = username.getText().toString().trim();
        String tr_mobile = mobile.getText().toString().trim();
        if (TextUtils.isEmpty(tr_email)) {
            Toast.makeText(getContext(), "Bạn chưa nhâp email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(tr_pass)) {
            Toast.makeText(getContext(), "Bạn chưa nhâp pass", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(tr_repass)) {
            Toast.makeText(getContext(), "Bạn chưa nhâp repass", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(tr_mobile)) {
            Toast.makeText(getContext(), "Bạn chưa nhâp Mobile", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(tr_username)) {
            Toast.makeText(getContext(), "Bạn chưa nhâp Username", Toast.LENGTH_SHORT).show();
        } else {
            if (tr_pass.equals(tr_repass)) {
                sigNupViewModel.sigNupAccount(tr_email, tr_pass, tr_username, tr_mobile);
            } else {
                Toast.makeText(getContext(), "Pass chưa khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }
}