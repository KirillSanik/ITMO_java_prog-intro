import java.util.Scanner;
import java.util.Arrays;

public class ReverseTranspose {	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size = 0, buffSize = 10, maxY = 0;
		int[][] numbers = new int[buffSize][];
		while (in.hasNextLine()) {
			Scanner str = new Scanner(in.nextLine());
			int tempBuffSize = 10;
			int[] tempNums = new int[tempBuffSize];
			tempNums[0] = 1;
			while (str.hasNextInt()) {
				if (tempNums[0] + 1 > tempBuffSize) {
					tempBuffSize *= 2;
					tempNums = Arrays.copyOf(tempNums, tempBuffSize);
				}
				tempNums[tempNums[0]] = str.nextInt();
				tempNums[0]++;
				maxY = Math.max(tempNums[0], maxY);
			}
			if (size + 1 > buffSize) {
				buffSize *= 2;
				numbers = Arrays.copyOf(numbers, buffSize);
			}
			numbers[size] = tempNums;
			size++;
		}
		for (int i = 1; i < maxY; i++) {
			for (int j = 0; j < size; j++) {
				if (numbers[j][0] > i) {
					System.out.print(numbers[j][i] + " ");
				}
			}
			System.out.println();
		}
	}
}