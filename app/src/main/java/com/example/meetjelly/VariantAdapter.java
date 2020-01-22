package com.example.meetjelly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.VariantViewHolder> {
    private Context mContext;
    private ArrayList<Variant> mVariantList;
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =listener;
    }

    public VariantAdapter(Context context, ArrayList<Variant> variantList){
        mContext = context;
        mVariantList = variantList;
    }

    @NonNull
    @Override
    public VariantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.table_variant, viewGroup, false);
        return new VariantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VariantViewHolder variantViewHolder, int i) {
        Variant currentItem = mVariantList.get(i);

        String imageVariant = currentItem.getmImageVariant();
        String variantName = currentItem.getmVariant();
        int variantPrice = currentItem.getmVariantPrice();

        variantViewHolder.mVariantView.setText(variantName);
        variantViewHolder.mTextViewPrice.setText("Rp " + variantPrice + ",- / Botol");
        Picasso.get().load(imageVariant).fit().centerInside().into(variantViewHolder.mImageViewVariant);
    }

    @Override
    public int getItemCount() {
        return mVariantList.size();
    }

    public class VariantViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageViewVariant;
        public TextView mVariantView;
        public TextView mTextViewPrice;

        public VariantViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewVariant = itemView.findViewById(R.id.imageVariant);
            mVariantView = itemView.findViewById(R.id.variantName);
            mTextViewPrice = itemView.findViewById(R.id.variantPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position );
                        }
                    }
                }
            });
        }
    }
}
