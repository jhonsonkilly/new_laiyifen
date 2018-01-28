package com.ody.p2p.productdetail.productdetail.bean;


import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.views.basepopupwindow.ProductBean;

import java.io.Serializable;
import java.util.List;

public class ProductInfoBean extends BaseRequestBean implements Serializable{



	public List<ProductBean> data;

	public List<ProductBean> getData() {
		return data;
	}

	public void setData(List<ProductBean> data) {
		this.data = data;
	}
}
