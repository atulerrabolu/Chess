package Pieces;

import Chess.Board;
import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(String side, int row, int col, Board board) {
        super(side, board, row, col);
    }

    public ArrayList<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        getDirMoves(moves, 1, 1); //down-right
        getDirMoves(moves, -1, 1); //down-left
        getDirMoves(moves, 1, -1); //up-right
        getDirMoves(moves, -1, -1); //up-left
        return moves;
    }

    /*
       For loop: Loops through all respective diagonal that is within the board.

       Inside for loop: If there is any piece in the spot,
       it will break the loop after checking 2 conditions:

       1) If it is an enemy in the spot, piece can move there.
       2) If it is a teammate in the spot, piece can not move there.
    */

    public void getDirMoves(ArrayList<int[]> moves, int horizontalDir, int verticalDir) {
        for (int i = 1; getBoard().isSafe(getRow() + i*verticalDir, getCol() + i*horizontalDir); i++) {
            if (getBoard().isPiece(getRow() + i*verticalDir, getCol() + i*horizontalDir)) {
                if (getBoard().isEnemyPiece(getSide(),getRow() + i*verticalDir, getCol() + i*horizontalDir))
                    moves.add(new int[] {getRow() + i*verticalDir, getCol() + i*horizontalDir});
                break;
            } else {
                moves.add(new int[] {getRow() + i*verticalDir, getCol() + i*horizontalDir});
            }
        }
    }

    public String toString() {
        return "b";
    }
}