package ch04.Assignment;

import java.util.Scanner;

public class BackAccount {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean run = true;
		int account = 0;
		
		while(run) {
			System.out.println("--------------------");
			System.out.println("1. 예금 | 2. 출금 | 3. 잔고 | 4. 종료");
			System.out.println("--------------------");
			System.out.print("선택: ");
			
			String strNum = scanner.nextLine();
			
			if (strNum.equals("1")) {
				System.out.println("예금액 > ");
				String strX = scanner.nextLine();
				int save = Integer.parseInt(strX);
				
				if (save > 0) {
					account += save;
				}
			} else if (strNum.equals("2")) {
				System.out.print("출금액 > ");
				String strX = scanner.nextLine();
				int draw = Integer.parseInt(strX);
				
				if (draw > 0 && draw < account) {
					account -= draw;	
				}
				
			} else if (strNum.equals("3")) {
				System.out.println("잔고 > " + account);
			} else if (strNum.equals("4")) {
				run = false;
			} else {
				System.out.println("1-4번까지 숫자를 입력하세요");
			}
		}
		System.out.println("프로그램 종료");
	}
}
