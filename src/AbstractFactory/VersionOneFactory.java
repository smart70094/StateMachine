package AbstractFactory;

import Model.Transition_V1;

public class VersionOneFactory implements StateDiagramAbstractFactory{

	
	public Object createTransition() {
		return new Transition_V1();
	}

}
