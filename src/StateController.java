import java.util.HashMap;
import Bridge.ClientBridge;
import Bridge.StateDiagram_V1_Bridge;
import Bridge.StateDiagram_V2_Bridge;
import Model.DiagramElement;
import Model.State;
import Model.StateDiagram;
import Model.Transition;
import ViewModel.ArrowLineView;
import ViewModel.StateDiagramView;
import ViewModel.StateView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class StateController {
	ClientBridge clientBridge;
	Stage stage;
	StateMachineView stateMachineView;
	ArrowLineView currentTransition;
	StateView currentState;
	StateDiagramView currentStateDiagram;
	DiagramElement stateDiagram;
	
	//記錄元件與相對應的model
	HashMap<ArrowLineView,Transition> arrowMap=new HashMap<ArrowLineView,Transition>();
	HashMap<StateView,State> stateMap=new HashMap<StateView,State>();
	HashMap<StateDiagramView,StateDiagram> stateDiagramMap=new HashMap<StateDiagramView,StateDiagram>();
	
	StateController(Stage stage){
		this.stage=stage;
		clientBridge=new ClientBridge(new StateDiagram_V2_Bridge());
	}
	void start() throws Exception {
		stateMachineView=StateMachineView.getInstance();
		stateMachineView.start(stage);
		//register event for each button
		stateMachineView.addActionForTransitionBtn(new CreateTransitionAction());
		stateMachineView.addActionStateBtn(new CreateStateAction());
		stateMachineView.addActionStateDiagramBtn(new CreateStateDiagramAction());
		
		stateDiagram=clientBridge.createStateDiagram();
		currentStateDiagram=createStateDiagram((StateDiagram)stateDiagram);
		stateDiagramMap.put(currentStateDiagram, (StateDiagram)stateDiagram);
	}
	
	
	//Event Class
	
	//新增Transition
	class CreateTransitionAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			Transition arrowModel=clientBridge.createTransition();	
			ArrowLineView arrowView=stateMachineView.createTransition(arrowModel);
				
			//register arrowLine
			arrowView.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectTransitionAction());
			arrowView.addEventHandler(MouseEvent.MOUSE_RELEASED, new SelectTransitionAction());
			
			//register move line
			arrowView.addMoveForTransitionEvent(new MoveTransitionElementAction());
			//register controll start and end size
			arrowView.addMoveForStartCircleEvent(new ResizeStartTransitionAction());
			arrowView.addMoveForEndCircleEvent(new ResizeEndTransitionAction());
			
			//register text
			arrowView.addMoveForTextEvent(new MoveAndRenameForTextAction());
			
			stateDiagram.add(arrowModel);
			arrowMap.put(arrowView,arrowModel);
			currentStateDiagram.getChildren().add(arrowView);
		}
	}
	
	//新增State
	class CreateStateAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			State state=clientBridge.createState();
			StateView stateView=stateMachineView.createState(state);
			stateView.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectStateAction());
			stateView.addEventHandler(MouseEvent.MOUSE_RELEASED, new SelectStateAction());
			stateView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new MoveStateAction());
			stateView.addRenameForTextEvent(new RenameForState());
			stateMap.put(stateView, state);
			stateDiagram.add(state);
			currentStateDiagram.getChildren().add(stateView);
			
		}
	}
	
	//新增StateDiagram
	class CreateStateDiagramAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			StateDiagram stateDiagram=clientBridge.createStateDiagram();
			StateDiagramView stateDiagramView=createStateDiagram(stateDiagram);
			stateDiagramMap.put(stateDiagramView, stateDiagram);
			stateDiagram.add(stateDiagram);
		}
	}
	@SuppressWarnings("unchecked")
	public StateDiagramView createStateDiagram(StateDiagram stateDiagram) {
		StateDiagramView stateDiagramView=stateMachineView.createStateDiagram(stateDiagram);
		stateDiagramView.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectStateDiagramAction());
		stateDiagramView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new MoveStateDiagramAction());
		stateDiagramView.addEventHandler(MouseEvent.MOUSE_CLICKED, new DoubleClickStateDiagramAction());
		stateDiagramView.addMoveForTextEvent(new RenameForStateDiagram());
		return stateDiagramView;
	}
	
	//取得被選取的Transtion
	class SelectTransitionAction implements EventHandler{
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(eventType.equals(MouseEvent.MOUSE_PRESSED)) {
				
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				double ox=((ArrowLineView) e.getSource()).getTranslateX();
				double oy=((ArrowLineView) e.getSource()).getTranslateY();
				currentTransition=(ArrowLineView)(e.getSource());	
				DiagramElement de=arrowMap.get(currentTransition);
				de.draggedMoveFrom(mx, my,ox,oy);
			}else if(eventType.equals(MouseEvent.MOUSE_RELEASED)) {
				currentTransition=null;
			}

				
		}
	}
	
	//取得被選取的State
	class SelectStateAction implements EventHandler{
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(eventType.equals(MouseEvent.MOUSE_PRESSED)) {
				currentState=null;
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				double ox=((StateView) e.getSource()).getTranslateX();
				double oy=((StateView) e.getSource()).getTranslateY();
				currentState=(StateView)(e.getSource());	
				DiagramElement de=stateMap.get(currentState);
				de.draggedMoveFrom(mx, my,ox,oy);
			}else if(eventType.equals(MouseEvent.MOUSE_RELEASED)) {
				currentState=null;
			}
		}
	}
	
	//取得被選取的StateDiagram
	class SelectStateDiagramAction implements EventHandler{
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(eventType.equals(MouseEvent.MOUSE_PRESSED)) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				double ox=((StateDiagramView) e.getSource()).getTranslateX();
				double oy=((StateDiagramView) e.getSource()).getTranslateY();
				currentStateDiagram=(StateDiagramView)(e.getSource());	
				DiagramElement de=stateDiagramMap.get(currentStateDiagram);
				de.draggedMoveFrom(mx, my,ox,oy);
				stateDiagram=de;
			}
		}
	}
	
	//對StateDiagram double click設定大小
	class DoubleClickStateDiagramAction implements EventHandler{
		public void handle(Event e) {
			MouseEvent mouseEvent=(MouseEvent)e;
			if(mouseEvent.getClickCount()>=2) {
				stateMachineView.showInputSizeDialog();
				int reSizeArr[]=stateMachineView.getReSize();
				int width=reSizeArr[0];
				int height=reSizeArr[1];
				System.out.println(width+","+height);
				currentStateDiagram.setSize(width, height);
			}
		}
	}
	
	//拖曳StateDiagram
	class MoveStateDiagramAction implements EventHandler{
		public void handle(Event e) {
			if(currentStateDiagram!=null && (currentState==null && currentTransition==null)) {
				DiagramElement de=stateDiagramMap.get(currentStateDiagram);
				EventType eventType=e.getEventType();
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();							
				if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=de.draggedMoveTo(mx, my);
					currentStateDiagram.setTranslateX(point[0]);
					currentStateDiagram.setTranslateY(point[1]);
					
					de.x=currentStateDiagram.getLayoutX();
					de.y=currentStateDiagram.getLayoutY();
				}
			}
		}
	}
	
	//拖曳Transtion
	class MoveTransitionElementAction implements EventHandler{
		public void handle(Event e) {
			if(currentTransition!=null && e.getSource() instanceof Line) {
				DiagramElement de=arrowMap.get(currentTransition);
				EventType eventType=e.getEventType();
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();							
				if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=de.draggedMoveTo(mx, my);
					currentTransition.setTranslateX(point[0]);
					currentTransition.setTranslateY(point[1]);
					
					de.x=currentTransition.getLayoutX();
					de.y=currentTransition.getLayoutY();
				}
			}
		}
	}
	
	//拖曳State
	class MoveStateAction implements EventHandler{
		public void handle(Event e) {
			if(currentState!=null && e.getSource() instanceof StateView) {
				DiagramElement de=stateMap.get(currentState);
				EventType eventType=e.getEventType();
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();							
				if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=de.draggedMoveTo(mx, my);
					currentState.setTranslateX(point[0]);
					currentState.setTranslateY(point[1]);
					
					de.x=currentState.getLayoutX();
					de.y=currentState.getLayoutY();
				}
			}
		}
	}
	
	
	// Transition Text 拖曳、改名
	class MoveAndRenameForTextAction implements EventHandler{
		//記錄當Text第一次被按的坐標
		double lx,ly;
		boolean isFirst=false;
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(e.getSource() instanceof Text) {
				Text nameText=((Text) e.getSource());
				//被點選時記錄初始坐標
				if(!isFirst && eventType.equals(MouseEvent.MOUSE_PRESSED)) {
					double ox=nameText.getTranslateX();
					double oy=nameText.getTranslateY();
					lx=ox;
					ly=oy;
					isFirst=true;
					//重新命名
				}else if (eventType.equals(MouseEvent.MOUSE_CLICKED)) {	
					String name=stateMachineView.showIinputDialog();
					nameText.setText(name);
					currentTransition=(ArrowLineView) nameText.getParent();
					DiagramElement de=arrowMap.get(currentTransition);				
					clientBridge.rename(name, de);
				} 
			}
			
			if(currentTransition!=null  && e.getSource() instanceof Text) {
				Transition arrowModel=arrowMap.get(currentTransition);
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				//設定model拖曳的起始點
				if(isFirst) {
					arrowModel.textModel.draggedMoveFrom(mx, my,lx,ly);
					isFirst=false;
				}
				if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=arrowModel.textModel.draggedMoveTo(mx, my);
					currentTransition.nameText.setTranslateX(point[0]);
					currentTransition.nameText.setTranslateY(point[1]);
				}else if(eventType.equals(MouseEvent.MOUSE_CLICKED)) {
					System.out.println("test");
				}
			}
			
		}
	}
	//更改State名稱
	class RenameForState implements EventHandler{
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(e.getSource() instanceof Text) {
				Text nameText=((Text) e.getSource());
				if (eventType.equals(MouseEvent.MOUSE_CLICKED)) {	
					String name=stateMachineView.showIinputDialog();
					nameText.setText(name);
					DiagramElement de=stateMap.get(currentState);
					clientBridge.rename(name, de);
				}
			}
			
			
		}
	}
	
	//更改StateDiagram名稱
	class RenameForStateDiagram implements EventHandler{
		public void handle(Event e) {
			EventType eventType=e.getEventType();
			if(e.getSource() instanceof Text) {
				Text nameText=((Text) e.getSource());
				if (eventType.equals(MouseEvent.MOUSE_CLICKED)) {	
					String name=stateMachineView.showIinputDialog();
					nameText.setText(name);
					DiagramElement de=stateDiagramMap.get(currentStateDiagram);
					clientBridge.rename(name, de);
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
			
			if(currentTransition!=null && e.getSource() instanceof Circle) {
				Transition arrowModel=arrowMap.get(currentTransition);
				
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				if(isFirst) {
					arrowModel.textModel.draggedMoveFrom(mx, my,lx,ly);
					isFirst=false;
				}
				double point[]=arrowModel.textModel.draggedMoveTo(mx, my);
				currentTransition.repaintStartCircle(point[0], point[1],mx,my);
	
				arrowModel.mainLine.sx=currentTransition.line.getStartX();
				arrowModel.mainLine.sy=currentTransition.line.getStartY();
				arrowModel.mainLine.ex=currentTransition.line.getEndX();
				arrowModel.mainLine.ey=currentTransition.line.getEndY();
				arrowModel.repaintArrow();
				currentTransition.repaintArrow(arrowModel.arrow1,arrowModel.arrow2);
			}
		}
	}
	//調整Transition end
	class ResizeEndTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentTransition!=null && e.getSource() instanceof Circle) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();		
				currentTransition.repaintEndCircle(mx, my);
				
				Transition arrowModel=arrowMap.get(currentTransition);
				double sx=currentTransition.line.getStartX();
				double sy=currentTransition.line.getStartY();
				arrowModel.mainLine.ex=mx;
				arrowModel.mainLine.ey=my;
				arrowModel.repaintArrow();
				currentTransition.repaintArrow(arrowModel.arrow1,arrowModel.arrow2);
			}
		}
	}
}


