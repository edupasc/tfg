package Models;

import java.util.Comparator;
import java.util.Map;

public class CandidatesComparator implements Comparator<Map.Entry<Integer, Double>> {
    @Override
    public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
        return (int) (o1.getValue() - o2.getValue());
    }
}
