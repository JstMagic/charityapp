
package com.kodemakers.charity.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class StaffResult {

    @SerializedName("result")
    private ArrayList<StaffDetails> mResult;
    @SerializedName("status")
    private Long mStatus;

    public ArrayList<StaffDetails> getResult() {
        return mResult;
    }

    public void setResult(ArrayList<StaffDetails> result) {
        mResult = result;
    }

    public Long getStatus() {
        return mStatus;
    }



    public void setStatus(Long status) {
        mStatus = status;
    }
}
