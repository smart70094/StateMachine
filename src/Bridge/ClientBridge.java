package Bridge;

import Model.DiagramElement;
import Model.State;
import Model.StateDiagram;
import Model.Transition;

public class ClientBridge {
	StateDiagramBridge bridge;
	public ClientBridge(StateDiagramBridge bridge) {
		this.bridge=bridge;
	}
	public void setBridge(StateDiagramBridge bridge) {
		this.bridge=bridge;
	}
	public Transition createTransition(StateDiagram sd) {
		return bridge.createTransition(sd);
	}
	public State createState(StateDiagram sd) {
		return bridge.createState(sd);
	}
	public StateDiagram createStateDiagram(StateDiagram sd) {
		return bridge.createStateDiagram(sd);
	}
	public void rename(String name,DiagramElement de,StateDiagram root,StateDiagram sd) {
		bridge.rename(name,de,root,sd);
	}
	public void remove(DiagramElement root ,DiagramElement children) {
		DiagramElement parent=root.getParent(children);
		
		bridge.remove((parent==null)?root:parent,children);
	}
	public StateDiagram undo(StateDiagram sd) {
		return bridge.undo(sd);
	}
	public StateDiagram redo(StateDiagram sd) {
		return bridge.redo(sd);
	}
	
}
