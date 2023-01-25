package com.ashadujjaman.cregadmin.Support;

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

public class AdapterSupportList extends RecyclerView.Adapter<AdapterSupportList.ViewHolder>{
    Context context;
    ArrayList<ClassChatList> supportList;
    public AdapterSupportList(Context context, ArrayList<ClassChatList> chatList)
    {
        this.context = context;
        this.supportList = chatList;
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
    public AdapterSupportList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_chat, parent, false);
        return new AdapterSupportList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSupportList.ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.userName.setText(supportList.get(position).getUserName());
        holder.lastMessage.setText(supportList.get(position).getLastMessage());
        holder.sentTime.setText(supportList.get(position).getSentTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityLegalSupport.class);
                intent.putExtra("UserId", supportList.get(holder.getAdapterPosition()).getUserId());
                intent.putExtra("UserName", supportList.get(holder.getAdapterPosition()).getUserName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return supportList.size();
    }
}
