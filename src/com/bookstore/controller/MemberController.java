package com.bookstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.model.MemberDTO;
import com.bookstore.util.CommonUtil;

public class MemberController {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

   
    // 아이디 비밀번호 확인 기능 (MemberDTO)
    public MemberDTO chechkIdPassword(String loginId, String loginPassword) {
        
        MemberDTO temp = null;
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MEMBER_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS, LOGIN_ID, LOGIN_PASSWORD ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE LOGIN_ID = ? AND LOGIN_PASSWORD = ? ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, loginId);
            pstmt.setString(2, loginPassword);
            
            rs = pstmt.executeQuery();
                        
            while(rs.next()) {
                temp = new MemberDTO(
                rs.getString("MEMBER_ID"),
                rs.getString("NAME"),
                rs.getString("PHONE"),
                rs.getString("EMAIL"),
                rs.getString("ROLE"),
                rs.getString("ADDRESS"),
                rs.getString("LOGIN_ID"),
                rs.getString("LOGIN_PASSWORD"));                
            }
            return temp;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        return null;
    }
    

    // 회원가입 기능 
    public int memberRegister(MemberDTO vo) {
        int result = 0;
        
        conn = CommonUtil.getConnection();
        if(conn == null) return result;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO MEMBER ");
        sql.append(" (MEMBER_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS, LOGIN_ID, LOGIN_PASSWORD) ");
        sql.append(" VALUES((SELECT NVL(MAX(MEMBER_ID),0)+1 FROM MEMBER), ?, ?, ?, 1, ?, ?, ?) ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getPhone());
            pstmt.setString(3, vo.getEmail());
            pstmt.setString(4, vo.getAddress());
            pstmt.setString(5, vo.getLoginId());
            pstmt.setString(6, vo.getLoginPassword());

            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt);
        }
        
        return result;
    }
    
    // 회원가입 시 아이디 중복 확인 기능
    public boolean checkloginId(String loginId) {
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(LOGIN_ID) AS CNT ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE LOGIN_ID = ? ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, loginId);
            
            rs = pstmt.executeQuery();
                        
            if(rs.next()) {
                if(rs.getInt("CNT") == 0) {
                    return true;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return false;
    }
    
    // 회원가입 시 이메일 중복 확인 기능
    public boolean checkloginEmail(String userEamil) {
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(LOGIN_ID) AS CNT ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE EMAIL = ? ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, userEamil);
            
            rs = pstmt.executeQuery();
                        
            if(rs.next()) {
                if(rs.getInt("CNT") == 0) {
                    return true;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return false;
    }
    
    // 회원가입 시 전화번호 중복 확인 기능
    public boolean checkloginPhone(String userPhone) {
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(LOGIN_ID) AS CNT ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE PHONE = ? ");

        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, userPhone);
            
            rs = pstmt.executeQuery();
                        
            if(rs.next()) {
                if(rs.getInt("CNT") == 0) {
                    return true;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return false;
    }
    
    // 아이디 & 비밀번호 찾기 - (회원정보 입력)
    public MemberDTO findIdPassword(MemberDTO vo) {
        MemberDTO temp = null;
        
        conn = CommonUtil.getConnection();
        
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT MEMBER_ID, NAME, PHONE, EMAIL, ROLE, ADDRESS, LOGIN_ID, LOGIN_PASSWORD ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE NAME = ? ");
        sql.append(" AND EMAIL = ? ");
        sql.append(" AND PHONE = ? ");
        
        try {
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getEmail());
            pstmt.setString(3, vo.getPhone());

            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                temp = new MemberDTO(
                        rs.getString("MEMBER_ID"),
                        rs.getString("NAME"),
                        rs.getString("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getString("ROLE"),
                        rs.getString("ADDRESS"),
                        rs.getString("LOGIN_ID"),
                        rs.getString("LOGIN_PASSWORD"));    
            }
            return temp;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtil.close(conn, pstmt, rs);
        }
        
        return null;
    }
    
//    // 회원가입 시 SHPPING에 데이터 추가
//    public int memberRegisteShippingDataInsert(MemberDTO vo) {
//        int result = 0;
//        
//        conn = CommonUtil.getConnection();
//        if(conn == null) return result;
//
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO SHIPPING ");
//        sql.append(" (SHIPPING_ID, MEMBER_ID, NICKNAME, NAME, PHONE, ADDRESS) ");
//        sql.append(" VALUES((SELECT NVL(MAX(SHIPPING_ID),0)+1 FROM SHIPPING), ?, ?, ?, ?, ?) ");
//
//        try {
//            pstmt = conn.prepareStatement(sql.toString());
//            
//            pstmt.setString(1, vo.getMemberId());
//            pstmt.setString(2, "기본");
//            pstmt.setString(3, vo.getName());
//            pstmt.setString(4, vo.getPhone());
//            pstmt.setString(5, vo.getAddress());
//
//            result = pstmt.executeUpdate();
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            CommonUtil.close(conn, pstmt, rs);
//        }
//        
//        return result;
//    }
    
}
