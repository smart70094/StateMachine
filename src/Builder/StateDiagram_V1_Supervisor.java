package Builder;

import Model.StateDiagram;
import Model.State_V1;

public class StateDiagram_V1_Supervisor extends StateDiagramSupervisor{

	public StateDiagram_V1_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	@Override
	public Object construct() {
		builder.setElements(new StateDiagram());
		builder.setShape(600,600);
		builder.setPosition(100,100);
		builder.setName("StateDiagram");
		builder.setStyle("StateDiagramStyle1");
		return builder.getResult();
	}

}
