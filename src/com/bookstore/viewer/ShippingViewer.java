package com.bookstore.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bookstore.controller.ShippingController;
import com.bookstore.model.MemberDTO;
import com.bookstore.model.ShippingDTO;
import com.bookstore.util.ScannerUtil;

public class ShippingViewer {
	private ShippingController shippingController;
	private Scanner scanner;
	private MemberDTO logIn;
	private final String LINE = "==========================================================", 
						 LINE2 = "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■";
						 
	private final int nicknameMax = 5,
					  nameMax = 5,
					  phoneMax = 13;

	public ShippingViewer() {
		shippingController = new ShippingController();
		scanner = new Scanner(System.in);
	}

	public ShippingViewer(MemberDTO logIn) {
		shippingController = new ShippingController();
		this.logIn = logIn;
		scanner = new Scanner(System.in);
	}

	public List<ShippingDTO> printOne() {
		List<ShippingDTO> list = new ArrayList<ShippingDTO>();
		System.out.println("--[배송지 관리]--");
		System.out.println("번호\t배송지명\t이름\t전화번호\t\t주소");
		System.out.println(LINE);
		list = shippingController.selectOne(logIn.getMemberId());
		if (list.size() == 0) {
			System.out.println("등록된 배송지 정보가 없습니다.");
		}
		int idx = 1;
		for (ShippingDTO temp : list) {
			System.out.println("  " + idx++ + "\t" + temp.getNickname() + "\t" + temp.getName() 
								+ "\t" + String.format("%-13s", temp.getPhone()) + "\t" + temp.getAddress());
		}

		System.out.println(LINE);
		return list;
	}

	public void choice() {
		while (true) {
			String msg;
			int uc;
			List<ShippingDTO> list = printOne();
			if (list.size() >= 3) {
				msg = "1. 배송지 수정 2. 배송지 삭제 0. 뒤로가기";
				uc = ScannerUtil.nextInt(scanner, msg, 0, 2);
				if (uc == 1) {
					update();
				} else if (uc == 2) {
					delete();
				} else {
					return;
				}
			} else if(list.size() == 0) {
				msg = "1. 배송지 추가(최대 3개) 0. 뒤로가기";
				uc = ScannerUtil.nextInt(scanner, msg, 0, 1);
				if (uc == 1) {
					insert();
				} else {
					return;
				}
				
			} else	{
				msg = "1. 배송지 추가(최대 3개) 2. 배송지 수정 3. 배송지 삭제 0. 뒤로가기";
				uc = ScannerUtil.nextInt(scanner, msg, 0, 3);
				if (uc == 1) {
					insert();
				} else if (uc == 2) {
					update();
				} else if (uc == 3) {
					delete();
				} else {
					return;
				}
			}
		}

	}

	public void insert() {
		ShippingDTO temp = new ShippingDTO();
		String input = null;
		boolean exit = false;

		System.out.println("배송지 입력(취소: X)");

		temp.setMember_id(logIn.getMemberId());
		temp.setShipping_id("0");

		String msg = "배송지명(" + nicknameMax + "자 이내)을 입력해주세요(취소: X)";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		while(input.length() > nicknameMax) {
			System.out.println("별칭은" + nicknameMax + "자 이내로 지정하셔야 합니다. 다시 입력해주세요.");
			input = ScannerUtil.nextLine(scanner, msg);
			if ("x".equalsIgnoreCase(input)) return;
		}
		temp.setNickname(input);

		msg = "받는 사람 이름(" + nameMax + "자 이내)을 입력해주세요(취소: X)";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		while(input.length() > nameMax) {
			System.out.println("이름은 " + nameMax+ "자 이내로 지정하셔야 합니다. 다시 입력해주세요.");
			input = ScannerUtil.nextLine(scanner, msg);
			if ("x".equalsIgnoreCase(input)) {
				exit = true;
				return;
			}
		}
		if(exit == true) {
			return;
		}
		temp.setName(input);

		msg = "받는 사람 전화번호('-' 포함 13자 이내)를 입력해주세요.(취소: X)";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		while(input.length() > phoneMax) {
			System.out.println("전화번호는 " + phoneMax + "자 이내로 지정하셔야 합니다. 다시 입력해주세요.");
			input = ScannerUtil.nextLine(scanner, msg);
			if ("x".equalsIgnoreCase(input)) {
				exit = true;
				return;
			}
		}
		if(exit == true) {
			return;
		}
		temp.setPhone(input);

		msg = "받는 사람 주소를 입력해주세요";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		temp.setAddress(input);

		int result = shippingController.insert(temp);
		if (result != 0) {
			System.out.println("새로운 배송지가 추가되었습니다.");
		} else {
			System.out.println("배송지 등록에 실패하셨습니다.");
		}
	}

	public void delete() {
		String msg = null;
		String result = null;
		msg = new String("삭제할 배송지의 번호를 입력해주세요.");
		List<ShippingDTO> list = printOne();
		int uc = ScannerUtil.nextInt(scanner, msg, 0, list.size());
		if (uc == 1) {
			result = list.get(0).getShipping_id();
		} else if (uc == 2) {
			result = list.get(1).getShipping_id();
		} else if (uc == 3) {
			result = list.get(2).getShipping_id();
		}

		msg = new String("정말로 삭제하시겠습니까?(Y/N)");
		String yn = ScannerUtil.nextLine(scanner, msg);
		if("y".equalsIgnoreCase(yn)) {
			int rs = shippingController.delete(logIn.getMemberId(), result);
			if (rs != 0) {
				System.out.println("삭제가 완료되었습니다.");
			} else {
				System.out.println("삭제에 실패하였습니다.");
			}
		}else if("n".equalsIgnoreCase(yn)) {
			System.out.println("취소하였습니다.");
		}
		
		
		
	}

	public void update() {
		ShippingDTO temp = new ShippingDTO();
		String result = null;
		String input = null;
		boolean exit = false;
		System.out.println("배송지 수정(취소: 0)");
		String msg = new String("수정할 배송지의 번호를 입력해주세요.");
		List<ShippingDTO> list = printOne();
		int uc = ScannerUtil.nextInt(scanner, msg, 1, list.size());
		if (uc == 1) {
			result = list.get(0).getShipping_id();
		} else if (uc == 2) {
			result = list.get(1).getShipping_id();
		} else if (uc == 3) {
			result = list.get(2).getShipping_id();
		}
		temp.setMember_id(logIn.getMemberId());
		temp.setShipping_id(result);

		msg = "배송지명(" + nicknameMax + "자 이내)을 입력해주세요(취소: X)";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		while(input.length() > nicknameMax) {
			System.out.println("별칭은" + nicknameMax + "자 이내로 지정하셔야 합니다. 다시 입력해주세요.");
			input = ScannerUtil.nextLine(scanner, msg);
			if ("x".equalsIgnoreCase(input)) return;
		}
		temp.setNickname(input);

		msg = "받는 사람 이름(" + nameMax + "자 이내)을 입력해주세요(취소: X)";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		while(input.length() > nameMax) {
			System.out.println("이름은 " + nameMax+ "자 이내로 지정하셔야 합니다. 다시 입력해주세요.");
			input = ScannerUtil.nextLine(scanner, msg);
			if ("x".equalsIgnoreCase(input)) {
				exit = true;
				return;
			}
		}
		if(exit == true) {
			return;
		}
		temp.setName(input);

		msg = "받는 사람 전화번호('-' 포함 13자 이내)를 입력해주세요(취소: X)";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input)) return;
		while(input.length() > phoneMax) {
			System.out.println("전화번호는 " + phoneMax + "자 이내로 지정하셔야 합니다. 다시 입력해주세요.");
			input = ScannerUtil.nextLine(scanner, msg);
			if ("x".equalsIgnoreCase(input)) {
				exit = true;
				return;
			}
		}
		if(exit == true) {
			return;
		}
		temp.setPhone(input);

		msg = "변경할 주소를 입력해주세요.";
		input = ScannerUtil.nextLine(scanner, msg);
		if ("x".equalsIgnoreCase(input))
			return;
		temp.setAddress(input);

		int rs = shippingController.update(temp, result);
		if (rs != 0) {
			System.out.println("수정이 완료되었습니다.");
		} else {
			System.out.println("수정이 실패하였습니다.");
		}

	}
}
