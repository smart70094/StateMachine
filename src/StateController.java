import java.util.HashMap;
import Bridge.ClientBridge;
import Bridge.StateDiagram_V1_Bridge;
import Bridge.StateDiagram_V2_Bridge;
import Decorator.Decorator;
import Decorator.NoteDecorator;
import Memento.DiagramCareTaker;
import Memento.DiagramMemento;
import Memento.Record;
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
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class StateController {
	Stage stage;
	
	ClientBridge clientBridge;
	DiagramCareTaker diagramCareTaker;
	//view
	//主要介面
	StateMachineView2 stateMachineView;
	//選取中的transition
	ArrowLineView currentTransition;
	//選取中的transition
	StateView currentState;
	//操作中的StateDiagram View
	StateDiagramView currentStateDiagramView;
	
	//model
	//操作中的StateDiagram model
	
	StateDiagram stateDiagram;
	
	//root
	StateDiagram root;
	
	//記錄元件與相對應的model
	HashMap<ArrowLineView,Transition> arrowMap=new HashMap<ArrowLineView,Transition>();
	HashMap<StateView,State> stateMap=new HashMap<StateView,State>();
	HashMap<StateDiagramView,StateDiagram> stateDiagramMap=new HashMap<StateDiagramView,StateDiagram>();
	HashMap<DiagramElement,Decorator> decoratorMap=new HashMap<DiagramElement,Decorator>();
	
	StateController(Stage stage){
		this.stage=stage;
		clientBridge=new ClientBridge(new StateDiagram_V1_Bridge(new DiagramCareTaker()));
		diagramCareTaker=new DiagramCareTaker();
		root=new StateDiagram();
	}
	void start() throws Exception {
		stateMachineView=StateMachineView2.getInstance();
		stateMachineView.start(stage);
		//register event for each button
		stateMachineView.addActionForTransitionBtn(new CreateTransitionAction());
		stateMachineView.addActionStateBtn(new CreateStateAction());
		stateMachineView.addActionStateDiagramBtn(new CreateStateDiagramAction());
		stateMachineView.addActionUndoBtn(new UndoAction());
		stateMachineView.addActionRedoBtn(new RedoAction());
		//stateMachineView.addActionRedoBtn(new DisplayInfoAction());
		
		
		stateDiagram=clientBridge.createStateDiagram(root);
		currentStateDiagramView=createStateDiagram((StateDiagram)stateDiagram,null);
		stateDiagramMap.put(currentStateDiagramView, (StateDiagram)stateDiagram);
	}
	
	boolean detectCollision(DiagramElement de,double mx,double my) {
		if(de.detectCollision(mx, my)) {
			if(stateMachineView.showCheckRemoveDialog()) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	//儲存create、remove的狀態
	void save(String cmd,Group g,DiagramElement diagramElement) {
		Record r=new Record(cmd,currentStateDiagramView,g,diagramElement);
		diagramCareTaker.addDiagramViewMemento(r.createMemento());
	}
	//儲存重新命名的狀態
	void save(String cmd,Group g,String context) {
		Record r=new Record(cmd,g,context);
		diagramCareTaker.addDiagramViewMemento(r.createMemento());
	}
	
	void addNote(DiagramElement de,String note) {
		Decorator decorate=new NoteDecorator(de,note);
		decorate.printInfo();
		System.out.println(decorate.getInfo());
		decoratorMap.put(de,decorate);
	}

	//Event Class
	
	//新增Transition
	class CreateTransitionAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			Transition arrowModel=clientBridge.createTransition(stateDiagram);
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
			
			arrowMap.put(arrowView,arrowModel);
			currentStateDiagramView.getChildren().add(arrowView);
			save("createTransition",arrowView,arrowModel);
		}
	}
	
	//新增State
	class CreateStateAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			
			//stateDiagram=(StateDiagram) root.get(stateDiagram);
			State state=clientBridge.createState(stateDiagram);
			
			StateView stateView=stateMachineView.createState(state);
			stateView.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectStateAction());
			stateView.addEventHandler(MouseEvent.MOUSE_RELEASED, new SelectStateAction());
			stateView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new MoveStateAction());
			
			stateView.addRenameForTextEvent(new RenameForState());
			stateMap.put(stateView, state);
			
			currentStateDiagramView.getChildren().add(stateView);
			save("createState",stateView,state);

			System.out.println(root.getInfo());
		}
	}
	
	//新增StateDiagram
	class CreateStateDiagramAction implements EventHandler{
		@SuppressWarnings("unchecked")
		public void handle(Event e) {
			StateDiagram sd=clientBridge.createStateDiagram(stateDiagram);
			StateDiagramView stateDiagramView=createStateDiagram(sd,currentStateDiagramView);
			stateDiagramView.lastStateDiagram=currentStateDiagramView;
			currentStateDiagramView=stateDiagramView;
			stateDiagramMap.put(stateDiagramView, sd);
			stateDiagram=sd;
			System.out.println(root.getInfo());
			save("createStateDiagram",stateDiagramView,sd);
		}
	}
	@SuppressWarnings("unchecked")
	public StateDiagramView createStateDiagram(StateDiagram stateDiagram,StateDiagramView lastStateDiagramView) {
		StateDiagramView stateDiagramView=stateMachineView.createStateDiagram(stateDiagram,lastStateDiagramView);
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
				currentStateDiagramView=(StateDiagramView)(e.getSource());	
				DiagramElement de=stateDiagramMap.get(currentStateDiagramView);
				de.draggedMoveFrom(mx, my,ox,oy);
				stateDiagram=(StateDiagram) de;
			}
		}
	}
	
	//對StateDiagram double click設定大小
	class DoubleClickStateDiagramAction implements EventHandler{
		public void handle(Event e) {
			MouseEvent mouseEvent=(MouseEvent)e;
			MouseButton button = ((MouseEvent) e).getButton();
			if(button==MouseButton.SECONDARY) {
				stateMachineView.showInputSizeDialog();
				int reSizeArr[]=stateMachineView.getReSize();
				if(reSizeArr!=null) {
					int width=reSizeArr[0];
					int height=reSizeArr[1];
					System.out.println(width+","+height);
					currentStateDiagramView.setSize(width, height);
				}
			}
		}
	}
	
	//拖曳StateDiagram
	class MoveStateDiagramAction implements EventHandler{
		public void handle(Event e) {
			if(currentStateDiagramView!=null && (currentState==null && currentTransition==null)) {
				DiagramElement de=stateDiagramMap.get(currentStateDiagramView);
				EventType eventType=e.getEventType();
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();							
				if(eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
					double point[]=de.draggedMoveTo(mx, my);
					currentStateDiagramView.setTranslateX(point[0]);
					currentStateDiagramView.setTranslateY(point[1]);
					
					de.x=currentStateDiagramView.getLayoutX();
					de.y=currentStateDiagramView.getLayoutY();
					if(detectCollision(de,mx,my)) {
						save("removeStateDiagram",currentStateDiagramView,de);
						StateDiagramView t=currentStateDiagramView;
						stateMachineView.removeStateDiagram(currentStateDiagramView);
						currentStateDiagramView=t.lastStateDiagram;
						stateDiagramMap.remove(t);
						clientBridge.remove(root,de);
					}
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
					
					if(detectCollision(de,mx,my)) {
						save("removeTransition",currentTransition,de);
						currentStateDiagramView.getChildren().remove(currentTransition);
						clientBridge.remove(stateDiagram, de);
						arrowMap.remove(currentTransition);
						
					}
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
					if(detectCollision(de,mx,my)) {
						save("removeState",currentState,de);
						clientBridge.remove(root, de);
						currentStateDiagramView.getChildren().remove(currentState);
						stateMap.remove(currentState);		
					}
					
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
					if(name!=null) {
						String lastName=nameText.getText();
						nameText.setText(name);
						currentTransition=(ArrowLineView) nameText.getParent();
						DiagramElement de=arrowMap.get(currentTransition);				
						clientBridge.rename(name, de,root,stateDiagram);
						save("renameTransition",(ArrowLineView)nameText.getParent(),name+","+lastName);
					}
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
					stateMachineView.showInputNoteDialog();
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
					Pair pair=stateMachineView.getInfo();
					if(pair.getKey()!=null) {
						String name=(String) pair.getKey();
						if(!name.equals("")) {
							String lastName=nameText.getText();
							nameText.setText(name);
							DiagramElement de=stateMap.get(nameText.getParent());
							clientBridge.rename(name, de,root,stateDiagram);
							save("renameState",(StateView)nameText.getParent(),name+","+lastName);
						}
					}
					if(pair.getValue()!=null && !pair.getValue().equals("")){
						DiagramElement de=stateMap.get(nameText.getParent());
						addNote(de,(String)pair.getValue());
					}
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
				if (eventType.equals(MouseEvent.MOUSE_CLICKED) ) {	
					String name=stateMachineView.showIinputDialog();
					if(name!=null) {
						String lastName=nameText.getText();
						nameText.setText(name);
						DiagramElement de=stateDiagramMap.get(currentStateDiagramView);
						clientBridge.rename(name, de,root,stateDiagram);
						save("renameStateDiagram",(StateDiagramView)nameText.getParent(),name+","+lastName);
					}
				}
				
			}
		}
	}
	
	//調整Transition start
	class ResizeStartTransitionAction implements EventHandler{
		public void handle(Event e) {
			if(currentTransition!=null && e.getSource() instanceof Circle) {
				double mx=((MouseEvent) e).getSceneX();
				double my=((MouseEvent) e).getSceneY();
				currentTransition.repaintStartCircle(mx, my);
				
				Transition arrowModel=arrowMap.get(currentTransition);
				arrowModel.mainLine.sx=mx;
				arrowModel.mainLine.sy=my;
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
				arrowModel.mainLine.ex=mx;
				arrowModel.mainLine.ey=my;
				arrowModel.repaintArrow();
				currentTransition.repaintArrow(arrowModel.arrow1,arrowModel.arrow2);
			}
		}
	}
	
	//Undo
	class UndoAction implements EventHandler{
		public void handle(Event arg0) {
			try {
				DiagramMemento memento;
				StateDiagramView stateDiagramView;
				StateView stateView;
				ArrowLineView transitionView;
				String strArr[];
				String lastName;
				memento = diagramCareTaker.getDiagramViewUndoMemento();
				if(memento!=null) {
					Record record=(Record) memento.get();
					String cmd=record.getCmd();
					switch(cmd) {
						case "createState":
							stateView=(StateView)record.getNode();
							stateDiagramView=(StateDiagramView)record.getParentNode();
							stateDiagramView.getChildren().remove(stateView);
							stateMap.remove(stateView);
							break;
						case "createTransition":
							transitionView=(ArrowLineView)record.getNode();
							stateDiagramView=(StateDiagramView)record.getParentNode();
							stateDiagramView.getChildren().remove(transitionView);
							arrowMap.remove(transitionView);
							break;
						case "createStateDiagram":
							stateDiagramView=(StateDiagramView)record.getNode();
							currentStateDiagramView=stateDiagramView.lastStateDiagram;
							stateMachineView.removeStateDiagram(stateDiagramView);
							stateDiagramMap.remove(stateMachineView);
							stateDiagram=stateDiagramMap.get(currentStateDiagramView);
							break;
						case "renameState":
							stateView=(StateView)record.getNode();
							strArr=record.getContext().split(",");
							lastName=strArr[1];
							stateView.nameText.setText(lastName);
							break;
						case "renameTransition":
							transitionView=(ArrowLineView)record.getNode();
							strArr=record.getContext().split(",");
							lastName=strArr[1];
							transitionView.nameText.setText(lastName);
							break;
						case "renameStateDiagram":
							stateDiagramView=(StateDiagramView)record.getNode();
							strArr=record.getContext().split(",");
							lastName=strArr[1];
							stateDiagramView.nameText.setText(lastName);
							break;
						case "removeStateDiagram":
							currentStateDiagramView=stateMachineView.add((StateDiagramView)record.getNode());
							stateDiagramMap.put(currentStateDiagramView, (StateDiagram) record.getDiagramElement());
							stateDiagram=stateDiagramMap.get(currentStateDiagramView);
							break;
						case "removeState":
							stateView=(StateView)record.getNode();
							stateDiagramView=(StateDiagramView)record.getParentNode();
							stateDiagramView.getChildren().add(stateView);
							stateMap.put(stateView,(State) record.getDiagramElement());
							break;
						case "removeTransition":
							transitionView=(ArrowLineView)record.getNode();
							stateDiagramView=(StateDiagramView)record.getParentNode();
							stateDiagramView.getChildren().add(transitionView);
							arrowMap.put(transitionView,(Transition) record.getDiagramElement());
							break;
					}
					root=clientBridge.undo(root);
					System.out.println(root.getInfo());
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Redo
		class RedoAction implements EventHandler{
			public void handle(Event arg0) {
				try {
					DiagramMemento memento;
					StateDiagramView stateDiagramView;
					StateView stateView;
					ArrowLineView transitionView;
					String strArr[];
					String leastName;
					memento = diagramCareTaker.getDiagramViewRedoMemento();
					if(memento!=null) {
						Record record=(Record) memento.get();
						String cmd=record.getCmd();
						switch(cmd) {
							case "createState":
								stateView=(StateView)record.getNode();
								stateDiagramView=(StateDiagramView)record.getParentNode();
								stateDiagramView.getChildren().add(stateView);
								stateMap.put(stateView,(State) record.getDiagramElement());
								break;
							case "createTransition":
								transitionView=(ArrowLineView)record.getNode();
								stateDiagramView=(StateDiagramView)record.getParentNode();
								stateDiagramView.getChildren().add(transitionView);
								arrowMap.put(transitionView,(Transition) record.getDiagramElement());
								break;
							case "createStateDiagram":
								currentStateDiagramView=stateMachineView.add((StateDiagramView)record.getNode());
								stateDiagramMap.put(currentStateDiagramView, (StateDiagram) record.getDiagramElement());
								stateDiagram=stateDiagramMap.get(currentStateDiagramView);
								break;
							case "renameState":
								stateView=(StateView)record.getNode();
								strArr=record.getContext().split(",");
								leastName=strArr[0];
								stateView.nameText.setText(leastName);
								break;
							case "renameTransition":
								transitionView=(ArrowLineView)record.getNode();
								strArr=record.getContext().split(",");
								leastName=strArr[0];
								transitionView.nameText.setText(leastName);
								break;
							case "renameStateDiagram":
								stateDiagramView=(StateDiagramView)record.getNode();
								strArr=record.getContext().split(",");
								leastName=strArr[0];
								stateDiagramView.nameText.setText(leastName);
								break;
							case "removeStateDiagram":
								stateDiagramView=(StateDiagramView)record.getNode();
								currentStateDiagramView=stateDiagramView.lastStateDiagram;
								stateMachineView.removeStateDiagram(stateDiagramView);
								stateDiagramMap.remove(stateMachineView);
								stateDiagram=stateDiagramMap.get(currentStateDiagramView);
								break;
							case "removeState":
								stateView=(StateView)record.getNode();
								stateDiagramView=(StateDiagramView)record.getParentNode();
								stateDiagramView.getChildren().remove(stateView);
								stateMap.remove(stateView);
								break;
							case "removeTransition":
								transitionView=(ArrowLineView)record.getNode();
								stateDiagramView=(StateDiagramView)record.getParentNode();
								stateDiagramView.getChildren().remove(transitionView);
								arrowMap.remove(transitionView);
								break;
						}
						root=clientBridge.redo(root);
						System.out.println(root.getInfo());
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		/*class AddNoteAction implements EventHandler{
			@Override
			public void handle(Event e) {
				Group g=(Group)e.getSource();
				if(g instanceof ArrowLineView) {
					System.out.println("1");
					
				}else if(g instanceof StateView){
					System.out.println("2");
					
				}else if(g instanceof StateDiagramView) {
					
					
				}
			}
			
		}*/
		
		
		class DisplayInfoAction implements EventHandler{
			public void handle(Event e) {
				
			}
		}
}


