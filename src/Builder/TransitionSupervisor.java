package Builder;

public abstract class TransitionSupervisor extends DiagramElementSupervisor{

	public TransitionSupervisor(DiagramElementBuilder builder) {
		super(builder);
	}
	public abstract Object construct();
}
