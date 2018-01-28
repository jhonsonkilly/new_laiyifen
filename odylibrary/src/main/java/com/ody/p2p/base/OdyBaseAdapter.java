package com.ody.p2p.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * ������
 * 
 * @author jianfeng.du
 * 
 */
public class OdyBaseAdapter<T> extends BaseAdapter {

	protected List<T> allDatas;
	protected Context ctx;

	public OdyBaseAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.allDatas = new ArrayList<T>();
		this.ctx = context;
	}

	public OdyBaseAdapter(List<T> allData, Context context) {
		// TODO Auto-generated constructor stub
		this.allDatas = allData;
		this.ctx = context;
	}

	public LayoutInflater getLayoutInflater() {
		return LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allDatas == null ? 0 : allDatas.size();
	}

	@Override
	public T getItem(int arg0) {
		// TODO Auto-generated method stub
		return allDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
