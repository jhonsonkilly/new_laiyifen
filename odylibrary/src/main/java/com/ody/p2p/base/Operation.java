package com.ody.p2p.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import com.ody.p2p.R;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.data.OdyDto;


/**
 * 基本的操作共通抽取
 * @author lxs
 * @version 1.0
 *
 */
public class Operation {

	/**激活Activity组件意图**/
	private Intent mIntent = new Intent();
	/***上下文**/
	private Activity mContext = null;
	/***整个应用Applicaiton**/
	private OdyApplication mApplication = null;
	
	public Operation(Activity mContext) {
		this.mContext = mContext;
		mApplication = (OdyApplication) this.mContext.getApplicationContext();
	}

	/**
	 * 跳转Activity
	 * @param activity 需要跳转至的Activity
	 */
	public void forward(Class<? extends Activity> activity) {
		mIntent.setClass(mContext, activity);
		mContext.startActivity(mIntent);
		mContext.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
	}
	
	/**
	 * 设置传递参数
	 * @param value 数据传输对象
	 */
	public void addParameter(OdyDto value) {
		mIntent.putExtra(OdySysEnv.ACTIVITY_DTO_KEY, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,OdyDto value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,Bundle value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,Serializable value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 设置传递参数
	 * @param key 参数key
	 * @param value 数据传输对象
	 */
	public void addParameter(String key,String value) {
		mIntent.putExtra(key, value);
	}
	
	/**
	 * 获取跳转时设置的参数
	 * @param key
	 * @return
	 */
	public Object getParameters(String key) {
		OdyDto parms = getParameters();
		if(null != parms){
			return parms.get(key);
		}else{
			parms = new OdyDto();
			parms.put(key, mContext.getIntent().getExtras().get(key));
		}
		return parms;
	}
	
	/**
	 * 获取跳转参数集合
	 * @return
	 */
	public OdyDto getParameters() {
		OdyDto parms = (OdyDto)mContext.getIntent().getExtras().getSerializable(OdySysEnv.ACTIVITY_DTO_KEY);
		return parms;
	}
	
	/**
	 * 设置全局Application传递参数
	 * @param strKey 参数key
	 * @param value 数据传输对象
	 */
	public void addGloableAttribute(String strKey,Object value) {
		mApplication.assignData(strKey, value);
	}
	
	/**
	 * 获取跳转时设置的参数
	 * @param strKey
	 * @return
	 */
	public Object getGloableAttribute(String strKey) {
		return mApplication.gainData(strKey);
	}

	
}
