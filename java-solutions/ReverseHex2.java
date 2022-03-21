import java.util.*;
import java.io.*;
import java.util.Arrays;

public class ReverseHex2 {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            int size = 0, buffSize = 10, maxY = 0;
            int[][] numbers = new int[buffSize][];
            try {
                while (in.hasNextLine()) {
                    Scanner str = new Scanner(in.nextLine());
                    int tempBuffSize = 10;
                    int[] tempNums = new int[tempBuffSize];
                    tempNums[0] = 1;
                    try {
                        while (str.hasNextInt(16)) {
                            if (tempNums[0] + 1 > tempBuffSize) {
                                tempBuffSize *= 2;
                                tempNums = Arrays.copyOf(tempNums, tempBuffSize);
                            }
                            tempNums[tempNums[0]] = str.nextInt(16);
                            tempNums[0]++;
                            maxY = Math.max(tempNums[0], maxY);
                        }
                    } finally {
                        str.close();
                    }
                    if (size + 1 > buffSize) {
                        buffSize *= 2;
                        numbers = Arrays.copyOf(numbers, buffSize);
                    }
                    numbers[size] = tempNums;
                    size++;
                }
            } finally {
                in.close();
            }
            for (int i = size - 1; i >= 0; i--) {
                for (int j = numbers[i][0] - 1; j >= 1; j--) {
                    System.out.print(numbers[i][j] + " ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("input file not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("input ioexception " + e.getMessage());
        }
    }
}