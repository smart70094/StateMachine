package Command;

import AbstractFactory.StateDiagramAbstractFactory;

public class CreateStateDiagramCommand implements StateDiagramCommand{
	StateDiagramAbstractFactory factory;
	public CreateStateDiagramCommand(StateDiagramAbstractFactory factory) {
		this.factory=factory;
	}
	@Override
	public Object execute() {
		return factory.createStateDiagram();
	}
	@Override
	public Object undo() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
