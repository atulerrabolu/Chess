package Chess;

import Pieces.Piece;

import java.util.*;

public class Game {
    public static void main(String[] args) {
        Board board = new Board();
        String turn = "white";
        String nextTurn = "black";
        String playAgainst = "AI";
        boolean run = true;
        Scanner kb = new Scanner(System.in);

        try {
            System.out.print("Press 1 if you want to play w/ a human, " +
                    "press 2 if you want to play w/ AI: ");
            int answer = kb.nextInt();
            if (answer != 2)
                playAgainst = "Human";
            kb.nextLine();

            while (run) {
                if (board.isCheck("white") || board.isCheck("black"))
                    System.out.println("CHECK!\n");
                if (playAgainst.equals("AI")) {
                    if (turn.equals("white")) {
                        board.printBoard();
                        //Select piece
                        System.out.print("Chose piece (Ex: C3): ");
                        String input = kb.nextLine();
                        System.out.println();

                        //Convert string position into grid position (Ex: C3 -> (2, 2))
                        int[] position = {Integer.parseInt(input.substring(1)) - 1,
                                convertLetter(input.substring(0, 1))};

                        //In case user chooses a spot that doesn't have a piece or the piece can't move
                        while (!board.hasPosition("white", position)) {
                            System.out.print("Invalid move, try again. Chose piece (Ex: C3): ");
                            input = kb.nextLine();
                            position = new int[]{Integer.parseInt(input.substring(1)) - 1,
                                    convertLetter(input.substring(0, 1))};
                            System.out.println();
                        }
                        Piece piece = (board.getGameBoard())[position[0]][position[1]]; //Get piece that will be moved

                        //Move piece to different spot
                        System.out.print("Move to (Ex: C3): ");
                        input = kb.nextLine();
                        int[] moveTo = new int[]{Integer.parseInt(input.substring(1)) - 1,
                                convertLetter(input.substring(0, 1))};
                        System.out.println();

                        //If piece can't move to that spot
                        while (!board.canMove(piece, moveTo)) {
                            System.out.print("Move to (Ex: C3): ");
                            input = kb.nextLine();
                            moveTo = new int[]{Integer.parseInt(input.substring(1)) - 1,
                                    convertLetter(input.substring(0, 1))};
                            System.out.println();
                        }
                        (board.getGameBoard())[position[0]][position[1]] = null; //Clear the old position on board
                        (board.getGameBoard())[moveTo[0]][moveTo[1]] = piece; //Update board
                        piece.setRow(moveTo[0]); //Update piece's row
                        piece.setCol(moveTo[1]); //Update piece's column
                    } else {
                        int maxScore = Integer.MIN_VALUE;
                        int[][] bestMove = new int[2][2];
                        for (Map.Entry mapElement : board.getBlackMoves().entrySet()) {
                            int[] position = (int[]) mapElement.getKey();
                            Piece piece = (board.getGameBoard())[position[0]][position[1]]; //Get piece that will be moved
                            Piece enemy;
                            ArrayList<int[]> moves = ((ArrayList<int[]>) mapElement.getValue());
                            if (piece != null) {
                                //Iterate through all possible moves
                                for (int[] moveTo : moves) {
                                    enemy = null;
                                    if (board.isEnemyPiece(turn, moveTo[0], moveTo[1]))
                                        enemy = (board.getGameBoard())[moveTo[0]][moveTo[1]];

                                    //Simulate taking the move
                                    (board.getGameBoard())[position[0]][position[1]] = null; //Clear the old position on board
                                    (board.getGameBoard())[moveTo[0]][moveTo[1]] = piece; //Update board
                                    piece.setRow(moveTo[0]); //Update piece's row
                                    piece.setCol(moveTo[1]); //Update piece's column

                                    int score = miniMax(board, "white", 0, board.evaluateSide("white"), board.evaluateSide("black"));

                                    //Untake the move
                                    (board.getGameBoard())[position[0]][position[1]] = piece;
                                    (board.getGameBoard())[moveTo[0]][moveTo[1]] = enemy;
                                    piece.setRow(position[0]);
                                    piece.setCol(position[1]);

                                    if (score > maxScore) {
                                        maxScore = score;
                                        bestMove = new int[][]{position, moveTo};
                                    }
                                }
                            }
                        }

                        int[] position = bestMove[0];
                        int[] moveTo = bestMove[1];

                        //Take the move
                        Piece piece = (board.getGameBoard())[position[0]][position[1]]; //Get piece that will be moved
                        (board.getGameBoard())[position[0]][position[1]] = null; //Clear the old position on board
                        (board.getGameBoard())[moveTo[0]][moveTo[1]] = piece; //Update board
                        if (piece != null)
                            piece.setRow(moveTo[0]); //Update piece's row
                        piece.setCol(moveTo[1]); //Update piece's column
                    }
                } else if (playAgainst.equals("Human")) {
                    board.printBoard();
                    //Select piece
                    System.out.print("Chose piece (Ex: C3): ");
                    String input = kb.nextLine();
                    System.out.println();

                    //Convert string position into grid position (Ex: C3 -> (2, 2))
                    int[] position = {Integer.parseInt(input.substring(1)) - 1,
                            convertLetter(input.substring(0, 1))};

                    //In case user chooses a spot that doesn't have a piece or the piece can't move
                    if (turn.equals("white")) {
                        while (!board.hasPosition("white", position)) {
                            System.out.print("Invalid move, try again. Chose piece (Ex: C3): ");
                            input = kb.nextLine();
                            kb.nextLine();
                            position = new int[]{Integer.parseInt(input.substring(1)) - 1,
                                    convertLetter(input.substring(0, 1))};
                            System.out.println();
                        }
                    } else if (turn.equals("black")) {
                        //In case user chooses a spot that doesn't have a piece or the piece can't move
                        while (!board.hasPosition("black", position)) {
                            System.out.print("Invalid move, try again. Chose piece (Ex: C3): ");
                            input = kb.nextLine();
                            position = new int[]{Integer.parseInt(input.substring(1)) - 1,
                                    convertLetter(input.substring(0, 1))};
                            System.out.println();
                        }
                    }

                    Piece piece = (board.getGameBoard())[position[0]][position[1]]; //Get piece that will be moved

                    //Move piece to different spot
                    seeMoves(piece);
                    System.out.print("Move to (Ex: C3): ");
                    input = kb.nextLine();
                    int[] moveTo = new int[]{Integer.parseInt(input.substring(1)) - 1,
                            convertLetter(input.substring(0, 1))};
                    System.out.println();

                    //If piece can't move to that spot
                    while (!board.canMove(piece, moveTo)) {
                        System.out.print("Move to (Ex: C3): ");
                        input = kb.nextLine();
                        moveTo = new int[]{Integer.parseInt(input.substring(1)) - 1,
                                convertLetter(input.substring(0, 1))};
                        System.out.println();
                    }
                    (board.getGameBoard())[position[0]][position[1]] = null; //Clear the old position on board
                    (board.getGameBoard())[moveTo[0]][moveTo[1]] = piece; //Update board
                    piece.setRow(moveTo[0]); //Update piece's row
                    piece.setCol(moveTo[1]); //Update piece's column
                }

                //Switch player turns
                if (turn.equals("white")) {
                    turn = "black";
                } else if (turn.equals("black")) {
                    turn = "white";
                }

                if (!(playAgainst.equals("AI") && turn.equals("black"))) {
                    System.out.print("Press 1 to continue playing, Press 2 to exit: ");
                    int play = kb.nextInt();
                    kb.nextLine();
                    if (play == 2)
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error: " + e + "\nPlease post this issue w\\ a brief " +
                                " description of what caused it on the Github repository issue section " +
                    "(found here: https://github.com/atulerrabolu/Chess/issues) so I can fix it asap!");
        }

        System.out.println("Thank you so much for playing! Please make sure to leave a like on the repository!" +
                            "\nIf you found any issues please post the issue w\\ a brief description of what caused" +
                " it on the Github repository issue section (found here: https://github.com/atulerrabolu/Chess/issues)");
    }

    //Minimax AI algorithm w/ alpha beta pruning
    public static int miniMax(Board board, String turn, int depth, int initWhiteScore, int initBlackScore) {
        if (depth >= 3) {
            //Encouraging enemy team loss while punishing self team loss.
            return (initWhiteScore - board.evaluateSide("white")) - (initBlackScore - board.evaluateSide("black"));
        }

        if (turn.equals("black")) {
            int maxScore = Integer.MIN_VALUE;
            //Try each possible move
            for (Map.Entry mapElement : board.getBlackMoves().entrySet()) {
                int[] position = (int[]) mapElement.getKey();
                Piece piece = (board.getGameBoard())[position[0]][position[1]]; //Get piece that will be moved
                Piece enemy = null;
                ArrayList<int[]> moves = ((ArrayList<int[]>) mapElement.getValue());
                boolean isBreak = false;
                if (piece != null) {
                    //Iterate through all possible moves
                    for (int[] moveTo : moves) {
                        enemy = null;
                        if (board.isEnemyPiece(turn, moveTo[0], moveTo[1]))
                            enemy = (board.getGameBoard())[moveTo[0]][moveTo[1]];

                        //Simulate taking the move
                        (board.getGameBoard())[position[0]][position[1]] = null; //Clear the old position on board
                        (board.getGameBoard())[moveTo[0]][moveTo[1]] = piece; //Update board
                        piece.setRow(moveTo[0]); //Update piece's row
                        piece.setCol(moveTo[1]); //Update piece's column

                        int score = miniMax(board, "white", depth + 1, initWhiteScore, initBlackScore);
                        maxScore = Math.max(maxScore, score);

                        //Untake the move
                        (board.getGameBoard())[position[0]][position[1]] = piece;
                        (board.getGameBoard())[moveTo[0]][moveTo[1]] = enemy;
                        piece.setRow(position[0]);
                        piece.setCol(position[1]);
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            //Try each possible move
            for (Map.Entry mapElement : board.getWhiteMoves().entrySet()) {
                int[] position = (int[]) mapElement.getKey();
                Piece piece = (board.getGameBoard())[position[0]][position[1]]; //Get piece that will be moved
                Piece enemy;
                ArrayList<int[]> moves = ((ArrayList<int[]>) mapElement.getValue());
                boolean isBreak = false;
                if (piece != null) {
                    //Iterate through all possible moves
                    for (int[] moveTo : moves) {
                        enemy = null;
                        if (board.isEnemyPiece(turn, moveTo[0], moveTo[1]))
                            enemy = (board.getGameBoard())[moveTo[0]][moveTo[1]];

                        //Simulate taking the move
                        (board.getGameBoard())[position[0]][position[1]] = null; //Clear the old position on board
                        (board.getGameBoard())[moveTo[0]][moveTo[1]] = piece; //Update board
                        piece.setRow(moveTo[0]); //Update piece's row
                        piece.setCol(moveTo[1]); //Update piece's column

                        int score = miniMax(board, "black", depth + 1, initWhiteScore, initBlackScore);
                        minScore = Math.min(minScore, score);

                        //Untake the move
                        (board.getGameBoard())[position[0]][position[1]] = piece;
                        (board.getGameBoard())[moveTo[0]][moveTo[1]] = enemy;
                        piece.setRow(position[0]);
                        piece.setCol(position[1]);
                    }
                }
            }
            return minScore;
        }
    }

    public static void seeMoves(Piece piece) {
        for(int[] x : piece.getMoves()) {
            System.out.println(Arrays.toString(x));
        }
    }

    public static int convertLetter(String letter) {
        String letters = "ABCDEFGH";
        return letters.indexOf(letter);
    }
}
