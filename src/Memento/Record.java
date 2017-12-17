package Memento;

import javafx.scene.Group;

public class Record implements Cloneable{
	private String cmd;
	private String context;
	private Group node,parentNode;
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
}
