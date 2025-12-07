class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] means: Does s[0...i-1] match p[0...j-1]?
        // Size is +1 to handle empty string cases.
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Base Case: Empty string matches empty pattern
        dp[0][0] = true;
        
        // Initialize Row 0: Patterns that can match empty string (e.g., "a*", "a*b*")
        // logic: if we see *, we look back 2 spots. If that was true, this is true.
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);
                
                if (pChar == '.' || pChar == sChar) {
                    // Case 1: Direct match or '.'
                    // Result depends on previous state (diagonal)
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pChar == '*') {
                    // Case 2: Wildcard '*'
                    // Subcase A: '*' represents 0 instances of preceding element.
                    // (Look back 2 columns, ignoring the char and the *)
                    dp[i][j] = dp[i][j - 2];
                    
                    // Subcase B: '*' represents 1 or more instances.
                    // We can only do this if the preceding char matches sChar (or is '.')
                    char precedingPChar = p.charAt(j - 2);
                    if (precedingPChar == '.' || precedingPChar == sChar) {
                        // Look at the row above (did we match s without this specific character?)
                        dp[i][j] = dp[i][j] | dp[i - 1][j];
                    }
                } else {
                    // Characters do not match
                    dp[i][j] = false;
                }
            }
        }
        
        return dp[m][n];
    }
}