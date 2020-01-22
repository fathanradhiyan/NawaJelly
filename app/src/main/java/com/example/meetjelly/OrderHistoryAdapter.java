package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter <OrderHistoryAdapter.OrderHistoryViewHolder> {
    private Context mContext;
    private ArrayList<OrderHistory> mOrderHistoryList;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistory> orderHistoryList) {
        mContext = context;
        mOrderHistoryList = orderHistoryList;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_order_history, viewGroup, false);
        return new OrderHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder orderHistoryViewHolder, int i) {
        OrderHistory currentItem = mOrderHistoryList.get(i);

        String dateOrder = currentItem.getmDateOrder();
        String harga = currentItem.getmTotalPrice();
        String listOrderHistory = currentItem.getmListOrderHistory();
        int id = currentItem.getId();

        orderHistoryViewHolder.mTextViewTotalPrice.setText("Rp" + harga + ",-");
        orderHistoryViewHolder.mTextViewDateOrder.setText(dateOrder);
        //orderHistoryViewHolder.mTextViewListOrderHistory.setText(listOrderHistory);
        //orderHistoryViewHolder.mTextViewId.setText(id);
    }

    @Override
    public int getItemCount() {
        return mOrderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewListOrderHistory;
        public TextView mTextViewDateOrder;
        public TextView mTextViewTotalPrice;
        public TextView mTextViewId;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            //mTextViewListOrderHistory = itemView.findViewById(R.id.list_orderHistory);
            mTextViewDateOrder = itemView.findViewById(R.id.dateOrder);
            mTextViewTotalPrice = itemView.findViewById(R.id.totalPrice);
            mTextViewId = itemView.findViewById(R.id.id_orderHistory);
        }
    }
}
