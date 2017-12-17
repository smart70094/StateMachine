package Bridge;

import AbstractFactory.StateDiagramAbstractFactory;
import AbstractFactory.VersionOneFactory;
import Command.CreateStateCommand;
import Command.CreateStateDiagramCommand;
import Command.CreateTransitionCommand;
import Command.RemoveCommand;
import Command.RenameCommand;
import Command.StateDiagramCommand;
import Memento.DiagramCareTaker;
import Memento.DiagramMemento;
import Memento.ModelRecord;
import Model.DiagramElement;
import Model.StateDiagram;
import Model.StateDiagram_V1;
import Model.State_V1;
import Model.Transition;
import Model.Transition_V1;

public class StateDiagram_V1_Bridge implements StateDiagramBridge{
	StateDiagramAbstractFactory factory;
	DiagramCareTaker diagramCareTaker;
	public StateDiagram_V1_Bridge(DiagramCareTaker diagramCareTaker) {
		factory=new VersionOneFactory();
		this.diagramCareTaker=diagramCareTaker;
	}
	
	@Override
	public State_V1 createState(StateDiagram sd) {
		StateDiagramCommand command=new CreateStateCommand(factory,sd);
		State_V1 state=(State_V1) command.execute();
		save(command);
		return state;
	}

	@Override
	public Transition_V1 createTransition(StateDiagram sd) {
		StateDiagramCommand command=new CreateTransitionCommand(factory,sd);
		Transition_V1 transition=(Transition_V1) command.execute();
		save(command);
		return transition;
	}

	@Override
	public StateDiagram_V1 createStateDiagram(StateDiagram sd) {
		StateDiagramCommand command=new CreateStateDiagramCommand(factory,sd);
		StateDiagram_V1 stateDiagram=(StateDiagram_V1) command.execute();
		save(command);
		return stateDiagram;
	}

	@Override
	public void remove(DiagramElement parent ,DiagramElement children) {
		StateDiagramCommand command=new RemoveCommand(parent,children);
		command=(StateDiagramCommand) command.execute();
		save(command);
	}

	@Override
	public void rename(String name,DiagramElement de,StateDiagram root,StateDiagram sd) {
		StateDiagramCommand command=new RenameCommand(name,de,root,sd);
		command.execute();
		save(command);
	}
	
	@Override
	public StateDiagram undo(StateDiagram sd) {
		try {
			DiagramMemento memento=diagramCareTaker.getDiagramModelUndoMemento();
			if(memento!=null) {
				ModelRecord record=(ModelRecord) memento.get();
				StateDiagramCommand command=record.getCommand();
				command.setRootStateDiagram(sd);
				return (StateDiagram) command.undo();
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StateDiagram redo(StateDiagram sd) {
		try {
			DiagramMemento memento=diagramCareTaker.getDiagramModelRedoMemento();
			if(memento!=null) {
				ModelRecord record=(ModelRecord) memento.get();
				StateDiagramCommand command=record.getCommand();
				command.setRootStateDiagram(sd);
				return (StateDiagram) command.redo();
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void save(StateDiagramCommand command) {
		ModelRecord record=new ModelRecord(command);
		diagramCareTaker.addDiagramModelMemento(record.createMemento());
	}

}
