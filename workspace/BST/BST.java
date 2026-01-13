//Sophia Babayev / 12/10/2025 / Creates a tree that works.
import java.util.ArrayList;

public class BST {
     Node root;

    // Pre: None
    // Post: Initializes an empty BST; root will be null.
    public BST() {
        root = null;
    }

    // Pre: key is an integer value to insert.
    // Post: If key was not already present, a new node with key is inserted preserving BST ordering. If key is duplicate, tree is unchanged.
    public void insert(int key) {
        // If tree is empty I create root
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
                // Duplicate so I don't insert
                return;
            }
        }
    }

    // Pre: key is the value to search for.
    // Post: Returns true if a node with key exists in the tree; otherwise false.
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

    // Pre: key is the value to remove from the tree.
    // Post: If key exists in the tree it is removed and the method returns true. If key does not exist the tree is unchanged and the method returns false.
    public boolean remove(int key) {
        
        Node curr = root;
        Node parent = null;

        if(search(key) == false ){
            return false;
        }

        while (curr != null && curr.key != key) {
            parent = curr;
            if (key < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // no children
        if (curr.left == null && curr.right == null) {
            if (parent.right == curr){
                parent.right = null;
            }else {
                parent.left = null;
            }
            
            return true;
        }


        // one child
        else if (curr.left == null || curr.right == null) {
            Node child = (curr.left != null) ? curr.left : curr.right;
            if (parent.left == curr) {
                parent.left = child;
            } else {
                parent.right = child;
            }
             return true;
        }

        //two children
        //another two nodes - find the parent of the node that's all the way left of the right child
        //replace yourself with that node's left child
        //remove that node (parent.left =  parent.left.right)
        else {
            // Find successor 
            Node successorParent = curr;
            Node successor = curr.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }


            // Replace current node's key with successor's key
            curr.key = successor.key;


            // Remove successor node 
            //if we never moved off of curr and curr == successorParent
            if(curr == successorParent){
                successorParent.right = successor.right;
            }
            else{
                //if we needed to move further down the tree to find successor
                successorParent.left = successor.right;
            }
            return true;
        }

    }

       
    

    // Pre: Nne
    // Post: Returns a string representation of the tree (keys by depth/level).
    public String toString() {

        String treeString = "";

        ArrayList<ArrayList<Node>> leNodes = new ArrayList<ArrayList<Node>>();
        //in order traversal (left, root, right)
        leNodes = inOrder(root, 0, leNodes);

        for (int i = 0; i < leNodes.size(); i++) {
            for (int k = 0; k < leNodes.get(i).size(); k++) {
                treeString = treeString + leNodes.get(i).get(k).key + ", ";
            }
            treeString += "\n";
        }

        return treeString;
    }

    // Pre: leNodes is a (possibly empty) ArrayList used to collect nodes by depth. curr may be null to indicate an empty subtree.
    // Post: Returns leNodes populated with nodes visited in-order grouped by depth.
    private ArrayList<ArrayList<Node>> inOrder(Node curr, int depth, ArrayList<ArrayList<Node>> leNodes){
        if (curr == null ){
            return leNodes;
        }
        //left subtree
        inOrder(curr.left, depth + 1, leNodes);

        while (leNodes.size() <= depth) {
            leNodes.add(new ArrayList<Node>());
        }

        leNodes.get(depth).add(curr);

        //right subtree
        inOrder(curr.right, depth + 1, leNodes);

        return leNodes;
    }





    // rotates the tree such that the subRoot is replaced with it's right child with subRoot becoming the left child of the new subRoot. prev now points to the new subRoot.
    // Pre: subRoot is a node in the tree and prev is its parent (or null if subRoot is root). subRoot.right must be non-null to perform a meaningful left rotation.
    // Post: Performs a left rotation at subRoot. Tree links are updated so that subRoot becomes the left child of its former right child.
    private void rotateLeft(Node subRoot, Node prev){
        if (subRoot == null || subRoot.right == null) {
            return;
        }

        Node newRoot = subRoot.right;

        if (prev == null) {
            root = newRoot;
        } else if (prev.right == subRoot) {
            prev.right = newRoot;
        } else {
            prev.left = newRoot;
        }

        subRoot.right = newRoot.left;
        newRoot.left = subRoot;

    }

 

    // rotates the tree such that the subRoot is replaced with it's left child with subRoot becoming the right child of the new subRoot. prev now points to the new subRoot.
    // Pre: subRoot is a node in the tree and prev is its parent (or null if subRoot is root).subRoot.left must be non-null to perform a meaningful right rotation.
    // Post: Performs a right rotation at subRoot. Tree links are updated so that subRoot becomes the right child of its former left child.
    private void rotateRight(Node subRoot, Node prev){
        if (subRoot == null || subRoot.left == null) {
            return;
        }

        Node newRoot = subRoot.left;

        if (prev == null) {
            root = newRoot;
        } else if (prev.left == subRoot) {
            prev.left = newRoot;
        } else {
            prev.right = newRoot;
        }

        subRoot.left = newRoot.right;
        newRoot.right = subRoot;

    }




    // Pre: node may be null (representing an empty subtree).
    // Post: Returns the height (max distance to a leaf) of the given node. If node is null, conventionally returns 0 (or - depending on implementation). returns the height of the node
    private int height(Node node){
        Node current = node;
        if (current == null) {
            return -1;
        }
        int leftHeight = height(current.left);
        int rightHeight = height(current.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Pre: node may be null.
    // Post: Returns the balance factor of node (height(left) - height(right)). If node is null, the balance is conventionally 0. returns the balance at the specified node
    private int balance(Node node){
        if (node == null) {
            return 0;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return leftHeight - rightHeight;
    }






 public boolean isBSTOrNot() {
        return isBSTOrNot(this.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTOrNot(Node root, int minValue, int maxValue) {
        // check for root is not null or not
        if (root == null) {
            return true;
        }
        // check for current node value with left node value and right node value and recursively check for left sub tree and right sub tree
        if(root.key >= minValue && root.key <= maxValue && isBSTOrNot(root.left, minValue, root.key) && isBSTOrNot(root.right, root.key, maxValue)){
            return true;
        }
        return false;
    }

 

   // please use the following pieces of code to display your tree in a more easy to follow style (Note* you'll need to place the Trunk class in it's own file)
    public static void showTrunks(Trunk p)
    {
        if (p == null) {
            return;
        }
 
        showTrunks(p.prev);
        System.out.print(p.str);
    }
 
    public void printTree(){
        printTree(root, null, false);
    }

    private void printTree(Node root, Trunk prev, boolean isLeft)
    {
        if (root == null) {
            return;
        }
 
        String prev_str = "    ";
        Trunk trunk = new Trunk(prev, prev_str);
 
        printTree(root.right, trunk, true);
 
        if (prev == null) {
            trunk.str = "———";
        }
        else if (isLeft) {
            trunk.str = ".———";
            prev_str = "   |";
        }
        else {
            trunk.str = "`———";
            prev.str = prev_str;
        }
 
        showTrunks(trunk);
        System.out.println(" " + root.key);
 
        if (prev != null) {
            prev.str = prev_str;
        }
        trunk.str = "   |";
 
        printTree(root.left, trunk, false);
    }

//this goes into it's own file
    class Trunk
   {
    Trunk prev;
    String str;
 
    Trunk(Trunk prev, String str)
    {
        this.prev = prev;
        this.str = str;
    }
   };

}