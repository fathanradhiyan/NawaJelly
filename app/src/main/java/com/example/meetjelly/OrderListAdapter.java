package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter <OrderListAdapter.OrderListViewHolder> {
    private Context mContext;
    private ArrayList<OrderList> mOrderList_List;

    public OrderListAdapter(Context context, ArrayList<OrderList> orderList){
        mContext = context;
        mOrderList_List = orderList;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_order_list, viewGroup, false);
        return new OrderListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        OrderList currentItem = mOrderList_List.get(i);

        String dateOrder = currentItem.getmDateOrder();
        String time = currentItem.getmTime();
        String transaction = currentItem.getmTransaction();
        String totalPrice = currentItem.getmTotalPrice();

        orderListViewHolder.mTextViewDateOrder.setText(dateOrder);
        Log.d("aing", time);
        orderListViewHolder.mTextViewTime.setText(time);
        orderListViewHolder.mTextViewTransaction.setText(transaction);
        orderListViewHolder.mTextViewTotalPrice.setText("Rp" + totalPrice + ",-");
    }

    @Override
    public int getItemCount() {
        return mOrderList_List.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewDateOrder;
        public TextView mTextViewTime;
        public TextView mTextViewTransaction;
        public TextView mTextViewTotalPrice;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewDateOrder = itemView.findViewById(R.id.dateOrder);
            mTextViewTime = itemView.findViewById(R.id.time);
            mTextViewTransaction = itemView.findViewById(R.id.transaction);
            mTextViewTotalPrice = itemView.findViewById(R.id.totalPrice);
        }
    }
}
