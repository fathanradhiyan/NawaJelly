package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyResellerAdapter extends RecyclerView.Adapter<MyResellerAdapter.MyResellerViewHolder> {
    private Context mContext;
    private ArrayList<CardMyReseller> mCardMyReseller;

    public MyResellerAdapter(Context context, ArrayList<CardMyReseller> myResellerList){
        mContext = context;
        mCardMyReseller = myResellerList;
    }

    @NonNull
    @Override
    public MyResellerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_my_reseller, viewGroup, false);
        return new MyResellerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyResellerViewHolder myResellerViewHolder, int i) {
        CardMyReseller cardMyReseller = mCardMyReseller.get(i);

        String resellerName = cardMyReseller.getmResellerName();

        myResellerViewHolder.mTextViewResellerName.setText(resellerName);
    }

    @Override
    public int getItemCount() {
        return mCardMyReseller.size();
    }

    public class MyResellerViewHolder extends  RecyclerView.ViewHolder {
        public TextView mTextViewResellerName;

        public MyResellerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewResellerName = itemView.findViewById(R.id.resellerName);
        }
    }
}
