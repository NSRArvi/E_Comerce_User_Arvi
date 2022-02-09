package com.example.e_comerce_user.viewmodels;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_comerce_user.callbacks.OnActionCompleteListener;
import com.example.e_comerce_user.models.EcomUser;
import com.example.e_comerce_user.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {
    final String TAG = LoginViewModel.class.getSimpleName();
    public enum AuthState {
        AUTHENTICATED, UNAUTHENTICATED
    }
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<AuthState> stateLiveData;
    private MutableLiveData<String> errMsgLiveData;
    private MutableLiveData<EcomUser> ecomUserMutableLiveData = new MutableLiveData<>();
    private FirebaseAuth auth;
    private FirebaseUser user;

    public LoginViewModel() {
        stateLiveData = new MutableLiveData<>();
        errMsgLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            stateLiveData.postValue(AuthState.UNAUTHENTICATED);
        }else {
            stateLiveData.postValue(AuthState.AUTHENTICATED);
        }
    }
    public MutableLiveData<EcomUser> getEcomUserMutableLiveData() {
        return ecomUserMutableLiveData;
    }

    public LiveData<AuthState> getStateLiveData() {
        return stateLiveData;
    }

    public LiveData<String> getErrMsgLiveData() {
        return errMsgLiveData;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    user = authResult.getUser();
                    stateLiveData.postValue(AuthState.AUTHENTICATED);
                }).addOnFailureListener(e -> {
                    errMsgLiveData.postValue(e.getLocalizedMessage());
                });
    }

    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    user = authResult.getUser();
                    stateLiveData.postValue(AuthState.AUTHENTICATED);
                   addUserToDatabase();
                }).addOnFailureListener(e -> {
            errMsgLiveData.postValue(e.getLocalizedMessage());
        });
    }
    private void addUserToDatabase() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference doc =
                db.collection(Constants.DbCollection.COLLECTION_USERS)
                        .document(user.getUid());
        final EcomUser ecomUser = new EcomUser(
                user.getUid(), null, user.getEmail(),  null, user.getPhoneNumber());
        doc.set(ecomUser).addOnSuccessListener(unused -> {

        }).addOnFailureListener(e -> {
            Log.e(TAG, "addUserToDatabase: "+e.getLocalizedMessage());
        });
    }

    public void logout() {
        if (user != null) {
            auth.signOut();
            stateLiveData.postValue(AuthState.UNAUTHENTICATED);
        }
    }

    public boolean isEmailVerified() {
        return user.isEmailVerified();
    }

    public void sendVerificationMail() {
        user.sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public LiveData<EcomUser> getUserData() {
        final MutableLiveData<EcomUser> userLiveData = new MutableLiveData<>();
        db.collection(Constants.DbCollection.COLLECTION_USERS)
                .document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    final EcomUser ecomUser = documentSnapshot.toObject(EcomUser.class);
                    userLiveData.postValue(ecomUser);
                }).addOnFailureListener(e -> {

        });

        return userLiveData;
    }
    public void updateDeliveryAddress(String address, OnActionCompleteListener actionCompleteListener) {
        final DocumentReference doc =
                db.collection(Constants.DbCollection.COLLECTION_USERS)
                        .document(user.getUid());
        doc.update("deliveryAddress", address)
                .addOnSuccessListener(unused -> {
                    actionCompleteListener.onSuccess();
                })
                .addOnFailureListener(unused -> {
                    actionCompleteListener.onFailure();
                });
    }

}
