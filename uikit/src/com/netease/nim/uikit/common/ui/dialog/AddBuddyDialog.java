package com.netease.nim.uikit.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nimlib.sdk.nos.model.NosThumbParam;
import com.netease.nimlib.sdk.nos.util.NosThumbImageUtil;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

public class AddBuddyDialog extends Dialog implements View.OnClickListener{
	private TextView contentTxt;
	private TextView titleTxt;
	private TextView submitTxt;
	private TextView cancelTxt;
	public EditText mEdit;

	private Context mContext;
	private String content;
	private OnCloseListener listener;
	private String positiveName;
	private String negativeName;
	private String title;
	private String account;
	private String msg;
	private HeadImageView headImageView;

	public AddBuddyDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	public AddBuddyDialog(Context context, int themeResId, String content) {
		super(context, themeResId);
		this.mContext = context;
		this.content = content;
	}

	public AddBuddyDialog(Context context, int themeResId) {
		super(context, themeResId);
		this.mContext = context;
	}


	protected AddBuddyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.mContext = context;
	}



	public AddBuddyDialog setListener(OnCloseListener listener){
		this.listener = listener;
		return this;
	}

	public AddBuddyDialog setTitle(String title, String account){
		this.title = title;
		this.account = account;
		return this;
	}

	public AddBuddyDialog setOnlyContent(String content){
		this.content = title;
		headImageView.setVisibility(View.GONE);
		titleTxt.setVisibility(View.GONE);
		mEdit.setVisibility(View.GONE);
		return this;
	}

	public AddBuddyDialog setPositiveButton(String name){
		this.positiveName = name;
		return this;
	}

	public AddBuddyDialog setNegativeButton(String name){
		this.negativeName = name;
		return this;
	}

	public String getEditMessage() {
		if (mEdit != null)
			return mEdit.getEditableText().toString();
		else return null;
	}

	public AddBuddyDialog setEditMessage(String msg) {
		this.msg = msg;
		return this;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nim_addbuddy_alert);
		setCanceledOnTouchOutside(false);
		initView();
	}

	private void initView(){
		contentTxt = (TextView)findViewById(R.id.content);
		titleTxt = (TextView)findViewById(R.id.title);
		submitTxt = (TextView)findViewById(R.id.submit);
		headImageView = findView(R.id.user_head_image);
		mEdit = (EditText)findViewById(R.id.et_message);
		submitTxt.setOnClickListener(this);
		cancelTxt = (TextView)findViewById(R.id.cancel);
		cancelTxt.setOnClickListener(this);


		contentTxt.setText(content);
		if(!TextUtils.isEmpty(positiveName)){
			submitTxt.setText(positiveName);
		}

		if(!TextUtils.isEmpty(negativeName)){
			cancelTxt.setText(negativeName);
		}

		if(!TextUtils.isEmpty(title)){
			titleTxt.setText(title);
			titleTxt.setVisibility(View.INVISIBLE);
		}


		if(!TextUtils.isEmpty(account)){
			headImageView.loadBuddyAvatar(account);
		}

		if(msg!=null) {
			if(!msg.equals(""))
			mEdit.setText(msg);
		}

	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.cancel) {
			if (listener != null) {
				listener.onClick(this, false);
			}
			this.dismiss();

		} else if (i == R.id.submit) {
			if (listener != null) {
				listener.onClick(this, true);
			}

		}
	}

	public interface OnCloseListener{
		void onClick(Dialog dialog, boolean confirm);
	}

	protected <T extends View> T findView(int resId) {
		return (T) (findViewById(resId));
	}
}
