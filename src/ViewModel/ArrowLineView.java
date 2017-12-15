package ViewModel;

import Model.Transition;
import OtherModel.LineModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ArrowLineView extends Group {
	Circle startCircle, endCircle;
	public Line line;
	Line l1;
	Line l2;
	public Text nameText;

	public ArrowLineView(Transition transition) {
		double sx = transition.mainLine.sx;
		double sy = transition.mainLine.sy;
		double ex = transition.mainLine.ex;
		double ey = transition.mainLine.ey;

		line = new Line(sx, sy, ex, ey);
		line.setId(transition.style);
		line.setLayoutX(this.getLayoutX());
		line.setLayoutY(this.getLayoutY());

		startCircle = new Circle(5);
		startCircle.setLayoutX(sx);
		startCircle.setLayoutY(sy);
		startCircle.setId("terminalPoint");

		endCircle = new Circle(5);
		endCircle.setLayoutX(ex);
		endCircle.setLayoutY(ey);
		endCircle.setId("terminalPoint");

		l1 = new Line();
		l1.setId(transition.style);
		l2 = new Line();
		l2.setId(transition.style);

		nameText = new Text(transition.name);
		nameText.setTranslateX(100);
		nameText.setTranslateY(-5);
		// nameText.setMinWidth(70);
		// nameText.setDisable(true);

		repaintArrow(transition.arrow1, transition.arrow2);

		this.setLayoutX(transition.x);
		this.setLayoutY(transition.y);

		this.getChildren().add(line);
		this.getChildren().add(l1);
		this.getChildren().add(l2);
		this.getChildren().add(nameText);
		this.getChildren().add(startCircle);
		this.getChildren().add(endCircle);
	}

	@SuppressWarnings("unchecked")
	public void addMoveForStartCircleEvent(EventHandler e) {
		startCircle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
		startCircle.addEventHandler(MouseEvent.MOUSE_PRESSED, e);
	}

	@SuppressWarnings("unchecked")
	public void addMoveForEndCircleEvent(EventHandler e) {
		endCircle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
	}

	@SuppressWarnings("unchecked")
	public void addMoveForTransitionEvent(EventHandler e) {
		line.addEventHandler(MouseEvent.MOUSE_PRESSED, e);
		line.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
	}

	@SuppressWarnings("unchecked")
	public void addMoveForTextEvent(EventHandler e) {
		nameText.addEventHandler(MouseEvent.MOUSE_PRESSED, e);
		nameText.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
		nameText.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
	}

	public void repaintStartCircle(double sx, double sy, double mx, double my) {
		startCircle.setTranslateX(sx);
		startCircle.setTranslateY(sy);
		line.setStartX(mx - this.getLayoutX());
		line.setStartY(my - this.getLayoutY());

	}

	public void repaintEndCircle(double sx, double sy) {
		line.setEndX(sx);
		line.setEndY(sy);
		endCircle.setLayoutX(sx);
		endCircle.setLayoutY(sy);
	}

	public void repaintArrow(LineModel arrow1, LineModel arrow2) {
		l1.setStartX(arrow1.sx);
		l1.setStartY(arrow1.sy);
		l1.setEndX(arrow1.ex);
		l1.setEndY(arrow1.ey);

		l2.setStartX(arrow2.sx);
		l2.setStartY(arrow2.sy);
		l2.setEndX(arrow2.ex);
		l2.setEndY(arrow2.ey);
	}
}
