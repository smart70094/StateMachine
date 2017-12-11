package Command;

import AbstractFactory.StateDiagramAbstractFactory;

public class CreateTransitionCommand implements StateDiagramCommand{
	StateDiagramAbstractFactory factory;
	public CreateTransitionCommand(StateDiagramAbstractFactory factory){
		this.factory=factory;
	}
	public Object execute() {
		return factory.createTransition();
	}


}
