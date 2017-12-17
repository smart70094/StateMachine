package Memento;

import Model.DiagramElement;
import javafx.scene.Group;

public class Record implements Cloneable{
	private String cmd;
	private String context;
	private Group node,parentNode;
	private DiagramElement diagramElement;
	
	public Record(String cmd,Group parentNode,Group node) {
		this.cmd=cmd;
		this.parentNode=parentNode;
		this.node=node;
	}
	public Record(String cmd,Group node,String  context) {
		this.cmd=cmd;
		this.node=node;
		this.context=context;
	}
	public Record(String cmd,DiagramElement diagramElement,Group node) {
		this.cmd=cmd;
		this.diagramElement=diagramElement;
		this.node=node;
	}
	public Record(String cmd,DiagramElement  diagramElement) {
		this.cmd=cmd;
		this.diagramElement=diagramElement;
	}
	
	public DiagramMemento createMemento() {
		DiagramMemento memento=new DiagramMemento(this);
		return memento;
	}
	public Record clone() throws CloneNotSupportedException {
		Record record=(Record)super.clone();
		return record;
	}
	
	public String getCmd() {return cmd;}
	public String getContext() {return context;}
	public Group getNode() {return node;}
	public Group getParentNode() {return parentNode;}
	public DiagramElement getDiagramElement() {return  diagramElement;}
}
