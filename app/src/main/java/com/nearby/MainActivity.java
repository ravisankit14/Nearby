package com.nearby;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nearby.adapter.Nearby;
import com.nearby.home.ItemFragment;
import com.nearby.home.SearchFragment;
import com.nearby.home.SearchFragment.OnFragmentSearchListener;
import com.nearby.home.ViewNearby;
import com.nearby.map.FetchLocation;
import com.nearby.map.MapsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 24/04/18.
 */

public class MainActivity extends AppCompatActivity implements OnFragmentSearchListener, ItemFragment.OnFragmentItemListener {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private FragmentManager fm;
    private SearchFragment searchFragment;
    private ItemFragment itemFragment;
    private Button nearMeButton, animateOnMap;
    FetchLocation fetchLocation;

    private List<Nearby> nearbyList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        nearMeButton = (Button) findViewById(R.id.nearMeButton);
        animateOnMap = (Button) findViewById(R.id.animateOnMap);

        nearbyList = new ArrayList<>();
        checkForPermission();

        if(savedInstanceState == null){

            FragmentTransaction ft = fm.beginTransaction();
            searchFragment = new SearchFragment();
            ft.add(R.id.searchFragment,searchFragment);
            ft.commit();

            FragmentTransaction ft2 = fm.beginTransaction();
            itemFragment = new ItemFragment();
            ft2.add(R.id.listFragment,itemFragment);
            ft2.commit();

        }else{
            searchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState,"searchFragment");
            itemFragment = (ItemFragment) getSupportFragmentManager().getFragment(savedInstanceState,"itemFragment");
        }

        nearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nearbyList.size() > 1){
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("nearby_list", (ArrayList<Nearby>) nearbyList);

                    Intent intent = new Intent(getBaseContext(), ViewNearby.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(),"Please enter landmark.",Toast.LENGTH_SHORT).show();
                }


            }
        });

        animateOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nearbyList.size() > 0) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("animate_list", (ArrayList<Nearby>) nearbyList);

                    Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else{
                    Toast.makeText(getBaseContext(),"No route found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"searchFragment",searchFragment);
        getSupportFragmentManager().putFragment(outState,"itemFragment",itemFragment);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        searchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState,"searchFragment");
        itemFragment = (ItemFragment) getSupportFragmentManager().getFragment(savedInstanceState,"itemFragment");
    }

    private void checkForPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
        }else{
            fetchLocation = FetchLocation.getInstance(getBaseContext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0){

                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                        fetchLocation = FetchLocation.getInstance(getBaseContext());
                    }

                }
                return;
            }
        }
    }

    @Override
    public void onFragmentSearch(List<Nearby> nearbyList) {

        this.nearbyList = nearbyList;

        itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        if(itemFragment != null){
            itemFragment.getList(nearbyList);

        }else{

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("parcel_list", (ArrayList<? extends Parcelable>) nearbyList);

            FragmentTransaction ft2 = fm.beginTransaction();
            itemFragment = new ItemFragment();
            itemFragment.setArguments(bundle);
            ft2.add(R.id.listFragment,itemFragment);
            ft2.commit();
            itemFragment.getList(nearbyList);

        }
    }

    @Override
    public void onFragmentItem(int position) {
        nearbyList.remove(position);
    }
}
