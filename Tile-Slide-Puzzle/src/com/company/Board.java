package com.company;

import java.util.*;

public class Board {
    private final int[] tiles;
    private final int moves;
    private final String prev;
    private int manhattan;
    private int linearConflicts;
    private HashMap<Integer,Integer> goalPos;

    public Board(int size, int[] tiles, int moves, String prev) {
        this.tiles = new int[size * size];
        System.arraycopy(tiles, 0, this.tiles, 0, size * size);
        this.moves = moves;
        this.prev = prev;
        manhattan();
    }

    public Board(int size, int[] tiles, int moves, String prev,int manhattan, int linearConflicts, HashMap<Integer,Integer> goalPos) {
        this.tiles = new int[size * size];
        System.arraycopy(tiles, 0, this.tiles, 0, size * size);
        this.moves = moves;
        this.prev = prev;
        this.manhattan = manhattan;
        this.linearConflicts = linearConflicts;
        this.goalPos = new HashMap<>(goalPos);
    }

    public Board(Board board) {
        this.tiles = new int[board.size() * board.size()];
        System.arraycopy(board.tiles, 0, this.tiles, 0, board.size() * board.size());
        this.moves = board.moves;
        this.prev = board.prev;
        this.manhattan = board.manhattan;
        this.linearConflicts = board.linearConflicts;
        this.goalPos = new HashMap<>(board.goalPos);
    }

    public int getMoves() {
        return moves;
    }

    public String getPrev() {
        return prev;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int n = size() * size();
        for (int i = 0; i < n; i++) {

            str.append(tiles[i]).append(" ");
            if ((i + 1) % size() == 0 && i != n - 1) str.append("\n");
        }
        return str.toString();
    }

    public int size() {
        return (int) Math.sqrt(tiles.length);
    }

    public void manhattan() {
        this.goalPos = new HashMap<>();

        int n = size() * size();
        int[] arr = new int[n - 1];
        fillArray(n, arr);
        for (int i = 0; i < n - 1; i++) {
            goalPos.put(arr[i], i);
        }
        int manhattanNum = 0;
        int inversions = 0;
        int size = size();
        for (int i = 0; i < n; i++) {
            int rowI = i / size;
            if (tiles[i] == 0) continue;

            int goal = goalPos.get(tiles[i]);
            int goalRow = goal / (size());
            int goalCol = goal % size();
            manhattanNum += Math.abs(goalRow - i / size());
            manhattanNum += Math.abs(goalCol - i % size());

            for (int j = i + 1; j < (rowI + 1) * size; j++) {

                if (tiles[j] == 0) continue;

                int rowJ = j / size;


                int goalRowI = goalPos.get(tiles[i]) / size;

                int goalRowJ = goalPos.get(tiles[j]) / size;

                if (rowI == goalRowI && rowJ == goalRowJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[i])) {

                        inversions++;

                    }

                }


            }

            for (int j = i + size; j < n; j += size) {

                if (tiles[j] == 0) continue;

                int colI = i % size;

                int colJ = j % size;


                int goalColI = goalPos.get(tiles[i]) % size;

                int goalColJ = goalPos.get(tiles[j]) % size;

                if (colI == goalColI && colJ == goalColJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[i])) {

                        inversions++;

                    }
                }
            }
        }

        this.manhattan = manhattanNum;
        this.linearConflicts = 2*inversions;
    }

    private void fillArray(int n, int[] arr) {
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (tiles[i] == 0) continue;
            arr[k++] = tiles[i];
        }
        Arrays.sort(arr);
    }

    public boolean isGoal() {
        int n = size() * size();
        int[] arr = new int[n - 1];
        int k = 0;

        fillArray(n, arr);

        for (int i = 0; i < n - 1; i++) {
            if (arr[k++] != tiles[i]) {
                return false;
            }
        }

        return true;
    }

    public boolean equals(Board y) {
        return this.toString().equals(y.toString());
    }

    public PriorityQueue<Board> neighbors() {
        int n = size() * size();
        int[] neighbor = new int[n];
        int manh;
        int linCon;
        System.arraycopy(tiles, 0, neighbor, 0, n);


        int row = 0, col = 0, position = 0;


        for (int j = 0; j < n; j++) {
            if (neighbor[j] == 0) {
                row = j / size();
                col = j % size();
                position = j;
                break;
            }
        }


        PriorityQueue<Board> neighbors = new PriorityQueue<>(2, Comparator.comparingInt(Board::weight));
        int temp;
        if (row > 0) {
            int size = size();
            manh = this.manhattan;
            temp = neighbor[position - size];
            neighbor[position - size] = 0;
            neighbor[position] = temp;


            int goal = goalPos.get(tiles[position - size]);
            int goalRow = goal / (size);
            int goalCol = goal % size;
            manh -= Math.abs(goalRow - (position - size) / size);
            manh -= Math.abs(goalCol - (position - size) % size);
            manh += Math.abs(goalRow - position / size);
            manh += Math.abs(goalCol - position % size);


            linCon = linearConflicts;
            int oldRow = position/size() - 1;
            for (int j = oldRow*size; j < (oldRow+1) * size; j++) {

                if (j == position - size) continue;

                int rowJ = j / size;


                int goalRowI = goalPos.get(tiles[position - size]) / size;

                int goalRowJ = goalPos.get(tiles[j]) / size;

                if (rowJ == goalRowI && rowJ == goalRowJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position - size]) && j > position - size) {
                        linCon-=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position - size]) && j < position - size) {
                        linCon-=2;
                    }
                }
            }
            int newRow = oldRow + 1;
            for (int j = newRow*size; j < (newRow+1) * size; j++) {

                if (j == position) continue;

                int rowJ = j / size;


                int goalRowI = goalPos.get(tiles[position-size]) / size;

                int goalRowJ = goalPos.get(tiles[j]) / size;

                if (rowJ == goalRowI && rowJ == goalRowJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position-size]) && j > position) {
                        linCon+=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position-size]) && j < position) {
                        linCon+=2;
                    }
                }
            }

            neighbors.add(new Board(size(), neighbor, this.moves + 1, this.prev + "\ndown",manh,linCon,goalPos));
            neighbor[position - size()] = temp;
            neighbor[position] = 0;
        }
        if (col > 0) {
            int size = size();
            manh = this.manhattan;
            temp = neighbor[position - 1];
            neighbor[position - 1] = 0;
            neighbor[position] = temp;

            int goal = goalPos.get(tiles[position - 1]);
            int goalRow = goal / (size);
            int goalCol = goal % size;
            manh -= Math.abs(goalRow - (position - 1) / size);
            manh -= Math.abs(goalCol - (position - 1) % size);
            manh += Math.abs(goalRow - position / size);
            manh += Math.abs(goalCol - position % size);

            linCon = linearConflicts;
            int oldCol = (position-1)%size;
            for (int j = oldCol; j < n; j+=size) {

                if (j == position - 1) continue;

                int colJ = j % size;


                int goalColI = goalPos.get(tiles[position-1]) % size;

                int goalColJ = goalPos.get(tiles[j]) % size;

                if (colJ == goalColI && colJ == goalColJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position - 1]) && j > position -1) {
                        linCon-=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position - 1]) && j < position -1) {
                        linCon-=2;
                    }
                }
            }
            int newCol = position%size;
            for (int j = newCol; j < n; j+=size) {

                if (j == position) continue;

                int colJ = j % size;


                int goalColI = goalPos.get(tiles[position-1]) % size;

                int goalColJ = goalPos.get(tiles[j]) % size;

                if (colJ == goalColI && colJ == goalColJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position - 1]) && j > position) {
                        linCon+=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position - 1]) && j < position) {
                        linCon+=2;
                    }
                }
            }

            neighbors.add(new Board(size(), neighbor, this.moves + 1, this.prev + "\nright",manh,linCon,goalPos));
            neighbor[position - 1] = temp;
            neighbor[position] = 0;
        }
        if (col != size() - 1) {
            int size = size();
            manh = this.manhattan;
            temp = neighbor[position + 1];
            neighbor[position + 1] = 0;
            neighbor[position] = temp;

            int goal = goalPos.get(tiles[position + 1]);
            int goalRow = goal / (size);
            int goalCol = goal % size;
            manh -= Math.abs(goalRow - (position + 1) / size);
            manh -= Math.abs(goalCol - (position + 1) % size);
            manh += Math.abs(goalRow - position / size);
            manh += Math.abs(goalCol - position % size);

            linCon = linearConflicts;
            int oldCol = (position+1)%size;
            for (int j = oldCol; j < n; j+=size) {

                if (j == position + 1) continue;

                int colJ = j % size;


                int goalColI = goalPos.get(tiles[position + 1]) % size;

                int goalColJ = goalPos.get(tiles[j]) % size;

                if (colJ == goalColI && colJ == goalColJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position + 1]) && j > position + 1) {
                        linCon-=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position + 1]) && j < position + 1) {
                        linCon-=2;
                    }
                }
            }
            int newCol = position%size;
            for (int j = newCol; j < n; j+=size) {

                if (j == position) continue;

                int colJ = j % size;


                int goalColI = goalPos.get(tiles[position + 1]) % size;

                int goalColJ = goalPos.get(tiles[j]) % size;

                if (colJ == goalColI && colJ == goalColJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position + 1]) && j > position) {
                        linCon+=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position + 1]) && j < position) {
                        linCon+=2;
                    }
                }
            }

            neighbors.add(new Board(size(), neighbor, this.moves + 1, this.prev + "\nleft",manh,linCon,goalPos));
            neighbor[position + 1] = temp;
            neighbor[position] = 0;
        }
        if (row != size() - 1) {

            int size = size();
            manh = this.manhattan;
            temp = neighbor[position + size];
            neighbor[position + size] = 0;
            neighbor[position] = temp;


            int goal = goalPos.get(tiles[position + size]);
            int goalRow = goal / (size);
            int goalCol = goal % size;
            manh -= Math.abs(goalRow - (position + size) / size);
            manh -= Math.abs(goalCol - (position + size) % size);
            manh += Math.abs(goalRow - position / size);
            manh += Math.abs(goalCol - position % size);


            linCon = linearConflicts;
            int oldRow = position/size() + 1;
            for (int j = oldRow*size; j < (oldRow+1) * size; j++) {

                if (j == position + size) continue;

                int rowJ = j / size;


                int goalRowI = goalPos.get(tiles[position + size]) / size;

                int goalRowJ = goalPos.get(tiles[j]) / size;

                if (rowJ == goalRowI && rowJ == goalRowJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position + size]) && j > position + size) {
                        linCon-=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position + size]) && j < position + size) {
                        linCon-=2;
                    }
                }
            }
            int newRow = oldRow - 1;
            for (int j = newRow*size; j < (newRow+1) * size; j++) {

                if (j == position) continue;

                int rowJ = j / size;


                int goalRowI = goalPos.get(tiles[position+size]) / size;

                int goalRowJ = goalPos.get(tiles[j]) / size;

                if (rowJ == goalRowI && rowJ == goalRowJ) {

                    if (goalPos.get(tiles[j]) < goalPos.get(tiles[position+size]) && j > position) {
                        linCon+=2;
                    }
                    if (goalPos.get(tiles[j]) > goalPos.get(tiles[position+size]) && j < position) {
                        linCon+=2;
                    }
                }
            }

            neighbors.add(new Board(size(), neighbor, this.moves + 1, this.prev + "\nup",manh,linCon,goalPos));
            neighbor[position + size()] = temp;
            neighbor[position] = 0;
        }

        return neighbors;
    }

    public boolean isSolvable() {
        int n = size() * size();
        int[] arr = new int[n - 1];
        int k = 0;
        int blankRow = 0;
        for (int i = 0; i < n; i++) {
            if (tiles[i] == 0) {
                blankRow = i / size();
                continue;
            }
            arr[k++] = tiles[i];

        }

        int inversions = InversionCounter.mergeSortAndCount(arr, 0, arr.length - 1);
        if (n % 2 == 1) {
            return inversions % 2 != 1;
        }
        return (inversions + blankRow) % 2 == 1;
    }

    public int weight() {
        return manhattan+linearConflicts;
    }
}
