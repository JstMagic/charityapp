
package com.kodemakers.charity.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class StaffResult {

    @SerializedName("result")
    private List<StaffDetails> mResult;
    @SerializedName("status")
    private Long mStatus;

    public List<StaffDetails> getResult() {
        return mResult;
    }

    public void setResult(List<StaffDetails> result) {
        mResult = result;
    }

    public Long getStatus() {
        return mStatus;
    }



    public void setStatus(Long status) {
        mStatus = status;
    }
}
