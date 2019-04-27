import java.util.ArrayList;

public class treeTable {
    final Tree[] studentTrees = new Tree[20];

    public treeTable(){
        for(int i=0; i<20; i++) studentTrees[i] = new Tree();
    }

    private int calculateYear(int id){
        String first4char = String.valueOf(id).substring(0,4);
        int year = Integer.parseInt(first4char);
        return year;
    }
    private int hashFunction(int year){
        return year%20;
    }
    // This method inserts students in the appropriate tree based on his id.
    void insert(Student student){
        int year = calculateYear(student.getId());
        int index = hashFunction(year);

        studentTrees[index].insertIntoTree(student);
    }

    //. It receives an id, and returns student record with that id, or it returns null if it was not found.
    Student find (int id){
        int year = calculateYear(id);
        int index = hashFunction(year);
        TreeNode findNode = studentTrees[index].findInTree(id);
        if(findNode == null)
            return null;

        return findNode.getStudent();
    }

    // . It receives student’ id, removes his record if found and returns true, or returns false if it was not found.
    void remove(int id){
        int year = calculateYear(id);
        int index = hashFunction(year);
        studentTrees[index].deleteKey(id);
//        if(studentTrees[index].removeFromTree(id))
//            return true;
//        return false;
    }

    // it receives an integer number represents the year, then prints students’
    //data who were admitted in that year using inoder approach.
    void printStudent(int year){
        int index = hashFunction(year);
        studentTrees[index].inorderTraversal();
    }
    // this method prints all students in the treeTable using preorder to print the students
    //in each tree
    void printAll(){
        for(int index=0; index<20; index++){
            studentTrees[index].preorderTraversal();
        }
    }

    void studentWithGPA(double GPA){
        ArrayList<Student> studentsWithGPA = new ArrayList<>();
        for(int index=0; index<20; index++){
            studentsWithGPA.addAll(studentTrees[index].studentWithGPA(GPA));
        }
        for (Student student: studentsWithGPA) {
            System.out.println(student);
        }

    }

}
