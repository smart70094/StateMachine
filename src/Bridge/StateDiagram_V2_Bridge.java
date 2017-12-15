package Bridge;

import AbstractFactory.StateDiagramAbstractFactory;
import AbstractFactory.VersionTwoFactory;
import Command.CreateStateCommand;
import Command.CreateTransitionCommand;
import Command.RenameCommand;
import Command.StateDiagramCommand;
import Model.DiagramElement;
import Model.StateDiagram;
import Model.State_V2;
import Model.Transition_V2;

public class StateDiagram_V2_Bridge implements StateDiagramBridge{
StateDiagramAbstractFactory factory;
	
	public StateDiagram_V2_Bridge() {
		factory=new VersionTwoFactory();
	}

	@Override
	public State_V2 createState() {
		StateDiagramCommand command=new CreateStateCommand(factory);
		return (State_V2) command.execute();
	}

	@Override
	public Transition_V2 createTransition() {
		// TODO Auto-generated method stub
		StateDiagramCommand command=new CreateTransitionCommand(factory);
		return (Transition_V2) command.execute();
	}

	@Override
	public StateDiagram createStateDiagram() {
		// TODO Auto-generated method stub
		
		return null;
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

	@Override
	public void rename(String name,DiagramElement de) {
		StateDiagramCommand command=new RenameCommand(name,de);
		command.execute();
		
	}
}
