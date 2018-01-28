package com.ody.p2p.check.aftersale.Bean;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

//初始化退货款
public class InitApplyRefundBean extends BaseRequestBean {

	private Data data;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return this.data;
	}

	public class Data {
		private String id;

		private String orderCode;

		private String createTime;

		private List<AfterSalesProductVOs> afterSalesProductVOs;

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return this.id;
		}

		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}

		public String getOrderCode() {
			return this.orderCode;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getCreateTime() {
			return this.createTime;
		}

		public void setAfterSalesProductVOs(
				List<AfterSalesProductVOs> afterSalesProductVOs) {
			this.afterSalesProductVOs = afterSalesProductVOs;
		}

		public List<AfterSalesProductVOs> getAfterSalesProductVOs() {
			return this.afterSalesProductVOs;
		}

	}

	public class AfterSalesProductVOs {
		private String id;
		private String mpId;
		private String productPicPath;

		private int productItemNum;

		private int buyType;
		private String chineseName;

		private String calculationUnit;

		private String productItemAmount;
		public int returnProductItemNum;

		private String productItemPrice;
		private int mayReturnProductItemNum;
		private String productPriceFinal;
		public boolean checked=false;
		public int checkedNum;

		/**
		 * @return the soItemId
		 */
		public String getSoItemId() {
			return id;
		}

		/**
		 * @param soItemId the soItemId to set
		 */
		public void setSoItemId(String soItemId) {
			this.id = soItemId;
		}

		/**
		 * @return the mayReturnProductItemNum
		 */
		public int getMayReturnProductItemNum() {
			return mayReturnProductItemNum;
		}

		/**
		 * @param mayReturnProductItemNum the mayReturnProductItemNum to set
		 */
		public void setMayReturnProductItemNum(int mayReturnProductItemNum) {
			this.mayReturnProductItemNum = mayReturnProductItemNum;
		}

		/**
		 * @return the productPriceFinal
		 */
		public String getProductPriceFinal() {
			return productPriceFinal;
		}

		/**
		 * @param productPriceFinal the productPriceFinal to set
		 */
		public void setProductPriceFinal(String productPriceFinal) {
			this.productPriceFinal = productPriceFinal;
		}

		public void setMpId(String mpId) {
			this.mpId = mpId;
		}

		public String getMpId() {
			return this.mpId;
		}

		public void setProductPicPath(String productPicPath) {
			this.productPicPath = productPicPath;
		}

		public String getProductPicPath() {
			return this.productPicPath;
		}

		public void setProductItemNum(int productItemNum) {
			this.productItemNum = productItemNum;
		}

		public int getProductItemNum() {
			return this.productItemNum;
		}

		public void setBuyType(int buyType) {
			this.buyType = buyType;
		}

		public int getBuyType() {
			return this.buyType;
		}

		public void setChineseName(String chineseName) {
			this.chineseName = chineseName;
		}

		public String getChineseName() {
			return this.chineseName;
		}

		public void setCalculationUnit(String calculationUnit) {
			this.calculationUnit = calculationUnit;
		}

		public String getCalculationUnit() {
			return this.calculationUnit;
		}

		public void setProductItemAmount(String productItemAmount) {
			this.productItemAmount = productItemAmount;
		}

		public String getProductItemAmount() {
			return this.productItemAmount;
		}

		public void setProductItemPrice(String productItemPrice) {
			this.productItemPrice = productItemPrice;
		}

		public String getProductItemPrice() {
			return this.productItemPrice;
		}

	}

}
