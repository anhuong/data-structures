/* 
linkedlist.h
Anh Uong
November 2, 2015


Defines a LinkedList struct and Node struct
as well as methods that manipulate the LinkedList.
	-methods requires a LinkedList pointer as an argument
*/

#include <stdio.h>
#include <stdlib.h>


typedef struct Node {
	struct Node * next;
	void * value;
};

typedef struct LinkedList {
	struct Node * head;
	struct Node * tail;
	int num_nodes;
		
};


// creates a new LinkedList struct, initializes it, and returns it.
struct LinkedList * ll_create(){
	struct LinkedList * ll;
	//malloc?
	ll = (struct LinkedList *)malloc(sizeof( struct LinkedList ));
	ll->head = NULL;
	ll->tail = NULL;
	ll->num_nodes = 0;
	return ll;
};

// adds node to FRONT of list, storing given data in node
void ll_push(struct LinkedList *ll, void *data){
	struct Node * new_node;
	new_node = (struct Node *)malloc(sizeof( struct Node ));
	new_node->value = data;
	
	if( ll->num_nodes == 0){
		ll->head = new_node;
		ll->tail = new_node;
		new_node->next = NULL;
	}
	else{
		struct Node * current;
		current = ll->head;
		new_node->next = current;
		ll->head = new_node;	
	}
	ll->num_nodes++;
}
	
// removes node at FRONT of list and returns associated data
void * ll_pop(struct LinkedList *ll) {
	if(ll->num_nodes == 0){
		return NULL;
	}
	
	struct Node * current;
	current = ll->head;
	
	if( ll->num_nodes == 0){
		return NULL;
	}
	else if(ll->num_nodes == 1){
		ll->head = NULL;
		ll->tail = NULL;
	}
	else{
		ll->head = ll->head->next;
	}
	ll->num_nodes--;
	return current->value;
}

// adds node to END of list, storing given data in node
void ll_append(struct LinkedList *ll, void *data) {
	struct Node * new_node;
	new_node = (struct Node *)malloc(sizeof( struct Node ));
	new_node->value = data;
	
	if( ll->num_nodes == 0){
		ll->head = new_node;
		ll->tail = new_node;
		new_node->next = NULL;
	}
	else{
		struct Node * current;
		current = ll->tail;
		current->next = new_node;
		ll->tail = new_node;	
	}
	ll->num_nodes++;
}


// removes first node in list whose data matches target 
//		given comparison function (which returns if # exists in list)
// returns pointer to data of node removed
void * ll_remove(struct LinkedList *ll, void *target, int (*compfunc)(void *, void *)) {
	if(ll->num_nodes == 0){
		return NULL;
	}
	
	struct Node * current;
	int num_curr = 0;
	current = ll->head;
	
	while(num_curr < ll->num_nodes){
		// options are 0 (yes) or 1 (no)	
		int target_exists = (*compfunc)(target, current->value);
		
		if(target_exists){
			// if first node being removed
			if(num_curr == 0){
				if(ll->num_nodes == 1){
					ll->head = NULL;
					ll->tail = NULL;
				}
				else{
					ll->head = ll->head->next;
				}
			}
			// if any other node
			else{
				struct Node * prev;
				prev = ll->head;

				// reach the node PRIOR to target node so can reroute
				for(int i=0; i<num_curr-1; i++){
					prev = prev->next;
				}
				
				// if last node -> need to reset tail to node prior
				if(num_curr == (ll->num_nodes - 1) ){
					prev->next = NULL;
					ll->tail = prev;
				}
				// if any middle node
				else{
					prev->next = prev->next->next;
				}
			}
			ll->num_nodes--;
			return current->value;
		}
		current = current->next;
		num_curr++;
	}
	return NULL;
}



// I THINK THIS IS THE LATEST VERSION THAT WORKS FOR MY ORIGINAL FUNCTION

// // removes first node in list whose data matches target
// //		given comparison function (which returns if # exists in list)
// // returns pointer to data of node removed
// void * ll_remove(struct LinkedList *ll, void *target, int (*compfunc)(struct LinkedList *, void *)) {
// 	// options are 0 (yes) or 1 (no)
// 	int target_exists = (*compfunc)(ll, target);
// 	printf("TARGET INDEX: %d\n", target_exists);
// 	if(target_exists == 0){
// 		return NULL;
// 	}
//
// 	struct Node * current;
// 	int num_curr = 0;
// 	current = ll->head;
//
// 	while(num_curr < ll->num_nodes){
// 		if(current->value == target){
// 			// if first node being removed
// 			if(num_curr == 0){
// 				if(ll->num_nodes == 1){
// 					ll->head = NULL;
// 					ll->tail = NULL;
// 				}
// 				else{
// 					ll->head = ll->head->next;
// 				}
// 			}
// 			// if last node -> need to reset tail to node prior
// 			else if(num_curr == ll->num_nodes-1){
// 				struct Node * winner;
// 				winner = ll->head;
// 				for(int i=0; i<ll->num_nodes-2; i++){
// 					winner = winner->next;
// 				}
// 				winner->next = NULL;
// 				ll->tail = winner;
// 			}
// 			// if any other node
// 			else{
// 				struct Node * winner;
// 				winner = current;
// 				winner->next = winner->next->next;
// 			}
// 			break;
// 		}
// 		current = current->next;
// 		num_curr++;
// 	}
//
// 	ll->num_nodes--;
// 	return current->value;
//
// }



// COMP FUNCTION GIVES INDEX OF NODE WITH DATA 
// porblem if first node because then index is 0 and null is 0

// void * ll_remove(struct LinkedList *ll, void *target, int (*compfunc)(struct LinkedList *, void *)) {
// 	int target_index = (*compfunc)(ll, target);
//
// 	if(target_index == 0){
// 		return NULL;
// 	}
//
// 	// if first node being removed
// 	if(target_index == 0){
// 		struct Node * winner;
// 		winner = ll->head;
// 		if(ll->num_nodes == 1){
// 			ll->head = NULL;
// 			ll->tail = NULL;
// 		}
// 		else{
// 			ll->head = ll->head->next;
// 		}
// 		ll->num_nodes--;
// 		return winner->value;
// 	}
// 	// if any other node being removed
// 	else{
// 		struct Node * current;
// 		current = ll->head;
//
// 		// reach the node PRIOR to target node so can reroute
// 		for(int i=0; i<target_index-1; i++){
// 			current = current->next;
// 		}
// 		// this is the node with data we want
// 		struct Node * winner;
// 		winner = current->next;
//
// 		// if last node -> need to reset tail to node prior
// 		if(target_index == (ll->num_nodes - 1) ){
// 			current->next = NULL;
// 			ll->tail = current;
// 		}
// 		else{
// 			current->next = current->next->next;
// 		}
// 		ll->num_nodes--;
// 		return winner->value;
// 	}
// }


// comparison function - finds the data matching target
// returns the index of node containing data
int search_ll(struct LinkedList *ll, void * target){
	if(ll->num_nodes == 0){
		return NULL;
	}
	else{
		int current = 1;
		struct Node * current_node;
		current_node = ll->head;
		
		while(current != ll->num_nodes){
			printf("SEARCHING: index %d, value %d for target %d\n", current, current_node->value, target);
			if(current_node->value == target){
				return current;
			}
			current_node = current_node->next;
			current++;
		}
		return NULL;
	}
}

// comparison function - finds the data matching target
// returns the index of node containing data
int search_int(struct LinkedList *ll, void * target){
	if(ll->num_nodes == 0){
		return NULL;
	}
	else{
		int current = 1;
		struct Node * current_node;
		current_node = ll->head;
		int * a = (int *)target;
		int * b = (int *)current_node->value;
		
		while(current != ll->num_nodes){
			printf("SEARCHING: index %d, value %d for target %d\n", current, current_node->value, target);
			printf("SEARCHING2: index %d, value %d for target %d\n", current, a, b);
			if(current_node->value == target){
				return current;
			}
			current_node = current_node->next;
			current++;
		}
		return NULL;
	}
}

// returns size of list
int ll_size(struct LinkedList *ll) {
	return ll->num_nodes;
}

// removes all of the nodes from the list
// frees the associated data using the given function
void ll_clear(struct LinkedList *ll, void (*freefunc)(struct LinkedList *)) {
	(*freefunc)(ll);
	ll->head = NULL;
	ll->tail = NULL;
	ll->num_nodes = 0;
}

// frees the memory attached to the Linked List
void free_list(struct LinkedList * ll){
	struct Node * current;
	current = ll->head;
	
	for(int i=0; i<ll->num_nodes; i++ ){
		free(current);
		current = current->next;
	}
	free(ll);
}

// traverses list and applies given function to the data in each node
void ll_map(struct LinkedList *ll, void (*mapfunc)(struct Node *)) {
	struct Node * current;
	current = ll->head;
	
	// instead of for loop can also do while current->next != NULL
	for(int i=0; i<ll->num_nodes; i++ ){
		(*mapfunc)(current->value);
		current = current->next;
	}
}

// function that prints an integer
void printInt(void *i) {
	int *a = (int *)i;

	printf("value: %d\n", *a);
}

// function that compares two integers and returns 1 if they are equal
int compInt(void *i, void *j) {
	int *a = (int *)i;
	int *b = (int *)j;

	return(*a == *b);
}
	
	
int main (int argc, char *argv[]) {
	struct Node * start;
	struct Node * second;

	start = (struct Node *)malloc(sizeof( struct Node ));
	second = (struct Node *)malloc(sizeof( struct Node ));

	start->value = "hi";
	second->value = "bye";
	second->next = start;

	printf("Start's value is %s\n", start->value);
	printf("Second's value is %s\n", second->value);
	printf("Second's value is %s\n", second->next->value);

	free(start);
	free(second);

	struct LinkedList * ll = ll_create();
	ll_push(ll, 5);
	ll_push(ll, 7);

	printf("Head's value is %d\n", ll->head->value);
	printf("Tails's value is %d\n", ll->tail->value);
	printf("Head points to %x\n", ll->head->next);
	printf("Value after head is %d\n", ll->head->next->value);
	printf("Size of linked list is %d\n", ll->num_nodes);

	ll_push(ll, 93);
	ll_append(ll, 3028);
	printf("Size of linked list is %d\n", ll->num_nodes);

	int current = 0;
	struct Node * current_node;
	current_node = ll->head;
	while(current < ll->num_nodes){
		printf("Value of %d node is %d\n", current, current_node->value);
		current_node = current_node->next;
		current++;
	}

	// 
// printf("Removing 10 from list -> %d\n", ll_remove(ll, 10, &search_ll));
// 	printf("Removing 93 from list -> %d\n", ll_remove(ll, 93, &search_ll));
// 	printf("Popping node off the front -> %d\n", ll_pop(ll));
//
// 	current = 0;
// 	current_node = ll->head;
// 	while(current < ll->num_nodes){
// 		printf("Value of %d node is %d\n", current, current_node->value);
// 		current_node = current_node->next;
// 		current++;
// 	}
	
	// create a list
	struct LinkedList *l;
	int *a;
	int *target;
	int i;
	l = ll_create();

	// push data on the list
	for(i=0;i<20;i+=2) {
		a = malloc(sizeof(int));
		*a = i;

		ll_push(l, a);
	}
	
	// testing removing data
	target = malloc(sizeof(int));

	*target = 16;
	a = ll_remove(l, target, compInt);
	if(a != NULL)
		printf("\nremoved: %d\n", *a);
	else
		printf("\nNo instance of %d\n", *target);

	*target = 11;
	a = ll_remove(l, target, compInt);
	if(a != NULL)
		printf("\nremoved: %d\n", *a);
	else
		printf("\nNo instance of %d\n", *target);

	printf("\nAfter removals\n");
	ll_map(l, printInt);
	
	
	ll_clear(l, free_list);
	printf("\nAfter clear\n");
	ll_map(l, printInt);
	
	
	// rebuild and test append and pop
	for(i=0;i<5;i++) {
		a = malloc(sizeof(int));
		*a = i;
		ll_append(l, a);
	}
	
	a = ll_pop(l);
	printf("\npopped: %d\n", *a);
	//free(a);

	a = ll_pop(l);
	printf("popped: %d\n", *a);
	//free(a);
	
	printf("\nAfter pop\n");
	ll_map(l, printInt);

	return (0);
}