package Bridge;

import AbstractFactory.StateDiagramAbstractFactory;
import AbstractFactory.VersionOneFactory;
import Command.CreateStateCommand;
import Command.CreateStateDiagramCommand;
import Command.CreateTransitionCommand;
import Command.RenameCommand;
import Command.StateDiagramCommand;
import Model.DiagramElement;
import Model.StateDiagram_V1;
import Model.State_V1;
import Model.Transition;
import Model.Transition_V1;

public class StateDiagram_V1_Bridge implements StateDiagramBridge{
	StateDiagramAbstractFactory factory;
	
	public StateDiagram_V1_Bridge() {
		factory=new VersionOneFactory();
	}
	
	@Override
	public State_V1 createState() {
		StateDiagramCommand command=new CreateStateCommand(factory);
		return (State_V1) command.execute();
	}

	@Override
	public Transition_V1 createTransition() {
		StateDiagramCommand command=new CreateTransitionCommand(factory);
		return (Transition_V1) command.execute();
	}

	@Override
	public StateDiagram_V1 createStateDiagram() {
		StateDiagramCommand command=new CreateStateDiagramCommand(factory);
		return (StateDiagram_V1) command.execute();
	}

	@Override
	public void remove(DiagramElement parent ,DiagramElement children) {
		
		
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
