package com.bookstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.CartDTO;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.CommonUtil;

public class CartController {
	private ArrayList<CartDTO> list;
	private int nextId;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public CartController() {
        list = new ArrayList<>();
        nextId = 101;
    }
	
	public List<CartDTO> selectAll() {
		List<CartDTO> list = null;
		
		
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT * ");
			sql.append("  FROM CART ");
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			//4. SQL문 실행 결과에 대한 처리
			list = new ArrayList<CartDTO>();
			while (rs.next()) {
				
				CartDTO cDto = new CartDTO(
						rs.getString("MEMBER_ID"), 
						rs.getString("BOOK_ID"), 
						rs.getString("BOOK_AMOUNT"),
						rs.getString("PRICE_SUM"));
				//리스트에 추가
				list.add(cDto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<CartDTO> selectAllById(String mId) {
		List<CartDTO> list = null;
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT * ");
			sql.append("  FROM CART WHERE MEMBER_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, mId);
			rs = pstmt.executeQuery();
			list = new ArrayList<CartDTO>();
			while (rs.next()) {
				CartDTO cDto = new CartDTO(
						rs.getString("MEMBER_ID"), 
						rs.getString("BOOK_ID"), 
						rs.getString("BOOK_AMOUNT"),
						rs.getString("PRICE_SUM"));
				list.add(cDto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}
    
	public CartDTO selectOne(String mId) {
		CartDTO cDto = null;
		
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT * ");
			sql.append("  FROM CART ");
			sql.append(" WHERE MEMBER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, mId);
			
			rs = pstmt.executeQuery();
			//4. SQL 실행 결과에 대한 처리
			if (rs.next()) {
				cDto = new CartDTO(
						rs.getString("MEMBER_ID"), 
						rs.getString("BOOK_ID"), 
						rs.getString("BOOK_AMOUNT"),
						rs.getString("PRICE_SUM"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		
		return cDto;
	}
	
	public CartDTO selectOne(CartDTO cDto) {
		return selectOne(cDto.getmId());
	}
	
	public int insert(CartDTO cDto) {
		int result = 0;
		
		try {
			
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO CART ");
			sql.append("       (MEMBER_ID, BOOK_ID, BOOK_AMOUNT, PRICE_SUM) ");
			sql.append("VALUES (?, ?, ?, ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, cDto.getmId());
			pstmt.setString(2, cDto.getbId());
			pstmt.setString(3, cDto.getBookAmount());
			pstmt.setString(4, cDto.getSum());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		
		return result;
	}
	
	public int update(CartDTO cDto) {
		int result = 0;

		try {
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CART ");
			sql.append("	SET MEMBER_ID = ?, BOOK_ID = ?, BOOK_AMOUNT = ?, PRICE_SUM = ? ");
			sql.append("WHERE ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, cDto.getmId());
			pstmt.setString(2, cDto.getbId());
			pstmt.setString(3, cDto.getBookAmount());
			pstmt.setString(4, cDto.getSum());
			pstmt.setString(5, cDto.getmId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public boolean updateAmount(CartDTO cDto, int Amount) {
		int result = 0;
		
		String temp = getBookPrice(cDto.getbId());
		int bPrice = Integer.parseInt(temp);
		
		int sum = bPrice * Amount;
		
		String sAmount = Integer.toString(Amount);
		try {
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CART ");
			sql.append("	SET BOOK_AMOUNT = ?, PRICE_SUM = ?");
			sql.append("WHERE MEMBER_ID = ? AND BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, sAmount);
			pstmt.setInt(2, sum);
			pstmt.setString(3, cDto.getmId());
			pstmt.setString(4, cDto.getbId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return true;
	}
	
	public int delete(String id) {
		int result = 0;
		
		try {
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM CART WHERE MEMBER_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public int deleteByBid(String oId) {
		int result = 0;
		
		try {
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM CART WHERE BOOK_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, oId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public int delete(CartDTO cDto) {
		return delete(cDto.getmId());
	}
	
	public String getBookPrice(String bId) {
		String result = "";
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT BOOK_PRICE ");
			sql.append("  FROM BOOK ");
			sql.append(" WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, bId);
			
			rs = pstmt.executeQuery();
			
			
			//4. SQL 실행 결과에 대한 처리
			if (rs.next()) {
				result = rs.getString("BOOK_PRICE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public String bookName(String bId) {
		String result = "";
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			sql.append("SELECT BOOK_TITLE ");
			sql.append("  FROM BOOK ");
			sql.append(" WHERE BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, bId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString("BOOK_TITLE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public void order(String mId, String oId, int tot) {
		try {
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append("ORDERS(ORDER_ID, MEMBER_ID, ORDER_PRICE, ORDER_DATE) ");
			sql.append("VALUES(?, ?, ?, TO_DATE(SYSDATE,'yyyy.mm.dd')) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, oId);
			pstmt.setString(2, mId);
			pstmt.setInt(3, tot);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
	}
	
	public void orderBook(String oId, CartDTO cDto ) {
		int result = 0;
		
		try {
			Connection conn = CommonUtil.getConnection();

			String temp = getBookPrice(cDto.getbId());
			int bPrice = Integer.parseInt(temp);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ORDER_BOOK");
			sql.append("			(ORDER_ID, BOOK_ID, BOOK_PRICE, BOOK_AMOUNT, ORDER_STATUS) ");
			sql.append("VALUES (?, ?, ?, ?, '상품준비중') ");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, oId);
			pstmt.setString(2, cDto.getbId());
			pstmt.setInt(3, bPrice);
			pstmt.setString(4, cDto.getBookAmount());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		
	}
	
	//orderId가 null값이 있는지 판단
	public String setOrderId() {
		String result = "";
		try {
			Connection conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			if (conn == null) return null;
			
			//null 체크
			sql.append("SELECT NVL2(MAX(ORDER_ID), 'N', 'Y') nCheck");
			sql.append(" FROM ORDERS");
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString("nCheck");
			}
			if(result.equalsIgnoreCase("y")) {
				result = "1";
			}else {
				sql.setLength(0);
				sql.append("SELECT MAX(ORDER_ID)+1 ");
				sql.append(" FROM ORDERS");
				pstmt = conn.prepareStatement(sql.toString());
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = rs.getString("MAX(ORDER_ID)+1");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public CartDTO bookShoppingBag(CartDTO cart) {
        int result = 0;
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO CART ");
        sql.append(" VALUES (?, ?, ?, ? )");
               
        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, cart.getmId());
            pstmt.setString(2, cart.getbId());
            pstmt.setString(3, cart.getBookAmount());
            pstmt.setString(4, cart.getSum());

            result = pstmt.executeUpdate();
            
            if(result == 1) {
                return cart;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt);
        }
        
        return null;
    }
	
	public CartDTO bookShoppingBagUpdate(CartDTO cart) {
        int result = 0;
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE CART ");
        sql.append(" SET BOOK_AMOUNT = ?, PRICE_SUM = ? WHERE BOOK_ID = ?");
               
        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, cart.getBookAmount());
            pstmt.setString(2, cart.getSum());
            pstmt.setString(3, cart.getbId());

            result = pstmt.executeUpdate();
            
            if(result == 1) {
                return cart;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt);
        }
        
        return null;
    }
	
	
}
