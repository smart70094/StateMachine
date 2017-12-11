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
		
		l1=new Line();
		l1.setId("line");
		l2=new Line();
		l2.setId("line");
		
		this.getChildren().add(line);
		this.getChildren().add(l1);
		this.getChildren().add(l2);
		this.getChildren().add(startCircle);
		this.getChildren().add(endCircle);
		repaint(sx,sy,ex,ey);
	}
	
	@SuppressWarnings("unchecked")
	void addMoveForStartCircleEvent(EventHandler e) {
		startCircle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
	}
	@SuppressWarnings("unchecked")
	void addMoveForEndCircleEvent(EventHandler e) {
		endCircle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e);
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
	void repaintEndCircle(double sx,double sy) {
		line.setEndX(sx);
		line.setEndY(sy);
		endCircle.setLayoutX(sx);
		endCircle.setLayoutY(sy);
	}
	void repaint(double x1,double y1,double x2,double y2) {
		l1.setStartX(line.getEndX());
		l1.setStartY(line.getEndY());
		l1.setEndX(x1);
		l1.setEndY(y1);
		
		l2.setStartX(line.getEndX());
		l2.setStartY(line.getEndY());
		l2.setEndX(x2);
		l2.setEndY(y2);
		
	}
}
