package Bridge;

import AbstractFactory.StateDiagramAbstractFactory;
import AbstractFactory.VersionOneFactory;
import Command.CreateTransitionCommand;
import Command.StateDiagramCommand;
import Model.Transition_V1;

public class StateDiagram_V1_Bridge implements StateDiagramBridge{
	StateDiagramAbstractFactory factory;
	
	public StateDiagram_V1_Bridge() {
		factory=new VersionOneFactory();
	}
	
	@Override
	public void createState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Transition_V1 createTransition() {
		StateDiagramCommand command=new CreateTransitionCommand(factory);
		return (Transition_V1) command.execute();
	}

	@Override
	public void createStateDiagram() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
