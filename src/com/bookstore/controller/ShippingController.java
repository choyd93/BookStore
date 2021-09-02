package com.bookstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.MemberDTO;
import com.bookstore.model.ShippingDTO;
import com.bookstore.util.CommonUtil;

public class ShippingController {
	static final int MAX_LIST = 3;
	PreparedStatement pstmt = null;
	StringBuilder sql = null;
	Connection conn = null;
	ResultSet rs = null;

	// 관리자용
	public List<ShippingDTO> selectAll() {
		List<ShippingDTO> list = null;
		list = new ArrayList<ShippingDTO>();
		conn = CommonUtil.getConnection();
		try {
			sql = new StringBuilder();
			sql.append("SELECT SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS FROM SHIPPING ");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShippingDTO temp = new ShippingDTO(rs.getString("SHIPPING_ID"), rs.getString("MEMBER_ID"),
						rs.getString("NICKNAME"), rs.getString("NAME"), rs.getString("PHONE"), rs.getString("ADDRESS"));
				list.add(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}

	public List<ShippingDTO> selectOne(MemberDTO logIn) {
		List<ShippingDTO> list = null;
		list = new ArrayList<ShippingDTO>();
		conn = CommonUtil.getConnection();
		try {
			sql = new StringBuilder();
			sql.append("SELECT SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS FROM SHIPPING WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, logIn.getMemberId());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShippingDTO temp = new ShippingDTO(rs.getString("SHIPPING_ID"), rs.getString("MEMBER_ID"),
						rs.getString("NICKNAME"), rs.getString("NAME"), rs.getString("PHONE"), rs.getString("ADDRESS"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}

	public List<ShippingDTO> selectOne(String id) {
		List<ShippingDTO> list = null;
		list = new ArrayList<ShippingDTO>();
		conn = CommonUtil.getConnection();
		sql = new StringBuilder();
		try {
			sql.append("SELECT SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS FROM SHIPPING WHERE MEMBER_ID = ? ORDER BY SHIPPING_ID");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShippingDTO temp = new ShippingDTO(rs.getString("SHIPPING_ID"), rs.getString("MEMBER_ID"),
						rs.getString("NICKNAME"), rs.getString("NAME"), rs.getString("PHONE"), rs.getString("ADDRESS"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}

	public int getMaxId() {
		int result = -1;
		rs = null;
		conn = CommonUtil.getConnection();
		if (conn == null)
			return result;
		sql = new StringBuilder();
		sql.append("SELECT MAX(SHIPPING_ID) FROM SHIPPING");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public int getMinId(String member_id) {
		int result = -1;
		rs = null;
		conn = CommonUtil.getConnection();
		if (conn == null)
			return result;
		sql = new StringBuilder();
		sql.append("SELECT MIN(SHIPPING_ID) FROM SHIPPING WHERE MEMBER_ID = ?");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}

	public int insert(ShippingDTO dto) {
		int result = -1;
		
		int sIdx = getMaxId() + 1;

		conn = CommonUtil.getConnection();
		if (conn == null) return result;
		
		sql = new StringBuilder();
		sql.append("INSERT INTO SHIPPING ");
		sql.append("	   (SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?) ");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 1;
			
			// SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS
			pstmt.setString(idx++, Integer.toString(sIdx));
			pstmt.setString(idx++, dto.getMember_id());
			pstmt.setString(idx++, dto.getNickname());
			pstmt.setString(idx++, dto.getName());
			pstmt.setString(idx++, dto.getPhone());
			pstmt.setString(idx++, dto.getAddress());
			int outcome = pstmt.executeUpdate();
			
			if(outcome != 0) result = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}

	public int delete(String member_id, String shipping_id) {
		int result = 0;
		conn = CommonUtil.getConnection();
		if (conn == null) return result;

		sql = new StringBuilder();
		sql.append("DELETE SHIPPING ");
		sql.append("WHERE MEMBER_ID = ? AND SHIPPING_ID = ?");
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, member_id);
			pstmt.setString(2, shipping_id);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public int update(ShippingDTO s, String shipping_id) {
		int result = -1;
		int sIdx = getMaxId() + 1;
		conn = CommonUtil.getConnection();
		if (conn == null) return result;
		
		sql = new StringBuilder();
		sql.append("UPDATE SHIPPING ");
		sql.append("   SET NICKNAME = ? ");
		sql.append("     , NAME = ? ");
		sql.append("     , PHONE = ? ");
		sql.append("     , ADDRESS = ? ");
		sql.append(" WHERE SHIPPING_ID =? ");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 1;
			pstmt.setString(idx++, s.getNickname());
			pstmt.setString(idx++, s.getName());
			pstmt.setString(idx++, s.getPhone());
			pstmt.setString(idx++, s.getAddress());
			pstmt.setString(idx++, shipping_id);
			int outcome = pstmt.executeUpdate();
			
			if(outcome != 0) result = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}

}
