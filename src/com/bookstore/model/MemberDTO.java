package com.bookstore.model;

public class MemberDTO {
    private String memberId;
    private String loginId;
    private String loginPassword;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String role;

    public MemberDTO() {}

    public MemberDTO(String member_id) {
		super();
		this.memberId = member_id;
	}

	public MemberDTO(String member_id, String name, String phone, String email, String role, String address,
			String login_id) {
		super();
		this.memberId = member_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.role = role;
		this.address = address;
		this.loginId = login_id;
	}
	
	public MemberDTO(String member_id, String name, String phone, String email, String role, String address,
			String login_id, String login_password) {
		super();
		this.memberId = member_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.role = role;
		this.address = address;
		this.loginId = login_id;
		this.loginPassword = login_password;
	}

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MemberDTO [memberId=" + memberId + ", loginId=" + loginId + ", loginPassword=" + loginPassword
                + ", name=" + name + ", phone=" + phone + ", email=" + email + ", address=" + address + ", role=" + role
                + "]";
    }
    
    
    

}