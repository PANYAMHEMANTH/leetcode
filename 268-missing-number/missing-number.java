class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        // Expected sum = 0 + 1 + 2 + ... + n = n(n+1)/2
        int expectedSum = n * (n + 1) / 2;
        
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
}