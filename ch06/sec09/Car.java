package ch06.sec09;

public class Car {
	//필드선언
	String model;
	int speed;
	
	//생성자 선언
	Car(String model) {
		this.model = model; //매개변수를 필드에 대입(this 생략 불가)
	} // this.인스턴스 필드명 = 생성자의 매개변수명

	//메소드 선언
	void setSpeed(int speed) {
		this.speed = speed;
	}
	
	void run() {
		this.setSpeed(100); // this 생략가능
		System.out.println(this.model + "가 달립니다. (시속: " + this.speed + "km/h)");
	}
}
