package Memento;

public class DiagramMemento implements Cloneable{
	private Record record;
	public DiagramMemento(Record record) {
		this.record=record;
	}
	public Record getRecord() {
		return record;
	}
	public DiagramMemento clone() throws CloneNotSupportedException {
		DiagramMemento diagramMemento = (DiagramMemento) super.clone();
		if (this.record != null)
			diagramMemento.record = (Record) this.record.clone();
		return diagramMemento;
	}
	
}
