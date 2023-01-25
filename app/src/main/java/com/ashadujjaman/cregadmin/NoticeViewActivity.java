package com.ashadujjaman.cregadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashadujjaman.cregadmin.adapter.AdapterNotice;
import com.ashadujjaman.cregadmin.model.NoticeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeViewActivity extends AppCompatActivity {

    TextView newNoticeBtn;
    RecyclerView noticeRV;

    AdapterNotice adapterNotice;
    ArrayList<NoticeModel> noticeList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);

        //init view
        newNoticeBtn = findViewById(R.id.newNoticeBtn);
        noticeRV = findViewById(R.id.noticeRV);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //loading
        loadNotice();

        newNoticeBtn.setOnClickListener(v -> {
            //send new notice activity
            startActivity(new Intent(NoticeViewActivity.this, NoticeActivity.class));
        });
    }

    private void loadNotice() {
        noticeList = new ArrayList<>();
        //setup adapter
        adapterNotice = new AdapterNotice(NoticeViewActivity.this, noticeList);

        //get data
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notice");
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //before getting reset
                noticeList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    NoticeModel noticeModel = ds.getValue(NoticeModel.class);
                    noticeList.add(noticeModel);
                    progressDialog.dismiss();
                }
                noticeRV.setAdapter(adapterNotice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}