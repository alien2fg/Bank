package com.laba.solvd.mergesort;

import java.util.Scanner;

public class MergeSort {

    public static class Main {

        private static int[] tempArray;

        public static void PrintSort(int[] numbers, int quantity) {
            System.out.println("\nContent of the array before sorting: ");
            for (int number : numbers) {
                System.out.print(number + " ");
            }

            //Sorting
            MergeSort(numbers, 0, quantity - 1);

            System.out.println("\nContent of the array after sorting: ");
            for (int number : numbers) {
                System.out.print(number + " ");
            }
        }

        public static void MergeSort(int[] numbers, int start, int end) {
            if (start < end) {
                int middle = (start + end) / 2;
                MergeSort(numbers, start, middle);
                MergeSort(numbers, middle + 1, end);
                Merge(numbers, start, middle, end);
            }
        }

        public static void Merge(int[] numbers, int start, int middle, int end) {

            System.arraycopy(numbers, start, tempArray, start, end - start + 1);

            int i = start;
            int j = middle + 1;
            int k = start;

            while (i <= middle && j <= end) {
                if (tempArray[i] <= tempArray[j]) {
                    numbers[k] = tempArray[i];
                    i++;
                } else {
                    numbers[k] = tempArray[j];
                    j++;
                }
                k++;
            }

            while (i <= middle) {
                numbers[k] = tempArray[i];
                i++;
                k++;
            }

        }

        public static void main(String[] args) {
            try {
                if (args.length != 0) {
                    int[] numbers = new int[args.length];
                    tempArray = new int[args.length];
                    for (int i = 0; i < args.length; i++) {
                        numbers[i] = Integer.parseInt(args[i]);
                    }
                    PrintSort(numbers, args.length);
                } else {
                    Scanner myObj = new Scanner(System.in);
                    System.out.println("Please enter the number of elements to be sorted.");
                    int quantity = myObj.nextInt();
                    if (quantity <= 0) {
                        System.out.println("Number of elements must be positive.");
                        return;
                    }
                    int[] numbers = new int[quantity];
                    tempArray = new int[quantity];
                    System.out.println("Please enter an array of numbers to be sorted");
                    for (int i = 0; i < quantity; i++) {
                        System.out.printf("Please enter %d number: %n", i + 1);
                        while (!myObj.hasNextInt()) {
                            System.out.println("Invalid input. Please enter an integer.");
                            myObj.nextInt();
                        }
                        numbers[i] = myObj.nextInt();
                    }
                    PrintSort(numbers, quantity);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please provide only integer values.");
            }
        }
    }
}
