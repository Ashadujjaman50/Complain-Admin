package com.ashadujjaman.cregadmin.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.ashadujjaman.cregadmin.R;
import com.ashadujjaman.cregadmin.model.NoticeModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AdapterNotice extends RecyclerView.Adapter<AdapterNotice.HolderViewNotice> {

    Context context;
    ArrayList<NoticeModel> noticeList;

    public AdapterNotice(Context context, ArrayList<NoticeModel> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public HolderViewNotice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate view
        View view = LayoutInflater.from(context).inflate(R.layout.card_notice, parent, false);
        return new HolderViewNotice(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderViewNotice holder, int position) {
        //get data
        NoticeModel noticeModel = noticeList.get(position);
        String noticeId = noticeModel.getNoticeId();
        String noticeTitle = noticeModel.getNoticeTitle();
        String noticeDesc = noticeModel.getNoticeDesc();
        String noticePublish = noticeModel.getNoticePublish();

        //convert timestamp to dd.mm/yyy hh:mm aa
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(Long.parseLong(noticeId));
        CharSequence time = DateFormat.format("dd MMM hh:mm aa", calendar1);

        //set data
        holder.noticeTitleTv.setText(noticeTitle);
        holder.noticeDescTv.setText(noticeDesc);
        holder.noticeTimeTv.setText(time);

        if (noticePublish.equals("Yes")){
            holder.noticeImageIv.setImageResource(R.drawable.ic_notifications_active);
        }
        else {
            holder.noticeImageIv.setImageResource(R.drawable.ic_notice_off);
        }

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder, noticeId);
            }
        });
    }

    private void showPopupMenu(HolderViewNotice holder, String noticeId) {
        PopupMenu popupMenu = new PopupMenu(context, holder.moreBtn, Gravity.END);

        popupMenu.getMenu().add(Menu.NONE,0,0,"Publish");
        popupMenu.getMenu().add(Menu.NONE,1,0,"Hide");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id==0){
                    updateNoticeAction("Yes", noticeId);
                }
                else if (id==1){
                    //
                    updateNoticeAction("No", noticeId);
                }
                return false;
            }
        });
        //show popupMenu
        popupMenu.show();
    }

    private void updateNoticeAction(String Action, String noticeId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notice");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("noticePublish", Action);

        ref.child(noticeId).updateChildren(hashMap);
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    static class HolderViewNotice extends RecyclerView.ViewHolder{

        TextView noticeTitleTv, noticeDescTv, noticeTimeTv;
        ImageView moreBtn, noticeImageIv;

        public HolderViewNotice(@NonNull View itemView) {
            super(itemView);

            //init view
            noticeTitleTv = itemView.findViewById(R.id.noticeTitleTv);
            noticeDescTv = itemView.findViewById(R.id.noticeDescTv);
            noticeTimeTv = itemView.findViewById(R.id.noticeTimeTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            noticeImageIv = itemView.findViewById(R.id.noticeImageIv);

        }
    }
}
