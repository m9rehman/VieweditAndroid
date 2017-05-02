package com.example.mrehman.vieweditandroid;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by mrehman on 2017-05-02.
 */

public class TokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh(){
        Intent i = new Intent(this, RegistrationService.class);
        startService(i);
    }
}
