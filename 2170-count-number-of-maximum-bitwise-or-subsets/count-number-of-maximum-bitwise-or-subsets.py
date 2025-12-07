class Solution:
    def countMaxOrSubsets(self, nums: List[int]) -> int:
        # Step 1: Calculate the maximum possible Bitwise OR
        # This is done by OR-ing all elements together.
        max_or = 0
        for num in nums:
            max_or |= num
        
        # Step 2: Use DFS (Backtracking) to explore all subsets
        def backtrack(index, current_or):
            # Base Case: If we've considered all numbers
            if index == len(nums):
                # Return 1 if this subset matches the max_or, else 0
                return 1 if current_or == max_or else 0
            
            # Recursive Step:
            
            # Choice 1: Include nums[index] in the subset
            include_count = backtrack(index + 1, current_or | nums[index])
            
            # Choice 2: Exclude nums[index] from the subset
            exclude_count = backtrack(index + 1, current_or)
            
            return include_count + exclude_count

        # Start recursion from index 0 with an initial OR value of 0
        return backtrack(0, 0)