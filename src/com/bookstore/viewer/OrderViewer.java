package com.bookstore.viewer;

import java.util.List;
import java.util.Scanner;

import com.bookstore.controller.OrderController;
import com.bookstore.model.OrderDTO;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.ScannerUtil;

public class OrderViewer {
	private OrderBookViewer orderBookViewer;
	private OrderController orderController;
	private MemberDTO logIn;
	private Scanner scanner;
	
	public OrderViewer() {
		orderBookViewer= new OrderBookViewer();
		orderController = new OrderController();
		scanner = new Scanner(System.in);
	}
	
	public OrderViewer(MemberDTO logIn) {
		this.logIn = logIn;
		orderBookViewer= new OrderBookViewer();
		orderController = new OrderController();
		scanner = new Scanner(System.in);
	}
	
	public void printAll() {
		while(logIn != null) {
			List<OrderDTO> orderList = orderController.selectOrder(logIn.getMemberId());
			
			printList(orderList);
			
			String msg;
			msg = new String("1.주문내역 확인 2.주문내역 선택취소 3.주문내역 전체취소 0. 뒤로가기");
			int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 3);
			
			if (userChoice == 1) {
				checkOrder(orderList, logIn.getMemberId());
			}else if(userChoice == 2) {
				selectDelete(orderList);
			}else if(userChoice == 3) {
				delete(orderList);
			}else if(userChoice == 0) {
				return;
			}
		}
	}
	
	public void printList(List<OrderDTO> list) {
		int index = 1;
		System.out.println("================================================");
        System.out.println(" 순차\t\t주문금액\t\t주문날짜");
        System.out.println("------------------------------------------------");
        if (list.isEmpty()) {
            System.out.println("\t표시할 주문내역이 없습니다.");
        } else {
            for (OrderDTO oDTO : list) {
                System.out.printf("  %d\t\t%s\t\t%s\n", index, oDTO.getOrderPrice(), oDTO.getOrderDate());
                index++;
            }
        }
        System.out.println("================================================");
    }
	
	public void checkOrder(List<OrderDTO> list, String mId) {
		if(list.isEmpty() == true) {
			System.out.println("주문내역이 없습니다.");
			return;
		}else {
			String msg;
			orderBookViewer = new OrderBookViewer(logIn);
			
			msg = new String("확인하고자 원하는 주문내역의 번호를 입력해주세요.");
			int selectIndex = ScannerUtil.nextInt(scanner, msg);
			String selectOid = list.get(selectIndex-1).getoId();
			
			orderBookViewer.printAll(selectOid);
		}
		
	}
	
	public void delete(List<OrderDTO> list) {
		if(list.isEmpty() == true) {
			System.out.println("주문내역이 없습니다.");
			return;
		}else {
			String msg = new String("정말로 구매목록 전체를 삭제하시겠습니까? (y/n)");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
            	orderController.deleteOrderBook(logIn.getMemberId());
    			orderController.delete(logIn.getMemberId());
            	return;
            } else {
            	return;
            }
		}
	}
	
	public void selectDelete(List<OrderDTO> list) {
		if(list.isEmpty() == true) {
			System.out.println("주문내역이 없습니다.");
			return;
		}else {
			String msg;
			msg = new String("삭제할 구매목록의 순차를 입력해주세요.");
			int selectIndex = ScannerUtil.nextInt(scanner, msg);
			
			msg = new String("정말로 해당 구매목록을 삭제하시겠습니까? (y/n)");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
            	orderController.selectDeleteOrderBook(logIn.getMemberId(), list.get(selectIndex-1).getoId());
    			orderController.selectDelete(logIn.getMemberId(), list.get(selectIndex-1).getoId());
            	return;
            } else {
            	return;
            }
			
		}
		
	}
	
	
}
