package Builder;

import Model.DiagramElement;

public abstract class DiagramElementBuilder {
	DiagramElement diagramElement;
	abstract void setShape(double... data);
	abstract void setPosition(double... data);
	abstract void setName(String name);
	DiagramElement getResult() {
		return diagramElement;
	}
	
}
