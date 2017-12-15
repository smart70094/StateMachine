package Builder;

import Model.DiagramElement;
import Model.Transition;
import Model.Transition_V1;

public class TransitionBuilder extends DiagramElementBuilder{
	Transition result=new Transition_V1();
	void setShape(double... data) {
		double sx=data[0];
		double sy=data[1];
		double ex=data[2];
		double ey=data[3];
		
		result.mainLine.sx=sx;
		result.mainLine.sy=sy;
		result.mainLine.ex=ex;
		result.mainLine.ey=ey;
		result.repaintArrow();
	}
	
	void setStyle(String style) {
		result.style=style;
	}
		
	@Override
	void setName(String name) {
		result.name=name;
	}

	@Override
	Object getResult() {
		return result;
		
	}

	@Override
	void setPosition(double... data) {
		// TODO Auto-generated method stub
		double x=data[0];
		double y=data[1];
		result.x=x;
		result.y=y;
	}
}
