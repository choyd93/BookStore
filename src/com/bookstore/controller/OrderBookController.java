package com.bookstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.OrderBookDTO;
import com.bookstore.util.CommonUtil;

public class OrderBookController {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public List<OrderBookDTO> selectAll() {
		List<OrderBookDTO> list = null;
		
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT ORDER_ID, BOOK_ID, BOOK_PRICE, BOOK_AMOUNT, ORDER_STATUS ");
			sql.append("FROM ORDER_BOOK ");
			sql.append("ORDER BY ORDER_ID ");
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<OrderBookDTO>();
			while (rs.next()) {
				OrderBookDTO obDTO = new OrderBookDTO(
						rs.getString("ORDER_ID"), 
						rs.getString("BOOK_ID"), 
						rs.getInt("BOOK_PRICE"),
						rs.getString("BOOK_AMOUNT"),
						rs.getString("ORDER_STATUS"));
				list.add(obDTO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	// join테이블 데이터로 DTO에 ORDER_BOOK외의 변수를 추가하여 받는다.
	public List<OrderBookDTO> selectOrderList(String mId, String oId) {
		List<OrderBookDTO> list = null;
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT B.BOOK_TITLE, OB.BOOK_AMOUNT, B.SALE_RPICE, O.ORDER_PRICE, OB.ORDER_STATUS ");
			sql.append("  FROM ORDER_BOOK OB, ORDERS O, BOOK B ");
			sql.append("  WHERE O.ORDER_ID = OB.ORDER_ID AND B.BOOK_ID = OB.BOOK_ID ");
			sql.append("  AND O.MEMBER_ID = ? AND O.ORDER_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, mId);
			pstmt.setString(2, oId);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<OrderBookDTO>();
			while (rs.next()) {
				OrderBookDTO obDTO = new OrderBookDTO(
						rs.getString("BOOK_TITLE"),
						rs.getString("BOOK_AMOUNT"),
						rs.getInt("SALE_RPICE"), 
						rs.getInt("ORDER_PRICE"),		
						rs.getString("ORDER_STATUS"));
				list.add(obDTO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	

}
