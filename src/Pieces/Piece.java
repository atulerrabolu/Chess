package Pieces;

import Chess.Board;

import java.util.ArrayList;

public abstract class Piece {
    private String side;
    private Board board;
    private int row;
    private int col;

    public Piece(String side, Board board, int row, int col) {
        this.side = side;
        this.board = board;
        this.row = row;
        this.col = col;
    }

    public abstract ArrayList<int[]> getMoves();

    public String getSide() {
        return side;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public Board getBoard() {
        return board;
    }
}
