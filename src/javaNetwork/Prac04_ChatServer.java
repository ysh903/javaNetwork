package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;




public class Prac04_ChatServer extends Application {

	TextArea textarea; //메세지 창
	Button startBtn,stopBtn; // 서버 시작,종료 버튼
	ServerSocket server; // 클라이언트의 접속을 받아들임
	ExecutorService executorService = 
			Executors.newCachedThreadPool(); // thread pool
	// singleton 형태의 공유객체를 생성
	SharedObject sharedObject = new SharedObject();
	int roomNum=1;
	
	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	// 클라이언트와 연결된 Thread가 사용하는 공유객체를 만들기 위한 클래스를 선언
	// inner class 형태로 선언 (사용하기 편함)
	// Thread의 공유 객체는 Thread가 가지고 있어야 하는 자료구조,
	// 기능을 구현해 놓은 객체를 지칭
	class SharedObject{
		//클라이언트 Thread를 저장하고 있어야 함
		List<ClientRunnable> clients =
				new ArrayList<ClientRunnable>();
		
		
		// 우리가 필요한 기능은 Broadcast
		//thread가 클라이언트로부터 데이터를 받아서 모든 클라이언트 Thread에게
		//데이터를 전달하는 기능을 구현
		// 공유 객체의 method는 여러 thread에 의해서 동시에 사용될 수 있음.
		//이런 경우에는 동기화 처리를 해줘야 문제없이 출력 될 수 있음.
		public synchronized void broadcast (String msg) {
			clients.stream().forEach(t->{
				t.out.println(msg);
				t.out.flush();
			});
		}
		
	}
	// 클라이언트와 매핑되는 Thread를 만들기 위한 runnable class
	class ClientRunnable implements Runnable {
		
		private SharedObject sharedObject; // 공유객체
		private Socket socket; //client와 연결된 소켓
		private BufferedReader br;
		private PrintWriter out;
		
		// 클라이언트가 서버에 접속해서 해당 클라이언트에 대한 Thread가 생성될 때
		//Thread에는 2 개의 객체가 전될 되어야 함
		//생성자를 2개의 인자(공유객체와 소캣)를 받는 형태로 작성
		//일반적으로 생성자는 초기화를 담당하기 때문에 여기에서 stream을 생성
		
		
		
		public ClientRunnable(SharedObject sharedObject, 
				Socket socket) {
			super();
			this.sharedObject = sharedObject;
			this.socket = socket;
			try {
				this.br = 
						new BufferedReader(new InputStreamReader(
								socket.getInputStream()));
				this.out = 
						 new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		// 클라이언트의 쓰레드가 시작되면 run 메소드가 호출되서 클아이언트와 통신시작
		//반복적으로 클라이언트의 데이터를 받아서 공유객체를 이용해서 broadcasting
		@Override
		public void run() {
			String msg = "";
			try {
				while((msg=br.readLine())!=null) {
					//클라이언트가 채팅을 종료하면
					if (msg.equals("/EXIT/")) {
						break;
					}
					//일반적인 채팅 메세지인 경우 모든 클라이언트에게 전달
					sharedObject.broadcast(msg);
				}
			} catch(Exception e){
				System.out.println(e);
			}
			
		}
	
		
	}

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

		startBtn = new Button("채팅서버 시작!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			textarea.clear();
			printMsg("[채팅서버 기동-6789]");
			//서버 소켓을 만들어서 클라이언트 접속을 기다려야 함
			// javaFX thread가 blocking되지 않도록 새로운 Thread를 만들어서
			// 클라이언트 접속을 기다려야 함.
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						server = new ServerSocket(6789);
						while(true) {
							printMsg("[클라이언트 접속 대기중]");
							Socket socket = server.accept();
							printMsg("[클라이언트 접속 성공]");
							//클라이언트가 접속했으니 Thread하나 생성 및 실행
							ClientRunnable cRunnable = 
									new ClientRunnable(sharedObject, socket);
							//새로운 클라이언트가 접속되었으니
							//공용객체의 list 넣기
							
							sharedObject.clients.add(cRunnable);
							printMsg("[현재 클라이언트 수 : "+
							sharedObject.clients.size()+"]");
							executorService.execute(cRunnable);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			};
			executorService.execute(runnable);
		});
		
		stopBtn = new Button("채팅서버 종료!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
		});
		
	
		
		
		
		
		

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);

		// Scene객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("전체 서버");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(); // start method 시작

	}

}
