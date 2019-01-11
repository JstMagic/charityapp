package com.kodemakers.charity.custom;//package com.kodemakers.charity.user.custom;
//
//
//import android.content.Context;
//
//import com.nkdroid.taazakitchenadmin.model.LoginResponse;
//
//public class PrefUtils {
//    public static void setUser(LoginResponse currentUser, Context ctx) {
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
//        complexPreferences.putObject("user_pref_value", currentUser);
//        complexPreferences.commit();
//    }
//
//    public static void clearCurrentUser(Context ctx) {
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
//        complexPreferences.clearObject();
//        complexPreferences.commit();
//    }
//
//
//    public static LoginResponse getUser(Context ctx) {
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
//        LoginResponse currentUser = complexPreferences.getObject("user_pref_value", LoginResponse.class);
//        return currentUser;
//    }
//
//
//
//
//
//}