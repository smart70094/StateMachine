package Command;

import Model.StateDiagram;

public interface StateDiagramCommand {
	Object execute();
	Object undo();
	Object redo();
	void setRootStateDiagram(StateDiagram sd);
}
