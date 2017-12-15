package Builder;

public abstract class StateDiagramSupervisor extends DiagramElementSupervisor{

	public StateDiagramSupervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	public abstract Object construct();
}
