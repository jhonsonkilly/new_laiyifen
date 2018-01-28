package com.ody.p2p.shopcart;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * 拆单
 * 
 * @author dujianfeng
 * 
 */
public class SeparateBean extends BaseRequestBean {

	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {

		public List<ProductList> checkoutGroups;

		public List<ProductList> getCheckoutGroups() {
			return checkoutGroups;
		}

		public void setCheckoutGroups(List<ProductList> checkoutGroups) {
			this.checkoutGroups = checkoutGroups;
		}

	}

	public class ProductList {

		public List<Product> productList;

		public List<Product> getProductList() {
			return productList;
		}

		public void setProductList(List<Product> productList) {
			this.productList = productList;
		}

	}

	public class Product {

		private Long mpId;

		private Long productId;

		private Long merchantId;

		private String name;

		private String picUrl;

		private int num;

		private Float weight;

		private Float price;

		private String tax;

		private int stockNum;

		private int checked;

		private int lackOfStock;

		private int disabled;

		private String url60x60;

		private String url100x100;

		private String url160x160;

		private String url120x120;

		private String url500x500;

		private String url220x220;

		private String url800x800;

		public Long getMpId() {
			return mpId;
		}

		public void setMpId(Long mpId) {
			this.mpId = mpId;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Long getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(Long merchantId) {
			this.merchantId = merchantId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public Float getWeight() {
			return weight;
		}

		public void setWeight(Float weight) {
			this.weight = weight;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		public String getTax() {
			return tax;
		}

		public void setTax(String tax) {
			this.tax = tax;
		}

		public int getStockNum() {
			return stockNum;
		}

		public void setStockNum(int stockNum) {
			this.stockNum = stockNum;
		}

		public int getChecked() {
			return checked;
		}

		public void setChecked(int checked) {
			this.checked = checked;
		}

		public int getLackOfStock() {
			return lackOfStock;
		}

		public void setLackOfStock(int lackOfStock) {
			this.lackOfStock = lackOfStock;
		}

		public int getDisabled() {
			return disabled;
		}

		public void setDisabled(int disabled) {
			this.disabled = disabled;
		}

		public String getUrl60x60() {
			return url60x60;
		}

		public void setUrl60x60(String url60x60) {
			this.url60x60 = url60x60;
		}

		public String getUrl100x100() {
			return url100x100;
		}

		public void setUrl100x100(String url100x100) {
			this.url100x100 = url100x100;
		}

		public String getUrl160x160() {
			return url160x160;
		}

		public void setUrl160x160(String url160x160) {
			this.url160x160 = url160x160;
		}

		public String getUrl120x120() {
			return url120x120;
		}

		public void setUrl120x120(String url120x120) {
			this.url120x120 = url120x120;
		}

		public String getUrl500x500() {
			return url500x500;
		}

		public void setUrl500x500(String url500x500) {
			this.url500x500 = url500x500;
		}

		public String getUrl220x220() {
			return url220x220;
		}

		public void setUrl220x220(String url220x220) {
			this.url220x220 = url220x220;
		}

		public String getUrl800x800() {
			return url800x800;
		}

		public void setUrl800x800(String url800x800) {
			this.url800x800 = url800x800;
		}

	}

}
