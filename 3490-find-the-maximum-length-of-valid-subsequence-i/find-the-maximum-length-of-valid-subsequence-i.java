class Solution {
    public int maximumLength(int[] nums) {
        int odd = 0;
        int even = 0;
        
        // Variables for alternating patterns
        int lenOddStart = 0;
        int wantOddStart = 1; // 1 means we are looking for an Odd number next
        
        int lenEvenStart = 0;
        int wantEvenStart = 0; // 0 means we are looking for an Even number next

        for (int num : nums) {
            int remainder = num % 2;

            // Pattern 1 & 2: Count pure evens and pure odds
            if (remainder == 0) {
                even++;
            } else {
                odd++;
            }

            // Pattern 3: Alternating starting with Odd (1, 0, 1, 0...)
            if (remainder == wantOddStart) {
                lenOddStart++;
                wantOddStart = 1 - wantOddStart; // Toggle expectation (1 -> 0 -> 1)
            }

            // Pattern 4: Alternating starting with Even (0, 1, 0, 1...)
            if (remainder == wantEvenStart) {
                lenEvenStart++;
                wantEvenStart = 1 - wantEvenStart; // Toggle expectation (0 -> 1 -> 0)
            }
        }

        // Return the maximum of all valid scenarios
        return Math.max(Math.max(odd, even), Math.max(lenOddStart, lenEvenStart));
    }
}