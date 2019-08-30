package javaStream;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream은 Java 8에서 도입이 됨. 주의해야할 것은 java.io안에 있는 Stream과 다름.
 * 사용용도: 컬렉션 처리(List, Set, Map, 배열)를 위해서 사용됨.
 * Stream은 컬렉션 데이터를 반복시키는 반복자의 역할을 함
 * 예를 들어 ArrayList안에 학생 객체가 5개 있으면 그 5개를 하나씩 가져오는 역할을 수행.
 * => 이렇게 가져온 데이터를 람다식을 이용해서 처리
 * 
 * 
 */

class Exam01_Student{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	private String name;
	private int kor;
	private int eng;
	public Exam01_Student() {
		// TODO Auto-generated constructor stub
	}
	public Exam01_Student(String name, int kor, int eng) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
	}
	
	
}


public class Exam01_StreamBasic {
	
	private static List<String> myBuddy=
			Arrays.asList("홍길동","김길동","최길동","신사임당");
	
	private static List<Exam01_Student> students =
			Arrays.asList(
					new Exam01_Student("홍길동",10,20),
					new Exam01_Student("최길동",60,30),
					new Exam01_Student("박길동",30,80),
					new Exam01_Student("이길동",90,10)
					);
	
	public static void main(String[] args) {
		// 사람이름을 출력
		// 방법1. 일반 for문(첨자를 사용)을 이용해서 처리.
		for(int i=0;i<myBuddy.size();i++) {
			System.out.println(myBuddy.get(i));
		}
		// 사람 이름을 출력
		// 첨자를 이용한 반복을 피하기 위해 Iterator를 사용
		
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		// 사람의 이름 출력
		// 반복자가 필요가 없음. 내부 반복자 이요. 병렬 처리가 가능
		//Stream<String> stream = myBuddy.parallelStream();
		// 컴퓨터가 사용자 컴퓨터 사양에 맞게 쓰레드 이용함
		Consumer<String> consumer = t->{
			System.out.println(t+","+Thread.currentThread().getName());
		};
		Stream<String> stream = myBuddy.parallelStream();
		stream.forEach(consumer);
		
		
		
		Stream<String> stream2 = myBuddy.stream();
		stream2.forEach(t->System.out.println(t));
		
		Stream<Exam01_Student> studentStream=students.stream();
		double avg = studentStream.mapToInt(t->t.getKor()).average().getAsDouble();
		System.out.println(avg);
		
				

	}

}
