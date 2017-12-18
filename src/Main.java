import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String args[]) {
		launch(args);
	}
	public void start(Stage stage) throws Exception {
		StateController controller=new StateController(stage);
		controller.start();
		
		
	}

}
