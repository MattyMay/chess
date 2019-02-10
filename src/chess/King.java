package chess;


import java.io.Serializable;
import java.util.ArrayList;


class King extends Piece implements Serializable{
    public boolean checked = false;
    public boolean canCastleQS = true;
    public boolean canCastleKS = true;
    public boolean tempCastle = true;

    ArrayList<Piece> checkingPieces = new ArrayList<Piece>();
    BoardSpace location = null;
    


    public King(Player player) {
        super(player);
        this.pieceType = "K";
        if (this.player.equals(Player.BLACK)) {
            this.playerStr = "B";
        } else {
            this.playerStr = "W";
        }
        this.image = this.loadImage();
    }

    public boolean canMove(ChessGame game, Piece[][] board) {
        BoardSpace[] possibleMoves = new BoardSpace[8];
        possibleMoves[0] = new BoardSpace(this.location.row + 1, this.location.col - 1);
        possibleMoves[1] = new BoardSpace(this.location.row + 1, this.location.col);
        possibleMoves[2] = new BoardSpace(this.location.row + 1, this.location.col + 1);
        possibleMoves[3] = new BoardSpace(this.location.row, this.location.col + 1);
        possibleMoves[4] = new BoardSpace(this.location.row - 1, this.location.col + 1);
        possibleMoves[5] = new BoardSpace(this.location.row - 1, this.location.col);
        possibleMoves[6] = new BoardSpace(this.location.row - 1, this.location.col - 1);
        possibleMoves[7] = new BoardSpace(this.location.row, this.location.col - 1);

        for (BoardSpace bs : possibleMoves) {
            try {
                if (this.isValidPath(board, location, bs)) {
                    return true;
                } else
                    continue;
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }
        return false;
    }

    // *** This method takes a board and checks every Piece if it has a valid
    // path to this king ***//
    public boolean isChecked(Piece[][] board) {
        boolean isChecked = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].equals(this)) {
                    this.location = new BoardSpace(i, j);
                    break;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    isChecked = board[i][j].getAttackingSpaces(board).contains(this.location);
                    if (isChecked == true) {
                        this.checkingPieces.add(board[i][j]);
                        return isChecked;
                    }
                }
            }
        }
        this.checkingPieces.clear();
        return isChecked;
    }

    @Override
    public boolean isValidPath(Piece[][] board, BoardSpace from, BoardSpace to) {
        int rowDif = Math.abs(to.row - from.row);
        int colDif = Math.abs(to.col - from.col);
        int startRow;
        BoardSpace fromTemp;
        if (this.player == Player.BLACK) {
            startRow = 0;
        } else {
            startRow = 7;
        }

        // returns true if path is valid castle path, and is not passing check.

        if (colDif == 2 && rowDif == 0 && from.row == startRow
                && board[to.row][to.col + 1] instanceof Rook
                && board[to.row][to.col + 1].player == this.player) {

            boolean passingCheck = false;

            if (this.canCastleKS && board[startRow][5] == null && board[startRow][6] == null) {

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] != null && board[i][j] != this
                                && board[i][j].player != this.player) {
                            fromTemp = new BoardSpace(i, j);
                            try {
                                passingCheck = board[fromTemp.row][fromTemp.col].isValidPath(board,
                                        fromTemp, new BoardSpace(startRow, 5));
                                if (passingCheck) {
                                    return false;
                                }
                                passingCheck = board[fromTemp.row][fromTemp.col].isValidPath(board,
                                        fromTemp, new BoardSpace(startRow, 6));
                                if (passingCheck) {
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                    }
                }
                return true;
            }

        } else if (colDif == -2 && rowDif == 0 && from.row == startRow
                && board[to.row][to.col - 2] instanceof Rook
                && board[to.row][to.col - 2].player == this.player) {
            boolean passingCheck;

            if (this.canCastleQS && board[startRow][1] == null && board[startRow][2] == null
                    && board[startRow][3] == null) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] != null && board[i][j] != this
                                && board[i][j].player != this.player) {
                            fromTemp = new BoardSpace(i, j);
                            try {
                                passingCheck = board[fromTemp.row][fromTemp.col].isValidPath(board,
                                        fromTemp, new BoardSpace(startRow, 2));
                                if (passingCheck) {
                                    return false;
                                }
                                passingCheck = board[fromTemp.row][fromTemp.col].isValidPath(board,
                                        fromTemp, new BoardSpace(startRow, 3));
                                if (passingCheck) {
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                    }
                }
            }
            return true;

            // checks if to space is occupied by another of player's pieces and
            // if
            // path is more than 1 space in any direction, or if starting
            // position was selected

        } else if (board[to.row][to.col] != null && board[to.row][to.col].player == this.player) {
            return false;
        } else if (rowDif > 1 || colDif > 1) {
            return false;
        } else if (rowDif == 0 && colDif == 0) {
            return false;
        }
        return true;

    }
}