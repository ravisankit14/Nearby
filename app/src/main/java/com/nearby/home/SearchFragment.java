package com.nearby.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nearby.MainActivity;
import com.nearby.R;
import com.nearby.adapter.MyItemRecyclerViewAdapter;
import com.nearby.adapter.Nearby;
import com.nearby.localdata.SharedPrefForArrayList;
import com.nearby.map.FetchLocation;
import com.nearby.map.MapsActivity;
import com.nearby.restapi.model.Geometry;
import com.nearby.restapi.model.Result;
import com.nearby.restapi.model.Location;
import com.nearby.restapi.model.Model;
import com.nearby.restapi.services.ApiService;
import com.nearby.restapi.services.RestClientApi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String KEY =  "NEARBY_KEY";
    private OnFragmentSearchListener mSearchListener;

    private List<String> landmark;
    private List<String> lat;
    private List<String> lng;
    private EditText editSearch;
    private ListView searchList;
    private ArrayAdapter<String> arrayAdapter;
    private Nearby nearby;
    private SharedPrefForArrayList sp;
    private List<Nearby> list;
    FetchLocation fetchLocation;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        landmark = new ArrayList<>();
        sp = new SharedPrefForArrayList(getActivity().getApplication());
        list = new ArrayList<>();
        lat = new ArrayList<>();
        lng = new ArrayList<>();

        fetchLocation = FetchLocation.getInstance(getContext());

        if(savedInstanceState != null){
            list = savedInstanceState.getParcelableArrayList(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        editSearch = (EditText)  view.findViewById(R.id.editSearch);
        searchList = (ListView) view.findViewById(R.id.searchList);

        editSearch.addTextChangedListener(textWatcher);
        searchList.setOnItemClickListener(this);

        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,landmark);
        searchList.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY, (ArrayList<? extends Parcelable>) list);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
           list = savedInstanceState.getParcelableArrayList(KEY);
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String location = String.valueOf(fetchLocation.getLatitude() +","+ fetchLocation.getLongitude());
            if(s.length() > 5){
                searchApi(s.toString(),location);
                searchList.setVisibility(View.VISIBLE);
            }else{
                searchList.setVisibility(View.GONE);
                clearList();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentSearchListener) {
            mSearchListener = (OnFragmentSearchListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSearchListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            nearby = new Nearby(landmark.get(position),lat.get(position),lng.get(position));
            if(list.size() <= 10){
                list.add(nearby);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        mSearchListener.onFragmentSearch(list);
        editSearch.setText("");
        searchList.setVisibility(View.GONE);
        clearList();
    }

    private void searchApi(String search, String location){
        ApiService restClientApi = RestClientApi.getClient().create(ApiService.class);

        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("query", search);
        paramsMap.put("location", location);  // restricted to bangalore
        paramsMap.put("radius", "5000");
        paramsMap.put("key", "AIzaSyCDkV097YwJvusKCjXQZ4m9-klTp0loQk0");

        Call<Model> call = restClientApi.getPost(paramsMap);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {

                Log.e("response ", response.body().getStatus());
                if(response.body().getStatus().equalsIgnoreCase("ok")){

                    for(Result name : response.body().getResults()){

                        String mLand = name.getName();

                        Geometry geometry = name.getGeometry();
                        Location location = geometry.location;

                        Log.e("response ",geometry.location.lat + mLand);

                        landmark.add(mLand);
                        lat.add(location.lat);
                        lng.add(location.lng);
                    }

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@Nullable Call<Model> call,@Nullable Throwable t) {
                if(t != null)
                    Log.e("response",t.toString());
            }
        });
    }

    public interface OnFragmentSearchListener {
        void onFragmentSearch(List<Nearby> nearby);
    }

    public void clearList(){
        landmark.clear();
        lat.clear();
        lng.clear();
    }
}
