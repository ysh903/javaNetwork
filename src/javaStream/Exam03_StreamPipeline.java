package javaStream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * reduction
 * 
 * => 대량의 데이터를 가공해서 축소하는 개념
 * => sum, average, count, max, min
 * 
 * Collection을 사용할 때 Stream을 이용해서 이런 reduction 작업을 쉽게 할 수 있음
 * 
 * 만약 Collection 안에 reduction하기가 쉽지 않은 형태로 데이터가 들어가있으면
 * 중간처리과정을 거쳐서 reduction하기 좋은 형태로 변환
 * 
 * Stream은 pipeline을 지원(Stream을 연결해서 사용 가능)
 * 
 * 직원객체를 생성해서 ArrayList안에 여러명의 직원을 저장.
 * 
 * 이 직원 중에 IT에 종사하고 남자인 직원을 추려서 해당 직원들의 연봉 평균을 출력!
 * 
 * 
 * 
 * 
 */

class Exam03_Employee implements Comparable<Exam03_Employee>{
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	private String name;
	private int age;
	private String dept;
	private String gender;
	private int salary;
	
	public Exam03_Employee() {
		// TODO Auto-generated constructor stub
	}

	public Exam03_Employee(String name, int age, String dept, 
			String gender, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
		this.gender = gender;
		this.salary = salary;
	}
	
	
	// 기본적으로 객체 비교 메소드 존재
	@Override
	public boolean equals(Object obj) {
		// 만약 overriding을 하지 않으며 메모리 주소를 가지고 비교.
		// 내가 원하는 방식으로 overriding을 해서 특정 조건을 만족하면 객체가 같다고 작성하기
		
		boolean result = false;
		Exam03_Employee target = (Exam03_Employee)obj;
		if(this.getName().equals(target.getName())){
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	
	@Override
	public int compareTo(Exam03_Employee o) {
		// 정수값을 리턴.
		// 양수 리턴-> 순서를 바꿈
		// 0 or 음수 리턴-> 순서 안 바뀜.
		int result=0;
		if(this.getSalary()>o.getSalary()) {
			result = 1;
			
		}else if (this.getSalary()==o.getSalary()) {
			result=0;
		}else {
			result=-1;
		}
		
		
		return result;
	}
	
	
}




public class Exam03_StreamPipeline {

	private static List<Exam03_Employee> employees =
			Arrays.asList(
					new Exam03_Employee("홍길동",20,"IT","남자",2000),
					new Exam03_Employee("김길동",30,"Sales","여자",3000),
					new Exam03_Employee("최길동",20,"IT","남자",1000),
					new Exam03_Employee("이순신",50,"Sales","남자",3500),
					new Exam03_Employee("유관순",35,"IT","여자",7000),
					new Exam03_Employee("신사임당",60,"IT","여자",4000),
					new Exam03_Employee("강감찬",30,"IT","남자",1000),
					new Exam03_Employee("이황",45,"Sales","남자",5000),
					new Exam03_Employee("홍길동",20,"IT","남자",2000)
					);
	public static void main(String[] args) {
		//부서가 IT에 종사하고 남자 직원들의 연봉 평균 구하기
		Stream<Exam03_Employee> stream = employees.stream();
		// stream의 중간처리와 최종처리를 이용해서 원하는 작업하기
		// filter method는 결과값을 가지고 있는 stream을 리턴
		//filter가 predicate(true) 인 것만 남김
//		double avg = 
//		stream	.filter(t->t.getDept().equals("IT"))
//				.filter(t->t.getGender().equals("남자"))
//				.mapToInt(t->t.getSalary()) // 중간처리, 최종처리 없으면 작동 안함.
//				.average().getAsDouble(); // reduction, 최종 처리가 있어야 작동, lazy
//		
//		System.out.println("IT 남자 직원 평균 연봉: "+avg);
		
		// 그럼 Stream이 가지고 있는 method는 무엇이 있는가?
		// 나이가 35이상인 직원 중 남자 직원의 이름 출력
		
//		stream.filter(t->(t.getAge()>=35))
//		.filter(t->t.getGender().equals("남자"))
//		.forEach(t->System.out.println(t.getName()));
		
		// 중복 제거를 위한 중간처리
//		int temp[]= {10,20,30,40,50,30,40};
//		IntStream s = Arrays.stream(temp);
//		s.distinct().forEach(t->System.out.println(t));
		
		// 객체에 대한 중복제거
		
		// vo안에서 equals()method를 overriding해서 처리.
		//employees.stream().distinct().forEach(t->System.out.println(t.getName()));
		
		// mapToInt()=>mapXXX()
		// 정렬(부서가 it인 사람들을 연봉순으로 출력)
//		employees.stream()
//		.filter(t->t.getDept().equals("IT"))
//		.sorted(Comparator.reverseOrder()) //오름차순 정렬
//		.forEach(t->System.out.println(t.getName()+","+t.getSalary()));
		
		// 반복
		// forEach()를 이용하면 스트림 안의 요소를 반복 가능
		// forEach는 최종 처리 함수여서 중간 함수에 넣을 수 없음
		// 중간 처리 함수로 반복처리하는 함수 제공됨.
		
//		employees.stream()
//		.peek(t->System.out.println(t.getName()))
//		.mapToInt(t->t.getSalary())
//		.forEach(t->System.out.println(t));
		
		//확인용 최종 처리 함수
		//50세 이상인 사람과 55세 초과 비교
//		boolean result = employees.stream().filter(t->(t.getAge()>=50))
//		.allMatch(t->(t.getAge()>55));
//		System.out.println(result);
		
		//최종 확인용으로 forEach() 대신 collection() 이용해보기.
		//나이가 50살 이상인 사람들의 연봉을 구해서 List<Integer>형태의 ArrayList에 저장
		
		List<Integer> result = employees.stream().filter(t->(t.getAge()>=50))
				.map(t->t.getSalary())
				.collect(Collectors.toList());
		System.out.println(result);
		
		
		Set<Integer> result1 = employees.stream().filter(t->(t.getAge()>=50))
				.map(t->t.getSalary())
				.collect(Collectors.toCollection(HashSet::new));
		System.out.println(result1);
		
		//set 이나 Map으로 저장 가능 
		
	}

}
