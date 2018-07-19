package com.trackbuzz.firebase.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trackbuzz.firebase.R;
import com.trackbuzz.firebase.ShowMsg;
import com.trackbuzz.firebase.model.ChatCredentials;
import com.trackbuzz.firebase.model.UserMsgInfo;

public class Chat extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainMsgContainer;
    private LinearLayout chatLayout;
    private ImageView sendMsgImgBtn;
    private EditText typeMsgEdt;
    private ScrollView chatScrollVw;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;
    private boolean displyMsgSts = false;
    private DatabaseReference fireDbRefnceOne, fireDbRefnceTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatLayout = findViewById(R.id.chat_layout);
        typeMsgEdt = findViewById(R.id.type_msg_edt);
        chatScrollVw = findViewById(R.id.chat_scroll_vw);
        sendMsgImgBtn = findViewById(R.id.send_msg_img_btn);
        sendMsgImgBtn.setOnClickListener(this);
        fireDbRefnceOne = FirebaseDatabase.getInstance().getReference("messages/" + ChatCredentials.getMyId() + "_" + ChatCredentials.getChatWithId());
        fireDbRefnceTwo = FirebaseDatabase.getInstance().getReference("messages/" + ChatCredentials.getChatWithId() + "_" + ChatCredentials.getMyId());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Tst", "fired!");

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    Log.d("Tst Msg key", child.getKey());
                    Log.d("Tst User val", child.child("username").getValue().toString());
                    Log.d("Tst User msg", child.child("usermsg").getValue().toString());
                    Log.d("---------", "------------");


                    if (child.child("username").getValue().toString().contentEquals(ChatCredentials.getMyId())) {
                        addMessageBox(child.child("usermsg").getValue().toString(), 1);
                    } else if (child.child("username").getValue().toString().contentEquals(ChatCredentials.getChatWithId())) {
                        addMessageBox(child.child("usermsg").getValue().toString(), 0);
                    } else {
                    }

                }
                fireDbRefnceOne.removeEventListener(this);
                displyMsgSts = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Query newChild = fireDbRefnceOne.orderByKey().limitToLast(1);
                newChild.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Tst", "fired!");
                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            Log.d("Tst Msg key", child.getKey());
                            Log.d("Tst User val", child.child("username").getValue().toString());
                            Log.d("Tst User msg", child.child("usermsg").getValue().toString());

                            if (child.child("dispsts").getValue().toString().contentEquals("0")) {
                                if (child.child("username").getValue().toString().contentEquals(ChatCredentials.getMyId())) {
                                    addMessageBox(child.child("usermsg").getValue().toString(), 1);
                                } else if (child.child("username").getValue().toString().contentEquals(ChatCredentials.getChatWithId())) {
                                    addMessageBox(child.child("usermsg").getValue().toString(), 0);
                                } else {
                                }
                                fireDbRefnceOne.child(child.getKey()).child("dispsts").setValue("1");
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        fireDbRefnceOne.addValueEventListener(valueEventListener);
        fireDbRefnceOne.addChildEventListener(childEventListener);

    }


    public void addMessageBox(String message, int type) {
        View txtMsgLayout = null;

        if (type == 1) {
            txtMsgLayout = LayoutInflater.from(Chat.this).inflate(R.layout.right_bubble, null, false);
            TextView msgTxt = txtMsgLayout.findViewById(R.id.right_txt_msg_txt);
            msgTxt.setText("me\n" + message);
        } else if (type == 0) {
            txtMsgLayout = LayoutInflater.from(Chat.this).inflate(R.layout.left_bubble, null, false);
            TextView msgTxt = txtMsgLayout.findViewById(R.id.left_txt_msg_txt);
            msgTxt.setText("Buddy\n" + message);
        }
        chatLayout.addView(txtMsgLayout);
        chatScrollVw.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_msg_img_btn:
                sendMsgToReciepient();
                break;
        }
    }

    private void sendMsgToReciepient() {

        String txtMsg = typeMsgEdt.getText().toString();
        if (txtMsg == null) {
            ShowMsg.showMessage(Chat.this, "Type message!");
            return;
        }

        UserMsgInfo msgInfo = new UserMsgInfo(ChatCredentials.getMyId(), txtMsg, "0");
        fireDbRefnceOne.push().setValue(msgInfo);
        fireDbRefnceTwo.push().setValue(msgInfo);
        typeMsgEdt.setText("");
    }
}
