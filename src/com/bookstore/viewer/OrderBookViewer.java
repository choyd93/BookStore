package com.bookstore.viewer;

import java.util.List;
import java.util.Scanner;

import com.bookstore.controller.OrderBookController;
import com.bookstore.model.MemberDTO;
import com.bookstore.model.OrderBookDTO;
import com.bookstore.util.ScannerUtil;

public class OrderBookViewer {
	private OrderBookController orderBookController;
	private MemberDTO logIn;
	private Scanner scanner;
	
	public OrderBookViewer() {
		orderBookController = new OrderBookController();
		scanner = new Scanner(System.in);
	}
	
	public OrderBookViewer(MemberDTO logIn) {
		this.logIn = logIn;
		orderBookController = new OrderBookController();
		scanner = new Scanner(System.in);
	}
	
	public void printAll(String selectOid) {
		while(logIn != null) {
			List<OrderBookDTO>obList = orderBookController.selectOrderList(logIn.getMemberId(), selectOid);
			
			printList(obList);
			
			String msg;
			msg = new String("0. 뒤로가기");
			int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 3);
			
			// 결제시스템상 한번에 결제되는 물품이기에 따로 취소하는 기능은 적합하지 않음.
			if (userChoice == 1) {
				//update(obList);
			}else if(userChoice == 2) {
				//delete(obList);
			}else if(userChoice == 3) {
				//deleteAll(obList);
			}else if(userChoice == 0) {
				return;
			}
		}
	}
	
	public void printList(List<OrderBookDTO> list) {
		int index = 1;
		System.out.println("=========================================================================================================================================================");
		System.out.println("| 순  차\t|\t\t\t책제목\t\t\t|\t수량\t|\t가격(권)\t|\t총금액\t|\t주문상태\t\t|");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(OrderBookDTO obDTO : list) {
			String bName = obDTO.getBookTitle();
			int bPrice = obDTO.getSalePrice();
			String bAmount = obDTO.getbAmount();
			String status = obDTO.getoStatus();
			int temp = Integer.parseInt(bAmount);
			int tot = bPrice * temp;
			System.out.printf("   %d\t\t\t\t%s\t\t\t\t%s\t\t%d\t\t%d\t\t%s\n", index, bName, bAmount, bPrice, tot, status);
			index++;
		}
		System.out.println("==========================================================================================================================================================");
	}
	
}
