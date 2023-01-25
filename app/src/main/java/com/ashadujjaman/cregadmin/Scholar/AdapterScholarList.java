package com.ashadujjaman.cregadmin.Scholar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashadujjaman.cregadmin.Chat.ClassChatList;
import com.ashadujjaman.cregadmin.R;

import java.util.ArrayList;

public class AdapterScholarList extends RecyclerView.Adapter<AdapterScholarList.ViewHolder> {
    Context context;
    ArrayList<ClassChatList> scholarList;
    public AdapterScholarList(Context context, ArrayList<ClassChatList> chatList)
    {
        this.context = context;
        this.scholarList = chatList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userName, lastMessage, sentTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.chatUserName);
            lastMessage = itemView.findViewById(R.id.chatLastMessage);
            sentTime = itemView.findViewById(R.id.chatTime);
        }
    }

    @NonNull
    @Override
    public AdapterScholarList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_chat, parent, false);
        return new AdapterScholarList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterScholarList.ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.userName.setText(scholarList.get(position).getUserName());
        holder.lastMessage.setText(scholarList.get(position).getLastMessage());
        holder.sentTime.setText(scholarList.get(position).getSentTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityScholarMessage.class);
                intent.putExtra("UserId", scholarList.get(holder.getAdapterPosition()).getUserId());
                intent.putExtra("UserName", scholarList.get(holder.getAdapterPosition()).getUserName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scholarList.size();
    }
}
