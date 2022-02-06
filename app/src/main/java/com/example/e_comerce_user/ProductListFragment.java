package com.example.e_comerce_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_comerce_user.databinding.FragmentProductListBinding;
import com.example.e_comerce_user.viewmodels.LoginViewModel;

public class ProductListFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FragmentProductListBinding binding;

    public ProductListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater);
        loginViewModel = new ViewModelProvider(requireActivity())
                .get(LoginViewModel.class);
        loginViewModel.getStateLiveData()
                .observe(getViewLifecycleOwner(), authState -> {
                    if (authState == LoginViewModel.AuthState.UNAUTHENTICATED) {
                        Navigation.findNavController(container)
                                .navigate(R.id.action_productListFragment_to_loginFragment);
                    }
                });
        return binding.getRoot();
    }
}