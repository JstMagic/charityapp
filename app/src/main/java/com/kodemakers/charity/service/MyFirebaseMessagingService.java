package com.kodemakers.charity.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kodemakers.charity.activities.DonationActivity;
import com.kodemakers.charity.activities.LoginActivity;
import com.kodemakers.charity.activities.SplashActivity;
import com.kodemakers.charity.app.Config;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.NotificationDetailModelList;
import com.kodemakers.charity.model.NotificationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    NotificationDetailModelList notificationDetailModelList;
    ArrayList<NotificationResponse> notificationResponseArrayList;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        try {
            notificationDetailModelList = PrefUtils.getNotification(getBaseContext());
            notificationResponseArrayList = notificationDetailModelList.notificationResponseArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            notificationDetailModelList = new NotificationDetailModelList();
            notificationResponseArrayList = new ArrayList<>();
        }

        try {
            notificationResponseArrayList.add(new NotificationResponse(remoteMessage.getData().toString()));
            notificationDetailModelList.notificationResponseArrayList = notificationResponseArrayList;
            PrefUtils.setNotification(notificationDetailModelList, getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "adding");
        }


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification


        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String click_action = data.getString("click_action");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            Log.e(TAG, "click_action: " + click_action);

            int notification_id = 0;
            Random r = new Random();
            int i1 = r.nextInt(1000000 - 1000) + 1000;

            notification_id = i1;
            Log.e(TAG, "notification_id: " + notification_id);

            //also add click_action in notificationActivity

            Intent resultIntent;
            if (click_action.equalsIgnoreCase("CHARITY_STATUS_NOTIFICATION")) {
                resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
                resultIntent.putExtra("from_notification", true);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (click_action.equalsIgnoreCase("MONEY_DONATION_NOTIFICATION")) {
                resultIntent = new Intent(getApplicationContext(), DonationActivity.class);
                resultIntent.putExtra("from_notification", true);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                resultIntent.putExtra("from_notification", true);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            // app is in background, show the notification in notification tray


            // check for image attachment
            if (TextUtils.isEmpty(imageUrl)) {
                showNotificationMessage(getApplicationContext(), notification_id, title, message, timestamp, resultIntent);
            } else {

                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), notification_id, title, message, timestamp, resultIntent, imageUrl);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, int notification_id, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(notification_id, title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, int notification_id, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(notification_id, title, message, timeStamp, intent, imageUrl);
    }
}
