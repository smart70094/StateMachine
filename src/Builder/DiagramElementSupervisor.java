package Builder;

import Model.DiagramElement;

public abstract class DiagramElementSupervisor {
	DiagramElementBuilder builder;
	DiagramElement diagramElement;
	public DiagramElementSupervisor(DiagramElementBuilder builder) {
		this.builder=builder;
	}
	abstract void construct();
	DiagramElement getResult() {
		return diagramElement;
	}
}
