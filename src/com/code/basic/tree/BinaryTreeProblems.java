package com.code.basic.tree;

import java.util.*;

public class BinaryTreeProblems {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // Problem 1: Level Order Traversal
    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                currentLevel.add(current.val);
                
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    // Problem 2: Validate BST
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private static boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        
        if (node.val <= min || node.val >= max) return false;
        
        return isValidBST(node.left, min, node.val) && 
               isValidBST(node.right, node.val, max);
    }
    
    public static void main(String[] args) {
        // Create a sample binary tree
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);
        
        // Test Level Order Traversal
        System.out.println("Level Order Traversal:");
        List<List<Integer>> levels = levelOrderTraversal(root);
        for (List<Integer> level : levels) {
            System.out.println(level);
        }
        
        // Test BST Validation
        System.out.println("\nIs Valid BST: " + isValidBST(root));
        
        // Create an invalid BST
        TreeNode invalidRoot = new TreeNode(5);
        invalidRoot.left = new TreeNode(4);
        invalidRoot.right = new TreeNode(6);
        invalidRoot.right.left = new TreeNode(3); // This makes it invalid
        
        System.out.println("Is Invalid BST Valid: " + isValidBST(invalidRoot));
    }
}
