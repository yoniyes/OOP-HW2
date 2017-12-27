package homework2;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * BipartiteGraphTest contains JUnit block-box unit tests for BipartiteGraph.
 */
public class BipartiteGraphTest {

	@Test
    public void testExample() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
        
        //create a graph
        driver.createGraph("graph1");
        
        //add a pair of nodes
        driver.addBlackNode("graph1", "n1");
        driver.addWhiteNode("graph1", "n2");
        
        //add an edge
        driver.addEdge("graph1", "n1", "n2", "edge");
        
        //check neighbors
        assertEquals("wrong black nodes", "n1", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "n2", driver.listWhiteNodes("graph1"));
        assertEquals("wrong children", "n2", driver.listChildren ("graph1", "n1"));
        assertEquals("wrong children", "", driver.listChildren ("graph1", "n2"));
        assertEquals("wrong parents", "", driver.listParents ("graph1", "n1"));
        assertEquals("wrong parents", "n1", driver.listParents ("graph1", "n2"));
    }
    
    
    @Test
    public void generalTest() {
    	BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
    	
    	//create a graph
    	driver.createGraph("g1");
    	
    	//add some legal nodes
    	driver.addBlackNode("g1", "bn1");
    	driver.addWhiteNode("g1", "wn1");
    	driver.addBlackNode("g1", "bn2");
    	driver.addWhiteNode("g1", "wn2");
    	driver.addBlackNode("g1", "bn3");
    	driver.addWhiteNode("g1", "wn3");
    	driver.addBlackNode("g1", "bn4");
    	driver.addWhiteNode("g1", "wn4");
    	
    	//add some legal edges
    	driver.addEdge("g1","bn1","wn1","b1 to w1");
    	driver.addEdge("g1","bn1","wn2","b1 to w2");
    	driver.addEdge("g1","wn3","bn2","w3 to b2");
    	driver.addEdge("g1","bn4","wn4","b4 to w4");
    	driver.addEdge("g1","wn4","bn3","w4 to b3");
    	driver.addEdge("g1","bn3","wn3","b3 to w3");
    	    	
    	assertEquals("Wrong black nodes", "bn1 bn2 bn3 bn4", driver.listBlackNodes("g1"));
    	assertEquals("Wrong white nodes", "wn1 wn2 wn3 wn4", driver.listWhiteNodes("g1"));
    	
    	assertEquals("Wrong children of bn1", "wn1 wn2", driver.listChildren("g1", "bn1"));
    	assertEquals("Wrong children of bn2", "", driver.listChildren("g1", "bn2"));
    	assertEquals("Wrong children of bn3", "wn3", driver.listChildren("g1", "bn3"));
    	assertEquals("Wrong children of bn4", "wn4", driver.listChildren("g1", "bn4"));
    	
    	
    	assertEquals("Wrong children of wn1", "", driver.listChildren("g1", "wn1"));
    	assertEquals("Wrong children of wn2", "", driver.listChildren("g1", "wn2"));
    	assertEquals("Wrong children of wn3", "bn2", driver.listChildren("g1", "wn3"));
    	assertEquals("Wrong children of wn4", "bn3", driver.listChildren("g1", "wn4"));
    	
    	assertEquals("Wrong parents of bn1","" , driver.listParents("g1", "bn1"));
    	assertEquals("Wrong parents of bn2","wn3" , driver.listParents("g1", "bn2"));
    	assertEquals("Wrong parents of bn3","wn4" , driver.listParents("g1", "bn3"));
    	assertEquals("Wrong parents of bn4", "", driver.listParents("g1", "bn4"));
    	
    	assertEquals("Wrong parents of wn1", "bn1", driver.listParents("g1", "wn1"));
    	assertEquals("Wrong parents of wn2", "bn1", driver.listParents("g1", "wn2"));
    	assertEquals("Wrong parents of wn3", "bn3", driver.listParents("g1", "wn3"));
    	assertEquals("Wrong parents of wn4", "bn4", driver.listParents("g1", "wn4"));

    	assertEquals("Wrong child of bn1", "wn1", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w1"));
    	assertEquals("Wrong parent of bn3", "wn4", driver.getParentByEdgeLabel("g1", "bn3", "w4 to b3"));
    }
    
  @Test
  public void nodesAdditionTest() {
	  BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
  	
  	//create a graph
  	driver.createGraph("g1");
  	
  	//add 10 nodes, twice. second time shouldn't add them
  	for(int j = 0 ; j < 2 ; j++){
		for(int i = 4 ; i >= 0 ; i--) {
			driver.addWhiteNode("g1", "wn" + i);
			driver.addBlackNode("g1", "bn" + i);
		}
  	}
  	
  	assertEquals("Wrong black nodes", "bn0 bn1 bn2 bn3 bn4", driver.listBlackNodes("g1"));
  	assertEquals("Wrong white nodes", "wn0 wn1 wn2 wn3 wn4", driver.listWhiteNodes("g1"));
  }
  
  @Test
  public void edgesAdditionTest() {
	BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
	  	
  	//create a graph
  	driver.createGraph("g1");
  	
  //add some legal nodes
	driver.addBlackNode("g1", "bn1");
	driver.addWhiteNode("g1", "wn1");
	driver.addBlackNode("g1", "bn2");
	driver.addWhiteNode("g1", "wn2");
	driver.addBlackNode("g1", "bn3");
	driver.addWhiteNode("g1", "wn3");
	driver.addBlackNode("g1", "bn4");
	driver.addWhiteNode("g1", "wn4");
	
	//add some legal edges
	driver.addEdge("g1","bn1","wn1","b1 to w1");
	driver.addEdge("g1","bn1","wn2","b1 to w2");
	driver.addEdge("g1","wn3","bn2","w3 to b2");
	driver.addEdge("g1","bn4","wn4","b4 to w4");
	driver.addEdge("g1","wn4","bn3","w4 to b3");
	driver.addEdge("g1","bn3","wn3","b3 to w3");
	
	//add edges with existing labels but different nodes
	driver.addEdge("g1","bn4","wn1","w3 to b2");
	
	//add illegal edges (same color nodes)
	driver.addEdge("g1","bn1","bn2","b1 to b2");
	driver.addEdge("g1","bn2","bn3","b2 to b3");
	driver.addEdge("g1","bn4","bn3","b4 to b3");
	driver.addEdge("g1","wn4","wn1","w4 to w1");
	driver.addEdge("g1","wn1","wn2","w1 to w2");
	driver.addEdge("g1","wn3","wn2","w3 to w2");
	
	//add illegal edges (same nodes)
	driver.addEdge("g1", "bn1", "bn1", "b1 to b1");
	driver.addEdge("g1", "wn1", "wn1", "w1 to w1");
	
	//add illegal edges (non-existing nodes)
	driver.addEdge("g1", "wn1", "BBB", "w1 to BBB");
	driver.addEdge("g1", "AAA", "bn1", "AAA to bn1");
	driver.addEdge("g1", "AAA", "BBB", "AAA to BBB");
	
	//add illegal edges (existing edges)
	driver.addEdge("g1","bn1","wn1","b1 to w1");
	driver.addEdge("g1","bn1","wn2","b1 to w2");
	driver.addEdge("g1","wn3","bn2","w3 to b2");
	driver.addEdge("g1", "bn1", "wn4", "b1 to w1");
	driver.addEdge("g1", "wn4", "bn1", "b1 to w1");
	
	//check that only legal edges were added
	assertEquals("Wrong black nodes", "bn1 bn2 bn3 bn4", driver.listBlackNodes("g1"));
	assertEquals("Wrong white nodes", "wn1 wn2 wn3 wn4", driver.listWhiteNodes("g1"));
	
	assertEquals("Wrong children of bn1", "wn1 wn2", driver.listChildren("g1", "bn1"));
	assertEquals("Wrong children of bn2", "", driver.listChildren("g1", "bn2"));
	assertEquals("Wrong children of bn3", "wn3", driver.listChildren("g1", "bn3"));
	assertEquals("Wrong children of bn4", "wn1 wn4", driver.listChildren("g1", "bn4"));
	
	
	assertEquals("Wrong children of wn1", "", driver.listChildren("g1", "wn1"));
	assertEquals("Wrong children of wn2", "", driver.listChildren("g1", "wn2"));
	assertEquals("Wrong children of wn3", "bn2", driver.listChildren("g1", "wn3"));
	assertEquals("Wrong children of wn4", "bn3", driver.listChildren("g1", "wn4"));
	
	assertEquals("Wrong parents of bn1","" , driver.listParents("g1", "bn1"));
	assertEquals("Wrong parents of bn2","wn3" , driver.listParents("g1", "bn2"));
	assertEquals("Wrong parents of bn3","wn4" , driver.listParents("g1", "bn3"));
	assertEquals("Wrong parents of bn4", "", driver.listParents("g1", "bn4"));
	
	assertEquals("Wrong parents of wn1", "bn1 bn4", driver.listParents("g1", "wn1"));
	assertEquals("Wrong parents of wn2", "bn1", driver.listParents("g1", "wn2"));
	assertEquals("Wrong parents of wn3", "bn3", driver.listParents("g1", "wn3"));
	assertEquals("Wrong parents of wn4", "bn4", driver.listParents("g1", "wn4"));

	assertEquals("Wrong child of bn1", "wn1", driver.getChildByEdgeLabel("g1", "bn1", "b1 to w1"));
	assertEquals("Wrong parent of bn3", "wn4", driver.getParentByEdgeLabel("g1", "bn3", "w4 to b3"));
  }
  
  @Test
  public void nullLables() {
	  BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
	
	  //create a graph
	  driver.createGraph("g1");
	  
	  driver.addBlackNode("g1", null);
	  driver.addWhiteNode("g1", "wn1");
	  driver.addBlackNode("g1", "bn1");
	  
	  driver.addEdge("g1", null, "wn1", "e1");
	  driver.addEdge("g1", "wn1", null, null);
	  
	  assertEquals("Wrong children of null", "wn1", driver.listChildren("g1", null));
	  assertEquals("Wrong child of wn1", null, driver.getChildByEdgeLabel("g1", "wn1", null));
	  
	  assertEquals("Node should not exist", "", driver.listChildren("g1", "BBB"));
  }
  
  @Test
  public void removeNodes() {
	  BipartiteGraph<String> g = new BipartiteGraph<String>();
	  
	  assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
	  assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
	  
	  assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn1", "e"));
	  
	  assertNotEquals("Node should exist", null, g.findNode("bn1"));
	  assertNotEquals("Node should exist", null, g.findNode("wn1"));
	  
	  assertEquals("Illegal node was deleted", false, g.removeNode("Non existing node"));
	  assertEquals("Legal node failed to be deleted", true, g.removeNode("bn1"));
	  assertEquals("Legal node failed to be deleted", true, g.removeNode("wn1"));
	  
	  assertEquals("Node should exist", null, g.findNode("bn1"));
	  assertEquals("Node should exist", null, g.findNode("wn1"));
  }
  
  @Test
  public void removeEdges() {
	  BipartiteGraph<String> g = new BipartiteGraph<String>();
	  
	  g.removeEdge("a", "b");
	  
	  assertEquals("Legal node failed to be added", true, g.addNode("bn1", null, true));
	  assertEquals("Legal node failed to be added", true, g.addNode("wn1", null, false));
	  assertEquals("Legal node failed to be added", true, g.addNode("bn2", null, true));
	  assertEquals("Legal node failed to be added", true, g.addNode("wn2", null, false));
	  
	  assertEquals("Legal edge failed to be added", true, g.addEdge("bn1", "wn1", "e1"));
	  assertEquals("Legal edge failed to be added", true, g.addEdge("wn1", "bn1", "e2"));
	  assertEquals("Legal edge failed to be added", true, g.addEdge("bn2", "wn1", "e3"));
	  assertEquals("Legal edge failed to be added", true, g.addEdge("wn2", "bn2", "e4"));
	  
	  assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn1", "e1"));
	  assertEquals("Wrong child", "bn1", g.getChildByEdgeLabel("wn1", "e2"));
	  assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn2", "e3"));
	  assertEquals("Wrong child", "bn2", g.getChildByEdgeLabel("wn2", "e4"));
	  assertEquals("Wrong parent", "bn1", g.getParentByEdgeLabel("wn1", "e1"));
	  assertEquals("Wrong parent", "wn1", g.getParentByEdgeLabel("bn1", "e2"));
	  assertEquals("Wrong parent", "bn2", g.getParentByEdgeLabel("wn1", "e3"));
	  assertEquals("Wrong parent", "wn2", g.getParentByEdgeLabel("bn2", "e4"));
	  
	  g.removeEdge("eee", "bn1");
	  g.removeEdge("bbb", "aaa");
	  
	  //check that nothing changed
	  assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn1", "e1"));
	  assertEquals("Wrong child", "bn1", g.getChildByEdgeLabel("wn1", "e2"));
	  assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn2", "e3"));
	  assertEquals("Wrong child", "bn2", g.getChildByEdgeLabel("wn2", "e4"));
	  assertEquals("Wrong parent", "bn1", g.getParentByEdgeLabel("wn1", "e1"));
	  assertEquals("Wrong parent", "wn1", g.getParentByEdgeLabel("bn1", "e2"));
	  assertEquals("Wrong parent", "bn2", g.getParentByEdgeLabel("wn1", "e3"));
	  assertEquals("Wrong parent", "wn2", g.getParentByEdgeLabel("bn2", "e4"));
	  
	  g.removeEdge("e1", "bn1");
	  g.removeEdge("e4", "wn2");
	  
	  assertEquals("edge not removed", null, g.getChildByEdgeLabel("bn1", "e1"));
	  assertEquals("Wrong child", "bn1", g.getChildByEdgeLabel("wn1", "e2"));
	  assertEquals("Wrong child", "wn1", g.getChildByEdgeLabel("bn2", "e3"));
	  assertEquals("edge not removed", null, g.getChildByEdgeLabel("wn2", "e4"));
	  assertEquals("edge not removed", null, g.getParentByEdgeLabel("wn1", "e1"));
	  assertEquals("Wrong parent", "wn1", g.getParentByEdgeLabel("bn1", "e2"));
	  assertEquals("Wrong parent", "bn2", g.getParentByEdgeLabel("wn1", "e3"));
	  assertEquals("edge not removed", null, g.getParentByEdgeLabel("bn2", "e4"));
	  
	  g.removeEdge("e2", "wn1");
	  g.removeEdge("e3", "bn2");
	  
	  assertEquals("edge not removed", null, g.getChildByEdgeLabel("bn2", "e3"));
	  assertEquals("edge not removed", null, g.getChildByEdgeLabel("wn1", "e2"));
	  assertEquals("edge not removed", null, g.getParentByEdgeLabel("wn1", "e3"));
	  assertEquals("edge not removed", null, g.getParentByEdgeLabel("bn1", "e2"));
  }
  
 @Test
 public void clearTest() {
	 BipartiteGraph<String> g = new BipartiteGraph<String>();
	 assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());
	 g.clear();
	 assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());
	 
	 assertEquals("Legal node failed to be addedd", true, g.addNode("bn1", null, true));
	 assertEquals("Legal node failed to be addedd", true, g.addNode("bn2", null, false));
	 assertEquals("Legal node failed to be addedd", true, g.addNode("wn1", null, true));
	 assertEquals("Legal node failed to be addedd", true, g.addNode("wn2", null, false));
	 g.addEdge("bn1", "wn1", "THE EDGE");
	 
	 assertEquals("Graph should have 4 nodes", 4 ,g.listNodes(true).size() + g.listNodes(false).size());
	 g.clear();
	 assertEquals("Graph should be empty", 0 ,g.listNodes(true).size() + g.listNodes(false).size());
 }
 @Test
 public void getNodeDataTest() {
	 BipartiteGraph<String> g = new BipartiteGraph<String>();
	 String data = "MY FRIGGIN DATA";
	 
	 g.addNode("bn1", data, true);
	 g.addNode("wn1", null, false);
	 
	 assertEquals("wrong data", data, g.getNodeData("bn1"));
	 assertEquals("wrong data", null, g.getNodeData("wn1"));
	 assertEquals("Non existing node should return null", null, g.getNodeData("ANOTHER NODE"));
 }
 
 @Test
 public void setNodeDataTest() {
	 BipartiteGraph<String> g = new BipartiteGraph<String>();
	 
	 String data = "DATA";
	 
	 g.addNode("bn1", data, true);
	 g.addNode("wn1", null, false);
	 
	 assertEquals("wrong data", data, g.getNodeData("bn1"));
	 assertEquals("wrong data", null, g.getNodeData("wn1"));
	 
	 String data1 = "DATA1";
	 
	 g.setNodeData("bn1", data1);
	 g.setNodeData("wn1", data);
	 
	 assertEquals("wrong data", data1, g.getNodeData("bn1"));
	 assertEquals("wrong data", data, g.getNodeData("wn1"));
 }
}
