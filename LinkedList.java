/* filename: LinkedList.java (Double-Linked List)
   author: Anh Uong
   date: October 7, 2014
 */
 
 import java.util.*;

/* LinkedList-method of arranging list (order) with Nodes and pointers
 * holds data, user cannot access data without going through LinkedList
  
 * this.head points to the first Node 
 * this.tail points to the last Node
 * numItems keeps track of number of Nodes 
 * Iterator allows easy traversal of list by keeping track of previous
   and next Nodes
 */
public class LinkedList<T> implements Iterable<T>{

	private Node head;  //head is the list
	private int numItems;
	private Node tail;
	
	/*initializes LinkedList as empty list 
	  with head and tail pointing to nothing (null)
	  and numItems in list as 0
	 */
	public LinkedList(){
		this.head = null;
		this.numItems = 0;
		this.tail = null;
	}
	
	/* empties LinkedList  */
	public void clear(){
		this.head = null;
		this.numItems = 0;
		this.tail = null;
	}
	
	/* returns size of LinkedList  */
	public int size(){
		return this.numItems;
	}
	
	/* returns the data of the Node head is pointing to  */
	public T getHead(){
		return this.head.getThing();
	}
	
	/* adds an item to the LinkedList (OLD) */
	public void add(T item){
		Node temp = this.head;
		this.head = new Node(item);
		this.head.setNext(temp);
		this.numItems += 1;
	}	
	
	/* inserts item AT INDEX in LinkedList
	 * keeps track of head and tail pointers and updates numItems
	 * exception cases met for if insert into index out of range,
	   insert into empty list, list of one item, and list of multiple items
	 */
	public void insert(T item, int index){
	
		if(index > size()){
			System.out.println("Index out of range. Must be less than " + size() );
		}
	
		//if empty list
		else if(size() == 0){  
			Node newItem = new Node(item);
			this.head = newItem;
			this.tail = newItem;
			this.head.setNext(null);
			this.head.setPrev(null);
			this.numItems++;
		}
		
		//add to first index if size of list is 1
		else if(size() == 1 && index == 0){   
			Node temp = this.head;
			Node newItem = new Node(item);
			
			this.head = newItem;
			this.head.setNext(temp);
			this.head.setPrev(null);
			
			temp.setPrev(newItem);
			this.tail = temp;
			
			this.numItems++;
		}	
		
		
		//add to any other index in list
		else{ 
	
			//add to first index 
			if(index == 0){   
				Node temp = this.head;
				Node newItem = new Node(item);
			
				this.head = newItem;
				this.head.setNext(temp);
				this.head.setPrev(null);
			
				temp.setPrev(newItem);
			
				this.numItems++;
			}	
			
			//add to last index
			else if(index == size()){    
				Node temp = this.tail;
				this.tail = new Node(item);
				this.tail.setNext(null);
				this.tail.setPrev(temp);
			
				temp.setNext(this.tail);
			
				this.numItems++;
			}
			
			//add to any other index in list
			else{
				Node temp = this.head;
				for(int n=0; n<index-1; n++){  
					temp = temp.getNext();
				}
			
				Node newItem = new Node(item);
			
				newItem.setNext(temp.getNext());
				newItem.setPrev(temp);
			
				temp.setNext(newItem);
				newItem.getNext().setPrev(newItem);
			
				numItems++;		
			}
		}
	}	
			
	/* removes an object from the LinkedList (OLD) */
	public boolean remove(Object obj){
		Node temp = this.head;
		
		//if first value is object
		if(temp.getThing().equals(obj)){
			temp.setNext(temp.getNext().getNext());
			this.numItems = this.numItems - 1;
			
			return true;
		}
		
	
		for(int n=0; n<size()-1; n++){				
			if(temp.getNext().getThing().equals(obj)){
				temp.setNext(temp.getNext().getNext());
				this.numItems = this.numItems - 1;
			
				return true;
			}
			else if(temp.getNext().getThing() == null){
				return false;
			}
			
			temp = temp.getNext();
		
		}
		return false;
			
	}
	
	/* deletes given object from list if found in list
	 * keeps track of head and tail pointers and updates numItems
	 * handles special cases of if delete from empty list, list with one item,
	   list with multiple items (if delete from first, last, or middle)
	 */
	public boolean delete(Object obj){
		
		//if empty list
		if(size() == 0){
			System.out.println("Cannot remove, no items in list");
			return false;
		}
		
		//if list has one item
		else if(size() == 1){
			if(this.head.getThing().equals(obj)){
				this.head = null;
				this.tail = null;
				this.numItems--;
				
				return true;
			}
			return false;
		}
		
		//delete in lists with multiple items
		else{
		
			//if first value is object
			if(this.head.getThing().equals(obj)){
				Node temp = this.head;
				this.head = temp.getNext();				
				temp.getNext().setPrev(null);
				
				this.numItems--;
				return true;
			}
			
			//if last value is object
			else if(this.tail.getThing().equals(obj)){
				Node temp = this.tail;
				this.tail = temp.getPrev();
				temp.getPrev().setNext(null);
				
				this.numItems--;
				return true;
			}
			
			//if any other value is object
			else{
				Node temp = this.head;
				for(int n=0; n<size()-1; n++){
					temp = temp.getNext();
					
					if(temp.getThing().equals(obj)){
						temp.getPrev().setNext(temp.getNext());
						temp.getNext().setPrev(temp.getPrev());
						
						this.numItems--;
						return true;
					}
					
				}
				return false;
			}
			
		}
	}
	
	/* creates a new LLIterator with head as list to traverse (constructor) */
	public Iterator iterator(){
		
		return new LLIterator(this.head);
	}
	
	/* returns an ArrayList of items in LinkedList */
	public ArrayList<T> toArrayList(){
		ArrayList<T> list = new ArrayList<T>();
		
		for(T item: this){  //this goes through Iterator
			list.add(item);
		}
					
		return list;
	}
	
	/* returns a shuffled ArrayList of items in LinkedList  */
	public ArrayList<T> toShuffledList(){
		ArrayList<T> list = new ArrayList<T>();
		
		for(T item: this){  //this goes through Iterator
			list.add(item);
		}

		Collections.shuffle(list);
		
		return list;
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
		public T getThing(){
			return this.data;
		}
		
		/* sets the next Node to a certain Node  */
		public void setNext(Node n){
			this.next = n;
		}
		
		/* returns the next Node  */
		public Node getNext(){
			return this.next;
		}
		
		/* sets the previous Node to a given Node */
		public void setPrev(Node p){
			this.prev = p;
		}
		
		/* returns the previous Node  */
		public Node getPrev(){
			return this.prev;
		}
	}
	
	/* LLIterator keeps track the Nodes and what it points to next  */
	private class LLIterator implements Iterator<T>{
	
		private Node nextNode;
		private Node prevNode;
		
		/* initializes LLIterator with the head of a list  */
		public LLIterator(Node head){
			this.nextNode = head;
			this.prevNode = tail;
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
			T temp = this.nextNode.getThing();
			this.nextNode = this.nextNode.getNext();
			return temp;
		}
		
		/* not needed to implement (yet...)  */
		public void remove(){
		}
		
		/* returns true if the Node has a previous Node
		 * returns false if the Node has reached the beginning 
		   and thus this.prevNode is null
		 */
		public boolean hasPev(){
			if(this.prevNode != null){
				return true;
			}
			else{
				return false;
			}
		}
		
		/* returns the previous data in the list 
		 * moves the traversal onto the next Node
		 */
		public T prev(){
			T temp = this.prevNode.getThing();
			this.prevNode = this.prevNode.getPrev();
			return temp;
		}

		
		
	}
	

		

	public static void main(String[] argv) {
    	LinkedList<Integer> llist = new LinkedList<Integer>();
    	
    	//test with insert
		llist.insert(5, 0);
		llist.insert(10, 1);
		llist.insert(0, 0);

		System.out.printf("\nAfter setup %d\n", llist.size());
		for(Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		llist.clear();

		for (int i=0;i<20;i+=2) {
				llist.insert( i, llist.size() );
			}

		System.out.printf("\nAfter setting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		llist.insert(1, 0);
		llist.insert(13, llist.size());		
		llist.insert(11, 3);
		System.out.printf("\nAfter inserting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		llist.insert(0, 15);
		
		llist.clear();
	
	
	
		//test with delete
		System.out.println(llist.delete(0));
		
		llist.insert(0, 0);
		System.out.printf("\nAfter setting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		
		System.out.println(llist.delete(0));
		System.out.printf("\nAfter deletingFirst %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		llist.insert(1, 0);
		llist.insert(0, 1);
		System.out.printf("\nAfter setting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		llist.delete(0);
		System.out.printf("\nAfter deletingLast %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		
		for (int i=0;i<20;i+=2) {
				llist.insert( i, llist.size() );
			}
		
		System.out.printf("\nAfter setting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		
		
		System.out.println(llist.delete(5));
		System.out.println(llist.delete(6));
		System.out.println(llist.delete(18));
		System.out.println(llist.delete(1));
		System.out.printf("\nAfter deleting %d\n", llist.size());
		for (Integer item: llist) {
			System.out.printf("thing %d\n", item);
		}
		
		
		
		
		
	}
}

			
		
		
		
		
		
		
		
			
			


