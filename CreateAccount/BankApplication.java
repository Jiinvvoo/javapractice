package CreateAccount;

import java.util.Scanner;

public class BankApplication {
    private static Account[] accountArray = new Account[100];
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		
		while(run) {
			System.out.println("-------------------------------------------------");
			System.out.println("1. 계좌생성 | 2. 계좌목록 | 3. 예금 | 4. 출금 | 5. 종료");
			System.out.println("-------------------------------------------------");
			System.out.print("선택> ");
			String select = scanner.nextLine();
			
			switch (select) {
				case "1": 
					createAccount();
					break;
				case "2":
					listAccount();
					break;
				case "3":
					deposit();
					break;
				case "4":
					withdraw();
					break;
				case "5":
					run = false;
					System.out.println("프로그램 종료");
					break;
			}
		}
		
	}
	
	private static void createAccount() {
		System.out.println("-----------");
		System.out.println("계좌생성");
		System.out.println("-----------");
		
		System.out.println("계좌번호: ");
		String accountNum = scanner.nextLine();
		System.out.println("계좌주: ");
		String owner = scanner.nextLine();
		System.out.println("초기입금액: ");
		int money = Integer.parseInt(scanner.nextLine());
	    
		//accountArray 0번 인덱스부터 빈곳에 Account 정보 저장
		for (int i = 0; i < accountArray.length; i++) {
            if (accountArray[i] == null) {
                accountArray[i] = new Account(accountNum, owner, money);
                System.out.println("결과: 계좌가 생성되었습니다.");
                break;
                }
		}
	}
	
	private static void listAccount() {
		System.out.println("-----------");
		System.out.println("계좌목록");
		System.out.println("-----------");
		
		//accountArray 0번 인덱스부터 null이 아니면(계좌가 있으면) Account 출력
		for (int i = 0; i < accountArray.length; i++) {
			if (accountArray[i] != null) {
				System.out.println(accountArray[i].getno() + "   " + accountArray[i].getowner() + "   " + accountArray[i].money());
			}
		}

	}
	
	private static void deposit() {
		System.out.println("-----------");
		System.out.println("예금");
		System.out.println("-----------");

		System.out.println("계좌번호: ");
		String searchNum = scanner.nextLine();
		Account account = findAccount(searchNum); // 입력한 번호와 같으면 account에 해당하는 계좌 리턴
		
		System.out.println("예금액: ");
		int depmoney = Integer.parseInt(scanner.nextLine());
		account.setMoney(account.money() + depmoney); // 현재 금액 + 입금액

	}
	
	private static void withdraw() {
		System.out.println("-----------");
		System.out.println("출금");
		System.out.println("-----------");

		System.out.println("계좌번호: ");
		String searchNum = scanner.nextLine();
		Account account = findAccount(searchNum); // 입력한 번호와 같으면 account에 해당하는 계좌 리턴

		System.out.println("출금액: ");
		int withmoney = Integer.parseInt(scanner.nextLine());
		if (withmoney <= account.money()) {
			account.setMoney(account.money() - withmoney); // 현재 금액 - 출금액
		} else {
			System.out.println("잔고 부족"); // 출금액 > 현재 금액
		}

	}
	
    private static Account findAccount(String ano) {
        for (int i = 0; i < accountArray.length; i++) {
        	if (accountArray[i] != null && accountArray[i].getno().equals(ano)) {
                return accountArray[i]; // 찾는 번호와 계좌번호가 같으면 반환
            }
        }
        return null; // 못찾으면 null
    }

}

