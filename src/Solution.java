import java.util.*;

public class Solution {
    private Instance instance;
    // open facilities
    private Set<Integer> facilities;
    // matches each node with up to alpha facilities, ordered from closest to furthest
    private Map<Integer, List<Integer>> allocations;

    public Solution(Instance instance) {
        this.instance = instance;
        this.facilities = new HashSet<>();
        this.allocations = new HashMap<>();
    }

    public void addFacilitiy(int facility){
        this.facilities.add(facility);
    }

    public boolean hasFacility(int facility){
        return facilities.contains(facility);
    }

    public void allocate(int node, int facility, int priority){
        if (this.allocations.get(node) == null){
            this.allocations.get(node).add(priority, facility);
        }
    }

    public Set<Integer> getFacilities() {
        return facilities;
    }
}
