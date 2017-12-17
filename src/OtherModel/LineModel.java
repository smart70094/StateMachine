package OtherModel;

import Model.DiagramElement;

public class LineModel extends DiagramElement{
	public double sx,sy,ex,ey;
	public LineModel() {}
	public LineModel(double sx,double sy,double ex,double ey) {
		this.sx=sx;
		this.sy=sy;
		this.ex=ex;
		this.ey=ey;
	}
	@Override
	public void printInfo() {
		
	}
}
