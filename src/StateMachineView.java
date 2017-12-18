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
import javafx.scene.control.ChoiceDialog;
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
	
	private ImageView transitionBtn;
	private ImageView stateBtn;
	private ImageView stateDiagramBtn;
	private ImageView undoBtn;
	private ImageView redoBtn;
	private ImageView versionBtn;
	private ImageView infoBtn;
	private ImageView orange;
	private ImageView black;
	
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
		Image blackImage = new Image(getClass().getResourceAsStream("/black1.png"));
		black = new ImageView(blackImage);
		black.setLayoutX(-56);
		black.setLayoutY(511);
		
		
		Image orangeImage = new Image(getClass().getResourceAsStream("/orange.png"));
		orange = new ImageView(orangeImage);
		orange.setLayoutX(239);
		orange.setLayoutY(-55);
		
		Image undoImage = new Image(getClass().getResourceAsStream("/Undo.png"));
		undoBtn=new ImageView(undoImage);
		undoBtn.setLayoutX(-1);
		undoBtn.setLayoutY(0);
		undoBtn.setFitWidth(120);
		undoBtn.setFitHeight(60);
		
		Image redoImage = new Image(getClass().getResourceAsStream("/Redo.png"));
		redoBtn=new ImageView(redoImage);
		redoBtn.setLayoutX(119);
		redoBtn.setLayoutY(0);
		redoBtn.setFitWidth(120);
		redoBtn.setFitHeight(60);
		
		Image versionImage = new Image(getClass().getResourceAsStream("/version.png"));
		versionBtn = new ImageView(versionImage);
		versionBtn.setLayoutY(61);
		versionBtn.setFitHeight(89);	
		versionBtn.setFitWidth(240);
		
		Image stateDiagramImage = new Image(getClass().getResourceAsStream("/state Actions.png"));
		stateDiagramBtn=new ImageView(stateDiagramImage);
		stateDiagramBtn.setLayoutY(151);
		stateDiagramBtn.setFitHeight(89);	
		stateDiagramBtn.setFitWidth(240);
				
		Image stateImage = new Image(getClass().getResourceAsStream("/State.png"));
		stateBtn=new ImageView(stateImage);
		stateBtn.setLayoutY(241);
		stateBtn.setFitHeight(89);
		stateBtn.setFitWidth(240);
		
		Image trainsitionImage = new Image(getClass().getResourceAsStream("/Transition.png"));
		transitionBtn=new ImageView(trainsitionImage);
		transitionBtn.setLayoutY(331);
		transitionBtn.setFitHeight(89);
		transitionBtn.setFitWidth(240);
			
		Image informationImage = new Image(getClass().getResourceAsStream("/information.png"));
		infoBtn=new ImageView(informationImage);
		infoBtn.setLayoutY(421);
		infoBtn.setFitHeight(89);
		infoBtn.setFitWidth(240);
		
		Image image = new Image(getClass().getResource("trash.png").toString());
		img=new ImageView(image);
		img.setFitWidth(50);
		img.setFitHeight(50);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		img.setCache(true);
		img.setLayoutX(1080-50);
		img.setLayoutY(768-50);

		root.getChildren().add(infoBtn);
		root.getChildren().add(undoBtn);
		root.getChildren().add(redoBtn);
		root.getChildren().add(versionBtn);
		root.getChildren().add(transitionBtn);
		root.getChildren().add(stateBtn);
		root.getChildren().add(stateDiagramBtn);
		root.getChildren().add(img);
		root.getChildren().add(orange);
		root.getChildren().add(black);
		css=getClass().getResource("stylesheet.css").toExternalForm();
		scene=new Scene(root,1080,800);
	}
	
	ArrowLineView createTransition(Transition transition) {
		ArrowLineView arrowLineView=new ArrowLineView(transition);
		return arrowLineView;
	}
	
	StateView createState(State state) {
		StateView stateView=new StateView(state);
		return stateView;
	}
	public StateDiagramView createStateDiagram(StateDiagram stateDiagram,StateDiagramView lastStateDiagram) {
		StateDiagramView stateDiagramView=new StateDiagramView(stateDiagram,lastStateDiagram);
		root.getChildren().add(stateDiagramView);
		return stateDiagramView;
	}
	
	void removeStateDiagram(StateDiagramView stateDiagramView) {
		//StateDiagramView lastStateDiagram=stateDiagramView.lastStateDiagram;
		root.getChildren().remove(stateDiagramView);
	}
	
	StateDiagramView add(StateDiagramView stateDiagramView) {

		root.getChildren().add(stateDiagramView);
		
		return stateDiagramView;
	}
	
	//register event
	@SuppressWarnings("unchecked")
	void addActionUndoBtn(EventHandler e) {
		undoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	
	@SuppressWarnings("unchecked")
	void addActionRedoBtn(EventHandler e) {
		redoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	@SuppressWarnings("unchecked")
	void addActionForTransitionBtn(EventHandler e) {
		transitionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	@SuppressWarnings("unchecked")
	void addExitForTransition(EventHandler e){
		transitionBtn.addEventHandler(MouseEvent.MOUSE_EXITED, e);
	}
	
	@SuppressWarnings("unchecked")
	void addActionForVersionBtn(EventHandler e) {
		versionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
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
	void addActionInfoBtn(EventHandler e) {
		infoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	
	
	//Dialog
	String showIinputDialog() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Message");
		dialog.setHeaderText("Please input your name");

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
		dialog.setHeaderText("Please Input State Diagram Size");


		// Set the button types.
		ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
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
			reSizeStr=null;
			return result;
		}
		return null;
	}
	
	boolean showCheckRemoveDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Do you want to delete?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true;
		} else {
		    return false;
		}
	}
	String note;
	void showInputNoteDialog() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Message");
		dialog.setHeaderText("Please input note");
		

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			note= result.get();
		}


	}
	public String getNote() {
		showInputNoteDialog();
		return note;
	}
	
	
	//-----info
	Pair pair;
	void showInputInfoDialog() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Message");
		dialog.setHeaderText("Please input information");


		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		name.setPromptText("name");
		TextField note = new TextField();
		note.setPromptText("note");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Note:"), 0, 1);
		grid.add(note, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		/*Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		name.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});*/

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> name.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(name.getText(), note.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(set -> {
			//strInfo=set.getKey() + ","+set.getValue();
			pair=set;
		});
	}
	
	Pair getInfo() {
		showInputInfoDialog();
		/*String arr[]=strInfo.split(",");
		strInfo="";*/
		Pair t=pair;
		pair=null;
		return t;
		
	}
	
  //select the version dialog
	public String showSelectVersionDialog(ImageView imgBtn){
		final ChoiceDialog<String> choiceDialog = new ChoiceDialog("version1","version2");
		choiceDialog.setTitle("Select Version");
		choiceDialog.setHeaderText("");
		choiceDialog.setContentText("Choose Your Version");
		choiceDialog.showAndWait();
		Image img = new Image(getClass().getResourceAsStream("/version.png"));
		imgBtn.setImage(img);	
		return 	choiceDialog.getResult();
				
	}
	
	
  //show the information	
	public void showDisplayDialog(ImageView imgBtn,String context,String url){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("display note");
		alert.setContentText(context);
		
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Image img = new Image(getClass().getResourceAsStream(url));
			imgBtn.setImage(img);
		}
	}
  //change the imageBtn
	public void changeImageBtn(ImageView imgBtn,String context) {
		Image img = new Image(getClass().getResourceAsStream(context));
		imgBtn.setImage(img);
	}
}
