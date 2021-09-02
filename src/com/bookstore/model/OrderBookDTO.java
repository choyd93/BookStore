package com.bookstore.model;

public class OrderBookDTO {
	private String oId;
	private String bId;
	private int bPrice;
	private String bAmount;
	private String oStatus;
	
	// Join후에 필요한 변수
	private String bookTitle;
	private int salePrice;
	private int tot;
		
	public OrderBookDTO(String oId, String bId, int bPrice, String bAmount, String oStatus) {
		this.oId = oId;
		this.bId = bId;
		this.bPrice = bPrice;
		this.bAmount = bAmount;
		this.oStatus = oStatus;
	}
	
	//join후 받을 생성자
	public OrderBookDTO(String bookTitle, String bookAmount, int salePrice, int tot, String oStatus) {
		this.bookTitle = bookTitle;
		this.bAmount = bookAmount;
		this.salePrice = salePrice;
		this.tot = tot;
		this.oStatus = oStatus;
	}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public int getbPrice() {
		return bPrice;
	}

	public void setbPrice(int bPrice) {
		this.bPrice = bPrice;
	}

	public String getbAmount() {
		return bAmount;
	}

	public void setbAmount(String bAmount) {
		this.bAmount = bAmount;
	}

	public String getoStatus() {
		return oStatus;
	}

	public void setoStatus(String oStatus) {
		this.oStatus = oStatus;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getTot() {
		return tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}
	
	
	
	
	
}
