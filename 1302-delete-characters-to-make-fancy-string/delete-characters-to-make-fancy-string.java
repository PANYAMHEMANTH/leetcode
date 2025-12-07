class Solution {
    public String makeFancyString(String s) {
        // Use StringBuilder for efficient appending (O(1) vs O(N) for String concatenation)
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            int n = result.length();
            
            // Check if we have at least 2 characters in the buffer
            // AND if the last two characters are identical to the current one 'c'
            if (n >= 2 && result.charAt(n - 1) == c && result.charAt(n - 2) == c) {
                // Skip this character
                continue;
            }
            
            // Otherwise, add it to the result
            result.append(c);
        }
        
        return result.toString();
    }
}