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
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MM_RSA_Encryption {
    BigInteger prime1_big, prime2_big, e, d, f, g, phiN, n;
    String path;

    public MM_RSA_Encryption(String path, int prime1, int prime2){

        this.prime1_big = BigInteger.valueOf(prime1);
        this.prime2_big = BigInteger.valueOf(prime2);
        Random r = new Random();
/*        int prime1,prime2;


        int prime[] = new int[165];
        int counter =0;
        for (int i = 7; i < 1000; i++)
        {
            if (isPrime(i)){
                prime[counter] = i;
                counter++;
            }
        }
        while (true){
            prime1 = prime[r.nextInt(165)];
            prime2 = prime[r.nextInt(165)];
            if (prime1*prime2 > 255)
                break;
        }
*/
        System.out.printf("prime number 1: %d \n",prime1);
        System.out.printf("prime number 2: %d \n",prime2);

        this.prime1_big = BigInteger.valueOf(prime1);
        this.prime2_big = BigInteger.valueOf(prime2);
        this.n = prime1_big.multiply(prime2_big);
        this.phiN = (prime1_big.subtract(new BigInteger("1"))).multiply(prime2_big.subtract(new BigInteger("1")));
        this.g = (n.multiply(BigInteger.valueOf(2))).subtract(BigInteger.ONE);
        this.path = path;
        System.out.printf("n value: %d \n",n);
        System.out.printf("phiN value: %d \n",phiN);

        boolean eAnddFound = false;

        int number_of_e_to_be_collected = 10;
        int count_e = 0;
        BigInteger e_array[] = new BigInteger[number_of_e_to_be_collected];
        BigInteger d_array[] = new BigInteger[number_of_e_to_be_collected];

        for (int e = (phiN.intValue() - 1); e > 0; e--) {
            if (phiN.mod(BigInteger.valueOf(e)) != BigInteger.valueOf(0) & n.mod(BigInteger.valueOf(e)) != BigInteger.valueOf(0) & isPrime(e))
                for (int d = e + 1; d < phiN.intValue(); d++) {

                    if ((phiN.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (n.mod(BigInteger.valueOf(d)) != BigInteger.valueOf(0)) & (isPrime(d))) {
                        boolean condition = ((BigInteger.valueOf(d).multiply(BigInteger.valueOf(e))).mod(phiN)).equals(BigInteger.valueOf(1));
                        if (condition) {
                            //candidateEDvalues.add(new valueED(e,d));
                            System.out.printf("e*d mod phiN: %d \n",((BigInteger.valueOf(d).multiply(BigInteger.valueOf(e))).mod(phiN)));
                            //this.e = new BigInteger(String.valueOf(e));
                            //this.d = new BigInteger(String.valueOf(d));
                            //eAnddFound = true;
                            System.out.printf("%dth e and d calculated.\n", count_e+1);

                            e_array[count_e] = new BigInteger(String.valueOf(e));
                            d_array[count_e] = new BigInteger(String.valueOf(d));

                            count_e++;
                            if(count_e == number_of_e_to_be_collected){
                                eAnddFound = true;
                                break;
                            }

                        }
                    }
                }
            if (eAnddFound == true)
                break;
        }

        System.out.println(Arrays.toString(e_array));
        System.out.println(Arrays.toString(d_array));

        int position_of_e_to_be_selected = r.nextInt(count_e);

        this.e = e_array[position_of_e_to_be_selected];
        this.d = d_array[position_of_e_to_be_selected];

        System.out.printf("e value: %d \n", this.e);
        System.out.printf("d value: %d \n", this.d);

        this.f = (this.e.multiply(BigInteger.valueOf(2))).add(BigInteger.valueOf(1)); //f=(e*2)+1
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
            BigInteger encrypted_char = char_bigint.modPow( ( (this.f).subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)), (this.g).add(BigInteger.ONE) );
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