package Builder;

import Model.DiagramElement;

public abstract class StateSupervisor extends DiagramElementSupervisor{

	public StateSupervisor(DiagramElementBuilder builder) {
		super(builder);
	}
	public abstract Object construct();
}
