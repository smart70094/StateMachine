package Command;

import AbstractFactory.StateDiagramAbstractFactory;
import Model.State;
import Model.StateDiagram;
import Model.Transition;

public class CreateTransitionCommand implements StateDiagramCommand{
	StateDiagramAbstractFactory factory;
	StateDiagram targetStateDiagram,rootStateDiagram;
	Transition transition;
	
	public CreateTransitionCommand(StateDiagramAbstractFactory factory,StateDiagram targetStateDiagram){
		this.factory=factory;
		this.targetStateDiagram=targetStateDiagram;
	}
	public Object execute() {
		transition=(Transition)factory.createTransition();
		targetStateDiagram.add(transition);
		return transition;
	}
	@Override
	public Object undo() {
		targetStateDiagram=(StateDiagram) rootStateDiagram.get(targetStateDiagram);
		targetStateDiagram.remove(transition);
		return rootStateDiagram;
	}
	@Override
	public void setRootStateDiagram(StateDiagram sd) {
		rootStateDiagram=sd;
	}
	@Override
	public Object redo() {
		targetStateDiagram=(StateDiagram) rootStateDiagram.get(targetStateDiagram);
		targetStateDiagram.add(transition);
		return rootStateDiagram;
	}


}
