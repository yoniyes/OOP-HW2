package homework2;

import java.util.ArrayList;
import java.util.Iterator;

public class Channel implements Simulatable<String> {
	
	private final double limit;
	private double currentTotal;
	private ArrayList<Transaction> buffer;
	private String label;
	
	public Channel(String label, double limit){
		this.limit = limit;
		this.currentTotal = 0;
		this.buffer = new ArrayList<>();
		this.label = label;
	}
	
	private boolean willOverflow(double value){
		return currentTotal + value > limit;
	}
	
	@Override
	public void simulate(BipartiteGraph<String> graph){
		Participant p = (Participant)graph.getNodeData(graph.listChildren(label).iterator().next());
		
		Iterator<Transaction> it = buffer.iterator();
		while(it.hasNext()){
			Transaction tx = it.next();
			p.addTransaction(tx);
			currentTotal -= tx.getValue();
			it.remove();
		}
	}
	
	public boolean pushTransaction(Transaction tx){
		if (willOverflow(tx.getValue())) return false;
		buffer.add(tx);
		return true;
	}

	public String getTransactions(){
		String s = new String();
		Iterator<Transaction> it = buffer.iterator();
		if(it.hasNext()){
			s += it.next().getValue();
		}
		while(it.hasNext()){
			s += " " + it.next().getValue();
		}
		return s;
	}
}
