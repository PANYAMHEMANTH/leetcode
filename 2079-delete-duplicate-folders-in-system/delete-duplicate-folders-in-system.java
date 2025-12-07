import java.util.*;

class Solution {
    // 1. Define the Trie Node Structure
    class TrieNode {
        String name;
        Map<String, TrieNode> children;
        boolean isDeleted;
        
        TrieNode(String name) {
            this.name = name;
            this.children = new HashMap<>();
            this.isDeleted = false;
        }
    }

    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        // Step 1: Build the Trie
        TrieNode root = new TrieNode("/");
        for (List<String> path : paths) {
            TrieNode current = root;
            for (String folder : path) {
                current.children.putIfAbsent(folder, new TrieNode(folder));
                current = current.children.get(folder);
            }
        }

        // Step 2 & 3: Serialize and Mark Duplicates
        // Map to store: SerializedString -> List of Nodes with that structure
        Map<String, List<TrieNode>> structureMap = new HashMap<>();
        
        serializeSubtrees(root, structureMap);

        // Mark nodes for deletion
        for (List<TrieNode> nodes : structureMap.values()) {
            // The problem says: "Two folders... identical if they contain the same non-empty set of identical subfolders"
            // So if more than 1 node has the same structure (and that structure is not empty), delete them.
            if (nodes.size() > 1) {
                for (TrieNode node : nodes) {
                    node.isDeleted = true;
                }
            }
        }

        // Step 4: Collect remaining paths
        List<List<String>> result = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        
        // We iterate children of root specifically to avoid adding empty root path if not needed
        for (TrieNode child : root.children.values()) {
            getPaths(child, currentPath, result);
        }

        return result;
    }

    // Helper: Post-order traversal (Bottom-up) to generate signatures
    private String serializeSubtrees(TrieNode node, Map<String, List<TrieNode>> map) {
        // Base case: Leaf node
        if (node.children.isEmpty()) {
            return ""; 
        }

        // To ensure unique signature regardless of insertion order, sort children by name
        // Use a list to sort keys
        List<String> sortedKeys = new ArrayList<>(node.children.keySet());
        Collections.sort(sortedKeys);

        StringBuilder sb = new StringBuilder();
        
        for (String key : sortedKeys) {
            TrieNode child = node.children.get(key);
            String childSignature = serializeSubtrees(child, map);
            
            // Structure: Name + (SubtreeSignature)
            // Parentheses are crucial to separate levels
            sb.append(child.name)
              .append("(")
              .append(childSignature)
              .append(")");
        }

        String signature = sb.toString();
        
        // Only track non-empty subfolders sets
        if (signature.length() > 0) {
            map.putIfAbsent(signature, new ArrayList<>());
            map.get(signature).add(node);
        }

        return signature;
    }

    // Helper: DFS to reconstruct paths
    private void getPaths(TrieNode node, List<String> path, List<List<String>> result) {
        if (node.isDeleted) return;

        path.add(node.name);
        // Add a copy of current valid path to results
        result.add(new ArrayList<>(path));

        for (TrieNode child : node.children.values()) {
            getPaths(child, path, result);
        }

        // Backtrack
        path.remove(path.size() - 1);
    }
}