package com.ody.p2p.productdetail.views;

import android.content.Context;
import android.view.View;

import com.ody.p2p.views.odyscorllviews.OdySnapPageLayout;


public class MyProductContentPage implements OdySnapPageLayout.McoySnapPage {
	
	private Context context;

	private View rootView = null;

	public MyProductContentPage(Context context, View rootView) {
		this.context = context;
		this.rootView = rootView;
	}

	@Override
	public View getRootView() {
		return rootView;
	}

	@Override
	public boolean isAtTop() {
		return true;
	}

	@Override
	public boolean isAtBottom() {
		return false;
	}

}
