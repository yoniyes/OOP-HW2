package homework2;

import static org.junit.Assert.*;
import org.junit.Test;

public class SimulatorTest {

	@Test
	public void basicTest(){
		SimulatorTestDriver driver = new SimulatorTestDriver();
		
		driver.createSimulator("sim");
		
		assertEquals("It failed for some reason", arg1 ,arg2);
		
	}
}
