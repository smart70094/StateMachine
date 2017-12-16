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
	public Transition createTransition() {
		return bridge.createTransition();
	}
	public State createState() {
		return bridge.createState();
	}
	public StateDiagram createStateDiagram() {
		return bridge.createStateDiagram();
	}
	public void rename(String name,DiagramElement de) {
		bridge.rename(name,de);
	}
	public void remove(DiagramElement de) {
		bridge.remove(de);
	}
	
}
