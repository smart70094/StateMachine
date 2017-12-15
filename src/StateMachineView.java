import java.util.Optional;

import Model.LineModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StateMachineView extends Application{
	private AnchorPane root;
	private String css;
	private Scene scene;
	
	private Button transitionBtn;
	static volatile StateMachineView stateView=null;
	public static StateMachineView getInstance() {
		if(stateView==null) {
			synchronized(StateMachineView.class){
				if(stateView==null)
					stateView=new StateMachineView();
			}
		}
		return stateView;
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(scene);
		scene.getStylesheets().add(css);
		primaryStage.show();
	}
	
	private StateMachineView() {
		root=new AnchorPane();
		//新增介面元件
		transitionBtn=new Button("Transition");
		
		//
		root.getChildren().add(transitionBtn);
		css=getClass().getResource("stylesheet.css").toExternalForm();
		scene=new Scene(root,1080,768);
	}
	
	ArrowLineView createTransition(LineModel line) {
		ArrowLineView arrowLineView=new ArrowLineView(line);
		root.getChildren().add(arrowLineView);
		return arrowLineView;
	}
	
	//register event
	@SuppressWarnings("unchecked")
	void addActionForTransitionBtn(EventHandler e) {
		transitionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	
	String showIinputDialog() {
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText("Please enter your name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your name: " + result.get());
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your name: " + name));
		return null;
		
	}
}
