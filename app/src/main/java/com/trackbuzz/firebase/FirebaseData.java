package com.trackbuzz.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trackbuzz.firebase.model.ChatCredentials;

/**
 * Created by CheemalaCh on 7/12/2018.
 */

public class FirebaseData {

    private Context context;
    private DatabaseReference fireDatabase = null;
    private UserInfoCallbacks userInfoCallbacks;

    public FirebaseData(Context context, UserInfoCallbacks userInfoCallbacks, String rfrncIdentifier) {
        this.context = context;
        this.userInfoCallbacks = userInfoCallbacks;
        fetchFirebaseUsers(rfrncIdentifier);
    }

    public void fetchFirebaseUsers(String identifier) {
        if (fireDatabase == null) {
            fireDatabase = FirebaseDatabase.getInstance().getReference(identifier);
        }
        fireDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDta : dataSnapshot.getChildren()) {
                    Log.d("key: ", "" + childDta.getKey());
                    Log.d("username: ", "" + childDta.child("username").getValue() + " password: " + childDta.child("password").getValue());
                    if (childDta.hasChild("username") && childDta.hasChild("password") && !childDta.child("username").getValue().toString().contentEquals(ChatCredentials.getMyId())) {
                        userInfoCallbacks.onUserInfoDetected(String.valueOf(childDta.child("username").getValue()), String.valueOf(childDta.child("password").getValue()));
                    } else {
                        Log.d("key_data: ", "has no children!");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ShowMsg.showMessage(context, "" + databaseError.getMessage());

            }
        });
    }

}
