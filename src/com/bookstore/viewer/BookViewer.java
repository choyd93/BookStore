package com.bookstore.viewer;

import java.util.HashMap;

import java.util.List;
import java.util.Scanner;
import com.bookstore.controller.BookController;
import com.bookstore.controller.CartController;
import com.bookstore.model.BookDTO;
import com.bookstore.model.CartDTO;
import com.bookstore.model.MemberDTO;
import com.bookstore.util.ScannerUtil;

public class BookViewer {
    private MemberDTO login;
    private OrderViewer orderViewer;
    private ShippingViewer shippingViewer;
    private MemberInfoViewer memberInfoViewer;
    private BookController bookController;
    private CartController cartController;
    private OrderBookViewer orderBookViewer;
    private CartViewer cartViewer;
    private Scanner scanner;
    private HashMap<String, String> map = null;
    private boolean exit, exit2, exit3, exit4, exit5;

    public BookViewer() {
        bookController = new BookController();
        cartController = new CartController();
        scanner = new Scanner(System.in);
        cartViewer = new CartViewer();
        orderViewer = new OrderViewer();
        shippingViewer = new ShippingViewer();
        memberInfoViewer = new MemberInfoViewer();
        orderBookViewer = new OrderBookViewer();
        login = new MemberDTO();
    }
    
    // 로그인 후 첫 메뉴 (임시)
    public void bookMenu(MemberDTO vo) {
        //memberID = "1";
        login = vo;
        memberInfoViewer = new MemberInfoViewer(login);
        shippingViewer = new ShippingViewer(login);
        
        cartViewer = new CartViewer(login);
        orderViewer = new OrderViewer(login);
        orderBookViewer = new OrderBookViewer();
        
        while(login != null) {
            System.out.println("======================================");
            System.out.println("1. 도서목록 2. 주문목록 3. 회원정보 보기 ");
            String msg = new String("4. 장바구니 5. 배송지 관리 0. 로그아웃");
            int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 5);
            
            if (userChoice == 1) {
                exit = false;
                exit2 = false;
                exit3 = false;
                exit4 = false;
                exit5 = false;
                bookListAll(login.getMemberId());
            } else if (userChoice == 2) {
                orderViewer.printAll();
            } else if (userChoice == 3) {
                login = memberInfoViewer.printOne();            
            } else if (userChoice == 4) {
                cartViewer.printAll();
            } else if (userChoice == 5) {
                shippingViewer.choice();
            } else if (userChoice == 0) {
                System.out.println("이용해주셔서 감사합니다.");
                login = null;
            }else {
                System.out.println("이용해주셔서 감사합니다.");
                scanner.close();
                break;
            } 
        }
    }
    
    // 도서 전체 리스트 
    public void bookListAll(String memberID) {
        exit = false;
        bookList(bookController.bookListAll());
        bookListMenu(memberID);
    }
    
    // 도서 전체 리스트 메뉴 (검색, 책선택)
    public void bookListMenu(String memberID) {
        System.out.println("=================================================");
        String msg = new String("1. 검색  2. 책 선택  0. 뒤로가기");
        
        int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 2);
        
        if(userChoice == 1) {
            bookSearch(memberID);
        } else if (userChoice == 2) {
            bookDetailInput(memberID);
        } else if (userChoice == 0) {
            exit2 = true;
            return;
        }else {
            
        }     
    }
    
    // 책 검색하기 메뉴 
    public void bookSearch(String memberID) {
        while(true) {
            if(exit || exit2 || exit3 || exit4 || exit5) {
                break;
            }
            System.out.println("=================================================");
            System.out.println("검색할 키워드를 선택해주세요.");
            String msg = new String("1. 책 이름  2. 저자 이름 3. 출판사  0. 뒤로가기");
            
            int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 3);
            if(userChoice == 1) {
                bookNameSelect(memberID);
            } else if (userChoice == 2) {
                bookWriterSelect(memberID);
            } else if (userChoice == 3) {
                bookPublisherSelect(memberID);
            } else if (userChoice == 0){
                return;
            } else {
                
            }
        }
    }
    
    // 책 선택 시 상세정보 보기를 위한 입력 (책 번호 입력) 
    public void bookDetailInput(String memberID) {
        if(exit) {
            return;
        }
        CartDTO cart = null;
        BookDTO book = null;
        String msg = "";
        
        // 북 데이터가 널값이 아니면 북DTO를 카트DTO에 넣고 쇼핑백에 넣기
        while(book == null) {
            msg = new String("상세보기할 책의 번호를 입력해주세요.");                
            book = bookDetail(validateBookIdSelect(ScannerUtil.nextLine(scanner, msg), memberID));
        }
        cart = new CartDTO(memberID, book.getBook_id(), "0", Integer.toString(book.getSale_price()));
       
        // 회원번호와 책번호를 함께 넣은 cartDTO를 리턴
        bookSelectOne(cart);
    }
    // 맵으로 정렬된 책번호(key)를 선택하면 실제 데이터베이스에 저장된 책 번호(value) 값을 가져오는 메소드
    public void bookDtailMapInput (String memberID, HashMap<String, String> map) {
        CartDTO cart = null;
        BookDTO book = null;
        String bookID = "";
        String msg = "";
        
            msg = new String("상세보기할 책의 번호를 입력해주세요.");
            String insertBookId = ScannerUtil.nextLine(scanner, msg);
            bookID = map.get(insertBookId);
            
            while(bookID == null) {
                msg = new String("잘못 입력하셨습니다. 다시 입력해주세요.");
                insertBookId = ScannerUtil.nextLine(scanner, msg);
                bookID = map.get(insertBookId);
            }    
                
            book = bookDetail(validateBookIdSelect(bookID, memberID, map));
                        
        cart = new CartDTO(memberID, book.getBook_id(), "0", Integer.toString(book.getSale_price()));
        bookSelectOne(cart);
    }
    
    
    // 책 리스트 출력 버전 1. - 도서 전체 리스트 출력 할 때
    public void bookList(List<BookDTO> list) {
        System.out.println("=================================================");
        System.out.println("                 전체 도서 목록 ");
        System.out.println("=================================================");
        System.out.println("도서번호 |  도 서 명  | 저  자 | 출판사 | 판매가 ");
        System.out.println("=================================================");
        
        for(BookDTO book : list) {
            System.out.println(book.getBook_id() + ". " + book.getBook_title() 
            + "\t" + book.getWriter() + " 지음" + "\t" + book.getPublisher() + "\t" + book.getSale_price() + "원");
        }
    }
    
    // 책 리스트 출력 버전 2. - (검색 시 도서번호를 HashMap에 넣어서 1부터 차례대로 정렬)
    public HashMap<String, String> bookListSort(List<BookDTO> list) {
        int idx = 1;
        map = new HashMap<String, String>();

        System.out.println("=================================================");
        System.out.println("도서번호 |  도 서 명  | 저  자 | 출판사 | 판매가 ");
        System.out.println("=================================================");

        for(BookDTO book : list) {
            map.put(String.valueOf(idx), book.getBook_id());
            System.out.println(idx + ". " + book.getBook_title() 
            + "\t" + book.getWriter() + " 지음" + "\t"  + book.getPublisher() + "\t" + book.getSale_price() + "원");
            idx++;
        }
        return map;
    }
    
    // 책 이름으로 검색하기 
    public void bookNameSelect(String memberID) {
        if(exit || exit2 || exit3) {
            return;
        }
        List<BookDTO> list = null;
        String msg = "";
        
        while(list == null) {
            msg = new String("검색하실 책의 이름을 입력해주세요.");                
            list = validateBookNameSelect(ScannerUtil.nextLine(scanner, msg), memberID);
            
            // 맵으로 번호 정렬하고 리스트로 출력 
            map = bookListSort(list);
            
        }
        bookDtailMapInput(memberID, map);
       
    }
    
    // 책 저자 이름으로 검색하기 
    public void bookWriterSelect(String memberID) {
        List<BookDTO> list = null;
        String msg = "";
        
        while(list == null) {
            msg = new String("검색하실 저자의 이름을 입력해주세요.");                
            list = validateBookWriterSelect(ScannerUtil.nextLine(scanner, msg), memberID);
            map = bookListSort(list);
        }
        bookDtailMapInput(memberID, map);
       
    }
    
    // 책 출판사 이름으로 검색하기 
    public void bookPublisherSelect(String memberID) {
        List<BookDTO> list = null;
        String msg = "";
        
        while(list == null) {
            msg = new String("검색하실 출판사의 이름을 입력해주세요.");                
            list = validateBookPublisherSelect(ScannerUtil.nextLine(scanner, msg), memberID);
            map = bookListSort(list);
        }
        bookDtailMapInput(memberID, map);
    }
    
    // 책 상세정보 보기 
    public BookDTO bookDetail(BookDTO vo) {
        System.out.println("=====================================");
        System.out.println("            책 상세보기 ");
        System.out.println("=====================================");
        System.out.println("제목 : " + vo.getBook_title());
        
        if(vo.getSubtitle() != null) {
            System.out.println("부제 : " + vo.getSubtitle());
        }
        
        System.out.println("글쓴이 : " + vo.getWriter());
        
        if(vo.getTranslator() != null) {
            System.out.println("옮긴이 : " + vo.getTranslator());
        } 
        System.out.println("출판사 : " + vo.getPublisher());
        System.out.println("출간일 : " + vo.getRelease_date());
        System.out.println("정가 : " + vo.getBook_price() + "원 " 
                           + "(" + (int)vo.getBook_sale_rate() + "% 할인!)");
        System.out.println("판매가 : " + vo.getSale_price() + "원");

        return vo;  
    }
    
    // 책 번호로 선택 확인 1. - 전체 책 리스트 용 
    public BookDTO validateBookIdSelect(String BookId, String memberID) {
        BookDTO vo = bookController.bookIdSelect(BookId);
        while(vo == null) {
            System.out.println("잘못된 책 번호를 입력하셨습니다.");
            String msg = new String("원하시는 책 번호를 다시 한번 입력해주세요. (뒤로 가기를 원하시면 [X] 버튼을 눌러주세요.)");
            BookId = ScannerUtil.nextLine(scanner, msg);
            
            if (BookId.equalsIgnoreCase("x")) {
                bookSearch(memberID);
            }
            vo = bookController.bookIdSelect(BookId);
        }
        return vo;
    }
    
    // 책 번호로 선택 확인 2. - 검색 시 맵 key 활용 시 
    public BookDTO validateBookIdSelect(String BookId, String memberID, HashMap<String, String> map) {
        BookDTO vo = bookController.bookIdSelect(BookId);
        
        ///// 앞에서 확인을 하기 떄문에 굳이 필요 없는 코드 
//        int IntBookId = Integer.parseInt(BookId);    
//        while(vo == null || IntBookId <= 0 ||  IntBookId > map.size()) {
//            System.out.println("잘못된 책 번호를 입력하셨습니다.");
//            String msg = new String("원하시는 책 번호를 다시 한번 입력해주세요. (뒤로 가기를 원하시면 [X] 버튼을 눌러주세요.)");            
//            String newBookId = map.get(ScannerUtil.nextLine(scanner, msg));
//            
//            if (newBookId == null) {
//                newBookId = "0";
//            }else if (newBookId.equalsIgnoreCase("x")) {
//                bookSearch(memberID);
//            } else 
//            
//            vo = bookController.bookIdSelect(newBookId);
//            BookId = vo.getBook_id();
//        }
        
        if(vo != null) {
            return vo;
        } else {
            System.out.println("BookDTO가 널값입니다. ");
        return null;
        }
    }
    
    // 책 이름으로 검색 확인 
    public List<BookDTO> validateBookNameSelect(String BookName, String memberID) {
        List<BookDTO> list = bookController.bookNameSelect(BookName);
        while(list.isEmpty()) {
            System.out.println("검색하신 책이 존재하지 않습니다.");
            String msg = new String("검색할 책의 이름을 입력해주세요. (뒤로 가기를 원하시면 [X] 버튼을 눌러주세요.)");
            BookName = ScannerUtil.nextLine(scanner, msg);
            
            if (BookName.equalsIgnoreCase("x")) {
                bookSearch(memberID);
            }
            list = bookController.bookNameSelect(BookName);
        }
        
        return list;
    }
    
    // 책 저자 이름으로 검색 확인 
    public List<BookDTO> validateBookWriterSelect(String BookWriter, String memberID) {
        List<BookDTO> list = bookController.bookWriterSelect(BookWriter);
        while(list.isEmpty()) {
            System.out.println("검색하신 저자가 존재하지 않습니다.");
            String msg = new String("검색할 저자의 이름을 입력해주세요. (뒤로 가기를 원하시면 [X] 버튼을 눌러주세요.)");
            BookWriter = ScannerUtil.nextLine(scanner, msg);
            
            if (BookWriter.equalsIgnoreCase("x")) {
                bookSearch(memberID);
            }
            list = bookController.bookWriterSelect(BookWriter);
        }
        return list;
    }
    
    // 출판사 이름으로 검색 확인 
    public List<BookDTO> validateBookPublisherSelect(String BookPublisher, String memberID) {
        List<BookDTO> list = bookController.bookPublisherSelect(BookPublisher);
        while(list.isEmpty()) {
            System.out.println("검색하신 출판사가 존재하지 않습니다.");
            String msg = new String("검색할 출판사의 이름을 입력해주세요. (뒤로 가기를 원하시면 [X] 버튼을 눌러주세요.)");
            BookPublisher = ScannerUtil.nextLine(scanner, msg);
            
            if (BookPublisher.equalsIgnoreCase("x")) {
                bookSearch(memberID);
            }
            list = bookController.bookPublisherSelect(BookPublisher);
        }
        return list;
    }
    

    // 책 선택하기 메뉴 
    public void bookSelectOne(CartDTO cart) {
        while(true) {
            if(exit || exit2 || exit3 || exit4 || exit5) {
                break;
            }
            System.out.println("=====================================");
            String msg = new String("1. 장바구니 넣기  2. 구매하기  3. 도서목록보기");
            int userChoice = ScannerUtil.nextInt(scanner, msg, 1, 3);
            if(userChoice == 1) {
                bookShoppingBag(cart);
            } else if(userChoice == 2) {
                buyBook(cart);
            } else if(userChoice == 3) {
                exit = true;
                bookListAll(login.getMemberId());
                break;
            }
        }
    }
    
    // 장바구니 메소드 
    public void bookShoppingBag(CartDTO cart) {   
        String msg = "";
        CartDTO ShoppingBagInsert = validateBookShoppingBagInsert(cart);
        
        msg = new String("장바구니로 이동하시겠습니까?(y/n)");
        String yesNo = ScannerUtil.nextLine(scanner, msg);
        
        if(yesNo.equalsIgnoreCase("y")) {
            exit3 = cartViewer.printAll();
            return;
        } else if (yesNo.equalsIgnoreCase("n")) {
            exit2 = true;
            return;
        }
    }
    
    // 장바구니에서 cartDTO 업데이트 확인
    public CartDTO validateBookShoppingBagInsert(CartDTO cart) {
        boolean inputSwitch = false;
        CartDTO InsertCart = null;
        String msg = "";
        
        while(InsertCart == null) {
            
            if(inputSwitch == false) {
                msg = new String("몇권을 넣으시겠습니까? (취소하려면 x버튼을 누르세요.)");
                inputSwitch = true;
            } else {
                System.out.println("선택한 책을 장바구니에 넣지 못했습니다. ");
                msg = new String("몇권을 넣으시겠습니까? (취소하려면 x버튼을 누르세요.)");
            }
            
            String count = ScannerUtil.nextLine(scanner, msg);
            
            if(count.equalsIgnoreCase("0")) {
                bookSelectOne(cart);
            }
            cart.setBookAmount(count);
            
            // 판매가와 수량을 곱해서 최종 판매가를 cartDTO에 업데이트 해줌.
            int finalPriceSum = Integer.parseInt(cart.getSum()) * Integer.parseInt(count);
            String sfinalPriceSum = Integer.toString(finalPriceSum);
            cart.setSum(sfinalPriceSum);
            
            // cart에 책번호가 같은게 있는지 검색 있으면 update 없으면 insert
            List<CartDTO> list = cartController.selectAllById(cart.getmId());
            
            int check = 0;
            for(CartDTO c : list) {
                if(cart.getbId().equals(c.getbId())) {
                    
                    int tempAmount = Integer.parseInt(c.getBookAmount()) + Integer.parseInt(cart.getBookAmount());
                    int tempUpdateTot = Integer.parseInt(c.getSum()) + Integer.parseInt(cart.getSum());
                    String updateAmount = Integer.toString(tempAmount);
                    String UpdateTot = Integer.toString(tempUpdateTot);
                    
                    cart.setBookAmount(updateAmount);
                    cart.setSum(UpdateTot);
                    InsertCart = cartController.bookShoppingBagUpdate(cart);
                    check = 1;
                }
            }
            
            if(check == 0){
                InsertCart = cartController.bookShoppingBag(cart);
            }
        }
        return InsertCart;
    }
    
    // 구매하기 메소드
    public void buyBook(CartDTO cart) {
        
        String bookName = bookController.bookIdSelect(cart.getbId()).getBook_title();
        String msg = new String("[" + bookName + "]을(를) 구매하시겠습니까? (y/n)");
        String yesNo = ScannerUtil.nextLine(scanner, msg);
        if (yesNo.equalsIgnoreCase("y")) {
            msg = new String("선택한 책을 몇권 구매하시겠습니까? (취소하려면 '0'버튼을 누르세요.)");
            int count = ScannerUtil.nextInt(scanner, msg, 0, 100);       
            if(String.valueOf(count).equalsIgnoreCase("0")) {
                exit5 = true;
                bookSelectOne(cart);
                return;
            }
            
            cart.setBookAmount(String.valueOf(count));
            
            String oId = bookController.setOrderId();
            int tot = 0;
            String temp = cart.getSum();
            int bP = Integer.parseInt(temp);
            tot = count * bP;
            
            bookController.order(cart.getmId(), oId, tot);
            int validate = bookController.orderBook(oId, cart);
            // ordes, orderbook, shipping 데이터 추가
            // bookController.bookOrder(cart);
            //bookController.bookShippingInsert(cart);
            /*
            if(bookController.bookShippingInsert(cart) == 1) {
                System.out.println("주문 완료되었습니다.");
                orderViewer.printAll();
            } else {
                bookSelectOne(cart);
            }
            */ 
            
            if(validate != 0) {
                System.out.println("주문 완료되었습니다.");
                exit4 = true;
                orderViewer.printAll();
            } else {
                bookSelectOne(cart);
            }
            
        } else {
            bookSelectOne(cart);
        }
        
    }
       
}
