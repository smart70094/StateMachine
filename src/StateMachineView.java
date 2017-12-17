import java.util.NoSuchElementException;
import java.util.Optional;

import Model.State;
import Model.StateDiagram;
import Model.Transition;
import OtherModel.LineModel;
import ViewModel.ArrowLineView;
import ViewModel.StateDiagramView;
import ViewModel.StateView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class StateMachineView extends Application{
	private AnchorPane root;
	private String css;
	private Scene scene;
	
	private Button transitionBtn;
	private Button stateBtn;
	private Button stateDiagramBtn;
	private Button undoBtn;
	
	private ImageView img;
	
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
		undoBtn=new Button("undo");
		undoBtn.setLayoutY(150);
		stateBtn.setLayoutY(50);
		stateDiagramBtn=new Button("stateDiagram");
		stateDiagramBtn.setLayoutY(100);
		
		Image image = new Image(getClass().getResource("123.png").toString());
		img=new ImageView(image);
		img.setFitWidth(50);
		img.setFitHeight(50);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		img.setCache(true);
		img.setLayoutX(1080-50);
		img.setLayoutY(768-50);

		
		root.getChildren().add(undoBtn);
		root.getChildren().add(transitionBtn);
		root.getChildren().add(stateBtn);
		root.getChildren().add(stateDiagramBtn);
		root.getChildren().add(img);
		css=getClass().getResource("stylesheet.css").toExternalForm();
		scene=new Scene(root,1080,768);
	}
	
	ArrowLineView createTransition(Transition transition) {
		ArrowLineView arrowLineView=new ArrowLineView(transition);
		return arrowLineView;
	}
	
	StateView createState(State state) {
		StateView stateView=new StateView(state);
		return stateView;
	}
	StateDiagramView createStateDiagram(StateDiagram stateDiagram,StateDiagramView lastStateDiagram) {
		StateDiagramView stateDiagramView=new StateDiagramView(stateDiagram,lastStateDiagram);
		root.getChildren().add(stateDiagramView);
		return stateDiagramView;
	}
	void removeStateDiagram(StateDiagramView stateDiagramView) {
		//StateDiagramView lastStateDiagram=stateDiagramView.lastStateDiagram;
		root.getChildren().remove(stateDiagramView);
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
	@SuppressWarnings("unchecked")
	void addActionStateDiagramBtn(EventHandler e) {
		stateDiagramBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	@SuppressWarnings("unchecked")
	void addActionRemoveDiagram(EventHandler e) {
		img.addEventHandler(MouseEvent.MOUSE_ENTERED, e);
	}
	@SuppressWarnings("unchecked")
	void addActionUndoBtn(EventHandler e) {
		undoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	
	
	//Dialog
	String showIinputDialog() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Message");
		dialog.setHeaderText("請輸入名稱");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		try {
			return result.get();
		}catch(NoSuchElementException e) {
			return null;
		}
	}
	
	String reSizeStr="";
	void showInputSizeDialog() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Message");
		dialog.setHeaderText("請輸入 State Diagram 大小");


		// Set the button types.
		ButtonType loginButtonType = new ButtonType("確定", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField width = new TextField();
		width.setPromptText("Width");
		TextField height = new TextField();
		height.setPromptText("Height");

		grid.add(new Label("Width:"), 0, 0);
		grid.add(width, 1, 0);
		grid.add(new Label("Height:"), 0, 1);
		grid.add(height, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		width.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> width.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(width.getText(), height.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(set -> {
			 reSizeStr=set.getKey() + ","+set.getValue();
		});
	}
	public int[] getReSize(){
		if(!reSizeStr.equals("")) {
			String strArr[]=reSizeStr.split(",");
			int width=Integer.parseInt(strArr[0]);
			int height=Integer.parseInt(strArr[1]);
			int result[]= {width,height};
			return result;
		}
		return null;
	}
	
	boolean showCheckRemoveDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Message");
		alert.setHeaderText("你確定要刪除?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true;
		} else {
		    return false;
		}
	}
}
