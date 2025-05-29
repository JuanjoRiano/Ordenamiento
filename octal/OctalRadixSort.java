package octal;

import java.util.*;

public class OctalRadixSort {

    public static void radixSortOctal(int[] arr) {
    	
        int max = Arrays.stream(arr).max().orElse(0);
        int maxDigits = Integer.toOctalString(max).length();

        for (int digit = 0; digit < maxDigits; digit++) {

            List<List<Integer>> buckets = new ArrayList<>();
            for (int i = 0; i < 8; i++) buckets.add(new ArrayList<>());

            for (int num : arr) {
                int octalDigit = getOctalDigit(num, digit);
                buckets.get(octalDigit).add(num);
            }

            // Combinar buckets
            int index = 0;
            for (List<Integer> bucket : buckets) {
                for (int num : bucket) {
                    arr[index++] = num;
                }
            }
        }
    }

    private static int getOctalDigit(int num, int position) {
        return (num >> (3 * position)) & 7;  // 3 bits por dígito octal
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.printf("%d (%s) ", num, Integer.toOctalString(num));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {8, 15, 7, 64, 1, 32, 12};
        System.out.println("Antes de ordenar:");
        printArray(arr);

        radixSortOctal(arr);

        System.out.println("Después de ordenar:");
        printArray(arr);
    }
}