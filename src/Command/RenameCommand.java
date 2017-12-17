package Command;

import Model.DiagramElement;
import Model.StateDiagram;

public class RenameCommand implements StateDiagramCommand{
	DiagramElement de;
	String name;
	String lastName;
	DiagramElement targetStateDiagram,rootStateDiagram;
	public RenameCommand(String name,DiagramElement de,DiagramElement root,DiagramElement targetStateDiagram) {
		this.de=de;
		this.name=name;
		this.rootStateDiagram=(StateDiagram) rootStateDiagram;
		this.targetStateDiagram=targetStateDiagram;
	}
	public Object execute() {
		lastName=de.name;
		de.name=name;
		return de;
	}
	@Override
	public Object undo() {
		targetStateDiagram=(DiagramElement) rootStateDiagram.get(de);	
		String s=targetStateDiagram.name;
		targetStateDiagram.name=lastName;
		lastName=s;
		return rootStateDiagram;
	}
	@Override
	public void setRootStateDiagram(StateDiagram sd) {
		rootStateDiagram=sd;
	}
	@Override
	public Object redo() {
		targetStateDiagram=(DiagramElement) rootStateDiagram.get(de);
		String s=targetStateDiagram.name;
		targetStateDiagram.name=lastName;
		lastName=s;
		return rootStateDiagram;
	}

}
