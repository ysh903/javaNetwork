package javaLambda;


/*
 * 람다식을 정의해서 사용할 때 주의해야 할 점이 몇 가지 있음
 * 클래스의 맴버(필드+매소드)왕 로컬변수(지역변수)의 사용에 약간의 제약 存在
 * 특히 this keyword를 사용할 때 주의해야 함
 * this: 현재 사용되는 객체의 reference.
 * 람다식은 익명 객체를 만들어 내는 코드. 람다식의 실행코드 내에서
 * this keyword를 쓰면 익명객체를 지칭하지 않음.
 * 람다식 안에서는 지역변수를 readonly 형태로 사용해야 함.
 * 
 * 
 * 
 * 
 */

@FunctionalInterface
interface Exam03_LambdaIF{
	public void myFunc();
}

class OuterClass{
	//Field (기본적으로 class의 field는 private)
		public int outerField = 100;
		public OuterClass() {
			// default 생성자
			System.out.println(this.getClass().getName());
		}
		
	//class안에 다른 class를 정의(inner class)
		class InnerClass {
			int innerField = 200; //Field
			
			Exam03_LambdaIF fieldLamda =()->{
				System.out.println("outerField: "+outerField);
				System.out.println("OuterClass의 객체를 찾기: "
				+OuterClass.this.outerField);
				System.out.println("innerField: "+innerField);
				System.out.println("InnerClass의 객체를 찾기: "
						+this.innerField);
				System.out.println(this.getClass().getName());
			}; //Field
						
			public InnerClass() {
				System.out.println(this.getClass().getName());
			}
			public void innerMethod() {//method
				int localVal = 100; //지역변수
				//지역변수는 stack영역에 저장이 되고, 메소드가 호출되면 생기고, 끝나면 사라짐.
				Exam03_LambdaIF localLambda=()->{
					System.out.println(localVal);
					
				};
				
				localLambda.myFunc();
				
			}
			
		}
}

//프로그램의 시작을 위한 dummyclass
public class Exam03_LambdaUsingVariable {

	public static void main(String[] args) {
		// 람다식을 사용하려면 InnerClass의 instance가 존재해야함
		// InnerClass가 inner class인 경우
		// inner class의 instance를 생성하려면 outer class의 instance부터 생성해야 함.
		OuterClass outer = new OuterClass(); // 외부 클래스 객체  생성
		OuterClass.InnerClass inner = outer.new InnerClass();
		inner.fieldLamda.myFunc();
		

	}

}
