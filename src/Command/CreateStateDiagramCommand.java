package Command;

import AbstractFactory.StateDiagramAbstractFactory;
import Model.State;
import Model.StateDiagram;

public class CreateStateDiagramCommand implements StateDiagramCommand{
	StateDiagramAbstractFactory factory;
	StateDiagram targetStateDiagram,rootStateDiagram;
	StateDiagram stateDiagram;
	public CreateStateDiagramCommand(StateDiagramAbstractFactory factory,StateDiagram targetStateDiagram){
		this.factory=factory;
		this.targetStateDiagram=targetStateDiagram;
	}
	@Override
	public Object execute() {
		stateDiagram=(StateDiagram)factory.createStateDiagram();
		targetStateDiagram.add(stateDiagram);
		return stateDiagram;
	}
	@Override
	public Object undo() {
		targetStateDiagram=(StateDiagram) rootStateDiagram.get(targetStateDiagram);
		if(targetStateDiagram!=null) 
			targetStateDiagram.remove(stateDiagram);
		else
			rootStateDiagram.remove(targetStateDiagram);
		return rootStateDiagram;
	}
	@Override
	public void setRootStateDiagram(StateDiagram sd) {
		rootStateDiagram=sd;
	}
	@Override
	public Object redo() {
		targetStateDiagram=(StateDiagram) rootStateDiagram.get(targetStateDiagram);
		if(targetStateDiagram!=null) 
			targetStateDiagram.add(stateDiagram);
		else
			rootStateDiagram.add(targetStateDiagram);
		return rootStateDiagram;
	}


	

}
