=begin
linked_list.rb
Anh Uong 
Fall 2015

creates a linked list data structure composed of nodes
  - uses classes as basis with functions inside of class
  - singly-linked list with head, tail, and num_nodes
=end

class Node 
  
  def initialize(value)
    @data = value
    @next = nil
  end
  
  def get_Data()
    return @data
  end
  
  def set_Data(value)
    @data = value
  end
  
  def get_Next()
    return @next
  end
  
  def set_Next(next_node)
    @next = next_node
    return @next
  end
  
end

class Linked_List
  
  def initialize()
    @head = nil
    @tail = nil
    @num_nodes = 0
  end
  
  # adds node to FRONT of list, storing given data in the node.
  def ll_push(data)
    new_node = Node.new(data)
    if @num_nodes == 0
      @head = new_node
      @tail = new_node
    else
      current = @head
      new_node.set_Next(current)
      @head = new_node
    end
    
    @num_nodes += 1
  end
  
  # removes node at FRONT of list and returns corresponding data
  def ll_pop()
    current = @head
    
    if @num_nodes == 0
      return nil
    elsif @num_nodes == 1
      @head = nil
      @tail = nil
    else
      second = @head.get_Next()
      @head = second
    end
    
    @num_nodes -= 1
    return current.get_Data()
  end
   
   # adds node to end of list, storing given data in node
  def ll_append(data)
     new_node = Node.new(data)
     
     if @num_nodes == 0
       @head = new_node
       @tail = new_node
     else
       end_node = @tail
        end_node.set_Next(new_node)
       @tail = new_node
     end
    
     @num_nodes += 1
  end
   
   # removes first node in list THAT MATCHES target
   # returns pointer to the data UNLESS node does not exist (returns nil)
  def ll_remove(target)
    target_node = find_node(target)

    if target_node == nil
     return nil
    end

    # if first node is being removed -> need to reset head to node 
    if target_node[0] == 0
     if @num_nodes == 1
       @head = nil
       @tail = nil
     else
       @head = @head.get_Next()
     end
    # if any other node in middle
    else
     current = @head
     # find node that comes before one being deleted
     for i in (0..(target_node[0]-2))
       current = current.get_Next()
     end
     
     # if last node -> need to reset tail to node prior
     if target_node[0] == @num_nodes - 1
       current.set_Next(nil)
       @tail = current
     else
       current.set_Next(current.get_Next().get_Next())
     end
    end

     @num_nodes -= 1
     return target_node[1].get_Data();
    end

    # traverses linked list searching for node that matches target
    # returns pointer to node w/ value unless doesn't exist (returns nil)
  def find_node(target)
   if @num_nodes == 0
     return nil
   else
     current = 0
     current_node = @head
     while current < @num_nodes
       if current_node.get_Data() == target
         return current, current_node
       end
 
       current_node = current_node.get_Next()
       current += 1
     end
     return nil
   end
  end

  # removes all nodes from list
  def ll_clear()
   @head = nil
   @tail = nil
   @num_nodes = 0
  end

  # traverses list and applies given function to the data in each node
  def ll_map(function) 
   current = 0
   current_node = @head

   while current < @num_nodes
     current_node.set_Data(function.call(current_node.get_Data()))
     current_node = current_node.get_Next()
     current += 1
   end
  end
  
  # returns size of linked list
  def ll_size()
   return @num_nodes
  end
  
  # returns NODE head is pointing to
  def get_Head()
   return @head
  end
  
  # returns NODE tail is pointing to
  def get_Tail()
   return @tail
  end
 
end

# creates new LinkedList, initializes, and returns
def ll_create()
  return Linked_List.new()
end

 
# initialize new Linked List 
ll = Linked_List.new()
# could also do ll = ll_create()

# add differing data type values to Linked list
ll.ll_push(50)
ll.ll_push("hi")
ll.ll_append(false)
ll.ll_append(638.3)

# print out values of nodes in order
# LinkedList = "hi", 50, false, 638.3
print "A: LinkedList has ", ll.ll_size(), " nodes\n"
current = ll.get_Head()
for i in (1..ll.ll_size())
  print "Index ", i, " in LinkedList = ", current.get_Data(), "\n"
  current = current.get_Next()
end

# print and delete certain values/nodes
print "\nRemoving 10 -> ", ll.ll_remove(10), "\n"     # nil (aka blank)
print "Removing false -> ", ll.ll_remove(false), "\n"  # false (which means able to delete)
print "Popping node -> ", ll.ll_pop(), "\n"          # "hi" 

# print out values of nodes in order
# LinkedList = 50, 638.3
print "\nB: LinkedList has ", ll.ll_size(), " nodes\n"
current = ll.get_Head()
for i in (1..ll.ll_size())
  print "Index ", i, " in LinkedList = ", current.get_Data(), "\n"
  current = current.get_Next()
end

# empties list
ll.ll_clear()
print "\nC: Clearing list - LinkedList has ", ll.ll_size(), " nodes\n"

# create two linked lists with different data types as values
l2 = Linked_List.new()
ll.ll_push(1)
ll.ll_push(2)
ll.ll_push(3)
ll.ll_push(4)
ll.ll_push(5)
# ll = 5, 4, 3, 2, 1

l2.ll_append(1.1)
l2.ll_append(2.2)
l2.ll_append(3.3)
l2.ll_append(4.4)
l2.ll_append(5.5)
# l2 = 1.1, 2.2, 3.3, 4.4, 5.5

# squares value within node
def square(value)
  puts value**2
  return value**2
end

def print(value)
  puts value
  return value
end

# applies above function to all nodes via mapping function
puts "\nLinked List 1 values:"
ll.ll_map(method(:print))
puts "Linked List 2 values:"
l2.ll_map(method(:print))

puts "\nLinked List 1 values squared:"
ll.ll_map(method(:square))
puts "Linked List 2 values squared:"
l2.ll_map(method(:square))

# ensures that values were changed - prints out values in nodes
puts "\nEnsure worked - Linked List 1 values:"
ll.ll_map(method(:print))

    
      
    