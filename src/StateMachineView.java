import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	ArrowLineView createTransition(double sx,double sy,double ex,double ey) {
		ArrowLineView arrowLineView=new ArrowLineView(sx,sy,ex,ey);
		root.getChildren().add(arrowLineView);
		return arrowLineView;
	}
	
	//register event
	@SuppressWarnings("unchecked")
	void addActionForTransitionBtn(EventHandler e) {
		transitionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
}
