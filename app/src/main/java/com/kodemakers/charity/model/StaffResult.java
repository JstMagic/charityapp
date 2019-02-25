
package com.kodemakers.charity.model;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class StaffResult {
    @SerializedName("result")
    private ArrayList<StaffDetails> mResult;
    @SerializedName("status")
    private String mStatus;
    public ArrayList<StaffDetails> getResult() {
        return mResult;
    }
    public void setResult(ArrayList<StaffDetails> result) {
        mResult = result;
    }
    public String getStatus() {
        return mStatus;
    }
    public void setStatus(String status) {
        mStatus = status;
    }
}
