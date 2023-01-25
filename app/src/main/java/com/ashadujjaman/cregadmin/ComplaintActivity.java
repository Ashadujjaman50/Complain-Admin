package com.ashadujjaman.cregadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.ashadujjaman.cregadmin.adapter.AdapterComplain;
import com.ashadujjaman.cregadmin.model.ComplainModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ComplaintActivity extends AppCompatActivity {

    RecyclerView complainRV;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    AdapterComplain adapterComplain;
    ArrayList<ComplainModel> complainList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        //init view
        complainRV = findViewById(R.id.complainRV);

        mAuth =FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        loadComplain();
    }

    private void loadComplain() {
        complainList = new ArrayList<>();
        //setup adapter
        adapterComplain = new AdapterComplain(ComplaintActivity.this, complainList);

        //get data
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Complain");
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //before getting reset list
                complainList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ComplainModel complainModel = ds.getValue(ComplainModel.class);
                    complainList.add(complainModel);
                    progressDialog.dismiss();
                }
                complainRV.setAdapter(adapterComplain);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}