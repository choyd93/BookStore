package com.bookstore.viewer;

import java.util.List;
import java.util.Scanner;

import com.bookstore.controller.CartController;
import com.bookstore.model.CartDTO;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.ScannerUtil;

public class CartViewer {
	private CartController cartController;
	private MemberDTO logIn;
	private Scanner scanner;
	private OrderViewer orderViewer;
	private boolean exit3;
	public CartViewer() {
		cartController = new CartController();
		scanner = new Scanner(System.in);
	}
	
	public CartViewer(MemberDTO logIn) {
		this.logIn = logIn;
		cartController = new CartController();
		scanner = new Scanner(System.in);
    }
	
	public boolean printAll() {
		while(true) {
			orderViewer = new OrderViewer(logIn);
			List<CartDTO> cartList = cartController.selectAllById(logIn.getMemberId());
			
			printList(cartList);
			
			String msg;
			msg = new String("1.수량 변경 2.장바구니 목록 선택삭제 3.장바구니 목록 전체삭제 4.장바구니 목록 주문하기 5.주문목록 확인하기 0. 뒤로가기");
			int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 5);
			
			if (userChoice == 1) {
				update(cartList);
			}else if(userChoice == 2) {
				selectDelte(cartList);
			}else if(userChoice == 3) {
				delete(cartList);
			}else if(userChoice == 4) {
				order(logIn.getMemberId(), cartList);
			}else if(userChoice == 5) {
				orderViewer.printAll();
			}else if(userChoice == 0) {
				exit3 = true;
				return exit3;
			}
		}
	}
	
	public void printList(List<CartDTO> list) {
		int index = 1;
		System.out.println("=================================================================================================================");
        if (list.isEmpty()) {
            System.out.println("\t\t\t등록된 장바구니가 없습니다.");
        } else {
        	System.out.println("| 순 차 |\t\t\t책제목\t\t\t|\t수량\t|\t가 격(권당)\t|\t합  계\t\t|");
        	System.out.println("-----------------------------------------------------------------------------------------------------------------");
            for (CartDTO cDto : list) {
            	String bTitle = cartController.bookName(cDto.getbId());
            	String bAmount = cDto.getBookAmount();
            	String bPrice = cartController.getBookPrice(cDto.getbId());
            	String tot = cDto.getSum();
                //System.out.printf("|  %d\t\t\t%s\t\t\t\t%s\t\t%s\t\t\t%s\t|\n", index, cartController.bookName(cDto.getbId()) ,cDto.getBookAmount(), cartController.getBookPrice(cDto.getbId()),cDto.getSum());
            	System.out.printf("|%3d\t%-35s\t\t%s\t%10s\t\t%13s\t\n", index, bTitle,bAmount, bPrice,tot);
            	
            	index++;
            }
        }
        System.out.println("=================================================================================================================");
    }
	
	// 구현변경
	public void selectDelte(List<CartDTO> list) {
		if(list.isEmpty() == true) {
			System.out.println("장바구니가 비어있습니다. 확인해주세요.");
			return;
		}else {
			String msg;
			msg = new String("어떤 도서를 삭제할지 순차를 입력해주세요.");
			int selectIndex = ScannerUtil.nextInt(scanner, msg);
			
			msg = "\"" + cartController.bookName(list.get(selectIndex-1).getbId()) + "\"을 장바구니에서 삭제하시겠습니까? (y/n)";
			String yesNo = ScannerUtil.nextLine(scanner, msg);
	        if (yesNo.equalsIgnoreCase("y")) {
	        	cartController.deleteByBid(list.get(selectIndex-1).getbId());
	        	return;
	        } else {
	        	return;
	        }
		}
		
	}
	
	public void delete(List<CartDTO> list) {
		if(list.isEmpty() == true) {
			System.out.println("장바구니가 비어있습니다. 확인해주세요.");
			return;
		}else {
			String msg = new String("정말로 장바구니 목록 전체를 삭제하시겠습니까? (y/n)");
	        String yesNo = ScannerUtil.nextLine(scanner, msg);
	        if (yesNo.equalsIgnoreCase("y")) {
	        	cartController.delete(logIn.getMemberId());
	        	return;
	        } else {
	        	return;
	        }
		}
		
	}
	
	public void update(List<CartDTO> list) {
		if(list.isEmpty() == true) {
			System.out.println("장바구니가 비어있습니다. 확인해주세요.");
			return;
		}else {
			String msg;
			msg = new String("어떤 도서의 수량을 변경합니다. 순차를 입력해주세요:");
			int selectIndex = ScannerUtil.nextInt(scanner, msg);
			
			msg = "\"" + cartController.bookName(list.get(selectIndex-1).getbId()) + "\"의 수량을 몇으로 변경하시겠습니까?";
			int selectAmount = ScannerUtil.nextInt(scanner, msg);
			
			if(cartController.updateAmount(list.get(selectIndex-1), selectAmount) == true) {
				return;
			}
		}
		
	}
	
	public void order(String mId, List<CartDTO> list) {
        if (list.isEmpty()) {
        	System.out.println("장바구니가 비어있습니다.");
        	return;
        }else {
        	String msg = new String("정말로 장바구니 목록 전체를 구매하시겠습니까? (y/n)");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            String oId = cartController.setOrderId();
            int tot = 0;
            for (CartDTO cDto : list) {
            	String temp = cDto.getSum();
    			int bP = Integer.parseInt(temp);
    			tot = tot + bP;
            }
        	if (yesNo.equalsIgnoreCase("y")) {
            	cartController.order(mId, oId, tot);
            	
            	for (CartDTO cDto : list) {
            		cartController.orderBook(oId, cDto);
        		}
            	cartController.delete(mId);
            	System.out.println("구매가 완료 되었습니다. 구매한 내용은 주문목록에서 확인 가능합니다.");
            	return;
            } else {
            	return;
            }
        }
        
        
		
		
	}
	
}
