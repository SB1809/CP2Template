//Sophia Babayev/ 12/15/25 / Testing
public class BSTMain {
    public static void main(String[] args) {
        BST tree = new BST();
        tree.insert(10);
        tree.insert(15);
        tree.insert(5);
        tree.insert(8);
        tree.printTree();
        System.out.println(tree.toString());

    }
}