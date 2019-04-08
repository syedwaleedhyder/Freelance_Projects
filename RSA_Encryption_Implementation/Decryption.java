/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.*;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Scanner;

import static newpackage.Encryption.isPrime;

/**
 * @author obaj9
 */
public class Decryption {
    BigInteger e, d, n;
    String path;
    BufferedReader encrypted_file;

    public Decryption(String path) throws FileNotFoundException, IOException {
        this.path = path;
        encrypted_file = read_file();
        this.e = new BigInteger(encrypted_file.readLine());
        this.n = new BigInteger(encrypted_file.readLine());
        this.d = calculate_d(this.e, this.n);
    }

    private BigInteger calculate_d(BigInteger e, BigInteger n) {
        BigInteger phiN = new BigInteger("1");
        long p1 = (long) Math.ceil(Math.sqrt(n.longValue()));
        long long_n = n.longValue();
        while (p1 > 0) {
            if (long_n % p1 == 0) {
                long p2 = long_n / p1;
                if (isPrime((int) p1) & isPrime(((int) p2))) {
                    phiN = (BigInteger.valueOf(p1 - 1)).multiply(BigInteger.valueOf(p2 - 1));
                    System.out.printf("Prime1: %d\n", p1);
                    System.out.printf("Prime2: %d\n", p2);
                }
            }
            p1--;
        }

        for (int d = e.intValue() + 1; d < n.intValue(); d++) {

            if ((n.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (n.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (isPrime(d))) {
                boolean condition = ((BigInteger.valueOf(d).multiply(e)).mod(phiN)).equals(BigInteger.valueOf(1));
                if (condition) {
                    System.out.println((BigInteger.valueOf(d).multiply(e)).mod(phiN));
                    this.d = new BigInteger(String.valueOf(d));
                    System.out.printf("e value: %d \n", this.e);
                    System.out.printf("d value: %d \n", this.d);
                    return BigInteger.valueOf(d);
                }
            }
        }
        return null;
    }

    private BufferedReader read_file() throws FileNotFoundException {
        FileReader filereader = new FileReader(this.path);
        BufferedReader bf = new BufferedReader(filereader);
        return bf;
    }

    private FileWriter write_file() throws IOException {
        FileWriter filewriter = new FileWriter("decrypted.txt");
        return filewriter;
    }


    public void decrypt() throws FileNotFoundException, IOException {
        if (this.d == null) {
            System.out.println("d cannot be calculated.");
            return;
        }
        FileWriter output = write_file();
        String character;
        while (null != (character = encrypted_file.readLine())) {
            BigInteger char_bigint = new BigInteger(character);
            BigInteger decrypted_char = char_bigint.modPow(this.d, this.n);
            output.write((char) decrypted_char.intValue());
        }

        output.close();
        encrypted_file.close();
        System.out.println("File decrypted successfully!");
    }

    public static boolean isPrime(int number) {
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Decryption decrypt = new Decryption("encrypted.txt");
        decrypt.decrypt();
    }
}