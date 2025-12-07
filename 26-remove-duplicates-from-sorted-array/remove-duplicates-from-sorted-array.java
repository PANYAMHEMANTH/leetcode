class Solution {
    public int removeDuplicates(int[] nums) {
        // Edge case: Empty array check (though constraints say length >= 1)
        if (nums.length == 0) return 0;
        
        // 'k' is the index where the next unique element will be placed.
        // We start at 1 because the first element (index 0) is always unique.
        int k = 1; 
        
        for (int i = 1; i < nums.length; i++) {
            // Compare current element with the previous one
            if (nums[i] != nums[i - 1]) {
                nums[k] = nums[i]; // Overwrite the value at k
                k++; // Move the insert pointer forward
            }
        }
        
        return k; // k represents the count of unique elements
    }
}