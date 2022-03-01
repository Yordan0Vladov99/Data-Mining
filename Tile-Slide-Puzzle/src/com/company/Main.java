package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tiles = scanner.nextInt();
        int index = scanner.nextInt();
        int size = (int) Math.sqrt(tiles + 1);
        int[] arr = new int[size * size];
        int[] row;
        String str;
        String[] strArr;
        int k = 0;
        scanner.nextLine();
        for (int i = 0; i < size; i++) {
            str = scanner.nextLine();
            strArr = str.split(" ");
            if (strArr.length != size) {
                System.out.println("Wrong row length!");
                return;
            }
            for (String s : strArr) {
                arr[k++] = Integer.parseInt(s);
            }
        }


        Board board = new Board(size, arr,0, "");
        Solver solver = new Solver(board);

        solver.movesIDA();
    }
}
