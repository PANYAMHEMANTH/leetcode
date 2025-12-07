import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // Create a HashMap to store (Number, Index)
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // Check if the complement exists in the map
            if (map.containsKey(complement)) {
                // If found, return the index of the complement and the current index
                return new int[] { map.get(complement), i };
            }

            // If not found, add the current number and its index to the map
            map.put(nums[i], i);
        }

        // The problem guarantees a solution, so we don't strictly need a return here,
        // but Java requires it to compile.
        return new int[] {}; 
    }
}