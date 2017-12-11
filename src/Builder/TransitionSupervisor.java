package Builder;

public class TransitionSupervisor extends DiagramElementSupervisor{

	public TransitionSupervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	void construct() {
		builder.setShape(100,100,200,100);
		builder.setPosition(100,100);
		builder.setName("Transition");
	}

}
