package Command;

import Model.StateDiagram;

public interface StateDiagramCommand {
	Object execute();
	Object undo();
	
	Object getRootStateDiagram();
	void setRootStateDiagram(StateDiagram sd);
}
