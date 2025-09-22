package ch02.sec08;

public class CastingExample {

	public static void main(String[] args) {
		int var1 = 10;
		byte var2 = (byte) var1; // 강제 타입 변환
		System.out.println(var2); // 변환 후 10 유지
		
		long var3 = 300;
		int var4 = (int) var3; // 강제 타입 변환
		System.out.println(var4); // 변환 후 300 유지
		
		int var5 = 65;
		char var6 = (char) var5; // 강제 타입 변환 
		System.out.println(var6); //'A' 출력

		double var7 = 3.14;
		int var8  = (int) var7; // 강제 타입 변환
		System.out.println(var8); // 3 출력
	}

}
