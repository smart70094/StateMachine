package Memento;

import Command.StateDiagramCommand;

public class ModelRecord implements Cloneable{
	private StateDiagramCommand command;
	public ModelRecord(StateDiagramCommand command) {
		this.command=command;
	}
	public DiagramMemento createMemento() {
		DiagramMemento memento=new DiagramMemento(this);
		return memento;
	}
	public StateDiagramCommand clone() throws CloneNotSupportedException {
		StateDiagramCommand command=(StateDiagramCommand)super.clone();
		return command;
	}
	public StateDiagramCommand getCommand() {
		return command;
	}
}
