package hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * Representation of a room 
 * 
 * @author Lucas Smith
 * @since 3/8/2024
 */
public class Room {
	List<Room> neighbors;
	int weight;
	int id;
	boolean visited;
	public Room(int i) {
		id = i;
		neighbors = new ArrayList<Room>();
		visited = false;
	}
	
	public void sortNeighbors(int[][] weights) {
		Comparator<Room> comparator = (room1, room2) -> {
            int weight1 = weights[this.id][room1.id];
            int weight2 = weights[this.id][room2.id];
            return Integer.compare(weight1, weight2); 
        	};
		this.neighbors.sort(comparator);
	}
	

}
