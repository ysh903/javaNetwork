package javaThread;

import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 * sleep은 해당 시간만큼 쉬게 할 수 있으나, 끝난 후 실행시간은 불확정
 * runnable상태에 들어가기 때문에 스케쥴러에 따라 결정됨 Thread 순서가 계속 바뀜.
 * 
 * 
 */


public class Exam03_ThreadSleep extends Application {
	
	TextArea textarea;
	Button btn;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg+"\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//화면구성해서 window 띄우는 코드
		//화면 기본 layout 설정=>화면을 동서남북중앙(5개 영역)으로 분리
		BorderPane root = new BorderPane();
		//BorderPane의 크기를 설정=>화면에 띄우는 window크기설정
		root.setPrefSize(700, 500);
		
		//Component 생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			//버튼에서 액션이 발생(클릭)했을 때 호출
			// 1부터 5까지 5번 반복 -> for문 이용
			IntStream intStream = IntStream.rangeClosed(1, 5);
			intStream.forEach(value->{
				// 1부터 5까지 5번 반복하면서 Thread 생성
				Thread thread = new Thread(()-> {
					for(int i=0;i<5;i++) {
						try {
							Thread.sleep(3000);
							printMsg(i+" : "
							+Thread.currentThread().getName());
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}) ;
				thread.setName("ThreadNumber-"+value);
				thread.start();
			});

			
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		//Scene객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread  예제");
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(); //start method 시작

	}

}
