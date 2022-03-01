package com.company;

import java.util.*;

public class Solver {
    private final Board board;

    public Solver(Board initial) {
        board = new Board(initial);
    }


    private int limitedSearch(int g, ArrayList<Board> pq, int threshold,long start) {
        Board top = pq.get(pq.size() - 1);
        if (g + top.weight() > threshold) {
            return (g + top.weight()) * -1;
        }
        if (top.isGoal()) {
            long finish = System.currentTimeMillis();
            double timeElapsed = finish - start;
            System.out.println(top.getMoves());
            System.out.println(top.getPrev());
            System.out.println(timeElapsed/1000);
            return top.getMoves();
        }
        PriorityQueue<Board> neighbors = top.neighbors();
        int min = Integer.MIN_VALUE;
        int t;
        for (Board neighbor : neighbors) {
            if (!pq.contains(neighbor)) {
                pq.add(neighbor);
                t = limitedSearch(g + 1, pq, threshold,start);
                if (t >= 0) return t;

                if (t > min) min = t;
                pq.remove(pq.size() - 1);
            }
        }
        return min;
    }

    public void movesIDA() {
        long start = System.currentTimeMillis();
        if (!board.isSolvable()) {
            System.out.println("Board isn't solvable.");
            return;
        }
        ArrayList<Board> pq = new ArrayList<>();
        int threshold;
        threshold = board.weight();
        pq.add(board);
        int t;
        while (true) {
            t = limitedSearch(0, pq, threshold,start);
            if (t >= 0) {
                return;
            }
            threshold = t * -1;
        }
    }
}
