package Builder;

import Model.StateDiagram_V1;
import Model.StateDiagram_V2;

public class StateDiagram_V2_Supervisor  extends StateDiagramSupervisor{

	public StateDiagram_V2_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	@Override
	public Object construct() {
		builder.setElements(new StateDiagram_V2());
		builder.setShape(600,600);
		builder.setPosition(100,70);
		builder.setName("StateDiagram");
		builder.setStyle("StateDiagramStyle2");
		return builder.getResult();
	}

}
