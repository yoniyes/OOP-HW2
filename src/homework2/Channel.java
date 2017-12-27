package homework2;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A channel is a pipe in a bipartite graph That transfers transactions between participants.
 * The channel has a capacity of transcation's value that it can hold at a specific time.
 * 
 * The class also implements Simulatable so that every simulation round allows the channel to
 * transfer it's transactions to the following participant (which is a pipe).
 */
public class Channel implements Simulatable<String> {
	
	/**
	 * Abstraction Function: The channel has a limit of transaction values. the transactions
	 * 						 are stored in a buffer and identified by a label
	 * 
	 * Rep. Invariant: limit >= 0, currentTotal >= 0
	 */
	
	private final double limit;
	private double currentTotal;
	private ArrayList<Transaction> buffer;
	private String label;
	
	private void checkRep(){
		assert(this.limit >= 0);
		assert(this.currentTotal >= 0);
	}
	/**
	 * @requires limit >= 0
	 * @modifies this
	 * @effects constructs a new Channel
	 */
	public Channel(String label, double limit){
		this.limit = limit;
		this.currentTotal = 0;
		this.buffer = new ArrayList<>();
		this.label = label;
		checkRep();
	}
	
	/**
	 * @return a boolean indicating whether or not adding another transaction
	 * 		   with the given value will make the channel overflow
	 */
	private boolean willOverflow(double value){
		checkRep();
		return currentTotal + value > limit;
	}
	
	/**
	 * @modifies this
	 * @effects simulates the channel's operation, by transferring all of its transactions
	 *          to the following participant.
	 */
	@Override
	public void simulate(BipartiteGraph<String> graph){
		checkRep();
		Participant p = (Participant)graph.getNodeData(graph.listChildren(label).iterator().next());
		
		Iterator<Transaction> it = buffer.iterator();
		while(it.hasNext()){
			Transaction tx = it.next();
			p.addTransaction(tx);
			currentTotal -= tx.getValue();
			it.remove();
		}
		checkRep();
	}
	
	/**
	 * @requires tx != null
	 * @modifies this
	 * @effects adds the given transaction to the channel's buffer if the channel's limit allows
	 * @return true iff the transaction was added to the channel
	 */
	public boolean pushTransaction(Transaction tx){
		checkRep();
		if(tx == null) throw new IllegalArgumentException("Transactions cannot be null");
		if (willOverflow(tx.getValue())) return false;
		buffer.add(tx);
		currentTotal += tx.getValue();
		checkRep();
		return true;
	}

	/**
	 * @return A string containing all the channel's transcation values, space-separated
	 */
	public String getTransactions(){
		checkRep();
		String s = new String();
		Iterator<Transaction> it = buffer.iterator();
		if(it.hasNext()){
			s += it.next().getValue();
		}
		while(it.hasNext()){
			s += " " + it.next().getValue();
		}
		checkRep();
		return s;
	}
}
