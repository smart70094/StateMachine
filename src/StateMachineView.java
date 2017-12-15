import java.util.Optional;

import Model.State;
import Model.Transition;
import OtherModel.LineModel;
import ViewModel.ArrowLineView;
import ViewModel.StateView;
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
	private Button stateBtn;
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
		stateBtn=new Button("State");
		stateBtn.setLayoutY(50);
		
		//
		root.getChildren().add(transitionBtn);
		root.getChildren().add(stateBtn);
		css=getClass().getResource("stylesheet.css").toExternalForm();
		scene=new Scene(root,1080,768);
	}
	
	ArrowLineView createTransition(Transition transition) {
		ArrowLineView arrowLineView=new ArrowLineView(transition);
		root.getChildren().add(arrowLineView);
		return arrowLineView;
	}
	
	StateView createState(State state) {
		StateView stateView=new StateView(state);
		root.getChildren().add(stateView);
		return stateView;
	}
	
	//register event
	@SuppressWarnings("unchecked")
	void addActionForTransitionBtn(EventHandler e) {
		transitionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	@SuppressWarnings("unchecked")
	void addActionStateBtn(EventHandler e) {
		stateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	
	String showIinputDialog() {
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText("Please enter your name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		return result.get();
		
	}
}
