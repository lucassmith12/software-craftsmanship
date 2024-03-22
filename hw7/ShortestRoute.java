package hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * Programming Assignment 7 -- create algorithm to find the shortest route through a building of 
 * n rooms and n-1 hallways
 * 
 * @author Lucas Smith
 * @since 3/8/2024
 */
public class ShortestRoute {

	/**
	 * Method takes a graph and a starting point in that graph and returns the shortest route 
	 * through the graph that visits every node
	 * 
	 * @param start -- Starting node
	 * @param weights -- Weight chart that defines the distance between connected nodes
	 * @return
	 */
	public static List<Room> shortestRoute(Room start, int[][] weights){
		Objects.requireNonNull(start);
		Objects.requireNonNull(weights);

		List<Room> routeList = new ArrayList<>();

		//Calculate the weights for every connected subgraph of the graph
		weightGraph(start, weights);

		//Build the route according to the shortest weight paths
		routeList = routeBuilder(start, weights, routeList);

		//The last entries in routeList are an unnecessary path back to the start, so we remove them
		
		while(routeList.get(routeList.size()-1).neighbors.size()>1) {
			routeList.remove(routeList.size()-1);
			
		}

		return routeList;

	}

	/**
	 * A helper method that builds a List path through the graph
	 * 
	 * @param start -- starting node
	 * @param weights -- weight chart
	 * @param build -- traversal list through the graph
	 * @return -- a list representation of a traversal through every node in the graph
	 */
	private static List<Room> routeBuilder(Room start, int[][]weights, List<Room> build){
		assert start != null;
		assert weights != null;
		build.add(start);
		start.visited = false;

		//Sort the weights to add the smallest weighted nodes first
		start.sortNeighbors(weights);
		
		//Base Case: Reach the end of a subgraph, return just this node
		//Pseudocode: if u has no unvisited neighbors do:
		//return {0, {u}}
		if(start.neighbors.size()<=1) {
			return build;
		}

		//Recursive Case: build a loop through the subgraph
		/*Pseudocode: for each neighbor p of u:
        if u.visited == false do:
            u.visited = true
            Pair = DepthFirst(G, p, W, {W[u,p], {p})
            KeyValuePair.key = KeyValuePair.key + Pair.key
            Append Pair.value to front of KeyValuePair.value
		 */
		for(Room next: start.neighbors) {
			if(next.visited) {
				routeBuilder(next, weights, build);
				build.add(start);
			}
		}
		return build;
		
		
	}

	/**
	 * Helper method that generates the weight of every subgraph of the graph
	 * as a field of the root node of that subgraph
	 * 
	 * @param start -- Starting room
	 * @param weights -- Weight chart
	 */
	private static void weightGraph(Room start, int[][] weights) {
		assert start != null;
		assert weights != null;
		start.visited = true;
		//Base Case: "leaf" node, the weight of an empty subgraph is 0 so return
		if(start.neighbors.size() <=1) {
			return;
		}

		/* 
		 * Recursive case: add twice the weight between the parent and the child (one for going down and one
		 * for coming up, plus the weight of the neighbor's subgraph
		 */
		for(Room neighbor: start.neighbors) {
			if(!neighbor.visited) {
				start.weight+=2*weights[start.id][neighbor.id];

				weightGraph(neighbor, weights);
				start.weight+=neighbor.weight;
			}
		}
	}
	class routeBuilderTests{
		//Tests CC2
		public static List<Room> mainCC() {
			
			Room start = new Room(0);
			Room neighbor1 = new Room(1);
			Room neighbor2 = new Room(2);
			
			start.neighbors.add(neighbor1);
			neighbor1.neighbors.add(start);
			start.neighbors.add(neighbor2);
			neighbor2.neighbors.add(start);
			
			neighbor1.visited = true;
			neighbor2.visited = true;
			
			int [][] weights = {{0,1,2},{1,0,-1},{2,-1,0}}; 
			List<Room> build = new ArrayList<>();
			return routeBuilder(start, weights, build);
			
			
		}
		
		//Tests CC1, Branch 1 
		public static List<Room> branch() {
			Room start = new Room(0);
			List<Room> build = new ArrayList<>();
			int [][] weights = {{0},{0}}; 
			return ShortestRoute.routeBuilder(start, weights, build);
		}
		
		//Tests Boundary, Branch 2
		public static List<Room> boundary() {
			Room start = new Room(0);
			Room neighbor = new Room(1);
			start.neighbors.add(neighbor);
			neighbor.neighbors.add(start);
			neighbor.visited = false;
			int [][] weights = {{0,1}, {1,0}}; 
			List<Room> build = new ArrayList<>();
			
			return ShortestRoute.routeBuilder(start, weights, build);
			
			

			
		}
		
		//Tests bad data
		public static int badData() {
			Room start = null;
			int[][] weights = new int[0][0];
			int thrown = 0;
			List<Room> build = new ArrayList<>();
			try{
				build = ShortestRoute.routeBuilder(start, weights, build);
				
			}catch(AssertionError e) {
				thrown++;
			}
			try {
				start = new Room(0);
				weights = null;
				build = ShortestRoute.routeBuilder(start, weights, build);
			}catch(AssertionError e) {
				thrown++;
			}
			return thrown;
		}

	}
	class weightGraphTests{
		//Tests CC2
		public static List<Room> mainCC() {
			Room start = new Room(0);
			Room neighbor1 = new Room(1);
			Room neighbor2 = new Room(2);
			
			start.neighbors.add(neighbor1);
			neighbor1.neighbors.add(start);
			start.neighbors.add(neighbor2);
			neighbor2.neighbors.add(start);
			neighbor1.visited = false;
			neighbor2.visited = false;
			
			int [][] weights = {{0,1,2},{1,0,-1},{2,-1,0}}; 
			List<Room> build = new ArrayList<>();
			build.add(start);
			build.add(neighbor1);
			build.add(neighbor2);
			weightGraph(start, weights);
			return build;
			
			
		}
		
		//Tests CC1, Branch 1 
		public static List<Room> branch() {
			Room start = new Room(0);
			List<Room> build = new ArrayList<>();
			build.add(start);
			int [][] weights = {{0},{0}}; 
			weightGraph(start, weights);
			return build;
		}
		
		//Tests Boundary, Branch 2
		public static List<Room> boundary() {
			Room start = new Room(0);
			Room neighbor = new Room(1);
			start.neighbors.add(neighbor);
			neighbor.neighbors.add(start);
			neighbor.visited = true;
			int [][] weights = {{0,1}, {1,0}}; 
			List<Room> build = new ArrayList<>();
			build.add(start);
			build.add(neighbor);
			
			weightGraph(start, weights);
			return build;
		}
		
		//Tests bad data
		public static int badData() {
			Room start = null;
			int[][] weights = new int[1][1];
			int thrown = 0;
			try{
				weightGraph(start, weights);
			}catch(AssertionError e) {
				thrown++;
			}
			try {
				start = new Room(0);
				weights = null;
				weightGraph(start, weights);
			}catch(AssertionError e){
				thrown++;
			}
			return thrown;
			
			
		}
	}
}
