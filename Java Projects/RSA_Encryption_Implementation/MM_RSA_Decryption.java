/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class MM_RSA_Decryption {
    BigInteger e, d, f, g, n;
    String path;
    BufferedReader myfile;

    public MM_RSA_Decryption(String path) throws FileNotFoundException, IOException{
        this.path = path;
        myfile = read_file();
        this.f = new BigInteger (myfile.readLine());
        System.out.println(f.toString());
        this.g = new BigInteger (myfile.readLine());
        System.out.println(g.toString());
        this.n = ((this.g).add(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        this.d = calculate_d((this.f).subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), this.n);
        System.out.println(d.toString());
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
                    System.out.printf("d value: %d \n", this.d);
                    return BigInteger.valueOf(d);
                }
            }
        }
        return null;
    }


    private BufferedReader read_file() throws FileNotFoundException{
        FileReader filereader = new FileReader(this.path);
        BufferedReader bf = new BufferedReader (filereader);
        return bf;
    }

    private FileWriter write_file() throws IOException{
        FileWriter filewriter = new FileWriter("decrypted_MM.txt");
        return filewriter;
    }

    public void decrypt() throws FileNotFoundException, IOException{
        FileWriter output = write_file();
        String character;
        while (null != (character = myfile.readLine())){
            BigInteger char_bigint = new BigInteger(character);
            BigInteger encrypted_char = char_bigint.modPow(this.d,((this.g).add(BigInteger.ONE)).divide(BigInteger.valueOf(2)));
            output.write((char)encrypted_char.intValue());
        }
        output.close();
        myfile.close();
        System.out.println("Decryption succeeded! ");
    }

    public static boolean isPrime(int number) {
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[]args) throws IOException
    {
        MM_RSA_Decryption md = new MM_RSA_Decryption("encrypted_MM.txt");
        md.decrypt();
    }
}