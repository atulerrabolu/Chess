package Pieces;

import Chess.Board;

import java.util.ArrayList;

public class King extends Piece {

    public King(String side, int row, int col, Board board) {
        super(side, board, row, col);
    }

    public ArrayList<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        getSpotMove(moves, 0, 1); //down
        getSpotMove(moves, 0, -1); //up
        getSpotMove(moves, -1, 0); //left
        getSpotMove(moves, 1, 0); //right
        getSpotMove(moves, 1, 1); //down-right
        getSpotMove(moves, -1, 1); //down-left
        getSpotMove(moves, 1, -1); //up-right
        getSpotMove(moves, -1, -1); //up-left
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
        return "K";
    }
}