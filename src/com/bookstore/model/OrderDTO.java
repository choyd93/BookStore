package com.bookstore.model;

public class OrderDTO {
	private String mId;
	private String oId;
	private int orderPrice;
	private String orderDate;
	
	public OrderDTO(String mId, String oId, int orderPrice, String orderDate) {
		this.mId = mId;
		this.oId = oId;
		this.orderPrice = orderPrice;
		this.orderDate = orderDate;
	}
	
	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

}
