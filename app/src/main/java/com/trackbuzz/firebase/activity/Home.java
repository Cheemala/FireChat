package com.trackbuzz.firebase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.trackbuzz.firebase.FirebaseData;
import com.trackbuzz.firebase.R;
import com.trackbuzz.firebase.UserInfoCallbacks;
import com.trackbuzz.firebase.Utils.RecyclerviewCallbacks;
import com.trackbuzz.firebase.adapter.UsersAdapter;
import com.trackbuzz.firebase.model.ChatCredentials;
import com.trackbuzz.firebase.model.UsersData;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private FirebaseData fireUsrData;
    private UsersAdapter usrAdapter;
    private List<UsersData> usrDetails;
    private RecyclerView usrLstRecyclervw;
    private UserInfoCallbacks userInfoCallbacks;
    private RecyclerviewCallbacks recyclerviewCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usrLstRecyclervw = findViewById(R.id.usr_lst_recyclervw);
        usrDetails = new ArrayList<>();
        userInfoCallbacks = new UserInfoCallbacks() {
            @Override
            public void onUserInfoDetected(String username, String password) {
                if (!chkForExistedUser(username)) {
                    UsersData usrDta = new UsersData(username, password);
                    usrDetails.add(usrDta);
                    usrAdapter.notifyDataSetChanged();
                    usrLstRecyclervw.scrollToPosition(usrDetails.size() - 1);
                    Log.d("un: " + username + " : ", "up: " + password);
                }
            }
        };
        recyclerviewCallbacks = new RecyclerviewCallbacks() {
            @Override
            public void OnRecyclerViewItemClicked(View v, int position) {
                ChatCredentials.setChatWithId(usrDetails.get(position).getUsername());
                startActivity(new Intent(Home.this, Chat.class));
            }
        };
        usrAdapter = new UsersAdapter(Home.this, usrDetails, recyclerviewCallbacks);
        fireUsrData = new FirebaseData(Home.this, userInfoCallbacks, "users");
    }

    private boolean chkForExistedUser(String username) {
        for (UsersData data : usrDetails) {
            if (data.getUsername().contentEquals(username))
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpUsrList();
    }

    private void setUpUsrList() {

        usrLstRecyclervw.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        usrLstRecyclervw.setLayoutManager(lm);
        usrLstRecyclervw.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));
        usrLstRecyclervw.setItemAnimator(new DefaultItemAnimator());
        usrLstRecyclervw.setAdapter(usrAdapter);
        usrAdapter.notifyDataSetChanged();

    }
}
