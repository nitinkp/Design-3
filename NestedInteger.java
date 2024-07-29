import java.util.ArrayList;
import java.util.List;

public class NestedInteger {
    private final Integer singleInteger;
    private final List<NestedInteger> nestedList;

    // Constructor initializes a single integer.
    public NestedInteger(Integer value) {
        this.singleInteger = value;
        this.nestedList = new ArrayList<>();
    }

    // Constructor initializes an empty list.
    public NestedInteger() {
        this.singleInteger = null;
        this.nestedList = new ArrayList<>();
    }

    // Constructor initializes a nested list.
    public NestedInteger(List<NestedInteger> list) {
        this.singleInteger = null;
        this.nestedList = list;
    }

    public boolean isInteger() {
        return singleInteger != null;
    }

    public Integer getInteger() {
        return singleInteger;
    }

    public List<NestedInteger> getList() {
        return nestedList;
    }

    // Additional method to add a NestedInteger to the nested list
    public void add(NestedInteger ni) {
        if (this.singleInteger != null) {
            throw new IllegalStateException("This NestedInteger holds a single integer.");
        }
        this.nestedList.add(ni);
    }
}
