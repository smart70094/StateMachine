package Memento;

import java.util.ArrayList;
import java.util.Stack;

public class DiagramCareTaker {
	Stack<DiagramMemento> viewUndoStack;
	Stack<DiagramMemento> viewRedoStack;
	
	Stack<DiagramMemento> modelUndoStack;
	Stack<DiagramMemento> modelRedoStack;
	
	public DiagramCareTaker() {
		viewUndoStack=new Stack<DiagramMemento>();
		viewRedoStack=new Stack<DiagramMemento>();
		modelUndoStack=new Stack<DiagramMemento>();
		modelRedoStack=new Stack<DiagramMemento>();
	}
	
	//View
	public void addDiagramViewMemento(DiagramMemento m) {
		viewUndoStack.push(m);
	}
	public DiagramMemento getDiagramViewUndoMemento() throws CloneNotSupportedException {
		DiagramMemento memento = null;
		if(!viewUndoStack.empty()) {
			memento=(DiagramMemento)viewUndoStack.pop().clone();
			viewRedoStack.push(memento);
		}
		return memento;
	}
	public DiagramMemento getDiagramViewRedoMemento() throws CloneNotSupportedException {
		DiagramMemento memento = null;
		if(!viewRedoStack.empty()) {
			memento=(DiagramMemento)viewRedoStack.pop().clone();
			viewUndoStack.push(memento);
		}
		return memento;
	}
	
	//Model
	public void addDiagramModelMemento(DiagramMemento m) {
		modelUndoStack.push(m);
	}
	public DiagramMemento getDiagramModelUndoMemento() throws CloneNotSupportedException {
		DiagramMemento memento=null;
		if(!modelUndoStack.empty()) {
			memento=(DiagramMemento)modelUndoStack.pop().clone();
			modelRedoStack.push(memento);
		}
		return memento;
	}
	public DiagramMemento getDiagramModelRedoMemento() throws CloneNotSupportedException {
		DiagramMemento memento=null;
		if(!modelRedoStack.empty()) {
			memento=(DiagramMemento)modelRedoStack.pop().clone();
			modelUndoStack.push(memento);
		}
		return memento;
	}
}
