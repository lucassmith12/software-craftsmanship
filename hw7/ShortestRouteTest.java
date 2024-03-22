package hw7;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ShortestRouteTest {

	@Test
	public void testShortestRouteMain() {
		//CC, Boundary 1 testcase
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
	public void testShortestRouteBranch() {
		//Branch Coverage and Boundary 2 - make while false
		Room soloRoom = new Room(0);
		int[][]weights = new int[1][1];
		weights[0][0] = 0;
		List<Room> soloRoute = ShortestRoute.shortestRoute(soloRoom, weights);

		assertEquals(soloRoute.get(0).id,0);
		assertEquals(soloRoute.size(), 1);
	}
	
	@Test
	public void testShortestRouteBoundary() {
		//Boundary 3 - one neighbor
		Room zero = new Room(0);
		Room one = new Room(1);
		zero.neighbors.add(one);
		one.neighbors.add(zero);
		
		int[][] weights = {{0,1},{1,0}};
		weights[0][0] = 0;
		List<Room> pairRoute = ShortestRoute.shortestRoute(zero, weights);

		assertEquals(pairRoute.get(0).weight, 1);
		
	}
	public void testBadData() {
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

}
