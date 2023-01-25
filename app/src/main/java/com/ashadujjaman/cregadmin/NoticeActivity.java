package com.ashadujjaman.cregadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NoticeActivity extends AppCompatActivity {

    EditText noticeTitleEt, noticeDescEt;
    Button submit_btn;
    String noticeTitle, noticeDesc;
    boolean valid = true;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        //init views
        noticeTitleEt = findViewById(R.id.noticeTitleEt);
        noticeDescEt = findViewById(R.id.noticeDescEt);
        submit_btn = findViewById(R.id.submit_btn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(noticeTitleEt);
                checkField(noticeDescEt);

                noticeTitle = noticeTitleEt.getText().toString().trim();
                noticeDesc = noticeDescEt.getText().toString().trim();
                if (valid){
                    inputData();
                }
            }
        });
    }

    private void inputData() {
        progressDialog.setMessage("Publishing....");
        progressDialog.show();

        //current time to millis
        String timestamp = ""+System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("noticeId", timestamp);
        hashMap.put("noticeTitle", noticeTitle);
        hashMap.put("noticeDesc", noticeDesc);
        hashMap.put("noticePublish", "Yes");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notice");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(unused -> {
                    //successful
                    progressDialog.dismiss();
                    Toast.makeText(NoticeActivity.this, "Publish Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(NoticeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void checkField(EditText textField){
        if (textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }
    }
}