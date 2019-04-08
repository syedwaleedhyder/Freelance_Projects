/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;
import java.io.*;

public class FileComparison {
    FileReader file1,file2;
    String path1,path2;
    float file1count,file2count;

    public FileComparison(String file1Path, String file2Path) throws FileNotFoundException, IOException {
        this.path1 = file1Path;
        this.path2 = file2Path;
        file1count = character_counter(path1);
        file2count = character_counter(path2);
        file1 = read_file(path1);
        file2 = read_file(path2);
    }
    
    private FileReader read_file(String path) throws FileNotFoundException{
         FileReader filereader = new FileReader(path);
         return filereader;
    }
    
    private float character_counter(String path) throws FileNotFoundException, IOException{
        FileReader file = new FileReader (path);
        String character = next_word(file);
        float counter=0;
        while (!"EOFmarkerReached".equals(character)){
            counter++;
            character = next_word(file);
        }
        file.close();
        return counter;
    }
    
    private static String next_word(FileReader myfile) throws IOException {
        int character;
        String word ="";
        while (true){
            character= myfile.read();
            if (-1 != character) {
                if (!(character == (int)' ' | character == (int)'\n' | character == (int)',' | character == (int)'.')) {
                    word += (char)character;
                }
                else
                    break;
            }
            else
                return ("EOFmarkerReached");
        }
        return word;
    }
    
    public void CompareFiles() throws IOException {
        int similarCharacters = 0;
        String word1,word2;
        while (true) {
            word1 = next_word(file1);
            word2 = next_word(file2);
            if (word1 != "EOFmarkerReached" & word2 !="EOFmarkerReached"){
                if (word1.equals(word2)){
                    similarCharacters++;
                }
            }
            else
                break;
        }
        if (file1count == file2count){
            System.out.printf("Word Count in file 1: %d \nWord Count in file 2: %d \n", (int)file1count,(int)file2count);
            System.out.printf("Similar Words in both files: %d \n",similarCharacters);
            System.out.printf("Files are %f%% similar \n", (similarCharacters/file1count)*100);
        }
        else if (file1count > file2count){
            System.out.printf("Word Count in file 1: %d \nWord Count in file 2: %d \n", (int)file1count,(int)file2count);
            System.out.printf("Similar Words in both files: %d \n",similarCharacters);
            System.out.printf("Files are %f%% similar \n", (similarCharacters/file1count)*100);
        }
        else{
            System.out.printf("Word Count in file 1: %d \nWord Count in file 2: %d \n", (int)file1count,(int)file2count);
            System.out.printf("Similar Words in both files: %d \n",similarCharacters);
            System.out.printf("Files are %f%% similar \n", (similarCharacters/file2count)*100);
        }
    }
    
    public static void main(String[]args) throws IOException {

        FileComparison f = new FileComparison ("decrypted_M.txt","SampleTextFile_1000kb.txt");
        FileComparison f1 = new FileComparison ("SampleTextFile_1000kb.txt","decrypted.txt");
        FileComparison f2 = new FileComparison ("encrypted.txt", "encrypted_M.txt");

        System.out.println("Original file and decrypted file(modified)");
        f.CompareFiles();
        System.out.println("\nOriginal file amd decrypted file(normal)");
        f1.CompareFiles();
        System.out.println("\nEncrypted file(normal) and encrypted file(modified)");
        f2.CompareFiles();
    }
}