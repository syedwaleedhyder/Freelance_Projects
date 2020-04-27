import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[]args) throws IOException {
        treeTable theTree = new treeTable();

        theTree.insert(new Student(201800003, "Ali Mohamed", "Doha", 3.8));
        theTree.insert(new Student(201800001, "Salih Jaber", "ALDafna", 3.5));
        theTree.insert(new Student(201800002, "Omar Salim", "AlRayyan", 3.9));
        theTree.insert(new Student(201800004, "Salih Jaber", "ALDafna", 3.5));
        theTree.insert(new Student(201800005, "Majed Abdulla", "Umm Selal", 4.0));

        theTree.insert(new Student(201700003, "Ali Mohamed", "Doha", 3.8));
        theTree.insert(new Student(201700002, "Salih Jaber", "ALDafna", 3.5));
        theTree.insert(new Student(201700001, "Omar Salim", "AlRayyan", 3.9));
        theTree.insert(new Student(201700004, "Salih Jaber", "ALDafna", 3.5));
        theTree.insert(new Student(201700005, "Majed Abdulla", "Umm Selal", 4.0));

        theTree.insert(new Student(201600003, "Ali Mohamed", "Doha", 3.8));
        theTree.insert(new Student(201600001, "Salih Jaber", "ALDafna", 3.5));
        theTree.insert(new Student(201600003, "Omar Salim", "AlRayyan", 3.9));
        theTree.insert(new Student(201600004, "Salih Jaber", "ALDafna", 3.5));
        theTree.insert(new Student(201600005, "Majed Abdulla", "Umm Selal", 4.0));

        /*Main Application*/

        Scanner reader = new Scanner(System.in);
        Scanner stringReader = new Scanner(System.in);
        FileHandler fh = new FileHandler();



        while (true){

            System.out.println("Select an option.");
            System.out.println("1- Add new student..");
            System.out.println("2- Search for a student.");
            System.out.println("3- Delete a student.");
            System.out.println("4- Display students' data.");
            System.out.println("5- Display students with less GPA.");
            System.out.println("6- Save to file.");
            System.out.println("7- Load data.");
            System.out.println("8- Display all data.");

            int selection = reader.nextInt();

            switch (selection){
                case 1:
                    System.out.print("Enter id: "); int id = reader.nextInt();
                    System.out.print("Enter name: "); String name = stringReader.nextLine();
                    System.out.print("Enter address: "); String address = stringReader.nextLine();
                    System.out.print("Enter GPA: "); double gpa = reader.nextDouble();
                    theTree.insert(new Student(id, name, address, gpa));
                    break;
                case 2:
                    System.out.print("Enter id to search: "); int search_id = reader.nextInt();
                    Student student = theTree.find(search_id);
                    System.out.println(student);
                    break;
                case 3:
                    System.out.print("Enter id to delete: "); int delete_id = reader.nextInt();
                    theTree.remove(delete_id);
                    break;
                case 4:
                    System.out.print("Enter year from 2001-2020: "); int year = reader.nextInt();
                    if(year < 2021 && year > 2001)
                        theTree.printStudent(year);
                    else
                        System.out.print("Year range from 2001-2020");
                    break;
                case 5:
                    System.out.print("Enter search GPA: "); double search_gpa = reader.nextDouble();
                    theTree.studentWithGPA(search_gpa);
                    break;
                case 6:
                    fh.writeFile(theTree);
                    break;
                case 7:
                    fh.readFile(theTree);
                    break;
                case 8:
                    theTree.printAll();
                    break;
                default:
                    System.out.print("Invalid option selected...");
            }
        }
    }
}
