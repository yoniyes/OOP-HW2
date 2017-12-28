package homework2;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * SimulatorTest contains JUnit block-box unit tests for Simulator.
 */
public class SimulatorTest {

	@Test
	public void basicTest(){
		SimulatorTestDriver driver = new SimulatorTestDriver();
		assertNotEquals("sim driver is null???", driver ,null);
		
		driver.createSimulator("sim");
		/**
		 * Graph:
		 * 		p1 --> c1 --> p2 --> c2 --> p3
		 */
		driver.addParticipant("sim", "p1", 1);
		driver.addParticipant("sim", "p2", 1);
		driver.addParticipant("sim", "p3", 0);
		assertEquals("participant p1 balance should be 0", driver.getParticipantBalace("sim", "p1") == 0, true);
		assertEquals("participant p2 balance should be 0", driver.getParticipantBalace("sim", "p2") == 0, true);
		assertEquals("participant p3 balance should be 0", driver.getParticipantBalace("sim", "p3") == 0, true);
		
		driver.addChannel("sim", "c1", 2);
		driver.addChannel("sim", "c2", 2);
		assertEquals("channel c1 should be empty", driver.listContents("sim", "c1"), "");
		assertEquals("channel c2 should be empty", driver.listContents("sim", "c2"), "");
		
		driver.addEdge("sim", "p1", "c1", "p1 to c1");
		driver.addEdge("sim", "p2", "c2", "p2 to c2");
		driver.addEdge("sim", "c1", "p2", "c1 to p2");
		driver.addEdge("sim", "c2", "p3", "c2 to p3");
		
		/**
		 * Put transaction of value 2.0 with destination p3 on c1.
		 * Try to put another transaction and fail to do so due to c1 limit.
		 */
		driver.sendTransaction("sim", "c1", new Transaction("p3", 2.0));
		driver.sendTransaction("sim", "c1", new Transaction("p3", 1.0));
		assertEquals("channel c1 should have one transaction due to limit", driver.listContents("sim", "c1"), "2.0");
		assertEquals("channel c2 should be empty", driver.listContents("sim", "c2"), "");
		
		/**
		 * Simulate the transaction route.
		 */
		driver.simulate("sim");
		assertEquals("channel c1 should be empty", driver.listContents("sim", "c1"), "");
		assertEquals("channel c2 should have one transaction", driver.listContents("sim", "c2"), "1.0");
		assertEquals("participant p2 balance should be 1.0", driver.getParticipantBalace("sim", "p2") == 1.0, true);
		driver.simulate("sim");
		assertEquals("channel c1 should be empty", driver.listContents("sim", "c1"), "");
		assertEquals("channel c2 should be empty", driver.listContents("sim", "c2"), "");
		assertEquals("participant p2 balance should be 1.0", driver.getParticipantBalace("sim", "p2") == 1.0, true);
		assertEquals("participant p3 balance should be 1.0", driver.getParticipantBalace("sim", "p3") == 1.0, true);
	}
	
	@Test
	public void channelLimitTest() {
		SimulatorTestDriver driver = new SimulatorTestDriver();
		assertNotEquals("sim driver is null???", driver ,null);
		
		driver.createSimulator("sim");
		/**
		 * Graph:
		 * 		p1 --> c1 --> p2 --> c2 --> p3 --> c3 --> p4 --> c4 --> p5
		 */
		driver.addParticipant("sim", "p1", 1);
		driver.addParticipant("sim", "p2", 1);
		driver.addParticipant("sim", "p3", 10);
		driver.addParticipant("sim", "p4", 0);
		driver.addParticipant("sim", "p5", 0);
		assertEquals("participant p1 balance should be 0", driver.getParticipantBalace("sim", "p1") == 0, true);
		assertEquals("participant p2 balance should be 0", driver.getParticipantBalace("sim", "p2") == 0, true);
		assertEquals("participant p3 balance should be 0", driver.getParticipantBalace("sim", "p3") == 0, true);
		assertEquals("participant p4 balance should be 0", driver.getParticipantBalace("sim", "p4") == 0, true);
		assertEquals("participant p5 balance should be 0", driver.getParticipantBalace("sim", "p5") == 0, true);
		
		driver.addChannel("sim", "c1", 20);
		driver.addChannel("sim", "c2", 20);
		driver.addChannel("sim", "c3", 1);
		driver.addChannel("sim", "c4", 0);
		assertEquals("channel c1 should be empty", driver.listContents("sim", "c1"), "");
		assertEquals("channel c2 should be empty", driver.listContents("sim", "c2"), "");
		assertEquals("channel c3 should be empty", driver.listContents("sim", "c3"), "");
		assertEquals("channel c4 should be empty", driver.listContents("sim", "c4"), "");
		
		driver.addEdge("sim", "p1", "c1", "p1 to c1");
		driver.addEdge("sim", "p2", "c2", "p2 to c2");
		driver.addEdge("sim", "p3", "c3", "p3 to c3");
		driver.addEdge("sim", "p4", "c4", "p4 to c4");
		driver.addEdge("sim", "c1", "p2", "c1 to p2");
		driver.addEdge("sim", "c2", "p3", "c2 to p3");
		driver.addEdge("sim", "c3", "p4", "c3 to p4");
		driver.addEdge("sim", "c4", "p5", "c4 to p5");
		
		driver.sendTransaction("sim", "c1", new Transaction("p2", 2.0));
		driver.sendTransaction("sim", "c1", new Transaction("p3", 10.0));
		// Shouldn't be sent.
		driver.sendTransaction("sim", "c1", new Transaction("p3", 11.0));
		// Shouldn't be sent.
		driver.sendTransaction("sim", "c2", new Transaction("p3", 30.0));
		// Should be sent and p3 should keep it all.
		driver.sendTransaction("sim", "c2", new Transaction("p5", 15.0));
		// Should be kept by p4.
		driver.sendTransaction("sim", "c3", new Transaction("p5", 1.0));
		assertEquals("channel c1 should have 2 transaction due to limit", driver.listContents("sim", "c1"), "2.0 10.0");
		assertEquals("channel c2 should have 1 transaction", driver.listContents("sim", "c2"), "15.0");
		assertEquals("channel c3 should have 1 transaction", driver.listContents("sim", "c3"), "1.0");
		
		/**
		 * Simulate the transaction route.
		 */
		driver.simulate("sim");
		assertEquals("channel c1 should be empty", driver.listContents("sim", "c1"), "");
		assertEquals("channel c2 should have 1 transaction", driver.listContents("sim", "c2"), "9.0");
		assertEquals("channel c3 should be empty", driver.listContents("sim", "c3"), "");
		assertEquals("channel c4 should be empty", driver.listContents("sim", "c4"), "");
		assertEquals("participant p2 balance should be 3.0", driver.getParticipantBalace("sim", "p2") == 3.0, true);
		assertEquals("participant p3 balance should be 15.0", driver.getParticipantBalace("sim", "p3") == 15.0, true);
		assertEquals("participant p4 balance should be 1.0", driver.getParticipantBalace("sim", "p4") == 1.0, true);
		assertEquals("participant p5 balance should be 0", driver.getParticipantBalace("sim", "p5") == 0, true);
		driver.simulate("sim");
		assertEquals("channel c1 should be empty", driver.listContents("sim", "c1"), "");
		assertEquals("channel c2 should be empty", driver.listContents("sim", "c2"), "");
		assertEquals("channel c3 should be empty", driver.listContents("sim", "c3"), "");
		assertEquals("channel c4 should be empty", driver.listContents("sim", "c4"), "");
		assertEquals("participant p2 balance should be 3.0", driver.getParticipantBalace("sim", "p2") == 3.0, true);
		assertEquals("participant p3 balance should be 24.0", driver.getParticipantBalace("sim", "p3") == 24.0, true);
		assertEquals("participant p4 balance should be 1.0", driver.getParticipantBalace("sim", "p4") == 1.0, true);
		assertEquals("participant p5 balance should be 0", driver.getParticipantBalace("sim", "p5") == 0, true);
	}
}
