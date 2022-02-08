package com.example.e_comerce_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_comerce_user.adapters.ProductAdapter;
import com.example.e_comerce_user.callbacks.AddRemoveCartItemListener;
import com.example.e_comerce_user.callbacks.OnActionCompleteListener;
import com.example.e_comerce_user.databinding.FragmentProductListBinding;
import com.example.e_comerce_user.models.CartModel;
import com.example.e_comerce_user.viewmodels.LoginViewModel;
import com.example.e_comerce_user.viewmodels.ProductViewModel;

public class ProductListFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private ProductViewModel productViewModel;
    private FragmentProductListBinding binding;
    private ProductAdapter adapter;

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
        productViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        productViewModel.getAllProducts();
        adapter = new ProductAdapter(productId -> {
            // TODO: 2/3/2022 go to details page with this id
        }, cartItemListener);
        binding.productRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.productRV.setAdapter(adapter);
        productViewModel.productListLiveData.observe(getViewLifecycleOwner(),
                productList -> {
                    if (!productList.isEmpty()) {
                        productViewModel.getAllCartItems(loginViewModel.getUser().getUid());
                    }
                    //adapter.submitList(productList);
                });

        productViewModel.userProductListLiveData.observe(
                getViewLifecycleOwner(), userProductModels -> {
                    adapter.submitList(userProductModels);
                }
        );
        return binding.getRoot();
    }
    private AddRemoveCartItemListener cartItemListener = new AddRemoveCartItemListener() {
        @Override
        public void onCartItemAdd(CartModel cartModel, int position) {
            productViewModel.addToCart(cartModel, loginViewModel.getUser().getUid(), new OnActionCompleteListener() {
                @Override
                public void onSuccess() {
                    adapter.notifyItemChanged(position);
                }

                @Override
                public void onFailure() {

                }
            });
        }

        @Override
        public void onCartItemRemove(String productId, int position) {
            productViewModel.removeFromCart(
                    loginViewModel.getUser().getUid(),
                    productId, new OnActionCompleteListener() {
                        @Override
                        public void onSuccess() {
                            adapter.notifyItemChanged(position);
                        }

                        @Override
                        public void onFailure() {

                        }
                    }
            );
        }
    };

}