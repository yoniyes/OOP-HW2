package homework2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A bipartite graph is a graph with a group of white nodes and a group of black nodes,
 * while all the edges cross from one group to the other. Nodes have unique labels and
 * edges are uniquely recognized by their label and one of their ends.
 */
public class BipartiteGraph<L> {
	
	//TODO rep. inv.
	
	//TODO abs. func.
	
	//TODO checkRep().
	
	//TODO think about exceptions.
	
	//TODO specs and doc for Node.
	private class Node<K> {
		
		//TODO rep. inv.
		
		//TODO abs. func.
		
		//TODO checkRep().

		private Object data;
		private K label;
		private Map<K, Node<K>> children;
		private Map<K, Node<K>> parents;
		private boolean isBlack;
		
		public Node(K label, Object data, boolean isBlack) {
			this.data = data;
			this.label = label;
			this.children = new HashMap<>();
			this.parents = new HashMap<>();
			this.isBlack = isBlack;
		}
		
		public boolean isBlack() {
			return this.isBlack;
		}
		
		public Object getData() {
			return this.data;
		}
		
		public void setData(Object data) {
			this.data = data;
		}
		
		public K getLabel() {
			return this.label;
		}
		
		public void setLabel(K label) {
			this.label = label;
		}
		
		public Map<K, Node<K>> getChildren() {
			return this.children;
		}
		
		public void addChild(K label, Node<K> child) {
			this.children.put(label, child);
		}
		
		public Map<K, Node<K>> getParents() {
			return this.parents;
		}
		
		public void addParent(K label, Node<K> parent) {
			this.parents.put(label, parent);
		}
	}
	
	Map<L, Node<L>> nodes;
		
	/**
	 * @return a new empty instance of this.
	 */
	public BipartiteGraph() {
		this.nodes = new HashMap<>();
	}
	
	/**
	 * @return the object in node 'label'. if not found, null is returned.
	 */
	public Node<L> findNode(L label) {
		return this.nodes.get(label);
	}
	
	/**
	 * @requires (label, data) != null.
	 * @modifies this.
	 * @effects adds label to the graph's nodes.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addNode(L label, Object data, boolean isBlack) {
//		if (label == null) {
//			throw new IllegalArgumentException();
//		}
		if (this.findNode(label) != null) {
			return false;
		}
		this.nodes.put(label, new Node<L>(label, data, isBlack));
		return true;
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects removes label from the graph's nodes.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeNode(L label) {
		return this.nodes.remove(label) != null;
	}
	
	/**
	 * @requires (from, to, label) != null.
	 * @modifies this.
	 * @effects adds label to the graph's edges.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addEdge(L from, L to, L label) {
		Node<L> _from = this.findNode(from);
		Node<L> _to = this.findNode(to);
		if (!_from.getChildren().containsKey(label) && !_to.getParents().containsKey(label)) {
			_from.addChild(label, _to);
			_to.addParent(label, _from);
			return true;
		}
		return false;
	}
	
	/**
	 * @requires label != null.
	 * @modifies this.
	 * @effects removes label from the graph's edges.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeEdge(L label, L from) {
		L to = this.findNode(from).getChildren().get(label).getLabel();
		this.findNode(from).getChildren().remove(label);
		return this.findNode(to).getParents().remove(label) != null;
	}
	
	/**
	 * @return a collection of the graph's black or white nodes.
	 */
	public Collection<L> listNodes(boolean isBlack) {
		List<L> list = new ArrayList<>();
		for (Map.Entry<L, Node<L>> entry : nodes.entrySet()) {
			if (entry.getValue().isBlack() == isBlack) {
				list.add(entry.getKey());
			}
		}
		return list;
	}
	
	/**
	 * @requires parent != null
	 * @return a collection of the parent's children.
	 */
	public Collection<L> listChildren(L parent) {
		List<L> list = new ArrayList<>();
		for (Map.Entry<L, Node<L>> entry : this.findNode(parent).getChildren().entrySet()) {
			list.add(entry.getValue().getLabel());
		}
		return list;
	}
	
	/**
	 * @requires child != null
	 * @return a collection of the child's parents.
	 */
	public Collection<L> listParents(L child) {
		List<L> list = new ArrayList<>();
		for (Map.Entry<L, Node<L>> entry : this.findNode(child).getParents().entrySet()) {
			list.add(entry.getValue().getLabel());
		}
		return list;
	}
	
	/**
	 * @requires (parent, edgeLabel) != null
	 * @return the child node of the edge.
	 */
	public L getChildByEdgeLabel(L parent, L edgeLabel) {
		return this.findNode(parent).getChildren().get(edgeLabel).getLabel();
	}
	
	/**
	 * @requires (child, edgeLabel) != null
	 * @return the parent node of the edge.
	 */
	public L getParentByEdgeLabel(L child, L edgeLabel) {
		return this.findNode(child).getParents().get(edgeLabel).getLabel();
	}
	
	/**
	 * @modifies this
	 * @effects resets the graph to initial state.
	 */
	public void clear() {
		this.nodes.clear();
	}
}
