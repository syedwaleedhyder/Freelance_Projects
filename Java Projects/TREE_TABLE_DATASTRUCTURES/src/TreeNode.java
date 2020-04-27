public class TreeNode {


    private Student student;
    private TreeNode leftChild;         // this node's left child
    private TreeNode rightChild;        // this node's right child

    public TreeNode(Student student){
        this.student = student;
    }

    // display the student data stored in the node.
    @Override
    public String toString() {
        return  student.toString();
    }
    // display the student data stored in the node.
    public void displayNode() {
        System.out.println(this);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

}
