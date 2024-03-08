package hw7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ShortestRoute {

	public static List<Room> shortestRoute(Room start, int[][] weights){
		List<Room> routeList = List.of(start);
		weightGraph(start, weights);
		
		

		return routeList;
		
	}
	
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
