import java.io.*;
import java.util.BitSet;
import java.util.Scanner;


public class PrimeNumberGenerator {

    public static void main(String[] args) throws IOException {
        int n, o;
        Scanner in = new Scanner(System.in);
        n = input(in);
        // Creates range of BitSet here
        BitSet prime = new BitSet(n + 1);
        System.out.print("Sieve of Eratosthenes (1) or Sieve of Sundaram (2): ");
        o = in.nextInt();
        if (o == 1)
            eratosthenes(prime, n);
        else
            sundaram(prime, n);

    }

    public static int input(Scanner in) {
        int n = 0;
        do {
            System.out.print("Enter Max Range Number: ");
            n = in.nextInt();
        } while (n < 2);
        return n;
    }

    public static void eratosthenes(BitSet prime, int n) throws IOException {
        long startTime, endTime;
        startTime= System.currentTimeMillis();

        //Start Algorithm

        int pNum = 2;

        do {
            for(int i = 2; pNum * i <= n; i++) {
                prime.set(pNum * i);
            }
            pNum = prime.nextClearBit(pNum + 1);
        } while(pNum <= n);

        //Stop Algorithm

        endTime= System.currentTimeMillis();

        writeToFile(startTime, endTime, prime, 1, n);
    }

    public static void sundaram(BitSet prime, int n) throws IOException {
        long startTime, endTime;
        startTime= System.currentTimeMillis();

        //Start Algorithm

        BitSet notPrime = new BitSet(n + 1);
        int notP = 4;
        notPrime.set(notP);
        prime.set(2);

        for(int j = 3; notP != -1; j= j + 2) {
            for(int k = j; notP + k < n; k = k + j) { //THIS IS HELLA BROKEN
                notPrime.set(notP + k);
            }
            notP = notPrime.nextSetBit(notP + 3);
        }

        int i = 1;
        int primeNum = 0;
        while(notPrime.nextClearBit(i) * 2 + 1 <= n) {
            primeNum = notPrime.nextClearBit(i) * 2 + 1;
            prime.set(primeNum);
            i = notPrime.nextClearBit(i + 1);
        }

        //Stop Algorithm

        endTime= System.currentTimeMillis();

        writeToFile(startTime, endTime, prime, 2, n);
    }

    public static void writeToFile(long startTime, long endTime, BitSet prime, int sieve, int n) throws IOException {
        if(sieve == 1) {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("eratosthenes.txt")));
            String sT, eT, tT, index;
            sT = Long.toString(startTime);
            eT = Long.toString(endTime);
            tT = Long.toString(endTime - startTime);
            out.write("Start Time: " + sT);
            out.println();

            int pNum = 2;
            do {
                index = Integer.toString(prime.nextClearBit(pNum));
                out.write(index);
                out.println();
                pNum = prime.nextClearBit(pNum + 1);
            } while(pNum <= n);

            out.write("End Time: " + eT);
            out.println();
            out.write("Total Elapsed Time: " + tT + " Milliseconds");
            out.close();

        } else if(sieve == 2) {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sundaram.txt")));
            String sT, eT, tT, index;
            sT = Long.toString(startTime);
            eT = Long.toString(endTime);
            tT = Long.toString(endTime - startTime);
            out.write("Start Time: " + sT);
            out.println();

            int pNum = 2;
            do {
                index = Integer.toString(prime.nextSetBit(pNum));
                out.write(index);
                out.println();
                pNum = prime.nextSetBit(pNum + 1);
            } while(pNum != -1);

            out.write("End Time: " + eT);
            out.println();
            out.write("Total Elapsed Time: " + tT + " Milliseconds");
            out.close();
        }
    }
}
