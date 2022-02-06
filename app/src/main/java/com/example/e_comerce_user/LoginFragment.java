package com.example.e_comerce_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_comerce_user.databinding.FragmentLoginBinding;
import com.example.e_comerce_user.viewmodels.LoginViewModel;


public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private boolean isLogin;
    public LoginFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        loginViewModel = new ViewModelProvider(requireActivity())
                .get(LoginViewModel.class);
        binding.loginBtn.setOnClickListener(v -> {
            isLogin = true;
            authenticate();
        });
        binding.registerBtn.setOnClickListener(v -> {
            isLogin = false;
            authenticate();
        });

        loginViewModel.getStateLiveData()
                .observe(getViewLifecycleOwner(), authState -> {
                    if (authState == LoginViewModel.AuthState.AUTHENTICATED) {
                        Navigation.findNavController(container)
                                .navigate(R.id.action_loginFragment_to_productListFragment);
                    }
                });

        loginViewModel.getErrMsgLiveData()
                .observe(getViewLifecycleOwner(), errMsg -> {
                    binding.errMsgTV.setText(errMsg);
                });

        return binding.getRoot();
    }
    private void authenticate() {
        final String email = binding.emailInputET.getText().toString();
        final String password = binding.passwordInputET.getText().toString();

        if (isLogin) {
            loginViewModel.login(email, password);
        }else {
            loginViewModel.register(email, password);
        }
    }
}