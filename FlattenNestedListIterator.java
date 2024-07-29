import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class FlattenNestedListIterator implements Iterator<Integer> {
    NestedInteger nextEl; //to store the next val from the iterator
    Stack<Iterator<NestedInteger>> st; //controlled recursive stack

    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        this.st = new Stack<>(); //O(n) S.C, where n is depth of nested integers
        st.push(nestedList.iterator()); //push the entire list as iterator into stack
    }

    @Override
    public Integer next() { //O(1) T.C
        return nextEl.getInteger(); //at any point, global variable nextEl stores nestedInteger version of the integer
    }

    @Override
    public boolean hasNext() { //O(1) amortized T.C, worst case O(n) where n is depth of nested integers
        while(!st.isEmpty()) { //recurse until the stack is empty
            if(!st.peek().hasNext()) st.pop(); //if the iterator in stack doesn't have a next element,
                // pop it as it is already processed. This is native iterators hasNext() method.
            else { //if there is a next element in stack
                nextEl = st.peek().next(); //store it in the global variable, as native iterators method
                //next() automatically increments the index, and we will lose the reference to actual 'next'
                if(nextEl.isInteger()) { //if this element is an integer
                    return true;
                }
                else { //if it not an integer, it must be a list
                    st.push(nextEl.getList().iterator()); //get the list out of this nestedInteger list type,
                    //put an iterator on it and push into the stack for further processing
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        // Create a nested list [1,[4,[6]]]
        NestedInteger n1 = new NestedInteger(1);
        NestedInteger n2 = new NestedInteger(4);
        NestedInteger n3 = new NestedInteger(List.of(new NestedInteger(6)));
        NestedInteger n4 = new NestedInteger(Arrays.asList(n2, n3));
        List<NestedInteger> nestedList = Arrays.asList(n1, n4);

        FlattenNestedListIterator i = new FlattenNestedListIterator(nestedList);
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
