package com.example.e_comerce_user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OrderSuccessfulFragment extends Fragment {


    public OrderSuccessfulFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_successful, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button button = view.findViewById(R.id.home);
        button.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_orderSuccessfulFragment_to_productListFragment);
        });
    }
}