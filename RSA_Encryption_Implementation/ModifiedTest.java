/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class ModifiedTest {
    BigInteger prime1_big, prime2_big, e, d, f, g, phiN, n;
    String path;

    public ModifiedTest(String path){
        int prime1,prime2;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1st prime number: ");
        prime1 = scanner.nextInt();
        System.out.println("Enter 2nd prime number: ");
        prime2 = scanner.nextInt();

        System.out.printf("prime number 1: %d \n",prime1);
        System.out.printf("prime number 2: %d \n",prime2);

        this.prime1_big = BigInteger.valueOf(prime1);
        this.prime2_big = BigInteger.valueOf(prime2);
        this.n = prime1_big.multiply(prime2_big);
        this.phiN = (prime1_big.subtract(new BigInteger("1"))).multiply(prime2_big.subtract(new BigInteger("1")));
        this.g = n.subtract(BigInteger.ONE);
        this.path = path;
        System.out.printf("n value: %d \n",n);
        System.out.printf("phiN value: %d \n",phiN);

        boolean eAnddFound = false;

        for (int e = (phiN.intValue() / 2); e > 0; e--) {
            if (phiN.mod(BigInteger.valueOf(e)) != BigInteger.valueOf(0) & n.mod(BigInteger.valueOf(e)) != BigInteger.valueOf(0) & isPrime(e))
                //andidateEvalues.add(e);
                //System.out.println(e);
                for (int d = e + 1; d < phiN.intValue(); d++) {
                    if ((phiN.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (n.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (isPrime(d))) {
                        boolean condition = ((BigInteger.valueOf(d).multiply(BigInteger.valueOf(e))).mod(phiN)).equals(BigInteger.valueOf(1));
                        if (condition) {
                            System.out.printf("e*d mod phiN: %d\n",((BigInteger.valueOf(d).multiply(BigInteger.valueOf(e))).mod(phiN)));
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

        this.f = (e.multiply(BigInteger.valueOf(2))).add(BigInteger.valueOf(1)); //f=(e*2)+1
    }

    private FileReader read_file() throws FileNotFoundException{
        FileReader filereader = new FileReader(this.path);
        return filereader;
    }

    private FileWriter write_file() throws IOException{
        FileWriter filewriter = new FileWriter("encrypted_M.txt");
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

        System.out.println(this.f.toString());
        System.out.println(this.g.toString());

        FileReader myFile = read_file();
        FileWriter output = write_file();
        output.write(this.f.toString());
        output.write('\n');
        output.write(this.g.toString());
        output.write('\n');
        int character;
        while (-1 != (character = myFile.read())){
            BigInteger char_bigint = BigInteger.valueOf(character);
            BigInteger encrypted_char = char_bigint.modPow( ( (this.f).subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)), (this.g).add(BigInteger.ONE) );
            output.write(encrypted_char.toString());
            output.write('\n');
        }

        output.close();
        myFile.close();
        System.out.println("Encryption done!");

    }

    public static void main(String[]args) throws IOException
    {
        ModifiedTest me = new ModifiedTest("SampleTextFile_1000kb.txt");
        me.encrypt();
    }
}
