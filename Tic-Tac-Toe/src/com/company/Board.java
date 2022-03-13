package com.company;

public class Board {
    int[][] tiles;
    private static final int player = 1;
    private static final int computer = 2;
    public Board() {
        tiles = new int[3][3];
    }

    public boolean isMovesLeft() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tiles[i][j] == 0)
                    return true;
        return false;
    }

    public int evaluate(int depth) {
        for (int row = 0; row < 3; row++) {
            if (tiles[row][0] == tiles[row][1] && tiles[row][1] == tiles[row][2]) {
                if (tiles[row][0] == player)
                    return 10 - depth;
                else if (tiles[row][0] == computer)
                    return depth - 10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (tiles[0][col] == tiles[1][col] &&
                    tiles[1][col] == tiles[2][col]) {
                if (tiles[0][col] == player)
                    return 10 - depth;

                else if (tiles[0][col] == computer)
                    return depth - 10;
            }
        }

        if (tiles[0][0] == tiles[1][1] && tiles[1][1] == tiles[2][2]) {
            if (tiles[0][0] == player)
                return 10 - depth;
            else if (tiles[0][0] == computer)
                return depth - 10;
        }

        if (tiles[0][2] == tiles[1][1] && tiles[1][1] == tiles[2][0]) {
            if (tiles[0][2] == player)
                return 10 - depth;
            else if (tiles[0][2] == computer)
                return depth - 10;
        }

        return 0;
    }

    private int minimax(int depth, Boolean isMax,int alpha, int beta) {
        int score = evaluate(depth);

        if (score != 0)
            return score;

        if (!isMovesLeft())
            return 0;

        if (isMax) {
            int best = -500;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tiles[i][j] == 0) {
                        tiles[i][j] = player;

                        best = Math.max(best, minimax(depth + 1, !isMax,alpha,beta));
                        tiles[i][j] = 0;
                        alpha = Math.max(alpha,best);
                        if(beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return best;
        } else {
            int best = 500;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tiles[i][j] == 0) {
                        tiles[i][j] = computer;

                        best = Math.min(best, minimax(depth + 1, !isMax,alpha,beta));
                        tiles[i][j] = 0;

                        beta = Math.min(beta,best);
                        if(beta<=alpha){
                            break;
                        }
                    }
                }
            }
            return best;
        }
    }

    public Move findBestMove() {
        int bestVal = 500;
        Move bestMove = new Move();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] == 0) {
                    tiles[i][j] = computer;

                    int moveVal = minimax(0, true,Integer.MIN_VALUE,Integer.MAX_VALUE);

                    tiles[i][j] = 0;

                    if (moveVal < bestVal) {
                        bestMove.setRow(i);
                        bestMove.setCol(j);
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    public void insertTile(boolean isPlayer, int row, int col) {
        if (row < 1 || col < 1 || row > tiles.length || col > tiles[0].length) return;

        if (isPlayer) {
            tiles[row - 1][col - 1] = player;
        } else {
            tiles[row - 1][col - 1] = computer;
        }
    }

    public void printBoard() {
        StringBuilder str;

        for (int i = 0; i < 3; i++) {
            str = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                str.append(" ");
                str.append("-");
            }
            System.out.println(str.toString());
            str = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                str.append("|");
                if (j != 3) {
                    if (tiles[i][j] == player) {
                        str.append("X");
                    } else if (tiles[i][j] == computer) {
                        str.append("O");
                    } else {
                        str.append(" ");
                    }
                }
            }
            System.out.println(str.toString());
        }


        str = new StringBuilder();
        for (int j = 0; j < 3; j++) {
            str.append(" ");
            str.append("-");
        }
        System.out.println(str.toString());
    }

}
