import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ArrowLineView extends Group{
	Circle startCircle,endCircle;
	Line line,l1,l2;
	ArrowLineView(double sx,double sy,double ex,double ey){
		line=new Line(sx,sy,ex,ey);
		line.setId("line");
		
		startCircle=new Circle(5);
		startCircle.setLayoutX(sx);
		startCircle.setLayoutY(sy);
		startCircle.setId("terminalPoint");
		
		endCircle=new Circle(5);
		endCircle.setLayoutX(ex);
		endCircle.setLayoutY(ey);
		endCircle.setId("terminalPoint");
		
		this.getChildren().add(line);
		this.getChildren().add(startCircle);
		this.getChildren().add(endCircle);
	}
	
	@SuppressWarnings("unchecked")
	void addMoveForStartCircleEvent(EventHandler e) {
		startCircle.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
		startCircle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
		startCircle.addEventHandler(MouseEvent.MOUSE_RELEASED, e);
	}
	@SuppressWarnings("unchecked")
	void addMoveForLineEvent(EventHandler e) {
		line.addEventHandler(MouseEvent.MOUSE_PRESSED, e);
		line.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
	}
	void repaintStartCircle(double sx,double sy) {
		line.setStartX(sx);
		line.setStartY(sy);
		startCircle.setLayoutX(sx);
		startCircle.setLayoutY(sy);
	}
}
