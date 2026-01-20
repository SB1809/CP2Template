//Sophia Babayev/ 12/15/25 / Testing
public class BSTMain {
    public static void main(String[] args) {
        BST tree = new BST();
        tree.insert(10);
        tree.printTree();
        tree.insert(15);
        tree.printTree();
        tree.insert(5);
        tree.printTree();
        tree.insert(8);
        tree.printTree();
        tree.insert(20);
        tree.printTree();
        tree.insert(6);
        tree.printTree();
        tree.insert(13);
        tree.printTree();
        tree.insert(18);
        tree.printTree();
        tree.insert(200);
        tree.printTree();
        tree.insert(16);
        tree.printTree();
        tree.insert(1);
        tree.printTree();
        tree.remove(18);
        tree.printTree();
        tree.remove(8);
        tree.printTree();
        tree.remove(10);
        tree.printTree();
        
        System.out.println(tree.toString());

    }
}