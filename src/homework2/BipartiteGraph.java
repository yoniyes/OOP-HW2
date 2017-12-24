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
	 * @effects adds label to the graph's nodes.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addNode(L label, boolean isBlack) {
		// TODO
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects removes label from the graph's nodes.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeNode(L label) {
		// TODO
	}
	
	/**
	 * @requires (from, to, label) != null.
	 * @modifies this.
	 * @effects adds label to the graph's edges.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addEdge(L from, L to, L label) {
		// TODO
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects removes label from the graph's edges.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeEdge(L label) {
		// TODO
	}
	
	/**
	 * @return a collection of the graph's black or white nodes.
	 */
	public Collection<L> listNodes(boolean isBlack) {
		// TODO
	}
	
	/**
	 * @requires parent != null
	 * @return a collection of the parent's children.
	 */
	public Collection<L> listChildren(L parent) {
		// TODO
	}
	
	/**
	 * @requires child != null
	 * @return a collection of the child's parents.
	 */
	public Collection<L> listParents(L child) {
		// TODO
	}
	
	/**
	 * @requires (parent, edgeLabel) != null
	 * @return the child node of the edge.
	 */
	public L getChildByEdgeLabel(L parent, L edgeLabel) {
		// TODO
	}
	
	/**
	 * @requires (child, edgeLabel) != null
	 * @return the parent node of the edge.
	 */
	public L getParentByEdgeLabel(L child, L edgeLabel) {
		// TODO
	}
	
	/**
	 * @return the object in node 'label'. if not found, null is returned.
	 */
	public Object findNode(L label) {
		// TODO
	}
}
