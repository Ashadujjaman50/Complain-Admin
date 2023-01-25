package com.ashadujjaman.cregadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashadujjaman.cregadmin.Chat.ClassChatMessage;
import com.ashadujjaman.cregadmin.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterChatMessage extends RecyclerView.Adapter<AdapterChatMessage.ViewHolder>{
    Context context;
    ArrayList<ClassChatMessage> msgList;

    public AdapterChatMessage(Context context, ArrayList<ClassChatMessage> msgList){
        this.context = context;
        this.msgList = msgList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView message, time, messageMe, timeMe;
        LinearLayout meLay, otherLay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chatMessage);
            time = itemView.findViewById(R.id.chatSentTime);
            messageMe = itemView.findViewById(R.id.chatMessageMe);
            timeMe = itemView.findViewById(R.id.chatSentTimeMe);

            meLay = itemView.findViewById(R.id.chatMeLay);
            otherLay = itemView.findViewById(R.id.chatOtherLay);
        }
    }

    @NonNull
    @Override
    public AdapterChatMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatMessage.ViewHolder holder, int position) {
        String currentAdminId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        if(Objects.equals(msgList.get(position).getStatus(), currentAdminId)){
            holder.otherLay.setVisibility(View.GONE);
            holder.meLay.setVisibility(View.VISIBLE);
            holder.messageMe.setText(msgList.get(position).getMessage());
            holder.timeMe.setText(msgList.get(position).getSentTime());
        }else{
            if(msgList.get(position).getSentTime().equals("Admin")){
                holder.message.setBackground(context.getDrawable(R.drawable.message_style_admin));
//                holder.message.setBackgroundColor(context.getColor(R.color.teal_200));
            }else{
                holder.message.setBackground(context.getDrawable(R.drawable.message_style));
            }
            holder.otherLay.setVisibility(View.VISIBLE);
            holder.meLay.setVisibility(View.GONE);
            holder.message.setText(msgList.get(position).getMessage());
            holder.time.setText(msgList.get(position).getSentTime());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Message Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }


}
