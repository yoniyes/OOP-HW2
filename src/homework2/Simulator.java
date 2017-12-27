package homework2;

// <L, D> --> <label type, data type>
public class Simulator<L, D> {
	
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
	
	public boolean addPipeNode(L nodeLabel, D nodeData) {
		return this._graph.addNode(nodeLabel, nodeData, true);
	}
	
	public boolean addFilterNode(L nodeLabel, D nodeData) {
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
	public void simulate() {
		for (L nodeLabel : _graph.listNodes(true)) {
			Object pipeNode = _graph.getNodeData(nodeLabel);
			if (pipeNode instanceof Simulatable<?>) {
				((Simulatable<L>)pipeNode).simulate(_graph);
			}
			//TODO is that it?
		}
		for (L nodeLabel : _graph.listNodes(false)) {
			Object filterNode = _graph.getNodeData(nodeLabel);
			if (filterNode instanceof Simulatable<?>) {
				((Simulatable<L>)filterNode).simulate(_graph);
			}
			//TODO is that it?
		}
		this._round++;
	}

}
