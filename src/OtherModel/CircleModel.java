package OtherModel;

import Model.DiagramElement;

public class CircleModel extends DiagramElement{
	double radius;
	CircleModel(){}
	CircleModel(double radius,double x,double y){
		this.radius=radius;
		this.x=x;
		this.y=y;
	}
}
