import java.util.ArrayList;

public class Tree {

    private TreeNode root;

    public Tree(){
        root = null;
    }

    public Tree(TreeNode root){
        this.root = root;
    }
    public TreeNode getRoot() {
        return root;
    }

    private TreeNode insertRecursive(TreeNode current, TreeNode newNode) {
        if (current == null) {
            return newNode;
        }

        if(newNode.getStudent().getId() < current.getStudent().getId()){
            current.setLeftChild(insertRecursive(current.getLeftChild(), newNode));
        }
        else if(newNode.getStudent().getId() > current.getStudent().getId()){
            current.setRightChild(insertRecursive(current.getRightChild(), newNode));
        }
        else{
            // value already exists
            return current;
        }
        return current;
    }

    public void insertIntoTree(Student student) {
        TreeNode newNode = new TreeNode(student);
        root = insertRecursive(root, newNode);
    }

    private TreeNode findNodeRecursive(TreeNode current, int id) {
        if (current == null) {
            return null;
        }
        if (id == current.getStudent().getId()) {
            return current;
        }
        return id < current.getStudent().getId()
                ? findNodeRecursive(current.getLeftChild(), id)
                : findNodeRecursive(current.getRightChild(), id);
    }
    public TreeNode findInTree(int id) {
        TreeNode treeNode = findNodeRecursive(root, id);
        return treeNode;
    }

    private TreeNode findSmallestValue(TreeNode root) {
        return root.getLeftChild() == null ? root : findSmallestValue(root.getLeftChild());
    }
    // This method mainly calls deleteRec()
    void deleteKey(int key)
    {
        root = deleteRec(root, key);
    }
    /* A recursive function to insert a new key in BST */
    TreeNode deleteRec(TreeNode root, int id)
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;

        /* Otherwise, recur down the tree */
        if (id < root.getStudent().getId())
            root.setLeftChild(deleteRec(root.getLeftChild(), id));
        else if (id > root.getStudent().getId())
            root.setRightChild(deleteRec(root.getRightChild(), id));
            // if key is same as root's key, then This is the node
            // to be deleted
        else
        {
            // node with only one child or no child
            if (root.getLeftChild() == null)
                return root.getRightChild();
            else if (root.getRightChild() == null)
                return root.getLeftChild();

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root = findSmallestValue(root.getRightChild());

            // Delete the inorder successor
            root.setRightChild(deleteRec(root.getRightChild(), root.getStudent().getId()));
        }

        return root;
    }

    private void inorderTraversal(TreeNode treeNode) {
        if (treeNode == null)
            return;

        /* first recur on left child */
        inorderTraversal(treeNode.getLeftChild());

        /* then print the data of node */
        System.out.println(treeNode.getStudent());

        /* now recur on right child */
        inorderTraversal(treeNode.getRightChild());
    }
    //Wrapper
    public void inorderTraversal(){
        inorderTraversal(root);
    }


    private void preorderTraversal(TreeNode treeNode) {
        if (treeNode == null)
            return;

        /* first print the data of node */
        System.out.println(treeNode.getStudent());

        /* then recur on left subtree */
        preorderTraversal(treeNode.getLeftChild());

        /* now recur on right subtree */
        preorderTraversal(treeNode.getRightChild());

    }
    public void preorderTraversal() {
        preorderTraversal(root);
    }

    public ArrayList<Student> studentWithGPA(double gpa) {
        ArrayList<Student> studentswithGPA = new ArrayList<>();
        studentsWithGPA(root, studentswithGPA, gpa);
        return studentswithGPA;
    }

    private void studentsWithGPA(TreeNode current, ArrayList<Student> studentsWithGPA, double gpa) {
        if (current == null)
            return;

        /* first recur on left child */
        studentsWithGPA(current.getLeftChild(), studentsWithGPA, gpa);

        /* then print the data of node */
        if(current.getStudent().getGPA()<gpa)
            studentsWithGPA.add(current.getStudent());

        /* now recur on right child */
        studentsWithGPA(current.getRightChild(), studentsWithGPA, gpa);
    }
}
