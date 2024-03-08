package hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class ShortestRoute {

	public static List<Room> shortestRoute(Room start, int[][] weights){
		Objects.requireNonNull(start);

		List<Room> routeList = new ArrayList<>();
		
		weightGraph(start, weights);


		routeList = routeBuilder(start, weights, routeList);
		while(routeList.get(routeList.size()-1).neighbors.size()!=1) {
			routeList.remove(routeList.size()-1);
		}
		
		return routeList;

	}

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
