package com.bookstore.controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.BookDTO;
import com.bookstore.model.CartDTO;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.CommonUtil;

public class BookController {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
 
    // 간단한 도서 리스트 모두 불러오기 (List<BookDTO> 리턴)
    public List<BookDTO> bookListAll(){
        BookDTO vo = null;
        List<BookDTO> list = new ArrayList<BookDTO>();
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BOOK_ID, BOOK_TITLE, WRITER, PUBLISHER, SALE_RPICE ");
        sql.append(" FROM BOOK ");
        
        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                vo = new BookDTO(
                rs.getString("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("WRITER"),
                rs.getString("PUBLISHER"),
                rs.getInt("SALE_RPICE"));
                list.add(vo);
            }
            return list;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return null;
    }
    // 책 번호로 검색하기 (전체도서목록에서 상세보기할 책 선택)
    public BookDTO bookIdSelect(String bookId) {
        BookDTO vo = null;
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BOOK_ID, BOOK_TITLE, SUBTITLE, WRITER, TRANSLATOR, PUBLISHER, ");
        sql.append("       TO_CHAR(RELEASE_DATE, '\"\"YYYY\"년 \"MM\"월 \"DD\"일\"') BOOK_DATE, ");
        sql.append("       BOOK_PRICE, SALE_RPICE, BOOK_SALE_RATE ");
        sql.append(" FROM BOOK ");
        sql.append(" WHERE BOOK_ID = ? ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, bookId);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                vo = new BookDTO(
                rs.getString("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("SUBTITLE"),
                rs.getString("WRITER"),
                rs.getString("TRANSLATOR"),
                rs.getString("PUBLISHER"),
                rs.getString("BOOK_DATE"),
                rs.getInt("BOOK_PRICE"),
                rs.getInt("SALE_RPICE"),
                rs.getInt("BOOK_SALE_RATE"));
            }
            return vo;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        return null;
    }
    
    
    // 책 이름으로 검색하기 (BookDTO 리턴) - 
    public List<BookDTO> bookNameSelect(String bookName) {
        List<BookDTO> list = new ArrayList<BookDTO>();
        BookDTO vo = null;
        
        conn = CommonUtil.getConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BOOK_ID, BOOK_TITLE, WRITER, PUBLISHER, SALE_RPICE  ");
        sql.append(" FROM BOOK ");
        sql.append(" WHERE BOOK_TITLE LIKE '%' || ? || '%'");
        
        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, bookName);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                vo = new BookDTO(
                rs.getString("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("WRITER"),
                rs.getString("PUBLISHER"),
                rs.getInt("SALE_RPICE"));
                list.add(vo);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return null;
    }
    
    // 책 저자 이름으로 검색하기 (BookDTO 리턴) 
    public List<BookDTO> bookWriterSelect(String BookWriter) {
        List<BookDTO> list = new ArrayList<BookDTO>();
        BookDTO vo = null;
        
        conn = CommonUtil.getConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BOOK_ID, BOOK_TITLE, WRITER, PUBLISHER, SALE_RPICE  ");
        sql.append(" FROM BOOK ");
        sql.append(" WHERE WRITER LIKE '%' || ? || '%'");
        
        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, BookWriter);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                vo = new BookDTO(
                rs.getString("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("WRITER"),
                rs.getString("PUBLISHER"),
                rs.getInt("SALE_RPICE"));
                list.add(vo);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return null;
    }
    
    // 책 출판사 이름으로 검색하기 (BookDTO 리턴)
    public List<BookDTO> bookPublisherSelect(String BookPublisher) {
        List<BookDTO> list = new ArrayList<BookDTO>();
        BookDTO vo = null;
        
        conn = CommonUtil.getConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BOOK_ID, BOOK_TITLE, WRITER, PUBLISHER, SALE_RPICE  ");
        sql.append(" FROM BOOK ");
        sql.append(" WHERE PUBLISHER LIKE '%' || ? || '%'");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, BookPublisher);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                vo = new BookDTO(
                rs.getString("BOOK_ID"),
                rs.getString("BOOK_TITLE"),
                rs.getString("WRITER"),
                rs.getString("PUBLISHER"),
                rs.getInt("SALE_RPICE"));
                list.add(vo);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return null;
    }   
    //***************************************************구매내역 수정사항
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
    
    public void order(String mId, String oId, int tot) {
		try {
			Connection conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append("ORDERS(ORDER_ID, MEMBER_ID, ORDER_PRICE, ORDER_DATE) ");
			sql.append("VALUES(?, ?, ?, TO_DATE(SYSDATE,'YY-MM-DD')) ");
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
	
	public int orderBook(String oId, CartDTO cDto ) {
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
			int outcome = pstmt.executeUpdate();
			
			if(outcome != 0) result = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
    /*
    // 선택 책 바로 구매하기 (order 데이터 추가)
    public void bookOrder(CartDTO cart) {
        try {
            Connection conn = CommonUtil.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ORDERS ");
            sql.append(" (MEMBER_ID, ORDER_ID, ORDER_PRICE, ORDER_DATE) ");
            sql.append(" VALUES(?,(SELECT NVL(MAX(ORDER_ID),0)+1 FROM ORDERS), ?, TO_DATE(SYSDATE,'yyyy.mm.dd')) ");
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, cart.getmId());
            pstmt.setString(2, cart.getSum());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt);
        }
    }
    */
    
    // 선택 책 바로 구매하기 (shipping 데이터 추가)
    public int bookShippingInsert(CartDTO cart) {
        int result = 0;
        MemberDTO dto = bookNameSelectOne(cart.getmId());
        conn = CommonUtil.getConnection();
       
        
        
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO SHIPPING ");
        sql.append("       (SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS) ");
        sql.append("VALUES ((SELECT NVL(MAX(SHIPPING_ID),0)+1 FROM SHIPPING), ?,'기본', ?, ?, ?) ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, cart.getmId());
            pstmt.setString(2, dto.getName());
            pstmt.setString(3, dto.getPhone());
            pstmt.setString(4, dto.getAddress());
            int outcome = pstmt.executeUpdate();
            
            if(outcome != 0) result = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt);
        }
        return result;
    }

    // 멤버ID로 멤버 데이터베이스에서 개인정보 데이터 가져오기 
    public MemberDTO bookNameSelectOne(String MemberId) {
        MemberDTO user = null;
        conn = CommonUtil.getConnection();
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT MEMBER_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS, LOGIN_ID, LOGIN_PASSWORD ");
            sql.append("FROM MEMBER  ");
            sql.append("WHERE MEMBER_ID = ? ");
            sql.append("ORDER BY MEMBER_ID ");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, MemberId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                user = new MemberDTO(
                        rs.getString("MEMBER_ID"),
                        rs.getString("NAME"),
                        rs.getString("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getString("ROLE"),
                        rs.getString("ADDRESS"),
                        rs.getString("LOGIN_ID"),
                        rs.getString("LOGIN_PASSWORD"));    
            }
            return user;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        return null;
    }

}