class Solution {
    public boolean isPalindrome(int x) {
        // Edge Case 1: Negative numbers are never palindromes (e.g., -121 != 121-)
        // Edge Case 2: Numbers ending in 0 are not palindromes unless the number is just 0 
        // (because a number cannot start with 0)
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversedHalf = 0;

        // Keep reversing digits until the reversed part is greater than or equal to the remaining x
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }

        // If length is even: x should equal reversedHalf (e.g., 1221 -> x=12, reversed=12)
        // If length is odd: x should equal reversedHalf / 10 (e.g., 12321 -> x=12, reversed=123. Remove 3)
        return x == reversedHalf || x == reversedHalf / 10;
    }
}