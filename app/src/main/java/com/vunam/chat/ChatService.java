package com.vunam.chat;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.vunam.mylibrary.common.Constants;

import static com.vunam.mylibrary.common.Constants.DISPLAY_MESSAGE_ACTION;

public class ChatService extends Service {
    public ChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Intent intent1 = new Intent(DISPLAY_MESSAGE_ACTION);
        sendBroadcast(intent1);
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//        Intent intent1 = new Intent(DISPLAY_MESSAGE_ACTION);
//        intent1.putExtra("message","test_nambu");
//        //getApplicationContext().sendBroadcast(intent1);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Intent intent1 = new Intent(DISPLAY_MESSAGE_ACTION);
        Bundle bundle=intent.getBundleExtra(Constants.INTENT_DATA);
        intent1.putExtra(Constants.INTENT_DATA,bundle);
        //intent.setAction(DISPLAY_MESSAGE_ACTION);
        getApplicationContext().sendBroadcast(intent1);
    }

}
