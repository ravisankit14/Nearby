package com.nearby.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearby.R;
import com.nearby.home.ItemFragment.OnFragmentItemListener;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Nearby> mValues;
    private Context mContext;
    private OnFragmentItemListener mItemListListner;
    private String mFlag;

    public MyItemRecyclerViewAdapter(Context context, @NonNull List<Nearby> items, OnFragmentItemListener listener, String flag) {
        mContext = context;
        mValues = items;
        mItemListListner = listener;
        mFlag = flag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.mLandmark.setText(mValues.get(position).getLandmark());
        holder.mLatLng.setText(mValues.get(position).getLat() +" , " + mValues.get(position).getLng());

        if(mFlag.equals("1")){
            holder.mDelete.setVisibility(View.GONE);
        }else{
            holder.mDelete.setVisibility(View.VISIBLE);
        }

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mValues.remove(holder.mItem);
                notifyItemRemoved(position);

                if(mItemListListner != null){
                    //mItemListListner.onFragmentItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if(mFlag.equals("1") && mValues.size() > 0 && mValues.size() < 3){
            return 2;

        }else if(mFlag.equals("0") && mValues.size() > 10){
            Toast.makeText(mContext,"Please delete some item.",Toast.LENGTH_SHORT).show();
            return mValues.size() - 1;
        }else {
            return mValues.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mLandmark;
        private final TextView mLatLng;
        private final ImageView mDelete;
        private Nearby mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLandmark = (TextView) view.findViewById(R.id.landamark);
            mLatLng = (TextView) view.findViewById(R.id.latlng);
            mDelete = (ImageView) view.findViewById(R.id.delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLatLng.getText() + "'";
        }
    }
}
