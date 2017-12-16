package ViewModel;

import Model.StateDiagram;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class StateDiagramView extends Group{
	public AnchorPane pane;
	public StateDiagramView lastStateDiagram;
	Text nameText;
	public StateDiagramView(StateDiagram stateDiagram,StateDiagramView lastStateDiagram) {
		this.lastStateDiagram=lastStateDiagram;
		pane=new AnchorPane();
		pane.setPrefWidth(stateDiagram.width);
		pane.setPrefHeight(stateDiagram.height);
		pane.setLayoutX(stateDiagram.x);
		pane.setLayoutY(stateDiagram.y);
		pane.setId(stateDiagram.style);
		
		nameText=new Text(stateDiagram.name);
		nameText.setLayoutX(pane.getLayoutX()+stateDiagram.width/2);
		nameText.setTranslateY(pane.getLayoutY()-5);
		
		this.getChildren().add(pane);
		this.getChildren().add(nameText);
	}
	public void setSize(int width,int height) {
		pane.setPrefWidth(width);
		pane.setPrefHeight(height);
		
		nameText.setLayoutX(pane.getLayoutX()+pane.getPrefWidth()/2);
		nameText.setTranslateY(pane.getLayoutY()-5);
		
	}
	
	@SuppressWarnings("unchecked")
	public void addMoveForTextEvent(EventHandler e) {
		nameText.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}

}
