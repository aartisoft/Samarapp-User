package com.smartloan.smtrick.samarapp_user;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Belal on 2/23/2017.
 */

public class Constants {

    public static final String STORAGE_PATH_UPLOADS = "NewImage/";
    public static final String DATABASE_PATH_UPLOADS = "NewImage";
    public static final String CHANNEL_ID = "samar app";
    public static final String CHANNEL_NAME = "samr app";
    public static final String CHANNEL_DESC = "samar app notification";
    public static final String SUCCESS = "Success";

    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static final DatabaseReference DATABASE_PATH_UPLOADS1 =DATABASE.getReference("NewImage");

    public static final String FCM_PUSH_URL = "https://fcm.googleapis.com/fcm/send";
}
