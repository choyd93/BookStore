package com.bookstore.model;

public class CartDTO {
	private String mId;
	private String bId;
	private String bookAmount;
	private String sum;
	
	public CartDTO(String mId, String bId, String bookAmount, String sum) {
		this.mId = mId;
		this.bId = bId;
		this.bookAmount = bookAmount;
		this.sum = sum;
	}
	

	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getBookAmount() {
		return bookAmount;
	}
	public void setBookAmount(String bookAmount) {
		this.bookAmount = bookAmount;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	@Override
	public String toString() {
		return "CartDTO [mId=" + mId + ", bId=" + bId + ", bookAmount=" + bookAmount + ", sum=" + sum + "]";
	}
	
	
}
