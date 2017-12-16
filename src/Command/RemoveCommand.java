package Command;

import Model.DiagramElement;

public class RemoveCommand implements StateDiagramCommand{
	DiagramElement parent,children;
	
	public RemoveCommand(DiagramElement parent,DiagramElement children) {
		this.parent=parent;
		this.children=children;
	}
	public Object execute() {
		parent.remove(children);
		return null;
	}

}
