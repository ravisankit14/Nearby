package com.nearby.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearby.R;
import com.nearby.adapter.MyItemRecyclerViewAdapter;
import com.nearby.adapter.Nearby;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    public OnFragmentItemListener mItemListener;
    private static final String KEY =  "NEARBY_KEY";

    private RecyclerView mItemList;
    private MyItemRecyclerViewAdapter adapter;

    private List<Nearby> nearbyList;
    private String flag = "0";

    public ItemFragment() {
    }

    public void getList(List<Nearby> nearbyList2){

        this.nearbyList = nearbyList2;

        adapter = new MyItemRecyclerViewAdapter(getActivity(),nearbyList,mItemListener, flag);
        mItemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nearbyList = new ArrayList<>();

        if(savedInstanceState != null){
            nearbyList = savedInstanceState.getParcelableArrayList(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mItemList = (RecyclerView) view.findViewById(R.id.searchRecycleList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mItemList.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            nearbyList = bundle.getParcelableArrayList("nearby_list2");
            flag = "1";
        }
        adapter = new MyItemRecyclerViewAdapter(getActivity(),nearbyList,mItemListener,flag);
        mItemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentItemListener) {
            mItemListener = (OnFragmentItemListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY, (ArrayList<? extends Parcelable>) nearbyList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            nearbyList = savedInstanceState.getParcelableArrayList(KEY);
        }
    }

    public interface OnFragmentItemListener {
        void onFragmentItem(int position);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
