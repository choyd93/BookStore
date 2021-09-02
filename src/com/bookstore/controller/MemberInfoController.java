package com.bookstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.MemberDTO;
import com.bookstore.util.CommonUtil;

public class MemberInfoController {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuilder sql = null;
	Connection conn = null;

	// > 가입된 멤버의 모든 정보를 가져오는 기능으로 관리자만 사용가능해야함
	// > 패스워드 정보는 가져오지 않음 (필요시 추가)
	public List<MemberDTO> selectAll() {
		List<MemberDTO> list = null; 
		list = new ArrayList<MemberDTO>();
		conn = CommonUtil.getConnection();
		try {
			sql = new StringBuilder();
			sql.append("SELECT MEMBER_ID, LOGIN_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS FROM MEMBER");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO temp = new MemberDTO(rs.getString("MEMBER_ID"), rs.getString("NAME"), rs.getString("PHONE"),
						rs.getString("EMAIL"), rs.getString("ROLE"), rs.getString("ADDRESS"), rs.getString("LOGIN_ID"));
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}

	// > Login 객체를 어떤 형태로 저장할지 몰라 우선 MemberDTO로 받아오는 걸로 설정
	public MemberDTO selectOne(MemberDTO m) {
		MemberDTO temp = null;
		try {
			conn = CommonUtil.getConnection();
			sql = new StringBuilder();
			sql.append("SELECT MEMBER_ID, LOGIN_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS FROM MEMBER WHERE ID = ?");
			conn.prepareStatement(sql.toString());
			pstmt.setString(1, m.getMemberId());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				temp = new MemberDTO(rs.getString("MEMBER_ID"), rs.getString("NAME"), rs.getString("PHONE"),
						rs.getString("EMAIL"), rs.getString("ROLE"), rs.getString("ADDRESS"), rs.getString("LOGIN_ID"),
						rs.getString("LOGIN_PASSWORD"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return temp;
	}
	
	public MemberDTO selectOne(String id) {
		MemberDTO temp = null;
		try {
			conn = CommonUtil.getConnection();
			sql = new StringBuilder();
			sql.append("SELECT MEMBER_ID, LOGIN_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS FROM MEMBER WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				temp = new MemberDTO(rs.getString("MEMBER_ID"), rs.getString("NAME"), rs.getString("PHONE"),
						rs.getString("EMAIL"), rs.getString("ROLE"), rs.getString("ADDRESS"), rs.getString("LOGIN_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return temp;
	}

	// > Login 객체를 어떤 형태로 저장할지 몰라 우선 MemberDTO로 받아오는 걸로 설정
	public boolean updatePassword(MemberDTO logIn, String password) {
		conn = CommonUtil.getConnection();

		sql = new StringBuilder();
		
		try {
			sql.append("UPDATE MEMBER SET LOGIN_PASSWORD = ?");
			sql.append(" WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, password);
			pstmt.setString(2, logIn.getMemberId());
			int result = pstmt.executeUpdate();
			if(result == 0) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CommonUtil.close(conn, pstmt);
		}

		return true;
	}
	
	// > Login 객체를 어떤 형태로 저장할지 몰라 id로 설정하는 것도 구분
	public boolean updatePassword(String id, String password) {
		conn = CommonUtil.getConnection();

		sql = new StringBuilder();
		
		try {
			sql.append("UPDATE MEMBER SET LOGIN_PASSWORD = ?");
			sql.append(" WHERE LOGIN_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()); 
			pstmt.setString(1, password);
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();
			if(result == 0) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CommonUtil.close(conn, pstmt);
		}
		return true;
	}
	
	
	public boolean updateInformation(String memberId, String data, String category) {
		conn = CommonUtil.getConnection();
		String ctg = category;
		sql = new StringBuilder();
		
		try {
			sql.append("UPDATE MEMBER SET " + ctg.toUpperCase() + " = ?");
			sql.append(" WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, data);
			pstmt.setString(2, memberId);
			int result = pstmt.executeUpdate();
			if(result == 0) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			CommonUtil.close(conn, pstmt);
		}
		return true;
	}

	public boolean validation(MemberDTO logIn, String password) {
		boolean result = false;
		conn = CommonUtil.getConnection();
		sql = new StringBuilder();
		sql.append("SELECT LOGIN_ID, LOGIN_PASSWORD FROM MEMBER ");
		sql.append("WHERE LOGIN_ID = ? AND LOGIN_PASSWORD = ? ");
		try {
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, logIn.getLoginId());
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public boolean validation(String login_id, String password) {
		boolean result = false;
		conn = CommonUtil.getConnection();
		sql = new StringBuilder();
		sql.append("SELECT LOGIN_ID, LOGIN_PASSWORD FROM MEMBER ");
		sql.append("WHERE LOGIN_ID = ? AND LOGIN_PASSWORD = ? ");

		try {
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, login_id);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public boolean validationInfor(String data, String category) {
		boolean result = false;
		String ctg = category;
		conn = CommonUtil.getConnection();
		sql = new StringBuilder();
		try {
			sql.append("SELECT PHONE, EMAIL FROM MEMBER ");
			sql.append("WHERE " + ctg.toUpperCase() + "= ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, data);
			rs = pstmt.executeQuery();

			if (rs.next()) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return result;
	}

	public boolean delete(String member_id) {
		boolean result = false;
		conn = CommonUtil.getConnection();
		if (conn == null) return result;
	
		sql = new StringBuilder();
		
		try {
			sql.append("DELETE FROM SHIPPING WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);

			pstmt.executeUpdate();
			sql.setLength(0);
			
			sql.append("DELETE FROM CART WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);
			sql.setLength(0);
			pstmt.executeUpdate();
			sql.setLength(0);
			
			sql.append("DELETE FROM ORDERS WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);
			sql.setLength(0);
			
			sql.append("DELETE FROM MEMBER WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);
			sql.setLength(0);
			int rs = pstmt.executeUpdate();
			if (rs != 0) result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}
	
}


//public boolean updatePhone(MemberDTO logIn, String phone) {
//conn = CommonUtil.getConnection();
//
//sql = new StringBuilder();
//
//try {
//	sql.append("UPDATE MEMBER SET PHONE = ?");
//	sql.append(" WHERE MEMBER_ID = ?");
//	pstmt = conn.prepareStatement(sql.toString());
//	pstmt.setString(1, phone);
//	pstmt.setString(2, logIn.getMember_id());
//	int result = pstmt.executeUpdate();
//	if(result == 0) return false;
//} catch (SQLException e) {
//	e.printStackTrace();
//}finally {
//	CommonUtil.close(conn, pstmt);
//}
//return true;
//}
//
//public boolean updateAddress(MemberDTO logIn, String address) {
//conn = CommonUtil.getConnection();
//
//sql = new StringBuilder();
//
//try {
//	sql.append("UPDATE MEMBER SET ADDRESS = ?");
//	sql.append(" WHERE MEMBER_ID = ?");
//	pstmt = conn.prepareStatement(sql.toString());
//	pstmt.setString(1, address);
//	pstmt.setString(2, logIn.getMember_id());
//	int result = pstmt.executeUpdate();
//	if(result == 0) return false;
//} catch (SQLException e) {
//	e.printStackTrace();
//}finally {
//	CommonUtil.close(conn, pstmt);
//}
//return true;
//}
//
//public boolean updateEmail(MemberDTO logIn, String email) {
//conn = CommonUtil.getConnection();
//
//sql = new StringBuilder();
//
//try {
//	sql.append("UPDATE MEMBER SET EMAIL = ?");
//	sql.append(" WHERE MEMBER_ID = ?");
//	pstmt = conn.prepareStatement(sql.toString());
//	pstmt.setString(1, email);
//	pstmt.setString(2, logIn.getMember_id());
//	int result = pstmt.executeUpdate();
//	if(result == 0) return false;
//} catch (SQLException e) {
//	e.printStackTrace();
//}finally {
//	CommonUtil.close(conn, pstmt);
//}
//return true;
//}
