package com.netease.nim.uikit.contact.core.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.contact.core.item.LabelItem;
import com.netease.nim.uikit.contact.core.model.ContactDataAdapter;

public class LabelHolder extends AbsContactViewHolder<LabelItem> {

	private TextView name;
	private ImageView imageView;

	@Override
	public void refresh(ContactDataAdapter contactAdapter, int position, LabelItem item) {
		this.name.setText(item.getText());

		switch (item.getText()){
			case "群组":
				this.imageView.setVisibility(View.VISIBLE);
				Glide.with(context).load(R.drawable.icon_group).into(this.imageView );
				break;
			case "通讯录":
				this.imageView.setVisibility(View.VISIBLE);
				Glide.with(context).load(R.drawable.icon_addressbook).into(this.imageView );
				break;
			case "消息记录":
				this.imageView.setVisibility(View.VISIBLE);
				Glide.with(context).load(R.drawable.icon_message).into(this.imageView );
				break;
		}

	}

	@Override
	public View inflate(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.nim_contacts_abc_item, null);
		this.name = (TextView) view.findViewById(R.id.tv_nickname);
		this.imageView=(ImageView)view.findViewById(R.id.imageview);
		return view;
	}

}
