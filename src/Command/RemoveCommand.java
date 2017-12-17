package Command;

import Model.DiagramElement;
import Model.StateDiagram;

public class RemoveCommand implements StateDiagramCommand{
	DiagramElement parent,children;
	StateDiagram rootStateDiagram;
	public RemoveCommand(DiagramElement parent,DiagramElement children) {
		this.parent=parent;
		this.children=children;
	}
	public Object execute() {
		parent.remove(children);
		return this;
	}
	@Override
	public Object undo() {
		rootStateDiagram.add(rootStateDiagram, children);
		return rootStateDiagram;
	}
	@Override
	public void setRootStateDiagram(StateDiagram sd) {
		rootStateDiagram=sd;
	}

}
