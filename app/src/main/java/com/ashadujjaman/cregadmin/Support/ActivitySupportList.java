package com.ashadujjaman.cregadmin.Support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashadujjaman.cregadmin.Chat.ClassChatList;
import com.ashadujjaman.cregadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ActivitySupportList extends AppCompatActivity {
    TextView emptyText;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    String adminId;
    LinearLayoutManager manager;

    ArrayList<ClassChatList> supportList;
    ArrayList<String> nameList;
    AdapterSupportList adapter;

    FirebaseDatabase database;
    DatabaseReference supportRef;
    FirebaseFirestore fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_list);
        Initialize();
    }
    private void Initialize() {
        emptyText = findViewById(R.id.supportEmptyText);
        recyclerView = findViewById(R.id.supportRecyclerView);
        mAuth = FirebaseAuth.getInstance();
        adminId = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        supportRef = database.getReference("SupportUser");
        fireStore = FirebaseFirestore.getInstance();

        manager = new LinearLayoutManager(ActivitySupportList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        nameList = new ArrayList<>();
        nameList = getIntent().getStringArrayListExtra("nameList");
        supportList = new ArrayList<>();
        supportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                supportList.clear();
                int i=0;
                for(DataSnapshot snap : snapshot.getChildren()){
                    String userId = snap.getKey();
                    String name = nameList.get(i++);
                    Log.e("User", userId);
                    ClassChatList chat = new ClassChatList(name, "This is last message", "22 jul 10:50pm", userId);
                    supportList.add(chat);
                }
                if(supportList.size() > 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.GONE);
                }else{
                    recyclerView.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivitySupportList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new AdapterSupportList(ActivitySupportList.this, supportList);
        recyclerView.setAdapter(adapter);
    }
}