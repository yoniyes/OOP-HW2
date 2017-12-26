package homework2;

// <L, D> --> <label type, data type>
public class Simulator<L, D> implements Simulatable<L> {
	
	private L _label;
	private int _round;
	private int _numOfRounds;
	private BipartiteGraph<L> _graph;
	
	public Simulator(L simLabel, int numOfRounds) {
		this._label = simLabel;
		this._round = 0;
		this._numOfRounds = numOfRounds;
		this._graph = new BipartiteGraph<>();
	}
	
	public L getLabel() {
		return this._label;
	}
	
	public int getRound() {
		return this._round;
	}
	
	public int getNumOfRounds() {
		return this._numOfRounds;
	}
	
	public void setNumOfRounds(int numOfRounds) {
		this._numOfRounds = numOfRounds;
	}
	
	public boolean addBlackNode(L nodeLabel, D nodeData) {
		return this._graph.addNode(nodeLabel, nodeData, true);
	}
	
	public boolean addWhiteNode(L nodeLabel, D nodeData) {
		return this._graph.addNode(nodeLabel, nodeData, false);
	}
	
	public boolean addEdge(L parent, L child, L edgeLabel) {
		return this._graph.addEdge(parent, child, edgeLabel);
	}
	
	public void reset() {
		this._round = 0;
	}
	
	public boolean isSimulationOver() {
		return this.getRound() >= this.getNumOfRounds();
	}

//	@SuppressWarnings("unchecked")
	@Override
	public void simulate(BipartiteGraph<L> graph) {
		for (L nodeLabel : graph.listNodes(true)) {
			Object pipeNode = graph.getNodeData(nodeLabel);
			if (pipeNode instanceof Simulatable<?>) {
				((Simulatable<L>)pipeNode).simulate(graph);
			}
			//TODO is that it?
		}
		for (L nodeLabel : graph.listNodes(false)) {
			Object filterNode = graph.getNodeData(nodeLabel);
			if (filterNode instanceof Simulatable<?>) {
				((Simulatable<L>)filterNode).simulate(graph);
			}
			//TODO is that it?
		}
		this._round++;
	}

}
