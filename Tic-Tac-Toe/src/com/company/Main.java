package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	        Board board = new Board();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Go first(y/n):");
			String ch = scanner.nextLine();
			boolean player = true;

			if(ch.equals("n")){
				player=false;
			}
			int depth = 0;
			while(true){
				int result = board.evaluate(depth);
				if(result > 0){
					System.out.println("Congatulations! You win!");
					break;
				}
				if(result < 0){
					System.out.println("You lose. Try again.");
					break;
				}
				if(!board.isMovesLeft()){
					board.printBoard();
					System.out.println("Draw.");
					break;
				}
				if(player){
					board.printBoard();
					System.out.print("Enter move:");
					String[] str = scanner.nextLine().split(" ");
					int row = Integer.parseInt(str[0]);
					int col = Integer.parseInt(str[1]);
					board.insertTile(true,row,col);
					player = false;
				}else {
					Move move = board.findBestMove();
					board.insertTile(false,move.getRow()+1,move.getCol()+1);
					player = true;

				}
				depth++;
			}
    }
}
