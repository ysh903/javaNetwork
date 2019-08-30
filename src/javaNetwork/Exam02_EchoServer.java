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

public class Exam02_EchoServer {

	public static void main(String[] args) {
		
		ServerSocket server=null;
		Socket socket = null;
		
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
			String msg=br.readLine();
			
			out.println(msg);
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
