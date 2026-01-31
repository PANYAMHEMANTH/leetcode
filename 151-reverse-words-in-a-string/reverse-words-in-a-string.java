import java.util.*;

class Solution {
    public String reverseWords(String s) {
        // Split by whitespace, filter out empty strings, reverse, join with space
        String[] words = s.trim().split("\\s+");
        
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            if (i < words.length - 1) {
                result.append(" ");
            }
            result.append(words[i]);
        }
        
        return result.toString();
    }
}