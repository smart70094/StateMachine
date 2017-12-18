package Model;

import java.util.ArrayList;

import java.util.Map;

import Decorator.Decorator;
import Decorator.NoteDecorator;
import Iterator.DiagramElementIterator;
import Iterator.Iterator;


public class StateDiagram extends DiagramElement{
	public double width,height;
	ArrayList<DiagramElement> diagramElementList=new ArrayList<DiagramElement>();
	
	public void add(DiagramElement diagramElement) {
		diagramElementList.add(diagramElement);
	}
	public void add(DiagramElement root,DiagramElement node) {
		
		DiagramElement parent=root.getParent(node);
		if(parent==null)
			root.add(node);
		else
			parent.add(node);
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
	
	public void search(DiagramElement diagramElement) {
		Iterator it=new DiagramElementIterator(diagramElementList);
		while(it.hasNext()) {
			DiagramElement de=(DiagramElement)it.next();
			if(de.getID()==diagramElement.getID()) {
				tempDiagramElement=de;
			}else {
				if(de instanceof StateDiagram) {
					de.search(diagramElement);
				}
			}
		}
	}
	public DiagramElement get(DiagramElement diagramElement) {
		search(diagramElement);
		DiagramElement de=tempDiagramElement;
		tempDiagramElement=null;
		return de;
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
	
	public void putDecorator(DiagramElement de,String note) {
		Decorator decorator = null;
		if(decoratorMap.containsKey(de)) {
			decorator=decoratorMap.get(de);
			decorator=new NoteDecorator(decorator,note);
		}else 
			decorator=new NoteDecorator(de,note);
		
		decoratorMap.put(de, decorator);
	}
	
	
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
