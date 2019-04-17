package com.kodemakers.charity.custom;//package com.kodemakers.charity.user.custom;


import android.content.Context;
import com.kodemakers.charity.model.CharityResponse;
import com.kodemakers.charity.model.NotificationDetailModelList;

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

    ////////////////Post Notification
    public static void setNotification(NotificationDetailModelList currentUser, Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "notification_pref", 0);
        complexPreferences.putObject("notification_pref_value", currentUser);
        complexPreferences.commit();
    }

    public static void clearCurrentNotification(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "notification_pref", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }


    public static NotificationDetailModelList getNotification(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "notification_pref", 0);
        NotificationDetailModelList currentUser = complexPreferences.getObject("notification_pref_value", NotificationDetailModelList.class);
        return currentUser;
    }
}