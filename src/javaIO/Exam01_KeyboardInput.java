package javaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exam01_KeyboardInput {

	public static void main(String[] args) {
		
		System.out.println("키보드로 한 줄을 입력해요!");
		//표준입력(키보드)로부터 입력을 받기 위해 키보드와 연결된 Stream 객체가 필요
		//Java는 표준입력에 대한 Stream을 기본적으로 제공
		//System.in 
		
		//Stream은 이렇게 inputStream과 OutputStream으로 구분
		//byteStream(기본 데이터형)과 reader,writer계열(문자형) stream으로 구분
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		try {
			String input = br.readLine(); //blocking
			System.out.println("입력받은 데이터 : "+input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
