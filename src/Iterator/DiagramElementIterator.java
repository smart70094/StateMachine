package Iterator;

import java.util.ArrayList;

import Model.DiagramElement;

public class DiagramElementIterator implements Iterator{
	ArrayList<DiagramElement> list=new ArrayList<DiagramElement>();
	int pos=0;
	public DiagramElementIterator(ArrayList<DiagramElement> list) {
		this.list=list;
	}
	@Override
	public boolean hasNext() {
		return (pos<list.size())? true : false;
	}

	@Override
	public Object next() {
		return list.get(pos++);
	}

	@Override
	public void remove() {
		list.clear();
	}

}
