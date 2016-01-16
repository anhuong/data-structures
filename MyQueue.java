/* filename: MyQueue.java
   author: Anh Uong
   date: October 21, 2014
*/

import java.util.*;

/* Queue class holds a LinkedList and only allows user limited access to manipulate list
 * methods: getCapacity(), add(), offer(), remove(), poll(), element(), peek()
     add()/offer() adds item to end of list
     remove()/poll() removes item from front of list
 */
public class MyQueue<T> implements Iterable<T>{

	private LinkedList<T> llist;
	private int capacity;

	/* initializes MyQueue with an empty LinkedList and capacity of list to (2^31)-1  */
	public MyQueue(){
		this.llist = new LinkedList<T>();
		this.capacity = Integer.MAX_VALUE;
	}
	
	/* initializes MyQueue with an empty LinkedList and capacity to the given value  */
	public MyQueue(int capacity){
		this.llist = new LinkedList<T>();
		this.capacity = capacity;
	}
	
	/* returns the size of the LinkedList thus the size of Queue  */
	public int getSize(){
		return this.llist.size();
	}
	
	/* returns the capacity (most number of item in list) of the LinkedList  */
	public int getCapacity(){
		return this.capacity;
	}
	
	/* adds an item to the END of the LinkedList
	 * if list is at capacity, cannot add item to list and will throw Exception
	 */ 
	public boolean add(T item) throws Exception{
		if(this.llist.size() == this.capacity){
			throw new IllegalStateException();
		}
		else{
			this.llist.insert(item, llist.size());
			return true;
		}
	}
	
	/* adds an item to the END of the LinkedList
	 * if list is at capacity, cannot add item to list and will return null
	 */ 
	public boolean offer(T item){
		if(llist.size() == this.capacity){
			return false;
		}
		else{
			this.llist.insert(item, llist.size());
			return true;
		}
	}
	
	/* returns the top element of the LinkedList (aka what head is pointing to)
	     top element = first item added to list
	 * if list is empty, no items in list and will throw Exception
	 */
	public T element() throws Exception{
		if(llist.size() == 0){
			throw new NoSuchElementException();
		}
		else{
			return this.llist.getHead();
		}
	}
	
	/* returns the top element of the LinkedList (aka what head is pointing to)
	     top element = first item added to list
	 * if list is empty, no items in list and return null
	 */
	public T peek(){
		if(llist.size() == 0){
			return null;
		}
		else{
			return this.llist.getHead();
		}
	}
	
	/* removes and returns the top element of the LinkedList (aka what head is pointing to)
	     top element = first item added to list
	 * if list is empty, cannot remove nothing, throws Exception
	 */
	public T remove(){
		if(llist.size() == 0){
			throw new NoSuchElementException();
		}
		else{
			T temp = this.llist.getHead();
			this.llist.delete(this.llist.getHead());
			return temp;
		}
	}
	
	/* removes and returns the top element of the LinkedList (aka what head is pointing to)
	     top element = first item added to list
	 * if list is empty, cannot remove nothing, returns null
	 */
	public T poll(){
		if(llist.size() == 0){
			return null;
		}
		else{
			T temp = this.llist.getHead();
			this.llist.delete(this.llist.getHead());
			return temp;
		}
	}
	
	/* implements LLIterator so can traverse through list  */
	public Iterator iterator(){
		
		return this.llist.iterator();
	}
	
	/* converts the LinkedList to an ArrayList  */
	public ArrayList<T> toArrayList(){
		ArrayList<T> list = new ArrayList<T>();
		
		for(T item: this.llist){  //this goes through Iterator
			list.add(item);
		}
					
		return list;
	}
	
	/* clears the LinkedList, where there are no items in the list and 
	   head and tail point to null  
	 */
	public void clear(){
		this.llist.clear();
	}
	
	public static void main(String[] args) throws Exception{
		MyQueue que = new MyQueue(4);
		
		System.out.println(que.getCapacity());
		
		System.out.println(que.peek());
//		que.element();     //threw Exception 
		
		que.add(0);
		que.add(10);
		que.offer(4);
		que.offer(5);
		
		System.out.println(que.offer(9));
//		que.add(9);       //threw Exception
		
		System.out.println(que.toArrayList());
		System.out.println(que.peek());	
		
		que.remove();
		System.out.println(que.element());
		que.poll();
		System.out.println(que.peek());	
		que.remove();
		System.out.println(que.poll());
		
		System.out.println(que.poll());
//		que.remove();      //threw Exception

		
		
	}
}
	
		




