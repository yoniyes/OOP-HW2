package homework2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.javafx.UnmodifiableArrayList;


/**
 * A bipartite graph is a graph with a group of white nodes and a group of black nodes,
 * while all the edges cross from one group to the other. Nodes have unique labels and
 * edges are uniquely recognized by their label and one of their ends.
 */
public class BipartiteGraph<L> {
	
	/**
	 * Abstraction function:	The class represents a bipartite graph where every node contains it's
	 * 							color inside of it. The nodes and their relations are stored in a field
	 * 							that maps node labels to node objects.
	 * 							Edges in the graph are defined by having a relation between two nodes 
	 * 							such that:
	 * 							Let A,B be nodes and let E be an edge in the graph.
	 * 							If (A.children.get(E) == B && B.parents.get(E) == A), then E is a 
	 * 							directed edge (A --E--> B).
	 */
	
	/**
	 * Rep. Invariant:	nodes != null
	 * 					for all edges touching node V:
	 * 						V.color != otherEndOfEdges.color
	 * 					all edges touching a node are uniquely labeled.
	 */
	
	private void checkRep() {
		assert(this.nodes != null);
		for(Node<L> node : nodes.values()) {
			for(Node<L> otherEnd : node.getChildrenEdges().values()) {
				assert(node.isBlack() != otherEnd.isBlack());
			}
			for(Node<L> otherEnd : node.getParentsEdges().values()) {
				assert(node.isBlack() != otherEnd.isBlack());
			}
			Set<L> childrenEdgeSet = new HashSet<>(node.getChildrenEdges().keySet());
			Set<L> parentsEdgeSet = new HashSet<>(node.getParentsEdges().keySet());
			assert(node.getChildrenEdges().keySet().size() == childrenEdgeSet.size());
			assert(node.getParentsEdges().keySet().size() == parentsEdgeSet.size());
			childrenEdgeSet.retainAll(parentsEdgeSet);
			assert(childrenEdgeSet.size() == 0);
		}
	}
	
	/**
	 * A Node is an object containing data and identified by a label.
	 * It can have incoming (parent) and outgoing (child) edges.
	 * It can be classified as black or not-black.
	 */
	public class Node<K> {
		
		/**
		 * Abstraction function:	The class represents a node that is labeled by some object of type K
		 * 							and contains data of type Object. It stores the child edges in the
		 * 							field 'children', and they are identified by the edge label which is
		 * 							also of type K. The same is done for parent edges. The color of the 
		 * 							node is either black or not based on the boolean field 'isBlack'.
		 */
		
		/**
		 * Rep. Invariant:	(children, parents, childrenLabels, parentLabels) != null
		 */
		
		private void checkRep() {
			assert(this.children != null);
			assert(this.parents != null);
			assert(this.childrenLabels != null);
			assert(this.parentLabels != null);
		}

		private Object data;
		private K label;
		private Map<K, Node<K>> children;
		private Map<K, Node<K>> parents;
		private List<K> childrenLabels;
		private List<K> parentLabels;
		private boolean isBlack;
		
		/**
		 * @return a new instance of this with the (label, data, isBlack).
		 */
		public Node(K label, Object data, boolean isBlack) {
			this.data = data;
			this.label = label;
			this.children = new HashMap<>();
			this.parents = new HashMap<>();
			this.childrenLabels = new ArrayList<>();
			this.parentLabels = new ArrayList<>();
			this.isBlack = isBlack;
			this.checkRep();
		}
		
		/**
		 * @return true if the node is black, false otherwise.
		 */
		public boolean isBlack() {
			this.checkRep();
			return this.isBlack;
		}
		
		/**
		 * @return the data object of this.
		 */
		public Object getData() {
			this.checkRep();
			return this.data;
		}
		
		/**
		 * @modifies this.
		 * @effects the data in this is set to 'data'.
		 */
		public void setData(Object data) {
			this.checkRep();
			this.data = data;
			this.checkRep();
		}
		
		/**
		 * @return the label object of this.
		 */
		public K getLabel() {
			this.checkRep();
			return this.label;
		}
		
		/**
		 * @modifies this.
		 * @effects the label of this is set to 'label'.
		 */
		public void setLabel(K label) {
			this.checkRep();
			this.label = label;
			this.checkRep();
		}
		
		/**
		 * @return a map of children nodes in the form of  (edgeLabel --> childNode).
		 */
		public Map<K, Node<K>> getChildrenEdges() {
			this.checkRep();
			return this.children;
		}
		
		/**
		 * @return a map of children nodes in the form of  (edgeLabel --> childNode).
		 */
		public List<K> getChildrenLabels() {
			this.checkRep();
			return this.childrenLabels;
		}
		
		/**
		 * @requires (child) != null.
		 * @modifies this.
		 * @effects an edge is drawn from this to child.
		 */
		public void addChild(K edgeLabel, Node<K> child) {
			this.checkRep();
			this.children.put(edgeLabel, child);
			if (!this.childrenLabels.contains(child.getLabel())) {
				this.childrenLabels.add(child.getLabel());
			}
			this.checkRep();
		}
		
		/**
		 * @return a map of parent nodes in the form of  (edgeLabel --> parentNode).
		 */
		public Map<K, Node<K>> getParentsEdges() {
			this.checkRep();
			return this.parents;
		}
		
		/**
		 * @return a map of parent nodes in the form of  (edgeLabel --> parentNode).
		 */
		public List<K> getParentsLabels() {
			this.checkRep();
			return this.parentLabels;
		}
		
		/**
		 * @requires (edgeLabel, child) != null.
		 * @modifies this.
		 * @effects an edge is drawn from parent to this.
		 */
		public void addParent(K edgeLabel, Node<K> parent) {
			this.checkRep();
			this.parents.put(edgeLabel, parent);
			if (!this.parentLabels.contains(parent.getLabel())) {
				this.parentLabels.add(parent.getLabel());
			}
			this.checkRep();
		}
		
		/**
		 * @modifies this.edges.
		 * @effects removes the node child relation from this.
		 */
		public void removeChild(K label) {
			this.getChildrenLabels().remove(label);
			K removedEdgeLabel = null;
			for (Map.Entry<K, Node<K>> childEdge : this.getChildrenEdges().entrySet()) {
				if (childEdge.getValue().getLabel().equals(label)) {
					removedEdgeLabel = childEdge.getKey();
//					this.getChildrenEdges().remove(childEdge.getKey());
				}
			}
			if (removedEdgeLabel != null) {
				this.getChildrenEdges().remove(removedEdgeLabel);
			}
		}
		
		/**
		 * @modifies this.edges.
		 * @effects removes the node parent relation from this.
		 */
		public void removeParent(K label) {
			this.getParentsLabels().remove(label);
			K removedEdgeLabel = null;
			for (Map.Entry<K, Node<K>> parentEdge : this.getParentsEdges().entrySet()) {
				if (parentEdge.getValue().getLabel().equals(label)) {
					removedEdgeLabel = parentEdge.getKey();
//					this.getParentsEdges().remove(parentEdge.getKey());
				}
			}
			if (removedEdgeLabel != null) {
				this.getParentsEdges().remove(removedEdgeLabel);
			}
		}
	}
	
	Map<L, Node<L>> nodes;
		
	/**
	 * @return a new empty instance of this.
	 */
	public BipartiteGraph() {
		this.nodes = new HashMap<>();
		this.checkRep();
	}
	
	/**
	 * @return the object in node 'label'. if not found, null is returned.
	 */
	public Node<L> findNode(L label) {
		this.checkRep();
		return this.nodes.get(label);
	}
	
	/**
	 * @requires data != null.
	 * @modifies this.
	 * @effects adds label to the graph's nodes.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addNode(L label, Object data, boolean isBlack) {
		this.checkRep();
		if (this.findNode(label) != null) {
			this.checkRep();
			return false;
		}
		this.nodes.put(label, new Node<L>(label, data, isBlack));
		this.checkRep();
		return true;
	}
	
	/**
	 * @modifies this.
	 * @effects removes label from the graph's nodes.
	 * @return a boolean value representing if the label was removed.
	 */
	public boolean removeNode(L label) {
		this.checkRep();
		boolean res = (this.nodes.remove(label) != null);
		for (Node<L> node : this.nodes.values()) {
			node.removeParent(label);
			node.removeChild(label);
		}
		this.checkRep();
		return res;
	}
	
	/**
	 * @modifies this.
	 * @effects adds label to the graph's edges.
	 * @return a boolean value representing if the label was added.
	 */
	public boolean addEdge(L from, L to, L label) {
		this.checkRep();
		Node<L> _from = this.findNode(from);
		Node<L> _to = this.findNode(to);
		if(_to == null || _from == null) return false;
		if (!_from.getChildrenEdges().containsKey(label) && !_to.getParentsEdges().containsKey(label) &&
				!_from.getParentsEdges().containsKey(label) && !_to.getChildrenEdges().containsKey(label) &&
				_from.isBlack() != _to.isBlack()) {
			_from.addChild(label, _to);
			_to.addParent(label, _from);
			this.checkRep();
			return true;
		}
		this.checkRep();
		return false;
	}
	
	/**
	 * @modifies this.
	 * @effects removes label from the graph's edges.
	 */
	public void removeEdge(L label, L from) {
		this.checkRep();
		Node<L> _from = this.findNode(from);
		L to = null;
		if (_from != null) {
			if (_from.getChildrenEdges().get(label) != null){
				to = _from.getChildrenEdges().get(label).getLabel();
			}
			_from.removeChild(to);
		}
		Node<L> _to = this.findNode(to);
		if (_to != null) {
			_to.removeParent(from);
		}
		this.checkRep();
	}
	
	/**
	 * @return an immutable collection of the graph's black or white nodes labels.
	 */
	public UnmodifiableArrayList<L> listNodes(boolean isBlack) {
		this.checkRep();
		List<Object> list = new ArrayList<>();
		for (Map.Entry<L, Node<L>> entry : nodes.entrySet()) {
			if (entry.getValue().isBlack() == isBlack) {
				list.add(entry.getKey());
			}
		}
		this.checkRep();
		return new UnmodifiableArrayList<L>((L[])(list.toArray()), list.size());
	}
	
	/**
	 * @return an immutable collection of the parent's children.
	 */
	public UnmodifiableArrayList<L> listChildren(L parent) {
		this.checkRep();
		List<L> list = new ArrayList<>();
		if (this.findNode(parent) != null) {
			list = this.findNode(parent).getChildrenLabels();
		}
		return new UnmodifiableArrayList<L>((L[])(list.toArray()), list.size());
	}
	
	/**
	 * @return a collection of the child's parents.
	 */
	public UnmodifiableArrayList<L> listParents(L child) {
		this.checkRep();
		List<L> list = new ArrayList<>();
		if (this.findNode(child) != null) {
			list = this.findNode(child).getParentsLabels();
		}
		return new UnmodifiableArrayList<L>((L[])(list.toArray()), list.size());
	}
	
	/**
	 * @return the child node of the edge.
	 */
	public L getChildByEdgeLabel(L parent, L edgeLabel) {
		this.checkRep();
		return (this.findNode(parent) != null && this.findNode(parent).getChildrenEdges().get(edgeLabel) != null ? 
				this.findNode(parent).getChildrenEdges().get(edgeLabel).getLabel() : null);
	}
	
	/**
	 * @return the parent node of the edge.
	 */
	public L getParentByEdgeLabel(L child, L edgeLabel) {
		this.checkRep();
		return (this.findNode(child) != null && this.findNode(child).getChildrenEdges().get(edgeLabel) != null ? 
				this.findNode(child).getParentsEdges().get(edgeLabel).getLabel() : null);
	}
	
	/**
	 * @return the data in the node with the given label. if doesn't exist, null is returned.
	 */
	public Object getNodeData(L label) {
		this.checkRep();
		return (this.findNode(label) != null ? 
				this.findNode(label).getData() : null);
	}
	
	/**
	 * @modifies this
	 * @effects resets the graph to initial state.
	 */
	public void clear() {
		this.checkRep();
		this.nodes.clear();
		this.checkRep();
	}
}
