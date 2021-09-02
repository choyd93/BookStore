package com.bookstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.OrderDTO;
import com.bookstore.util.CommonUtil;

public class OrderController {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public List<OrderDTO> selectOrder(String mId) {
		List<OrderDTO> list = null;
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT MEMBER_ID, ORDER_ID, ORDER_PRICE, TO_CHAR(ORDER_DATE, 'YY-MM-DD') ORDER_DATE");
			sql.append("  FROM ORDERS WHERE MEMBER_ID = ? ORDER BY ORDER_ID DESC");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, mId);
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderDTO>();
			while (rs.next()) {
				
				OrderDTO oDTO = new OrderDTO(
						rs.getString("MEMBER_ID"),
						rs.getString("ORDER_ID"),
						rs.getInt("ORDER_PRICE"), 
						rs.getString("ORDER_DATE"));
				list.add(oDTO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	// Order 전체 삭제용
	public boolean deleteOrderBook(String member_id) {
		boolean result = false;
		
		
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql = new StringBuilder();
			sql.append("DELETE FROM ORDER_BOOK WHERE ORDER_ID IN (SELECT ORDER_ID FROM ORDERS O WHERE O.MEMBER_ID = ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);
			int rs = pstmt.executeUpdate();
			if (rs != 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	// Order 전체 삭제용
	public boolean delete(String member_id) {
		boolean result = false;
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return result;
		
			sql = new StringBuilder();
			
			sql.append("DELETE FROM ORDERS O WHERE O.MEMBER_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);

			int rs = pstmt.executeUpdate();
			if (rs != 0) result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}
	
	// OrderBook 선택삭제용
	public boolean selectDeleteOrderBook(String member_id, String order_id) {
		boolean result = false;
		
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql = new StringBuilder();
			sql.append("DELETE FROM ORDER_BOOK WHERE ORDER_ID IN (SELECT ORDER_ID FROM ORDERS O WHERE O.MEMBER_ID = ? AND O.ORDER_ID = ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);
			pstmt.setNString(2, order_id);
			int rs = pstmt.executeUpdate();
			if (rs != 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	// OrderBook 선택삭제용
	public boolean selectDelete(String member_id, String order_id) {
		boolean result = false;
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return result;
		
			sql = new StringBuilder();
			
			sql.append("DELETE FROM ORDERS O WHERE O.MEMBER_ID = ? AND O.ORDER_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setNString(1, member_id);
			pstmt.setNString(2, order_id);

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
