package Builder;

import Model.DiagramElement;
import Model.Transition_V1;

public abstract class DiagramElementBuilder {
	abstract void setElements(DiagramElement de);
	abstract void setShape(double... data);
	abstract void setPosition(double... data);
	abstract void setStyle(String style);
	abstract void setName(String name);
	abstract Object getResult();
	
}
