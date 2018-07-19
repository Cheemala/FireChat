package com.trackbuzz.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trackbuzz.firebase.activity.Home;
import com.trackbuzz.firebase.model.ChatCredentials;
import com.trackbuzz.firebase.model.UsersData;
import com.trackbuzz.firebase.networkapi.APIServices;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private FirebaseData fireUsrData;
    private Button signBtn, signUpBtn;
    private EditText emailEdt, pswdEdt;
    private UserInfoCallbacks userInfoCallbacks;
    private List<UsersData> usrDetails;
    private DatabaseReference fireDatabase;
    private APIServices apiServices;


    @Override
    protected void onStart() {
        super.onStart();
        usrDetails = new ArrayList<>();
        userInfoCallbacks = new UserInfoCallbacks() {
            @Override
            public void onUserInfoDetected(String username, String password) {

                UsersData usrDta = new UsersData(username, password);
                usrDetails.add(usrDta);
                if (!signBtn.isEnabled())
                    signBtn.setEnabled(true);
                if (!signUpBtn.isEnabled())
                    signUpBtn.setEnabled(true);

            }
        };
        fireUsrData = new FirebaseData(SignIn.this, userInfoCallbacks, "users");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        fireDatabase = FirebaseDatabase.getInstance().getReference("users");
        emailEdt = findViewById(R.id.email_edt);
        pswdEdt = findViewById(R.id.pswd_edt);
        signBtn = findViewById(R.id.sign_btn);
        signUpBtn = findViewById(R.id.sign_up_btn);
        signBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_btn:
                proceedToSignin();
                break;
            case R.id.sign_up_btn:
                proceedToSignUp();
                break;
        }
    }

    private void proceedToSignUp() {
        boolean regSts = false;
        String usrId = emailEdt.getText().toString().trim();
        String usrPswd = pswdEdt.getText().toString().trim();

        //Email validation
        if (usrId.contentEquals("")) {
            ShowMsg.showMessage(SignIn.this, "Enter phone number!");
            return;
        }

        //Password validation
        if (usrPswd.contentEquals("")) {
            ShowMsg.showMessage(SignIn.this, "Enter Password!");
            return;
        }

        //Validations are clear
        UsersData userRegData = new UsersData(usrId, usrPswd);
        if (userRegData != null) {
            if (usrDetails != null && usrDetails.size() > 0) {
                for (UsersData usrDta : usrDetails) {
                    if (usrDta.getUsername().contentEquals(userRegData.getUsername()))
                        regSts = true;
                }
            }

            if (!regSts) {
                fireDatabase.child(usrId).setValue(userRegData);
                ShowMsg.showMessage(SignIn.this, "Registration successful!");
            } else {
                ShowMsg.showMessage(SignIn.this, "Someone already registered!");
            }
        } else {
            ShowMsg.showMessage(SignIn.this, "Please connect to internet!");
        }

    }

    private void proceedToSignin() {
        boolean usrLoginSts = false;
        String usrId = emailEdt.getText().toString().trim();
        String usrPswd = pswdEdt.getText().toString().trim();

        //Email validation
        if (usrId.contentEquals("")) {
            ShowMsg.showMessage(SignIn.this, "Enter Email!");
            return;
        }

        //Password validation
        if (usrPswd.contentEquals("")) {
            ShowMsg.showMessage(SignIn.this, "Enter Password!");
            return;
        }

        //Validations are clear

        if (usrDetails != null && usrDetails.size() > 0) {
            //String uniqueId = fireDatabase.push().getKey();
            if (usrDetails != null && usrDetails.size() > 0) {
                for (UsersData usrDta : usrDetails) {
                    if (usrDta.getUsername().contentEquals(usrId)) {
                        if (usrDta.getPassword().contentEquals(usrPswd)) {
                            usrLoginSts = true;
                        }
                    }
                }
            }

            if (usrLoginSts) {
                ChatCredentials.setMyId(usrId);
                ShowMsg.showMessage(SignIn.this, "Login successful!");
                startActivity(new Intent(SignIn.this, Home.class));
                finish();
            } else {
                ShowMsg.showMessage(SignIn.this, "Username (or) Password incorrect!");
            }
        }
    }
}
