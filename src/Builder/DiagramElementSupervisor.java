package Builder;

import Model.DiagramElement;

public abstract class DiagramElementSupervisor {
	DiagramElementBuilder builder;
	DiagramElement diagramElement;
	public DiagramElementSupervisor(DiagramElementBuilder builder) {
		this.builder=builder;
	}
	public abstract Object construct();

}
