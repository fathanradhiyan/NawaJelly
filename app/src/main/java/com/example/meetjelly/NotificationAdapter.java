package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private Context mContext;
    private ArrayList<Notification> mNotificationList;

    public NotificationAdapter (Context context, ArrayList<Notification> notificationList){
        mContext = context;
        mNotificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_notification, viewGroup, false);
        return new NotificationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        Notification currentItem = mNotificationList.get(i);

//        int id_notification = currentItem.getmIdNotification();
//        String title_notification = currentItem.getmTitle();
//        String date_notification = currentItem.getmDate();
        String subject_notification = currentItem.getmSubject();
        String message_notification = currentItem.getmMessage();

//        notificationViewHolder.mIdNotification.setText("" + id_notification);
//        notificationViewHolder.mTitle.setText(title_notification);
//        notificationViewHolder.mDate.setText(date_notification);
        notificationViewHolder.mSubject.setText(subject_notification);
        notificationViewHolder.mMessage.setText(message_notification);

    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        public TextView mIdNotification;
        public TextView mTitle;
        public TextView mDate;
        public TextView mSubject;
        public TextView mMessage;

        public NotificationViewHolder( View itemView) {
            super(itemView);
            mIdNotification = itemView.findViewById(R.id.id_ntf);
            mTitle = itemView.findViewById(R.id.title_ntf);
            mDate = itemView.findViewById(R.id.dateOrder_ntf);
            mSubject = itemView.findViewById(R.id.subject_ntf);
            mMessage = itemView.findViewById(R.id.message_ntf);
        }
    }
}
