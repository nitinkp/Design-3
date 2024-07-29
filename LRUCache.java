import java.util.HashMap;
import java.util.List;

/*
LinkedList is used to store the most frequently used nodes in ascending order i.e., from head to tail.
So whenever a get() or push() operation is called on a specific key, remove that key's node (if available) and
insert in to the head of the list to maintain the order of recently used. The last node before tail is the least
recently used cache, and hence when the LRU is at capacity, we can remove this node, and since node also stores the key,
we can also remove it from the map.

HashMap is used to store the keys and value as the above nodes. Each node consists of this specific key and its value
as its fields i.e., Node(key, val). This is used to search for a key and retrieve its value.
 */
public class LRUCache {
    static class Node { //doubly linked list node, O(n) S.C where n is capacity
        int key;
        int val;
        Node prev; //reference to previous node
        Node next; //reference to next node
        public Node(int key, int val) { //constructor
            this.key = key;
            this.val = val;
        }
    }

    int capacity; //take capacity as global
    HashMap<Integer,Node> map; //holds key and node(key,val), O(n) S.C where n is capacity
    Node head; //initial dummy head
    Node tail; //initial dummy tail

    public LRUCache(int capacity) { //constructor
        this.capacity = capacity;
        this.map = new HashMap<>();

        //assign head and tail
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) { //O(1) T.C
        if(map.containsKey(key)) { //if the map contains given key
            Node current = map.get(key); //take the value of this key as current
            removeNode(current); //remove this node from the linked list
            addToHead(current); //and add it to the next of head, to say that it has been used
            return current.val; //now return its value
        }
        else return -1; //if no key found
    }

    public void put(int key, int value) { //O(1) T.C
        if(map.containsKey(key)) { //if map already contains the key
            Node current = map.get(key); //take the node as current
            current.val = value; //update its value
            removeNode(current); //remove it from the list
            addToHead(current); //and add it to the head
        } else { //if it doesn't contain already
            if(map.size() == capacity) { //check if the cache is at capacity
                Node nodeToRemove = tail.prev; //if yes, remove the trailing node
                int keyToRemove = nodeToRemove.key; //find its key from the node's field
                removeNode(tail.prev); //remove the node from the list
                map.remove(keyToRemove); //and remove it from the map as well
            }
            //if the cache's capacity isn't breached
            Node newNode = new Node(key, value); //create a new node with the given key-value
            map.put(key, newNode); //insert into both hashmap
            addToHead(newNode); //and the linked list's head as most recently used
        }
    }

    //This method is used to add a specific node to the head of the linked list
    public void addToHead(Node node) { //O(1) T.C
        node.next = head.next; //take head.next node and put it in node.next pointer place
        head.next.prev = node; //make the head.next node's prev pointer point to current node
        head.next = node; //now change the head.next's pointer to current node
        node.prev = head; //also make the current node's prev point to head
    }

    //This method is used to remove a particular node from the linked list
    public void removeNode(Node node) { //O(1) T.C
        node.next.prev = node.prev; //make the current node's next node's prev point to current node's prev
        node.prev.next = node.next; //make the current node's prev node's next point to current node's next
        node.prev = null; //and remove the links of current node's prev and
        node.next = null; //next pointers to isolate this and let garbage collector collect it if not used further.
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        int get1 = cache.get(1);
        cache.put(3,3);
        int get2 = cache.get(2);
        cache.put(4,4);
        int get3 = cache.get(3);
        int get4 = cache.get(4);

        System.out.println("The results for above get operations in the LRU cache is: " +
                List.of(get1, get2, get3, get4));
    }
}