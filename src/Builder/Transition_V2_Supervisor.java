package Builder;

import Model.Transition_V2;

public class Transition_V2_Supervisor extends TransitionSupervisor{

	public Transition_V2_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}
	
	public Object construct() {
		builder.setElements(new Transition_V2());
		builder.setShape(100, 10, 300, 10);
		builder.setPosition(100,100);
		builder.setName("Transition");
		builder.setStyle("TransitionStyle2");
		return builder.getResult();
	}
}
