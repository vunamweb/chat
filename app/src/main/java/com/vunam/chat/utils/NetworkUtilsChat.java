package com.vunam.chat.utils;

import android.content.Context;

import com.vunam.mylibrary.network.NetworkUtils;

public class NetworkUtilsChat extends NetworkUtils
{
	public NetworkUtilsChat(Context context, String url) {
		super(context,url);
	}

	@Override
	public Object registerNotification()
	{
		return null;
	}

	@Override
	public void updateGUI(Object result)
	{

	}

}
