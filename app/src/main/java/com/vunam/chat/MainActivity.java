package com.vunam.chat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vunam.chat.adapter.RecyclerViewAdapterMessage;
import com.vunam.chat.model.Message;
import com.vunam.chat.recycle.RecycleViewMessage;
import com.vunam.chat.utils.NetworkUtilsChat;
import com.vunam.chat.utils.ProcessAsyncTaskChat;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.common.Constants;
import com.vunam.mylibrary.network.NetworkUtils;
import com.vunam.mylibrary.utils.Android;
import com.vunam.mylibrary.utils.StringUtils;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.vunam.mylibrary.common.Constants.DISPLAY_MESSAGE_ACTION;

//import com.vunam.mylibrary.network.NetworkUtils;


public class MainActivity extends AppCompatActivity {

    Button send;
    Button camera;
    EditText textMessage;
    Boolean check=false;
    AppCompatActivity appCompatActivity;
	String regId;
	String urlImage;
	List<Message> listMessage = new ArrayList<Message>();
	RecyclerView recyclerView;
	RecyclerViewAdapterBasic myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check=true;
        appCompatActivity=this;
        urlImage = "";
        Android.setFlag(this);
        setContentView(R.layout.activity_main);
        LoginActivity.check="1";
		Log.i("Mainactivity",LoginActivity.check);
        //String projectNumber = getResources().getString(R.string.project_number);
        regId = new Android.MySharedPreferences(this).getSharedPreferences("regId");
        if(regId.equals(""))
		{
			regId = Android.getValueFromKeyOfBundle(this,Constants.INTENT_DATA,"regId");
			new Android.MySharedPreferences(this).putSharedPreferences("regId",regId);
		}
        textMessage = (EditText)findViewById(R.id.message);
        send = (Button)findViewById(R.id.send);
		camera = (Button)findViewById(R.id.camera);
		recyclerView = (RecyclerView)findViewById(R.id.recycleview_message);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String message = (!textMessage.getText().toString().equals("")) ? textMessage.getText().toString() : urlImage;
				String checkTypeSend = (urlImage.equals("")) ? "text" : "image";
				final String url = getResources().getString(R.string.call) + "?message=" + message + "&check_type_send=" + checkTypeSend +
						"&regid=" + regId;
				new Thread() {
					@Override
					public void run() {
						try {
							new NetworkUtilsChat(getApplicationContext(), url).getResponse(null);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
        camera.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				Android.gotoGaleryChoose(MainActivity.this, Constants.REQUEST_CODE_GALERY, "Choose image");
			}
		});

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		Toast.makeText(getApplicationContext(), "create", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        check=false;
        Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
		super.onResume();
		LoginActivity.check="1";
		Log.i("mainactivity_resume",LoginActivity.check);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "start" ,Toast.LENGTH_LONG).show();
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final String url = getResources().getString(R.string.upload_file);
		textMessage.setText("");
		if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_GALERY) {
			try {
				InputStream imageStream = getContentResolver().openInputStream(data.getData());
				final String filePath = Android.getPathFromURI(data.getData(), this);
				urlImage = Android.getFileName(data.getData(), this);
				new ProcessAsyncTaskChat() {
					@Override
					public Object getBackground() {
						return new NetworkUtilsChat(getApplicationContext(), url).uploadFile(filePath);
					}
				}.execute();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
		}
	}

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getBundleExtra(Constants.INTENT_DATA).getString("message");
			String urlImage =  intent.getBundleExtra(Constants.INTENT_DATA).getString("url_image");
            Message message = new Message(newMessage,urlImage);
			listMessage.add(message);
			//if is first time to show list message
			if(listMessage.size()==1)
			{
				myAdapter = new RecyclerViewAdapterMessage(listMessage,MainActivity.this)
						.setLayoutImage(R.layout.list_image)
						.setLayoutItem(R.layout.list_message);
				new RecycleViewMessage(getApplicationContext())
						.setTypeRotation(LinearLayoutManager.VERTICAL)
						.setAdapter(myAdapter)
						.setTypeLayoutItemDecoration(R.drawable.line_bottom_recycleview)
						.into(recyclerView)
						.setLayoutList()
						.init();
			}
			//not first time
			else
			{
               myAdapter.notifyDataSetChanged();
			}
        }
    };

}
