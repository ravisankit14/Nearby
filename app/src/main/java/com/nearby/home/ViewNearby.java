package com.nearby.home;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nearby.map.FetchLocation;
import com.nearby.utility.CalculateDistance;
import com.nearby.R;
import com.nearby.adapter.Nearby;

import java.util.ArrayList;
import java.util.List;

public class ViewNearby extends AppCompatActivity {

    ItemFragment itemFragment;
    FetchLocation fetchLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nearby);

        fetchLocation = FetchLocation.getInstance(getBaseContext());
        FragmentManager fm = getSupportFragmentManager();
        //list = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        List<Nearby> nearbyList = bundle.getParcelableArrayList("nearby_list");

        CalculateDistance cal = new CalculateDistance();
        nearbyList = cal.sortDistance(fetchLocation,nearbyList);

        if(savedInstanceState == null) {
            Bundle bundle1 = new Bundle();
            bundle1.putParcelableArrayList("nearby_list2", (ArrayList<? extends Parcelable>) nearbyList);
            FragmentTransaction ft2 = fm.beginTransaction();
            itemFragment = new ItemFragment();
            itemFragment.setArguments(bundle1);
            ft2.add(R.id.nearbyFragment, itemFragment);
            ft2.commit();
        }else{
            itemFragment = (ItemFragment) getSupportFragmentManager().getFragment(savedInstanceState,"itemFragment");
        }

        /*

        HeapSort ob = new HeapSort();
        ob.sort(arr);

        HeapSort.printArray(arr);*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"itemFragment",itemFragment);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        itemFragment = (ItemFragment) getSupportFragmentManager().getFragment(savedInstanceState, "itemFragment");
    }
}
