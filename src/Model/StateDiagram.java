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
	public void add(DiagramElement root,DiagramElement parent,DiagramElement node) {
		Iterator it=new DiagramElementIterator(diagramElementList);
		while(it.hasNext()) {
			DiagramElement de=(DiagramElement)it.next();
			if(de.getID()==parent.getID()) {
				parent.add(node);
			}else {
				if(de instanceof StateDiagram) {
					de.add(root,parent,node);
				}
			}
		}
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
	
	static DiagramElement parent=null;
	public void searchParent(DiagramElement diagramElement) {
		Iterator it=new DiagramElementIterator(diagramElementList);
		while(it.hasNext()) {
			DiagramElement de=(DiagramElement)it.next();
			if(de.getID()==diagramElement.getID()) {
				parent=this;
			}else {
				if(de instanceof StateDiagram) {
					de.searchParent(diagramElement);
				}
			}
		}
	}
	public DiagramElement getParent(DiagramElement diagramElement) {
		searchParent(diagramElement);
		DiagramElement result=parent;
		parent=null;
		return result;
	}
	
	static int countSpace=0;
	
	public void printInfo() {
		Iterator it=new DiagramElementIterator(diagramElementList);
		while(it.hasNext()) {
			DiagramElement de=(DiagramElement)it.next();
			printSpace(countSpace);
			if(de instanceof StateDiagram) {
				
				info=info+"   "+de.name+"\n";
				countSpace++;
			}else {
				info=info+"   ";
				//System.out.print(" ");
			}
			
			de.printInfo();
		}
		countSpace--;
	}
	public String getInfo() {
		printInfo();
		String result=info;
		info="";
		countSpace=0;
		return result;
	}
	
	private void printSpace(int countSpace) {
		for(int i=0;i<countSpace;i++) 
			info=info+"     ";
			//System.out.print(" ");
	}
	
}
