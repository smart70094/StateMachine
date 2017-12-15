package Builder;

import Model.State_V1;

public class State_V1_Supervisor extends StateSupervisor{

	public State_V1_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	@Override
	public Object construct() {
		builder.setElements(new State_V1());
		builder.setShape(50);
		builder.setPosition(100,100);
		builder.setName("State");
		builder.setStyle("StateStyle1");
		return builder.getResult();
	}
	


}
