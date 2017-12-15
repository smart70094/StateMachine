package Bridge;

import Model.DiagramElement;
import Model.State;
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
	public void rename(String name,DiagramElement de) {
		bridge.rename(name,de);
	}
	
}
