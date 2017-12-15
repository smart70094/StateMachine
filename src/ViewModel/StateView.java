package ViewModel;

import Model.State;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class StateView extends Group{
	Circle circle;
	Text nameText;
	public StateView(State state) {
		circle=new 	Circle(state.radius);
		circle.setLayoutX(state.x);
		circle.setLayoutY(state.y);
		circle.setId(state.style);
		nameText=new Text(state.name);
		nameText.setLayoutX(circle.getLayoutX());
		nameText.setLayoutY(circle.getLayoutY());
		
		this.getChildren().add(circle);
		this.getChildren().add(nameText);
	}
	@SuppressWarnings("unchecked")
	public void addRenameForTextEvent(EventHandler e) {
		nameText.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}
	

}
