package CreateAccount;

public class Account {
	
	private String no; //계좌번호
	private String owner; //계좌주
	private int money; //잔액
	//private으로 막아서 클래스 밖에선 수정/조회 불가능->캡슐화
	
	public Account(String no, String owner, int money) {
		this.no=no;
		this.owner=owner;
		this.money=money;
	}
	// 계좌 만들 때 호출되는 함수
	
	public String getno() {
		return no;
	}
	
	public String getowner() {
		return owner;
	}
	
	public int money() {
		return money;
	}
	//private이라 외부에서 직접 접근이 안 되기 때문에 읽기 전용 통로를 만들어줌
	
	public void setMoney(int money) {
		this.money=money;
	}
	//잔액수정
}