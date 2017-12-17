package Decorator;

import Model.DiagramElement;

public class NoteDecorator extends Decorator{
	String note="";
	public NoteDecorator(DiagramElement de,String note) {
		super(de);
		this.note=note;
	}
	public void printInfo() {
		super.printInfo();
		addNote();
	}
	public void addNote() {
		super.info=super.info+"["+note+"]";
	}
	
}
