package Builder;

public class TransitionSupervisor extends DiagramElementSupervisor{

	public TransitionSupervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	public Object construct() {
		builder.setShape(100, 10, 300, 10);
		builder.setPosition(100,100);
		builder.setName("Transition");
		return builder.getResult();
	}
}
