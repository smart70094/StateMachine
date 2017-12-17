package Memento;

import java.util.ArrayList;
import java.util.Stack;

public class DiagramCareTaker {
	Stack<DiagramMemento> viewStack;
	Stack<DiagramMemento> modelStack;
	
	public DiagramCareTaker() {
		viewStack=new Stack<DiagramMemento>();
		modelStack=new Stack<DiagramMemento>();
	}
	
	//View
	public void addDiagramViewMemento(DiagramMemento m) {
		viewStack.push(m);
	}
	public DiagramMemento getDiagramViewMemento() throws CloneNotSupportedException {
		return (!viewStack.empty())?(DiagramMemento)viewStack.pop().clone():null;
	}
	
	//Model
	public void addDiagramModelMemento(DiagramMemento m) {
		modelStack.push(m);
	}
	public DiagramMemento getDiagramModelMemento() throws CloneNotSupportedException {
		return (!modelStack.empty())?(DiagramMemento)modelStack.pop().clone():null;
	}
}
