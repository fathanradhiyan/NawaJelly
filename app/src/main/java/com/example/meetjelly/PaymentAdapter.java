package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>{
    private Context mContext;
    private ArrayList<paymentList> mPaymentList;

    public PaymentAdapter(Context context, ArrayList<paymentList> paymentList){
        mContext = context;
        mPaymentList = paymentList;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.payment_list, viewGroup, false);
        return new PaymentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder paymentViewHolder, int i) {
        paymentList currentItem = mPaymentList.get(i);

        String codePayment = currentItem.getmCode();
        String namePayment = currentItem.getmName();
        String emailPayment = currentItem.getmEmail();

        paymentViewHolder.mTextViewCode.setText(codePayment);
        paymentViewHolder.mTextViewName.setText(namePayment);
        paymentViewHolder.mTextViewEmail.setText(emailPayment);
    }

    @Override
    public int getItemCount() {
        return mPaymentList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewCode;
        public TextView mTextViewName;
        public TextView mTextViewEmail;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewCode = itemView.findViewById(R.id.code_payment);
            mTextViewName = itemView.findViewById(R.id.name_payment);
            mTextViewEmail = itemView.findViewById(R.id.email_payment);
        }
    }
}
