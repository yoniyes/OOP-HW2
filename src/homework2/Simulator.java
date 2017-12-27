package homework2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * a simulator is a class for simulating a system represented by a bipartite graph.
 */
public class Simulator<L, D> {
	
	/**
	 * Abstraction function:	a simulator is identified by its label. it holds a bipartite graph that the
	 * 							simulator accesses to alter the graph for every simulation and system setup
	 * 							phase.
	 */
	
	/**
	 * Rep. Invariant:	none.
	 */
	
	private L _label;
	private BipartiteGraph<L> _graph;
	
	/**
	 * @return a new instance of this labeled 'simLabel'.
	 */
	public Simulator(L simLabel) {
		this._label = simLabel;
		this._graph = new BipartiteGraph<>();
	}
	
	/**
	 * @return this label.
	 */
	public L getLabel() {
		return this._label;
	}
	
	/**
	 * @return the data in node 'label'.
	 */
	public Object getNodeData(L label) {
		return this._graph.getNodeData(label);
	}
	
	/**
	 * @return an immutable collection of the edges in this.
	 */
	public Collection<L> getEdges() {
		List<L> list = new ArrayList<>();
		for (L nodeLabel : this._graph.listNodes(true)) {
			list.addAll(this._graph.findNode(nodeLabel).getParentsEdges().keySet());
			list.addAll(this._graph.findNode(nodeLabel).getChildrenEdges().keySet());
		}
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * @modifies this graph.
	 * @effects adds the labeled data as a pipe node to graph.
	 * @return if addition was successful.
	 */
	public boolean addPipeNode(L nodeLabel, Object nodeData) {
		return this._graph.addNode(nodeLabel, nodeData, true);
	}
	
	/**
	 * @modifies this graph.
	 * @effects adds the labeled data as a filter node to graph.
	 * @return if addition was successful.
	 */
	public boolean addFilterNode(L nodeLabel, Object nodeData) {
		return this._graph.addNode(nodeLabel, nodeData, false);
	}
	
	/**
	 * @modifies this graph.
	 * @effects creates an edge in the graph.
	 * @return if creation was successful.
	 */
	public boolean addEdge(L parent, L child, L edgeLabel) {
		return this._graph.addEdge(parent, child, edgeLabel);
	}

	/**
	 * @modifies this graph.
	 * @effects advances the simulator one time-slot forward.
	 */
	@SuppressWarnings("unchecked")
	public void simulate() {
		for (L nodeLabel : _graph.listNodes(true)) {
			Object pipeNode = _graph.getNodeData(nodeLabel);
			if (pipeNode instanceof Simulatable<?>) {
				((Simulatable<L>)pipeNode).simulate(_graph);
			}
		}
		for (L nodeLabel : _graph.listNodes(false)) {
			Object filterNode = _graph.getNodeData(nodeLabel);
			if (filterNode instanceof Simulatable<?>) {
				((Simulatable<L>)filterNode).simulate(_graph);
			}
		}
	}
}
