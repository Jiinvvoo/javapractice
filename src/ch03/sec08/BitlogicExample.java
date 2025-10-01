package ch03.sec08;

public class BitlogicExample {

	public static void main(String[] args) {
		System.out.println("45 & 25 = " + (45 & 25));
		System.out.println("45 | 25 = " + (45 | 25));
		System.out.println("45 ^ 25 = " + (45 ^ 25));
		System.out.println("~45 = " + (~45));
		System.out.println("------------------------");
		// 0010 1101 & 0001 1001 == 0000 1001
		// 0010 1101 | 0001 1001 == 0011 1101
		// 0010 1101 ^ 0001 1001 == 0011 0100 
		// ~0010 1101 == 1101 0010 == -0010 1110
		byte receiveData = -120;
		
		//방법1: 비트 논리곱 연산으로 Unsigned 정수 얻기
		int unsignedInt1 = receiveData & 255;
		System.out.println(unsignedInt1);
		// 1000 1000 & 1111 1111 = 1000 1000 = 136
		
		//방법2: 
		int unsignedInt2 = Byte.toUnsignedInt(receiveData);
		System.out.println(unsignedInt2);
		
		int test = 136;
		byte btest = (byte) test;
		System.out.println(btest);
	}

}
