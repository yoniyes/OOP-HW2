package homework2;

/**
 * This class implements a transaction in a graph of payment channels.
 * A transaction is immutable.
 * A transaction has a dest who is a participant in a payment channel graph and a value.
 */

public class Transaction {
    private final double value;
    private final String dest;
   
    
	/**
	 * @requires value > 0, the destination must exist in the graph
	 * @modifies this
	 * @effects Constructs a new transaction
	 */
    public Transaction(String dest, double value) {
        this.dest = dest;
        this.value = value;
        assert value > 0;
    }
    
	/**
	 * @effects Returns the value of the transaction
	 */
    public double getValue() {
        return value;
    }
    
	/**
	 * @effects Returns the destination of the transaction
	 */
    public String getDest() {
        return dest;
    }
    
    @Override
    public String toString() {
        return "Transaction: " + this.hashCode() + " Dest: " + dest + " Value: " + value;
    }
    
    
}
