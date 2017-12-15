package AbstractFactory;

public interface StateDiagramAbstractFactory {
	Object createTransition();
	Object createState();
	Object createStateDiagram();
}
