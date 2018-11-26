package com.vunam.chat.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunam.chat.MainActivity;
import com.vunam.chat.R;
import com.vunam.chat.common.Constant;
import com.vunam.chat.model.Chat;
import com.vunam.chat.model.Message;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.LoadImg.ImgPicasso;
import com.vunam.mylibrary.utils.Android;

import java.util.List;

import static com.vunam.chat.common.Constant.*;
import static com.vunam.chat.common.Constant.TYPE_MESSAGE;

public class RecyclerViewAdapterMessage extends RecyclerViewAdapterBasic<Message> {
	private int layoutImage;

    public RecyclerViewAdapterMessage(List<Message> data, Context context)
    {
        super(data,context);
    }

	public RecyclerViewAdapterBasic setLayoutImage(int layoutImage)
	{
		this.layoutImage=layoutImage;
		return this;
	}

    @Override
    public RecyclerView.ViewHolder getViewItem(View view)
    {
          return null;
    }

    @Override
    public RecyclerView.ViewHolder getViewFooter(View view)
    {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder getViewHeader(View view)
    {
        return null;
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if(holder instanceof RecyclerViewHolderMessage)
		{
			RecyclerViewHolderMessage holderMessage= (RecyclerViewHolderMessage) holder;
			holderMessage.txtName.setText(getData().get(position).getMessage());
		} else {
            String urlImage = context.getResources().getString(R.string.nameserver);
            urlImage = urlImage + "uploads/" + getData().get(position).getUrlImage();
        	RecyclerViewHolderImage holderImage = (RecyclerViewHolderImage) holder;
			new ImgPicasso(context).load(urlImage, holderImage.imageView);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(copyData.get(position).getMessage().equals(""))
			return 2;
		else
			return 1;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case 1:
				return new RecyclerViewHolderMessage(inflater.inflate(layoutItem, parent, false));
			case 2:
				return new RecyclerViewHolderImage(inflater.inflate(layoutImage, parent, false));
			default:
				return getViewItem(inflater.inflate(layoutItem, parent, false));
		}
	}

	public class RecyclerViewHolderMessage extends RecyclerView.ViewHolder {
		TextView txtName;

		public RecyclerViewHolderMessage(View view) {
			super(view);
			txtName = (TextView) view.findViewById(R.id.textView_message);
		}
	}

	public class RecyclerViewHolderImage extends RecyclerView.ViewHolder {
		ImageView imageView;

		public RecyclerViewHolderImage(View view) {
			super(view);
			imageView = (ImageView) view.findViewById(R.id.imageView_message);
		}
	}
}
