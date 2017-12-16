package AbstractFactory;

import Builder.DiagramElementBuilder;
import Builder.DiagramElementSupervisor;
import Builder.StateBuilder;
import Builder.StateDiagramBuilder;
import Builder.StateDiagram_V2_Supervisor;
import Builder.State_V1_Supervisor;
import Builder.State_V2_Supervisor;
import Builder.TransitionBuilder;
import Builder.TransitionSupervisor;
import Builder.Transition_V2_Supervisor;

public class VersionTwoFactory implements StateDiagramAbstractFactory{

	public Object createTransition() {
		DiagramElementBuilder builder=new TransitionBuilder();
		DiagramElementSupervisor supervisor=new Transition_V2_Supervisor(builder);
		return supervisor.construct();
	}
	public Object createState() {
		DiagramElementBuilder builder=new StateBuilder();
		DiagramElementSupervisor supervisor=new State_V2_Supervisor(builder);
		return supervisor.construct();
	}
	public Object createStateDiagram() {
		DiagramElementBuilder builder=new StateDiagramBuilder();
		DiagramElementSupervisor supervisor=new StateDiagram_V2_Supervisor(builder);
		return supervisor.construct();
	}

}
