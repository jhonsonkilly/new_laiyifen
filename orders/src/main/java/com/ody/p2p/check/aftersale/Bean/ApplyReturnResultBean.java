package com.ody.p2p.check.aftersale.Bean;


import com.ody.p2p.base.BaseRequestBean;

public class ApplyReturnResultBean extends BaseRequestBean {

	public class Data {
		private int operationCode;

		private String operationMessage;

		private String orderAfterSalesId;

		public void setOperationCode(int operationCode) {
			this.operationCode = operationCode;
		}

		public int getOperationCode() {
			return this.operationCode;
		}

		public void setOperationMessage(String operationMessage) {
			this.operationMessage = operationMessage;
		}

		public String getOperationMessage() {
			return this.operationMessage;
		}

		public void setOrderAfterSalesId(String orderAfterSalesId) {
			this.orderAfterSalesId = orderAfterSalesId;
		}

		public String getOrderAfterSalesId() {
			return this.orderAfterSalesId;
		}

	}

	private Data data;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return this.data;
	}
}
