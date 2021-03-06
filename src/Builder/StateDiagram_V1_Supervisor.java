package Builder;


import Model.StateDiagram_V1;


public class StateDiagram_V1_Supervisor extends StateDiagramSupervisor{

	public StateDiagram_V1_Supervisor(DiagramElementBuilder builder) {
		super(builder);
	}

	@Override
	public Object construct() {
		builder.setElements("version1");
		builder.setShape(600,600);
		builder.setPosition(370,100);
		builder.setName("StateDiagram");
		builder.setStyle("StateDiagramStyle1");
		return builder.getResult();
	}

}
