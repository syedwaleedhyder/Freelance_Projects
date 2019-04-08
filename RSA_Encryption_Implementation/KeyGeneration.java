import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

public class KeyGeneration {
    BigInteger prime1, prime2, e, d, f, g, phiN, n;
    String mode;

    KeyGeneration(ModifiedEncryption encryption_object){
        this.prime1 = encryption_object.prime1_big;
        this.prime2 = encryption_object.prime2_big;
        this.n = encryption_object.n;
        this.phiN = encryption_object.phiN;
        this.mode = "ME"; // Single Modified

        System.out.printf("n value: %d \n",n);
        System.out.printf("phiN value: %d \n",phiN);
    }
    KeyGeneration(MM_RSA_Encryption encryption_object){
        this.prime1 = encryption_object.prime1_big;
        this.prime2 = encryption_object.prime2_big;
        this.n = encryption_object.n;
        this.phiN = encryption_object.phiN;
        this.mode = "MME"; // Double Modified Encryption

        System.out.printf("n value: %d \n",n);
        System.out.printf("phiN value: %d \n",phiN);
    }

    public static boolean isPrime(int number) {
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public BigInteger[] generator(){
        System.out.println("Generating key...");
        LocalTime time1 = LocalTime.now();

        BigInteger key[] = new BigInteger[2];
        Random r = new Random();

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
        if(this.mode.equals("MME")){
            System.out.println(mode);
            this.g = (n.multiply(BigInteger.valueOf(2))).subtract(BigInteger.ONE); // g = n*2-1
        }
        else if(this.mode.equals("ME")){
            System.out.println(mode);
            this.g = this.n;
        }
        key[0] = f;
        key[1] = g;

        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);

        System.out.println("Duration to generate key: " + duration.toMillis() + "ms");
        System.out.println("Key generated...");

        return key;
    }
}
