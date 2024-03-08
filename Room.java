package hw7;

import java.util.ArrayList;
import java.util.Collections;

public class Room {
	ArrayList<Room> neighbors;
	Room parent;
	int id;
	boolean visited;
	public Room(int i) {
		id = i;
		neighbors = new ArrayList<Room>();
		visited = false;
	}
	
	public void sortNeighbors(Room parent, int[][] weights) {
		Collections.sort(neighbors, (n1, n2) -> weights[parent.id][n2.id]-weights[parent.id][n2.id]);
	}

}
