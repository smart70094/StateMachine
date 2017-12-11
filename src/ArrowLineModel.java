
public class ArrowLineModel {
	//起點與終點座標
	double sx,sy,ex,ey;
	//拖移時記錄的坐標
	double orgSceneX=0;
	double orgSceneY=0;
	double orgTranslateX=0;
	double orgTranslateY=0;

	public ArrowLineModel(double sx,double sy,double ex,double ey) {
		this.sx=sx;
		this.sy=sy;
		this.ex=ex;
		this.ey=ey;
	}
	
	void draggedMoveFrom(double mx,double my,double ox,double oy) {
		orgSceneX = mx;
	    orgSceneY = my;
	    orgTranslateX = ox;
	    orgTranslateY = oy;   
	}
	double[] draggedMoveTo(double mx,double my) {
		double offsetX = mx - orgSceneX;
	    double offsetY = my - orgSceneY;
	    double newTranslateX = orgTranslateX + offsetX;
	    double newTranslateY = orgTranslateY + offsetY;
	    double[] point= {newTranslateX,newTranslateY};
	    return point;
	}
}
