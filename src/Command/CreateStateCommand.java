package Command;

import AbstractFactory.StateDiagramAbstractFactory;

public class CreateStateCommand implements StateDiagramCommand{
	StateDiagramAbstractFactory factory;
	public CreateStateCommand(StateDiagramAbstractFactory factory){
		this.factory=factory;
	}
	public Object execute() {
		return factory.createState();
	}
}
