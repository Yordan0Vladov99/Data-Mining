package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Board_V2 {
    private int[] queens;
    private int[] rowConflicts;
    private int[] mainDiagConflicts;
    private int[] secDiagConflicts;

    public Board_V2(int n) {
        long start = System.currentTimeMillis();

        solveBoard(n);

        long finish = System.currentTimeMillis();
        double timeElapsed = finish - start;
        System.out.println(timeElapsed / 1000);
    }

    private void solveBoard(int n) {
        if (n == 6) {
            queens = new int[]{1, 3, 5, 0, 2, 4};
            return;
        }
        if (n == 7) {
            queens = new int[]{0, 4, 1, 5, 2, 6, 3};
            return;
        }
        Random random = new Random();
        queens = new int[n];
        rowConflicts = new int[n];
        mainDiagConflicts = new int[2 * n];
        secDiagConflicts = new int[2 * n];
        int min = 0;
        int sum = 0;
        int row = -1;
        ArrayList<Integer> rows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            min = Integer.MAX_VALUE;
            row = -1;
            for (int j = 0; j < n; j++) {
                sum = rowConflicts[j] + mainDiagConflicts[i + j] + secDiagConflicts[n - j - 1 + i];
                if (sum == 0) {
                    row = j;
                    break;
                } else if (sum < min) {
                    min = sum;
                    row = j;
                } else if (sum == min) {
                    row = j;
                }
            }

            queens[i] = row;

            rowConflicts[queens[i]]++;
            mainDiagConflicts[queens[i] + i]++;
            secDiagConflicts[n - queens[i] - 1 + i]++;
        }

        ArrayList<Integer> attackedQueens = new ArrayList<Integer>();

        while (true) {
            attackedQueens.clear();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < queens.length; i++) {
                int conflictSum = rowConflicts[queens[i]] + mainDiagConflicts[queens[i] + i] + secDiagConflicts[n - queens[i] - 1 + i];
                if (conflictSum != 0) {
                    if (conflictSum > max) {
                        max = conflictSum;
                        attackedQueens.clear();
                        attackedQueens.add(i);
                    } else if (conflictSum == max) {
                        attackedQueens.add(i);
                    }
                }
            }
            if (max == 3) {
                return;
            }
            int temp = (int) (Math.random() * attackedQueens.size());
            int index = attackedQueens.get(temp);
            min = Integer.MAX_VALUE;
            int conflicts = 0;
            row = -1;
            attackedQueens.clear();
            for (int i = 0; i < n; i++) {
                if (i == queens[index]) continue;

                conflicts = countQueenConflicts(index, i);
                if (conflicts == 0) {
                    row = i;
                    break;
                }
                if (conflicts < min) {
                    attackedQueens.clear();
                    attackedQueens.add(i);
                    min = conflicts;
                } else if (conflicts == min) {
                    attackedQueens.add(i);
                }
            }
            if (row == -1) {
                temp = (int) (Math.random() * attackedQueens.size());
                row = attackedQueens.get(temp);
            }

            decreaseQueenConflicts(index, queens[index]);

            queens[index] = row;

            increaseQueenConflicts(index, queens[index]);
        }
    }

    private int countQueenConflicts(int col, int row) {
        return rowConflicts[row] + mainDiagConflicts[row + col] + secDiagConflicts[queens.length - row - 1 + col];
    }

    private void decreaseQueenConflicts(int col, int row) {
        rowConflicts[row]--;
        mainDiagConflicts[row + col]--;
        secDiagConflicts[queens.length - row - 1 + col]--;

    }

    private void increaseQueenConflicts(int col, int row) {
        rowConflicts[row]++;
        mainDiagConflicts[row + col]++;
        secDiagConflicts[queens.length - row - 1 + col]++;
    }


    public String toString() {

        int[][] board = new int[queens.length][queens.length];
        for (int i = 0; i < queens.length; i++) {
            board[queens[i]][i] = 1;
        }
        StringBuilder str = new StringBuilder();
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    str.append("*").append(" ");
                } else {
                    str.append("_").append(" ");
                }
            }
            if (i != n - 1) str.append("\n");
        }
        return str.toString();
    }
}
