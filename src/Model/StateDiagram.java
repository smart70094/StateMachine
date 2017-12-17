package Model;

import java.util.ArrayList;

import Iterator.DiagramElementIterator;
import Iterator.Iterator;

public class StateDiagram extends DiagramElement{
	public double width,height;
	ArrayList<DiagramElement> diagramElementList=new ArrayList<DiagramElement>();
	public void add(DiagramElement diagramElement) {
		diagramElementList.add(diagramElement);
	}
	public void remove(DiagramElement diagramElement) {
		Iterator it=new DiagramElementIterator(diagramElementList);
		while(it.hasNext()) {
			DiagramElement de=(DiagramElement)it.next();
			if(de.equals(diagramElement)) {
				diagramElementList.remove(de);
			}else {
				if(de instanceof StateDiagram) {
					de.remove(diagramElement);
				}
			}
		}
	}
	public DiagramElement get(DiagramElement diagramElement) {
		Iterator it=new DiagramElementIterator(diagramElementList);
		while(it.hasNext()) {
			DiagramElement de=(DiagramElement)it.next();
			if(de.getID()==diagramElement.getID()) {
				return de;
			}else {
				if(de instanceof StateDiagram) {
					de.get(diagramElement);
				}
			}
		}
		return null;
	}
}
