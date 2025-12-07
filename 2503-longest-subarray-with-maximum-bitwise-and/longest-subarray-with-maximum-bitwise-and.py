class Solution:
    def longestSubarray(self, nums: List[int]) -> int:
        max_val = 0
        longest = 0
        current_streak = 0
        
        for num in nums:
            if num > max_val:
                # Found a new global maximum
                max_val = num
                longest = 1
                current_streak = 1
            elif num == max_val:
                # Continue the streak of the current maximum
                current_streak += 1
                longest = max(longest, current_streak)
            else:
                # Streak broken
                current_streak = 0
                
        return longest