/* filename: Vertex.java
   author: Anh Uong
   date: November 18, 2014
*/

import java.util.*;
import java.awt.Graphics;
import java.awt.Color;

/* child clas of Cell, implements Comparable, will go into a Graph and 
   contains pointers in the North, South, East, and West direction to other vertices
 * contains a HashMap of the edges/pointers of the Vertex with the 
   direction as keys and the Vertex it points to as values
 * also contains...
       -cost = how far the Vertex is from a particular other Vertex or Object
       -marked = boolean value for traversing Graph to see if visited vertex
       -label = helpful String for labeling vertices
       -visible = boolean value marking if the Vertex is visible (drawn) or not 
 * at creation, must set as parameters either....
       -location of Vertex (x, y, values)
       -label 
       -label and location
       -label, cost, and if marked
 * drawn/acts as a room in the HuntTheWumpus game 
 */
public class Vertex extends Cell implements Comparable<Vertex>{
	public enum Direction{ NORTH, SOUTH, EAST, WEST, WAIT }
	
	private HashMap<Direction, Vertex> edges;
	private int cost;
	private boolean marked;
	String label;
	private boolean visible;
	
	/* initializes Vertex with USER PROVIDED location (x0, y0) and 
	   AUTOMATICALLY SETS cost to infinite value, marked to false, blank label,
	   visibility to false, and edges to a empty HashMap 
	 */
	public Vertex(double x0, double y0){
		super(x0, y0);
		this.edges = new HashMap<Direction, Vertex>();
		this.cost = Integer.MAX_VALUE;
		this.marked = false;
		this.label = "";
		this.visible = false;
	}
	
	/* initializes Vertex with USER PROVIDED string name and 
	   AUTOMATICALLY SETS cost to infinite value, marked to false, location to (0,0)
	   visibility to false, and edges to a empty HashMap 
	 */
	public Vertex(String name){
		super(0, 0);
		this.edges = new HashMap<Direction, Vertex>();
		this.cost = Integer.MAX_VALUE;
		this.marked = false;
		this.label = name;
		this.visible = false;
	}
	
	/* initializes Vertex with USER PROVIDED label and location (x0, y0) and 
	   AUTOMATICALLY SETS cost to infinite value, marked to false,
	   visibility to false, and edges to a empty HashMap 
	 */
	public Vertex(String name, double x0, double y0){
		super(x0, y0);
		this.edges = new HashMap<Direction, Vertex>();
		this.cost = Integer.MAX_VALUE;
		this.marked = false;
		this.label = name;
		this.visible = false;
	}
	
	/* initializes Vertex with USER PROVIDED label, cost, and if marked and AUTOMATICALLY 
	   SETS location to (0,0), visibility to false, and edges to empty HashMap 
	 */
	public Vertex(String name, int c, boolean m){
		super(0, 0);
		this.edges = new HashMap<Direction, Vertex>();
		this.cost = c;
		this.marked = m;
		this.label = name;
		this.visible = false;
	}
	
	/* returns the opposite cardinal value of the given direction, acts like
	   a compass (North returns South, East returns West)
	 */
	public Direction opposite(Direction d){
		if(d == Direction.NORTH)
			return Direction.SOUTH;
		else if(d == Direction.SOUTH)
			return Direction.NORTH;
		else if(d == Direction.EAST)
			return Direction.WEST;
		else if(d == Direction.WEST)
			return Direction.EAST;
		else{
			System.out.println("Incorrect direction inputted");
			return d;
		}
	}
	
	/* returns the HashMap of the edges of the Vertex with keys as Directions
	   and values as the Vertex the direction points to
	 */
	public HashMap<Direction, Vertex> getEdges(){
		return this.edges;
	}
	
	/* returns the cost of the Vertex  */
	public int getCost(){
		return this.cost;
	}
	
	/* sets the cost of the Vertex  */
	public void setCost(int value){
		this.cost = value;
	}
	
	/* returns whether the Vertex is marked or not (true/false)  */
	public boolean getMarked(){
		return this.marked;
	}
	
	/* sets the marked value of the Vertex  */
	public void setMarked(boolean x){
		this.marked = x;
	}
	
	/* returns whether the Vertex is visible or not (true/false)  */
	public boolean getVisible(){
		return this.visible;
	}
	
	/* sets the visibility of the Vertex  */
	public void setVisible(boolean x){
		this.visible = x;
	}
	
	/* connects Vertex to another Vertex with the given direction
	 * inputs the given data from parameters into edges Hashmap
	 */
    public void connect(Vertex other, Direction dir){
    	this.edges.put(dir, other);
    }
    
    /* returns the neighbor (Vertex) in the given direction  */
    public Vertex getNeighbor(Direction dir){
    	return this.edges.get(dir);
    }
    
    /* returns the Collection list of all the neighbors (all vertices connected to)  */
    public Collection getNeighbors(){
    	return this.edges.values();
    }
    
    /* returns a String giving information about Vertex
     * gives you number of neighbors, cost, and whether marked
     */
	public String toString(){
		int numNeighbors = this.edges.size();
		return "Number of Neighbors: " + numNeighbors + "\n" + 
			   "Cost: " + this.cost + " Marked Flag: " + this.marked;
	}
	
	/* compares the costs of Vertex to one given 
	 * if this Vertex's cost is larger then positive value returned
	 * overrides method in Comparable
	 */
	public int compareTo(Vertex A){
		return this.cost - A.getCost();
	}
	
	/* updates the State of Vertex in Landscape, but not needed so does nothing  
	 * abstract method of parent Cell
	 */
	public void updateState( Landscape scape ){
		return;
	}
	
	/* when visible, draws Vertex as a white rectangle with the connections to other
	   vertices as small black rectangles acting as doors
	 */
	public void draw(Graphics g, int x0, int y0, int scale) {
        if (!this.visible)
            return;
        int xpos = x0 + this.getRow()*scale;
        int ypos = y0 + this.getCol()*scale;
        int border = 2;
        int half = scale / 2;
        int eighth = scale / 8;
        int sixteenth = scale / 16;
        
        // draw rectangle for the walls of the cave
        if (this.cost <= 2)
            // wumpus is nearby
            g.setColor(Color.red);
        else
            // wumpus is not nearby
            g.setColor(Color.black);
        
        g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
        
        // draw doorways as boxes
        g.setColor(Color.black);
        if (this.edges.containsKey(Direction.NORTH))
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        if (this.edges.containsKey(Direction.SOUTH))
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
                       eighth, eighth + sixteenth);
        if (this.edges.containsKey(Direction.WEST))
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        if (this.edges.containsKey(Direction.EAST))
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
                       eighth + sixteenth, eighth);
	}

	
	public static void main(String[] args){ 
		Vertex v1 = new Vertex("1", 5, true);
		Vertex v2 = new Vertex("2", 10, false);
		Vertex v3 = new Vertex("3", 0, true);
		Vertex v4 = new Vertex("4", 5, false);
		
		//should print NORTH EAST SOUTH WEST
		System.out.println(v1.opposite(Direction.SOUTH));
		System.out.println(v1.opposite(Direction.WEST));
		System.out.println(v1.opposite(Direction.NORTH));
		System.out.println(v1.opposite(Direction.EAST));
		
		//tests compareTo method and creates connections accordingly
		if(v1.compareTo(v2) < 0){
			v1.connect(v2, Direction.NORTH);
			v2.connect(v1, v1.opposite(Direction.NORTH));}
		else{
			v1.connect(v2, Direction.SOUTH);
			v2.connect(v1, v1.opposite(Direction.SOUTH));}

		if(v3.compareTo(v1) < 0 && v3.compareTo(v2) < 0 ){
			v2.connect(v3, Direction.NORTH);
			v3.connect(v2, v2.opposite(Direction.NORTH));}
		else{
			v1.connect(v3, Direction.EAST);
			v3.connect(v1, v1.opposite(Direction.EAST));}
			
		if(v4.compareTo(v1) < 0){
			v2.connect(v4, Direction.WEST);
			v4.connect(v2, v1.opposite(Direction.WEST));}
		else{
			v1.connect(v4, Direction.WEST);
			v4.connect(v1, v1.opposite(Direction.EAST));}
		 
		System.out.println(v1.toString());
		System.out.println(v2.toString());
		System.out.println(v3.toString());
		System.out.println(v4.toString());
		
		System.out.println(v1.getNeighbors());
	}
}



