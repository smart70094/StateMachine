package Builder;

import Model.DiagramElement;
import Model.StateDiagram;
import Model.StateDiagram_V1;
import Model.StateDiagram_V2;

public class StateDiagramBuilder extends DiagramElementBuilder{
	StateDiagram result;
	@Override
	void setElements(String version) {
		result=(version.equals("version1")?new StateDiagram_V1():new StateDiagram_V2());
	}

	@Override
	void setShape(double... data) {
		// TODO Auto-generated method stub
		double width=data[0];
		double height=data[1];
		result.width=width;
		result.height=height;
	}

	@Override
	void setPosition(double... data) {
		double x=data[0];
		double y=data[1];
		result.x=x;
		result.y=y;
	}

	@Override
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

}
