package Bridge;

import Model.DiagramElement;
import Model.State;
import Model.StateDiagram;
import Model.Transition;

public interface StateDiagramBridge {
	State createState(StateDiagram sd);
	Transition createTransition(StateDiagram sd);
	StateDiagram createStateDiagram(StateDiagram sd);
	void rename(String name,DiagramElement de);
	void remove(DiagramElement parent ,DiagramElement children);
	StateDiagram undo(StateDiagram sd);
	void redo();
}
