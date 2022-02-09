package com.example.e_comerce_user.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_comerce_user.callbacks.OnActionCompleteListener;
import com.example.e_comerce_user.models.CartModel;
import com.example.e_comerce_user.models.ProductModel;
import com.example.e_comerce_user.models.UserProductModel;
import com.example.e_comerce_user.utils.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;


import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public MutableLiveData<List<String>> categoryListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> productListLiveData = new MutableLiveData<>();

    public MutableLiveData<List<UserProductModel>> userProductListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CartModel>> cartListLiveData = new MutableLiveData<>();
    public List<CartModel> cartModelList = new ArrayList<>();
    public String paymentMethod = Constants.PaymentMethod.COD;

    private final String TAG = ProductViewModel.class.getSimpleName();
    public ProductViewModel() {
        getAllCategories();
        getAllProducts();
    }

    public void addToCart(CartModel cartModel, String uid, OnActionCompleteListener completeListener) {
        db.collection(Constants.DbCollection.COLLECTION_USERS)
                .document(uid)
                .collection(Constants.DbCollection.COLLECTION_CART)
                .document(cartModel.getProductId())
                .set(cartModel)
                .addOnSuccessListener(unused -> {
                    completeListener.onSuccess();
                }).addOnFailureListener(e -> {
                    completeListener.onFailure();
                });
    }

    public void removeFromCart(String uid, String productId, OnActionCompleteListener completeListener) {
        db.collection(Constants.DbCollection.COLLECTION_USERS)
                .document(uid)
                .collection(Constants.DbCollection.COLLECTION_CART)
                .document(productId)
                .delete()
                .addOnSuccessListener(unused -> {
                    completeListener.onSuccess();
                }).addOnFailureListener(e -> {
                    completeListener.onFailure();
        });
    }

    public void updateCartQuantity(String uid, List<CartModel> cartModels) {
        final WriteBatch batch = db.batch();
        for (CartModel c : cartModels) {
            final DocumentReference doc =
                    db.collection(Constants.DbCollection.COLLECTION_USERS)
                    .document(uid)
                    .collection(Constants.DbCollection.COLLECTION_CART)
                    .document(c.getProductId());
            batch.update(doc, "quantity", c.getQuantity());
        }
        batch.commit().addOnSuccessListener(unused -> {

        })
        .addOnFailureListener(unused -> {

        });
    }


    private void getAllCategories() {
        db.collection(Constants.DbCollection.COLLECTION_CATEGORY)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    final List<String> items = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        items.add(doc.get("name", String.class));
                    }
                    categoryListLiveData.postValue(items);
                });
    }

    public void getAllProducts() {
        db.collection(Constants.DbCollection.COLLECTION_PRODUCT)
                .whereEqualTo("status", true)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;

                    final List<ProductModel> items = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        items.add(doc.toObject(ProductModel.class));
                    }

                    productListLiveData.postValue(items);
                });
    }

    public void getAllCartItems(String uid) {
        db.collection(Constants.DbCollection.COLLECTION_USERS)
                .document(uid)
                .collection(Constants.DbCollection.COLLECTION_CART)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    final List<CartModel> cartModels = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        cartModels.add(doc.toObject(CartModel.class));
                    }
                    prepareUserProductList(cartModels);
                }).addOnFailureListener(e -> {

                });
    }

    public void getAllCartItemSnapshot(String uid) {
        db.collection(Constants.DbCollection.COLLECTION_USERS)
                .document(uid)
                .collection(Constants.DbCollection.COLLECTION_CART)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    final List<CartModel> cartModels = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        cartModels.add(doc.toObject(CartModel.class));
                    }
                    cartListLiveData.postValue(cartModels);
                });
    }
    public void clearCart(String uid, List<CartModel> cartModels) {
        final WriteBatch writeBatch = db.batch();
        for (CartModel c : cartModels) {
            final DocumentReference doc = db.collection(Constants.DbCollection.COLLECTION_USERS)
                    .document(uid)
                    .collection(Constants.DbCollection.COLLECTION_CART)
                    .document(c.getProductId());
            writeBatch.delete(doc);
        }
        writeBatch.commit()
                .addOnSuccessListener(unused -> {
                    getAllCartItems(uid);
                })
                .addOnFailureListener(unused -> {});

    }

    private void prepareUserProductList(List<CartModel> cartModels) {
        List<UserProductModel> userProductModels = new ArrayList<>();
        for (ProductModel p : productListLiveData.getValue()) {
            final UserProductModel upm = new UserProductModel();
            upm.setProductId(p.getProductId());
            upm.setProductName(p.getProductName());
            upm.setCategory(p.getCategory());
            upm.setDescription(p.getDescription());
            upm.setPrice(p.getPrice());
            upm.setProductImageUrl(p.getProductImageUrl());
            upm.setStatus(p.isStatus());
            upm.setInCart(false);
            upm.setFavorite(false);
            userProductModels.add(upm);
        }

        if (!cartModels.isEmpty()){
            for (CartModel c : cartModels) {
                for (UserProductModel up : userProductModels) {
                    if (c.getProductId().equals(up.getProductId())) {
                        up.setInCart(true);
                    }
                }
            }
        }

        userProductListLiveData.postValue(userProductModels);
    }

    public void getAllProductsByCategory(String category) {
        db.collection(Constants.DbCollection.COLLECTION_PRODUCT)
                .whereEqualTo("category", category)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;

                    final List<ProductModel> items = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        items.add(doc.toObject(ProductModel.class));
                    }

                    productListLiveData.postValue(items);
                });
    }

    public LiveData<ProductModel> getProductByProductId(String productId) {
        final MutableLiveData<ProductModel> productLiveData =
                new MutableLiveData<>();
        db.collection(Constants.DbCollection.COLLECTION_PRODUCT)
                .document(productId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    productLiveData.postValue(
                            value.toObject(ProductModel.class));
                });
        return productLiveData;
    }

    public double calculateTotalPrice(List<CartModel> cartModels) {
        double total = 0.0;
        for (CartModel c : cartModels) {
            total += c.getPrice() * c.getQuantity();
        }
        return total;
    }
}
