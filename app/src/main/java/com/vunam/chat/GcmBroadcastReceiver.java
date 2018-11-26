package com.vunam.chat;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;
import com.vunam.mylibrary.Notification.NotificationBasic;
import com.vunam.mylibrary.utils.Android;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//if loginactivity is running
		if (LoginActivity.check.equals("1"))
		{
			Bundle bundle = new Bundle();
			String message = intent.getStringExtra("message");
			String url_image = intent.getStringExtra("url_image");
			bundle.putString("message", message);
			bundle.putString("url_image",url_image);
			Android.startService(context, ChatService.class, bundle);
		}
		//if not loginactivity running
		else {
			Android.startActivity(context, MainActivity.class, null);
		}
	}
}
