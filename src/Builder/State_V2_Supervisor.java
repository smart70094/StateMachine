package Builder;

import Model.State_V2;
import Model.Transition_V2;

public class State_V2_Supervisor extends DiagramElementSupervisor{

	public State_V2_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}
	@Override
	public Object construct() {
		builder.setElements("version2");
		builder.setShape(50);
		builder.setPosition(500,200);
		builder.setName("State");
		builder.setStyle("StateStyle2");
		return builder.getResult();
	}

}
