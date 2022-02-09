package com.example.e_comerce_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_comerce_user.adapters.CartAdapter;
import com.example.e_comerce_user.databinding.FragmentCartListBinding;
import com.example.e_comerce_user.models.CartModel;
import com.example.e_comerce_user.viewmodels.LoginViewModel;
import com.example.e_comerce_user.viewmodels.ProductViewModel;

import java.util.List;


public class CartListFragment extends Fragment {
    private final String TAG = CartListFragment.class.getSimpleName();
    private ProductViewModel productViewModel;
    private LoginViewModel loginViewModel;
    private FragmentCartListBinding binding;
    private CartAdapter adapter;
public CartListFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartListBinding.inflate(inflater);
        productViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        loginViewModel = new ViewModelProvider(requireActivity())
                .get(LoginViewModel.class);
        adapter = new CartAdapter(cartList -> {
            updateUI(cartList);
        });
        binding.cartRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.cartRV.setAdapter(adapter);
        productViewModel.getAllCartItemSnapshot(loginViewModel.getUser().getUid());
        productViewModel.cartListLiveData.observe(getViewLifecycleOwner(),
                cartModels -> {
                    adapter.submitList(cartModels);
                    updateUI(cartModels);
                });
        binding.checkoutBtn.setOnClickListener(v -> {
            productViewModel.cartModelList = adapter.getCurrentList();
            Navigation.findNavController(v)
                    .navigate(R.id.action_cartListFragment_to_checkoutFragment);
        });
        return binding.getRoot();
    }
    private void updateUI(List<CartModel> cartModels) {
        final double price = productViewModel.calculateTotalPrice(cartModels);
        binding.cartTotalPriceTV.setText("Total: BDT "+price);
    }

    @Override
    public void onStop() {
        super.onStop();
        productViewModel.cartModelList = adapter.getCurrentList();
        productViewModel.updateCartQuantity(
                loginViewModel.getUser().getUid(),
                adapter.getCurrentList()
        );
    }
}