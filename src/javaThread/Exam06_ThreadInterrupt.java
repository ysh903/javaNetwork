package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam06_ThreadInterrupt extends Application {
	
	TextArea textarea;
	Button startBtn, stopBtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		//textarea에 문자열 출력하는 method
		Platform.runLater(()->{
			textarea.appendText(msg+"/");
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
		
		startBtn = new Button("Thread Start");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//버튼에서 액션이 발생(클릭)했을 때 호출
			counterThread = new Thread(()->{
				try {
					for(int i=0;i<10;i++) {
						Thread.sleep(1000);
						printMsg(i+"-"+Thread.currentThread().getName());
					}
				} catch (Exception e) {
					
					//만약 interrupt가 걸려있는 상태에서 block 상태로 진입하면
					//exception을 내면서 catch문으로 이동.
					printMsg("Thread가 종료되었어요.");
				}
			});
			counterThread.start();
			
			
		});
		
		stopBtn = new Button("Thread Stop");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			//버튼에서 액션이 발생(클릭)했을 때 호출
			counterThread.interrupt(); // method가 실행된다고 바로 thread가 종료되지 않음.
			// interrupt() method가 호출된 thread는 sleep과 같이 
			//block 상태에 들어가야 interrupt를 시킬 수 있음.
			
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
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
