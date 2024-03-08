package hw7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ShortestRoute {
	
	public static List<Room> shortestRoute(Room start, int[][] weights){
		//Initialize routeList as a linked list to return
		List<Room> routeList = new LinkedList<>();
		//Initialize weightTable as a hash table
		HashMap<Integer, LinkedList<Room>> weightTable = new HashMap<>();
		int maxWeight = 0;
		/* For each neighbor of v
			Pair = new key value pair of {W[v] [u], {v , u}}
			Add Depth First (G, u , W, Pair ) to WeightTable
		*/
		
		start.visited = true;
		for(Room neighbor: start.neighbors) {
			neighbor.parent = start;
			neighbor.neighbors.remove(start);
			LinkedList<Room> list = new LinkedList<>();
			list.push(start);
			
			HashEntry startPair = new HashEntry(weights[start.id][neighbor.id], list); 
			HashEntry remainder = DepthFirst(startPair, neighbor, weights);
			
			startPair.addWeight(remainder.weight());
			startPair.rooms().addAll(remainder.rooms());
			weightTable.put(remainder.weight(), remainder.rooms());
			if(remainder.weight()>maxWeight) {
				maxWeight = remainder.weight();
			}
		}
		
		LinkedList<Room> lastSubgraph = weightTable.get(maxWeight);
		while(!lastSubgraph.getLast().neighbors.isEmpty()) {
			lastSubgraph.removeLast();
		}
		
		for(LinkedList<Room> roomList: weightTable.values()) {
			routeList.addAll(roomList);
		}
		for(LinkedList<Room> list: weightTable.values()) {
			for(Room room:list)
				System.out.print(room.id+1 + " ");
			System.out.println();
		}
		
		return routeList;
		
	}
	
	public static HashEntry DepthFirst(HashEntry subgraph, Room start, int[][] weights){
		start.visited = true;
		if(start.neighbors.size() == 0) {
			subgraph.rooms().add(start);
			return subgraph;
		}
		start.sortNeighbors(start, weights);

		for(int i = 0; i< start.neighbors.size(); i++) {
			Room neighbor = start.neighbors.get(i);
			if(neighbor.visited) {
				break;
			}
			neighbor.visited = true;
			neighbor.parent = start;
			neighbor.neighbors.remove(start);
			
			subgraph.rooms().add(start);
			subgraph.addWeight(weights[start.id][neighbor.id]);
			
			
			HashEntry pair = DepthFirst(subgraph, neighbor, weights);
			subgraph.rooms().addAll(pair.rooms());
			subgraph.addWeight(pair.weight());
			
			subgraph.rooms().add(start);
			subgraph.addWeight(weights[start.id][neighbor.id]);
		}
		
		return subgraph;
	}
}
