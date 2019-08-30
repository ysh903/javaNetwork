package javaThread;

// 공용객체를 생성하기 위한 class 정의
class MyShared {
	
	//method 호출할 때 thread가 번갈아 호출하도록 만들기
	//wait는 notify되어야만 깨어날 수 있음
	
	public synchronized void printNum() {
		for(int i=0;i<10;i++) {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName()+" : "+i);
				notify(); //현재 wait 상태에 있는 Thread를 깨워서 runnable 상태로 전환
				wait(); // 자신이 가지고 있는 monitor객체를 놓고 스스로 wait block에 들어감
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}

class Exam05_Runnable implements Runnable{
	MyShared obj;
	
	public Exam05_Runnable(MyShared obj) {
		this.obj=obj;
	}
	
	@Override
	public void run() {
		obj.printNum();
		
	}
}

public class Exam05_ThreadWaitNotify {

	public static void main(String[] args) {
		
		MyShared shared = new MyShared();
		
		Thread t1 = new Thread(new Exam05_Runnable(shared));
		Thread t2 = new Thread(new Exam05_Runnable(shared));
		
		t1.start();
		t2.start();
	}

}
