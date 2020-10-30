package Chess;

import Pieces.*;

import java.util.*;

public class Board {
    private Piece[][] gameBoard;

    public Board() {
        gameBoard = new Piece[8][8];

        //Pawns
        for (int j = 0; j < gameBoard[0].length; j++) {
            gameBoard[1][j] = new Pawn("black", 1, j, this);
            gameBoard[6][j] = new Pawn("white", 6, j, this);
        }

        //Black back pieces
        gameBoard[0][0] = new Rook("black", 0, 0, this);
        gameBoard[0][1] = new Knight("black", 0, 1, this);
        gameBoard[0][2] = new Bishop("black", 0, 2, this);
        gameBoard[0][3] = new Queen("black", 0, 3, this);
        gameBoard[0][4] = new King("black", 0, 4, this);
        gameBoard[0][5] = new Bishop("black", 0, 5, this);
        gameBoard[0][6] = new Knight("black", 0, 6, this);
        gameBoard[0][7] = new Rook("black", 0, 7, this);

        //White back pieces
        gameBoard[7][0] = new Rook("white", 7, 0, this);
        gameBoard[7][1] = new Knight("white", 7, 1, this);
        gameBoard[7][2] = new Bishop("white", 7, 2, this);
        gameBoard[7][3] = new Queen("white", 7, 3, this);
        gameBoard[7][4] = new King("white", 7, 4, this);
        gameBoard[7][5] = new Bishop("white", 7, 5, this);
        gameBoard[7][6] = new Knight("white", 7, 6, this);
        gameBoard[7][7] = new Rook("white", 7, 7, this);

    }

    //The spot is within the bounds of the board
    public boolean isSafe(int row, int col) {
        if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length) {
            return false;
        }
        return true;
    }

    //The spot holds an enemy piece
    public boolean isEnemyPiece(String side, int row, int col) {
        return (gameBoard[row][col] != null
                && !gameBoard[row][col].getSide().equals(side));
    }

    //The spot holds a team piece
    public boolean isTeamPiece(String side, int row, int col) {
        return (gameBoard[row][col] != null
                && gameBoard[row][col].getSide().equals(side));
    }

    //There is any type of piece on the spot, teammate or enemy
    public boolean isPiece(int row, int col) {
        return gameBoard[row][col] != null;
    }


    public Piece[][] getGameBoard() {
        return gameBoard;
    }

    //Returns list of coordinates of all the white pieces
    public ArrayList<Piece> getWhitePieces() {
        ArrayList<Piece> whitePieces = new ArrayList<Piece>();

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] != null && gameBoard[i][j].getSide().equals("white"))
                    whitePieces.add(gameBoard[i][j]);
            }
        }

        return whitePieces;
    }

    //Returns a hash map of all the current position and possible moves of the white pieces
    public HashMap<int[], ArrayList<int[]>> getWhiteMoves() {
        HashMap<int[], ArrayList<int[]>> whiteMoves = new HashMap<>();
        for (Piece p : getWhitePieces())
            whiteMoves.put(new int[] {p.getRow(), p.getCol()}, p.getMoves());
         return whiteMoves;
    }

    //Returns list of coordinates of all the black pieces
    public ArrayList<Piece> getBlackPieces() {
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] != null && gameBoard[i][j].getSide().equals("black"))
                    blackPieces.add(gameBoard[i][j]);
            }
        }
        return blackPieces;
    }

    //Returns a hash map of all the current position and possible moves of the black pieces
    public HashMap<int[], ArrayList<int[]>> getBlackMoves() {
        HashMap<int[], ArrayList<int[]>> blackMoves = new HashMap<>();
        for (Piece p : getBlackPieces())
            blackMoves.put(new int[] {p.getRow(), p.getCol()}, p.getMoves());
        return blackMoves;
    }

    //Determines if there is a check position.
    public boolean isCheck(String side) {
        HashMap<int[], ArrayList<int[]>> whiteMovesMap = getWhiteMoves();
        HashMap<int[], ArrayList<int[]>> blackMovesMap = getBlackMoves();
        ArrayList<int[]> whiteMoves = new ArrayList<int[]>();
        ArrayList<int[]> blackMoves = new ArrayList<int[]>();

        //Convert the hashmaps into an arraylist of sole potential moves
        for (Map.Entry mapElement : whiteMovesMap.entrySet()) {
            int[] position = (int[]) mapElement.getKey();
            ArrayList<int[]> moves = ((ArrayList<int[]>) mapElement.getValue());
            whiteMoves.addAll(moves);
        }

        for (Map.Entry mapElement : blackMovesMap.entrySet()) {
            int[] position = (int[]) mapElement.getKey();
            ArrayList<int[]> moves = ((ArrayList<int[]>) mapElement.getValue());
            blackMoves.addAll(moves);
        }

        //White pieces check for attacking king
        if (side.equals("black"))
            for (int[] x : whiteMoves)
                for(int[] k : getKings())
                    //Enemy king can be attacked or killed in simulated move
                    if (k == null || Arrays.equals(x, k))
                        return true;

        //Black pieces check for attacking king
        if (side.equals("white"))
            for (int[] x : blackMoves)
                for(int[] k : getKings())
                    //Enemy king can be attacked or killed in simulated move
                    if (k == null || Arrays.equals(x, k))
                        return true;

        return false;
    }

    //Get the coordinates of the kings
    public ArrayList<int[]> getKings() {
        ArrayList<int[]> kings = new ArrayList<int[]>();

        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard[0].length; j++)
                //Is a king
                if (gameBoard[i][j] != null &&
                    gameBoard[i][j].toString().equals("K")) {
                    kings.add(new int[]{i, j});
                }
        return kings;
    }

    //Determines if a valid piece is being selected
    public boolean hasPosition(String turn, int[] position) {
        ArrayList<Piece> pieces = getWhitePieces();

        if (turn.equals("black"))
            pieces = getBlackPieces();

        for (Piece p : pieces) {
            int[] piecePos = {p.getRow(), p.getCol()};
            //If the position the user inputted is a valid piece on board
            if (Arrays.equals(piecePos, position) &&
               (gameBoard[piecePos[0]][piecePos[1]]).getMoves().size() > 0) { //If the piece can even move
                return true;
            }
        }

        return false;
    }

    //If the piece can move to a certain coordinate
    public boolean canMove(Piece piece, int[] moveTo) {
        ArrayList<int[]> moves = piece.getMoves();

        for (int[] m : moves)
            //if the position the user inputted is a valid spot the piece can move to
            if (Arrays.equals(m, moveTo))
                return true;

        return false;
    }

    //Gives a numeric valuation of how good a team is doing based on the pieces they have left
    public int evaluateSide(String side) {
        int score = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] != null && gameBoard[i][j].getSide().equals(side)) {
                    if (gameBoard[i][j].toString().equals("p"))
                        score += 1;
                    else if (gameBoard[i][j].toString().equals("k"))
                        score += 3;
                    else if (gameBoard[i][j].toString().equals("b"))
                        score += 3;
                    else if (gameBoard[i][j].toString().equals("r"))
                        score += 5;
                    else if (gameBoard[i][j].toString().equals("Q"))
                        score += 9;
                    else if (gameBoard[i][j].toString().equals("K"))
                        score += 1000; //Pieces.King is PRICELESS... okay 1000 is close enough :)
                }
            }
        }
        return score;
    }

    //Print the current status of the board
    public void printBoard() {
        System.out.println("      A   B   C   D   E   F   G   H");
        System.out.println("    ---------------------------------");
        for (int i = 0; i < gameBoard.length; i++) {
            System.out.print((i+1) + "   | ");
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == null)
                    System.out.print("  | ");
                else
                    System.out.print(gameBoard[i][j] + " | ");
            }
            System.out.println("\n    ---------------------------------");
        }
    }
}
