package com.nearby.restapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justdial on 13/2/18.
 */

public class Model {

    @SerializedName("results")
    public List<Result> results = new ArrayList<>();

    @SerializedName("status")
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


}
