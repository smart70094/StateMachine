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
import javafx.scene.text.Text;
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
			Transition_V1 arrowModel=stateBridge.createTransition();
			arrowModel.repaintArrow();
			
			ArrowLineView arrowView=stateMachineView.createTransition(arrowModel.mainLine);
			arrowView.repaintArrow(arrowModel.arrow1,arrowModel.arrow2);
			arrowView.setLayoutX(arrowModel.x);
			arrowView.setLayoutY(arrowModel.y);
			
				
			//register arrowLine
			arrowView.addEventHandler(MouseEvent.MOUSE_PRESSED, new selectTransitionAction());
			arrowView.addEventHandler(MouseEvent.MOUSE_RELEASED, new selectTransitionAction());
			
			//register move line
			arrowView.addMoveForLineEvent(new MoveTransitionAction());
			//register controll start and end size
			arrowView.addMoveForStartCircleEvent(new ResizeStartTransitionAction());
			arrowView.addMoveForEndCircleEvent(new ResizeEndTransitionAction());
			
			//register text
			arrowView.addMoveForTextEvent(new MoveTextAction());
			
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
					
					arrowModel.x=currentArrowLineView.getLayoutX();
					arrowModel.y=currentArrowLineView.getLayoutY();
				}
			}
		}
	}
	//拖曳Text
	class MoveTextAction implements EventHandler{
		//記錄當Text第一次被按的坐標
		double lx,ly;
		boolean isFirst=false;
		
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(e.getSource() instanceof Text && !isFirst && eventType.equals(MouseEvent.MOUSE_PRESSED)) {
				double ox=((Text) e.getSource()).getTranslateX();
				double oy=((Text) e.getSource()).getTranslateY();
				lx=ox;
				ly=oy;
				isFirst=true;
			}
			if(currentArrowLineView!=null  && e.getSource() instanceof Text) {
				Transition_V1 arrowModel=arrowMap.get(currentArrowLineView);
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				//設定model拖曳的起始點
				if(isFirst) {
					arrowModel.textModel.draggedMoveFrom(mx, my,lx,ly);
					isFirst=false;
				}
				
				if(eventType.equals(MouseEvent.MOUSE_CLICKED)) {			
					stateMachineView.showIinputDialog();
				}else if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=arrowModel.textModel.draggedMoveTo(mx, my);
					currentArrowLineView.nameText.setTranslateX(point[0]);
					currentArrowLineView.nameText.setTranslateY(point[1]);
				}	
			}
			
		}
	}
	//調整Transition start
	class ResizeStartTransitionAction implements EventHandler{
		double lx,ly;
		boolean isFirst=false;
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(e.getSource() instanceof Circle && eventType.equals(MouseEvent.MOUSE_PRESSED)) {
				double ox=((Circle) e.getSource()).getTranslateX();
				double oy=((Circle) e.getSource()).getTranslateY();
				lx=ox;
				ly=oy;
				isFirst=true;
			}
			
			if(currentArrowLineView!=null && e.getSource() instanceof Circle) {
				Transition_V1 arrowModel=arrowMap.get(currentArrowLineView);
				
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				if(isFirst) {
					arrowModel.textModel.draggedMoveFrom(mx, my,lx,ly);
					isFirst=false;
				}
				double point[]=arrowModel.textModel.draggedMoveTo(mx, my);
				currentArrowLineView.repaintStartCircle(point[0], point[1],mx,my);
	
				arrowModel.mainLine.sx=currentArrowLineView.line.getStartX();
				arrowModel.mainLine.sy=currentArrowLineView.line.getStartY();
				arrowModel.mainLine.ex=currentArrowLineView.line.getEndX();
				arrowModel.mainLine.ey=currentArrowLineView.line.getEndY();
				arrowModel.repaintArrow();
				currentArrowLineView.repaintArrow(arrowModel.arrow1,arrowModel.arrow2);
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
				arrowModel.mainLine.ex=mx;
				arrowModel.mainLine.ey=my;
				arrowModel.repaintArrow();
				currentArrowLineView.repaintArrow(arrowModel.arrow1,arrowModel.arrow2);
			}
		}
	}
}


