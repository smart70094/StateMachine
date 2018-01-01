package Builder;

import Model.DiagramElement;
import Model.State;
import Model.State_V1;
import Model.State_V2;

public class StateBuilder extends DiagramElementBuilder{
	State state;
	@Override
	void setElements(String version) {
		state=(version.equals("version1")?new State_V1():new State_V2());
	}

	@Override
	void setShape(double... data) {
		// TODO Auto-generated method stub
		double radius=data[0];
		state.radius=radius;
	}

	@Override
	void setPosition(double... data) {
		// TODO Auto-generated method stub
		double x=data[0];
		double y=data[1];
		state.x=x;
		state.y=y;
	}

	@Override
	void setStyle(String style) {
		// TODO Auto-generated method stub
		state.style=style;
	}

	@Override
	void setName(String name) {
		// TODO Auto-generated method stub
		state.name=name;
	}

	@Override
	Object getResult() {
		// TODO Auto-generated method stub
		return state;
	}

}
