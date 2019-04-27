import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    FileWriter fw;


    private void preorderTraversal(TreeNode treeNode) throws IOException {
        if (treeNode == null)
            return;

        /* first print the data of node */
        fw.write(String.valueOf(treeNode.getStudent().getId()));
        fw.write(System.lineSeparator());
        fw.write(treeNode.getStudent().getName());
        fw.write(System.lineSeparator());
        fw.write(treeNode.getStudent().getAddress());
        fw.write(System.lineSeparator());
        fw.write(String.valueOf(treeNode.getStudent().getGPA()));
        fw.write(System.lineSeparator());

        /* then recur on left subtree */
        preorderTraversal(treeNode.getLeftChild());

        /* now recur on right subtree */
        preorderTraversal(treeNode.getRightChild());

    }
    public void writeFile(treeTable treeTable) throws IOException {
        fw=new FileWriter("output.txt");
        for (Tree tree: treeTable.studentTrees) {
            preorderTraversal(tree.getRoot());
        }
        fw.close();
    }

    public String extractLine(FileReader fr) throws IOException{
        int character;
        String word = "";
        while(true){
            character = fr.read();
            if(character !=-1){
                if(character == (int)'\r'){
                    fr.read();
//                    System.out.print(word);
                    return word;
                }
                else
                    System.out.println(word);
                    word +=(char)character;
            }
            else return null;
        }
    }
    public void readFile(treeTable treeTable) throws IOException{
        // check if File exists or not
        FileReader fr=null;
        try
        {
            fr = new FileReader("output.txt");
        }
        catch (FileNotFoundException fe)
        {
            System.out.println("File not found");
        }

        while(true){
            String stringid = extractLine(fr);
            if(stringid == null)
                break;
            int id = Integer.valueOf(stringid);
            String name = extractLine(fr);
            String address = extractLine(fr);
            double gpa = Double.parseDouble(extractLine(fr));

            treeTable.insert(new Student(id, name, address, gpa));

        }
        System.out.println("Data Inserted. Printing inserted data.");
        treeTable.printAll();

    }
}
