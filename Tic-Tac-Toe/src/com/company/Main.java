package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	Board board = new Board();
	board.printBoard();
	System.out.println("");
	Boolean player = true;
	Scanner scanner = new Scanner(System.in);
	while(true){
		if(player){
			String str = scanner.nextLine();
			String[] arr = str.split(" ");
			int type = Integer.parseInt(arr[0]);
			int row = Integer.parseInt(arr[1]);
			int col = Integer.parseInt(arr[2]);
			int playerScore = board.getPlayerScore();
			board.addLine(player,type,row,col);
			board.printBoard();
			if(playerScore == board.getPlayerScore()){
				player = false;
			}
		}
		else{
			Move move = board.findBestMove();
			int compScore=board.getCompScore();
			board.addLine(player,move.getType(),move.getRow()+1,move.getCol()+1);
			board.printBoard();
			System.out.println("");
			if(compScore==board.getCompScore()){
				player=true;
			}
		}

	}



	//board.addLine(true,1,1,2);
	//board.printBoard();
	//System.out.println("");
//
	//board.addLine(true,2,2,1);
	//board.printBoard();
	//System.out.println("");
//
	//board.addLine(true,2,1,1);
	//board.printBoard();
	//System.out.println("");
//
	//board.addLine(true,1,1,1);
	//board.printBoard();
	//System.out.println("");
	}
}

