import java.util.HashSet;
import java.util.Set;

class Solution {
    public int maxSum(int[] nums) {
        int maxElement = Integer.MIN_VALUE;
        
        // Step 1: Find the maximum element in the array
        for (int num : nums) {
            if (num > maxElement) {
                maxElement = num;
            }
        }
        
        // Hint 1: If the maximum element is negative, return it.
        // This handles cases like [-5, -2, -9] -> answer is -2
        if (maxElement < 0) {
            return maxElement;
        }
        
        // Hint 2: Otherwise, sum all UNIQUE positive numbers.
        Set<Integer> seen = new HashSet<>();
        int sum = 0;
        
        for (int num : nums) {
            // We only care about positive numbers.
            // If we have seen the number before, we skip it (must be unique).
            if (num > 0 && !seen.contains(num)) {
                sum += num;
                seen.add(num);
            }
        }
        
        return sum;
    }
}