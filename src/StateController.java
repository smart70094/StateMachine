import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class StateController {
	StateFacade facade;
	Stage stage;
	StateMachineView stateMachineView;
	ArrowLineView currentArrowLineView;
	//記錄arrow的元件與相對應的model
	HashMap<ArrowLineView,ArrowLineModel> arrowMap=new HashMap<ArrowLineView,ArrowLineModel>();
	
	StateController(Stage stage){
		facade=new StateFacade();
		this.stage=stage;
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
			ArrowLineModel arrowModel=facade.createTransition(points[0],points[1],points[2],points[3]);
			ArrowLineView arrowView=stateMachineView.createTransition(points[0],points[1],points[2],points[3]);
			
			//register move line
			arrowView.addMoveForLineEvent(new MoveTransitionAction());
			//register controll start size
			arrowView.addMoveForStartCircleEvent(new ResizeStartTransitionAction());
			
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
				currentArrowLineView=(ArrowLineView)(e.getSource());	
			}
			else if(eventType.equals(MouseEvent.MOUSE_RELEASED))
				currentArrowLineView=null;
		}
	}
	//拖曳Transition
	class MoveTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentArrowLineView!=null && e.getSource() instanceof Line) {
				ArrowLineModel arrowModel=arrowMap.get(currentArrowLineView);
				EventType eventType=e.getEventType();
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				double ox=currentArrowLineView.getTranslateX();
				double oy=currentArrowLineView.getTranslateY();
				
				if(eventType.equals(MouseEvent.MOUSE_PRESSED)) {
					arrowModel.draggedMoveFrom(mx, my,ox,oy);
				}else if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=arrowModel.draggedMoveTo(mx, my);
					currentArrowLineView.setTranslateX(point[0]);
					currentArrowLineView.setTranslateY(point[1]);
				}
			}
		}
	}
	//調整Transition
	class ResizeStartTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentArrowLineView!=null && e.getSource() instanceof Circle) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();		
				currentArrowLineView.repaintStartCircle(mx, my);
			}
		}
	}
}


