import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    class NestedList {
        List<Object> intValues;
    }

    public int calculateDepth(NestedList nestedList){
        return calculateWithDepth(1,nestedList);
    }

    private int calculateWithDepth(final int depth,NestedList nestedList){
        List<Integer> intList = new ArrayList<>();

        intList = nestedList.intValues.stream().filter(value->value instanceof Integer).
                map(value->(Integer)value).collect(Collectors.toList());
        nestedList.intValues.removeAll(intList);
        int sum = intList.stream().mapToInt(value->value*depth).sum();
        if(nestedList.intValues!=null) {
            for (int i = 0; i < nestedList.intValues.size(); i++) {
                sum=sum+calculateWithDepth(depth + 1, (NestedList)nestedList.intValues.get(i));
                return sum;
            }
        }
        return sum;
    }
}