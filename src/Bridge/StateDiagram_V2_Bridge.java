package Bridge;

import AbstractFactory.StateDiagramAbstractFactory;
import AbstractFactory.VersionTwoFactory;
import Command.CreateStateCommand;
import Command.CreateStateDiagramCommand;
import Command.CreateTransitionCommand;
import Command.RenameCommand;
import Command.StateDiagramCommand;
import Model.DiagramElement;
import Model.StateDiagram;
import Model.StateDiagram_V2;
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
		StateDiagramCommand command=new CreateTransitionCommand(factory);
		return (Transition_V2) command.execute();
	}

	@Override
	public StateDiagram_V2 createStateDiagram() {
		StateDiagramCommand command=new CreateStateDiagramCommand(factory);
		return (StateDiagram_V2) command.execute();
	}

	@Override
	public void remove(DiagramElement parent ,DiagramElement children) {
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
