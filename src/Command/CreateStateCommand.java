package Command;

import AbstractFactory.StateDiagramAbstractFactory;
import Model.State;
import Model.StateDiagram;

public class CreateStateCommand implements StateDiagramCommand{
	StateDiagramAbstractFactory factory;
	StateDiagram targetStateDiagram,rootStateDiagram;
	State state;
	public CreateStateCommand(StateDiagramAbstractFactory factory){
		this.factory=factory;
	}
	public CreateStateCommand(StateDiagramAbstractFactory factory,StateDiagram targetStateDiagram){
		this.factory=factory;
		this.targetStateDiagram=targetStateDiagram;
	}
	public Object execute() {
		state=(State)factory.createState();
		targetStateDiagram.add(state);
		return state;
	}
	@Override
	public Object undo() {
		targetStateDiagram=(StateDiagram) rootStateDiagram.get(targetStateDiagram);
		targetStateDiagram.remove(state);
		return null;
	}
	public Object redo() {
		targetStateDiagram=(StateDiagram) rootStateDiagram.get(targetStateDiagram);
		targetStateDiagram.add(state);
		return null;
	}
	@Override
	public Object getRootStateDiagram() {
		return rootStateDiagram;
	}
	@Override
	public void setRootStateDiagram(StateDiagram sd) {
		rootStateDiagram=sd;
	}
	
}
