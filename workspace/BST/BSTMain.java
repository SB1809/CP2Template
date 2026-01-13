//Sophia Babayev/ 12/15/25 / Testing
public class BSTMain {
    public static void main(String[] args) {
        BST tree = new BST();
        tree.insert(10);
        tree.insert(15);
        tree.insert(5);
        tree.insert(8);
        tree.insert(20);
        tree.insert(6);
        tree.insert(13);


        tree.printTree();
        tree.rotateRight(tree.root.left.right, tree.root.left);

        tree.printTree();

            tree.rotateLeft(tree.root.left, tree.root);

        tree.printTree();
        System.out.println(tree.toString());

    }
}