package com.ashadujjaman.cregadmin.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ashadujjaman.cregadmin.R;

import java.util.ArrayList;

public class AdapterChatList extends RecyclerView.Adapter<AdapterChatList.ViewHolder> {
    Context context;
    ArrayList<ClassChatList> chatList;
    public AdapterChatList(Context context, ArrayList<ClassChatList> chatList)
    {
        this.context = context;
        this.chatList = chatList;
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
    public AdapterChatList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatList.ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.userName.setText(chatList.get(position).getUserName());
        holder.lastMessage.setText(chatList.get(position).getLastMessage());
        holder.sentTime.setText(chatList.get(position).getSentTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityMessage.class);
                intent.putExtra("UserId", chatList.get(holder.getAdapterPosition()).getUserId());
                intent.putExtra("UserName", chatList.get(holder.getAdapterPosition()).getUserName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
