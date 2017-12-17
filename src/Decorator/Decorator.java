package Decorator;

import Model.DiagramElement;

public class Decorator extends DiagramElement{
	DiagramElement de;
	public Decorator(DiagramElement de) {
		this.de=de;
	}
	
	@Override
	public void printInfo() {
		de.printInfo();
	}

}
