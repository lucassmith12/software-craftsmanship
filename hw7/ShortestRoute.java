package hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


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

		List<Room> routeList = new ArrayList<>();
		
		//Calculate the weights for every connected subgraph of the graph
		weightGraph(start, weights);

		//Build the route according to the shortest weight paths
		routeList = routeBuilder(start, weights, routeList);
		
		//The last entries in routeList are an unnecessary path back to the start, so we remove them
		while(routeList.get(routeList.size()-1).neighbors.size()!=1) {
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
		build.add(start);
		start.visited = false;
		
		if(start.neighbors.size()==1) {
			return build;
		}
		
		start.sortNeighbors(weights);
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
		start.visited = true;

		if(start.neighbors.size() ==1) {
			System.out.println(start.id +1 + ": " + start.weight);
			return;
		}
		for(Room neighbor: start.neighbors)
			if(!neighbor.visited) {
				start.weight+=weights[start.id][neighbor.id];
				weightGraph(neighbor, weights);
				start.weight+=neighbor.weight;
				start.weight+=weights[start.id][neighbor.id];
			}
		System.out.println(start.id +1 + ": " + start.weight);
	}


}
