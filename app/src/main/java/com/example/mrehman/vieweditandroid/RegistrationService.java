package com.example.mrehman.vieweditandroid;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by mrehman on 2017-05-02.
 */

public class RegistrationService extends IntentService {
    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        InstanceID myID = InstanceID.getInstance(this);


        try {
            String registrationToken = myID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d("REG_TOKEN",registrationToken);


            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(registrationToken, "/topics/my_little_topic", null);


        }
        catch (IOException ex){
            Log.e("EXCEPTION_REGISTRATION", "Device registration failed", ex);

        }


    }
}
