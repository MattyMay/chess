package chess;

import java.io.Serializable;
import java.util.ArrayList;

public class ChessGame implements Serializable {
    Piece[][] board = new Piece[8][8];
    private Piece[][] proxyBoard = new Piece[8][8];
    private Player activePlayer;
    private Player victory;
    private boolean draw;
    private int fullMoves;
    private int halfMoves;
    private boolean whiteCanCastleQ;
    private boolean whiteCanCastleK;
    private boolean blackCanCastleK;
    private boolean blackCanCastleQ;

    private Player checked;
    King whiteKing;
    King blackKing;
    Piece lastMoved;

    public ChessGame() {
        this.victory = null;
        this.draw = false;
        this.fullMoves = 1;
        this.halfMoves = 0;
        this.activePlayer = Player.WHITE;
        this.blackCanCastleK = true;
        this.blackCanCastleQ = true;
        this.whiteCanCastleK = true;
        this.whiteCanCastleQ = true;

        blackKing = new King(Player.BLACK);
        whiteKing = new King(Player.WHITE);

        // *****Fills Array with Starting Board Position (Black occupying rows 0
        // and 1 of 7) *****//
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0) {
                    board[0][0] = new Rook(Player.BLACK, 'q');
                    board[0][1] = new Knight(Player.BLACK);
                    board[0][2] = new Bishop(Player.BLACK);
                    board[0][3] = new Queen(Player.BLACK);
                    board[0][4] = blackKing;
                    board[0][5] = new Bishop(Player.BLACK);
                    board[0][6] = new Knight(Player.BLACK);
                    board[0][7] = new Rook(Player.BLACK, 'k');
                    break;
                } else if (i == 1) {
                    board[1][j] = new Pawn(Player.BLACK);
                } else if (i == 2) {
                    board[6][j] = new Pawn(Player.WHITE);
                } else {
                    board[7][0] = new Rook(Player.WHITE, 'q');
                    board[7][1] = new Knight(Player.WHITE);
                    board[7][2] = new Bishop(Player.WHITE);
                    board[7][3] = new Queen(Player.WHITE);
                    board[7][4] = whiteKing;
                    board[7][5] = new Bishop(Player.WHITE);
                    board[7][6] = new Knight(Player.WHITE);
                    board[7][7] = new Rook(Player.WHITE, 'k');
                }
            }
        }
    }

    // *******Will take FEN board state and load a game from ***// --to do
    public boolean loadGame(String FEN_boardState) {
        return false;
    }

    // ***Will return a FEN board state of the current game ***// -- to do
    public String saveGame() {

        return "foobar";
    }

    // *** returns true if current board state is a draw ***// -- to do
    private boolean isDraw() {
        return false;
    }

    private boolean isWin(Piece[][] aBoard) {
        int moveCount = 0;
        boolean hasAMove = false;

        if (this.blackKing.isChecked(aBoard)) {
            ArrayList<BoardSpace> kingsMoves = this.blackKing.getAttackingSpaces(aBoard);
            for (int i = 0; i < kingsMoves.size(); i++) {
                if (this.blackKing.isValidPath(aBoard, this.blackKing.location,
                        kingsMoves.get(i))) {
                    hasAMove = this.makeProxyBoard(this.blackKing.location, kingsMoves.get(i),
                            this.blackKing) && !this.blackKing.isChecked(this.proxyBoard);
                    if (hasAMove) {
                        return false;
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (aBoard[i][j] != null && aBoard[i][j].player == Player.BLACK) {
                        ArrayList<BoardSpace> attackingSpaces = aBoard[i][j]
                                .getAttackingSpaces(aBoard);
                        for (int z = 0; z < attackingSpaces.size(); z++) {
                            if (aBoard[i][j].isValidPath(aBoard, new BoardSpace(i, j),
                                    attackingSpaces.get(z))) {
                                hasAMove = this.makeProxyBoard(new BoardSpace(i, j),
                                        attackingSpaces.get(z), aBoard[i][j])
                                        && !this.blackKing.isChecked(this.proxyBoard);
                                if (hasAMove) {
                                    return false;
                                }
                            }

                        }
                    }
                }
            }

        } else if (this.whiteKing.isChecked(aBoard)) {
            ArrayList<BoardSpace> kingsMoves = this.whiteKing.getAttackingSpaces(aBoard);
            for (int i = 0; i < kingsMoves.size(); i++) {
                if (this.whiteKing.isValidPath(aBoard, this.whiteKing.location,
                        kingsMoves.get(i))) {
                    hasAMove = this.makeProxyBoard(this.whiteKing.location, kingsMoves.get(i),
                            this.whiteKing) && !this.whiteKing.isChecked(this.proxyBoard);
                    if (hasAMove) {
                        return false;
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (aBoard[i][j] != null && aBoard[i][j].player == Player.WHITE) {
                        ArrayList<BoardSpace> attackingSpaces = aBoard[i][j]
                                .getAttackingSpaces(aBoard);
                        for (int z = 0; z < attackingSpaces.size(); z++) {
                            if (aBoard[i][j].isValidPath(aBoard, new BoardSpace(i, j),
                                    attackingSpaces.get(z))) {
                                hasAMove = this.makeProxyBoard(new BoardSpace(i, j),
                                        attackingSpaces.get(z), aBoard[i][j])
                                        && !this.whiteKing.isChecked(this.proxyBoard);
                                if (hasAMove) {
                                    return false;
                                }
                            }

                        }
                    }
                }
            }
        } else if (!this.blackKing.isChecked(aBoard) && !this.whiteKing.isChecked(aBoard)) {
            return false;
        }

        return true;

    }

    // *** If rooks or king were moved, appropriate canCastle boolean set to
    // false ***//
    private void setCastleStates(Piece movingPiece) {
        if (movingPiece.equals(this.blackKing)) {
            this.blackCanCastleK = false;
            this.blackCanCastleQ = false;
        }

        if (movingPiece.equals(this.whiteKing)) {
            this.blackCanCastleK = false;
            this.blackCanCastleQ = false;
        }

        if (movingPiece instanceof Rook) {
            Rook rook = (Rook) movingPiece;
            if (rook.side == 'q' && this.activePlayer == Player.BLACK) {
                this.blackCanCastleQ = false;
            } else if (rook.side == 'k' && this.activePlayer == Player.BLACK) {
                this.blackCanCastleK = false;
            } else if (rook.side == 'q' && this.activePlayer == Player.WHITE) {
                this.blackCanCastleQ = false;
            } else if (rook.side == 'k' && this.activePlayer == Player.WHITE) {
                this.blackCanCastleQ = false;
            }
        }
    }

    // *** returns a proxy board for checking game states (in movePiece method).
    // returns true if a piece was captured (for 50 move rule)
    // Contains capture logic, castling logic, and en passant logic.
    // assumes the path of moving piece is valid. ***//
    private boolean makeProxyBoard(BoardSpace from, BoardSpace to, Piece movingPiece) {
        int colDif = to.col - from.col;

        int modif;
        if (this.activePlayer == Player.BLACK) {
            modif = 1;
        } else
            modif = -1;
        Piece targetPiece = null;
        this.proxyBoard = new Piece[this.board.length][];
        for (int i = 0; i < this.board.length; i++) {
            this.proxyBoard[i] = this.board[i].clone();
        }

        if (this.board[to.row][to.col] != null) {
            targetPiece = this.board[to.row][to.col];
        }

        // Moves piece then uses castling logic --> passant logic --> capture
        // logic to move/remove target pieces. then returns proxy board in each
        // case.

        if (movingPiece instanceof King && Math.abs(colDif) > 1) {
            if (colDif == 2 && this.activePlayer == Player.BLACK) {
                this.proxyBoard[0][4] = null;
                this.proxyBoard[0][6] = movingPiece;
                this.proxyBoard[0][5] = this.proxyBoard[0][7];
                this.proxyBoard[0][7] = null;
            } else if (colDif == 2 && this.activePlayer == Player.WHITE) {
                this.proxyBoard[7][4] = null;
                this.proxyBoard[7][6] = movingPiece;
                this.proxyBoard[7][5] = this.proxyBoard[7][7];
                this.proxyBoard[7][7] = null;
            } else if (colDif == -2 && this.activePlayer == Player.BLACK) {
                this.proxyBoard[0][4] = null;
                this.proxyBoard[0][2] = movingPiece;
                this.proxyBoard[0][3] = this.proxyBoard[0][0];
                this.proxyBoard[0][0] = null;
            } else if (colDif == -2 && this.activePlayer == Player.WHITE) {
                this.proxyBoard[7][4] = null;
                this.proxyBoard[7][2] = movingPiece;
                this.proxyBoard[7][3] = this.proxyBoard[0][0];
                this.proxyBoard[7][0] = null;
            }
            return false;

        } else if (movingPiece instanceof Pawn && targetPiece == null
                && (to.col == from.col + 1 || to.col == from.col - 1)) {
            this.proxyBoard[to.row][to.col] = movingPiece;
            this.proxyBoard[to.row - 1 * modif][to.col] = null;
            this.proxyBoard[from.row][from.col] = null;
            return true;
        } else {
            this.proxyBoard[to.row][to.col] = movingPiece;
            this.proxyBoard[from.row][from.col] = null;
            return this.board[to.row][to.row] != null;
        }
    }

    // *** the idea here is to check path validity, then check game state
    // validity,
    // and then committing the move and updating board states.
    // Should be the only method the GUI uses (aside from getters)***//
    public boolean movePiece(BoardSpace from, BoardSpace to) {
        // grabs the piece to be moved. if there is no piece returns false;
        // gui probably shouldn't let a user try to move a null piece...
        if (this.victory == Player.WHITE || this.victory == Player.BLACK) {
            return false;
        }
        this.proxyBoard = null;
        Piece movingPiece = null;

        if (this.board[from.row][from.col] != null
                && this.board[from.row][from.col].player == this.activePlayer) {
            movingPiece = this.board[from.row][from.col];
        } else
            return false;

        // *** Checks to see if path is valid.
        if (!movingPiece.getAttackingSpaces(this.board).contains(to)) {
            return false;
        }

        // *** makes a proxy board to further check move validity before
        // committing.
        // If piece was captured sets pieceTaken flag to true for 50 move
        // rule***/
        boolean pieceTaken = this.makeProxyBoard(from, to, movingPiece);

        // *** this part checks to see if the move
        // *** would put the active player in check. ***///
        if (this.activePlayer == Player.BLACK && this.blackKing.isChecked(this.proxyBoard)) {
            return false;
        } else if (this.activePlayer == Player.WHITE && this.whiteKing.isChecked(this.proxyBoard)) {
            return false;
        }

        // *** If everything checks out, proxy board is copied to main board and
        // board states are saved.
        // if last moved piece was a pawn, sets that pawn's passant flag back
        // to false. ***//
        if (this.lastMoved instanceof Pawn) {
            ((Pawn) this.lastMoved).passantFlag = false;
        }
        this.board = this.proxyBoard;
        this.setCastleStates(movingPiece);
        this.draw = this.isDraw();

        // If move resulted in check or piece capture, halfMoves set to 0.
        // Else halfMoves incremented for 50 turn rule
        if (this.whiteKing.isChecked(board) || this.whiteKing.isChecked(board) || pieceTaken) {
            this.halfMoves = 0;
        } else
            this.halfMoves++;

        // Changes activePlayer and increments fullMoves if black moved.
        if (this.activePlayer == Player.BLACK) {
            this.activePlayer = Player.WHITE;
            this.fullMoves++;
        } else
            this.activePlayer = Player.BLACK;

        // Sets new lastMoved for passant checking
        if (movingPiece instanceof Pawn && Math.abs(to.row - from.row) == 2) {
            Pawn passantPawn = (Pawn) movingPiece;
            passantPawn.passantFlag = true;
        }
        this.lastMoved = this.board[to.row][to.col];
        if (this.isWin(this.board) && this.activePlayer == Player.BLACK) {
            this.victory = Player.WHITE;
        }else if (this.isWin(this.board) && this.activePlayer == Player.WHITE) {
            this.victory = Player.BLACK;
        }
        

        return true;
    }

    public void resign() {
        this.victory = this.activePlayer;
    }

    // *** Some getters for gui ***//

    // returns chars 'w' and 'b'
    public char getActivePlayer() {
        if (this.activePlayer == Player.WHITE) {
            return 'w';
        } else
            return 'b';
    }

    // returns chars 'w', 'b', and ' ' for no victory
    public char getVictory() {

        if (this.victory == Player.WHITE) {
            return 'w';
        } else if (this.victory == Player.BLACK) {
            return 'b';
        } else
            return ' ';
    }

    // returns 'w', 'b', and ' ' for no check
    public char getChecked() {

        if (this.checked == Player.WHITE) {
            return 'w';
        } else if (this.checked == Player.BLACK) {
            return 'b';
        } else
            return ' ';
    }

    public boolean getDraw() {
        return this.draw;
    }

    public int getFullMoves() {
        return this.fullMoves;
    }

    public int getHalfMoves() {
        return this.halfMoves;
    }

    public boolean getWhiteCanCastleK() {
        return this.whiteCanCastleK;
    }

    public boolean getWhiteCanCastleQ() {
        return this.whiteCanCastleQ;
    }

    public boolean getBlackCanCastleK() {
        return this.blackCanCastleK;
    }

    public boolean getBlackCanCastleQ() {
        return this.blackCanCastleQ;
    }
}
