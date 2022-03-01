package com.company;

public class Board {
    private boolean[][] verticalLines;
    private boolean[][] horizontalLines;
    private int[][] cells;
    private int playerScore;
    private int compScore;
    public Board(int rows,int cols){
        verticalLines = new boolean[rows][cols+1];
        horizontalLines = new boolean[rows+1][cols];
        cells = new int[rows][cols];
        playerScore=0;
        compScore=0;
    }
    public Board(){
        this(3,3);
    }
    public boolean addLine(boolean player,int type,int row,int col){
        if(type==1){
            if(row > verticalLines.length || row < 1 || col < 1 || col > verticalLines[0].length || verticalLines[row-1][col-1]){
                return false;
            }
            else {
                verticalLines[row-1][col-1]=true;
                checkCells(player,type,row-1,col-1);
                return true;
            }
        }
        else{
            if(row > horizontalLines.length || row < 1 || col < 1 || col > horizontalLines.length || horizontalLines[row-1][col-1]){
                return false;
            }
            else {
                horizontalLines[row-1][col-1]=true;
                checkCells(player,type,row-1,col-1);
                return true;
            }
        }
    }
    public void checkCells(boolean player,int type,int row,int col){
            if(type==1){
                if(col!=0){
                    if(horizontalLines[row][col-1] && horizontalLines[row+1][col-1] && verticalLines[row][col-1]){
                            if(player){
                                cells[row][col-1]=1;
                                playerScore+=1;
                            }
                            else{
                                cells[row][col-1]=2;
                                compScore+=1;
                            }
                    }
                }
                if(col!=verticalLines[0].length-1){
                    if(horizontalLines[row][col] && horizontalLines[row+1][col] && verticalLines[row][col+1]){
                        if(player){
                            cells[row][col]=1;
                            playerScore+=1;
                        }
                        else{
                            cells[row][col]=2;
                            compScore+=1;
                        }
                    }
                }
            }
            else {
                if(row!=0){
                    if(horizontalLines[row-1][col] && verticalLines[row-1][col] && verticalLines[row-1][col+1]){
                        if(player){
                            cells[row-1][col]=1;
                            playerScore+=1;
                        }
                        else{
                            cells[row-1][col]=2;
                            compScore+=1;
                        }
                    }
                }
                if(row!=horizontalLines.length-1){
                    if(horizontalLines[row+1][col] && verticalLines[row][col] && verticalLines[row][col+1]){
                        if(player){
                            cells[row][col]=1;
                            playerScore+=1;
                        }
                        else{
                            cells[row][col]=2;
                            compScore+=1;
                        }
                    }
                }
            }
    }
    public void uncheckCells(boolean player,int type,int row,int col){
        if(type==1){
            if(col!=0){
                if(horizontalLines[row][col-1] && horizontalLines[row+1][col-1] && verticalLines[row][col-1]){
                    if(player){
                        cells[row][col-1]=0;
                        playerScore-=1;
                    }
                    else{
                        cells[row][col-1]=0;
                        compScore-=1;
                    }
                }
            }
            if(col!=verticalLines[0].length-1){
                if(horizontalLines[row][col] && horizontalLines[row+1][col] && verticalLines[row][col+1]){
                    if(player){
                        cells[row][col]=0;
                        playerScore-=1;
                    }
                    else{
                        cells[row][col]=0;
                        compScore-=1;
                    }
                }
            }
        }
        else {
            if(row!=0){
                if(horizontalLines[row-1][col] && verticalLines[row-1][col] && verticalLines[row-1][col+1]){
                    if(player){
                        cells[row-1][col]=0;
                        playerScore-=1;
                    }
                    else{
                        cells[row-1][col]=0;
                        compScore-=1;
                    }
                }
            }
            if(row!=horizontalLines.length-1){
                if(horizontalLines[row+1][col] && verticalLines[row][col] && verticalLines[row][col+1]){
                    if(player){
                        cells[row][col]=0;
                        playerScore-=1;
                    }
                    else{
                        cells[row][col]=0;
                        compScore-=1;
                    }
                }
            }
        }
    }
    public void printBoard(){
        StringBuilder str;
        for (int i = 0; i < verticalLines.length; i++) {
            str= new StringBuilder("o");
            for (int j = 0; j < horizontalLines[i].length; j++) {
                if(horizontalLines[i][j]){
                    str.append("-");
                }
                else {
                    str.append(" ");
                }
                str.append("o");
            }
            System.out.println(str);
            str=new StringBuilder("");
            for (int j = 0; j < verticalLines[i].length; j++) {
                if(verticalLines[i][j]){
                    str.append("|");
                }else{
                    str.append(" ");
                }
                if(j!= verticalLines[i].length - 1){
                    if(cells[i][j]==0){
                        str.append(" ");
                    }
                    else if(cells[i][j]==1){
                        str.append("P");
                    }
                    else{
                        str.append("C");
                    }
                }
            }
            System.out.println(str);
        }
        str = new StringBuilder("o");
        for (int i = 0,n=horizontalLines.length-1; i < horizontalLines[n].length; i++) {
            if(horizontalLines[n][i]){
                str.append("-");
            }
            else {
                str.append(" ");
            }
            str.append("o");
        }
        System.out.println(str);
    }

    private int evalBoard(){
            return playerScore-compScore;
    }
    private boolean hasMovesLeft(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if(cells[i][j]==0)return true;
            }
        }
        return false;
    }
    private int minimax(int depth, Boolean player) {
        int score = evalBoard();

        //if (score > 0)
        //    return score;
//
        //if (score < 0)
        //    return score;


        if (!hasMovesLeft())
            return 0;

        if(depth==6)
            return score;

        if (player) {
            int best = -1000;

            for (int i = 0; i < verticalLines.length; i++) {
                for (int j = 0; j < verticalLines[0].length; j++) {
                    if(!verticalLines[i][j]){
                        verticalLines[i][j]=true;
                        int pScore = playerScore;
                        checkCells(player,1,i,j);
                        if(pScore!=playerScore){
                            best = Math.max(best, minimax(
                                    depth + 1, player));
                        }
                        else {
                            best = Math.max(best, minimax(
                                    depth + 1, !player));
                        }

                        verticalLines[i][j] = false;
                        uncheckCells(player,1,i,j);
                    }
                }
            }

            for (int i = 0; i < horizontalLines.length; i++) {
                for (int j = 0; j < horizontalLines[0].length; j++) {
                    if(!horizontalLines[i][j]){
                        horizontalLines[i][j]=true;
                        int pScore = playerScore;
                        checkCells(player,2,i,j);
                        if(pScore!=playerScore){
                            best = Math.max(best, minimax(
                                    depth + 1, player));
                        }
                        else {
                            best = Math.max(best, minimax(
                                    depth + 1, !player));
                        }
                        horizontalLines[i][j] = false;
                        uncheckCells(player,2,i,j);
                    }
                }
            }
            return best;
        }
        else {
            int best = 1000;

            for (int i = 0; i < verticalLines.length; i++) {
                for (int j = 0; j < verticalLines[0].length; j++) {
                    if(!verticalLines[i][j]){
                        verticalLines[i][j]=true;
                        int cScore=compScore;
                        checkCells(player,1,i,j);
                        if(cScore!=compScore){
                            best = Math.min(best, minimax(
                                    depth + 1, player));
                        }
                        else{
                            best = Math.min(best, minimax(
                                    depth + 1, !player));
                        }
                        verticalLines[i][j] = false;
                        uncheckCells(player,1,i,j);
                    }
                }
            }

            for (int i = 0; i < horizontalLines.length; i++) {
                for (int j = 0; j < horizontalLines[0].length; j++) {
                    if(!horizontalLines[i][j]){
                        horizontalLines[i][j]=true;
                        int cScore=compScore;
                        checkCells(player,2,i,j);
                        if(cScore!=compScore){
                            best = Math.min(best, minimax(
                                    depth + 1, player));
                        }
                        else{
                            best = Math.min(best, minimax(
                                    depth + 1, !player));
                        }
                        best = Math.min(best, minimax(
                                depth + 1, !player));
                        horizontalLines[i][j] = false;
                        uncheckCells(player,2,i,j);
                    }
                }
            }
            return best;
        }
    }
    public Move findBestMove() {
        int bestVal = 1000;
        Move bestMove = new Move();

        for (int i = 0; i < verticalLines.length; i++) {
            for (int j = 0; j < verticalLines[0].length; j++) {
                if(!verticalLines[i][j]){
                    verticalLines[i][j]=true;
                    checkCells(false,1,i,j);

                    int moveVal = minimax( 0, true);
                    verticalLines[i][j] = false;
                    uncheckCells(false,1,i,j);
                    if (moveVal < bestVal)
                    {
                        bestMove.setRow(i);
                        bestMove.setCol(j);
                        bestMove.setType(1);
                        bestVal = moveVal;
                    }
                }
            }
        }

        for (int i = 0; i < horizontalLines.length; i++) {
            for (int j = 0; j < horizontalLines[0].length; j++) {
                if(!horizontalLines[i][j]){
                    horizontalLines[i][j]=true;
                    checkCells(false,2,i,j);
                    int moveVal = minimax( 0, true);
                    horizontalLines[i][j] = false;
                    uncheckCells(false,2,i,j);
                    if (moveVal < bestVal)
                    {
                        bestMove.setRow(i);
                        bestMove.setCol(j);
                        bestMove.setType(2);
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
    public int getPlayerScore() {
        return playerScore;
    }

    public int getCompScore() {
        return compScore;
    }
}
