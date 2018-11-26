package com.vunam.chat.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vunam.chat.MainActivity;
import com.vunam.chat.R;
import com.vunam.chat.model.Chat;
import com.vunam.mylibrary.Adapter.RecyclerViewAdapterBasic;
import com.vunam.mylibrary.utils.Android;

import java.util.List;

public class RecyclerViewAdapterChat extends RecyclerViewAdapterBasic<Chat> {

    public RecyclerViewAdapterChat(List<Chat> data, Context context)
    {
        super(data,context);
    }

    @Override
    public RecyclerView.ViewHolder getViewItem(View view)
    {
          return new RecyclerViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getViewFooter(View view)
    {
        return new RecyclerViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getViewHeader(View view)
    {
        return new RecyclerViewHolder(view);
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, final int position)
    {
        RecyclerViewHolder chatHolder = (RecyclerViewHolder) holder;
        chatHolder.txtName.setText(getData().get(position).getName());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putString("regId", getData().get(position).getRegId());
				Android.startActivity(context, MainActivity.class, bundle);
			}
		});
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        public RecyclerViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.name);
        }
    }
}
