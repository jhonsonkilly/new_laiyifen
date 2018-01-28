package com.ody.p2p.RefoundInfo;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

public class OrderInfoBean extends BaseRequestBean {
	public Data data;

	public class Data {
		public int cancelTime;
		public double amount; // 总价
		public String merchantName;
		public String orderCode;
		public String orderCreateTime;
		public String orderCreateTimeStr;
		public int orderStatus;
		public int orderReturnStatus;
		public String orderStatusName;
		public String payGateway;
		public String payMethod;
		public List<ProductList> productList;
		public Receiver receiver;
		public String totalCount;
		public int orderBusinessType;//海关 0普通 1 海关
		public String orderReturnStatusName;//海关文字描述
		
		public String merchantId;
		public float productAmount; // 产品总价
		public float orderDeliveryFeeAccounting; // 运费
		public float promotionAmount; // 促销
		public float taxAmount; // 税费

		public String orderNeedCsName;
	}

	public class ProductList {
		public String attrs;
		public String merchantId;
		public String mpId;
		public String name;
		public String picUrl;
		public int totalCount;
		public int num;
		public double price;
		public String productId;
	}

	public class Receiver {
		public String areaId;
		public String areaName;
		public String cityId;
		public String cityName;
		public String countryId;
		public String countryName;
		public String detailAddress;
		public String provinceid;
		public String provinceName;
		public String receiverId;
		public String receiverMobile;
		public String receiverName;
		public String receiverPhone;

	}

}
