package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

class EchoRunnable implements Runnable{
	// 가지고 있어야 할 필드
	Socket socket;//클라이언트와 연결된 소켓
	BufferedReader br; //입력을 위한 stream
	PrintWriter out; //출력을 위한 stream
	
	public EchoRunnable(Socket socket)  {
		super();
		this.socket = socket;
		try {
			this.br=new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		// 클라이언트와 echo처리 구현
		// 클라이언트가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 전달
		// 한번하고 종료하는 것이 아니라 클라이언트가 "/EXIT"라는 문자열을 보낼때까지 지속
		String line="";
		try {
			while((line=br.readLine())!=null) {
				if(line.equals("EXIT")) {
					break;// 가장 근접한 loop 탈출
				}else {
					out.println(line);
					out.flush();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


public class Exam03_EchoServerMultiClient extends Application {

	TextArea textarea;
	Button startBtn,stopBtn; //서버 시작, 서버 중지 버튼
	//thread pool 생성.
	ExecutorService executorService = Executors.newCachedThreadPool();
	//클라이언트 접속을 받아들이는 서버소켓
	ServerSocket server;
	
	
	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면 기본 layout 설정=>화면을 동서남북중앙(5개 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정=>화면에 띄우는 window크기설정
		root.setPrefSize(700, 500);

		// Component 생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);

		startBtn = new Button("EchoServer 접속!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			//서버프로그램 시작
			//클라이언트 접속 기다림->접속이 되면 thread하나 생섣
			//Thread를 시작해서 클라이언트와 thread가 통신하도록 만듦
			//서버는 다시 다른 클라이언트 접속을 가디람.
			//서버는 클라이언트 받을 준비하는 것이 중요. 서버 프로세스는 짧아야함.
			Runnable runnable=()->{
				try {
					server = new ServerSocket(7777);
					printMsg("Echo 서버 기동!!");
					while(true) {
						printMsg("클라이언트 접속 대기");
						
						Socket s = server.accept();
						printMsg("클라이언트 전속 성공");
						//클라이언트가 접속했으니 쓰레드 만들고 시작해야 함.
						EchoRunnable r = new EchoRunnable(s);
						executorService.execute(r);//thread 실행
						
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			
			
			executorService.execute(runnable);
			

		});
		
		stopBtn = new Button("EchoServer 해제!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			
			
		});
		
		

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);

		// Scene객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multi-client Echo Server");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(); // start method 시작

	}

}
