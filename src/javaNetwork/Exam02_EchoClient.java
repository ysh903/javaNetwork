package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam02_EchoClient extends Application {

	TextArea textarea;
	Button btn;

	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	TextField  tf;

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

		btn = new Button("EchoServer 접속!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {

			try {				
				socket = new Socket("127.0.0.1", 5557);				
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				 br = new BufferedReader(isr);
				 out = new PrintWriter(socket.getOutputStream());
				printMsg("Echo  서버 접속 성공!!");
				

			} catch (Exception e) {
				System.out.println(e);
			}

		});
		
		
		
		
		tf = new TextField();
		tf.setPrefSize(200,40);
		tf.setOnAction(t->{
			//입력상자에서 enter key가 입력되면 호출
			String msg = tf.getText();
		
			out.println(msg);//서버로 문자열 전송!
			out.flush();
			try {
				String result = br.readLine();
				printMsg(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(btn);
		flowpane.getChildren().add(tf);
		root.setBottom(flowpane);

		// Scene객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread  예제");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(); // start method 시작

	}

}
