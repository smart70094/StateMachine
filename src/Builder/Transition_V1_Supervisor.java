package Builder;

import Model.Transition_V1;

public class Transition_V1_Supervisor extends TransitionSupervisor{

	public Transition_V1_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}
	public Object construct() {
		builder.setElements(new Transition_V1());
		builder.setShape(100, 10, 300, 10);
		builder.setPosition(500,200);
		builder.setName("Transition");
		builder.setStyle("TransitionStyle1");
		return builder.getResult();
	}
}
