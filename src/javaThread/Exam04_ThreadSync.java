package javaThread;

/*
 * 2개의 Thread를 파생시켜서 공용 객체를 이용하도록 만들기
 * Thread가 공용객체를 동기화해서 사용하는 경우와 그렇지 않은 경우를 비교해서 이해하기
 * 
 * 공용객체를 만들기 위한 class를 정의
 * 
 * 
 */


class SharedObject{
	//gette&setter (thread에 의해 사용됨)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	private int number; //공용 객체가 가지는 field
	
	//Thread에 의해 사용이 되는 business method
	//public void assignNumber(int number) {
	// 위 코드는 첫 번째가 sleep동안 두 번째가 숫자를 변경.
	// 아래 코드는 첫번째가 sleep동안 모니터를 독점, 수행이 끝나 반환 후 두 번째 작동
	// method 동기화는 효율이 별로 않 좋음.
	// 동기화 block을 이용해서 처리하는게 일반적.
	//public synchronized void assignNumber(int number) {
	public void assignNumber(int number) {	
		synchronized (this) { // 특정 코드 부분만 동기화10
			this.number = number;
			try {
				Thread.sleep(3000);
				System.out.println("현재 공용객체의 number : "+this.number);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}

//runnable interface를 구현한 class(Thread 생성자의 인자로 사용하기 위해)
class MyRunnable implements Runnable{
	
	SharedObject shared;
	int input;
	
	public MyRunnable(SharedObject shared, int input) {
		this.shared = shared;
		this.input=input;
	}
	@Override
	public void run() {
		shared.assignNumber(input);
		
	}
}


public class Exam04_ThreadSync  {
	
	

	public static void main(String[] args) {
		//공용객체를 하나 생성
		SharedObject shared = new SharedObject();
		
		//Thread를 생성(2개)
		Thread t1 = new Thread(new MyRunnable(shared,100));
		Thread t2 = new Thread(new MyRunnable(shared,200));
		//Thread 실행(runnable 상태로 전환)
		t1.start();
		t2.start();
	}

	

}
