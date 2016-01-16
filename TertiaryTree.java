/* filename: TertiaryTree.java
   author: Anh Uong
   date: November 11, 2014
 */
 
import java.util.*;

/* TreeNode holds its data and a pointer to a left, right, and center TreeNode  */
class TreeNode<T>{

	public T data;
	public TreeNode left;
	public TreeNode right;
	public TreeNode center;
	
		

	/* initializes TreeNode with the given data in parameters  */
	public TreeNode(T d, TreeNode l, TreeNode c, TreeNode r){
			this.data = d;
			this.left = l;
			this.right = r;
			this.center = c;
	}
}

/* holds a root TreeNode that points to a TreeNode connected Tree,
   where each TreeNode can have up to 3 child nodes
 * contains methods to manipulate the tree or the Nodes
 * NOT ordered
 */
public class TertiaryTree<T>{

	public TreeNode root;

	public enum Direction{ LEFT, RIGHT, CENTER }

	/* initializes Tree to empty (root points to null, no Nodes in Tree)  */
	public TertiaryTree(){
		this.root = null;
	}
	
	/* returns the size of the Tree  */
	public int size(){
		return sizeAux(this.root);
	}
	
	/* Auxiliary method using recursion to return the size of the Tree
	 * takes in a TreeNode as its parameter, which is the root of the Tree  */
	public int sizeAux(TreeNode node){
		if(node == null){
			return 0;
		}
		else{
			return ( 1 + sizeAux(node.left) + sizeAux(node.center) + sizeAux(node.right) );
		}
	}
	
	/* adds up the data of every TreeNode in the Tree
	 * only works if the data held in the TreeNodes are Integers
	 */
	public int total() {
	   return totalAux(this.root); 
	}
	
	/* Auxiliary method using recursion to obtain the data from every TreeNode  */
	public int totalAux(TreeNode node) {
		if(node == null){
			return 0;
		}
		else{
			if(!(node.data instanceof Integer)){
				System.out.println("Cannot complete. Data not integers");
				return 0;
			}
			else
				return (int) node.data + totalAux(node.left) + totalAux(node.center) + totalAux(node.right);
		}
	}
	
	/* returns true if the Tree isEmpty and false if it has at least one Node  */
	public boolean isEmpty(){
		if(this.root == null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/* empties the Tree by setting root back to null  */
	public void clear(){
		this.root = null;
	}
	
	/* returns true if the data is found within the Tree  */
	public boolean contains(T data){
		return containsAux(this.root, data);
	}
	
	/* Auxiliary function using recursion to check every Node in the Tree and
	   check the data inside each Node 
	 * if the item is within the Tree, it will return true 
	 */
	public boolean containsAux(TreeNode node, T x){

		if(node.data == x){
			return true;
		}
		else{
			if(node.left != null && containsAux(node.left, x) ){
				return true;
			}
			if(node.center != null && containsAux(node.center, x) ){
				return true;
			}
			if(node.right != null && containsAux(node.right, x) ){
				return true;
			}
		}
		return false;
	}
	
	/* returns the TreeNode with the given data  
	 * if there is no TreeNode with the value, then returns null
	 */
	public TreeNode getNode(T data){
		if(contains(data) == false)
			return null;
		else
			return getAux(this.root, data);
	}
	
	/* Auxiliary method using recursion to return the Node with the given value
	 * checks every Node and compares the data to the given value in parameters
	 * the root of the Tree should be the given node
	 */
	public TreeNode getAux(TreeNode node, T x){

		if(node.data == x){
			return node;
		}
		else{
			if(node.left != null &&  getAux(node.left, x) != null){
				return getAux(node.left, x);
			}
			if(node.center != null &&  getAux(node.center, x) != null){
				return getAux(node.center, x);
			}
			if(node.right != null && getAux(node.right, x) != null ){
				return getAux(node.right, x);
			}
		}
		return null;
	}	
	
	/* adds the given node to the Tree after the node with the 
	   given data in the direction designated
	 * calls get() to get the Node with the provided data and then 
	   uses enum class to figure out if should add to left, center, or right node
	 */
	public void add(TreeNode node, T prevData, Direction dir){
		
		//if empty Tree
		if(this.root == null){
			this.root = node;
		}

		//if Tree has multiple items --> adds after designated Node in given direction
		else{
			if(contains(prevData) == false){
				System.out.println("Data not found in Tree. Could not add Node");
				return;
			}
			else{
				if(dir == Direction.LEFT)
					getNode(prevData).left = node;
					
				else if(dir == Direction.CENTER)
					getNode(prevData).center = node;
					
				else if(dir == Direction.RIGHT)
					getNode(prevData).right = node;
					
				else
					System.out.println("Please input correct direction placement of new Node");
			}
		}
	}	
	
	/* returns the previous Node of the node with the given data  */
	public TreeNode findPrev( T x){
		return findPrevAux(this.root, x);
	}
	
	/* Auxiliary code using recursion to return the previous node 
	   of the node with the given data
	 */
	public TreeNode findPrevAux(TreeNode node, T x){
		if(this.root.data == x){
			return null;
		}
		
		if(node.left != null && node.left.data == x){
			return node;
		}
		if(node.center != null && node.center.data == x){
			return node;
		}
		if(node.right != null && node.right.data == x)
			return node;
		else{
			if(node.left != null &&  findPrevAux(node.left, x) != null){
				return findPrevAux(node.left, x);
			}
			if(node.center != null &&  findPrevAux(node.center, x) != null){
				return findPrevAux(node.center, x);
			}
			if(node.right != null && findPrevAux(node.right, x) != null ){
				return findPrevAux(node.right, x);
			}
		}
		return null;
	}
	
	/* deletes the Node with the given data and detaches the rest of the
	   Tree that came along with the Node
	 * calls findPrev() to get the previous Node in order to set its pointer to null
	 */
	public boolean delete(T x){
		if(this.contains(x) == false){
			return false;
		}
		else{
			if(findPrev(x).left != null && findPrev(x).left.data == x){
				findPrev(x).left = null;
				return true;
			}
			else if(findPrev(x).center != null && findPrev(x).center.data == x){
				findPrev(x).center = null;
				return true;
			}
			else if(findPrev(x).right != null && findPrev(x).right.data == x){
				findPrev(x).right = null;
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		TertiaryTree tree1 = new TertiaryTree();
		tree1.root = new TreeNode(3, new TreeNode(4,  null, null,null),
		                            new TreeNode(5,  null, null,null), 
		                             new TreeNode(6,  null, null,null) );
		
		System.out.println("Tree 1:");
		System.out.println(tree1.size());
		System.out.println(tree1.total());
		System.out.println(tree1.contains(5));
		System.out.println(tree1.findPrev(5).data);
		System.out.println(tree1.findPrev(6).data);
		System.out.println(tree1.findPrev(10));
		System.out.println(tree1.findPrev(3));
		System.out.println(tree1.findPrev(5).right.data);
		
		if(tree1.findPrev(5).left != null && (int) tree1.findPrev(5).left.data == 5)
			tree1.findPrev(5).left = null;
			
		else if(tree1.findPrev(5).center != null && (int) tree1.findPrev(5).center.data == 5)
			tree1.findPrev(5).center = null;
			
		else if(tree1.findPrev(5).right != null && (int) tree1.findPrev(5).right.data == 5)
			tree1.findPrev(5).right = null;
			
		System.out.println(tree1.contains(5));
		System.out.println(tree1.getNode(3).center);
		
		
		
		TertiaryTree tree2 = new TertiaryTree();
		tree2.root = new TreeNode(1, new TreeNode(2,new TreeNode(3, null, null,null), null,
		                new TreeNode(4,null, null ,null)), null, new TreeNode(5,null, null,
		                    new TreeNode(6,new TreeNode(7,null, null,null), null,
		                                     new TreeNode(8,null, null ,null))));
		
		System.out.println("\n" + "Tree 2:");
		System.out.println(tree2.size());
		System.out.println(tree2.total());
		
		System.out.println(tree2.contains(8));
		System.out.println(tree2.contains(0));

		System.out.println(tree2.getNode(8));
		System.out.println(tree2.getNode(8).data);
		System.out.println(tree2.getNode(0));

		System.out.println(tree2.findPrev(8).data);
		System.out.println(tree2.findPrev(8).left.data);
		System.out.println(tree2.findPrev(8).right.data);


		tree2.add(new TreeNode(10, null, new TreeNode(22, null, null, null),
										 new TreeNode(13, null, null, null)), 7, Direction.LEFT);
										 
		
		System.out.println(tree2.size());
		System.out.println(tree2.total());
		
		System.out.println(tree2.getNode(7).data);
		System.out.println(tree2.getNode(7).left.data);
		System.out.println(tree2.getNode(10).right.data);
		
		// can try replacing left, right, center and check values after
		// to see if it works, which it does!
		tree2.add(new TreeNode(5, null, null, null), 1, Direction.LEFT);
		System.out.println(tree2.size());
		System.out.println(tree2.total());
		
		System.out.println(tree2.getNode(7));
		System.out.println(tree2.getNode(1).right.data);
		System.out.println(tree2.getNode(1).left.data);
				
		System.out.println(tree2.delete(8));
		System.out.println(tree2.delete(0));
		
		System.out.println(tree2.contains(8));
		
		System.out.println(tree2.delete(7));
		System.out.println(tree2.size());
		System.out.println(tree2.total());
		

	}

}

