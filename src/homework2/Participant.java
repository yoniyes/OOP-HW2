package homework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * a participant is a filter in a graph that can be simulated. it takes a fee from every transaction that it
 * passes on and adds it to it's balance.
 */
public class Participant implements Simulatable<String> {
	
	/**
	 * Abstraction function:	this class represents a filter node of type participant that can take a fee,
	 * 							can be identified using a string label, stores it's transaction history in a
	 * 							storage buffer and works on transactions in a work buffer. it's balance is 
	 * 							the sum of all the transactions' values in it's storage buffer.
	 */
	
	/**
	 * Rep. Invariant:	(fee, balance) >= 0
	 */
	
	private void checkRep() {
		assert(fee >= 0);
		assert(balance >= 0);
	}
	
	private final double fee;
	private String label;
	private List<Transaction> storageBuffer;
	private List<Transaction> workBuffer;
	private double balance;
	
	/**
	 * @requires fee >= 0
	 * @return a new instance of Participant labeled 'label' with fee 'fee'.
	 */
	public Participant(String label, double fee) {
		if (fee < 0 || label == null) {
			throw new IllegalArgumentException("fee should be >= 0 and label should be != null");
		}
		this.fee = fee;
		this.label = label;
		this.storageBuffer = new ArrayList<>();
		this.workBuffer = new ArrayList<>();
		this.balance = 0;
		this.checkRep();
	}
	
	/**
	 * @return this fee.
	 */
	public double getFee() {
		this.checkRep();
		return this.fee;
	}
	
	/**
	 * @return the label of this.
	 */
	public String getLabel() {
		this.checkRep();
		return this.label;
	}
	
	/**
	 * @return an immutable list of transactions that passed through and were fined by this.
	 */
	public List<Transaction> getStorageBuffer() {
		this.checkRep();
		return Collections.unmodifiableList(this.storageBuffer);
	}
	
	/**
	 * @return a list of the pre-processed transactions in this.
	 */
	public List<Transaction> getWorkBuffer() {
		this.checkRep();
		return this.workBuffer;
	}
	
	/**
	 * @return the balance of this.
	 */
	public double getBalance() {
		this.checkRep();
		return this.balance;
	}
	
	/**
	 * @modifies this work buffer.
	 * @effects adds the given transaction to the work buffer of this.
	 */
	public void addTransaction(Transaction tx) {
		this.checkRep();
		if (tx != null) this.workBuffer.add(tx);
		this.checkRep();
	}
	
	/**
	 * @modifies this balance, this storage buffer.
	 * @effects takes a fee from transaction and adds it to the storage buffer of this along with balance update.
	 * @return a new transaction with the remaining value. if no value is left, null is returned.
	 */
	private Transaction takeFee(Transaction tx) {
		this.checkRep();
		if (tx == null) return null;
		if (tx.getDest().equals(this.getLabel())) {
			this.storageBuffer.add(tx);
			this.balance += tx.getValue();
			this.checkRep();
			return null;
		}
		double takenFee = (tx.getValue() < this.getFee() ? tx.getValue() : this.getFee());
		if (takenFee > 0) {
			this.storageBuffer.add(new Transaction(this.getLabel(), takenFee));
		}
		this.balance += takenFee;
		this.checkRep();
		return (takenFee == tx.getValue() ? 
				null : new Transaction(tx.getDest(), tx.getValue() - takenFee));
	}

	/**
	 * @modifies this, graph
	 * @effects iterates over all transactions in the work buffer and passes them to the out channel. 
	 * 			takes fees along the way.
	 */
	@Override
	public void simulate(BipartiteGraph<String> graph) {
		this.checkRep();
		// Get the out channel.
		Channel channel = null;
		Iterator<String> channelIt = graph.listChildren(this.label).iterator();
		while (channelIt.hasNext()) {
			String label = channelIt.next();
			channel = (Channel)graph.getNodeData(label);
		}
		// Iterate over transactions.
		Iterator<Transaction> it = this.workBuffer.iterator();
		while (it.hasNext()) {
			Transaction tx = it.next();
			Transaction pipeOut = this.takeFee(tx);
			if (pipeOut == null || (channel != null && channel.pushTransaction(pipeOut))) {
				it.remove();
			}
		}
		this.checkRep();
	}

}
