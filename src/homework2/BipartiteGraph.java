package homework2;

import java.util.Collection;

public class BipartiteGraph<L> {
	
	/**
	 * @return a new empty instance of this.
	 */
	public BipartiteGraph() {
		// TODO
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects adds label to the graph.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addNode(L label, boolean isBlack) {
		// TODO
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects removes label from the graph.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeNode(L label) {
		// TODO
	}
	
	public void addEdge(L from, L to, L label) {
		// TODO
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects removes label from the graph.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeEdge(L label) {
		// TODO
	}
	
	public Collection<L> listBlackNodes() {
		// TODO
	}
	
	public Collection<L> listWhiteNodes() {
		// TODO
	}
	
	public Collection<L> listChildren(L parent) {
		// TODO
	}
	
	public Collection<L> listParents(L child) {
		// TODO
	}
	
	public L getChildByEdgeLabel(L parent, L edgeLabel) {
		// TODO
	}
	
	public L getParentByEdgeLabel(L child, L edgeLabel) {
		// TODO
	}
	
	public Object findNode(L label) {
		// TODO
	}
}
