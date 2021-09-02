package com.bookstore.viewer;

import java.util.Scanner;

import com.bookstore.controller.MemberController;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.ScannerUtil;

public class MemberViewer {
    private BookViewer bookViewer;
    private MemberController memberController;
    private Scanner scanner;
    private MemberDTO login;
    public MemberViewer() {
        bookViewer = new BookViewer();
        memberController = new MemberController();
        scanner = new Scanner(System.in);
        login = new MemberDTO();
    }
    
    // 회원 메뉴
    public void LoginMenu() {
        String msg = new String("1. 로그인 2. 회원가입 3. 아이디 & 비밀번호 찾기 4. 종료 ");
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, msg, 1, 4);
            if (userChoice == 1) {
                logIn();
            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                findIdPassword();
            } else if (userChoice == 4) {
                System.out.println("사용해주셔서 감사합니다.");
                scanner.close();
                break;
                } 
            }
        }

    // 로그인 메뉴 
    public String logIn() {        
        String msg = "";
        
        msg = new String("아이디 : ");
        String loginId = ScannerUtil.nextLine(scanner, msg);
        
        msg = new String("비밀번호 : ");
        String password = ScannerUtil.nextLine(scanner, msg);
        
        return validateLogin(loginId, password).getMemberId();
     
    }
    
    // 회원가입 메뉴
    public void register() {        
        MemberDTO vo = null;

        vo = validateRegister(profileInsert(validatePassword(validateId(vo))));
        if(vo == null) {
            LoginMenu();
        }
    }
    
    
    // 회원가입 시 아이디 중복 확인
    public MemberDTO validateId(MemberDTO vo) {
        vo = new MemberDTO();
        String msg = "";
        System.out.println("회원가입 (과정중 뒤로가기를 원하실 경우 X를 입력해주세요.)");

        msg = new String("아이디를 입력해주세요: ");
        String loginId = ScannerUtil.nextLine(scanner, msg);
        
        while (memberController.checkloginId(loginId) == false) {
            System.out.println("중복되는 아이디입니다 다른 아이디를 입력해주세요");
            msg = new String("아이디를 입력해주세요: ");
            loginId = ScannerUtil.nextLine(scanner, msg);
        }
        vo.setLoginId(loginId);
        return vo;
    }
    
    // 회원가입 시 비밀번호 중복 확인
    public MemberDTO validatePassword(MemberDTO vo) {
        String msg = "";
        msg = new String("비밀번호를 입력해주세요: ");
        String password = ScannerUtil.nextLine(scanner, msg);
        
        msg = new String("비밀번호를 다시 한번 입력해주세요: ");
        String passwordAgain = ScannerUtil.nextLine(scanner, msg);
        
        while(!(password.equals(passwordAgain))) {
            System.out.println("위에 비밀번호와 일치하지 않습니다. 다시 입력해주세요");
            msg = new String("비밀번호를 다시 한번 입력해주세요. (초기메뉴로 돌아가려면 X를 눌러주세요.))");
            
            passwordAgain = ScannerUtil.nextLine(scanner, msg);
            
            if (passwordAgain.equalsIgnoreCase("x")) {
                LoginMenu();
            }     
        }
        vo.setLoginPassword(password);
        return vo;
        
    }
    
    
    // 로그인 시 아이디, 비밀번호 확인
    public MemberDTO validateLogin(String loginId, String password) {
        String msg = "";
        MemberDTO vo = memberController.chechkIdPassword(loginId, password);
        while (vo == null) {
            msg = "";
            System.out.println("아이디나 비밀번호가 틀렸습니다. 다시 입력해주세요.");
            System.out.println(" X를 입력하면 초기메뉴로 돌아갑니다 ");
            
            msg = new String("아이디 : ");
            loginId = ScannerUtil.nextLine(scanner, msg);
            
            if (loginId.equalsIgnoreCase("x")) {
                break;
            }  
    
            msg = new String("비밀번호 : ");
            password = ScannerUtil.nextLine(scanner, msg);
            
            if (password.equalsIgnoreCase("x")) {
                break;
            }  
            vo = memberController.chechkIdPassword(loginId, password);
        }

        System.out.println("********** 로그인 성공 **********");
        bookViewer.bookMenu(vo);
        return vo;
        
    }
    
    // 회원가입 확인 
    public MemberDTO validateRegister(MemberDTO vo) {
    
        if(memberController.memberRegister(vo) == 1) {
            // 회원가입 시 SHPPING에 데이터 추가
//            memberController.memberRegisteShippingDataInsert(vo);
            
            System.out.println("회원가입이 완료되었습니다.");
        } else {
            System.out.println("회원가입이 정상적으로 완료되지 않았습니다.");
        }
        return vo;
    }
    
    // x 누르면 전 단계로 가기 (초기메뉴로 가기) <- 사용x
    public boolean breakStatement(String temp) {
        if (temp.equalsIgnoreCase("x")) {
            return false;
        }  
        return true;
    }
    
    // 아이디 혹은 비밀번호 찾기 (버전 1. -- 아이디/비밀번호 공개)
    public void findIdPassword() {
        MemberDTO vo = new MemberDTO(); 
        System.out.println("회원가입 시 입력한 개인정보를 정확히 입력해주세요.");
        
        essentialProfileIdPwInsert(vo);
        
        MemberDTO LoginProfile = validateProfile(vo);
        
        System.out.println("아이디 & 비밀번호찾기 성공하였습니다! 다시 로그인 해주세요.");
        System.out.println("아이디 : " + LoginProfile.getLoginId());
        System.out.println("비밀번호 : " + LoginProfile.getLoginPassword());
        
    }
    
    // 아이디 & 비밀번호 찾기 시 회원정보 (이름, 이메일, 전화번호) 확인 (버전 1. -- 아이디/비밀번호 공개)
    public MemberDTO validateProfile(MemberDTO vo) {
        MemberDTO temp = memberController.findIdPassword(vo);
        while(temp == null) {
            System.out.println("입력한 내용이 회원가입 시 작성한 정보와 일치하지 않습니다.");
            System.out.println("다시 한번 입력해주세요. (X를 입력하면 초기메뉴로 돌아갑니다.)");
            
            essentialProfileInsert(vo);
        
            temp = memberController.findIdPassword(vo);
        }
        
        return temp;
    }
    
    // 개인정보 입력 버전 1. (중복 방지) - 회원가입 
    public MemberDTO profileInsert(MemberDTO vo) {
        String msg;
        essentialProfileInsert(vo);
        
        msg = new String("주소를 입력해주세요.");
        String userAddress = ScannerUtil.nextLine(scanner, msg);
        vo.setAddress(userAddress);
        return vo;
    }
    
    
    // 개인정보 입력 버전 2. - 회원가입용으로 변경 
    public void essentialProfileInsert(MemberDTO vo) {
        String msg;
        msg = new String("이름을 입력해주세요.");
        String userName = ScannerUtil.nextLine(scanner, msg);
        vo.setName(userName);
        
        msg = new String("이메일을 입력해주세요.");
        String userEamil = ScannerUtil.nextLine(scanner, msg);
        boolean checkResult = memberController.checkloginEmail(userEamil);
        
        while(checkResult == false) {
            msg = new String("중복되는 이메일이 있습니다. 다시 한번 입력해주세요.");
            userEamil = ScannerUtil.nextLine(scanner, msg);
            checkResult = memberController.checkloginEmail(userEamil);
        }
        vo.setEmail(userEamil);
        
        msg = new String("전화번호를 입력해주세요.");
        String userPhone = ScannerUtil.nextLine(scanner, msg);
        checkResult = memberController.checkloginPhone(userPhone);
        
        while(checkResult == false) {
            msg = new String("중복되는 전화번호가 있습니다. 다시 한번 입력해주세요.");
            userPhone = ScannerUtil.nextLine(scanner, msg);
            checkResult = memberController.checkloginPhone(userPhone);
        }
        vo.setPhone(userPhone);        
    }
    
    // 개인정보 입력 버전 3. - 아이디 비밀번호 찾기 용
    public void essentialProfileIdPwInsert(MemberDTO vo) {
        String msg;
        msg = new String("이름을 입력해주세요.");
        String userName = ScannerUtil.nextLine(scanner, msg);
        vo.setName(userName);
        
        msg = new String("이메일을 입력해주세요.");
        String userEamil = ScannerUtil.nextLine(scanner, msg);        
        vo.setEmail(userEamil);
        
        msg = new String("전화번호를 입력해주세요.");
        String userPhone = ScannerUtil.nextLine(scanner, msg);
        vo.setPhone(userPhone);        
    }

}
