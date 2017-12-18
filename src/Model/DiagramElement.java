package Model;

import java.util.HashMap;
import java.util.Map;

import Decorator.Decorator;


public abstract class DiagramElement {
	public double x,y;
	public String name,style,note;
	static int countID=0;
	private int id=countID++;
	double orgSceneX=0;
	double orgSceneY=0;
	double orgTranslateX=0;
	double orgTranslateY=0;
	
	public void add(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	public void add(DiagramElement root,DiagramElement node) {
		throw new UnsupportedOperationException();
	}
	public void remove(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	
	public static DiagramElement tempDiagramElement=null;
	public void search(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	public DiagramElement get(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	
	
	public void searchParent(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	public DiagramElement getParent(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	
	public void draggedMoveFrom(double mx,double my,double ox,double oy) {
		orgSceneX = mx;
	    orgSceneY = my;
	    orgTranslateX = ox;
	    orgTranslateY = oy;   
	}
	public int getID() {
		return id;
	}
	
	public double[] draggedMoveTo(double mx,double my) {
		double offsetX = mx - orgSceneX;
	    double offsetY = my - orgSceneY;
	    double newTranslateX = orgTranslateX + offsetX;
	    double newTranslateY = orgTranslateY + offsetY;
	    double[] point= {newTranslateX,newTranslateY};
	    return point;
	}
	public boolean detectCollision(double mx,double my) {
		if(mx>1030 && mx<1080 && my>718 && my<768)
			return true;
		return false;
	}
	
	//以DiagramElement的生命週期蒐集存放資料(leaf、component)
	public abstract void printInfo();
	public static String info="";
	public static int countSpace=0;
	public String getInfo() {
		printInfo();
		String result=info;
		info="";
		countSpace=0;
		return result;
	}
	
	public static Map<DiagramElement,Decorator> decoratorMap=new HashMap<DiagramElement,Decorator>();
	public String printDecorator() {
		   java.util.Iterator it = decoratorMap.entrySet().iterator();
		   String result="";
		    while (it.hasNext()) {
		    	 Map.Entry pair = (Map.Entry)it.next();
		        Decorator decorator=(Decorator) pair.getValue();	
		        result+=decorator.getInfo()+"\n";
		    }
		    return result;
	}
	
	
}
