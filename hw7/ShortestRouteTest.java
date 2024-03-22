package hw7;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import hw7.ShortestRoute.routeBuilderTests;
import hw7.ShortestRoute.weightGraphTests;

public class ShortestRouteTest {

	//Legacy code - good data
	@Test
	//CC, Boundary 1, good data testcase
	public void testShortestRouteMain() {
		int[][] weights = {
		{0,1,-1,-1,1,-1,-1,-1},                   
		{1,0,1,-1,-1,-1,-1,-1},                   
		{-1,1,0,1,-1,-1,-1,-1},                   
		{-1,-1,1,0,-1,-1,-1,-1},
		{1,-1,-1,-1,0,3,4,3},
		{-1,-1,-1,-1,3,0, -1,-1},
		{-1,-1,-1,-1,4,-1,0,-1},
		{-1,-1,-1,-1,3,-1,-1,0}};
		Room room1 = new Room(0);
		Room room2 = new Room(1);
		Room room3 = new Room(2);
		Room room4 = new Room(3);
		Room room5 = new Room(4);
		Room room6 = new Room(5);
		Room room7 = new Room(6);
		Room room8 = new Room(7);
		room1.neighbors.add(room2);
		room1.neighbors.add(room5);
		
		room2.neighbors.add(room1);
		room2.neighbors.add(room3);
		
		room3.neighbors.add(room2);
		room3.neighbors.add(room4);
		
		room4.neighbors.add(room3);
		
		room5.neighbors.add(room1);
		room5.neighbors.add(room6);
		room5.neighbors.add(room7);
		room5.neighbors.add(room8);
		
		room6.neighbors.add(room5);
		
		room7.neighbors.add(room5);
		
		room8.neighbors.add(room5);
		
		
		List<Room> route = ShortestRoute.shortestRoute(room1, weights);
		int[] expected = {0,1,2,3,2,1,0,4,5,4,7,4,6};
		for(int i = 0; i<13; i++) {
			assertEquals(route.get(i).id, expected[i]);
		}
//		assertEquals(route.get(0).id+1, true);
//		assertEquals(route.get(1).id+1==2, true);
//		assertEquals(route.get(2).id+1==3, true);
//		assertEquals(route.get(3).id+1==4, true);
//		assertEquals(route.get(4).id+1==3, true);
//		assertEquals(route.get(5).id+1==2, true);
//		assertEquals(route.get(6).id+1==1, true);
//		assertEquals(route.get(7).id+1==5, true);
//		assertEquals(route.get(8).id+1==6, true);
//		assertEquals(route.get(9).id+1==5, true);
//		assertEquals(route.get(10).id+1==8, true);
//		assertEquals(route.get(11).id+1==5, true);
//		assertEquals(route.get(12).id+1==7, true);
		
		
	}
	
	@Test 
	//Tests Branch and Boundary 2 
	public void testShortestRouteBranch() {
		
		Room soloRoom = new Room(0);
		int[][]weights = new int[1][1];
		weights[0][0] = 0;
		List<Room> soloRoute = ShortestRoute.shortestRoute(soloRoom, weights);

		assertEquals(soloRoute.get(0).id,0);
		assertEquals(soloRoute.size(), 1);
	}
	
	@Test
	//Tests Boundary 3 
	public void testShortestRouteBoundary() {
		Room zero = new Room(0);
		Room one = new Room(1);
		zero.neighbors.add(one);
		one.neighbors.add(zero);
		
		int[][] weights = {{0,1},{1,0}};
		
		List<Room> pairRoute = ShortestRoute.shortestRoute(zero, weights);
		
		assertEquals(pairRoute.get(0).weight, 0);
		
	}
	@Test
	//Tests bad neighbor
	public void testShortestRouteBadData() {
		int[][] weights = null;
		Room one = null;
		boolean thrown = false;
		
		try {
		ShortestRoute.shortestRoute(one, weights);
		} catch(Exception e) {
			thrown = true;
		}
		if(!thrown) {
			fail();
		}
	}
	
	//Tests for routeBuilder
	@Test
	//Tests CC2, good data
	public void testBuildRouteMain() {
		List<Room> main = routeBuilderTests.mainCC();
		
		int[] expected = {0,1,0,2,0};
		for(int i = 0; i<3; i++) {
			assertEquals(main.get(i).id, expected[i]);
		}
		
				
	}
	@Test
	//Tests CC1, branch 1
	public void testBuildRouteBranch() {
		List<Room> branch = routeBuilderTests.branch();
		assertEquals(branch.get(0).id,0);
		assertEquals(branch.size(), 1);
	}
	
	@Test
	//Tests Boundary, branch 2
	public void testBuildRouteBoundary() {
		List<Room> boundary = routeBuilderTests.boundary();
		assertEquals(boundary.get(0).id,0);
		assertEquals(boundary.size(), 1);
	}
	
	@Test
	//Tests bad data
	public void testBuildRouteData() {
		int data = routeBuilderTests.badData();
		if(data!=2) {
			fail();
		}
	}
	
	//Tests for weightGraph
	
	@Test
	//Tests CC2, good data
	public void testWeightGraphMain() {
		List<Room> mainCC = weightGraphTests.mainCC();
		int[] expected = {6,0,0};
		for(int i = 0; i<3; i++) {
			assertEquals(mainCC.get(i).weight, expected[i]);
		}
	}
	@Test
	//Tests CC1, branch 1
	public void testWeightGraphBranch() {
		List<Room> branch = weightGraphTests.branch();
		assertEquals(branch.get(0).weight,0);
		assertEquals(branch.size(), 1);
	}
	@Test
	//Tests boundary, branch 2
	public void testWeightGraphBoundary() {
		List<Room> boundary = weightGraphTests.boundary();
		assertEquals(boundary.get(0).weight,0);
		
	}
	@Test
	//Tests bad data
	public void testWeightGraphData() {
		int data = weightGraphTests.badData();
		if(data!= 2) {
			fail();
		}
	}
}
