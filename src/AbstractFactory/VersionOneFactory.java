package AbstractFactory;

import Builder.DiagramElementBuilder;
import Builder.DiagramElementSupervisor;
import Builder.StateBuilder;
import Builder.State_V1_Supervisor;
import Builder.TransitionBuilder;
import Builder.TransitionSupervisor;
import Builder.Transition_V1_Supervisor;
import Model.Transition_V1;

public class VersionOneFactory implements StateDiagramAbstractFactory{

	public Object createTransition() {
		DiagramElementBuilder builder=new TransitionBuilder();
		DiagramElementSupervisor supervisor=new Transition_V1_Supervisor(builder);
		return supervisor.construct();
	}
	public Object createState() {
		DiagramElementBuilder builder=new StateBuilder();
		DiagramElementSupervisor supervisor=new State_V1_Supervisor(builder);
		return supervisor.construct();
	}
	public Object createStateDiagram() {
		DiagramElementBuilder builder=new StateBuilder();
		DiagramElementSupervisor supervisor=new State_V1_Supervisor(builder);
		return supervisor.construct();
	}
}
