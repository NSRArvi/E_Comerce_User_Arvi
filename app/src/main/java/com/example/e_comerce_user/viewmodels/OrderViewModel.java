package com.example.e_comerce_user.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_comerce_user.callbacks.OnActionCompleteListener;
import com.example.e_comerce_user.models.CartModel;
import com.example.e_comerce_user.models.OrderModel;
import com.example.e_comerce_user.models.OrderSettingsModel;
import com.example.e_comerce_user.utils.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;


import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<OrderSettingsModel> settingsModelMutableLiveData =
            new MutableLiveData<>();
    private MutableLiveData<List<OrderModel>> orderListLiveData = new MutableLiveData<>();
    private OrderSettingsModel orderSettingsModel;
    public MutableLiveData<OrderSettingsModel> getSettingsModelMutableLiveData() {
        return settingsModelMutableLiveData;
    }

    public void getOrderSettings() {
        db.collection(Constants.DbCollection.COLLECTION_ORDER_SETTINGS)
                .document(Constants.DbCollection.DOCUMENT_ORDER_SETTING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    settingsModelMutableLiveData.postValue(
                            value.toObject(OrderSettingsModel.class));
                });

    }

    public void getAllOrders(String userId) {
        db.collection(Constants.DbCollection.COLLECTION_ORDERS)
                .whereEqualTo("userId", userId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;

                    final List<OrderModel> items = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        items.add(doc.toObject(OrderModel.class));
                    }

                    orderListLiveData.postValue(items);
                });
    }

    public double getDiscountAmount(double price, double discount) {
        return (price * discount) / 100;
    }

    public double getVatAmount(double price, double vat) {
        return (price * vat) / 100;
    }

    public double getGrandTotal(double price, double vat, double discount, double deliveryCharge) {
        return (price - getDiscountAmount(price, discount))
                +getVatAmount(price, vat) + deliveryCharge;
    }

    public OrderSettingsModel getOrderSettingsModel() {
        return orderSettingsModel;
    }

    public void setOrderSettingsModel(OrderSettingsModel orderSettingsModel) {
        this.orderSettingsModel = orderSettingsModel;
    }

    public MutableLiveData<List<OrderModel>> getOrderListLiveData() {
        return orderListLiveData;
    }

    public void addNewOrder(OrderModel orderModel, List<CartModel> cartModelList, OnActionCompleteListener actionCompleteListener) {
        final WriteBatch writeBatch = db.batch();
        final DocumentReference orderDoc =
                db.collection(Constants.DbCollection.COLLECTION_ORDERS)
                .document();
        orderModel.setOrderId(orderDoc.getId());
        writeBatch.set(orderDoc, orderModel);

        for (CartModel c : cartModelList) {
            final DocumentReference doc =
                    db.collection(Constants.DbCollection.COLLECTION_ORDERS)
                    .document(orderDoc.getId())
                    .collection(Constants.DbCollection.COLLECTION_ORDER_DETAILS)
                    .document();
            writeBatch.set(doc, c);
        }

        writeBatch.commit()
                .addOnSuccessListener(unused -> {
                    actionCompleteListener.onSuccess();
                })
                .addOnFailureListener(unused -> {
                    actionCompleteListener.onFailure();
                });
    }
}
