package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context mContext;
    private ArrayList<orderListItem> mOrderList;

    public OrderAdapter(Context context, ArrayList<orderListItem> orderList){
        mContext = context;
        mOrderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_list, viewGroup, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        orderListItem currentItem = mOrderList.get(i);

        String codeOrder = currentItem.getmCodeOrder();
        String nameOrder = currentItem.getmNameOrder();
        String emailOrder = currentItem.getmEmailOrder();

        orderViewHolder.mTextViewCodeOrder.setText(codeOrder);
        orderViewHolder.mTextViewNameOrder.setText(nameOrder);
        orderViewHolder.mTextViewEmailOrder.setText(emailOrder);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewCodeOrder;
        public TextView mTextViewNameOrder;
        public TextView mTextViewEmailOrder;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mTextViewCodeOrder = itemView.findViewById(R.id.code_order);
            mTextViewNameOrder = itemView.findViewById(R.id.name_order);
            mTextViewEmailOrder = itemView.findViewById(R.id.email_order);
        }
    }
}
