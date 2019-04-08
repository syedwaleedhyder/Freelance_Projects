/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Encryption {
    BigInteger e, n, d, phiN;
    String path;

    public Encryption(int p, int q, String path) {
        this.path = path;
        System.out.printf("prime number 1: %d \n", p);
        System.out.printf("prime number 2: %d \n", q);
        BigInteger prime1 = BigInteger.valueOf(p);
        BigInteger prime2 = BigInteger.valueOf(q);
        this.n = prime1.multiply(prime2);
        phiN = (prime1.subtract(new BigInteger("1"))).multiply(prime2.subtract(new BigInteger("1")));
        System.out.printf("n value: %d \n", n);
        System.out.printf("phiN value: %d \n", phiN);

        boolean eAnddFound = false;

        for (int e = (phiN.intValue() / 2); e > 0; e--) {
            if (phiN.mod(BigInteger.valueOf(e)) != BigInteger.valueOf(0) & n.mod(BigInteger.valueOf(e)) != BigInteger.valueOf(0) & isPrime(e))
                for (int d = e + 1; d < phiN.intValue(); d++) {
                    if ((phiN.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (n.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (isPrime(d))) {
                        boolean condition = ((BigInteger.valueOf(d).multiply(BigInteger.valueOf(e))).mod(phiN)).equals(BigInteger.valueOf(1));
                        if (condition) {
                            //candidateEDvalues.add(new valueED(e,d));
                            System.out.println((BigInteger.valueOf(d).multiply(BigInteger.valueOf(e))).mod(phiN));
                            this.e = new BigInteger(String.valueOf(e));
                            this.d = new BigInteger(String.valueOf(d));
                            System.out.printf("e value: %d \n", this.e);
                            System.out.printf("d value: %d \n", this.d);
                            eAnddFound = true;
                            System.out.println("e and d calculated.");
                            break;
                        }
                    }
                }

            if (eAnddFound == true)
                break;
        }
    }

    private FileReader read_file() throws FileNotFoundException {
        FileReader filereader = new FileReader(this.path);
        return filereader;
    }

    private FileWriter write_file() throws IOException {
        FileWriter filewriter = new FileWriter("encrypted.txt");
        return filewriter;
    }

    public void encrypt() throws FileNotFoundException, IOException {
        FileReader myfile = read_file();
        FileWriter output = write_file();
        output.write(this.e.toString());
        output.write("\n");
        output.write(this.n.toString());
        output.write("\n");
        int character;
        while (-1 != (character = myfile.read())) {
            BigInteger char_bigint = BigInteger.valueOf(character);
            BigInteger encrypted_char = char_bigint.modPow(this.e, this.n);
            output.write(encrypted_char.toString());
            output.write('\n');
        }

        output.close();
        myfile.close();
        System.out.println("File encrypted successfully!");

    }

    public static boolean isPrime(int number) {
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter 1st prime value: ");
        int p1 = reader.nextInt();
        System.out.println("Please enter 2nd prime value: ");
        int p2 = reader.nextInt();

        Encryption e = new Encryption(p1, p2, "SampleTextFile_1000kb.txt");
        e.encrypt();
    }
}
