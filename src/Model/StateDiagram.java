package Model;

import java.util.ArrayList;

public class StateDiagram extends DiagramElement{
	public double width,height;
	ArrayList<DiagramElement> diagramElementList=new ArrayList<DiagramElement>();
	public void add(DiagramElement de) {
		diagramElementList.add(de);
	}
	public void remove(DiagramElement diagramElement) {
		diagramElementList.remove(diagramElement);
	}
}
