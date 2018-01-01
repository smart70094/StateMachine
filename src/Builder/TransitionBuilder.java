package Builder;

import Model.DiagramElement;
import Model.State_V1;
import Model.State_V2;
import Model.Transition;
import Model.Transition_V1;
import Model.Transition_V2;

public class TransitionBuilder extends DiagramElementBuilder{
	Transition result;
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

	@Override
	void setElements(String version) {
		// TODO Auto-generated method stub
		result=(version.equals("version1")?new Transition_V1():new Transition_V2());
	}
}
