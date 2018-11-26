package com.vunam.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.vunam.chat.adapter.RecyclerViewAdapterChat;
import com.vunam.chat.model.Chat;
import com.vunam.chat.recycle.RecycleViewChat;
import com.vunam.chat.utils.NetworkUtilsChat;
import com.vunam.chat.utils.ProcessAsyncTaskChat;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.RecycleView.RecycleViewBasic;
import com.vunam.mylibrary.multithreading.ProcessAsyncTask;
import com.vunam.mylibrary.network.NetworkUtils;
import com.vunam.mylibrary.utils.Mapper;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.vunam.mylibrary.common.Constants.DISPLAY_MESSAGE_ACTION;

public class ListChatActivity extends AppCompatActivity {

	RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		recyclerView = (RecyclerView) findViewById(R.id.list_item);
		final String url = getResources().getString(R.string.list);
		SharedPreferences prefs = getApplicationContext().getSharedPreferences("GCM", MODE_PRIVATE);
		final String regid=prefs.getString("Notification","");
		Log.i("listchatactivity",LoginActivity.check);
		new ProcessAsyncTaskChat() {

			@Override
			public Object getBackground() {
				try {
					return new NetworkUtilsChat(getApplicationContext(),url + "?regId=" + regid).getResponseArray(null);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public void updateGUI(Object result) {

				JSONArray array = (JSONArray) result;

				List<Chat> items = new Mapper<Chat>().convertJsonArrayToList(array,Chat.class);

				RecyclerViewAdapterBasic myAdapter = new RecyclerViewAdapterChat(items, ListChatActivity.this)
						.setLayoutItem(R.layout.list_item)
						.setLayoutFooter(R.layout.list_item)
						.setLayoutHeader(R.layout.list_item);

				new RecycleViewChat(getApplicationContext())
						.setTypeRotation(LinearLayoutManager.VERTICAL)
						.setAdapter(myAdapter)
						.setTypeLayoutItemDecoration(R.drawable.line_bottom_recycleview)
						.into(recyclerView)
						.setLayoutList()
						.init();
			}
		}.execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LoginActivity.check = "0";
		Log.i("listchatactivity_resume", LoginActivity.check);
	}


}
