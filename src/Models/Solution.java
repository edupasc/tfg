package Models;

import java.util.*;

public class Solution implements Cloneable{
    private Instance instance;
    // open facilities
    private Set<Integer> facilities;
    // matches each node with up to alpha facilities, ordered from closest to furthest
    private Map<Integer, List<Integer>> allocations;
    private Double objectiveFunction;

    public Solution(Instance instance) {
        this.instance = instance;
        this.facilities = new HashSet<>();
        this.allocations = new HashMap<>();
        this.objectiveFunction = Double.MIN_VALUE;
    }

    public void addFacilitiy(int facility){
        this.facilities.add(facility);
    }

    public boolean hasFacility(int facility){
        return facilities.contains(facility);
    }

    public void allocate(int node, int facility, int priority){
        List<Integer> l = this.allocations.get(node);
        // if the given node had no facilities allocated, create a new facilities list and add it to the map
        if (l == null){
            l = new LinkedList<Integer>();
            this.allocations.put(node, l);
        }
        l.add(priority, facility);
        // if the distance between the node and its alpha-th facility is greater than the current maximum, update it
        if (priority == instance.getAlpha() - 1 && instance.getDistance(facility, node) > this.objectiveFunction){
            this.objectiveFunction = instance.getDistance(facility, node);
        }
    }

    public Set<Integer> getFacilities() {
        return facilities;
    }
    
    public void printSolution(){
        System.out.println("Solution for instance: " + instance.getFilepath());
        System.out.println("No of nodes: " + instance.getnNodes() + ". No. of facilties: " + instance.getnFacilities() + ". No. of demand points: " + (instance.getnNodes() - instance.getnFacilities()));
        System.out.println("Alpha = " + this.instance.getAlpha());
        System.out.println("Value of the objective function: " + this.objectiveFunction);
        System.out.print("Facilities: ");
        for (int facility : this.facilities){
            System.out.print(facility + ", ");
        }
        System.out.print("\nAllocations:");
        for (int i = 0; i<instance.getnNodes(); i++){
            if (!this.facilities.contains(i)){
                System.out.print("\n" + i + "-> ");
                for (int j = 0; j<this.instance.getAlpha(); j++){
                    System.out.print(this.allocations.get(i).get(j) + ", ");
                }
            }
        }
    }

    public Object clone() throws CloneNotSupportedException{
        Solution cloned = (Solution) super.clone();
        cloned.instance = (Instance) this.instance.clone();
        cloned.facilities = new HashSet<>(this.facilities);
        cloned.allocations = new HashMap<>(this.allocations);
        return cloned;
    }

    public void swap(int facility1, int facility2){
       this.facilities.remove(facility1);
       this.facilities.add(facility2);
    }

    public Double getObjectiveFunction() {
        return objectiveFunction;
    }

    public void deleteAllocations(){
        this.allocations = new HashMap<>();
        this.objectiveFunction = Double.MIN_VALUE;
    }

    public void reset(){
        this.facilities = new HashSet<>();
        this.allocations = new HashMap<>();
        this.objectiveFunction = Double.MIN_VALUE;
    }
}
