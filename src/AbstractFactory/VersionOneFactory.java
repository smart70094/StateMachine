package AbstractFactory;

import Builder.DiagramElementBuilder;
import Builder.DiagramElementSupervisor;
import Builder.TransitionBuilder;
import Builder.TransitionSupervisor;
import Model.Transition_V1;

public class VersionOneFactory implements StateDiagramAbstractFactory{

	public Object createTransition() {
		DiagramElementBuilder builder=new TransitionBuilder();
		DiagramElementSupervisor supervisor=new TransitionSupervisor(builder);
		return supervisor.construct();
	}

}
