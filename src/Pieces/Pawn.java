package Pieces;

import Chess.Board;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String side, int row, int col, Board board) {
        super(side, board, row, col);
    }

    public ArrayList<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<int[]>();

        if (getSide().equals("white")) {
            //Pawns can move 2 ahead in beginning
            if (getRow() == 6 && !getBoard().isPiece(4, getCol()))
                moves.add(new int[] {4, getCol()});

            getSpotMove(moves, 0, -1); //Up 1
            getAttackMove(moves, 1, -1); //Up right attack
            getAttackMove(moves, -1, -1); //Up left attack
        } else if (getSide().equals("black")) {
            //Pawns can move 2 ahead in beginning
            if (getRow() == 1)
                moves.add(new int[] {3, getCol()});

            getSpotMove(moves, 0, 1); //Down 1
            getAttackMove(moves, 1, 1); //Down right attack
            getAttackMove(moves, -1, 1); //Down left attack
        }

        return moves;
    }

    //Checks if the individual spot is valid
    public void getSpotMove(ArrayList<int[]> moves, int xMove, int yMove) {
        if (getBoard().isSafe(getRow() + yMove, getCol() + xMove) &&
                !getBoard().isTeamPiece(getSide(),getRow() + yMove, getCol() + xMove)) {
            moves.add(new int[] {getRow() + yMove, getCol() + xMove});
        }
    }

    //Checks if pawn can attack
    public void getAttackMove(ArrayList<int[]> moves, int xMove, int yMove) {
        if (getBoard().isSafe(getRow() + yMove, getCol() + xMove) &&
            getBoard().isEnemyPiece(getSide(),getRow() + yMove, getCol() + xMove)) {
            moves.add(new int[] {getRow() + yMove, getCol() + xMove});
        }
    }

    public String toString() {
        return "p";
    }
}
