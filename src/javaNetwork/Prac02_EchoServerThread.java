package javaNetwork;

/*
 * Echo program 작성하기
 * 지금 프로그램은 서버가 클라이언트 1명만 서비스 가능
 * Thread를 이용해서 다수의 클라이언트 서비스 가능
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;

public class Prac02_EchoServerThread {

	ExecutorService executorService;
	
	
	public static void main(String[] args) {
		
		ServerSocket server=null;
		Socket socket = null;
		ExecutorService executorService;
		executorService = Executors.newCachedThreadPool();
		
		try {
			server = new ServerSocket(5557);
			System.out.println("서버프로그램 기동 - 5557");			
			socket = server.accept();
			//stream 생성
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			//br로부터 데이터를 읽어서 out을 통해 다시 전달
			System.out.println("서버접속 성공");
			
			out.println("<Server>: 서버에 접속하신 것을 환영합니다.");
			out.flush();
			String msg="";
			while(!((msg=br.readLine()).equals("/@EXIT"))) {
				out.println(msg);
				out.flush();
			}
			
			out.println("<Server>: 서버 접속을 해제하겠습니다.");
			out.flush();
			
			//사용된 리소스 해제
			out.close();
			br.close();
			socket.close();
			server.close();
			System.out.println("서버프로그램 종료!!");
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
