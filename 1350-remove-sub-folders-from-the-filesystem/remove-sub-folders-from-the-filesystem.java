import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> removeSubfolders(String[] folder) {
        // Step 1: Sort the folders lexicographically
        Arrays.sort(folder);
        
        List<String> result = new ArrayList<>();
        
        // Step 2: Add the first folder (it's always a root because nothing comes before it)
        result.add(folder[0]);
        
        // Step 3: Iterate through the rest
        for (int i = 1; i < folder.length; i++) {
            // Get the last valid parent we added to the result
            String lastParent = result.get(result.size() - 1);
            
            // Check if current folder is a sub-folder of the lastParent.
            // We append "/" to ensure we are matching a directory boundary 
            // (e.g., preventing "/a" from matching "/ab")
            if (!folder[i].startsWith(lastParent + "/")) {
                result.add(folder[i]);
            }
        }
        
        return result;
    }
}