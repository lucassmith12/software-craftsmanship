package hw7;

import java.util.LinkedList;

public class HashEntry {
	private int weight;
	private LinkedList<Room> rooms;
	
	public HashEntry(int w, LinkedList<Room> r) {
		weight = w;
		rooms = r;
	}
	
	public int weight() {
		return weight;
	}
	public LinkedList<Room> rooms(){
		return rooms;
	}
	public void addWeight(int w) {
		weight+=w;
	}
	
}
