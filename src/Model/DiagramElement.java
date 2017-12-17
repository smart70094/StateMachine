package Model;

public abstract class DiagramElement {
	public double x,y;
	public String name,style;
	static int countID=0;
	private int id=countID++;
	double orgSceneX=0;
	double orgSceneY=0;
	double orgTranslateX=0;
	double orgTranslateY=0;
	
	public void add(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	public void add(DiagramElement diagramElement,DiagramElement targetDiagramElement) {
		throw new UnsupportedOperationException();
	}
	public void remove(DiagramElement diagramElement) {
		throw new UnsupportedOperationException();
	}
	public DiagramElement get(DiagramElement diagramElement) {
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
}
