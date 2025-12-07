import java.util.Arrays;

class Solution {
    int[][][][] memo;

    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        // Initialize memoization with sufficient size
        // n <= 28, max depth decreases n
        memo = new int[n + 2][n + 2][n + 2][];
        return dp(n, firstPlayer, secondPlayer);
    }

    private int[] dp(int n, int first, int second) {
        // Standardize: ensure first < second
        if (first > second) {
            return dp(n, second, first);
        }

        // Base Case: They compete this round
        if (first + second == n + 1) {
            return new int[]{1, 1};
        }

        // Check Memoization
        if (memo[n][first][second] != null) {
            return memo[n][first][second];
        }

        // Counters for "fixed" outcomes (no choice involved)
        int fixedLeft = 0; // Winners that definitely end up before 'first'
        int fixedGap = 0;  // Winners that definitely end up between 'first' and 'second'

        // Counters for "choice" outcomes
        // Type 1: Pair (Left, Between) -> Winner contributes to Left OR Gap
        int choice_Left_Gap = 0;
        // Type 2: Pair (Left, Outside) -> Winner contributes to Left OR Nothing
        int choice_Left_Outside = 0;
        // Type 3: Pair (Between, Outside) -> Winner contributes to Gap OR Nothing
        int choice_Gap_Outside = 0;

        // Iterate through all pairs in the current round
        for (int i = 1; i <= n / 2; i++) {
            int j = n - i + 1; // The opponent
            
            // Identify regions for i and j
            // Region 0: Before first
            // Region 1: first (The player itself)
            // Region 2: Between first and second
            // Region 3: second (The player itself)
            // Region 4: After second

            int regionI = getRegion(i, first, second);
            int regionJ = getRegion(j, first, second);

            // Logic to classify the pair
            if (regionI == 1 || regionJ == 1) {
                // One player is 'first'. 'first' MUST win.
                // Does NOT add to fixedLeft or fixedGap (it IS 'first')
                continue;
            }
            if (regionI == 3 || regionJ == 3) {
                // One player is 'second'. 'second' MUST win.
                // Does NOT add to fixedLeft or fixedGap (it IS 'second')
                continue;
            }

            // Case: Both strictly before first (Region 0 vs Region 0 is impossible as i < j, but Region 0 vs 0 logic applies if simplified)
            // Actually, simply map combinations:
            
            // Case A: Both inputs are from Left (0) -> Winner is Left
            if (regionI == 0 && regionJ == 0) fixedLeft++;
            
            // Case B: Both inputs are from Gap (2) -> Winner is Gap
            else if (regionI == 2 && regionJ == 2) fixedGap++;
            
            // Case C: Left (0) vs Gap (2) -> Choice
            else if ((regionI == 0 && regionJ == 2) || (regionI == 2 && regionJ == 0)) choice_Left_Gap++;
            
            // Case D: Left (0) vs Outside (4) -> Choice
            else if ((regionI == 0 && regionJ == 4) || (regionI == 4 && regionJ == 0)) choice_Left_Outside++;
            
            // Case E: Gap (2) vs Outside (4) -> Choice
            else if ((regionI == 2 && regionJ == 4) || (regionI == 4 && regionJ == 2)) choice_Gap_Outside++;
        }

        // Handle Middle Player (if n is odd)
        if (n % 2 == 1) {
            int mid = (n + 1) / 2;
            int r = getRegion(mid, first, second);
            if (r == 0) fixedLeft++;
            else if (r == 2) fixedGap++;
            // If r == 1 or 3 (targets) or 4 (outside), no count change
        }

        int minRounds = Integer.MAX_VALUE;
        int maxRounds = Integer.MIN_VALUE;
        int nextN = (n + 1) / 2;

        // Recursively iterate through all choice combinations
        // a: number of Left wins from (Left vs Gap)
        for (int a = 0; a <= choice_Left_Gap; a++) {
            // b: number of Left wins from (Left vs Outside)
            for (int b = 0; b <= choice_Left_Outside; b++) {
                // c: number of Gap wins from (Gap vs Outside)
                for (int c = 0; c <= choice_Gap_Outside; c++) {
                    
                    // Determine new indices for next round
                    
                    // How many people are before 'first'?
                    // fixedLeft + (Left wins from L_G) + (Left wins from L_O)
                    int nextLeftCount = fixedLeft + a + b;
                    
                    // How many people are between 'first' and 'second'?
                    // fixedGap + (Gap wins from L_G: remainder of a) + (Gap wins from G_O: c)
                    int gapFrom_L_G = choice_Left_Gap - a;
                    int nextGapCount = fixedGap + gapFrom_L_G + c;
                    
                    int nextFirst = nextLeftCount + 1;
                    int nextSecond = nextFirst + nextGapCount + 1;

                    int[] res = dp(nextN, nextFirst, nextSecond);
                    
                    minRounds = Math.min(minRounds, 1 + res[0]);
                    maxRounds = Math.max(maxRounds, 1 + res[1]);
                }
            }
        }

        return memo[n][first][second] = new int[]{minRounds, maxRounds};
    }

    private int getRegion(int idx, int first, int second) {
        if (idx < first) return 0;       // Left
        if (idx == first) return 1;      // First Target
        if (idx < second) return 2;      // Gap
        if (idx == second) return 3;     // Second Target
        return 4;                        // Outside
    }
}