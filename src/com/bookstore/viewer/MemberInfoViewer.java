package com.bookstore.viewer;

import java.util.Scanner;

import com.bookstore.controller.MemberInfoController;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.ScannerUtil;

public class MemberInfoViewer {
	private MemberInfoController memberInfoController;
	private Scanner scanner;
	private MemberDTO logIn;
	private final String LINE = "=============================",
						 LINE2 = "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■";

	public MemberInfoViewer() {
		memberInfoController = new MemberInfoController();
		scanner = new Scanner(System.in);
	}
	
	public MemberInfoViewer(MemberDTO logIn) {
		memberInfoController = new MemberInfoController();
		this.logIn = logIn;
		scanner = new Scanner(System.in);
	}
	
	public MemberDTO getLogIn() {
		return logIn;
	}

	public MemberDTO printOne() {
		while (logIn != null){
			String msg;
			MemberDTO m = memberInfoController.selectOne(logIn.getMemberId());
			String role = "구분 불가";
			System.out.println("--[회원정보]--");	
			System.out.println("ID: " + m.getLoginId());
			System.out.println("이름: " + m.getName());
			System.out.println("주소: " + m.getAddress());
			System.out.println("전화번호: " + m.getPhone());
			System.out.println("이메일: " + m.getEmail());
			if (m.getRole().equalsIgnoreCase("1")) {
				role = "일반회원";
			} else if (m.getRole().equalsIgnoreCase("0")) {
				role = "관리자";
			}
			System.out.println("회원구분: " + role);
			System.out.println(LINE);
			msg = "1. 비밀번호 변경 \t2. 주소 변경 \n3. 전화번호 변경 \t4. 이메일 변경\n5. 회원탈퇴 \t0. 뒤로가기";
			int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 5);
			if (userChoice == 1) {
				changePassword(m.getLoginId());
			} else if (userChoice == 2) {
				changeInformation(m.getMemberId(), "address");
			} else if (userChoice == 3) {
				changeInformation(m.getMemberId(), "phone");
			} else if (userChoice == 4) {
				changeInformation(m.getMemberId(), "email");
			} else if (userChoice == 5) {
				boolean rs = deleteAccount(m.getMemberId());
				if (rs) {
					System.out.println("탈퇴가 완료되었습니다. 초기화면으로 이동합니다.");
					logIn=null;
					return logIn;
				}else {
					System.out.println("탈퇴 실패 또는 취소하였습니다.");
				}
			} else if (userChoice == 0) {
				return logIn;
			}

		}
		return logIn;
	}

	public void changePassword(String id) {
		String nPassword;
		String nPassword2;
		System.out.println("비밀번호 변경('X' 입력시 취소)");
		String msg = "현재 비밀번호를 입력해주세요.";
		String password = ScannerUtil.nextLine(scanner, msg);
		boolean val = memberInfoController.validation(id, password);
		if (password.equalsIgnoreCase("x")) {
			return;
		} else if (val == false) {
			System.out.println("비밀번호 확인 후 다시 시도해주세요.");
			System.out.println();
			return;
		}
		msg = "새로운 비밀번호를 입력해주세요.";
		nPassword = ScannerUtil.nextLine(scanner, msg);
		if (nPassword.equalsIgnoreCase("x")) return;
		msg = "새 비밀번호 다시 입력해주세요.";
		nPassword2 = ScannerUtil.nextLine(scanner, msg);
		if (nPassword2.equalsIgnoreCase("x")) {
			return;
		} else if (!nPassword.equals(nPassword2)) {
			System.out.println("새로 입력한 비밀번호가 다릅니다. 다시 시도해주세요.");
			System.out.println();
			return;
		}
		memberInfoController.updatePassword(id, nPassword2);
		System.out.println("비밀번호가 변경되었습니다.");
		return;
	}
	
	private void changeInformation(String memberId, String category) {
		MemberDTO temp = memberInfoController.selectOne(memberId);
		String ctg = category;
		String msg = null;
		String mes;
		boolean check = false;
		boolean val = false;
		if ("address".equalsIgnoreCase(ctg)) {
			msg = "주소";
		} else if ("email".equalsIgnoreCase(ctg)) {
			msg = "이메일 주소";
			check = true;
		} else if ("phone".equalsIgnoreCase(ctg)) {
			msg = "전화번호";
			check = true;
		}
		System.out.println(msg + " 변경('X' 입력시 취소)");
		mes = "변경할 " + msg + "를 입력해주세요.";
		String input = ScannerUtil.nextLine(scanner, mes);
		if(check) {
			val = memberInfoController.validationInfor(input, ctg);
		}
		if(val == true) {
			System.out.println("동일한 "+ msg +"가 존재합니다. 다시 시도해주세요.");
			return;
		}
		if (input.equalsIgnoreCase("x")) {
			System.out.println("변경을 취소하였습니다.");
			System.out.println();
			return;
		}
		memberInfoController.updateInformation(memberId, input, ctg);
		System.out.println(msg + "가 변경되었습니다.");
		return;
	}
	
	private boolean deleteAccount(String id) {
		boolean result = false;
		System.out.println("회원탈퇴('X' 입력시 취소)");
		String msg = "비밀번호를 입력해주세요.";
		String password = ScannerUtil.nextLine(scanner, msg);
		MemberDTO temp = memberInfoController.selectOne(id);
		boolean val = memberInfoController.validation(temp.getLoginId(), password);
		if("x".equalsIgnoreCase(password)) {
			return result;
		}else if(val == true){
			while(true) {
				msg = "정말로 탈퇴하시겠습니까?(Y/N)";
				String yn = ScannerUtil.nextLine(scanner, msg);
				if("y".equalsIgnoreCase(yn)) {
					result = memberInfoController.delete(id);
					return result;
				}else if("n".equalsIgnoreCase(yn)) {
					return result;
				}else {
					System.out.println("Y 또는 N을 다시 입력해주세요.");
				}
			}
		}
		return result;
	}
	
}
