package Model;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Transition_V1 extends Transition{
	//拖移時記錄的坐標
	public Transition_V1() {
		mainLine=new LineModel();
		arrow1=new LineModel();
		arrow2=new LineModel();
		textModel=new TextModel();
	}
}
