package homework2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Participant implements Simulatable<String> {
	
	private double fee;
	private String label;
	private List<Transaction> storageBuffer;
	private List<Transaction> workBuffer;
	private double balance;
	
	public Participant(String label, double fee) {
		if (fee < 0 || label == null) {
			throw new IllegalArgumentException("fee should be >= 0 and label should be != null");
		}
		this.fee = fee;
		this.label = label;
		this.storageBuffer = new ArrayList<>();
		this.workBuffer = new ArrayList<>();
		this.balance = 0;
	}
	
	public double getFee() {
		return this.fee;
	}
	public String getLabel() {
		return this.label;
	}
	
	public List<Transaction> getStorageBuffer() {
		return Collections.unmodifiableList(this.storageBuffer);
	}
	
	public List<Transaction> getWorkBuffer() {
		return this.workBuffer;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void addTransaction(Transaction tx) {
		this.workBuffer.add(tx);
	}
	
	private Transaction takeFee(Transaction tx) {
		if (tx == null) return null;
		if (tx.getDest().equals(this.getLabel())) {
			this.storageBuffer.add(tx);
			this.balance += tx.getValue();
			return null;
		}
		double takenFee = (tx.getValue() < this.getFee() ? tx.getValue() : this.getFee());
		if (takenFee > 0) {
			this.storageBuffer.add(new Transaction(this.getLabel(), takenFee));
		}
		this.balance += takenFee;
		return (takenFee == tx.getValue() ? 
				null : new Transaction(tx.getDest(), tx.getValue() - takenFee));
	}

	@Override
	public void simulate(BipartiteGraph<String> graph) {
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
			if (pipeOut == null) {
				it.remove();
			}
			else if (channel != null && !channel.willOverflow(pipeOut.getValue())) {
				channel.pushTransaction(pipeOut);
				it.remove();
			}
		}
	}

}
