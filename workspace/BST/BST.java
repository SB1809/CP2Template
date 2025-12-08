public class BST {
    private Node root;

    public BST() {
        root = null;
    }

    public void insert(int key) {
        // If tree is empty, create root
        if (root == null) {
            root = new Node(key);
            return;
        }

        // Traverse tree to find insertion point
        Node curr = root;
        while (true) {
            if (key < curr.key) {
                // Go left
                if (curr.left == null) {
                    curr.left = new Node(key);
                    return;
                }
                curr = curr.left;
            } else if (key > curr.key) {
                // Go right
                if (curr.right == null) {
                    curr.right = new Node(key);
                    return;
                }
                curr = curr.right;
            } else {
                // Duplicate - don't insert
                return;
            }
        }
    }

    public boolean search(int key) {
        Node curr = root;
        while (curr != null) {
            if (key == curr.key) {
                return true;
            } else if (key < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    public boolean remove(int key) {
        // Find parent and node to remove
        Node parent = null;
        Node curr = root;

        while (curr != null && curr.key != key) {
            parent = curr;
            if (key < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        if (curr == null) {
            return false; // Key not found
        }

        // Case 1: Node has no children
        if (curr.left == null && curr.right == null) {
            if (parent == null) {
                root = null; // Removing root
            } else if (parent.left == curr) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        // Case 2: Node has right child only
        else if (curr.left == null) {
            if (parent == null) {
                root = curr.right;
            } else if (parent.left == curr) {
                parent.left = curr.right;
            } else {
                parent.right = curr.right;
            }
        }
        // Case 3: Node has left child only
        else if (curr.right == null) {
            if (parent == null) {
                root = curr.left;
            } else if (parent.left == curr) {
                parent.left = curr.left;
            } else {
                parent.right = curr.left;
            }
        }
        // Case 4: Node has two children
        else {
            // Find inorder successor (smallest in right subtree)
            Node successorParent = curr;
            Node successor = curr.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            // Replace current node's key with successor's key
            curr.key = successor.key;

            // Remove successor node (it has at most one right child)
            if (successorParent == curr) {
                curr.right = successor.right;
            } else {
                successorParent.left = successor.right;
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node != null) {
            inOrder(node.left, sb);
            sb.append(node.key).append(" ");
            inOrder(node.right, sb);
        }
    }

}

//     public static void showTrunks(Trunk p)
//     {
//         if (p == null) {
//             return;
//         }
 
//         showTrunks(p.prev);
//         System.out.print(p.str);
//     }
 
//     public void printTree(){
//         printTree(root, null, false);
//     }

//     private void printTree(Node root, Trunk prev, boolean isLeft)
//     {
//         if (root == null) {
//             return;
//         }
 
//         String prev_str = "    ";
//         Trunk trunk = new Trunk(prev, prev_str);
 
//         printTree(root.right, trunk, true);
 
//         if (prev == null) {
//             trunk.str = "———";
//         }
//         else if (isLeft) {
//             trunk.str = ".———";
//             prev_str = "   |";
//         }
//         else {
//             trunk.str = "`———";
//             prev.str = prev_str;
//         }
 
//         showTrunks(trunk);
//         System.out.println(" " + root.data);
 
//         if (prev != null) {
//             prev.str = prev_str;
//         }
//         trunk.str = "   |";
 
//         printTree(root.left, trunk, false);
//     }

// //this goes into it's own file
//     
//    };

