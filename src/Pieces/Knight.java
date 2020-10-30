package Pieces;

import Chess.Board;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(String side, int row, int col, Board board) {
        super(side, board, row, col);
    }

    public ArrayList<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        getSpotMove(moves, 1, 2); //2 down, 1 right
        getSpotMove(moves, 1, -2); //2 up, 1 right
        getSpotMove(moves, -1, 2); //2 down, 1 left
        getSpotMove(moves, -1, -2); //2 up, 1 left
        getSpotMove(moves, 2, 1); //1 down, 2 right
        getSpotMove(moves, 2, -1); //1 up, 2 right
        getSpotMove(moves, -2, 1); //1 down, 2 left
        getSpotMove(moves, -2, -1); //1 up, 2 left
        return moves;
    }

    //Checks if the individual spot is valid
    public void getSpotMove(ArrayList<int[]> moves, int xMove, int yMove) {
        if (getBoard().isSafe(getRow() + yMove, getCol() + xMove) &&
                !getBoard().isTeamPiece(getSide(),getRow() + yMove, getCol() + xMove)) {
            moves.add(new int[] {getRow() + yMove, getCol() + xMove});
        }
    }

    public String toString() {
        return "k";
    }
}
