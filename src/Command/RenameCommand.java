package Command;

import Model.DiagramElement;
import Model.StateDiagram;

public class RenameCommand implements StateDiagramCommand{
	DiagramElement de;
	String name;
	public RenameCommand(String name,DiagramElement de) {
		this.de=de;
		this.name=name;
	}
	public Object execute() {
		de.name=name;
		return de;
	}
	@Override
	public Object undo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRootStateDiagram(StateDiagram sd) {
		// TODO Auto-generated method stub
		
	}

}
