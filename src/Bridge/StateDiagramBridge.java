package Bridge;

import Model.DiagramElement;
import Model.State;
import Model.StateDiagram;
import Model.Transition;

public interface StateDiagramBridge {
	State createState();
	Transition createTransition();
	StateDiagram createStateDiagram();
	void rename(String name,DiagramElement de);
	void remove(DiagramElement parent ,DiagramElement children);
	void undo();
	void redo();
}
