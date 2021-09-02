package com.bookstore.model;

public class ShippingDTO {
	private String shipping_id;
	private String member_id;
	private String nickname;
	private String name;
	private String phone;
	private String address;
	
	// constructor
	public ShippingDTO() {
	}
	
	public ShippingDTO(String shipping_id, String member_id, String nickname, String name, String phone,
			String address) {
		this.shipping_id = shipping_id;
		this.member_id = member_id;
		this.nickname = nickname;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	// getter and setter
	public String getShipping_id() {
		return shipping_id;
	}
	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ShippingDTO [shipping_id=" + shipping_id + ", member_id=" + member_id + ", nickname=" + nickname
				+ ", name=" + name + ", phone=" + phone + ", address=" + address + "]";
	}

	
	
}
