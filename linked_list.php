<?php
/*
linked_list.php
Anh Uong 
November 2, 2015

creates a linked list data structure composed of nodes
  - uses classes as basis with functions inside of class
  - singly-linked list with head, tail, and num_nodes
=end
*/


class Node{
	private $data;
	private $next;
	
	function __construct($value=NULL, $next_node=NULL){
		$this->data = $value;
		$this->next = $next_node;
	}
	
	function get_Data(){
		return $this->data;
	}
	
	function set_Data($value){
		$this->data = $value;
	}
	
	function get_Next(){
		return $this->next;
	}
	
	function set_Next($next_node){
		$this->next = $next_node;
	}
}


class Linked_List{
	
	private $head;
	private $tail;
	private $num_nodes;
	
	function __construct(){
		$this->head = NULL;
		$this->tail = NULL;
		$this->num_nodes = 0;
	}
	
    # adds node to FRONT of list, storing given data in the node.
    function ll_push($data){
    	$node = new Node($data);
		if($this->num_nodes == 0){
			$this->head = $node;
			$this->tail = $node;
		}
		else{
			$current = $this->head;
			$node->set_Next($current);
			$this->head = $node;
				
		}
		$this->num_nodes++;
    }
	
    # removes node at FRONT of list and returns corresponding data
    function ll_pop(){
    	$current = $this->head;
		
		if( $this->num_nodes == 0){
			return NULL;
		}
		elseif($this->num_nodes == 1){
			$this->head = NULL;
			$this->tail = NULL;
		}
		else{
			$this->head = $this->head->get_Next();
		}
		$this->num_nodes--;
		return $current->get_Data();
    }
	
    # adds node to end of list, storing given data in node
	function ll_append($data){
		$new_node = new Node($data);
		
		if($this->num_nodes == 0){
			$this->head = $new_node;
			$this->tail = $new_node;
		}
		else{
			$last_node = $this->tail;
			$last_node->set_Next($new_node);
			$this->tail = $new_node;
		}
		$this->num_nodes++;
	}
	
    # removes first node in list THAT MATCHES target
    # returns pointer to the data UNLESS node does not exist (returns nil)
	function ll_remove($target, $compfunc){
		if( $this->num_nodes == 0){
			return NULL;
		}
		
		$current = $this->head;
		$num_curr = 0;
		
		while($num_curr < $this->num_nodes){
			$exists = $compfunc( $target, $current->get_Data() );
			# if finds node with value to remove
			if($exists == 1){
				# if first node
				if($num_curr == 0){
					# if only one node in list
					if($$this->num_nodes == 1){
						$this->head = NULL;
						$this->tail = NULL;
					}
					else{
						$this->head = $this->head->get_Next();
					}
				}
				# if any other node
				else{
					# get node that comes before one deleting
					$prev = $this->head;
					for($x=0; $x<$num_curr-1; $x++){
						$prev = $prev->get_Next();
					}
					# if last node
					if($num_curr == $this->num_nodes-1){
						$prev->set_Next(NULL);
						$this->tail = $prev;
					}
					# if middle node
					else{
						$prev->set_Next($prev->get_Next()->get_Next());
					}
				}
				$this->num_nodes--;
				return $current->get_Data();
			}
			$num_curr++;
			$current = $current->get_Next();
		}
		return NULL;
	}
	 
     # removes all nodes from list
	 function ll_clear(){
		 $this->head = NULL;
		 $this->tail = NULL;
		 $this->num_nodes = 0;
	 }
	 
     # traverses list and applies given function to the data in each node
     function ll_map($function) {
		 $current = $this->head;
		 $num_curr = 0;
		 
		 while($num_curr < $this->num_nodes){
			 $current->set_Data( $function($current->get_Data()) );
			 $current = $current->get_Next();
			 $num_curr++;
		 }
     }
	 
     # returns size of linked list
     function ll_size(){
		 return $this->num_nodes;
     }
	
}


# creates new LinkedList, initializes, and returns
function ll_create(){
	return new Linked_List();
}

function print_values($val){
	echo $val, "\n";
	return $val;
}

# compares value of two different items 
# if same -> returns 1, else returns 0
function compare($target, $node_val){
	 if($target === $node_val){
		 return 1;
	 }
	 return 0;
 }
 
 function add_ten($node_val){
	 return $node_val + 10;
 }


# TESTING

# initialize new Linked List 
$ll = ll_create();

# add different data type values to Linked list
$ll->ll_push(1);
$ll->ll_push(2.89);
$ll->ll_push(3);
$ll->ll_push(4.11);
$ll->ll_append("Sally");
$ll->ll_append("Joe");
$ll->ll_append("Steven");

# print out values of nodes in order - test mapping
echo "A: LinkedList has ", $ll->ll_size(), " nodes\n";
$ll->ll_map(print_values);

# print and delete certain values/nodes
echo "\nRemoving 9 -> ", $ll->ll_remove(9, "compare"), "\n";     # nil (aka blank)
echo "Removing Joe -> ", $ll->ll_remove("Joe", "compare"), "\n";  # Joe (which means able to delete)
echo "Popping node -> ", $ll->ll_pop(), "\n";          # 4.11 
echo "Popping node -> ", $ll->ll_pop(), "\n";          # 3


# print out values of nodes in order - test removing
echo "\nB: LinkedList has ", $ll->ll_size(), " nodes\n";
$ll->ll_map(print_values);


$ll->ll_map(add_ten);
echo "\nC: LinkedList has ", $ll->ll_size(), " nodes\n";
$ll->ll_map(print_values);

$ll->ll_clear();
echo "\nD: LinkedList has ", $ll->ll_size(), " nodes\n";
$ll->ll_map(print_values);


?>