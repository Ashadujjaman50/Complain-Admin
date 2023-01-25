package com.ashadujjaman.cregadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ashadujjaman.cregadmin.Chat.ActivityChat;
import com.ashadujjaman.cregadmin.Scholar.ActivityScholarList;
import com.ashadujjaman.cregadmin.Support.ActivitySupportList;
import com.ashadujjaman.cregadmin.model.ClassUserInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    CardView scholar, complaint, chat, legalSupport,noticeId;
    ArrayList<String> scholarList, chatList, supportList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scholar = findViewById(R.id.scholarid);
        complaint =findViewById(R.id.complaintid);
        chat = findViewById(R.id.chatId);
        legalSupport =findViewById(R.id.legalSupportId);
        noticeId =findViewById(R.id.noticeId);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Data loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        scholarList = new ArrayList<>();
        chatList = new ArrayList<>();
        supportList = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Scholar");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scholarList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    String userId = snap.getKey();
                    Log.e("User", userId);
                    assert userId != null;
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(userId);
                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ClassUserInfo userInfo = documentSnapshot.toObject(ClassUserInfo.class);
                            assert userInfo != null;
                            scholarList.add(userInfo.getName());
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference supportRef = FirebaseDatabase.getInstance().getReference("SupportUser");
        supportRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                supportList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    String userId = snap.getKey();
                    Log.e("User", userId);
                    assert userId != null;
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(userId);
                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ClassUserInfo userInfo = documentSnapshot.toObject(ClassUserInfo.class);
                            assert userInfo != null;
                            supportList.add(userInfo.getName());
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Admin");
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    String userId = snap.getKey();
                    Log.e("User", userId);
                    assert userId != null;
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(userId);
                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ClassUserInfo userInfo = documentSnapshot.toObject(ClassUserInfo.class);
                            assert userInfo != null;
                            chatList.add(userInfo.getName());
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        scholar.setOnClickListener(this);
        chat.setOnClickListener(this);
        //legalSupport.setOnClickListener(this);

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ComplaintActivity.class));
            }
        });

        noticeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NoticeViewActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.scholarid:
                i = new Intent(this, ActivityScholarList.class);
                i.putExtra("nameList", scholarList);
                Log.e("User", " " + scholarList.size());
                startActivity(i);
                break;
            case R.id.chatId:
                i = new Intent(this, ActivitySupportList.class);
                i.putExtra("nameList", chatList);
                Log.e("User", " " + chatList.size());
                startActivity(i);
                break;
            /*case R.id.legalSupportId:
                i = new Intent(this, ActivityChat.class);
                i.putExtra("nameList", supportList);
                Log.e("User", " " + supportList.size());
                startActivity(i);*/

            default:
                break;
        }
    }

}
