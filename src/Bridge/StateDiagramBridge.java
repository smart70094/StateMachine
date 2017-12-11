package Bridge;

import Model.Transition_V1;

public interface StateDiagramBridge {
	void createState();
	Transition_V1 createTransition();
	void createStateDiagram();
	void remove();
	void undo();
	void redo();
	
}
