import java.util.HashMap;

import Bridge.StateDiagramBridge;
import Bridge.StateDiagram_V1_Bridge;
import Model.Transition_V1;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class StateController {
	StateDiagramBridge stateBridge;
	Stage stage;
	StateMachineView stateMachineView;
	ArrowLineView currentArrowLineView;
	//記錄arrow的元件與相對應的model
	HashMap<ArrowLineView,Transition_V1> arrowMap=new HashMap<ArrowLineView,Transition_V1>();
	
	
	StateController(Stage stage){
		this.stage=stage;
		stateBridge=new StateDiagram_V1_Bridge();
	}
	void start() throws Exception {
		stateMachineView=StateMachineView.getInstance();
		stateMachineView.start(stage);
		
		//register event
		stateMachineView.addActionForTransitionBtn(new CreateTransitionAction());
		
	}
	
	
	//Event Class
	
	//新增Transition
	class CreateTransitionAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			//default transition position
			final double points[]= {100, 10, 300, 10};
			Transition_V1 arrowModel=stateBridge.createTransition();
			ArrowLineView arrowView=stateMachineView.createTransition(points[0],points[1],points[2],points[3]);
			double arrowPoint[]=arrowModel.repaintArrow(points[0],points[1],points[2],points[3]);
			arrowView.repaint(arrowPoint[0],arrowPoint[1],arrowPoint[2],arrowPoint[3]);
				
			//register move line
			arrowView.addMoveForLineEvent(new MoveTransitionAction());
			//register controll start and end size
			arrowView.addMoveForStartCircleEvent(new ResizeStartTransitionAction());
			arrowView.addMoveForEndCircleEvent(new ResizeEndTransitionAction());
			
			//register arrowLine
			arrowView.addEventHandler(MouseEvent.MOUSE_PRESSED, new selectTransitionAction());
			arrowView.addEventHandler(MouseEvent.MOUSE_RELEASED, new selectTransitionAction());
			
			arrowMap.put(arrowView,arrowModel);
		}
	}
	//取得被選取的ArrowLine
	class selectTransitionAction implements EventHandler{
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(eventType.equals(MouseEvent.MOUSE_PRESSED)) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				double ox=((ArrowLineView) e.getSource()).getTranslateX();
				double oy=((ArrowLineView) e.getSource()).getTranslateY();
				currentArrowLineView=(ArrowLineView)(e.getSource());	
				Transition_V1 arrowModel=arrowMap.get(currentArrowLineView);
				arrowModel.draggedMoveFrom(mx, my,ox,oy);
			}
			else if(eventType.equals(MouseEvent.MOUSE_RELEASED))
				currentArrowLineView=null;
		}
	}
	//拖曳Transition
	class MoveTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentArrowLineView!=null && e.getSource() instanceof Line) {
				Transition_V1 arrowModel=arrowMap.get(currentArrowLineView);
				EventType eventType=e.getEventType();
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();							
				if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=arrowModel.draggedMoveTo(mx, my);
					currentArrowLineView.setTranslateX(point[0]);
					currentArrowLineView.setTranslateY(point[1]);
				}
			}
		}
	}
	//調整Transition start
	class ResizeStartTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentArrowLineView!=null && e.getSource() instanceof Circle) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				currentArrowLineView.repaintStartCircle(mx, my);
				
				Transition_V1 arrowModel=arrowMap.get(currentArrowLineView);
				double ex=currentArrowLineView.line.getEndX();
				double ey=currentArrowLineView.line.getEndY();
				double arrowPoint[]=arrowModel.repaintArrow(mx, my, ex, ey);
				currentArrowLineView.repaint(arrowPoint[0],arrowPoint[1],arrowPoint[2],arrowPoint[3]);
			}
		}
	}
	//調整Transition end
	class ResizeEndTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentArrowLineView!=null && e.getSource() instanceof Circle) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();		
				currentArrowLineView.repaintEndCircle(mx, my);
				
				Transition_V1 arrowModel=arrowMap.get(currentArrowLineView);
				double sx=currentArrowLineView.line.getStartX();
				double sy=currentArrowLineView.line.getStartY();
				double arrowPoint[]=arrowModel.repaintArrow(sx,sy,mx, my);
				currentArrowLineView.repaint(arrowPoint[0],arrowPoint[1],arrowPoint[2],arrowPoint[3]);
			}
		}
	}
}


