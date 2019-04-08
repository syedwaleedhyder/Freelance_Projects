/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class MM_RSA_Encryption {
    BigInteger prime1_big, prime2_big, e, d, f, g, phiN, n;
    String path;

    public MM_RSA_Encryption(String path, int prime1, int prime2){

        this.prime1_big = BigInteger.valueOf(prime1);
        this.prime2_big = BigInteger.valueOf(prime2);

        System.out.printf("Prime number 1: %d \n",prime1);
        System.out.printf("Prime number 2: %d \n",prime2);

        this.prime1_big = BigInteger.valueOf(prime1);
        this.prime2_big = BigInteger.valueOf(prime2);
        this.n = prime1_big.multiply(prime2_big);
        this.phiN = (prime1_big.subtract(new BigInteger("1"))).multiply(prime2_big.subtract(new BigInteger("1")));
        this.path = path;
        KeyGeneration keyGeneration = new KeyGeneration(this);
        BigInteger[] key = keyGeneration.generator();
        this.f = key[0];
        this.g = key[1];
    }

    private FileReader read_file() throws FileNotFoundException{
        FileReader filereader = new FileReader(this.path);
        return filereader;
    }

    private FileWriter write_file() throws IOException{
        FileWriter filewriter = new FileWriter("encrypted_MM.txt");
        return filewriter;
    }

    public static boolean isPrime(int number) {
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public void encrypt() throws FileNotFoundException, IOException
    {
        System.out.printf( "f: %d\n", this.f);
        System.out.printf( "g: %d\n", this.g);
        FileReader myfile = read_file();
        FileWriter output = write_file();
        output.write(this.f.toString());
        output.write('\n');
        output.write(this.g.toString());
        output.write('\n');
        int character;
        while (-1 != (character = myfile.read())){
            BigInteger char_bigint = BigInteger.valueOf(character);
            // en = or**e % n
            // en = or**[(f-1)/2]%[(g+1)/2]
            BigInteger encrypted_char = char_bigint.modPow( ( (this.f).subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)), ((this.g).add(BigInteger.ONE)).divide(BigInteger.valueOf(2)));
            output.write(encrypted_char.toString());
            output.write('\n');
        }

        output.close();
        myfile.close();
        System.out.println("Encryption done!");

    }

    public static void main(String[]args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first prime number.");
        int input1 = scanner.nextInt();
        if(!isPrime(input1)){
            System.out.println("Number not prime. Exiting");
            return;
        }
        System.out.println("Enter second prime nuumber.");
        int input2 = scanner.nextInt();
        if(!isPrime(input2)){
            System.out.println("Second input not prime. Exiting.");
            return;
        }
        MM_RSA_Encryption me = new MM_RSA_Encryption("SampleTextFile_1000kb.txt", input1, input2);
        me.encrypt();
    }
}