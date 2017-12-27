package homework2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class implements a testing driver for Simulator. The driver manages
 * Simulators for payment channels
 */
public class SimulatorTestDriver {

	private Map<String, Simulator<String, Transaction>> simulators;

	/**
	 * @modifies this
	 * @effects Constructs a new test driver.
	 */
	public SimulatorTestDriver() {
        simulators = new HashMap<>();
	}

	/**
	 * @requires simName != null
	 * @modifies this
	 * @effects Creates a new simulator named simName. The simulator's graph is
	 *          initially empty.
	 */
	public void createSimulator(String simName) {
	    simulators.put(simName, new Simulator<String, Transaction>(simName, 0));
	}

	/**
	 * @requires createSimulator(simName) 
     *           && channelName != null && channelName has
	 *           not been used in a previous addChannel()  or
	 *           addParticipant() call on this object
	 *           limit > 0
	 * @modifies simulator named simName
	 * @effects Creates a new Channel named by the String channelName, with a limit, and add it to
	 *          the simulator named simName.
	 */
	public void addChannel(String simName, String channelName, double limit) {
	    simulators.get(simName).addPipeNode(channelName, new Channel(channelName, limit));
	}

	/**
	 * @requires createSimulator(simName) && participantName != null 
	 *           && participantName has not been used in a previous addParticipant(), addChannel()
	 *           call on this object
	 *           fee > 0
	 * @modifies simulator named simName
	 * @effects Creates a new Participant named by the String participantName and add
	 *          it to the simulator named simName.
	 */
	public void addParticipant(String simName, String participantName, double fee) {
        simulators.get(simName).addFilterNode(participantName, new Participant(participantName, fee));
	}

	/**
	 * @requires createSimulator(simName) && ((addPipe(parentName) &&
	 *           addFilter(childName)) || (addFilter(parentName) &&
	 *           addPipe(childName))) && edgeLabel != null && node named
	 *           parentName has no other outgoing edge labeled edgeLabel 
	 *           && node named childName has no other incoming edge labeled edgeLabel
	 * @modifies simulator named simName
	 * @effects Adds an edge from the node named parentName to the node named
	 *          childName in the simulator named simName. The new edge's label
	 *          is the String edgeLabel.
	 */
	public void addEdge(String simName, String parentName, String childName, String edgeLabel) {
        simulators.get(simName).addEdge(parentName, childName, edgeLabel);
	}

	/**
	 * @requires createSimulator(simName) && addChannel(channelName)
	 *           A transaction Transaction != null
	 * @modifies channel named channelName
	 * @effects pushes the Transaction into the channel named channelName in the
	 *          simulator named simName.
	 */
	public void sendTransaction(String simName, String channelName, Transaction tx) {
        Object c = simulators.get(simName).getNodeData(channelName);
        if (c instanceof Channel){
        	((Channel)c).pushTransaction(tx);
        }
    }
	
	
	/**
	 * @requires addChannel(channelName)
	 * @return a space-separated list of the Transaction values currently in the
	 *         channel named channelName in the simulator named simName.
	 */
	public String listContents(String simName, String channelName) {
		Object c = simulators.get(simName).getNodeData(channelName);
        if (c instanceof Channel){
        	return ((Channel)c).getTransactions();
        }
        return null;
	}

	/**
	 * @requires addParticipant(participantName)
	 * @return The sum of all  Transaction values stored in the storage of the participant participantName in the simulator simName
	 */
	public double getParticipantBalace(String simName, String participantName) {
        Object p = simulators.get(simName).getNodeData(participantName);
        if (p instanceof Participant){
        	return ((Participant)p).getBalance();
        }
        return 0;
	}
	
	/**
	 * @requires createSimulator(simName)
	 * @modifies simulator named simName
	 * @effects runs simulator named simName for a single time slice.
	 */
	public void simulate(String simName) {
        simulators.get(simName).simulate();
	}

	/**
	 * Prints the all edges.
	 *
	 * @requires simName the sim name
	 * @effects Prints the all edges.
	 */
	public void printAllEdges(String simName) {
		String str = new String();
        Iterator<String> it = simulators.get(simName).getEdges().iterator();
        if (it.hasNext()){
        	str += it.next();
        }
        while(it.hasNext()){
        	str += " " + it.next();
        }
        System.out.println(str);
	}

}
