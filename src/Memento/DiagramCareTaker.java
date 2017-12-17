package Memento;

import java.util.ArrayList;
import java.util.Stack;

public class DiagramCareTaker {
	Stack<DiagramMemento> stack;
	public DiagramCareTaker() {
		stack=new Stack<DiagramMemento>();
	}
	public void add(DiagramMemento m) {
		stack.push(m);
	}
	public DiagramMemento get() throws CloneNotSupportedException {
		return (!stack.empty())?(DiagramMemento)stack.pop().clone():null;
	}
}
