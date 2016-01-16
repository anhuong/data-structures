/* filename: Graph.java
   author: Anh Uong
   date: November 18, 2014
*/

import java.util.*;

/* holds an ArrayList of vertices - in this way, contains a chart of all of the 
   vertices and their connections
 * can manipulate data of vertices by adding edges/connections between some or
   between new vertices and by setting the costs of all of the vertices in the 
   Graph depending on the location of a single vertex
 */
public class Graph{

	private ArrayList<Vertex> vertices;
	
	/* initializes Graph to an new empty ArrayList of vertices  */
	public Graph(){
		this.vertices = new ArrayList<Vertex>();
	}
	
	/* returns the ArrayList of vertices  */
	public ArrayList<Vertex> getVertices(){
		return this.vertices;
	}
	
	/* adds given Vertex to Graph by adding to ArrayList of vertices  */
	public void addAgent(Vertex v){
		this.vertices.add(v);
	}
	
	/* returns the number of vertices in Graph  */
	public int vertexCount(){
		return this.vertices.size();
	}
	
	/* creates a new edge/connection between two given vertices in the 
	   given Direction
	 * if one or both of the vertices are not within the Graph already, 
	   will create a new Vertex/vertices and connect them
	 * if both already exist will check if there is already a connection there
	      -if so, will print error message and will NOT override previous one
	      -if not, will create connection
	 */
	public void addEdge(Vertex v1, Vertex.Direction dir, Vertex v2){
		if(this.vertices.contains(v1) == false && this.vertices.contains(v2) == true){
			addAgent(v1);
			
			v1.connect(v2, dir);
			v2.connect(v1, v1.opposite(dir));
		}
		else if(this.vertices.contains(v1) == true && this.vertices.contains(v2) == false){
			addAgent(v2);
			
			v2.connect(v1, dir);
			v1.connect(v2, v2.opposite(dir));
		}
		else if(this.vertices.contains(v1) == false && this.vertices.contains(v2) == false){
			addAgent(v1);
			addAgent(v2);
			
			v1.connect(v2, dir);
			v2.connect(v1, v1.opposite(dir));
		}
		else{
			if(!(v1.getNeighbors().contains(v2))){
				v1.connect(v2, dir);
				v2.connect(v1, v1.opposite(dir));
			}
			else{
				System.out.println("Edge already exists between vertices");
				return;
			}
		}
	}
	
	/* sets the costs of all of the vertices within the Graph based off of the 
	   user provided one
	 */
	public void shortestPath(Vertex v0){
		for(Vertex vertex: this.vertices){
			vertex.setMarked(false);
			vertex.setCost(Integer.MAX_VALUE);
		}
		
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
		v0.setCost(0);
		pq.offer(v0);
		
		while(pq.size() > 0){
			Vertex v = pq.poll();
			v.setMarked(true);
			for(Object w: v.getNeighbors()){
				if(((Vertex) w).getMarked() == false && 
				   v.getCost()+1 < ((Vertex) w).getCost() ){
					((Vertex) w).setCost( (v.getCost() + 1) );
					pq.remove((Vertex) w);
					pq.offer((Vertex) w);
				}
			}
		}
	}

					
	public static void main(String[] args){
		Graph g = new Graph();
		
		//creates vertices and connections and adds them to graph
		Vertex v1 = new Vertex("1");
		Vertex v2 = new Vertex("2");
		Vertex v3 = new Vertex("3");
		Vertex v4 = new Vertex("4");
		
		v1.connect(v2, Vertex.Direction.SOUTH);
		v2.connect(v1, v1.opposite(Vertex.Direction.SOUTH));
		
		v1.connect(v3, Vertex.Direction.EAST);
		v3.connect(v1, v1.opposite(Vertex.Direction.EAST));
		
		v3.connect(v4, Vertex.Direction.NORTH);
		v4.connect(v3, v3.opposite(Vertex.Direction.NORTH));
		
		g.addAgent(v1);
		g.addAgent(v2);
		g.addAgent(v3);
		g.addAgent(v4);
		
		//should print 4
		System.out.println(g.vertexCount());
		
		g.addEdge(v2, Vertex.Direction.WEST, new Vertex("5"));
		
		//should print 5
		System.out.println(g.vertexCount());
		
		//should print 1 2 3 4 5
		for(int n=0; n<g.vertexCount(); n++)
			System.out.println(g.getVertices().get(n).label);
		
		//should print 1,0  2,1  3,1  4,2  5,2
		g.shortestPath(v1);
		System.out.println("From Vertex1:");
		for(int n=0; n<g.vertexCount(); n++)
			System.out.println(g.getVertices().get(n).label + ", " + 
							   g.getVertices().get(n).getCost());
		
		//should print 1,2  2,3  3,1  4,0  5,4
		g.shortestPath(v4);
		System.out.println("From Vertex4:");
		for(int n=0; n<g.vertexCount(); n++)
			System.out.println(g.getVertices().get(n).label + ", " + 
						       g.getVertices().get(n).getCost());
	}
}
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	


