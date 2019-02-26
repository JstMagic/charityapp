package com.kodemakers.charity.custom;//package com.kodemakers.charity.user.custom;


import android.content.Context;
import com.kodemakers.charity.model.CharityResponse;
public class PrefUtils {

    public static void setUser(CharityResponse currentUser, Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        complexPreferences.putObject("user_pref_value", currentUser);
        complexPreferences.commit();
    }

    public static void clearCurrentUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

    public static CharityResponse getUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        CharityResponse currentUser = complexPreferences.getObject("user_pref_value", CharityResponse.class);
        return currentUser;
    }

}