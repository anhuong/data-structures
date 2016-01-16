/* filename: MyPriorityQueue.java
   author: Anh Uong
   date: October 28. 2014
*/

import java.util.*;

/* a list structure that contains a LinkedList of Nodes in an ORDERED manner
 * head points to the Node with the highest priority item/data
 * add() determines priority of Node and places it in list where belong
 * remove() returns and deletes the Node of highest priority
 */
public class MyPriorityQueue<T> implements Iterable<T>{

	private Node head;
	private int size;
	private int capacity;
	private Comparator comparator;
	
	/* initializes MyPriorityQueue with set capacity and comparator
	 * capacity = total number of Nodes allowed with data
	 * comparator = method of ordering list, finding highest priority item
	 */
	public MyPriorityQueue(int capacity, Comparator<T> comp){
		this.head = null;
		this.size = 0;
		this.capacity = capacity;
		this.comparator = comp;
	}
	
	/* adds the item to the Queue where belong in ordered way
	 * compares the new item with previous data and places where belongs
	 * head always points to item of HIGHEST priority
	 */
	public boolean add(T item){
		if(this.size == this.capacity){
			return false;
		}
		
		else{
			//if empty list
			if(this.size == 0){  
				this.head = new Node(item);
				this.head.setNext(null);
				this.size++;
				return true;
			}
			
			//if list has one item
			else if(this.size == 1){
				if(this.comparator.compare(item, this.head.getData()) > 0 ){  //if a is greater than b
					Node current = this.head;
					this.head = new Node(item);
					this.head.setNext(current);
					this.size++;
					return true;
				}
				else{
					Node newNode = new Node(item);
					newNode.setNext(null);
					this.head.setNext(newNode);
					this.size++;
					return true;
				}
			}
		
			//if list has multiple items
			else{
				Node current = this.head;
				Node next = current.getNext(); 
				
				//if belongs in first spot
				if(this.comparator.compare(item, this.head.getData()) > 0 ){
					Node newNode = new Node(item);
					newNode.setNext(this.head);
					this.head = newNode;
					this.size++;
					return true;
				}
				
				//if belongs in middle
				else{				
					while(next != null){
						if(this.comparator.compare(item, next.getData()) > 0 ){  //if a is greater than b
							Node newNode = new Node(item);
							newNode.setNext(next);
							current.setNext(newNode);
							this.size++;
							return true;
						}
						current = current.getNext();
						next = current.getNext();
					}
					
					//if belongs at end
					Node newNode = new Node(item);
					newNode.setNext(null);
					current.setNext(newNode);
					this.size++;
					return true;
					
				}
				
			}
		}
	}
	
	/* clears the Queue  */
	public void clear(){
		this.head = null;
		this.size = 0;
	}
	
	/* returns a reference to the highest priority element in the Queue
	 * since when adding an item to the Queue, it places the items in an ordered
	   way, just needs to return the head 
	 */
	public T getNext(){
		if(this.size == 0){
			return null;
		}
		else{
			return this.head.getData();
		}
	}
	
	/* returns the size of the Queue  */
	public int getSize(){
		return this.size;
	}
	
	/* returns the capacity of Queue  */
	public int getCapacity(){
		return this.capacity;
	}
	
	/* returns true if the Queue is empty and false if it has any items  */
	public boolean isEmpty(){
		if(this.size == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*returns true if the Queue has reached capacity and false if it has not  */
	public boolean isFull(){
		if(this.size == this.capacity){
			return true;
		}
		else{
			return false;
		}
	}
	
	/* removes and returns the item of highest priority aka the head Node  */
	public T remove(){
		if(this.size == 0){
			return null;
		}
		
		else if(this.size == 1){
			Node current = this.head;
			this.head = null;
			this.size--;
			return current.getData();
		}
		
		else{
			Node current = this.head;
			this.head = this.head.getNext();
			this.size--;
			return current.getData();
		}
	}
			
	/* generates a String that represents the Queue  */
	public String toString(){
		String string = "[";
		ArrayList list = toArrayList();
		for(int n=0; n<this.size; n++){
			string += list.get(n) + ", ";
		}
		string += "]";
		return string;
	}
	
	/* returns an ArrayList of the elements in Queue  */
	public ArrayList toArrayList(){
		ArrayList<T> list = new ArrayList<T>();
		for(T item: this){  //this goes through Iterator
			list.add(item);
		}		
		return list;
	}
	
	/* sets the comparator to given and reorders list based on new comparator  */
	//note: set all of the fields to the new values
	public void setComparator(Comparator comp){
		this.comparator = comp;

		//alternative idea of making sure when set new Comparator, reorders list
		//potentially slower so used other method
/*		MyPriorityQueue newPQ = new MyPriorityQueue(this.capacity, comp);
		for(int n = 0; n<this.size; n++){
			newPQ.add(this.remove());
		}
		
		for(int m = 0; m<this.size; m++){
			this.add(((T)newPQ.remove()));
		}		
*/		
		ArrayList<T> list = toArrayList();
		this.clear();
		
		for(int n = 0; n<list.size(); n++){
			this.add(list.get(n));
		}
				
	}

	/* returns the Node that the head pointer is pointing to  */
	public Node getHead(){
		return this.head;
	}
	
	/* creates a new LLIterator with head as list to traverse (constructor) */
	public Iterator iterator(){
		
		return new LLIterator(this.head);
	}
	
	/* Nodes hold a piece of data and a pointer to the next Node  */
	private class Node{
		
		private Node next;
		private Node prev;
		private T data;
		
		/* initializes empty Node with designated data (item)  */
		public Node(T item){
			this.next = null;
			this.prev = null;
			this.data = item;
		}
		
		/* returns data of Node  */
		public T getData(){
			return this.data;
		}
		
		/* sets the data of the Node to given value  */
		public void setData(T d){
			this.data = d;
		}
		
		/* sets the next Node to a certain Node  */
		public void setNext(Node n){
			this.next = n;
		}
		
		/* returns the next Node  */
		public Node getNext(){
			return this.next;
		}
		
	}
	
	/* LLIterator keeps track the Nodes and what it points to next  */
	private class LLIterator implements Iterator<T>{
	
		private Node nextNode;
		
		/* initializes LLIterator with the head of a list  */
		public LLIterator(Node head){
			this.nextNode = head;
		}
		
		/* returns true if the Node has a subsequent Node
		 * returns false if the Node has reached the end and thus 
		   this.nextNode is null
		 */
		public boolean hasNext(){
			if(this.nextNode != null){
				return true;
			}
			else{
				return false;
			}
		}
		
		/* returns the next item in the list 
		 * moves the traversal onto the next Node
		 */
		public T next(){
			T temp = this.nextNode.getData();
			this.nextNode = this.nextNode.getNext();
			return temp;
		}		
		
	}
}
		


 