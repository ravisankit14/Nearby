package com.nearby.utility;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.nearby.adapter.Nearby;
import com.nearby.map.FetchLocation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by ankit on 26/04/18.
 */

public class CalculateDistance {

    public List<Nearby> sortDistance(FetchLocation loc,List<Nearby> nearbyList){

        List<Nearby> distance = new ArrayList<>();
        LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());

        for(int i = 0; i< nearbyList.size(); i++){

            Nearby nearby =  nearbyList.get(i);
            double dis = (double) getDistance(latLng, new LatLng(Double.parseDouble(nearby.getLat()),Double.parseDouble(nearby.getLng())));

            nearby.setsDist((int) dis);
            distance.add(nearby);
        }

        return compare(distance);
    }

    private Double getDistance(LatLng point1, LatLng point2) {
        if (point1 == null || point2 == null) {
            return null;
        }

        return SphericalUtil.computeDistanceBetween(point1, point2)/1000;
    }

    private List<Nearby> compare(List<Nearby> distance){

        Collections.sort(distance, new Comparator<Nearby>() {
            @Override
            public int compare(Nearby o1, Nearby o2) {

                return o1.getsDist() > o2.getsDist() ? 1 : (o1.getsDist() < o2.getsDist() ? -1 : 0);
            }
        });

        return distance;
    }
}
