package Decorator;

import Model.DiagramElement;

public class NoteDecorator extends Decorator{
	String note="";
	public NoteDecorator(DiagramElement de,String note) {
		super(de);
		this.note=note;
	}
	public void printInfo() {
		addNote();
		super.printInfo();
		
	}
	public void addNote() {
		super.info=info+"["+note+"]";
	}
	
}
