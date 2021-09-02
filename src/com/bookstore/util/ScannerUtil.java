package com.bookstore.util;

import java.util.Scanner;

public class ScannerUtil {
    // 특정 양식에 맞춰서
    // 출력에 필요한 안내 문구를
    // 출력하는 메소드
    public static void printMessage(String message) {
        System.out.println(message);
        System.out.print("> ");
    }

    // String 입력을 담당하는
    // nextLine()
    public static String nextLine(Scanner scanner, String message) {
        // 사용자가 입력한 String 값을 임시 보관할
        // String 변수 선언
        String temp = "";
        
        // 파라미터로 들어온 메시지를 출력해서
        // 입력 폼을 만들어준다.
        printMessage(message);
        
        // temp에 입력값 저장
        temp = scanner.nextLine();
        
        // String 클래스의 isExmpty() 라는 메소드를 사용하면
        // 해당 String 클래스 변수에 아무런 값도 안 들어가 있으면
        // true, 무언가 글자가 하나라도 들어가 있으면 false라는
        // 결과값이 리턴된다.
        // 그 메소드를 사용해서, temp의 값이 비어있으면
        // 다시 temp = scanner.nextLine()을 실행시켜서
        // 값을 입력 받는다.
        
       if(temp.isEmpty()) {
           temp = scanner.nextLine();
       }
        return temp;
    }
    
    // int 입력을 담당하는
    // nextInt()
    public static int nextInt(Scanner scanner, String message) {
        printMessage(message);
        return scanner.nextInt();
    }
    
    // 최소값과 최대값을 받아서
    // 그 사이의 int 값 입력을 담당하는
    // nextInt()
    public static int nextInt(Scanner scanner, String message, 
            int min, int max) {
        // int 변수에
        // 위의 최소, 최대값이 없는
        // nextInt() 결과값을 저장
        int temp = nextInt(scanner, message);
        
        // temp의 값이
        // 파라미터로 보낸 최소/최대값의 속하지 않는다면
        // 속한 값을 입력할 때까지 다시 입력을 받는다.
        while(!(temp >= min && temp <= max)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextInt(scanner, message);
        }
        
        return temp;
        
    }
    
    // 해보기
    // double 값의 입력을 담당하는
    // nextDouble() 메소드
    // -> double이 아니고 String
    public static double nextDouble(Scanner scanner, String message) {
        printMessage(message);
        return scanner.nextDouble();
    }
    
    
    // 최소값과 최대값을 받아서
    // 그 사이의 값 입력을 담당하는
    // nextDouble() 메소드
    public static double nextDouble(Scanner scanner, String message,
            double min, double max) {
        
        double temp = nextDouble(scanner,message);
        
        while(!(temp >= min && temp <= max)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextDouble(scanner, message);
    }
        return temp;
    }
}