//Sophia Babayev / 12/10/2025 / Creates a tree that works.
import java.util.ArrayList;

public class BST {
    private Node root;

    public BST() {
        root = null;
    }

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
            curr = null;
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
            successorParent.left = null;

            return true;
        }

    }

       
    

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