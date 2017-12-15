package Model;

public abstract class DiagramElement {
	double orgSceneX=0;
	double orgSceneY=0;
	double orgTranslateX=0;
	double orgTranslateY=0;
	
	public void draggedMoveFrom(double mx,double my,double ox,double oy) {
		orgSceneX = mx;
	    orgSceneY = my;
	    orgTranslateX = ox;
	    orgTranslateY = oy;   
	}
	
	public double[] draggedMoveTo(double mx,double my) {
		double offsetX = mx - orgSceneX;
	    double offsetY = my - orgSceneY;
	    double newTranslateX = orgTranslateX + offsetX;
	    double newTranslateY = orgTranslateY + offsetY;
	    double[] point= {newTranslateX,newTranslateY};
	    return point;
	}
}
