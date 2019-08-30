package javaLambda;
/* 
 * 함수적 프로그래밍 패턴을 위해 Java는 8버전부터 Lambda를 지원
 *  
 * 람다는 익명함수를 만들기 위한 expression(식)
 * ==> 객체지향언어보다는 함수지향적언어에서 사용
 * 
 * 기존 자바 개발자들은 이 Lambda 개념에 익숙하지 않음
 * 그럼에도 불구하고 자바가 Lambda를 도입한 이유
 * 1. 코드가 간결해짐
 * 2. Java Stream을 이용하기 위해서
 * Java Stream은 collection(List,Map,Set,Array...)의 처리를
 * 굉장히 효율적으로 할 수 있음. (병렬처리가 가능)
 * 
 * 람다식의 기본 형태
 * (매개변수)-> {실행 코드}
 * 익명함수를 정의하는 형태로 되어 있지만 실제로는 익명클래스의 인스턴스를 생성.
 * 람다식이 어떤 객체를 생성하느냐는 람다식이 대입되는 interface 변수가
 * 어떤 interface인가에 달려있음!
 * 
 * 일반적인 interface를 정의해서 람다식으로 표현하기
 * 
 * 
 * 
 *
 */

/*class MyThread extends Thread{
	public void run() {
		System.out.println("쓰레드가 실행되요!");
	}
}*/

/*class MyRunnable implements Runnable{
	public void run() {
		System.out.println("쓰레드가 실행되요!");
	}
}
*/


interface Exam01_LambdaIF{
	// 인터페이스에는 추상 method만 가능
	// method의 정의가 없고 선언만 존재함.
	void myFunc(int k);
	
}




public class Exam01_LambdaBasic {

	public static void main(String[] args) {

		Exam01_LambdaIF sample = (int k) -> {
			System.out.println("출력되요");
		};

		sample.myFunc(100);
	}

}
	
		// Thread 생성
		// 1. Thread의 subclass를 이용해서 Thread 생성
		// 상속을 하면 유지보수가 힘들어져서 잘 안 씀
		//MyThread t = new MyThread();
		//t.start();
		
		
		// 2. Runnable interface를 구현한 class를 이용해서 Thread 생성
		//MyRunnable runnable = new MyRunnable();
		//Thread t = new Thread(runnable);
		//t.start();
		
		// 3. Runnable interface를 구현한 익명 class를 이용해서 
		// Thread 생성(안드로이드 형태)
		
		/*
		 * Runnable runnable = new Runnable() { 
		 * // 객체를 생성 못하는 이유는 추상 메소드가 존재하기 때문 
		 * // 이method를 overriding하면 객체를 생성할 수 있음
		 * 
		 * @Override public void run() { } 
		 * };
		 */
/*	new Thread(()->{
		System.out.println("쓰레드가 실행!");
	}).start();*/
		


