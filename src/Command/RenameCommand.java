package Command;

import Model.DiagramElement;

public class RenameCommand implements StateDiagramCommand{
	DiagramElement de;
	String name;
	public RenameCommand(String name,DiagramElement de) {
		this.de=de;
		this.name=name;
	}
	public Object execute() {
		de.name=name;
		return null;
	}

}
