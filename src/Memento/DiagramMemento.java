package Memento;

public class DiagramMemento implements Cloneable{
	private Object memento;
	public DiagramMemento(Object memento) {
		this.memento=memento;
	}
	public Object get() {
		return memento;
	}
	public DiagramMemento clone() throws CloneNotSupportedException {
		DiagramMemento diagramMemento = (DiagramMemento) super.clone();
		return diagramMemento;
	}
	
}
